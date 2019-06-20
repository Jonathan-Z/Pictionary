package UI;

import Protocol.datagramDRAW;

import javax.swing.*;
import javax.swing.event.*;
import java.io.ObjectOutputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Canvas;
/**
* Main drawing canvas
*/
public class pictionaryCanvas extends Canvas {
    private Boolean editable;
    private ObjectOutputStream out;
    private String usrname;

    /**
    * Reset canvas
    */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
    * enable or disable drawing
    */
    public void setEditable(Boolean b) {
        this.editable = b;
    }

    /**
    * draw a point
    */
    public void ptDwn(datagramDRAW dg) {
        ptDwn(dg.x, dg.y, dg.c);
    }

    /**
    * draw a point
    */
    public void ptDwn(int x, int y) {
        ptDwn(x, y, Color.black);
    }


    /**
    * draw a point
    */
    public void ptDwn(int x, int y, Color c) {
        if (this.editable) {
            this.getGraphics().setColor(c);
            this.getGraphics().fillOval(x, y, 10, 10);
            try {
                // System.out.println(out);
                out.writeObject(new datagramDRAW(usrname, x, y));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * draw a point but don't send it to server
    */
    public void ptDwnNoSend(datagramDRAW dg) {
        ptDwnNoSend(dg.x, dg.y, dg.c);
    }

    /**
    * draw a point but don't send it to server
    */
    public void ptDwnNoSend(int x, int y, Color c) {
        if (!this.editable) {
            this.getGraphics().setColor(c);
            this.getGraphics().fillOval(x, y, 10, 10);
        }
    }

    /**
    * reset the canvas
    */ 
    public void reset() {
        super.paint(this.getGraphics());
    }

    /**
    * class constructor
    */
    public pictionaryCanvas(ObjectOutputStream out, String usrname) {
        super();
        this.out = out;
        this.editable = true;
        this.setBackground(Color.white);

        //this.setBackground(Color.blue);
        this.setPreferredSize(new Dimension(600, 600));

        this.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                ptDwn(e.getX(), e.getY());
            }

            public void mouseMoved(MouseEvent e) {}
        });

        this.setVisible(true);

    }
}
