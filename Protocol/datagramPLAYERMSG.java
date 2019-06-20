package Protocol;

import Protocol.pdSpecies;
import GameLogic.pictionaryPlayer;

import java.io.Serializable;
import java.util.List;

/**
* Player Message (Guess) Datagram
*/
public class datagramPLAYERMSG extends pictionaryDatagram implements Serializable {
	public String contents;
	public String playerName;

	/**
	* Class constructor
	*/
	public datagramPLAYERMSG(String playerName, String contents) {
		super(pdSpecies.PLAYERMSG);
		this.playerName = playerName;
		this.contents = contents;
	}
}

