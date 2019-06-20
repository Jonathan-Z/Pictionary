package GameLogic;

import java.util.ArrayList;
import java.util.Arrays;

import java.net.*; 
import java.io.*; 

/**
* Handles the aquisition of incoming clinent connections until termination
*/ 
public class pictionaryLobbyThread extends Thread {
	pictionaryServer srv;
	/**
	* Class constructor
	*/ 
	public pictionaryLobbyThread(pictionaryServer srv){
		this.srv = srv;
	}
	/**
	* Thread routine after instantiation. Starts new client handler for each new connection.
	*/
	public void run(){
		System.out.println("Lobby Thread Started!");
		while(true){
			try {
				Socket s = srv.serverSocket.accept();
				System.out.println(s);
				new pictionaryClientHandler(srv,s).start();

			} catch (IOException e) {
				e.printStackTrace(); 
			}

		}
	}

}