/* The serializable file object */
/* by Jia Rao */
import java.io.Serializable;
public interface MessageInfo extends Serializable {
	String getFrom();
	String getContent();
	void setInfo(String MessageFrom, String content);
}
