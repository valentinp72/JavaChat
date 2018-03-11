package chat.messages;

import java.awt.Color;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class DataUser.
 */
public class DataUser implements Serializable {

	/** The username. */
	private String username;
	
	/** The color. */
	private Color  color;

	/**
	 * Instantiates a new data user.
	 *
	 * @param username the username
	 */
	public DataUser(String username) {
		this.username = username;

		// color of user is "unique" for each username
		int hash = username.hashCode();
		int r = (hash & 0xFF0000) >> 16;
		int g = (hash & 0x00FF00) >> 8;
		int b = (hash & 0x0000FF);

		this.color = new Color(r, g, b);

		// we make sure the color is dark enough to be printed on white
		double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
		System.out.println(luminance);
		if(luminance > 200) {
			this.color = color.darker().darker();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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

}
