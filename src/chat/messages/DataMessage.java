package chat.messages;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the content of a message, both used by the server and the client.
 */
public class DataMessage implements Serializable {

	/** The user that sent the message*/
	private DataUser user;

	/** The content of the message */
	private String message;

	/** The date the message has been made*/
	private Date time;

	/** The format to display the date */
	private DateFormat dformat;

	/**
	 * Instantiates a new message.
	 *
	 * @param user the sender
	 * @param message the message contents
	 */
	public DataMessage(DataUser user, String message) {
		this.user     = user;
		this.message  = message.replaceAll("(?m)^\\s", "");
		this.time     = new Date();
		this.dformat  = new SimpleDateFormat("HH:mm");
	}

	/**
	 * Returns the message
	 * @return the message
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
	 * Gets the message color
	 *
	 * @return the color to be displayed
	 */
	public Color getColor() {
		return Color.BLACK;
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
