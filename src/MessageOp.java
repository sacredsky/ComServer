/* File operations */
/* by Jia Rao */
public class MessageOp implements MessageInfo {
	private String MessageFrom = null;
	private String content = null;
	public String getContent(){
		return content;
	}
	public String getFrom(){
		return MessageFrom;
	}
	public void setInfo(String MessageFrom, String content){
		this.MessageFrom = MessageFrom;
		this.content = content;
	}
}
