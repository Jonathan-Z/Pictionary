package UI;

import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.Dimension;
/**
* Toolbar at the top of the game with button for erasing the board
*/
public class pictionaryToolbar extends JPanel {
	JButton erase;
	pictionaryCanvas cv;
	/**
	* Class constructor
	* @param cv reference to the canvas so that it can be cleared
	*/
	public pictionaryToolbar(pictionaryCanvas cv) {
		super();
		this.cv = cv;
		this.setBackground(Color.cyan);
		this.setPreferredSize(new Dimension(150, 450));

		JButton erase = new JButton("Erase");


		erase.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cv.reset();
			}

		});
		this.add(erase);

		this.setVisible(true);

	}
}