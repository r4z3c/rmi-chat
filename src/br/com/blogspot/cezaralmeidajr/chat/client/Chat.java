package br.com.blogspot.cezaralmeidajr.chat.client;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Chat {

	private IChat chat;
	private Client client;
	private ICallbackHandler callbackHandler;

	public void run(String args[]) throws Exception {
		this.setup();
		this.live();
		this.teardown();
	}

	private void setup() throws Exception {
		this.startChatClient();
		this.connect();
		this.registerCallbackHandler();
	}

	// -- setup

	private void startChatClient() throws Exception {
		System.out.println("|i| Starting chat client");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		Registry registry = LocateRegistry.getRegistry();
		this.chat = (IChat) registry.lookup("Chat");
	}

	private void connect() throws Exception {
		while(true){
			System.out.print("|?| Nickname: ");

			String nickname = new Scanner(System.in).nextLine();

			if(nickname.indexOf(" ") >= 0){
				System.out.println("|E| Can't connect as " + nickname + ": should not contain blank spaces");
				continue;
			}

			this.client = new Client(nickname);

			if(chat.connect(this.client)){
				System.out.println("|i| Connected as " + this.client.name);
				break;
			} else {
				System.out.println("|E| Can't connect as " + this.client.name + ": already in use");
			}
		}
	}

	private void registerCallbackHandler() throws Exception {
		try {
			this.callbackHandler = new CallbackHandler(this.client);
			UnicastRemoteObject.exportObject(this.callbackHandler, 0);
			if(!chat.registerCallbackHandler(this.callbackHandler)){
				System.out.println("|E| Can't register callback handler");
				throw new Exception();
			}
		} catch(Exception e) {
			this.disconnect();
			throw e;
		}
	}

	private void disconnect() throws Exception {
		this.chat.disconnect(this.client);
		System.out.println("|i| Disconnected");
	}

	// -- tear down

	private void teardown() throws Exception {
		this.unregisterCallbackHandler();
		UnicastRemoteObject.unexportObject(this.callbackHandler, false);
		this.disconnect();
	}

	private void unregisterCallbackHandler() throws Exception {
		try {
			if(!chat.unregisterCallbackHandler(this.callbackHandler)){
				System.out.println("|E| Can't unregister callback handler");
				throw new Exception();
			}
		} catch(Exception e) {
			this.disconnect();
			throw e;
		}
	}

	// -- live

	private void live() throws Exception {
		String input = null;
		Scanner scanner = new Scanner(System.in);
		boolean quit = false;

		System.out.println("|i| Type 'h' for help or 'q' for quit");

		do {
			System.out.print("|?| Send: ");
			input = scanner.nextLine();

			quit = input.equals("q");
			if(!quit) {
				if(input.equals("h")){
					this.printHelp();
				} else if(input.equals("c")){
					this.printClients();
				} else {
					this.sendMessage(input);
				}
			}
		} while(!quit);
	}

	private void printHelp() {
		System.out.println("\n--------------------------------------- HELP ---------------------------------------\n");
		System.out.println("\tTo send a public message, just type it and press ENTER");
		System.out.println("\tTo send a private message, type '\\nickname message' and press ENTER");
		System.out.println("\tTo display online clients list, type 'c' and press ENTER");
		System.out.println("\n--------------------------------------- HELP ---------------------------------------\n");
	}

	private void printClients() throws Exception {
		List<String> nicknames = new ArrayList<String>();
		for(Client client : this.callbackHandler.getClients()){
			nicknames.add(client.name);
		}
		System.out.println("|!| Online clients: " + String.join(", ", nicknames));
	}

	private void sendMessage(String input) throws Exception {
		try {
			Client target = null;
			String message = input;

			if(input.startsWith("\\")){
				int nicknameSeparatorPosition = input.indexOf(" ");
				String targetNickname = input.substring(1, nicknameSeparatorPosition);
				message = input.substring(nicknameSeparatorPosition + 1);
				target = this.getClientByNickname(targetNickname);
			}

			chat.sendMessage(new Message(this.client, target, message));
		} catch(Exception e) {
			this.teardown();
			throw e;
		}
	}

	private Client getClientByNickname(String nickname) throws Exception {
		for(Client client : this.callbackHandler.getClients()) if(client.name.equals(nickname)) return client;
		return null;
	}

	public static void main(String args[]) throws Exception {
		new Chat().run(args);
	}

}