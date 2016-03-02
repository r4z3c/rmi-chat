package br.com.blogspot.cezaralmeidajr.chat.client;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.RemoteException;

public class CallbackHandler implements ICallbackHandler, Serializable {

	private Client client;
	private List<Client> clients;

	public CallbackHandler(Client client){
		this.client = client;
	}

	public Client getClient() throws RemoteException {
		return this.client;
	}

	public List<Client> getClients() throws RemoteException {
		return this.clients;
	}

	public void messageReceived(IMessage message) throws RemoteException {
		if(!message.getAuthor().equals(this.client)){
			System.out.print("\n|!| Message from " + message.getAuthor().name + ": " + message.getBody() + "\n|?| Send: " );
		}
	}

	public void clientsChanged(List<Client> clients) throws RemoteException {
		this.clients = new ArrayList<Client>();
		for(Client client : clients) if(!client.equals(this.client)) this.clients.add(client);
	}

	@Override
	public boolean equals(Object object) {
		try {
			CallbackHandler other = (CallbackHandler) object;
			return this.client.equals(other.getClient());
		} catch (RemoteException re) {
			return false;
		}
	}

}