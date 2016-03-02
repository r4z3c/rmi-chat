package br.com.blogspot.cezaralmeidajr.chat.core;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallbackHandler extends Remote {

	public Client getClient() throws RemoteException;
	public List<Client> getClients() throws RemoteException;
	public void messageReceived(IMessage message) throws RemoteException;
	public void clientsChanged(List<Client> clients) throws RemoteException;

}