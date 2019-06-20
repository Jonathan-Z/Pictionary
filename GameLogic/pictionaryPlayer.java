package GameLogic;
import java.io.Serializable;
/**
* Holding class for pictionary player data
*/
public class pictionaryPlayer implements Serializable{
	public String name;
	public int score;
	public Boolean hasGuessedWord;

	/**
	* Class constructor
	*/ 
	public pictionaryPlayer(String name, int score){
		this.name = name;
		this.score = score;
		this.hasGuessedWord = false;
	}

	public String toString(){
		return String.format("{name:%s,score:%d,:hasGuessedWord%b}",name,score,hasGuessedWord);
	}

}