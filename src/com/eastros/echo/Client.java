package com.eastros.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private String host;
	private int port;
	
	private boolean running = true;
	private Socket s = null;
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	private BufferedReader consoleReader = null;
	private Receiver receiver = null;
	
	public Client(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		
		this.startup();
	}
	
	private void startup() {
		
		try {
			s = new Socket(host, port);
			System.out.println("Connected to server");
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			writer = new PrintWriter(s.getOutputStream(), true);
			consoleReader = new BufferedReader(new InputStreamReader(System.in));
			
			// start reader in a seperate thread
			// (new Thread(new Receiver(reader))).start();
			receiver = new Receiver(reader, this);
			receiver.start();
			
			System.out.println("Waiting for an input ...");
			while ( running ) {				
				String line = consoleReader.readLine();
//				if ( "exit".equals(line) ) {
//					this.shutdown();
//				}
				writer.println(line);
			}
			
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		} finally {
			try { s.close(); reader.close(); writer.close(); consoleReader.close();} catch( Exception e ) {}
		}
	}
	
	public void shutdown() {
		System.out.println("Closing the loop ...");
		this.running = false;
		
		// close opened resources		
		System.out.println("Closing resources ...");
		this.receiver.shutdown();
		try { s.close(); reader.close(); writer.close(); consoleReader.close(); } catch( Exception e ) {}
		
		
		System.out.println("Client has shutdown");
	}
}
