package br.com.blogspot.cezaralmeidajr.chat.client;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

import java.util.List;

public class CallbackHandler implements ICallbackHandler {

	private Client client;

	public CallbackHandler(Client client){
		this.client = client;
	}

	public Client getClient(){
		return this.client;
	}

	public void messageReceived(IMessage message) {
		System.out.println("|i| messageReceived@CallbackHandler");
	}

	public void clientsChanged(List<Client> clients) {
		System.out.println("|i| clientsChanged@CallbackHandler");
	}

	@Override
	public boolean equals(Object object) {
		CallbackHandler other = (CallbackHandler) object;
		return this.client.equals(other.getClient());
	}

}