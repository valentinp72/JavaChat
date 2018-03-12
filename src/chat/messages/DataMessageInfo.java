package chat.messages;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataMessageInfo extends DataMessage {

	/**
	 * Instantiates a new information message.
	 *
	 * @param user the sender
	 * @param message the message contents
	 */
	public DataMessageInfo(DataUser user, String message) {
		super(user, message);
	}

	/**
	 * Gets the message color
	 *
	 * @return the color to be displayed
	 */
	public Color getColor() {
		return Color.GRAY;
	}

}
