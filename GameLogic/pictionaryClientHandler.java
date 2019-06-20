package GameLogic;

import Protocol.*;

import java.io.*;
import java.net.*;

/**
* Handles all incoming client requests such as drawing and player guesses for the server
*/ 
class pictionaryClientHandler extends Thread {
	pictionaryServer srv;
	Socket s;
	ObjectInputStream clientInput;
	ObjectOutputStream clientOutput;
	/**
	* Constructor which references socket for comms and the server for interrupt and modification
	*/
	public pictionaryClientHandler(pictionaryServer srv, Socket s) {
		this.srv = srv;
		this.s = s;
		try{
			this.clientInput = new ObjectInputStream(s.getInputStream()); 
			this.clientOutput = new ObjectOutputStream(s.getOutputStream()); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		srv.serverOutputStreams.add(clientOutput);
		System.out.println(clientInput);
	}

	/**
	* Thread routine for server. Loops forever looking for messages and handing them.
	*/
	public synchronized void run(){
	    	try{
			    while(true){
		            pictionaryDatagram dg = (pictionaryDatagram) clientInput.readObject();
		            // System.out.println(dg);
		            if (!srv.started) {

		            	if(dg.species==pdSpecies.PLAYERMSG){
		            	System.out.print("New Player:");
		            	System.out.println(((datagramPLAYERMSG)dg).playerName);
		            	srv.players.add(new pictionaryPlayer(((datagramPLAYERMSG)dg).playerName,0));

		            }
		            }
		            else {
		            	switch (dg.species) {
		            		case PLAYERMSG:
		            			System.out.println(((datagramPLAYERMSG)dg).playerName);
		            			System.out.println(((datagramPLAYERMSG)dg).contents);
			            		for (int i=0;i<srv.players.size();i++) {
			            			pictionaryPlayer playerCpy = srv.players.get(i);
			            			// If this is the right player.
			            			if (((datagramPLAYERMSG)dg).playerName==playerCpy.name) {
			            				System.out.println("Processing:"+playerCpy.name);
			            				System.out.println("Guessed right before:"+playerCpy.hasGuessedWord);
			            				System.out.println("The word:"+srv.curword);

			            				// If the guess was right and the player hasn't guessed
			            				if (((datagramPLAYERMSG)dg).contents.toLowerCase().equals(srv.curword.toLowerCase())&&(!playerCpy.hasGuessedWord)){
			            					System.out.println("He got it");

			            					// If the player hasn't just entered the word in again
			            					int delta = (int)(srv.guessTime-(srv.roundStartTime-System.currentTimeMillis()/1000L));
			            					System.out.println(delta);
			            					if (!playerCpy.hasGuessedWord) {
			            						playerCpy.score+=delta;	
			            					}

			            					playerCpy.hasGuessedWord = true;
			            					srv.players.set(i,playerCpy);
			            					System.out.println(srv.players);
			            					srv.sendPlayerMsg(new datagramPLAYERMSG("Server ",((datagramPLAYERMSG)dg).playerName+" guessed the word!"));


											// Check to make sure that all players haven't guessed the word.
		            						Boolean allDone=true;

		            						for (pictionaryPlayer p:srv.players) {
		            							if (p.hasGuessedWord=false&&!p.name.equals(srv.drawer)) {
		            								allDone=false;
		            							}
		            						}
		            						// If all players have guessed the word
		            						if (allDone) {
		            							srv.done=true;
		            							System.out.println("All players have guessed the word");
		            							//srv.notify();

		            						}

			            				}
			            				else{
			            					srv.sendPlayerMsg(dg);
			            				}

			            			}
			            		}

		            			break;
		            		case DRAW:
			            		srv.sendPlayerMsg(dg);
		            			break;
		            		default:
		            			break;		            			
		            	}
		            }
		        	}
		        }
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
}


