package br.com.blogspot.cezaralmeidajr.chat;

import java.util.List;
import java.util.ArrayList;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {

	public static void main(String args[]){
		try {
			System.out.println("|i| Starting chat client");

			if(args.length < 2){
				throw new Exception("|E| Expected 'author' and 'message' args");
			}

			Registry registry = LocateRegistry.getRegistry(1099);
			IChat chat = (IChat) registry.lookup("Chat");

			System.out.println("|i| Fetching server messages");

			for(IMessage message : chat.getMessages()){
				System.out.println(message.toString());
			}

			System.out.println("|i| Sending your message");

			chat.sendMessage(args[0], args[1]);

			System.out.println("|i| Fetching server messages");

			for(IMessage message : chat.getMessages()){
				System.out.println(message.toString());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}