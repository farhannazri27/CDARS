/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.dao.CardPairingDAO;
import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhMpListDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.CardPairing;
import com.onsemi.cdars.model.CardPairingTemp;
import com.onsemi.cdars.model.WhBibCardCsvTemp;
import com.onsemi.cdars.model.WhEqptCsvTemp;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhInventoryTemp;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhRetrievalTemp;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
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

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "id,eqpt_type,eqpt_id,quantity,mp_number,mp_expiry_date,requested_by,"
            + "requestor_email,rack,shelf";
    private static final String HEADER_CARD = "id,eqpt_type,eqpt_id,load_card,load_card_qty,program_card,program_card_qty,"
            + "total_quantity,mp_number,mp_expiry_date,requested_by,"
            + "requestor_email,rack,shelf";

//    @Scheduled(fixedRate = 60000)
//    hold for now
    @Scheduled(cron = "0 */2 * * * *") //every 2 minutes
    public void DownloadCsv() throws IOException {

        //utk inventory
        CSVReader csvReader = null;
        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }
//            LOGGER.info("username!!!" + username);
            File file = new File("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv");

            if (file.exists()) {
                csvReader = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\HMS\\hms_inventory.csv"), ',', '"', 1);

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
                            inventory[21], inventory[22],
                            inventory[23]);
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
                            updateftp.setInventoryRack(e.getInventoryRack());
                            updateftp.setInventoryShelf(e.getInventoryShelf());
                            updateftp.setFlag("0");
                            WhInventoryDAO updateDao = new WhInventoryDAO();
                            QueryResult update = updateDao.updateWhInventoryLocation(updateftp);
                            if (update.getResult() == 1) {

                                //update statusLog
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(e.getRequestId());
                                stat.setModule("cdars_wh_inventory");
                                stat.setStatus("SF Inventory Updated");
                                stat.setCreatedBy("-");
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[HmsFtpConfig] - insert status log failed");
                                } else {
                                    LOGGER.info("[HmsFtpConfig] - insert status log done");
                                }
                                LOGGER.info("update file");

                                //update spts location
                                //get item pkid and version
                                WhRequestDAO reqD = new WhRequestDAO();
                                WhRequest reqSf = reqD.getWhRequest(e.getRequestId());
                                if ("PCB".equals(e.getEquipmentType())) {
                                    if (!"0".equals(e.getPcbAQty())) {
                                        System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                        JSONObject paramsQualA = new JSONObject();
//                                        String itemID = e.getPcbA();
//                                        paramsQualA.put("itemID", itemID);
                                        paramsQualA.put("pkID", reqSf.getSfpkid());
                                        JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                        int itemApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                        String versionA = getItemByParamA.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemApkid............." + itemApkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemApkid);
                                        params3.put("version", versionA);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB A");
                                        } else {
                                            LOGGER.info("failed to update spts PCB A");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbBQty())) {
                                        System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                        JSONObject paramsQualB = new JSONObject();
//                                        String itemID = e.getPcbB();
//                                        paramsQualB.put("itemID", itemID);
                                        paramsQualB.put("pkID", reqSf.getSfpkidB());
                                        JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                                        int itemBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                                        String versionB = getItemByParamB.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemBpkid............." + itemBpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemBpkid);
                                        params3.put("version", versionB);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB B");
                                        } else {
                                            LOGGER.info("failed to update spts PCB B");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbCQty())) {
                                        System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                        JSONObject paramsQualC = new JSONObject();
//                                        String itemID = e.getPcbC();
//                                        paramsQualC.put("itemID", itemID);
                                        paramsQualC.put("pkID", reqSf.getSfpkidC());
                                        JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                                        int itemCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                                        String versionC = getItemByParamC.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemCpkid............." + itemCpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemCpkid);
                                        params3.put("version", versionC);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB C");
                                        } else {
                                            LOGGER.info("failed to update spts PCB C");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbCtrQty())) {
                                        System.out.println("GET SFITEM PCB CONTROL BY PARAM...");
                                        JSONObject paramsQualCtr = new JSONObject();
//                                        String itemID = e.getPcbCtr();
//                                        paramsQualCtr.put("itemID", itemID);
                                        paramsQualCtr.put("pkID", reqSf.getSfpkidCtr());
                                        JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                                        int itemCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                                        String versionCtr = getItemByParamCtr.getJSONObject(0).getString("Version");
                                        LOGGER.info("itemCtrpkid............." + itemCtrpkid);

                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itemCtrpkid);
                                        params3.put("version", versionCtr);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts PCB Ctr");
                                        } else {
                                            LOGGER.info("failed to update spts PCB Ctr");
                                        }
                                    }
                                } else if ("Load Card".equals(e.getEquipmentType())) {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    paramsSfItem.put("pkID", reqSf.getSfpkidLc());
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                } else if ("Program Card".equals(e.getEquipmentType())) {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    paramsSfItem.put("pkID", reqSf.getSfpkidPc());
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                } else if ("Load Card & Program Card".equals(e.getEquipmentType())) {
                                    if (!"0".equals(e.getPcbAQty())) {
                                        System.out.println("GET SFITEM BY PARAM...");
                                        JSONObject paramsSfItem = new JSONObject();
                                        paramsSfItem.put("pkID", reqSf.getSfpkidLc());
                                        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                        int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                        String version = getItemByParam.getJSONObject(0).getString("Version");
                                        LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itempkid);
                                        params3.put("version", version);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts");
                                        } else {
                                            LOGGER.info("failed to update spts");
                                        }
                                    }
                                    if (!"0".equals(e.getPcbBQty())) {
                                        System.out.println("GET SFITEM BY PARAM...");
                                        JSONObject paramsSfItem = new JSONObject();
                                        paramsSfItem.put("pkID", reqSf.getSfpkidPc());
                                        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                        int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                        String version = getItemByParam.getJSONObject(0).getString("Version");
                                        LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                        JSONObject params3 = new JSONObject();
                                        params3.put("pkID", itempkid);
                                        params3.put("version", version);
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getInventoryRack());
                                        params3.put("sfShelf", e.getInventoryShelf());
                                        SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                        System.out.println("status: " + sfPkid.getStatus());

                                        if (sfPkid.getStatus()) {
                                            LOGGER.info("done update spts");
                                        } else {
                                            LOGGER.info("failed to update spts");
                                        }
                                    }

                                } else {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    paramsSfItem.put("pkID", reqSf.getSfpkid());
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                }

                                //update shipping table
                                WhShippingDAO shipD = new WhShippingDAO();

                                int count = shipD.getCountRequestId(e.getRequestId());
                                if (count == 1) {
                                    shipD = new WhShippingDAO();
                                    WhShipping ship = shipD.getWhShippingNyRequestId(e.getRequestId());
                                    ship.setStatus("In SF Inventory");
                                    ship.setFlag("1");
                                    shipD = new WhShippingDAO();
                                    QueryResult updateShip = shipD.updateWhShipping(ship);
                                    if (updateShip.getResult() == 1) {
                                        LOGGER.info("[UPDATE Inventory] - update ship status and flag");
                                    } else {
                                        LOGGER.info("[UPDATE Inventory] - failed to update ship status and flag");
                                    }
                                } else {
                                    LOGGER.info("[UPDATE Inventory] - no request id");
                                }

                                //update status at master table request
                                reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(e.getRequestId());
                                if (countReq == 1) {
                                    reqD = new WhRequestDAO();
                                    WhRequest req = reqD.getWhRequest(e.getRequestId());
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(req.getModifiedBy());
                                    reqUpdate.setStatus("In SF Inventory");
                                    reqUpdate.setId(e.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[CREATE Inventory] - update status at request table done");
                                    } else {
                                        LOGGER.info("[CREATE Inventory] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[CREATE Inventory] - requestId not found");
                                }

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
                        if ("Load Card".equals(e.getEquipmentType())) {
                            insertftp.setLoadCard(e.getPcbA());
                            insertftp.setLoadCardQty(e.getPcbAQty());
                            insertftp.setPcbA("");
                            insertftp.setPcbAQty("0");

                            insertftp.setPcbB(e.getPcbB());
                            insertftp.setPcbBQty(e.getPcbBQty());
                            insertftp.setProgramCard("");
                            insertftp.setProgramCardQty("0");

                        } else if ("Program Card".equals(e.getEquipmentType())) {
                            insertftp.setProgramCard(e.getPcbB());
                            insertftp.setProgramCardQty(e.getPcbBQty());
                            insertftp.setPcbB("");
                            insertftp.setPcbBQty("0");

                            insertftp.setPcbA(e.getPcbA());
                            insertftp.setPcbAQty(e.getPcbAQty());
                            insertftp.setLoadCard("");
                            insertftp.setLoadCardQty("0");
                        } else if ("Load Card & Program Card".equals(e.getEquipmentType())) {
                            insertftp.setLoadCard(e.getPcbA());
                            insertftp.setLoadCardQty(e.getPcbAQty());
                            insertftp.setPcbA("");
                            insertftp.setPcbAQty("0");

                            insertftp.setProgramCard(e.getPcbB());
                            insertftp.setProgramCardQty(e.getPcbBQty());
                            insertftp.setPcbB("");
                            insertftp.setPcbBQty("0");
                        } else {
                            insertftp.setPcbA(e.getPcbA());
                            insertftp.setPcbAQty(e.getPcbAQty());
                            insertftp.setPcbB(e.getPcbB());
                            insertftp.setPcbBQty(e.getPcbBQty());

                            insertftp.setLoadCard("");
                            insertftp.setLoadCardQty("0");
                            insertftp.setProgramCard("");
                            insertftp.setProgramCardQty("0");
                        }

                        insertftp.setPcbC(e.getPcbC());
                        insertftp.setPcbCQty(e.getPcbCQty());
                        insertftp.setPcbCtr(e.getPcbCtr());
                        insertftp.setPcbCtrQty(e.getPcbCtrQty());
                        insertftp.setQuantity(e.getQuantity());
                        insertftp.setRequestedBy(e.getRequestedBy());
                        insertftp.setRequestedDate(e.getRequestedDate());
                        insertftp.setRemarks(e.getRemarks());
                        insertftp.setVerifiedDate(e.getVerifiedDate());
                        insertftp.setReceivalDate(e.getReceivalDate());
                        insertftp.setInventoryDate(e.getInventoryDate());
                        insertftp.setInventoryBy(e.getInventoryBy());
                        insertftp.setInventoryRack(e.getInventoryRack());
                        insertftp.setInventoryShelf(e.getInventoryShelf());
                        insertftp.setStatus(e.getStatus());
                        insertftp.setFlag("0");
                        QueryResult add = insertDao.insertWhInventory(insertftp);
                        if (add.getResult() == 1) {
                            LOGGER.info("insert file");

                            //update statusLog
                            WhStatusLog stat = new WhStatusLog();
                            stat.setRequestId(e.getRequestId());
                            stat.setModule("cdars_wh_inventory");
                            stat.setStatus("In SF Inventory");
                            stat.setCreatedBy("-");
                            stat.setFlag("0");
                            WhStatusLogDAO statD = new WhStatusLogDAO();
                            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                            if (queryResultStat.getGeneratedKey().equals("0")) {
                                LOGGER.info("[HmsFtpConfig] - insert status log failed");
                            } else {
                                LOGGER.info("[HmsFtpConfig] - insert status log done");
                            }

                            //update spts location
                            //get item pkid and version
                            WhRequestDAO reqD = new WhRequestDAO();
                            WhRequest reqSf = reqD.getWhRequest(e.getRequestId());
                            if ("PCB".equals(e.getEquipmentType())) {
                                if (!"0".equals(e.getPcbAQty())) {
                                    System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                    JSONObject paramsQualA = new JSONObject();
//                                    String itemID = e.getPcbA();
//                                    paramsQualA.put("itemID", itemID);
                                    paramsQualA.put("pkID", reqSf.getSfpkid());
                                    JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                    int itemApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                    String versionA = getItemByParamA.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemApkid............." + itemApkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemApkid);
                                    params3.put("version", versionA);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB A");
                                    } else {
                                        LOGGER.info("failed to update spts PCB A");
                                    }
                                }
                                if (!"0".equals(e.getPcbBQty())) {
                                    System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                    JSONObject paramsQualB = new JSONObject();
//                                    String itemID = e.getPcbB();
//                                    paramsQualB.put("itemID", itemID);
                                    paramsQualB.put("pkID", reqSf.getSfpkidB());
                                    JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                                    int itemBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                                    String versionB = getItemByParamB.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemApkid............." + itemBpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemBpkid);
                                    params3.put("version", versionB);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB B");
                                    } else {
                                        LOGGER.info("failed to update spts PCB B");
                                    }
                                }
                                if (!"0".equals(e.getPcbCQty())) {
                                    System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                    JSONObject paramsQualC = new JSONObject();
//                                    String itemID = e.getPcbC();
//                                    paramsQualC.put("itemID", itemID);
                                    paramsQualC.put("pkID", reqSf.getSfpkidC());
                                    JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                                    int itemCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                                    String versionC = getItemByParamC.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemCpkid............." + itemCpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemCpkid);
                                    params3.put("version", versionC);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB C");
                                    } else {
                                        LOGGER.info("failed to update spts PCB C");
                                    }
                                }
                                if (!"0".equals(e.getPcbCtrQty())) {
                                    System.out.println("GET SFITEM PCB CONTROL BY PARAM...");
                                    JSONObject paramsQualCtr = new JSONObject();
//                                    String itemID = e.getPcbCtr();
//                                    paramsQualCtr.put("itemID", itemID);
                                    paramsQualCtr.put("pkID", reqSf.getSfpkidCtr());
                                    JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                                    int itemCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                                    String versionCtr = getItemByParamCtr.getJSONObject(0).getString("Version");
                                    LOGGER.info("itemCtrpkid............." + itemCtrpkid);

                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itemCtrpkid);
                                    params3.put("version", versionCtr);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts PCB Ctr");
                                    } else {
                                        LOGGER.info("failed to update spts PCB Ctr");
                                    }
                                }
                            } else if ("Load Card".equals(e.getEquipmentType())) {
                                System.out.println("GET SFITEM BY PARAM...");
                                JSONObject paramsSfItem = new JSONObject();
                                paramsSfItem.put("pkID", reqSf.getSfpkidLc());
                                JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                String version = getItemByParam.getJSONObject(0).getString("Version");
                                LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                JSONObject params3 = new JSONObject();
                                params3.put("pkID", itempkid);
                                params3.put("version", version);
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", e.getInventoryRack());
                                params3.put("sfShelf", e.getInventoryShelf());
                                SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                System.out.println("status: " + sfPkid.getStatus());

                                if (sfPkid.getStatus()) {
                                    LOGGER.info("done update spts");
                                } else {
                                    LOGGER.info("failed to update spts");
                                }
                            } else if ("Program Card".equals(e.getEquipmentType())) {
                                System.out.println("GET SFITEM BY PARAM...");
                                JSONObject paramsSfItem = new JSONObject();
                                paramsSfItem.put("pkID", reqSf.getSfpkidPc());
                                JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                String version = getItemByParam.getJSONObject(0).getString("Version");
                                LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                JSONObject params3 = new JSONObject();
                                params3.put("pkID", itempkid);
                                params3.put("version", version);
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", e.getInventoryRack());
                                params3.put("sfShelf", e.getInventoryShelf());
                                SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                System.out.println("status: " + sfPkid.getStatus());

                                if (sfPkid.getStatus()) {
                                    LOGGER.info("done update spts");
                                } else {
                                    LOGGER.info("failed to update spts");
                                }
                            } else if ("Load Card & Program Card".equals(e.getEquipmentType())) {
                                if (!"0".equals(e.getPcbAQty())) {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    paramsSfItem.put("pkID", reqSf.getSfpkidLc());
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                }
                                if (!"0".equals(e.getPcbBQty())) {
                                    System.out.println("GET SFITEM BY PARAM...");
                                    JSONObject paramsSfItem = new JSONObject();
                                    paramsSfItem.put("pkID", reqSf.getSfpkidPc());
                                    JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    String version = getItemByParam.getJSONObject(0).getString("Version");
                                    LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                    JSONObject params3 = new JSONObject();
                                    params3.put("pkID", itempkid);
                                    params3.put("version", version);
                                    params3.put("sfItemStatus", "0");
                                    params3.put("sfRack", e.getInventoryRack());
                                    params3.put("sfShelf", e.getInventoryShelf());
                                    SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                    System.out.println("status: " + sfPkid.getStatus());

                                    if (sfPkid.getStatus()) {
                                        LOGGER.info("done update spts");
                                    } else {
                                        LOGGER.info("failed to update spts");
                                    }
                                }

                            } else {
                                System.out.println("GET SFITEM BY PARAM...");
                                JSONObject paramsSfItem = new JSONObject();
                                paramsSfItem.put("pkID", reqSf.getSfpkid());
                                JSONArray getItemByParam = SPTSWebService.getSFItemByParam(paramsSfItem);
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                String version = getItemByParam.getJSONObject(0).getString("Version");
                                LOGGER.info("itempkid............." + itempkid);

//                                public bool UpdateSFItemLocation(int pkID, string version, int sfItemStatus, string sfRack, string sfShelf)
                                JSONObject params3 = new JSONObject();
                                params3.put("pkID", itempkid);
                                params3.put("version", version);
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", e.getInventoryRack());
                                params3.put("sfShelf", e.getInventoryShelf());
                                SPTSResponse sfPkid = SPTSWebService.updateSFItemLocation(params3);

                                System.out.println("status: " + sfPkid.getStatus());

                                if (sfPkid.getStatus()) {
                                    LOGGER.info("done update spts");
                                } else {
                                    LOGGER.info("failed to update spts");
                                }
                            }

                            //update shipping table
                            WhShippingDAO shipD = new WhShippingDAO();

                            int count = shipD.getCountRequestId(e.getRequestId());
                            if (count == 1) {
                                shipD = new WhShippingDAO();
                                WhShipping ship = shipD.getWhShippingNyRequestId(e.getRequestId());
                                ship.setStatus("In SF Inventory");
                                ship.setFlag("1");
                                shipD = new WhShippingDAO();
//                                QueryResult updateShip = shipD.updateWhShipping(ship);
                                QueryResult updateShip = shipD.updateWhShippingStatusAndInventoryDate(ship);
                                if (updateShip.getResult() == 1) {
                                    LOGGER.info("[CREATE Inventory] - update ship status and flag");
                                } else {
                                    LOGGER.info("[CREATE Inventory] - failed for update ship status and flag");
                                }
                            } else {
                                LOGGER.info("[CREATE Inventory] - no request id");
                            }

                            //update status at master table request
                            reqD = new WhRequestDAO();
                            int countReq = reqD.getCountRequestId(e.getRequestId());
                            if (countReq == 1) {
                                reqD = new WhRequestDAO();
                                WhRequest req = reqD.getWhRequest(e.getRequestId());
                                WhRequest reqUpdate = new WhRequest();
                                reqUpdate.setModifiedBy(req.getModifiedBy());
                                reqUpdate.setStatus("In SF Inventory");
                                reqUpdate.setId(e.getRequestId());
                                reqD = new WhRequestDAO();
                                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                if (ru.getResult() == 1) {
                                    LOGGER.info("[CREATE Inventory] - update status at request table done");
                                } else {
                                    LOGGER.info("[CREATE Inventory] - update status at request table failed");
                                }
                            } else {
                                LOGGER.info("[CREATE Inventory] - requestId not found");
                            }

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

