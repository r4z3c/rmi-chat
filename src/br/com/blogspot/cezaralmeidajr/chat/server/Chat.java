package br.com.blogspot.cezaralmeidajr.chat.server;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

import java.util.List;
import java.util.ArrayList;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Chat implements IChat {

	private List<Client> clients = new ArrayList<Client>();
	private List<ICallbackHandler> callbackHandlers = new ArrayList<ICallbackHandler>();

	public boolean connect(Client client) throws RemoteException {
		if(!this.clients.contains(client)){
			this.clients.add(client);
			this.notifyClientsChange();
			return true;
		}

		return false;
	}

	public boolean disconnect(Client client) throws RemoteException {
		if(this.clients.remove(client)){
			this.notifyClientsChange();
			return true;
		}

		return false;
	}

	private void notifyClientsChange(){
		for(ICallbackHandler callbackHandler : this.callbackHandlers){
			callbackHandler.clientsChanged(this.clients);
		}
	}

	public void sendMessage(IMessage message) throws RemoteException {
		for(ICallbackHandler callbackHandler : this.callbackHandlers){
			if(message.getTarget() == null || message.getTarget().equals(callbackHandler.getClient())){
				callbackHandler.messageReceived(message);
			}
		}
	}

	public boolean registerCallbackHandler(ICallbackHandler callbackHandler) throws RemoteException {
		if(!this.callbackHandlers.contains(callbackHandler)){
			this.callbackHandlers.add(callbackHandler);
			return true;
		}

		return false;
	}

	public boolean unregisterCallbackHandler(ICallbackHandler callbackHandler) throws RemoteException {
		return this.callbackHandlers.remove(callbackHandler);
	}

	public static void main(String args[]){
		try {
			System.out.println("|i| Starting chat server");

			if(System.getSecurityManager() == null){
				System.setSecurityManager(new SecurityManager());
			}

			Chat server = new Chat();
			IChat stub = (IChat) UnicastRemoteObject.exportObject(server,0);

			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Chat", stub);

			System.out.println("|i| Chat server online");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}