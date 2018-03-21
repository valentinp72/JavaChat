package chat.messages;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

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

	/** The hash making the link between emojis and human translation */
	private static Map<String,String> emojis;

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
	 * Sets the message of the current DataMessage
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Replaces emojis in the message with unicode ones
	 */
	public void replaceEmojis() {
		String s = this.getMessage();

		for(Map.Entry<String, String> pair : emojis.entrySet()) {
			String toReplace   = pair.getKey();
			String replacement = pair.getValue();

			// replace the emojis with the corresponding emoji
			s = s.replaceAll("(?<!:)" + Pattern.quote(toReplace), replacement);

			// now remove the emojis that started with !
			s = s.replaceAll(":" + Pattern.quote(toReplace), toReplace);

		}

		this.setMessage(s);
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

	/**
	 * Returns the map of emojis and shortcuts
	 *
	 * @return the map with all the shortcuts as the key and the unicode emojis as the emojis.
	 */
	public static Map<String, String> getEmojis() {
		return emojis;
	}

	/**
	 * Defines emojis and shortcuts to them
	 */
	static {

		// http://www.fileformat.info/info/unicode/block/emoticons/list.htm
		emojis = new HashMap<String, String>();

		emojis.put(":)", "😊");
		emojis.put(":(", "😟");
		emojis.put(":D", "😃");
		emojis.put(";)", "😉");
		emojis.put(":|", "😑");
		emojis.put(":/", "😕");
		emojis.put(":p", "😛");
		emojis.put(":X", "😝");
		emojis.put("oO", "😲");

		emojis.put(":laughing:",         "😆");
		emojis.put(":smiley:",           "😃");
		emojis.put(":smile:",            "😄");
		emojis.put(":sweat_smile:",      "😅");
		emojis.put(":joy:",              "😂");
		emojis.put(":grinning:",         "😀");
		emojis.put(":grin:",             "😁");
		emojis.put(":innocent:",         "😇");
		emojis.put(":wink:",             "😉");
		emojis.put(":blush:",            "😊");
		emojis.put(":neutral_face:",     "😐");
		emojis.put(":sweat:",            "😓");
		emojis.put(":confused:",         "😕");
		emojis.put(":stuck_out_tongue:", "😛");
		emojis.put(":expressionless:",   "😑");
		emojis.put(":sleeping:",         "😴");
		emojis.put(":cold_sweat:",       "😰");
		emojis.put(":scream:",           "😱");
		emojis.put(":astonished:",       "😲");
		emojis.put(":raised_hands:",     "🙌");
		emojis.put(":rage:",             "😡");
		emojis.put(":smiling_imp:",      "😈");

		emojis.put(":see_no_evil:",      "🙈");
		emojis.put(":hear_no_evil:",     "🙉");
		emojis.put(":speak_no_evil:",    "🙊");

		emojis.put(":smile_cat:",        "😸");
		emojis.put(":joy_cat:",          "😹");
		emojis.put(":smiley_cat:",       "😺");
		emojis.put(":heart_eyes_cat:",   "😻");
		emojis.put(":smirk_cat:",        "😼");
		emojis.put(":kissing_cat:",      "😽");
		emojis.put(":pouting_cat:",      "😾");
		emojis.put(":crying_cat_face:",  "😿");
		emojis.put(":scream_cat:",       "🙀");

	}

}
