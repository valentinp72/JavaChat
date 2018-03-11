package chat.messages;

import java.io.Serializable;

/**
 * This class is the core component of a message the
 * client and the server can send an receive.
 */

public abstract class Message implements Serializable {

	/**
	 * Returns the message class anme
	 * @return the message class name
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns the message with a parameter (className='parameter')
	 *
	 * @param parameter the parameter
	 * @return the message
	 */
	public String toString(Object parameter) {
		return this.getClass().getSimpleName() + "='" + parameter.toString() + "'";
	}

}
