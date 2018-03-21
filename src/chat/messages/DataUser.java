package chat.messages;

import java.awt.Color;
import java.io.Serializable;

/**
 * This class represents a user in the chat.
 */

public class DataUser implements Serializable {

	/** The username */
	private String username;

	/** The unique color of the user */
	private Color color;

	/**
	 * Instantiates a new data user. A color is given to the user
	 * according to it's username.
	 *
	 * @param username the username of the user
	 */
	public DataUser(String username) {
		this.username = username;

		// color of user is "unique" for each username
		int hash = username.hashCode();
		this.setColor(hash);
	}

	/**
	 * Returns the username
	 * @return the username
	 */
	public String toString() {
		return this.getUsername();
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color of the user
	 *
	 * @param c the new color
	 */
	public void setColor(Color c) {
		this.color = c;
	}

	/**
	 * Sets the color of the user, from an integer
	 * containing the three color components
	 *
	 * @param c the new color
	 */
	public void setColor(int c) {
		int r = (c & 0xFF0000) >> 16;
		int g = (c & 0x00FF00) >> 8;
		int b = (c & 0x0000FF);
		this.color = new Color(r, g, b);

		// we make sure the color is dark enough to be printed on white
		double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
		if(luminance > 200) {
			this.color = color.darker().darker();
		}
	}

	/**
	 * Sets the color of the user, from String
	 * like "#rrbbgg"
 	 *
	 * @param hexColor the new color
	 */
	public void setColor(String hexColor) {
		this.setColor(Integer.decode(hexColor));
	}

	public Color getBackgroundColor() {
		return Color.LIGHT_GRAY;
	}

}
