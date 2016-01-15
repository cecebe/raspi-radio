package raspiradio.radio.dab;

public class DABStation {
	
	private DABEnsemble ensemble;
	private String serviceLabel;
	private String serviceID;
	private int serviceNumber;
	private String ensembleID;
	
	public String getEnsembleID() {
		return ensembleID;
	}
	public void setEnsembleID(String ensembleID) {
		this.ensembleID = ensembleID;
	}
	public DABEnsemble getEnsemble() {
		return ensemble;
	}
	public void setEnsemble(DABEnsemble ensemble) {
		this.ensemble = ensemble;
	}
	public String getServiceLabel() {
		return serviceLabel;
	}
	public void setServiceLabel(String serviceLabel) {
		this.serviceLabel = serviceLabel;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public int getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(int serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	
	

}
