package raspiradio.radio.dab;

import java.util.ArrayList;
import java.util.List;

import raspiradio.helper.Execute;
import raspiradio.helper.Execute.ExecResult;

public class DABRadio implements IDABRadio {

	public boolean isRunning = false;
	public static String defaulfreq = "106100";
	public List<DABStation> stationList = new ArrayList<DABStation>();
	public int indexPlayingStation;
        public DABStation tunedDABStation;

    public DABStation getTunedDABStation() {
        return tunedDABStation;
    }

	public int getIndexPlayingStation() {
		return indexPlayingStation;
	}

	public void setIndexPlayingStation(int indexPlayingStation) {
		this.indexPlayingStation = indexPlayingStation;
	}

	@Override
	public void startDABRadio(String channel) {

		if (!isRunning) {

			// DAB-Mode
			String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -a";
			ExecResult result;
			try {

				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec: " + result.getExitCode());
				List<String> lines = result.getLines();
				if (lines.size() > 0) {
					for (String line : lines) {
						System.out.println(line);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Tune to the Third service of the German Ensemble
			String cmd2 = "sudo /home/pi/dabpi_ctl/dabpi_ctl -f " + channel;
			ExecResult result2;
			try {
				result2 = Execute.execCmd(cmd2, 0);
				System.out.println("Result Exec: " + result2.getExitCode());
				List<String> lines2 = result2.getLines();
				if (lines2.size() > 0) {
					for (String line2 : lines2) {
						System.out.println(line2);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			isRunning = true;

		} else {
			System.out.println("DAB Radio is already running");

		}

	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;
	}

	@Override
	public void stopDABRadio() {
		String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -a";
			ExecResult result;
			try {

				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec: " + result.getExitCode());
				List<String> lines = result.getLines();
				if (lines.size() > 0) {
					for (String line : lines) {
						System.out.println(line);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               isRunning = false;
               System.out.println("DAB Radio stopped");


	}

	@Override
	public void dabSeekServiceUp()
	{
		int sizeOfList= stationList.size();
                System.out.println("UP Size Service List: "+sizeOfList);
                System.out.println("IPL: "+indexPlayingStation);

		int tuneToServiceInList=0;

		if(indexPlayingStation==sizeOfList-1){
			tuneToServiceInList=0;

		}else{
			tuneToServiceInList=indexPlayingStation+1;
		}
		selectStationWithIndex(stationList,tuneToServiceInList);

	}

	@Override
	public void dabSeekServiceDown()
	{
		int sizeOfList= stationList.size();
                System.out.println("DOWN Size Service List: "+sizeOfList);
                System.out.println("IPL: "+indexPlayingStation);
		int tuneToServiceInList=0;

		if(indexPlayingStation==0){
			tuneToServiceInList= stationList.size()-1;

		}else{
			tuneToServiceInList=indexPlayingStation-1;
		}
		selectStationWithIndex(stationList,tuneToServiceInList);

	}

	

	@Override
	public void selectStation(DABStation station) {
	
		//todo check tuned index and don't tune when you are already on the ensemble
		tuneToEnsemble(station.getEnsemble(),true);
		
		String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -f "+station.getServiceNumber();
		ExecResult result;
		
			try {
				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec Tune to Station: " + result.getExitCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tunedDABStation=station;
			System.out.println("Station: "+station.getServiceLabel() +" is tuned.");
	}

	@Override
	public void selectStationWithIndex(List<DABStation> dabStations, int indexOfPlayingStation) {
		
		//todo check tuned index and don't tune when you are already on the ensemble
		DABStation station=dabStations.get(indexOfPlayingStation);
		tuneToEnsemble(station.getEnsemble(),true);
		
		String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -f "+station.getServiceNumber();
		ExecResult result;
		
			try {
				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec Tune to Station: " + result.getExitCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tunedDABStation=station;
			System.out.println("Station: "+station.getServiceLabel() +" is tuned.");
                        setIndexPlayingStation(indexOfPlayingStation);
	}
	@Override
	public String getServiceLabel() {
		// TODO Auto-generated method stub
		return tunedDABStation.getServiceLabel();
	}

	@Override
	public List<DABStation> scanStationList() {
		// TODO Auto-generated method stub

		if (isRunning) {
			// Do a full Scan
                    stationList.clear();
			String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -k 1";
			ExecResult result;
			try {

				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec: " + result.getExitCode());
				List<String> lines = result.getLines();
				List<String> channelLines = new ArrayList<String>();
				List<DABEnsemble> ensembleList = new ArrayList<DABEnsemble>();
				List<DABStation> ensembleStationList = new ArrayList<DABStation>();
				
				if (lines.size() > 0) {
					for (String line : lines) {
						System.out.println(line);

						if (line.contains("Name:")) {
							// search for channels with reception
							channelLines.add(line);
						}

					}
					// get all ensembles
					for (String channelLine : channelLines) {
						DABEnsemble ensemble = parseEnsembleInfo(channelLine);
						// add Ensemble to List
						ensembleList.add(ensemble);
						// tune to ensemble get Service and make Objects

					}

				}
				System.out.println("Checking " + ensembleList.size()
						+ " Ensembles.");
				for (DABEnsemble ensemble : ensembleList) {
					
					System.out.println("Checking Ensemble: "
							+ ensemble.getEnsembleName());
					ensembleStationList = parseStationsInEnsemble(ensemble);
					for (DABStation station : ensembleStationList){
						stationList.add(station);
						
					}
				}
				System.out.println("--------------------------");

				System.out.println("Anzahl Stationen: "+stationList.size());

				for (DABStation stat: stationList){
					
					System.out.println(stat.getServiceLabel() +" SID: "+stat.getServiceID()+ " EID: "+stat.getEnsembleID());

				}
				System.out.println("--------------------------");

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Nothing to do DAB Radio is not Running");
                        

		}

		return stationList;
	}

	private List<DABStation> parseStationsInEnsemble(DABEnsemble ensemble) {
		// First Tune to Ensemble;
		List<DABStation> ensembleStationList = new ArrayList<DABStation>();

		if (tuneToEnsemble(ensemble, false)) {

			// get Service List
			String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -g";
			ExecResult result;
			try {
				result = Execute.execCmd(cmd, 0);
				System.out.println("Result Exec: " + result.getExitCode());
				List<String> lines = result.getLines();
				List<String> channelLines = new ArrayList<String>();
				// Look into each Line in Service list
				if (lines.size() > 0) {
					for (String line : lines) {
						if (line.contains("Service Name:")) {
							// search for lines with
							channelLines.add(line);
						}

					}
					for (String channelLine : channelLines) {
						System.out.println("Line: " + channelLine);
						DABStation station = parseServiceInfo(channelLine,ensemble);
						if (station != null) {
							ensembleStationList.add(station);
						}
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		;

		return ensembleStationList;
	}

	private boolean tuneToEnsemble(DABEnsemble ensemble, boolean fast) {
		String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -i "
				+ ensemble.getChannelID();
		ExecResult result;

		try {
			result = Execute.execCmd(cmd, 0);
			System.out.println("Result Exec tune to ensemble: "
					+ result.getExitCode());
			List<String> lines = result.getLines();
			if(!fast){
			Thread.sleep(2500);
			}
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tuning to ensemble did not work.");
			return false;
		}

	}

	private DABStation parseServiceInfo(String channelLine, DABEnsemble ensemble) {
		// TODO Auto-generated method stub
		DABStation stat = new DABStation();
		// System.out.println("Parsing Line: " + channelLine);
		int indexOfServiceID = channelLine.lastIndexOf("Service ID:");
		int indexOfServiceName = channelLine.lastIndexOf("Service Name:");
		int indexOfServiceNum = channelLine.lastIndexOf("Num:");
		int indexOfServiceComp = channelLine.lastIndexOf("Component ID:");
		System.out.println("Index SID: " + indexOfServiceID + " Index Label: "
				+ indexOfServiceName + " Index Num: " + indexOfServiceNum
				+ " Index Comp: " + indexOfServiceComp);

		// ServiceNumber
		String serviceNumber = channelLine.substring(5, 7);
		serviceNumber = serviceNumber.replaceAll(":", "");
		serviceNumber = serviceNumber.trim().replaceAll("\n ", "");
		System.out.println("ServiceNumber: " + serviceNumber);

		// ServiceID
		String serviceID = channelLine.substring(
				channelLine.lastIndexOf("Service ID:") + 13,
				channelLine.lastIndexOf("Service Name:"));
		serviceID = serviceID.trim().replaceAll("\n ", "");
		if (serviceID.length() > 4) {
			return null;
		}
		System.out.println("ServiceID: " + serviceID);

		// Service Name
		String serviceName = channelLine.substring(
				channelLine.lastIndexOf("Service Name:") + 14,
				channelLine.lastIndexOf("Component ID:"));
		serviceName = replaceAtTheEnd(serviceName);

		System.out.println("ServiceName: " + serviceName);

		stat.setServiceNumber(Integer.parseInt(serviceNumber));
		stat.setServiceID(serviceID);
		stat.setServiceLabel(serviceName);
		stat.setEnsemble(ensemble);
		stat.setEnsembleID(ensemble.getEnsembleID());

		return stat;
	}

	public static String replaceAtTheEnd(String input) {
		input = input.replaceAll("\\s+$", "");
		return input;
	}

	private DABEnsemble parseEnsembleInfo(String channelLine) {
		DABEnsemble ens = new DABEnsemble();

		// channelID
		System.out.println("ChannelLine: " + channelLine);
		String channelID = channelLine.substring(8, 10);
		channelID = channelID.replaceAll(":", "");
		System.out.println("channelID: " + channelID);

		// ensName
		String ensembleName = channelLine.substring(
				channelLine.lastIndexOf("Name:") + 6,
				channelLine.lastIndexOf("Ensemble ID:"));
		System.out.println("ensembleName: " + ensembleName);

		// rssi
		String rssi = channelLine
				.substring(channelLine.lastIndexOf("RSSI: ") + 6);
		if (rssi.length() > 1)
			rssi = rssi.substring(0, 2);
		System.out.println("rssi:" + rssi);

		// ensID
		String ensembleID = channelLine.substring(channelLine
				.lastIndexOf("Ensemble ID:") + 13);
		System.out.println("ensembleID: " + ensembleID);
		int ensId = Integer.parseInt(ensembleID);
		String hexEnsId = Integer.toHexString(ensId);
		System.out.println("ensembleID in Hex: " + hexEnsId);

		ens.setEnsembleName(ensembleName);
		ens.setChannelID(Integer.parseInt(channelID));
		ens.setRssi(Integer.parseInt(rssi));
		ens.setSnr(10);
		ens.setACQStatus(true);
		ens.setEnsembleID(hexEnsId);

		return ens;
	}

    @Override
    public DABInfo getDABInfo() {
        DABInfo info= new DABInfo();
        
        String cmd = "sudo /home/pi/dabpi_ctl/dabpi_ctl -e";
		ExecResult result;
		try {
			result = Execute.execCmd(cmd, 0);
			System.out.println("Result Exec DAB Info: "
					+ result.getExitCode());
			List<String> lines = result.getLines();
                                          
                        if (lines.size() > 6) {
                            String lineRSSI= lines.get(4);
                            String lineSNR= lines.get(5);
                            String lineFIC= lines.get(6);
                            lineRSSI=lineRSSI.substring(6);
                            lineSNR=lineSNR.substring(5);
                            lineFIC=lineFIC.substring(13);

                            for (String line : lines) {
				System.out.println(line);

				}
                           System.out.println(lineRSSI);
                           System.out.println(lineSNR);
                           System.out.println(lineFIC);
                           
                           
                           info.setSnr(Integer.parseInt(lineSNR));
                           info.setRssi(Integer.parseInt(lineRSSI));
                           info.setFic_quality(Integer.parseInt(lineFIC));



                           
                        }
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        
        return info;
    }

}
