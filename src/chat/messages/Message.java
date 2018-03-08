package chat.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

	public String toString() {
		return this.getClass().getSimpleName();
	}

	public String toString(Object parameter) {
		return this.getClass().getSimpleName() + "='" + parameter.toString() + "'";
	}

}
