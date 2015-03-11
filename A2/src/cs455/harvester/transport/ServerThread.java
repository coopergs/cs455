package cs455.harvester.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerThread extends Thread{
	
	private ServerSocket server;
	private ArrayList<Reciever> recievers;
	
	public ServerThread(int port){
		try {
			server = new ServerSocket(port);
			recievers = new ArrayList<Reciever>();
		} catch (IOException e) {
			System.out.println("Error setting up connections");
			System.exit(1);
		}
	}
	
	public void run(){
		while(!Thread.interrupted()){
			try {
				server.setSoTimeout(500);
			} catch (SocketException e) {
				System.err.println("Error with the thread.");
			}
			try {
				Socket s = server.accept();
				recievers.add(new Reciever(s));
			} catch (IOException e) {}
		}
	}

}
