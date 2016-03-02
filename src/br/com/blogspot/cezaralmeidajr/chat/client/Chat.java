package br.com.blogspot.cezaralmeidajr.chat.client;

import br.com.blogspot.cezaralmeidajr.chat.core.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

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
		System.out.print("|?| Nickname: ");

		this.client = new Client(new Scanner(System.in).nextLine());

		if(chat.connect(this.client)){
			System.out.println("|i| Connected as " + this.client.name);
		} else {
			System.out.println("|E| Can't connect as " + this.client.name);
			throw new Exception();
		}
	}

	private void registerCallbackHandler() throws Exception {
		try {
			this.callbackHandler = new CallbackHandler(this.client);
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
			System.out.print("|?| Message: ");
			input = scanner.nextLine();

			quit = input.equals("q");
			if(!quit) {
				if(input.equals("h")){
					this.printHelp();
				} else {
					this.sendMessage(input);
				}
			}
		} while(!quit);
	}

	private void printHelp() {

	}

	private void sendMessage(String input) throws Exception {
		try {
			chat.sendMessage(new Message(this.client, input));
		} catch(Exception e) {
			this.teardown();
			throw e;
		}
	}

	public static void main(String args[]) throws Exception {
		new Chat().run(args);
	}

}