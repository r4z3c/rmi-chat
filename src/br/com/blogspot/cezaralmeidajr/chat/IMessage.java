package br.com.blogspot.cezaralmeidajr.chat;

import java.io.Serializable;

public interface IMessage extends Serializable {

	public Long getId();

	public void setAuthor(String author);
	public String getAuthor();

	public void setBody(String body);
	public String getBody();

	public String toString();

}