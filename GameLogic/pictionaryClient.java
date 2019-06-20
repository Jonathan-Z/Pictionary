package GameLogic;
import Protocol.*;
import UI.pictionaryGameClientUI;

import javax.swing.SwingWorker;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
* Concurrent Client which runs alongside and updates GUI
*/
public class pictionaryClient extends SwingWorker<Void, pictionaryDatagram> {
	private pictionaryGameClientUI pg;
	private String hostIP;
	private int portNumber;
    private ObjectInputStream in;
    private Socket s;
	/**
	* Class constructor
	* @param hostIP hostIP
	* @param portNumber port number on server
	*/
	public pictionaryClient(Socket s, pictionaryGameClientUI pg) throws Exception{
        this.s = s;
        System.out.println("Before in");
		this.in = new ObjectInputStream(s.getInputStream());;
        System.out.println("After in");

		this.pg = pg;
    }
 
     /**
     * Process an incoming server datagram by examining its type and doing the appropriate UI updates
     */
    @Override
    protected void process(List<pictionaryDatagram> dgs) {
        pictionaryDatagram dg = dgs.get(dgs.size() - 1);
        switch (dg.species) {
        	// Client Recived Messages
        	case DRAW:
		        pg.cv.ptDwnNoSend((datagramDRAW)dg);
        		break;
        	case ENDGAME:
                pg.ap.setUsers((datagramENDGAME)dg);
                pg.cv.reset();
                pg.cv.setEditable(false);
                pg.cl.setClock(0);
                pg.gp.newWord(0);
                pg.omnichat.append("\nGame Over!\n");
                JOptionPane.showMessageDialog(null, "Game Over!");
        		break;
        	case STARTROUND:
                System.out.println(((datagramSTARTROUND)dg).players);
                pg.ap.setUsers((datagramSTARTROUND)dg);
                pg.cl.setClock(((datagramSTARTROUND)dg).tdur);
                pg.cv.reset();
                pg.cv.setEditable(((datagramSTARTROUND)dg).drawer.equals(pg.usrname));
                pg.omnichatInput.setEditable(!((datagramSTARTROUND)dg).drawer.equals(pg.usrname));

                pg.omnichat.append("\nNew Round: "+((datagramSTARTROUND)dg).drawer+" is drawing!\n");
                pg.gp.newWord(((datagramSTARTROUND)dg).wlen);

                break;
            case PLAYERMSG:
        		pg.omnichat.append(((datagramPLAYERMSG)dg).playerName+":"+((datagramPLAYERMSG)dg).contents+"\n");
                break;
        	case HINT:
        		pg.gp.hint((datagramHINT)dg);
        	default:
        		break;
        }

		}

    /**
    * Reads incoming stream from server
    */
    @Override
    protected Void doInBackground() throws Exception{
        while(!isCancelled()){
                pictionaryDatagram dg = (pictionaryDatagram) in.readObject();
                System.out.println(dg);
                publish(dg);
            }
        
        return null;
}
}