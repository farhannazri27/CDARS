/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhInventoryTemp;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhRetrievalTemp;
import com.onsemi.cdars.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
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
public class HmsFtpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HmsFtpConfig.class);

//    @Scheduled(fixedRate = 60000)
//    hold for now
//    @Scheduled(cron = "0 5 * * * *") //every hour after 5 minute every day
    @Scheduled(cron = "0 */1 * * * *") //every 2 minutes
    public void DownloadCsv() {

        //utk inventory
        CSVReader csvReader = null;
        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

//            File file = new File("C:\\Users\\fg79cj\\Documents\\inventory\\hms_inventory.csv");
            String username = System.getProperty("user.name");
            File file = new File("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv");

            if (file.exists()) {
                csvReader = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv"), ',', '"', 1);

                //employeeDetails stores the values current line
                String[] inventory = null;
                //Create List for holding Employee objects
                List<WhInventoryTemp> empList = new ArrayList<WhInventoryTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhInventoryTemp emp = new WhInventoryTemp(inventory[0],
                            inventory[1], inventory[2],
                            inventory[3], inventory[4],
                            inventory[5], inventory[6],
                            inventory[7], inventory[8],
                            inventory[9], inventory[10],
                            inventory[11], inventory[12],
                            inventory[13], inventory[14],
                            inventory[15], inventory[16],
                            inventory[17], inventory[18],
                            inventory[19], inventory[20],
                            inventory[21], inventory[22]);
                    empList.add(emp);
                }

                //Lets print the Inventory List
                for (WhInventoryTemp e : empList) {

                    // check id exist or not
                    WhInventoryDAO checkidDao = new WhInventoryDAO();
                    int countinventoryid = checkidDao.getCountInventoryiId(e.getRequestId());
                    if (countinventoryid > 0) {

                        //check if exist, same data or not
                        WhInventoryDAO inDao = new WhInventoryDAO();
                        int countdata = inDao.getCountInventoryiIdAndLocation(e.getRequestId(), e.getInventoryRack(), e.getInventoryShelf());
                        if (countdata == 0) {
                            WhInventoryDAO countid = new WhInventoryDAO();
                            String id = countid.getId(e.getRequestId());
                            WhInventory updateftp = new WhInventory();

                            updateftp.setId(id);
                            updateftp.setRequestId(e.getRequestId());
                            updateftp.setInventoryDate(e.getInventoryDate());
                            updateftp.setInventoryBy(e.getInventoryBy());
//                            updateftp.setInventoryLocation(e.getInventoryLocation());
                            updateftp.setInventoryRack(e.getInventoryRack());
                            updateftp.setInventoryShelf(e.getInventoryShelf());
                            updateftp.setFlag("0");
                            WhInventoryDAO updateDao = new WhInventoryDAO();
                            QueryResult update = updateDao.updateWhInventoryLocation(updateftp);
                            if (update.getResult() == 1) {
                                LOGGER.info("update file");
                            } else {
                                LOGGER.info("failed to update");
                            }

                        }
                    } else {
                        WhInventoryDAO insertDao = new WhInventoryDAO();
                        WhInventory insertftp = new WhInventory();
                        insertftp.setRequestId(e.getRequestId());
                        insertftp.setMpNo(e.getMpNo());
                        insertftp.setMpExpiryDate(e.getMpExpiryDate());
                        insertftp.setEquipmentType(e.getEquipmentType());
                        insertftp.setEquipmentId(e.getEquipmentId());
                        insertftp.setPcbA(e.getPcbA());
                        insertftp.setPcbAQty(e.getPcbAQty());
                        insertftp.setPcbB(e.getPcbB());
                        insertftp.setPcbBQty(e.getPcbBQty());
                        insertftp.setPcbC(e.getPcbC());
                        insertftp.setPcbCQty(e.getPcbCQty());
                        insertftp.setPcbCtr(e.getPcbCtr());
                        insertftp.setPcbCtrQty(e.getPcbCtrQty());
                        insertftp.setQuantity(e.getQuantity());
                        insertftp.setRequestedBy(e.getRequestedBy());
                        insertftp.setRequestedDate(e.getRequestedDate());
                        insertftp.setRemarks(e.getRemarks());
                        insertftp.setVerifiedDate(e.getVerifiedDate());
                        insertftp.setInventoryDate(e.getInventoryDate());
                        insertftp.setInventoryBy(e.getInventoryBy());
                        insertftp.setInventoryRack(e.getInventoryRack());
                        insertftp.setInventoryShelf(e.getInventoryShelf());
                        insertftp.setStatus(e.getStatus());
                        insertftp.setFlag("0");
                        QueryResult add = insertDao.insertWhInventory(insertftp);
                        if (add.getResult() == 1) {
                            LOGGER.info("insert file");
                        } else {
                            LOGGER.info("failed to insert");
                        }

                    }

                }
            } else {
                LOGGER.info("csv file not found!");
            }

            //for retrieval from sg gadut
//            String username = System.getProperty("user.name");
            File fileRetrieval = new File("C:\\Users\\" + username + "\\Documents\\HMS\\hms_shipping.csv");
//            File fileRetrieval = new File("C:\\Users\\fg79cj\\Documents\\HMS\\hms_shipping.csv");

            if (fileRetrieval.exists()) {

                CSVReader csvReaderForRetrieval = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\HMS\\hms_shipping.csv"), ',', '"', 1);

                String[] retrieval = null;

                List<WhRetrievalTemp> empList = new ArrayList<WhRetrievalTemp>();

                while ((retrieval = csvReaderForRetrieval.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhRetrievalTemp emp = new WhRetrievalTemp(retrieval[0],
                            retrieval[1], retrieval[2],
                            retrieval[3], retrieval[4],
                            retrieval[5], retrieval[6]);
                    empList.add(emp);
                }

                for (WhRetrievalTemp e : empList) {

                    WhRetrievalDAO retriveDao = new WhRetrievalDAO();

                    int count = retriveDao.getCountRequestIdAndMpNo(e.getRequestId(), e.getMpNo());

                    if (count == 1) {

                        WhRetrievalDAO idDao = new WhRetrievalDAO();
                        String id = idDao.getId(e.getRequestId(), e.getMpNo());

                        WhRetrievalDAO check = new WhRetrievalDAO();
                        String checkstatus = check.getWhRetrieval(id).getStatus();

                        if (!"Ship".equals(checkstatus) && !"Closed".equals(checkstatus) && !"Barcode Verified".equals(checkstatus)) {
                            WhRetrieval update = new WhRetrieval();
                            update.setId(id);
                            update.setRequestId(e.getRequestId());
                            update.setMpNo(e.getMpNo());
                            update.setVerifiedBy(e.getVerifiedBy());
                            update.setVerifiedDate(e.getVerifiedDate());
                            update.setShippingBy(e.getShippingBy());
                            update.setShippingDate(e.getShippingDate());
                            update.setStatus(e.getStatus());
                            update.setFlag("0");

                            WhRetrievalDAO updateDao = new WhRetrievalDAO();
                            QueryResult updateRetrieval = updateDao.updateWhRetrievalFromCsv(update);
                            if (updateRetrieval.getResult() == 1) {
                                LOGGER.info("Update Done!");
                            } else {
                                LOGGER.info("Update Failed!");
                            }
                        }

                    } else {
                        LOGGER.info("No data found");
                    }
                }

            } else {
                LOGGER.info("retrieval csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

}
