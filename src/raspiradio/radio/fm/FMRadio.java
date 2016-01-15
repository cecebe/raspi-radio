package raspiradio.radio.fm;

import java.util.List;

import raspiradio.helper.Execute;
import raspiradio.helper.Execute.ExecResult;

public class FMRadio implements IFMRadio{
	
	public boolean isRunning=false;
	public static String defaulfreq="106100";

	@Override
	public void startFMRadio(String frequency) {
		if(!isRunning){
		if(frequency==null){
			defaulfreq="106100";
		}
		//ToDo
		String cmd="sudo /home/pi/dabpi_ctl/dabpi_ctl -b";
		ExecResult result;
		try {

			result = Execute.execCmd(cmd,0);
			System.out.println("Result Exec: "+result.getExitCode());
		     List<String> lines = result.getLines();
		   if (lines.size()>0){
		     for (String line:lines) {
		         System.out.println(line);
		     }
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//ToDo
		String cmd2="sudo /home/pi/dabpi_ctl/dabpi_ctl -c "+frequency;
		ExecResult result2;
		try {
			result2 = Execute.execCmd(cmd2,0);
			System.out.println("Result Exec: "+result2.getExitCode());
		     List<String> lines2 = result2.getLines();
		   if (lines2.size()>0){
		     for (String line2:lines2) {
		         System.out.println(line2);
		     }
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		isRunning=true;

		}else{
	         System.out.println("FM Radio is already running");

		}
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return isRunning;
	}

	@Override
	public void stopFMRadio() {
		// TODO Auto-generated method stub
		if(isRunning){
			
			
			String stopCmd="sudo /home/pi/dabpi_ctl/dabpi_ctl -b";
			

				try {
					ExecResult result;

					result = Execute.execCmd(stopCmd,0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isRunning=false;

		}else{
			
	         System.out.println("Nothing to stop FM Radio is not Running");

		}
		
	}

	@Override
	public void fmSeekUp() {
		if(isRunning){
			String seekUpCmd="sudo /home/pi/dabpi_ctl/dabpi_ctl -l up";
			try {
				ExecResult result;

				result = Execute.execCmd(seekUpCmd,0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't seekUp because FM Radio is not running.");
		}
		
	}

	@Override
	public void fmSeekDown() {
		if(isRunning){
			String seekDownCmd="sudo /home/pi/dabpi_ctl/dabpi_ctl -l down";
			try {
				ExecResult result;

				result = Execute.execCmd(seekDownCmd,0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't seekUp because FM Radio is not running.");
		}
		
	}

	@Override
	public void changeFrequency(String frequency) {
		if(isRunning){
			//todo check if frequency has a valid format
			String gotoFreqCmd="sudo /home/pi/dabpi_ctl/dabpi_ctl -c"+frequency;
			try {
				ExecResult result;

				result = Execute.execCmd(gotoFreqCmd,0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't tune to frequency because FM Radio is not running.");
		}
		
	}

	@Override
	public String getRDSPI() {
		String PI ="FFFF";
		if(isRunning){
			String getRDSInfo="sudo /home/pi/dabpi_ctl/dabpi_ctl -m";
			try {
				ExecResult result;

				result = Execute.execCmd(getRDSInfo,0);
				System.out.println("Result Exec: "+result.getExitCode());
				   List<String> lines = result.getLines();
				   if (lines.size()>3){
					   
					   //TODOcheck RDSSyncState to 1
					  PI =lines.get(3);
					  PI=getPIFromRDSInfo(PI);
				     for (String line:lines) {
				         System.out.println("RDSINFO: "+line);
				     }
				    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't seekUp because FM Radio is not running.");
		}
		return PI;
	}

	@Override
	public String getRDSPS() {
		
		String rdsInfo="";
		if(isRunning){
			String getRDSInfo="sudo /home/pi/dabpi_ctl/dabpi_ctl -m";
			try {
				ExecResult result;

				result = Execute.execCmd(getRDSInfo,0);
				System.out.println("Result Exec: "+result.getExitCode());
			     List<String> lines = result.getLines();
			   if (lines.size()>3){
				  rdsInfo =lines.get(3);
				 rdsInfo=getPSFromRDSInfo(rdsInfo);
			     //for (String line:lines) {
			      //   System.out.println("RDSINFO: "+line);
			    // }
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't get RDSPI because FM Radio is not running.");
		}
		return rdsInfo;
	}
	@Override
	public String getFrequency() {
		
		String frequency="";
		if(isRunning){
			String getFMInfo="sudo /home/pi/dabpi_ctl/dabpi_ctl -d";
			try {
				ExecResult result;

				result = Execute.execCmd(getFMInfo,0);
				System.out.println("Result Exec: "+result.getExitCode());
			     List<String> lines = result.getLines();
			   if (lines.size()>6){
				  frequency =lines.get(5);
				 frequency=getFreqFromFMInfo(frequency);
			     //for (String line:lines) {
			      //   System.out.println("FMInfo: "+line);
			    // }
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't get Frequency because FM Radio is not running.");
		}
		return frequency;
	}
	
	@Override
	public int getRSSI() {
		String rssi_text="";
		int RSSI=0;
		if(isRunning){
			String getFMInfo="sudo /home/pi/dabpi_ctl/dabpi_ctl -d";
			try {
				ExecResult result;

				result = Execute.execCmd(getFMInfo,0);
				System.out.println("Result Exec: "+result.getExitCode());
			     List<String> lines = result.getLines();
			   if (lines.size()>6){
				   rssi_text =lines.get(4);
				  System.out.println(lines.get(4));
				  RSSI=getRSSIFromFMInfo(rssi_text);
				  		 
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
	         System.out.println("Can't get RSSI because FM Radio is not running.");
		}
		return RSSI;
	}

	@Override
	public int getSNR() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRadioText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String getPIFromRDSInfo(String RDSInfo){
		String PI="FFFF";
		
		if (RDSInfo!=null){
			if(RDSInfo.length()>9){
				RDSInfo = RDSInfo.substring(4, 9);
				//System.out.println("RDSInfo Substring: "+RDSInfo);
				RDSInfo = RDSInfo.replaceAll("\\s","");

				int piInt = Integer.parseInt(RDSInfo);
				PI=Integer.toHexString(piInt);
				System.out.println("PI: "+PI);

			}
		}
	return PI;
	}
	
	private String getPSFromRDSInfo(String RDSInfo){
		String PS="";
		
		if (RDSInfo!=null){
			if(RDSInfo.length()>16){
				
			    PS=RDSInfo.substring(RDSInfo.lastIndexOf("Name:") + 5);
				System.out.println("THE PS:"+PS);

			}
		}
	return PS;
	}

	

	private String getFreqFromFMInfo(String frequency) {
		// TODO Auto-generated method stub
		String freq="";
		
		if (frequency!=null){
			System.out.println(frequency);
			if(frequency.length()>16){			
				  frequency=frequency.substring(frequency.lastIndexOf("Frequency:") +11);
				freq=frequency.substring(0, frequency.length()-3);

			}
		}
	return freq;
	}
	
	private int getRSSIFromFMInfo(String fminfo) {
		// TODO Auto-generated method stub
		int rssi=0;
		
	
		
		if (fminfo!=null){
			System.out.println(fminfo);
			if(fminfo.length()>8){
				fminfo=fminfo.substring(6);
				String rssi_string= fminfo.substring(0, fminfo.length()-5);
				rssi = Integer.parseInt(rssi_string);
			}
		}	
	return rssi;
	}


}
	
