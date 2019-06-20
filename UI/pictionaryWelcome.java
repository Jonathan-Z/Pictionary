package UI;

import UI.*;

import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
* Pictionary Welcome Window
*/
public class pictionaryWelcome extends JFrame {
	private JTextField usrNameField; // Username Field
	private JTextField hostIpField; // Host server IP Field
	private JTextField portField; // Host server Port Field

	private JButton openGameConfig; // G
	private JButton createGame;

	/**
	* Class constructor
	*/
	public pictionaryWelcome() throws Exception {
		super("Welcome to Pictionary!");
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {;}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(400, 120);
		this.setResizable(false);
		// Username
		this.usrNameField = new JTextField(20);
		this.hostIpField = new JTextField(20);
		this.portField = new JTextField(20);

		this.createGame  = new JButton("Join Game!");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 1;
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 0;
		this.add(new JLabel(" Username: "), c);

		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 0;
		this.add(usrNameField, c);

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 1;
		this.add(new JLabel(" Host IP: "), c);


		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 1;
		this.add(hostIpField, c);

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 2;
		this.add(new JLabel(" Host Port: "), c);


		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 2;
		this.add(portField, c);


		c.gridx = 1;
		c.gridy = 3;
		this.add(createGame, c);

		/**
		* Acts on button press of create game button
		*/
		createGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String hostname = "";

				try {
					hostname = InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException u) {;}

				String usrname = usrNameField.getText().equals("") ? hostname : usrNameField.getText();
				try {
					Integer.parseInt(portField.getText());
					pictionaryGameClientUI a = new pictionaryGameClientUI(hostIpField.getText(), Integer.parseInt(portField.getText()), usrname);
				}

				catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Invalid Port!");
					return;
				}

				setVisible(false);

			}

		});

		this.setVisible(true);
	}
}