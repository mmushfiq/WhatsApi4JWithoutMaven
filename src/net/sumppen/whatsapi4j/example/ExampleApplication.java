package net.sumppen.whatsapi4j.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONException;
import org.json.JSONObject;

import net.sumppen.whatsapi4j.EventManager;
import net.sumppen.whatsapi4j.MessageProcessor;
import net.sumppen.whatsapi4j.WhatsApi;
import net.sumppen.whatsapi4j.WhatsAppException;

public class ExampleApplication {

	private enum WhatsAppCommand {
		send,request,register,status
	}

        static String username = "YourPhoneNumber";  //Example: 994707351114
	static String password = ""; //Generate by WhatsApp, example: OcsRdOE+ziJdNRljnsOU5Ee6W5c=
        static String identity = "IMEI"; //example:864877021261702   p.s. type *#06# on your phone screen  
        static String nickname = "nickname"; // example: MushfiqMammadov

	public static void main(String[] args) {
            startProgram(username,password,identity,nickname);
	}
        
        private static void startProgram(String _username, String _password, String _identity, String _nickname){
        
            boolean running = true;
		boolean loggedIn = false;
		Logger.getRootLogger().setLevel(Level.ALL);
		Layout layout = new PatternLayout("%d [%t] %-5p %c %x - %m%n");
		String filename = "exampleapplication.log";
		try {
			Logger.getRootLogger().addAppender(new FileAppender(layout, filename));
		} catch (IOException e1) {
			System.err.println("Failed to open logfile");
			e1.printStackTrace();
		}
                Scanner sc = new Scanner(System.in);

		String username = _username;
		String password = _password;
		if(password.length() == 0) {
			password = null;
		}
		String identity = _identity;
		if(identity.length() == 0) {
			identity = null;
		}
		String nickname = _nickname;
		WhatsApi wa = null;
		try {
			wa = new WhatsApi(username, identity, nickname);

			EventManager eventManager = new ExampleEventManager();
			wa.setEventManager(eventManager );
			MessageProcessor mp = new ExampleMessageProcessor();
			wa.setNewMessageBind(mp);
			if(!wa.connect()) {
				System.out.println("Failed to connect to WhatsApp");
				System.exit(1);
			}
			if(password != null) {
				wa.loginWithPassword(password);
				loggedIn = true;
			}
			String cmd;
			ExampleMessagePoller poller = new ExampleMessagePoller(wa);
			poller.start();
			System.out.print("$ ");
                        while(running && (cmd=sc.nextLine()) != null) {
                            try {
                                    WhatsAppCommand wac = WhatsAppCommand.valueOf(cmd);
                                    switch(wac) {
                                    case send:
                                            if(loggedIn) {
                                                    sendMessage(sc,wa);
                                            } else {
                                                    System.out.println("Not logged in!");
                                            }
                                            break;
                                    case status:
                                            if(loggedIn) {
                                                    setStatus(sc,wa);
                                            } else {
                                                    System.out.println("Not logged in!");
                                            }
                                            break;
                                    case request:
                                            if(!loggedIn) {
                                                    sendRequest(wa);
                                                    running = false;
                                            } else {
                                                    System.out.println("Already logged in!");
                                            }
                                            break;
                                    case register:
                                            if(!loggedIn) {
                                                    sendRegister(sc,wa);
                                                    running = false;
                                            } else {
                                                    System.out.println("Already logged in!");
                                            }
                                            break;
                                    default: 
                                            System.out.println("Unknown command: "+cmd);
                                    }
                            } catch (IllegalArgumentException e) {
                                    if(cmd.length() > 0)
                                            System.out.println("Unknown command: "+cmd);
                            }
                            System.out.print("$ ");
                    }
                        poller.setRunning(false);
                        System.out.println("Done! Logging out");
                        wa.disconnect();
		} catch (Exception e) {
			System.out.println("Caught exception: "+e.getMessage());
			e.printStackTrace();
			if(wa != null) {
				wa.disconnect();
			}

                        System.exit(1);
		}
        }

	private static void setStatus(Scanner sc, WhatsApi wa) throws WhatsAppException {
		System.out.print("Status: ");
                String status = sc.nextLine();
		if(status == null || status.length() == 0) {
			return;
		}
		wa.sendStatusUpdate(status);
		System.out.println("Ok");
	}

	private static void sendRegister(Scanner sc, WhatsApi wa) throws JSONException, WhatsAppException {
		System.out.print("Code: ");
                String code = sc.nextLine();
		if(code == null || code.length() == 0) {
			return;
		}
		JSONObject res = wa.codeRegister(code);
		System.out.println(res.toString(2));
	}

	private static void sendRequest(WhatsApi wa) throws WhatsAppException, JSONException, UnsupportedEncodingException {
		JSONObject resp = wa.codeRequest("sms", null, null);
		System.out.println("Registration sent: "+resp.toString(2));
	}

	private static void sendMessage(Scanner sc, WhatsApi wa) throws WhatsAppException {
		System.out.print("To: ");
                String to = sc.nextLine();
		if(to == null || to.length() == 0) {
			return;
		}
		System.out.print("Message: ");
                String message = sc.nextLine();
		if(message == null || message.length() == 0) {
			return;
		}
		String res = wa.sendMessage(to, message);
		System.out.println(res);
	}
        
}
