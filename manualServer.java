import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Protocol.*;
import GameLogic.pictionaryPlayer;
/**
* Testing Class for manual sending of input to client:
*/
public class manualServer{
	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
		Scanner sc = new Scanner(System.in);
		Socket s = server.accept();
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		System.out.println(s);
        String[] ln;
        // Replace this with deserialization routines
        while (true) {
        	ln = sc.nextLine().split(" "); 
            System.out.println(ln[0]);
        	switch (ln[0]) {
            case "DRAW":
                // int x,int y
	            out.writeObject(new datagramDRAW("",Integer.parseInt(ln[1]),
            						     Integer.parseInt(ln[2])));
        		break;
        	case "ENDGAME":
                    List<pictionaryPlayer> playerz = new ArrayList<pictionaryPlayer>();

                    for (int i=1; i<ln.length-1; i+=2) {
                        playerz.add(new pictionaryPlayer(ln[i],Integer.parseInt(ln[i+1])));
                    }
                out.writeObject(new datagramENDGAME(playerz));                              
        		break;
        	case "STARTROUND":
                    System.out.println("Here");
                    int tdur = Integer.parseInt(ln[1]);
                    int wlen = Integer.parseInt(ln[2]);
                    String drawer = ln[3];
                    List<pictionaryPlayer> players = new ArrayList<pictionaryPlayer>();

                    for (int i=4; i<ln.length-1; i+=2) {
                        players.add(new pictionaryPlayer(ln[i],Integer.parseInt(ln[i+1])));
                    }
                out.writeObject((pictionaryDatagram)new datagramSTARTROUND(tdur, wlen, drawer,players));
        		break;
        	case "HINT":
                //int pos,String chr
                out.writeObject(new datagramHINT(Integer.parseInt(ln[1]),ln[2]));
        		out.flush();
        		break;
            case "PLAYERMSG":
                //String playerName,String contents
                out.writeObject(new datagramPLAYERMSG(ln[1],ln[2]));
        	default:
        		break;
        	}
        }
	}
}