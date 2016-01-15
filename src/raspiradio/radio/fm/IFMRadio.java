package raspiradio.radio.fm;

public interface IFMRadio {

	void startFMRadio(String frequency);
	boolean isRunning();
	void stopFMRadio();
	void fmSeekUp();
	void fmSeekDown();
	void changeFrequency(String frequency);
	String getRDSPI();
	String getRDSPS();
	String getFrequency();
	int getRSSI();
	int getSNR();
	String getRadioText();

	
}
