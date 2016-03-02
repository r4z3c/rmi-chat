package br.com.blogspot.cezaralmeidajr.chat.core;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {

	public boolean connect(Client client) throws RemoteException;
	public boolean disconnect(Client client) throws RemoteException;

	public void sendMessage(IMessage message) throws RemoteException;

	public boolean registerCallbackHandler(ICallbackHandler callbackHandler) throws RemoteException;
	public boolean unregisterCallbackHandler(ICallbackHandler callbackHandler) throws RemoteException;

}