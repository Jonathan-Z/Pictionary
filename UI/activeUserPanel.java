package UI;

import GameLogic.pictionaryPlayer;
import Protocol.datagramSTARTROUND;
import Protocol.datagramENDGAME;

import javax.swing.*;
import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class activeUserPanel extends Canvas {
	private String usrname;
	private List<pictionaryPlayer> players = new ArrayList<pictionaryPlayer>();
	private String drawer = "";
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("SansSerif", Font.PLAIN, 12));

		int offset = 10;

		for (pictionaryPlayer player : players) {
			System.out.println(player.name);
			System.out.println(drawer);


			if (drawer.equals(player.name)) {
				g.setColor(Color.YELLOW);
			}
			g.fillOval(10, offset, 70, 70);
			g.setColor(Color.BLACK);
			g.drawString(player.name, 10, offset + 85);
			g.drawString(String.format("Score:%d", player.score), 10, offset + 100);
			offset += 120;
		}
	}


	public activeUserPanel(String usrname) {
		super();
		this.usrname = usrname;
		// this.setBackground(Color.green);
		this.setPreferredSize(new Dimension(100, 800));
		this.setVisible(true);
	}

	public void setUsers(datagramENDGAME dg) {
		this.players = dg.players;
		this.drawer = "";
		this.paint(this.getGraphics());
	}


	public void setUsers(datagramSTARTROUND dg) {
		this.players = dg.players;
		this.drawer = dg.drawer;
		this.paint(this.getGraphics());
	}
}