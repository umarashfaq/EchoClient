package com.eastros.echo;

import java.io.BufferedReader;

public class Receiver extends Thread {
	private BufferedReader reader;
	private Client client;
	private boolean running = true;
	
	public Receiver(BufferedReader reader, Client client) {
		super();
		this.reader = reader;
		this.client = client;
	}

	@Override
	public void run() {
		try {			
			while ( running ) {
				String line = reader.readLine();
				System.out.println("Server says: "+line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void shutdown() {
		System.out.println("Closing receiver loop ...");
		this.running = false;
		
		System.out.println("Interrupting the thread ...");
		this.interrupt();
		
		System.out.println("Receiver has shutdown");
	}
}
