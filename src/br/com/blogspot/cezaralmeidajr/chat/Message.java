package br.com.blogspot.cezaralmeidajr.chat;

public class Message implements IMessage {

	private Long id;
	private String author;
	private String body;

	public String toString(){
		return "(" + id + ") From " + author + ": " + body;
	}

	public Message(){
		this.id = getSequence();
	}
	public Message(String author, String body){
		this.id = getSequence();
		this.author = author;
		this.body = body;
	}

	public Long getId(){
		return this.id;
	}

	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(String author){
		this.author = author;
	}

	public String getBody(){
		return this.body;
	}
	public void setBody(String body){
		this.body = body;
	}

	//

	private static Long sequence = 0L;
	private static Long getSequence(){
		sequence++;
		return sequence;
	}

}