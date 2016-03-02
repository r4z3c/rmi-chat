package br.com.blogspot.cezaralmeidajr.chat.core;

import java.io.Serializable;

public class Client implements Serializable {

	public String name;

	public Client(String name){
		this.name = name;
	}

	@Override
	public boolean equals(Object object) {
		if(object == null) return false;
		if(!Client.class.isAssignableFrom(object.getClass())) return false;

		Client other = (Client) object;

		if(this.name == null){
			if(other.name == null) return true;
			return false;
		}

		return this.name.equals(other.name);
	}

}