//                        if (!"Ship".equals(checkstatus) && !"Closed".equals(checkstatus) && !"Barcode Verified".equals(checkstatus)) {
                        if ("Requested".equals(checkstatus)) {
                            WhRetrieval update = new WhRetrieval();
                            update.setId(id);
                            update.setRequestId(e.getRequestId());
                            update.setMpNo(e.getMpNo());
                            update.setVerifiedBy(e.getVerifiedBy());
                            update.setVerifiedDate(e.getVerifiedDate());
                            update.setShippingBy(e.getShippingBy());
                            update.setShippingDate(e.getShippingDate());
//                            update.setStatus(e.getStatus()); //original 3/11/16
                            update.setStatus("Shipped"); //as requested 2/11/16
                            update.setFlag("0");

                            WhRetrievalDAO updateDao = new WhRetrievalDAO();
                            QueryResult updateRetrieval = updateDao.updateWhRetrievalFromCsv(update);
                            if (updateRetrieval.getResult() == 1) {
                                LOGGER.info("Update Done!");

                                //update statusLog
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(e.getRequestId());
                                stat.setModule("cdars_wh_retrieval");
                                stat.setStatus("Shipped From Seremban Factory");
                                stat.setCreatedBy("-");
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[HmsFtpConfig] - insert status log failed");
                                } else {
                                    LOGGER.info("[HmsFtpConfig] - insert status log done");
                                }

                                //update status at master table request
                                WhRequestDAO reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(e.getRequestId());
                                if (countReq == 1) {
                                    reqD = new WhRequestDAO();
                                    WhRequest req = reqD.getWhRequest(e.getRequestId());
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(req.getModifiedBy());
                                    reqUpdate.setStatus("Shipped From Seremban Factory");
                                    reqUpdate.setId(e.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[From SF] - update status at request table done");
                                    } else {
                                        LOGGER.info("[From SF] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[From SF] - requestId not found");
                                }
                            } else {
                                LOGGER.info("Update Failed!");
                            }
                        }

                    }
