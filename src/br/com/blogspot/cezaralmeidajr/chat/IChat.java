package br.com.blogspot.cezaralmeidajr.chat;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {

	public void sendMessage(String author, String message) throws RemoteException;
	public List<IMessage> getMessages() throws RemoteException;

}