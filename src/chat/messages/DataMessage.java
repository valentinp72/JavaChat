package chat.messages;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class DataMessage.
 */
public class DataMessage implements Serializable {

	/** The user. */
	private DataUser   user;
	
	/** The message. */
	private String     message;
	
	/** The time. */
	private Date       time;
	
	/** The dformat. */
	private DateFormat dformat;

	/**
	 * Instantiates a new data message.
	 *
	 * @param user the user
	 * @param message the message
	 */
	public DataMessage(DataUser user, String message) {
		this.user     = user;
		this.message  = message;
		this.time     = new Date();
		this.dformat  = new SimpleDateFormat("HH:mm");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + this.user + " | " + this.time + "] - " + this.message;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public DataUser getUser() {
		return this.user;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return this.dformat.format(this.time);
	}
}
