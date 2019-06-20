package UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Font;
import java.util.List;

/**
* Pictionary clock for client side display
*/
public class pictionaryClock extends Canvas{
	private int sec=0;
	private pictionaryClientClockService s;

    /**
    * Update clock
    */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Monospace", Font.PLAIN, 80)); 
      	g.drawString(String.format("%03d",sec),10,90);
		}

    /**
    * Update clock
    */
    public void repaint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Monospace", Font.PLAIN, 80)); 
      	g.drawString(String.format("%03d",sec),10,90);
		}

    /**
    * Class constructor
    */
	public pictionaryClock(){
		super();
		this.setBackground(Color.green);
		this.setPreferredSize(new Dimension(150,150));
	    (s = new pictionaryClientClockService()).execute();

		this.setVisible(true);

	}

    /**
    * Set the clock to a crtain amount of time
    */
	public void setClock(int n_Seconds){
		this.sec = n_Seconds;
		this.repaint(this.getGraphics());
    }

	
    /**
    * Clock itself, counts down once every second
    */
	private class pictionaryClientClockService extends SwingWorker<Void, Integer>{


        /**
        * Timing task which sleeps for one second, then updates
        */
        @Override
        protected Void doInBackground() {
            while (!isCancelled()) {
            	try{
            	Thread.sleep(1000);
            	}catch(InterruptedException e){;}
            	if(sec>0){sec--;}
            	publish(0);
            }
            return null;
 	}
 
        /**
        * Update after one second
        */
        @Override
        protected void process(List<Integer> nums) {
            repaint();
    }
 
				
}

}