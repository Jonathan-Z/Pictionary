package Protocol;

import Protocol.pdSpecies;

import java.io.Serializable;
import java.util.List;
import java.awt.Color;
/**
* Datgram for pictionary drawing data
*/
public class datagramDRAW extends pictionaryDatagram implements Serializable {
	public String playerName;
	public int x;
	public int y;
	public Color c;
	/**
	* Class constructor
	*/
	public datagramDRAW(String playerName, int x, int y) {
		this(playerName, x, y, Color.black);
	}
	
	/**
	* Class constructor
	*/
	public datagramDRAW(String playerName, int x, int y, Color c) {
		super(pdSpecies.DRAW);
		this.playerName = playerName;
		this.x = x;
		this.y = y;
		this.c = c;
	}
}