/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiradio;

import raspiradio.controller.RadioManager;
import raspiradio.gui.GuiJFrame;
import raspiradio.radio.dab.DABRadio;
import raspiradio.radio.fm.FMRadio;

/**
 *
 * @author christianwinter
 */
public class RaspiRadio {
        public FMRadio fmr;
	public DABRadio dabr;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello World");
       // RadioManager mgr= new RadioManager();
        //mgr.createRadios();
        //mgr.startDABMode();
        //GuiJFrame myFrame = new GuiJFrame();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiJFrame().setVisible(true);
            }
        });
    }
    
}
