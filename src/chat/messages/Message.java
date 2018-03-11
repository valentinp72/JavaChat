package chat.messages;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 */
public abstract class Message implements Serializable {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * To string.
	 *
	 * @param parameter the parameter
	 * @return the string
	 */
	public String toString(Object parameter) {
		return this.getClass().getSimpleName() + "='" + parameter.toString() + "'";
	}

}
