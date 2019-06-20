package UI;
import UI.*;
import GameLogic.*;
import Protocol.datagramPLAYERMSG;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.net.*;
import java.io.*;

/**
* Game client UI iteself
*/
public class pictionaryGameClientUI extends JFrame {
	public JTextArea omnichat;
	public JTextArea omnichatInput;

	public guessPanel gp;
	public pictionaryCanvas cv;
	public pictionaryToolbar tb;
	public activeUserPanel ap;
	public pictionaryClock cl;

	public String usrname;
	public Socket s;
	public ObjectOutputStream out;
	public ObjectInputStream in;



	/**
	* Class constructor
	*/ 
	public pictionaryGameClientUI(String hostIP, int port , String usrname) throws Exception {
		super("Pictionary@" + usrname);
		this.usrname = usrname;

		try{
		this.s = new Socket(hostIP, port);
		this.out = new ObjectOutputStream(s.getOutputStream());
		} catch(Exception e){
			e.printStackTrace();
		}
		out.writeObject(new datagramPLAYERMSG(usrname, ""));

		System.out.println(s);



		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {;}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(1000, 800);
		this.setResizable(false);
		this.setVisible(true);

		omnichat = new JTextArea();

//		this.messenger = new Communicator();

		omnichatInput = new JTextArea();

		cv = new pictionaryCanvas(out, usrname);
		gp = new guessPanel();
		tb = new pictionaryToolbar(cv);
		ap = new activeUserPanel(usrname);
		cl = new pictionaryClock();

		JScrollPane omnichatSP = new JScrollPane(omnichat);
		omnichat.setEditable(false);

		JScrollPane omnichatInputSP = new JScrollPane(omnichatInput);
		// Key listener for the chat
		omnichatInput.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER ) {
					String output = omnichatInput.getText();
					output = output.substring(0, output.length() - 1);
					try {
						// System.out.println(out);
						if (output.length() > 0) {
							out.writeObject(new datagramPLAYERMSG(usrname, output));

						}
					} catch (Exception z) {
						z.printStackTrace();
					}
					omnichatInput.setText("");
				}

			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {

			}
		});


		this.setLayout(null);
		this.add(gp);
		this.add(cv);
		this.add(tb);
		this.add(ap);
		this.add(omnichatSP);
		this.add(omnichatInputSP);
		this.add(cl);

		gp.setBounds(0, 0, 600, 50);
		cv.setBounds(0, 50, 600, 600);
		cl.setBounds(0, 650, 150, 150);
		tb.setBounds(150, 650, 450, 150);

		omnichatSP.setBounds(600, 0, 300, 600);
		omnichatInputSP.setBounds(600, 600, 300, 200);
		ap.setBounds(900, 0, 100, 800);

		pictionaryClient pc = new pictionaryClient(this.s, this);
		pc.execute();
		this.setVisible(true);
	}
}


