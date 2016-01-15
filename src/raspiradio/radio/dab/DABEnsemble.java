package raspiradio.radio.dab;

public class DABEnsemble {
	
	//Channel 27: ACQ: 1 RSSI: 63 SNR: -10 Name: BR Bayern   
	private String EnsembleName;
	private int channelID;
	private boolean ACQStatus;
	private int rssi;
	private int snr;
	private String ensembleID;
	
	
	public String getEnsembleID() {
		return ensembleID;
	}
	public void setEnsembleID(String ensembleID) {
		this.ensembleID = ensembleID;
	}
	public String getEnsembleName() {
		return EnsembleName;
	}
	public void setEnsembleName(String ensembleName) {
		EnsembleName = ensembleName;
	}
	public int getChannelID() {
		return channelID;
	}
	public void setChannelID(int channelID) {
		this.channelID = channelID;
	}
	public boolean isACQStatus() {
		return ACQStatus;
	}
	public void setACQStatus(boolean aCQStatus) {
		ACQStatus = aCQStatus;
	}
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
	public DABEnsemble(String ensembleName, int channelID, boolean aCQStatus,
			int rssi, int snr) {
		super();
		EnsembleName = ensembleName;
		this.channelID = channelID;
		ACQStatus = aCQStatus;
		this.rssi = rssi;
		this.snr = snr;
	}
	
	public DABEnsemble(){
		
	}

}
