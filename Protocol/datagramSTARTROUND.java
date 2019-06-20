package Protocol;

import Protocol.pdSpecies;
import GameLogic.pictionaryPlayer;

import java.io.Serializable;
import java.util.List;
/**
* Datagram for starting a game round
*/
public class datagramSTARTROUND extends pictionaryDatagram implements Serializable {
	public int tdur;
	public int wlen;
	public String drawer;
	public List<pictionaryPlayer> players;


	/**
	* Class constructor
	*/
	public datagramSTARTROUND(int tdur, int wlen, String drawer, List<pictionaryPlayer> players) {
		super(pdSpecies.STARTROUND);
		this.tdur = tdur;
		this.wlen = wlen;
		this.drawer = drawer;
		this.players = players;


	}
}