package br.com.blogspot.cezaralmeidajr.chat.client;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

public class Message implements IMessage {

	private Client author;
	private Client target;
	private String body;

	public Message(Client author, Client target, String body){
		this.author = author;
		this.target = target;
		this.body = body;
	}

	public Client getAuthor(){
		return this.author;
	}
	public void setAuthor(Client author){
		this.author = author;
	}

	public Client getTarget(){
		return this.target;
	}
	public void setTarget(Client target){
		this.target = target;
	}

	public String getBody(){
		return this.body;
	}
	public void setBody(String body){
		this.body = body;
	}

}