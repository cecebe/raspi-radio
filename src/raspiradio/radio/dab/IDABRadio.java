package raspiradio.radio.dab;

import java.util.List;

public interface IDABRadio {
	
	void startDABRadio(String channel);
	boolean isRunning();
	void stopDABRadio();
	void dabSeekServiceUp();
	void dabSeekServiceDown();
	DABInfo getDABInfo();
	
	void selectStation(DABStation station);
	void selectStationWithIndex(List<DABStation> dabStations, int indexOfPlayingStation);
	String getServiceLabel();
	List<DABStation> scanStationList();


}
