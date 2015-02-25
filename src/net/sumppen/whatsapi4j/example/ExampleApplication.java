package net.sumppen.whatsapi4j.example;

import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
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
import org.apache.commons.codec.binary.Base64;

public class ExampleApplication {

	private enum WhatsAppCommand {
		send,request,register,status
	}
        
        //358401122333 'mypassword' 'mytestapplication' 'My Test Account'
        //acount with father's number: 994503732410  ieG5MsrFL5Gjo3a4VOvfG644N/U=  353686053691811  Mr.Mammadov
        //account Cavid:  994707351114  OcsRdOE+ziJdNRljnsOU5Ee6W5c=   864877021261702   Mr.Mammadov
        static String username = "994707351114";  //994707118199 994503732410
	static String password = "OcsRdOE+ziJdNRljnsOU5Ee6W5c="; // me-6ujnlSWDuztu/AcGzCNCCmlOBJU=   870297
        static String identity = "864877021261702"; //  me-357409053106904 Leyla-353722051974303 ata-353686053691811
        static String nickname = "Mr.Mammadov"; //Leyla Aliyeva  Mushfiq Mammadov

	public static void main(String[] args) {
//            usingProxyForConnect();
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
//		if(args.length != 4) {
//			System.out.println("Usage: ExampleApplication <username> <password> <id> <nick>");
//			System.exit(1);
//		}
		Console cons = System.console();
                Scanner sc = new Scanner(System.in);
//                String i = sc.nextLine();
//		if(cons == null) {
//			System.out.println("No console found. Aborting");
//			System.exit(1);
//		}

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
//                        String cmd="send";
			ExampleMessagePoller poller = new ExampleMessagePoller(wa);
			poller.start();
			System.out.print("$ ");
//			while(running && (cmd=cons.readLine()) != null) {
                            
                            while(running && (cmd=sc.nextLine()) != null) {
				try {
					WhatsAppCommand wac = WhatsAppCommand.valueOf(cmd);
					switch(wac) {
					case send:
						if(loggedIn) {
//							sendMessage(cons,wa); 
                                                        sendMessage(sc,wa);
						} else {
							System.out.println("Not logged in!");
						}
						break;
					case status:
						if(loggedIn) {
//							setStatus(cons,wa);
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
//							sendRegister(cons,wa);
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

	private static void setStatus(/*Console cons*/Scanner sc, WhatsApi wa) throws WhatsAppException {
		System.out.print("Status: ");
//		String status = cons.readLine();
                String status = sc.nextLine();
		if(status == null || status.length() == 0) {
			return;
		}
		wa.sendStatusUpdate(status);
		System.out.println("Ok");
	}

	private static void sendRegister(/*Console cons*/Scanner sc, WhatsApi wa) throws JSONException, WhatsAppException {
		System.out.print("Code: ");
//		String code = cons.readLine();
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

	private static void sendMessage(/*Console cons*/Scanner sc, WhatsApi wa) throws WhatsAppException {
		System.out.print("To: ");
//		String to = cons.readLine();
                String to = sc.nextLine();
		if(to == null || to.length() == 0) {
			return;
		}
		System.out.print("Message: ");
//		String message = cons.readLine();
                String message = sc.nextLine();
		if(message == null || message.length() == 0) {
			return;
		}
		String res = wa.sendMessage(to, message);
		System.out.println(res);
	}
        
        
        
         //---------------------------------------------------------------------------    
    public static void usingProxyForConnect() {
        try {
//            System.setProperty("http.proxyHost", "proxy");
//            System.setProperty("http.proxyPort", "3128");
//            System.setProperty("https.proxyHost", "proxy");
//            System.setProperty("https.proxyPort", "3128");          
             
//            Authenticator.setDefault(new DummyAuthenticator());
            
            
            System.setProperty("socksProxyHost", "proxy");  //socksProxyHost socksProxyPort
            System.setProperty("socksProxyPort", "3128");
            System.setProperty("java.net.socks.username", "mushfigm");
            System.setProperty("java.net.socks.password", "186207");
            Authenticator.setDefault(new ProxyAuth("mushfigm", "186207"));
            
            ProxySelector.setDefault(null);
            
        } catch (Exception e) {
            System.out.println("usingProxyForConnect catch-->" + e);
        }
    } 
     
    
    public static class DummyAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("mushfigm", "186207".toCharArray());
        }
    }
    

    public static class ProxyAuth extends Authenticator {
        private PasswordAuthentication auth;

        private ProxyAuth(String user, String password) {
            auth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return auth;
        }
    }
    




}
