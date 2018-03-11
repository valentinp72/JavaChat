package chat.messages;

import java.util.Random;
import java.awt.Color;
import java.io.Serializable;

public class DataUser implements Serializable {

	private String username;
	private Color  color;

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

	public String toString() {
		return this.getUsername();
	}

	public String getUsername() {
		return this.username;
	}

	public Color getColor() {
		return this.color;
	}

}
