/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiradio.controller;

import java.util.List;
import raspiradio.radio.dab.DABRadio;
import raspiradio.radio.dab.DABStation;
import raspiradio.radio.fm.FMRadio;

/**
 *
 * @author christianwinter
 */
public class RadioManager {
         public FMRadio fm;
	public DABRadio dab;
        public List<DABStation> dabList;
        
        public void createRadios(){
       
            fm= new FMRadio();
                       System.out.println("FM Radio created successfully");

            dab= new DABRadio();
           System.out.println("DAB Radio created successfully");
            
        }
        public void startFMMode(){
        fm.startFMRadio("107900");
        System.out.println("FM Radio started");

        }
        public void startDABMode(){
        dab.startDABRadio("3");
        //dabList= dab.scanStationList();
         System.out.println("DAB Radio started");
       // printStationList();
        }
        
    
        public void stopDABMode(){
        dab.stopDABRadio();
        }
        
        public void printStationList(){
            dabList=dab.stationList;
        if (dab.stationList.size()>0){
            DABStation stat= dabList.get(0);
            	dab.selectStation(stat);
        int i=0;
            	for (DABStation station : dabList){
            		System.out.println(i+ " Name: "+station.getServiceLabel()+" SID: "+station.getServiceID()+" Ens: "+station.getEnsemble().getEnsembleName());
         
            		i=i+1;
            	}
        }else{
        System.out.println("No Station Liste available");
        }
        }
        
        public void startScanTuneDAB(){
                    dab.startDABRadio("3");

             //Start
             List<DABStation> sList = dab.scanStationList();
            if(sList.size()>0){
            	DABStation stat= sList.get(0);
            	dab.selectStation(stat);
            	
            	int i=0;
            	for (DABStation station : sList){
            		System.out.println(i+ " Name: "+station.getServiceLabel()+" SID: "+station.getServiceID()+" Ens: "+station.getEnsemble().getEnsembleName());
         
            		i=i+1;
            	}
            	
            	}

        }
}
