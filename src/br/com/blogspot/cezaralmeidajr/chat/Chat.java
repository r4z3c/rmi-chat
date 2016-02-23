package br.com.blogspot.cezaralmeidajr.chat;

import java.util.List;
import java.util.ArrayList;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Chat implements IChat {

	private static List<IMessage> messages = new ArrayList<IMessage>();

	public List<IMessage> getMessages(){
		return messages;
	}
	public void sendMessage(String author, String body){
		IMessage message = new Message(author, body);
		messages.add(message);
	}

	public static void main(String args[]){
		try {
			System.out.println("Starting chat server");

			Chat chat = new Chat();
			IChat stub = (IChat) UnicastRemoteObject.exportObject(chat,0);

			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Chat", stub);

			System.out.println("Chat server online");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}