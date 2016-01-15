/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiradio.radio.dab;

/**
 *
 * @author christianwinter
 */
public class DABInfo {
    	private int rssi;
	private int snr;
	private int fic_quality;

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getSnr() {
        return snr;
    }

    public void setSnr(int snr) {
        this.snr = snr;
    }

    public int getFic_quality() {
        return fic_quality;
    }

    public void setFic_quality(int fic_quality) {
        this.fic_quality = fic_quality;
    }

}