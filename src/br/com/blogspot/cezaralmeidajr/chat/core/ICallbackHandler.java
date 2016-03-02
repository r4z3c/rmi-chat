package br.com.blogspot.cezaralmeidajr.chat.core;

import java.io.Serializable;
import java.util.List;

public interface ICallbackHandler extends Serializable {

	public Client getClient();
	public void messageReceived(IMessage message);
	public void clientsChanged(List<Client> clients);
	public boolean equals(Object object);

}