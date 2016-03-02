package br.com.blogspot.cezaralmeidajr.chat.core;

import java.io.Serializable;

public interface IMessage extends Serializable {

	public void setAuthor(Client author);
	public Client getAuthor();

	public void setTarget(Client target);
	public Client getTarget();

	public void setBody(String body);
	public String getBody();

	public String toString();

}