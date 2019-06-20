package Protocol;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
* Used to transport pictionary data over the network though object serialization. Parent class of all other datagrams.
*/
public class pictionaryDatagram implements Serializable {
	public pdSpecies species; // What kind of data is being sent
	public pictionaryDatagram(pdSpecies s) {
		this.species = s;
	}
}