package com.eastros.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
	public static void main(String[] args) {
//		String host = "localhost";
		String host = "178.79.133.239";
		int port = 4444;
		
		Client c = new Client(host, port);
	}
}
