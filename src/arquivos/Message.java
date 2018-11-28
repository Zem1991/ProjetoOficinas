
package arquivos;

public class Message
{

	private String id, message;

	public Message(String values[])
        {
		id=values[0];
		message=values[1];
	}

	public Message() {}

	public Message(String id, String message)
        {
		this.id = id;
		this.message = message;
	}

	public String getId()
        {
		return id;
	}

	public void setId(String id)
        {
		this.id = id;
	}

	public String getMessage()
        {
		return message;
	}

	public void setMessage(String message)
        {
		this.message = message;
	}
}
