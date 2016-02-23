package br.com.blogspot.cezaralmeidajr.chat;

import java.util.List;
import java.util.ArrayList;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {

	public class MessagePool implements Runnable {

		private Long lastMessageId = 0L;
		private IChat chat;

		public MessagePool(IChat chat){
			this.chat = chat;
		}

		public void run(){
			while(true){
				for(IMessage message : chat.getMessages()){
					if(message.getId() > this.lastMessageId){
						this.lastMessageId = message.getId();
						System.out.println(message.toString());
					}
				}

				Thread.sleep(5000);
			}
		}
	}

	public static void main(String args[]){
		try {
			System.out.println("Starting chat client");

			Registry registry = LocateRegistry.getRegistry(1099);
			IChat chat = (IChat) registry.lookup("Chat");

			MessagePool messagePool = new MessagePool(chat);
			Thread messagePoolThread = new Thread(messagePool);
			messagePoolThread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}