//                    else {
//                        LOGGER.info("No data found");
//                    }
                }

            } else {
                LOGGER.info("retrieval csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

//    @Scheduled(cron = "0 49 11 * * *") //every 5 minutes
    public void DownloadPairCardCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }
//            LOGGER.info("username!!!" + username);
            File file = new File("\\\\mysed-rel-app03\\c$\\Users\\imperial\\Documents\\CDARS\\card_pairing.csv");

            //            File file = new File("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
            if (file.exists()) {
//                LOGGER.info("File dots_rms_ftp found!!!");
                csvReader = new CSVReader(new FileReader("\\\\mysed-rel-app03\\c$\\Users\\imperial\\Documents\\CDARS\\card_pairing.csv"), ',', '"', 1);
                //                csvReader = new CSVReader(new FileReader("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<CardPairingTemp> empList = new ArrayList<CardPairingTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    CardPairingTemp emp = new CardPairingTemp(inventory[0],
                            inventory[1], inventory[2]);
                    empList.add(emp);
                }

                int count = 0;
                //Lets print the Inventory List
                for (CardPairingTemp e : empList) {

                    CardPairingDAO cardD = new CardPairingDAO();

                    String pairId = cardD.getNextPairId();

                    CardPairing req = new CardPairing();
                    req.setType(e.getType());
                    req.setProgramCard(e.getProgramCard());
                    req.setLoadCard(e.getLoadCard());
                    req.setPairId(pairId);
                    req.setCreatedBy("CSV");

                    CardPairingDAO insertD = new CardPairingDAO();
                    QueryResult queryAdd1 = insertD.insertCardPairingFromCsv(req);
                    if (queryAdd1.getGeneratedKey().equals("0")) {
                        LOGGER.info("Fail to insert Program Card = " + e.getProgramCard() + " Load Card = " + e.getLoadCard());

                    } else {
                        count++;
                    }

                }
                LOGGER.info("total = " + count);

            } else {
                LOGGER.info("csv file not found!");
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    //migrate data from csv for eqp
//    @Scheduled(cron = "0 */15 * * * *") //every 15 minutes
    public void DownloadMigrateCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }
