package chat.messages;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataMessage implements Serializable {
	private String username;
	private String message;
	private Date   time;
	private DateFormat dformat;

	public DataMessage(String username, String message) {
		this.username = username;
		this.message  = message;
		this.time     = new Date();
		this.dformat  = new SimpleDateFormat("HH:mm");
	}

	public String toString() {
		return "[" + this.username + " | " + this.time + "] - " + this.message;
	}

	public String getUsername() {
		return this.username;
	}

	public String getMessage() {
		return this.message;
	}

	public String getTime() {
		return this.dformat.format(this.time);
	}
}
