package UI;


import Protocol.datagramHINT;

import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;
/**
* Container for top bar for guess and hint.
*/
public class guessPanel extends JPanel {
	private String guess;
	private ArrayList<JTextField> guessesList;

	/**
	* Constructor
	*/
	public guessPanel() {
		super();
		this.setBackground(Color.pink);
		setPreferredSize(new Dimension(600, 50));
		this.setVisible(true);
		this.guessesList = new ArrayList<JTextField>();

	}
	/**
	* Put in data from a hint
	*/
	public void hint(datagramHINT dg) {
		hint(dg.pos, dg.chr);
	}


	/**
	* Put in a hint
	*/
	public void hint(int pos, String chr) {
		if (pos < guessesList.size()) {guessesList.get(pos).setText(chr);}
	}

	/**
	* Reset the panel
	*/
	public void newWord(int len) {
		this.removeAll();
		this.guessesList = new ArrayList<JTextField>();

		for (int i = 0; i < len; i++) {
			JTextField textInput = new JTextField(2);
			this.add(textInput);
			this.guessesList.add(textInput);
			textInput.setEditable(false);
		}
		this.updateUI();
	}

	/**
	* Put Word in the panel
	*/
	public void revealWord(String word) {
		if (word.length() == guessesList.size()) {
			for (int i = 0; i < word.length(); i++) {
				guessesList.get(i).setText(word.substring(i, i + 1));
			}
		} else {
			newWord(word.length());
			revealWord(word);
		}
	}
}