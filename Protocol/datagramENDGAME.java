package Protocol;

import Protocol.pdSpecies;
import GameLogic.pictionaryPlayer;

import java.io.Serializable;
import java.util.List;

/**
* Endgame indicator datagram
*/
public class datagramENDGAME extends pictionaryDatagram implements Serializable {
	public List<pictionaryPlayer> players;

	/**
	* Class constructor
	*/
	public datagramENDGAME(List<pictionaryPlayer> players) {
		super(pdSpecies.ENDGAME);
		this.players = players;
	}
}