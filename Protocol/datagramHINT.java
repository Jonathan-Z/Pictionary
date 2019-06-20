package Protocol;

import Protocol.pdSpecies;

import java.io.Serializable;
import java.util.List;

/**
* Hint datagram
*/
public class datagramHINT extends pictionaryDatagram implements Serializable {
	public int    pos;
	public String chr;

	/**
	* Class constructor
	*/
	public datagramHINT(int pos, String chr) {
		super(pdSpecies.HINT);
		this.pos = pos;
		this.chr = chr;
	}
}