//            LOGGER.info("username!!!" + username);
//            File file = new File("\\\\mysed-rel-app03\\c$\\Users\\" + username + "imperial\\Documents\\CDARS\\hims_eqp_data_migration.csv");
            File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_eqp_data_migration.csv");

            //            File file = new File("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
            if (file.exists()) {
//                LOGGER.info("File dots_rms_ftp found!!!");
                csvReader = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_eqp_data_migration.csv"), ',', '"', 1);
                //                csvReader = new CSVReader(new FileReader("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<WhEqptCsvTemp> empList = new ArrayList<WhEqptCsvTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhEqptCsvTemp emp = new WhEqptCsvTemp(inventory[0],
                            inventory[1], inventory[2], inventory[3],
                            inventory[4], inventory[5], inventory[6],
                            inventory[7], inventory[8]);
                    empList.add(emp);
                }

                int count = 0;
                //Lets print the Inventory List
                for (WhEqptCsvTemp e : empList) {

                    WhRequestDAO whReq = new WhRequestDAO();
                    int countEqpt = whReq.getCountRequestEqpTypeAndEqpIdandMpNo(e.getEquipmentType(), e.getEquipmentId(), e.getMpNo());

                    if (countEqpt == 0) {

                        WhRequest req = new WhRequest();
                        req.setRequestType("Ship");
                        req.setEquipmentType(e.getEquipmentType());
                        req.setEquipmentId(e.getEquipmentId());
                        req.setQuantity(e.getQuantity());
                        req.setMpNo(e.getMpNo());
                        req.setMpExpiryDate(e.getMpExpiryDate());
                        req.setRequestedBy(e.getRequestedBy());
                        req.setRequestorEmail(e.getRequestedEmail());
                        req.setRack(e.getRack());
                        req.setShelf(e.getShelf());
                        req.setRemarks("Migrated from Csv file");
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = new Date();
                        String formattedDate = dateFormat.format(date);
                        String RemarksLogFull = "[" + formattedDate + "] - Migrated from Csv file";
                        req.setRemarksLog(RemarksLogFull);
                        req.setCreatedBy("1");
                        req.setStatus("In SF Inventory");
                        req.setFlag("0");
                        req.setPcbAQty("0");
                        req.setPcbBQty("0");
                        req.setPcbCQty("0");
                        req.setPcbCtrQty("0");
                        req.setSfpkidB("0");
                        req.setSfpkidC("0");
                        req.setSfpkidCtr("0");
                        req.setLoadCardQty("0");
                        req.setProgramCardQty("0");
                        req.setSfpkidLc("0");
                        req.setSfpkidPc("0");

                        WhRequestDAO reqD = new WhRequestDAO();
                        QueryResult queryAdd1 = reqD.insertWhRequestForEqptCsv(req);
                        if (queryAdd1.getGeneratedKey().equals("0")) {
                            break;
                        } else {
                            //status log
                            WhStatusLog stat = new WhStatusLog();
                            stat.setRequestId(queryAdd1.getGeneratedKey());
                            stat.setModule("cdars_wh_request");
                            stat.setStatus("Migrated from Csv file");
                            stat.setCreatedBy("Csv File");
                            stat.setFlag("0");
                            WhStatusLogDAO statD = new WhStatusLogDAO();
                            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                            if (queryResultStat.getGeneratedKey().equals("0")) {
                                LOGGER.info("[WhRequest] - insert status log failed");
                            } else {
                                LOGGER.info("[WhRequest] - insert status log done");
                            }

                            //update SPTS
                            System.out.println("GET ITEM BY PARAM...");
                            JSONObject params0 = new JSONObject();
                            String itemID = e.getEquipmentId();
                            params0.put("itemID", itemID);
                            JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
//                            for (int i = 0; i < getItemByParam.length(); i++) {
//                                System.out.println(getItemByParam.getJSONObject(i));
//                            }
                            System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                            int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                            LOGGER.info("itempkid............." + itempkid);

                            //                        //insert transaction spts
                            JSONObject params2 = new JSONObject();
                            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                            //get current date time with Date()
                            Date date1 = new Date();
                            String formattedDate1 = dateFormat1.format(date1);
                            params2.put("dateTime", formattedDate1);
                            params2.put("itemsPKID", itempkid);
                            params2.put("transType", "19");
                            params2.put("transQty", e.getQuantity());
                            params2.put("remarks", "send to storage factory through cdars");
                            SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                            System.out.println("transPKID: " + TransPkid.getResponseId());

                            if (TransPkid.getResponseId() > 0) {
                                //                        //insert sfitem spts
                                JSONObject params3 = new JSONObject();
                                params3.put("itemPKID", itempkid);
                                params3.put("transactionPKID", TransPkid.getResponseId());
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", e.getRack());
                                params3.put("sfShelf", e.getShelf());
                                SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                System.out.println("sfPKID: " + sfPkid.getResponseId());

                                //update sfpkid to request table
                                req = new WhRequest();
                                req.setSfpkid(sfPkid.getResponseId().toString());
                                req.setId(queryAdd1.getGeneratedKey());
                                reqD = new WhRequestDAO();
                                QueryResult queryAdd2 = reqD.updateWhRequestSfpkid(req);

                                //insert to ship table
                                WhShipping ship = new WhShipping();
                                ship.setRequestId(queryAdd1.getGeneratedKey());
                                ship.setMpNo(e.getMpNo());
                                ship.setMpExpiryDate(e.getMpExpiryDate());
                                ship.setHardwareBarcode1(e.getEquipmentId());
                                ship.setHardwareBarcode2(e.getMpNo());
                                ship.setStatus("In SF Inventory");
                                ship.setRemarks("Migrated from Csv file");
                                //ship.setFlag("1");

                                //available utk print trip ticket & barcode sticker
                                ship.setFlag("0");
                                ship.setCreatedBy("1");
                                ship.setSfpkid(sfPkid.getResponseId().toString());
                                ship.setItempkid("0");
                                ship.setSfpkidB("0");
                                ship.setSfpkidC("0");
                                ship.setSfpkidCtr("0");
                                ship.setSfpkidLc("0");
                                ship.setSfpkidPc("0");

                                WhShippingDAO shipD = new WhShippingDAO();
                                QueryResult queryAddShip = shipD.insertWhShippingEqptFromCsv(ship);

                                //insert inventory table
                                WhInventory inv = new WhInventory();
                                inv.setRequestId(queryAdd1.getGeneratedKey());
                                inv.setEquipmentType(e.getEquipmentType());
                                inv.setEquipmentId(e.getEquipmentId());
                                inv.setMpNo(e.getMpNo());
                                inv.setMpExpiryDate(e.getMpExpiryDate());
                                inv.setQuantity(e.getQuantity());
                                inv.setInventoryRack(e.getRack());
                                inv.setInventoryShelf(e.getShelf());
                                inv.setRequestedBy(e.getRequestedBy());
                                inv.setStatus("Move to Inventory");
                                inv.setFlag("0");
                                inv.setInventoryBy("Migrated from Csv file");
                                inv.setPcbAQty("0");
                                inv.setPcbBQty("0");
                                inv.setPcbCQty("0");
                                inv.setPcbCtrQty("0");
                                inv.setLoadCardQty("0");
                                inv.setProgramCardQty("0");

                                WhInventoryDAO invD = new WhInventoryDAO();
                                QueryResult queryAddInv = invD.insertWhInventoryEqptFromCsv(inv);

                                //add to csv file for hims sf data migration
                                File file1 = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_eqpt_migrate.csv");

                                if (file1.exists()) {
                                    //create csv file
                                    LOGGER.info("tiada header");
                                    FileWriter fileWriter = null;
                                    try {
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_eqpt_migrate.csv", true);
                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        //                            fileWriter.append(queryResult.getGeneratedKey());
                                        fileWriter.append(queryAdd1.getGeneratedKey());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getMpNo());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRequestedEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRack());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getShelf());
                                        fileWriter.append(COMMA_DELIMITER);
                                        System.out.println("append to CSV file Succeed!!!");
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    } finally {
                                        try {
                                            fileWriter.close();
                                        } catch (IOException ie) {
                                            System.out.println("Error occured while closing the fileWriter");
                                            ie.printStackTrace();
                                        }
                                    }
                                } else {
                                    FileWriter fileWriter = null;
                                    try {
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_eqpt_migrate.csv");
                                        LOGGER.info("no file yet");
                                        //Adding the header
                                        fileWriter.append(HEADER);

                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        fileWriter.append(queryAdd1.getGeneratedKey());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getMpNo());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRequestedEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getRack());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(e.getShelf());
                                        fileWriter.append(COMMA_DELIMITER);
                                        System.out.println("Write new to CSV file Succeed!!!");
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    } finally {
                                        try {
                                            fileWriter.close();
                                        } catch (IOException ie) {
                                            System.out.println("Error occured while closing the fileWriter");
                                            ie.printStackTrace();
                                        }
                                    }
                                }

                                count++;
                            } else {
                                break;
                            }

                        }
                    }

                }
                LOGGER.info("total = " + count);

            } else {
                LOGGER.info("hims_eqp_data_migration.csv file not found!");

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    //migrate data from csv for lc, pc
//    @Scheduled(cron = "0 */20 * * * *") //every 20 minutes
//    @Scheduled(cron = "0 */7 * * * *") //every 7 minutes
    public void DownloadMigrateLcPcCsv() throws IOException {

        CSVReader csvReader = null;
        //        LOGGER.info("cron detected!!!");

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             *
             */

            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }
//            LOGGER.info("username!!!" + username);
//            File file = new File("\\\\mysed-rel-app03\\c$\\Users\\" + username + "imperial\\Documents\\CDARS\\hims_eqp_data_migration.csv");
            File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_data_migration.csv");

            //            File file = new File("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv");
            if (file.exists()) {
//                LOGGER.info("File dots_rms_ftp found!!!");
                csvReader = new CSVReader(new FileReader("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_data_migration.csv"), ',', '"', 1);
                //                csvReader = new CSVReader(new FileReader("D:\\DOTS\\RMS_FTP\\dots_rms_ftp.csv"), ',', '"', 1);

                String[] inventory = null;
                //Create List for holding Employee objects
                List<WhBibCardCsvTemp> empList = new ArrayList<WhBibCardCsvTemp>();

                while ((inventory = csvReader.readNext()) != null) {
                    //Save the employee details in Employee object
                    WhBibCardCsvTemp emp = new WhBibCardCsvTemp(inventory[0],
                            inventory[1], inventory[2], inventory[3],
                            inventory[4], inventory[5], inventory[6],
                            inventory[7], inventory[8], inventory[9], inventory[10],
                            inventory[11], inventory[12]);
                    empList.add(emp);
                }

                int count = 0;
                //Lets print the Inventory List
                for (WhBibCardCsvTemp e : empList) {

                    if ("Load Card".equals(e.getEquipmentType())) {
                        CardPairingDAO cardD = new CardPairingDAO();
                        int countLcSingle = cardD.getCountLoadCardSingle(e.getLoadCardId());

                        if (countLcSingle == 1) {

                            WhRequestDAO whReq = new WhRequestDAO();
                            int countEqpt = whReq.getCountRequestEqpTypeAndLcandMpNo(e.getEquipmentType(), e.getLoadCardId(), e.getMpNo());

                            if (countEqpt == 0) {

                                cardD = new CardPairingDAO();
                                CardPairing card = cardD.getCardPairingWithLoadCardSingle(e.getLoadCardId());

                                WhRequest req = new WhRequest();
                                req.setRequestType("Ship");
                                req.setEquipmentType(e.getEquipmentType());
                                req.setEquipmentId(card.getPairId() + " - " + card.getType());
                                req.setLoadCard(e.getLoadCardId());
                                req.setLoadCardQty(e.getLoadCardQty());
                                req.setQuantity(e.getLoadCardQty());
                                req.setMpNo(e.getMpNo());
                                req.setMpExpiryDate(e.getMpExpiryDate());
                                req.setRequestedBy(e.getRequestedBy());
                                req.setRequestorEmail(e.getRequestedEmail());
                                req.setRack(e.getRack());
                                req.setShelf(e.getShelf());
                                req.setRemarks("Migrated from Csv file");
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                String RemarksLogFull = "[" + formattedDate + "] - Migrated from Csv file";
                                req.setRemarksLog(RemarksLogFull);
                                req.setCreatedBy("1");
                                req.setStatus("In SF Inventory");
                                req.setFlag("0");
                                req.setPcbAQty("0");
                                req.setPcbBQty("0");
                                req.setPcbCQty("0");
                                req.setPcbCtrQty("0");
                                req.setSfpkidB("0");
                                req.setSfpkidC("0");
                                req.setSfpkidCtr("0");
                                req.setProgramCardQty("0");
                                req.setSfpkidLc("0");
                                req.setSfpkidPc("0");
                                req.setSfpkid("0");

                                WhRequestDAO reqD = new WhRequestDAO();
                                QueryResult queryAdd1 = reqD.insertWhRequestForLcCsv(req);
                                if (queryAdd1.getGeneratedKey().equals("0")) {
                                    break;
                                } else {
                                    //status log
                                    WhStatusLog stat = new WhStatusLog();
                                    stat.setRequestId(queryAdd1.getGeneratedKey());
                                    stat.setModule("cdars_wh_request");
                                    stat.setStatus("Migrated from Csv file");
                                    stat.setCreatedBy("Csv File");
                                    stat.setFlag("0");
                                    WhStatusLogDAO statD = new WhStatusLogDAO();
                                    QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                    if (queryResultStat.getGeneratedKey().equals("0")) {
                                        LOGGER.info("[WhRequest] - insert status log failed");
                                    } else {
                                        LOGGER.info("[WhRequest] - insert status log done");
                                    }

                                    //update SPTS
                                    System.out.println("GET ITEM BY PARAM...");
                                    JSONObject params0 = new JSONObject();
                                    String itemID = e.getLoadCardId();
                                    params0.put("itemID", itemID);
                                    JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
//                            for (int i = 0; i < getItemByParam.length(); i++) {
//                                System.out.println(getItemByParam.getJSONObject(i));
//                            }
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    LOGGER.info("itempkid............." + itempkid);

                                    //                        //insert transaction spts
                                    JSONObject params2 = new JSONObject();
                                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                    //get current date time with Date()
                                    Date date1 = new Date();
                                    String formattedDate1 = dateFormat1.format(date1);
                                    params2.put("dateTime", formattedDate1);
                                    params2.put("itemsPKID", itempkid);
                                    params2.put("transType", "19");
                                    params2.put("transQty", e.getLoadCardQty());
                                    params2.put("remarks", "send to storage factory through cdars");
                                    SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                                    System.out.println("transPKID: " + TransPkid.getResponseId());

                                    if (TransPkid.getResponseId() > 0) {
                                        //                        //insert sfitem spts
                                        JSONObject params3 = new JSONObject();
                                        params3.put("itemPKID", itempkid);
                                        params3.put("transactionPKID", TransPkid.getResponseId());
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getRack());
                                        params3.put("sfShelf", e.getShelf());
                                        SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                        System.out.println("sfPKID: " + sfPkid.getResponseId());

                                        //update sfpkid to request table
                                        req = new WhRequest();
                                        req.setSfpkidLc(sfPkid.getResponseId().toString());
                                        req.setId(queryAdd1.getGeneratedKey());
                                        reqD = new WhRequestDAO();
                                        QueryResult queryAdd2 = reqD.updateWhRequestSfpkidLc(req);

                                        //insert to ship table
                                        WhShipping ship = new WhShipping();
                                        ship.setRequestId(queryAdd1.getGeneratedKey());
                                        ship.setMpNo(e.getMpNo());
                                        ship.setMpExpiryDate(e.getMpExpiryDate());
                                        ship.setHardwareBarcode1(card.getPairId() + " - " + card.getType());
                                        ship.setHardwareBarcode2(e.getMpNo());
                                        ship.setStatus("In SF Inventory");
                                        ship.setRemarks("Migrated from Csv file");
                                        //ship.setFlag("1");

                                        //available utk print trip ticket & barcode sticker
                                        ship.setFlag("0");
                                        ship.setCreatedBy("1");
                                        ship.setSfpkid("0");
                                        ship.setItempkid("0");
                                        ship.setSfpkidB("0");
                                        ship.setSfpkidC("0");
                                        ship.setSfpkidCtr("0");
                                        ship.setSfpkidLc(sfPkid.getResponseId().toString());
                                        ship.setSfpkidPc("0");

                                        WhShippingDAO shipD = new WhShippingDAO();
                                        QueryResult queryAddShip = shipD.insertWhShippingEqptFromCsv(ship);

                                        //insert inventory table
                                        WhInventory inv = new WhInventory();
                                        inv.setRequestId(queryAdd1.getGeneratedKey());
                                        inv.setEquipmentType(e.getEquipmentType());
                                        inv.setEquipmentId(card.getPairId() + " - " + card.getType());
                                        inv.setLoadCard(e.getLoadCardId());
                                        inv.setMpNo(e.getMpNo());
                                        inv.setMpExpiryDate(e.getMpExpiryDate());
                                        inv.setQuantity(e.getLoadCardQty());
                                        inv.setInventoryRack(e.getRack());
                                        inv.setInventoryShelf(e.getShelf());
                                        inv.setRequestedBy(e.getRequestedBy());
                                        inv.setStatus("Move to Inventory");
                                        inv.setFlag("0");
                                        inv.setInventoryBy("Migrated from Csv file");
                                        inv.setPcbAQty("0");
                                        inv.setPcbBQty("0");
                                        inv.setPcbCQty("0");
                                        inv.setPcbCtrQty("0");
                                        inv.setLoadCardQty(e.getLoadCardQty());
                                        inv.setProgramCardQty("0");

                                        WhInventoryDAO invD = new WhInventoryDAO();
                                        QueryResult queryAddInv = invD.insertWhInventoryLcFromCsv(inv);

                                        //add to csv file for hims sf data migration
                                        File file1 = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");

                                        if (file1.exists()) {
                                            //create csv file
                                            LOGGER.info("tiada header");
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv", true);
                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                //                            fileWriter.append(queryResult.getGeneratedKey());
                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append("0");
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("append to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        } else {
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");
                                                LOGGER.info("no file yet");
                                                //Adding the header
                                                fileWriter.append(HEADER_CARD);

                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append("0");
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("Write new to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        }

                                        count++;
                                    } else {
                                        LOGGER.info("Fail SPTS :  " + queryAdd1.getGeneratedKey());
//                                        break;
                                    }

                                }
                            }

                        } else {
                            break;
                        }
                    } else if ("Program Card".equals(e.getEquipmentType())) {
                        CardPairingDAO cardD = new CardPairingDAO();
                        int countPcSingle = cardD.getCountProgramCardSingle(e.getProgramCardId());

                        if (countPcSingle == 1) {

                            WhRequestDAO whReq = new WhRequestDAO();
                            int countEqpt = whReq.getCountRequestEqpTypeAndPcandMpNo(e.getEquipmentType(), e.getProgramCardId(), e.getMpNo());

                            if (countEqpt == 0) {

                                cardD = new CardPairingDAO();
                                CardPairing card = cardD.getCardPairingWithProgramCardSingle(e.getProgramCardId());

                                WhRequest req = new WhRequest();
                                req.setRequestType("Ship");
                                req.setEquipmentType(e.getEquipmentType());
                                req.setEquipmentId(card.getPairId() + " - " + card.getType());
                                req.setProgramCard(e.getProgramCardId());
                                req.setProgramCardQty(e.getProgramCardQty());
                                req.setQuantity(e.getProgramCardQty());
                                req.setMpNo(e.getMpNo());
                                req.setMpExpiryDate(e.getMpExpiryDate());
                                req.setRequestedBy(e.getRequestedBy());
                                req.setRequestorEmail(e.getRequestedEmail());
                                req.setRack(e.getRack());
                                req.setShelf(e.getShelf());
                                req.setRemarks("Migrated from Csv file");
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                String RemarksLogFull = "[" + formattedDate + "] - Migrated from Csv file";
                                req.setRemarksLog(RemarksLogFull);
                                req.setCreatedBy("1");
                                req.setStatus("In SF Inventory");
                                req.setFlag("0");
                                req.setPcbAQty("0");
                                req.setPcbBQty("0");
                                req.setPcbCQty("0");
                                req.setPcbCtrQty("0");
                                req.setSfpkidB("0");
                                req.setSfpkidC("0");
                                req.setSfpkidCtr("0");
                                req.setLoadCardQty("0");
                                req.setSfpkidLc("0");
                                req.setSfpkidPc("0");
                                req.setSfpkid("0");

                                WhRequestDAO reqD = new WhRequestDAO();
                                QueryResult queryAdd1 = reqD.insertWhRequestForPcCsv(req);
                                if (queryAdd1.getGeneratedKey().equals("0")) {
                                    break;
                                } else {
                                    //status log
                                    WhStatusLog stat = new WhStatusLog();
                                    stat.setRequestId(queryAdd1.getGeneratedKey());
                                    stat.setModule("cdars_wh_request");
                                    stat.setStatus("Migrated from Csv file");
                                    stat.setCreatedBy("Csv File");
                                    stat.setFlag("0");
                                    WhStatusLogDAO statD = new WhStatusLogDAO();
                                    QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                    if (queryResultStat.getGeneratedKey().equals("0")) {
                                        LOGGER.info("[WhRequest] - insert status log failed");
                                    } else {
                                        LOGGER.info("[WhRequest] - insert status log done");
                                    }

                                    //update SPTS
                                    System.out.println("GET ITEM BY PARAM...");
                                    JSONObject params0 = new JSONObject();
                                    String itemID = e.getProgramCardId();
                                    params0.put("itemID", itemID);
                                    JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
//                            for (int i = 0; i < getItemByParam.length(); i++) {
//                                System.out.println(getItemByParam.getJSONObject(i));
//                            }
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                    int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                    LOGGER.info("itempkid............." + itempkid);

                                    //                        //insert transaction spts
                                    JSONObject params2 = new JSONObject();
                                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                    //get current date time with Date()
                                    Date date1 = new Date();
                                    String formattedDate1 = dateFormat1.format(date1);
                                    params2.put("dateTime", formattedDate1);
                                    params2.put("itemsPKID", itempkid);
                                    params2.put("transType", "19");
                                    params2.put("transQty", e.getProgramCardQty());
                                    params2.put("remarks", "send to storage factory through cdars");
                                    SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                                    System.out.println("transPKID: " + TransPkid.getResponseId());

                                    if (TransPkid.getResponseId() > 0) {
                                        //                        //insert sfitem spts
                                        JSONObject params3 = new JSONObject();
                                        params3.put("itemPKID", itempkid);
                                        params3.put("transactionPKID", TransPkid.getResponseId());
                                        params3.put("sfItemStatus", "0");
                                        params3.put("sfRack", e.getRack());
                                        params3.put("sfShelf", e.getShelf());
                                        SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                        System.out.println("sfPKID: " + sfPkid.getResponseId());

                                        //update sfpkid to request table
                                        req = new WhRequest();
                                        req.setSfpkidPc(sfPkid.getResponseId().toString());
                                        req.setId(queryAdd1.getGeneratedKey());
                                        reqD = new WhRequestDAO();
                                        QueryResult queryAdd2 = reqD.updateWhRequestSfpkidPc(req);

                                        //insert to ship table
                                        WhShipping ship = new WhShipping();
                                        ship.setRequestId(queryAdd1.getGeneratedKey());
                                        ship.setMpNo(e.getMpNo());
                                        ship.setMpExpiryDate(e.getMpExpiryDate());
                                        ship.setHardwareBarcode1(card.getPairId() + " - " + card.getType());
                                        ship.setHardwareBarcode2(e.getMpNo());
                                        ship.setStatus("In SF Inventory");
                                        ship.setRemarks("Migrated from Csv file");
                                        //ship.setFlag("1");

                                        //available utk print trip ticket & barcode sticker
                                        ship.setFlag("0");
                                        ship.setCreatedBy("1");
                                        ship.setSfpkid("0");
                                        ship.setItempkid("0");
                                        ship.setSfpkidB("0");
                                        ship.setSfpkidC("0");
                                        ship.setSfpkidCtr("0");
                                        ship.setSfpkidLc("0");
                                        ship.setSfpkidPc(sfPkid.getResponseId().toString());

                                        WhShippingDAO shipD = new WhShippingDAO();
                                        QueryResult queryAddShip = shipD.insertWhShippingEqptFromCsv(ship);

                                        //insert inventory table
                                        WhInventory inv = new WhInventory();
                                        inv.setRequestId(queryAdd1.getGeneratedKey());
                                        inv.setEquipmentType(e.getEquipmentType());
                                        inv.setEquipmentId(card.getPairId() + " - " + card.getType());
                                        inv.setProgramCard(e.getProgramCardId());
                                        inv.setMpNo(e.getMpNo());
                                        inv.setMpExpiryDate(e.getMpExpiryDate());
                                        inv.setQuantity(e.getProgramCardQty());
                                        inv.setInventoryRack(e.getRack());
                                        inv.setInventoryShelf(e.getShelf());
                                        inv.setRequestedBy(e.getRequestedBy());
                                        inv.setStatus("Move to Inventory");
                                        inv.setFlag("0");
                                        inv.setInventoryBy("Migrated from Csv file");
                                        inv.setPcbAQty("0");
                                        inv.setPcbBQty("0");
                                        inv.setPcbCQty("0");
                                        inv.setPcbCtrQty("0");
                                        inv.setLoadCardQty("0");
                                        inv.setProgramCardQty(e.getProgramCardQty());

                                        WhInventoryDAO invD = new WhInventoryDAO();
                                        QueryResult queryAddInv = invD.insertWhInventoryPcFromCsv(inv);

                                        //add to csv file for hims sf data migration
                                        File file1 = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");

                                        if (file1.exists()) {
                                            //create csv file
                                            LOGGER.info("tiada header");
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv", true);
                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                //                            fileWriter.append(queryResult.getGeneratedKey());
                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append("0");
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("append to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        } else {
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");
                                                LOGGER.info("no file yet");
                                                //Adding the header
                                                fileWriter.append(HEADER_CARD);

                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append("0");
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("Write new to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        }

                                        count++;
                                    } else {
                                        LOGGER.info("Fail SPTS :  " + queryAdd1.getGeneratedKey());
//                                        break;
                                    }

                                }
                            }

                        } else {
                            break;
                        }

                    } else if ("Load Card & Program Card".equals(e.getEquipmentType())) {
                        CardPairingDAO cardD = new CardPairingDAO();
                        int countLcPcSingle = cardD.getCountLoadCardProgramCardPair(e.getLoadCardId(), e.getProgramCardId());

                        boolean flag = true;
                        String sfpkidLc = "0";
                        String sfpkidPc = "0";
                        int transLc = 0;
                        int transPc = 0;
                        int itemPkidLc = 0;
                        int itemPkidPc = 0;

                        if (countLcPcSingle == 1) {

                            WhRequestDAO whReq = new WhRequestDAO();
                            int countEqpt = whReq.getCountRequestEqpTypeAndLcAndPcandMpNo(e.getEquipmentType(), e.getLoadCardId(), e.getProgramCardId(), e.getMpNo());

                            if (countEqpt == 0) {

                                cardD = new CardPairingDAO();
                                CardPairing card = cardD.getCardPairingWithLoadCardProgramCardPair(e.getLoadCardId(), e.getProgramCardId());

                                WhRequest req = new WhRequest();
                                req.setRequestType("Ship");
                                req.setEquipmentType(e.getEquipmentType());
                                req.setEquipmentId(card.getPairId() + " - " + card.getType());
                                req.setLoadCard(e.getLoadCardId());
                                req.setLoadCardQty(e.getLoadCardQty());
                                req.setProgramCard(e.getProgramCardId());
                                req.setProgramCardQty(e.getProgramCardQty());
                                req.setQuantity(e.getTotalQty());
                                req.setMpNo(e.getMpNo());
                                req.setMpExpiryDate(e.getMpExpiryDate());
                                req.setRequestedBy(e.getRequestedBy());
                                req.setRequestorEmail(e.getRequestedEmail());
                                req.setRack(e.getRack());
                                req.setShelf(e.getShelf());
                                req.setRemarks("Migrated from Csv file");
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                String RemarksLogFull = "[" + formattedDate + "] - Migrated from Csv file";
                                req.setRemarksLog(RemarksLogFull);
                                req.setCreatedBy("1");
                                req.setStatus("In SF Inventory");
                                req.setFlag("0");
                                req.setPcbAQty("0");
                                req.setPcbBQty("0");
                                req.setPcbCQty("0");
                                req.setPcbCtrQty("0");
                                req.setSfpkidB("0");
                                req.setSfpkidC("0");
                                req.setSfpkidCtr("0");
                                req.setSfpkidLc("0");
                                req.setSfpkidPc("0");
                                req.setSfpkid("0");

                                WhRequestDAO reqD = new WhRequestDAO();
                                QueryResult queryAdd1 = reqD.insertWhRequestForLcPcCsv(req);
                                if (queryAdd1.getGeneratedKey().equals("0")) {
                                    break;
                                } else {
                                    //status log
                                    WhStatusLog stat = new WhStatusLog();
                                    stat.setRequestId(queryAdd1.getGeneratedKey());
                                    stat.setModule("cdars_wh_request");
                                    stat.setStatus("Migrated from Csv file");
                                    stat.setCreatedBy("Csv File");
                                    stat.setFlag("0");
                                    WhStatusLogDAO statD = new WhStatusLogDAO();
                                    QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                    if (queryResultStat.getGeneratedKey().equals("0")) {
                                        LOGGER.info("[WhRequest] - insert status log failed");
                                    } else {
                                        LOGGER.info("[WhRequest] - insert status log done");
                                    }

                                    if (!"0".equals(e.getLoadCardQty())) {

                                        //update SPTS
                                        System.out.println("GET ITEM BY PARAM...");
                                        JSONObject params0 = new JSONObject();
                                        String itemID = e.getLoadCardId();
                                        params0.put("itemID", itemID);
                                        JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                        int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                        LOGGER.info("itempkid............." + itempkid);
                                        itemPkidLc = itempkid;

                                        //                        //insert transaction spts
                                        JSONObject params2 = new JSONObject();
                                        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                        //get current date time with Date()
                                        Date date1 = new Date();
                                        String formattedDate1 = dateFormat1.format(date1);
                                        params2.put("dateTime", formattedDate1);
                                        params2.put("itemsPKID", itempkid);
                                        params2.put("transType", "19");
                                        params2.put("transQty", e.getLoadCardQty());
                                        params2.put("remarks", "send to storage factory through cdars");
                                        SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                                        System.out.println("transPKID: " + TransPkid.getResponseId());

                                        if (TransPkid.getResponseId() > 0) {
                                            //                        //insert sfitem spts
                                            JSONObject params3 = new JSONObject();
                                            params3.put("itemPKID", itempkid);
                                            params3.put("transactionPKID", TransPkid.getResponseId());
                                            params3.put("sfItemStatus", "0");
                                            params3.put("sfRack", e.getRack());
                                            params3.put("sfShelf", e.getShelf());
                                            SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                            System.out.println("sfPKID: " + sfPkid.getResponseId());

                                            sfpkidLc = sfPkid.getResponseId().toString();
                                        } else {
                                            flag = false;
//                                            break;
                                        }

                                    }
                                    if (!"0".equals(e.getProgramCardQty())) {
                                        //update SPTS
                                        System.out.println("GET ITEM BY PARAM...");
                                        JSONObject params0 = new JSONObject();
                                        String itemID = e.getProgramCardId();
                                        params0.put("itemID", itemID);
                                        JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);

                                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                                        int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                                        LOGGER.info("itempkid............." + itempkid);
                                        itemPkidPc = itempkid;

                                        //insert transaction spts
                                        JSONObject params2 = new JSONObject();
                                        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                        //get current date time with Date()
                                        Date date1 = new Date();
                                        String formattedDate1 = dateFormat1.format(date1);
                                        params2.put("dateTime", formattedDate1);
                                        params2.put("itemsPKID", itempkid);
                                        params2.put("transType", "19");
                                        params2.put("transQty", e.getProgramCardQty());
                                        params2.put("remarks", "send to storage factory through cdars");
                                        SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                                        System.out.println("transPKID: " + TransPkid.getResponseId());

                                        if (TransPkid.getResponseId() > 0) {
                                            //                        //insert sfitem spts
                                            JSONObject params3 = new JSONObject();
                                            params3.put("itemPKID", itempkid);
                                            params3.put("transactionPKID", TransPkid.getResponseId());
                                            params3.put("sfItemStatus", "0");
                                            params3.put("sfRack", e.getRack());
                                            params3.put("sfShelf", e.getShelf());
                                            SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                            System.out.println("sfPKID: " + sfPkid.getResponseId());

                                            sfpkidPc = sfPkid.getResponseId().toString();
                                        } else {
                                            flag = false;
//                                            break;
                                        }

                                    }
                                    if (flag == true) {

                                        //update sfpkid to request table
                                        req = new WhRequest();
                                        req.setSfpkidLc(sfpkidLc);
                                        req.setSfpkidPc(sfpkidPc);
                                        req.setId(queryAdd1.getGeneratedKey());
                                        reqD = new WhRequestDAO();
                                        QueryResult queryAdd2 = reqD.updateWhRequestSfpkidLcAndPc(req);

                                        //insert to ship table
                                        WhShipping ship = new WhShipping();
                                        ship.setRequestId(queryAdd1.getGeneratedKey());
                                        ship.setMpNo(e.getMpNo());
                                        ship.setMpExpiryDate(e.getMpExpiryDate());
                                        ship.setHardwareBarcode1(card.getPairId() + " - " + card.getType());
                                        ship.setHardwareBarcode2(e.getMpNo());
                                        ship.setStatus("In SF Inventory");
                                        ship.setRemarks("Migrated from Csv file");
                                        //ship.setFlag("1");

                                        //available utk print trip ticket & barcode sticker
                                        ship.setFlag("0");
                                        ship.setCreatedBy("1");
                                        ship.setSfpkid("0");
                                        ship.setItempkid("0");
                                        ship.setSfpkidB("0");
                                        ship.setSfpkidC("0");
                                        ship.setSfpkidCtr("0");
                                        ship.setSfpkidLc(sfpkidLc);
                                        ship.setSfpkidPc(sfpkidPc);

                                        WhShippingDAO shipD = new WhShippingDAO();
                                        QueryResult queryAddShip = shipD.insertWhShippingEqptFromCsv(ship);

                                        //insert inventory table
                                        WhInventory inv = new WhInventory();
                                        inv.setRequestId(queryAdd1.getGeneratedKey());
                                        inv.setEquipmentType(e.getEquipmentType());
                                        inv.setEquipmentId(card.getPairId() + " - " + card.getType());
                                        inv.setLoadCard(e.getLoadCardId());
                                        inv.setProgramCard(e.getProgramCardId());
                                        inv.setMpNo(e.getMpNo());
                                        inv.setMpExpiryDate(e.getMpExpiryDate());
                                        inv.setQuantity(e.getTotalQty());
                                        inv.setInventoryRack(e.getRack());
                                        inv.setInventoryShelf(e.getShelf());
                                        inv.setRequestedBy(e.getRequestedBy());
                                        inv.setStatus("Move to Inventory");
                                        inv.setFlag("0");
                                        inv.setInventoryBy("Migrated from Csv file");
                                        inv.setPcbAQty("0");
                                        inv.setPcbBQty("0");
                                        inv.setPcbCQty("0");
                                        inv.setPcbCtrQty("0");
                                        inv.setLoadCardQty(e.getLoadCardQty());
                                        inv.setProgramCardQty(e.getProgramCardQty());

                                        WhInventoryDAO invD = new WhInventoryDAO();
                                        QueryResult queryAddInv = invD.insertWhInventoryLcAndPcFromCsv(inv);

                                        //add to csv file for hims sf data migration
                                        File file1 = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");

                                        if (file1.exists()) {
                                            //create csv file
                                            LOGGER.info("tiada header");
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv", true);
                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                //                            fileWriter.append(queryResult.getGeneratedKey());
                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getTotalQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("append to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        } else {
                                            FileWriter fileWriter = null;
                                            try {
                                                fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\hims_bibCard_migrate.csv");
                                                LOGGER.info("no file yet");
                                                //Adding the header
                                                fileWriter.append(HEADER_CARD);

                                                //New Line after the header
                                                fileWriter.append(LINE_SEPARATOR);

                                                fileWriter.append(queryAdd1.getGeneratedKey());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getEquipmentType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(card.getPairId() + " - " + card.getType());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getLoadCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardId());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getProgramCardQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getTotalQty());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpNo());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getMpExpiryDate());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedBy());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRequestedEmail());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getRack());
                                                fileWriter.append(COMMA_DELIMITER);
                                                fileWriter.append(e.getShelf());
                                                fileWriter.append(COMMA_DELIMITER);
                                                System.out.println("Write new to CSV file Succeed!!!");
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            } finally {
                                                try {
                                                    fileWriter.close();
                                                } catch (IOException ie) {
                                                    System.out.println("Error occured while closing the fileWriter");
                                                    ie.printStackTrace();
                                                }
                                            }
                                        }
                                        count++;
                                    } else {
                                        //revert spts transaction
                                        LOGGER.info("Fail spts transaction - revert! = " + queryAdd1.getGeneratedKey());
                                        if (transLc > 0) {
                                            JSONObject paramsA2 = new JSONObject();
                                            DateFormat dateFormatLc = new SimpleDateFormat("yyyy-MM-dd");
                                            Date dateLc = new Date();
                                            String formattedDateLc = dateFormatLc.format(dateLc);
                                            paramsA2.put("dateTime", formattedDateLc);
                                            paramsA2.put("itemsPKID", itemPkidLc);
                                            paramsA2.put("transType", "20");
                                            paramsA2.put("transQty", e.getLoadCardQty());
                                            paramsA2.put("remarks", "Cancel shipment to SBN Factory");
                                            SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                                            System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());
                                            if (TransPkidQualA.getResponseId() > 0) {

                                                //get item from sfitem
                                                JSONObject paramsQualA = new JSONObject();
                                                paramsQualA.put("pkID", sfpkidLc);
                                                JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                                int itemSfApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                                String versionSfA = getItemByParamA.getJSONObject(0).getString("Version");

                                                //delete sfitem
                                                JSONObject paramsdeleteSfA = new JSONObject();
                                                paramsdeleteSfA.put("pkID", itemSfApkid);
                                                paramsdeleteSfA.put("version", versionSfA);
                                                SPTSResponse deleteA = SPTSWebService.DeleteSFItem(paramsdeleteSfA);
                                            } else {
                                                LOGGER.info("transaction failed load card");
                                            }
                                        }
                                        if (transPc > 0) {
                                            JSONObject paramsA2 = new JSONObject();
                                            DateFormat dateFormatPc = new SimpleDateFormat("yyyy-MM-dd");
                                            Date datePc = new Date();
                                            String formattedDatePc = dateFormatPc.format(datePc);
                                            paramsA2.put("dateTime", formattedDatePc);
                                            paramsA2.put("itemsPKID", itemPkidPc);
                                            paramsA2.put("transType", "20");
                                            paramsA2.put("transQty", e.getProgramCardQty());
                                            paramsA2.put("remarks", "Cancel shipment to SBN Factory");
                                            SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                                            System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());

                                            if (TransPkidQualA.getResponseId() > 0) {
                                                //get item from sfitem
                                                JSONObject paramsQualA = new JSONObject();
                                                paramsQualA.put("pkID", sfpkidPc);
                                                JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                                                int itemSfApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                                                String versionSfA = getItemByParamA.getJSONObject(0).getString("Version");

                                                //delete sfitem
                                                JSONObject paramsdeleteSfA = new JSONObject();
                                                paramsdeleteSfA.put("pkID", itemSfApkid);
                                                paramsdeleteSfA.put("version", versionSfA);
                                                SPTSResponse deleteA = SPTSWebService.DeleteSFItem(paramsdeleteSfA);
                                            } else {
                                                LOGGER.info("transaction failed load card");
                                            }
                                        }
                                        LOGGER.info("PAIR Fail SPTS :  " + queryAdd1.getGeneratedKey());
//                                        break;
                                    }

                                }
                            }

                        } else {
                            break;
                        }

                    }
                }
                LOGGER.info("total = " + count);

            } else {
                LOGGER.info("hims_bibCard_data_migration.csv file not found!");

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
}
