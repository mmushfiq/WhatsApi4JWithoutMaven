package net.sumppen.whatsapi4j;

import java.net.Socket;
import java.util.List;
import java.util.Map;

public interface EventManager {

	public abstract void fireClose( 
			String phone, // The user phone number including the country code.
			String error  // The error message. 
			);

	public abstract void fireCodeRegister(
			String phone, // The user phone number including the country code.
			String login, // Phone number with country code.
			String pw,    // Account password.
			String type,  //, // Type of account.
			String expiration, //Expiration date in UNIX TimeStamp.
			String kind,  // Kind of account.
			String price, // Formated price of account.
			String cost,  // Decimal amount of account.
			String currency, // Currency price of account.
			String price_expiration // Price expiration in UNIX TimeStamp.
			);

	public abstract void fireCodeRegisterFailed(
			String phone, // The user phone number including the country code.
			String status, // The server status number
			String reason, // Reason of the status (e.g. too_recent/missing_param/bad_param).
			String retry_after// Waiting time before requesting a new code in seconds.
			);

	public abstract void fireCodeRequest(
			String phone, // The user phone number including the country code.
			String method, // Used method (SMS/voice).
			String length// Registration code length.
			);

	public abstract void fireCodeRequestFailed(
			String phone, // The user phone number including the country code.
			String method, // Used method (SMS/voice).
			String reason, // Reason of the status (e.g. too_recent/missing_param/bad_param).
			String value// The missing_param/bad_param or waiting time before requesting a new code.
			);

	public abstract void fireCodeRequestFailedTooRecent(
			String phone, // The user phone number including the country code.
			String method, // Used method (SMS/voice).
			String reason, // Reason of the status (too_recent).
			String retry_after // Waiting time before requesting a new code in seconds.
			);

	public abstract void fireConnect(
			String phone, // The user phone number including the country code.
			String socket // The resource socket id.
			);

	public abstract void fireConnectError(
			String phone, // The user phone number including the country code.
			String socket // The resource socket id.
			);

	public abstract void fireCredentialsBad(
			String phone, // The user phone number including the country code.
			String status, // Account status.
			String reason // The reason.
			);

	public abstract void fireCredentialsGood(
			String phone, // The user phone number including the country code.
			String login, // Phone number with country code.
			String pw, // Account password.
			String type, // Type of account.
			String expiration, // Expiration date in UNIX TimeStamp.
			String kind, // Kind of account.
			String price, // Formated price of account.
			String cost, // Decimal amount of account.
			String currency, // Currency price of account.
			String price_expiration // Price expiration in UNIX TimeStamp.
			);

	public abstract void fireDisconnect(
			String phone, // The user phone number including the country code.
			Socket socket
			);

	public abstract void fireDissectPhone(
			String phone, // The user phone number including the country code.
			String country, // The detected country name.
			String cc, // The number's country code.
			String mcc, // International cell network code for the detected country.
			String lc, // Location code for the detected country
			String lg // Language code for the detected country
			);

	public abstract void fireDissectPhoneFailed(
			String phone // The user phone number including the country code.
			);

	public abstract void fireGetAudio(
			String phone, // The user phone number including the country code.
			String from, // The sender phone number.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			String size, // The image size.
			String url, // The url to bigger audio version.
			String file, // The audio name.
			String mimetype, // The audio mime type.
			String filehash, // The audio file hash.
			String duration, // The audio duration.
			String acodec // The audio codec.
			);

	public abstract void fireGetError(
			String phone, // The user phone number including the country code.
			String id, // The id of the request that caused the error
			String error // Array with error data for why request failed.
			);

	public abstract void fireGetGroups(
			String phone, // The user phone number including the country code.
			Map<String, String> groupList
			);

	public abstract void fireGetGroupsInfo(
			String phone, // The user phone number including the country code.
			Map<String, String> groupList
			);

	public abstract void fireGetGroupsSubject(
			String phone, // The user phone number including the country code.
			String[] reset_from, // The group JID.
			String time, // The unix time when send subject.
			String[] reset_author, // The author phone number including the country code.
			String[] reset_author2, // The participant phone number including the country code.
			String name, // The sender name.
			byte[] bs
			);

	public abstract void fireGetImage(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			String size, // The image size.
			String url, // The url to bigger image version.
			String file, // The image name.
			String mimetype, // The image mime type.
			String filehash, // The image file hash.
			String width, // The image width.
			String height, // The image height.
			byte[] bs
			);

	public abstract void fireGetLocation(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			String place_name, // The place name.
			String longitude, // The location longitude.
			String latitude, // The location latitude.
			String url, // The place url.
			byte[] bs
			);

