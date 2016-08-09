/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.HardwareRequestDAO;
import com.onsemi.cdars.dao.IonicFtpDAO;
import com.onsemi.cdars.dao.IonicQueueDAO;
import com.onsemi.cdars.dao.PcbFtpDAO;
import com.onsemi.cdars.model.HardwareRequest;
import com.onsemi.cdars.model.HardwareRequestTemp;
import com.onsemi.cdars.model.IonicFtp;
import com.onsemi.cdars.model.IonicFtpTemp;
import com.onsemi.cdars.model.IonicQueue;
import com.onsemi.cdars.model.PcbFtp;
import com.onsemi.cdars.model.PcbFtpTemp;
import com.onsemi.cdars.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author fg79cj
 */
@Configuration
@EnableScheduling
public class FtpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpConfig.class);

//    @Scheduled(fixedRate = 60000)
    
//    hold for now
//    @Scheduled(cron = "0 5 * * * *") //every hour after 5 minute every day
    public void DownloadCsv() {
        
        //utk ionic
        CSVReader csvReader = null;
        LOGGER.info("testjerkkkkk" + new Date());

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            Date initDate1 = new Date();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
            String parsedDate1 = formatter1.format(initDate1);
            System.out.println(parsedDate1 + " tarikh csv!!!!...");
            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-" + parsedDate1 + ".csv"), ',', '"', 1);

//            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-20160505.csv"), ',', '"', 1);

            //employeeDetails stores the values current line
            String[] ionicFtp = null;
            //Create List for holding Employee objects
            List<IonicFtpTemp> empList = new ArrayList<IonicFtpTemp>();

            while ((ionicFtp = csvReader.readNext()) != null) {
                //Save the employee details in Employee object
                IonicFtpTemp emp = new IonicFtpTemp(ionicFtp[0],
                        ionicFtp[1], ionicFtp[2],
                        ionicFtp[3], ionicFtp[4],
                        ionicFtp[5], ionicFtp[6],
                        ionicFtp[7], ionicFtp[8]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (IonicFtpTemp e : empList) {

                Date initDate = new SimpleDateFormat("MM/dd/yy H:mm").parse(e.getDateOff());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                IonicFtp ftp = new IonicFtp();
                ftp.setEventCode(e.getEventCode());
                ftp.setRms(e.getRms());
                ftp.setIntervals(e.getIntervals());
                ftp.setCurrentStatus(e.getCurrentStatus());
                ftp.setDateOff(parsedDate);
                ftp.setEquipId(e.getEquipId());
                ftp.setLcode(e.getLcode());
                ftp.setHardwareFinal(e.getHardwareFinal());
                ftp.setSupportItem(e.getSupportItem());

                IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);

                if (count == 0) {
                    ionicFtpDAO = new IonicFtpDAO();
//                    IonicFtpDAO ionicFtpDAO1 = new IonicFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);

                    if (!queryResult1.getGeneratedKey().equals("0")) {

                        IonicQueue i = new IonicQueue();
                        i.setIonicFtpId(queryResult1.getGeneratedKey());
                        i.setClassification("1st Hardware");
                        i.setSource("Production");
                        i.setEventNameCode(e.getEventCode());
                        i.setRms(e.getRms());
                        i.setIntervals(e.getIntervals());
                        i.setCurrentStatus(e.getCurrentStatus());
                        i.setDateOff(parsedDate);
                        i.setEquipmentId(e.getEquipId());
                        i.setLcode(e.getLcode());
                        i.setHardwareFinal(e.getHardwareFinal());
                        i.setFinalSupportItem(e.getSupportItem());
                        i.setStatus("Waiting for Verification");
                        i.setCreatedBy("0");
                        IonicQueueDAO idao = new IonicQueueDAO();
                        QueryResult qresult = idao.insertIonicQueue(i);
                    } else {

                        System.out.println("Fail to insert data!!!!");
                    }
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        
        //utk pcb

        CSVReader csvReaderPcb = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            csvReaderPcb = new CSVReader(new FileReader("C:\\FOL_PCB_FTP-20160503.csv"), ',', '"', 2);
            
            //employeeDetails stores the values current line
            String[] pcbFtp = null;
            //Create List for holding Employee objects
            List<PcbFtpTemp> empList = new ArrayList<PcbFtpTemp>();

            while ((pcbFtp = csvReaderPcb.readNext()) != null) {
                //Save the employee details in Employee object
                PcbFtpTemp emp = new PcbFtpTemp(pcbFtp[0],
                        pcbFtp[1], pcbFtp[2],
                        pcbFtp[3], pcbFtp[4]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (PcbFtpTemp e : empList) {

                Date initDate = new SimpleDateFormat("dd-MMM-yyyy h:mm").parse(e.getDateOff());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                PcbFtp ftp = new PcbFtp();
                ftp.setDateOff(parsedDate);
                ftp.setRms(e.getRms());
                ftp.setProcess(e.getProcess());
                ftp.setStatus(e.getStatus());
                ftp.setSupportItem(e.getSupportItem());

                PcbFtpDAO ionicFtpDAO = new PcbFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);
                if (count == 0) {
                    ionicFtpDAO = new PcbFtpDAO();
//                    PcbFtpDAO ionicFtpDAO1 = new PcbFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertPcbFtp(ftp);

                    if (!queryResult1.getGeneratedKey().equals("0")) {

                        IonicQueue i = new IonicQueue();
                        i.setPcbFtpId(queryResult1.getGeneratedKey());
                        i.setClassification("1st Hardware");
                        i.setSource("Production");
                        i.setEventNameCode("N/A");
                        i.setRms(e.getRms());
                        i.setIntervals("N/A");
                        i.setCurrentStatus(e.getStatus());
                        i.setDateOff(parsedDate);
                        i.setEquipmentId("N/A");
                        i.setLcode("N/A");
                        i.setHardwareFinal(e.getSupportItem());
                        i.setFinalSupportItem("PCB");
                        i.setStatus("Waiting for Verification");
                        i.setCreatedBy("0");
                        IonicQueueDAO idao = new IonicQueueDAO();
                        QueryResult qresult = idao.insertIonicQueue(i);
                    } else {

                        System.out.println("Fail to insert data!!!!");
                    }
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        
        //utk hardware request
        
         CSVReader RequestcsvReader = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            RequestcsvReader = new CSVReader(new FileReader("C:\\Hardware_Request_FTP-20160503.csv"), ',', '"', 1);
            //employeeDetails stores the values current line
            String[] requestFtp = null;
            //Create List for holding Employee objects
            List<HardwareRequestTemp> empList = new ArrayList<HardwareRequestTemp>();

            while ((requestFtp = RequestcsvReader.readNext()) != null) {
                //Save the employee details in Employee object
                HardwareRequestTemp emp = new HardwareRequestTemp(requestFtp[0],
                        requestFtp[1], requestFtp[2],
                        requestFtp[3]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (HardwareRequestTemp e : empList) {

                 Date initDate = new SimpleDateFormat("dd-MMM-yyyy h:mm").parse(e.getForecastReadoutStart());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                HardwareRequest ftp = new HardwareRequest();
                ftp.setForecastReadoutStart(parsedDate);
                ftp.setEventCode(e.getEventCode());
                ftp.setRms(e.getRms());
                ftp.setProcess(e.getProcess());
                ftp.setStatus("Pending");

                HardwareRequestDAO hardwareFtpDAO = new HardwareRequestDAO();
                int count = hardwareFtpDAO.getCountExistingData(ftp);
                if (count == 0) {
                    hardwareFtpDAO = new HardwareRequestDAO();
//                    HardwareRequestDAO  hardwareFtpDAO1 = new HardwareRequestDAO();
                    QueryResult queryResult1 = hardwareFtpDAO.insertHardwareRequest(ftp);
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

//    @Scheduled(fixedRate = 1000)
//    public void doTask() {
//        System.out.println("Do Task...");
//    }
}
