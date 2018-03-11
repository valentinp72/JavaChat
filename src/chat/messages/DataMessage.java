package chat.messages;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataMessage implements Serializable {

	private DataUser   user;
	private String     message;
	private Date       time;
	private DateFormat dformat;

	public DataMessage(DataUser user, String message) {
		this.user     = user;
		this.message  = message;
		this.time     = new Date();
		this.dformat  = new SimpleDateFormat("HH:mm");
	}

	public String toString() {
		return "[" + this.user + " | " + this.time + "] - " + this.message;
	}

	public DataUser getUser() {
		return this.user;
	}

	public String getMessage() {
		return this.message;
	}

	public String getTime() {
		return this.dformat.format(this.time);
	}
}