	public abstract void fireGetMessage(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			byte[] bs
			);

	public abstract void fireGetGroupMessage(
			String phone, // The user phone number including the country code.
			String from, // The group JID.
			String author, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			byte[] bs
			);

	public abstract void fireGetGroupParticipants(
			String phone,
			String groupId,
			Map<String, String> groupList            
			);

	public abstract void fireGetPrivacyBlockedList(
			String phone, // The user phone number including the country code.
			List<ProtocolNode> list
			);

	public abstract void fireGetProfilePicture(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String type, // The type of picture (image/preview).
			byte[] bs
			);

	public abstract void fireGetRequestLastSeen(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String sec // The number of seconds since the user went offline.
			);

	public abstract void fireGetServerProperties(
			String phone, // The user phone number including the country code.
			String version, // The version number on the server.
			Map<String, String> props
			);

	public abstract void fireGetStatus(
			String phone,
			String from,
			String type,
			String id,
			String t,
			byte[] bs
			);

	public abstract void fireGetvCard(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			String contact, // The vCard contact name.
			byte[] bs
			);

	public abstract void fireGetVideo(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time, // The unix time when send message notification.
			String name, // The sender name.
			String url, // The url to bigger video version.
			String file, // The video name.
			String size, // The video size.
			String mimetype, // The video mime type.
			String filehash, // The video file hash.
			String duration, // The video duration.
			String vcodec, // The video codec.
			String acodec, // The audio codec.
			byte[] bs
			);

	public abstract void fireGroupsChatCreate(
			String phone, // The user phone number including the country code.
			String gId // The group JID.
			);

	public abstract void fireGroupsChatEnd(
			String phone, // The user phone number including the country code.
			String gId // The group JID.
			);

	public abstract void fireGroupsParticipantsAdd(
			String phone, // The user phone number including the country code.
			String groupId, // The group JID.
			String participant // The participant JID.
			);

	public abstract void fireGroupsParticipantsRemove(
			String phone, // The user phone number including the country code.
			String groupId, // The group JID.
			String participant, // The participant JID.
			String author // The author JID.
			);

	public abstract void fireLogin(
			String phone // The user phone number including the country code.
			);

	public abstract void fireLoginFailed(
			String phone, // The user phone number including the country code.
			String tag
			);

	public abstract void fireMediaMessageSent(
			String phone, // The user phone number including the country code.
			String to,
			String id,
			String filetype,
			String url,
			String filename,
			String filesize,
			byte[] icon        
			);

	public abstract void fireMediaUploadFailed(
			String phone, // The user phone number including the country code.
			String id,
			ProtocolNode node,  
			Map<String, Object> messageNode,
			String reason
			);

	public abstract void fireMessageComposing(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time // The unix time when send message notification.
			);

	public abstract void fireMessagePaused(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time // The unix time when send message notification.
			);

	public abstract void fireMessageReceivedClient(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type, // The message type.
			String time // The unix time when send message notification.
			);

	public abstract void fireMessageReceivedServer(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String msgid, // The message id.
			String type // The message type.
, String t
			);

	public abstract void firePing(
			String phone, // The user phone number including the country code.
			String msgid // The message id.
			);

	public abstract void firePresence(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String type // The presence type.
			);

	public abstract void fireProfilePictureChanged(
			String phone, 
			String from,
			String id,
			String t            
			);

	public abstract void fireProfilePictureDeleted(
			String phone, 
			String from,
			String id,
			String t            
			);

	public abstract void fireSendMessage(
			String phone, // The user phone number including the country code.
			String targets,
			String id,
			ProtocolNode node 
			);

	public abstract void fireSendMessageReceived(
			String phone, // The user phone number including the country code.
			String from, // The sender JID.
			String type // Message type
			);

	public abstract void fireSendPong(
			String phone, // The user phone number including the country code.
			String msgid // The message id.
			);

	public abstract void fireSendPresence(
			String phone, // The user phone number including the country code.
			String type, // Presence type.
			String name  // User nickname.
			);

	public abstract void fireSendStatusUpdate(
			String phone, // The user phone number including the country code.
			String msg  // The status message.
			);

	public abstract void fireUploadFile(
			String phone, // The user phone number including the country code.
			String name, // The filename.       
			String url  // The remote url on WhatsApp servers (note, // this is NOT the URL to download the file, only used for sending message).
			);

	public abstract void fireUploadFileFailed(
			String phone, // The user phone number including the country code.
			String name // The filename.     
			);

	/**
	 * @param String result SyncResult
	 * @return mixed
	 */
	public abstract void fireGetSyncResult(
			String result
			);

	public abstract void fireGetReceipt(
			String from,
			String id,
			String offline,
			String retry
			);
}
