package com.onsemi.cdars.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhMpListDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.WhShippingCsvTemp;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.CSV;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/wh/whShipping/whMpList")
@SessionAttributes({"userSession"})
public class WhMpListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhMpListController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "id,hardware_type,hardware_id,pcb_a,pcb_a_qty,pcb_b,pcb_b_qty,pcb_c,pcb_c_qty,pcb_ctr,pcb_ctr_qty,quantity,material pass number,material pass expiry date,requested_by,"
            + "requestor_email,requested_date,remarks,shipping_date,status";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whMpList(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplay();
        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();
        model.addAttribute("whMpListList", whMpListList);
        model.addAttribute("count", count);
        return "whMpList/whMpList";
    }

//    @RequestMapping(value = "/22", method = RequestMethod.GET)
//    public String whMpList2(
//            Model model
//    ) {
//        WhMpListDAO whMpListDAO = new WhMpListDAO();
//        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
//        whMpListDAO = new WhMpListDAO();
//        int count = whMpListDAO.getCountWithFlag0();
//
//        //count dashboard ship
//        WhShippingDAO shipD = new WhShippingDAO();
//        int countMpShip = shipD.getCountNoMpNumFlag0();
//        shipD = new WhShippingDAO();
//        int countBsShip = shipD.getCountBSFlag0();
//        shipD = new WhShippingDAO();
//        int countTtShip = shipD.getCountTtFlag0();
//        shipD = new WhShippingDAO();
//        int countShippedShip = shipD.getCountShippedFlag0();
//        model.addAttribute("countMpShip", countMpShip);
//        model.addAttribute("countBsShip", countBsShip);
//        model.addAttribute("countTtShip", countTtShip);
//        model.addAttribute("countShippedShip", countShippedShip);
//
//        //count dashboard requests
//        WhRequestDAO reqD = new WhRequestDAO();
//        int countWaitReq = reqD.getCountWaitingFlag0();
//        reqD = new WhRequestDAO();
//        int countAppReq = reqD.getCountApprovedFlag0();
//        reqD = new WhRequestDAO();
//        int countNotAppReq = reqD.getCountNotApprovedFlag0();
//        model.addAttribute("countWaitReq", countWaitReq);
//        model.addAttribute("countAppReq", countAppReq);
//        model.addAttribute("countNotAppReq", countNotAppReq);
//
//        //count dashboard retrieval
//        WhRetrievalDAO retD = new WhRetrievalDAO();
//        int countReqRet = retD.getCountRequestFlag0();
//        retD = new WhRetrievalDAO();
//        int countShipRet = retD.getCountShipFlag0();
//        retD = new WhRetrievalDAO();
//        int countBsVerRet = retD.getCountBsVerifiedFlag0();
//        retD = new WhRetrievalDAO();
//        int countBsMisRet = retD.getCountBsMismatchedFlag0();
//        retD = new WhRetrievalDAO();
//        int countBsUnvRet = retD.getCountBsUnverifiedFlag0();
//        retD = new WhRetrievalDAO();
//        int countBsHoldRet = retD.getCountBsHoldFlag0();
//        retD = new WhRetrievalDAO();
//        int countTtMisRet = retD.getCountTtMismatchedFlag0();
//        retD = new WhRetrievalDAO();
//        int countTtUnvRet = retD.getCountTtUnverifiedFlag0();
//        retD = new WhRetrievalDAO();
//        int countTtHoldRet = retD.getCountTtHoldFlag0();
//        model.addAttribute("countReqRet", countReqRet);
//        model.addAttribute("countShipRet", countShipRet);
//        model.addAttribute("countBsVerRet", countBsVerRet);
//        model.addAttribute("countBsMisRet", countBsMisRet);
//        model.addAttribute("countBsUnvRet", countBsUnvRet);
//        model.addAttribute("countBsHoldRet", countBsHoldRet);
//        model.addAttribute("countTtMisRet", countTtMisRet);
//        model.addAttribute("countTtUnvRet", countTtUnvRet);
//        model.addAttribute("countTtHoldRet", countTtHoldRet);
//
//        model.addAttribute("whMpListList", whMpListList);
//        model.addAttribute("count", count);
//        return "whMpList/whMpList_2";
//    }
    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String whMpList1(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.updateWhMpListtoFlag1();

        whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplay();

        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();
        model.addAttribute("count", count);
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplay();

        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();

        whMpListDAO = new WhMpListDAO();
        int countAll = whMpListDAO.getCountAllList();

        model.addAttribute("count", count);
        model.addAttribute("countAll", countAll);
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            HttpServletRequest request,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mpNo
    ) throws IOException {
        //check ad material pass ke tidak dkt shipping n status = Verified or Ship
        WhShippingDAO whShipD = new WhShippingDAO();
        int count = whShipD.getCountMpNoEligbleToShip(mpNo);
        if (count != 0) {
            //check da ade ke mp_no dkt whmplist
            WhMpListDAO whMpListDAO = new WhMpListDAO();
            int countMpNo = whMpListDAO.getCountMpNoWithFlag0(mpNo);
            if (countMpNo == 0) {
                WhShippingDAO whshipD = new WhShippingDAO();

                WhShipping whship = whshipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                WhMpList whMpList = new WhMpList();
                whMpList.setWhShipId(whship.getId());
                whMpList.setMpNo(mpNo);
                whMpList.setMpExpiryDate(whship.getMpExpiryDate());
                whMpList.setHardwareType(whship.getRequestEquipmentType());
                whMpList.setHardwareId(whship.getRequestEquipmentId());
                whMpList.setQuantity(whship.getRequestQuantity());
                whMpList.setRequestedBy(whship.getRequestRequestedBy());
                whMpList.setRequestedDate(whship.getRequestRequestedDate());
                whMpList.setCreatedBy(userSession.getFullname());
                whMpList.setFlag("0");
                whMpListDAO = new WhMpListDAO();
                QueryResult queryResult = whMpListDAO.insertWhMpList(whMpList);
                args = new String[1];
                args[0] = mpNo;
                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("whMpList", whMpList);
                } else {

                    if (!"PCB".equals(whship.getRequestEquipmentType()) && !"Tray".equals(whship.getRequestEquipmentType())) {

                        //check dkt stps da transfer ke sf ke belum
                        WhShippingDAO shipD = new WhShippingDAO();
                        WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                        if ("0".equals(checkSfPkid.getSfpkid())) {
                            //get pkid
                            System.out.println("GET ITEM BY PARAM...");
                            JSONObject params0 = new JSONObject();
                            String itemID = whship.getRequestEquipmentId();
                            params0.put("itemID", itemID);
                            JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
                            for (int i = 0; i < getItemByParam.length(); i++) {
                                System.out.println(getItemByParam.getJSONObject(i));
                            }
                            System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                            int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                            LOGGER.info("itempkid............." + itempkid);

                            //                        //insert transaction spts
                            JSONObject params2 = new JSONObject();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //get current date time with Date()
                            Date date = new Date();
                            String formattedDate = dateFormat.format(date);
                            params2.put("dateTime", formattedDate);
                            params2.put("itemsPKID", itempkid);
                            params2.put("transType", "19");
                            params2.put("transQty", "1");
                            params2.put("remarks", "send to storage factory through cdars");
                            SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                            System.out.println("transPKID: " + TransPkid.getResponseId());

                            if (TransPkid.getResponseId() > 0) {
                                //insert sfitem spts
                                JSONObject params3 = new JSONObject();
                                params3.put("itemPKID", itempkid);
                                params3.put("transactionPKID", TransPkid.getResponseId());
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", "");
                                params3.put("sfShelf", "");
                                SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                System.out.println("sfPKID: " + sfPkid.getResponseId());

                                //update statusLog
                                whshipD = new WhShippingDAO();
                                WhShipping shipping = whshipD.getWhShipping(whship.getId());

                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(shipping.getRequestId());
                                stat.setModule("cdars_wh_mp_list");
                                stat.setStatus("Ship to Seremban Factory");
                                stat.setCreatedBy(userSession.getFullname());
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[WhRequest] - insert status log failed");
                                } else {
                                    LOGGER.info("[WhRequest] - insert status log done");
                                }

                                //update status, sfpkid, itempkid at shipping list to "Pending Shipment to Seremban Factory"
                                shipD = new WhShippingDAO();
                                WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                                WhShipping shipUpdate = new WhShipping();
                                shipUpdate.setId(ship.getId());
                                shipUpdate.setRequestId(ship.getRequestId());
                                shipUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                                shipUpdate.setSfpkid(sfPkid.getResponseId().toString());
                                shipUpdate.setItempkid(String.valueOf(itempkid));
                                WhShippingDAO shipDD = new WhShippingDAO();
                                QueryResult u = shipDD.updateWhShippingStatusWithItemPkidAndSfpkid(shipUpdate);
                                if (u.getResult() == 1) {
                                    LOGGER.info("Status Ship updated");
                                } else {
                                    LOGGER.info("Status Ship updated failed");
                                }

                                //update status,sfpkid dkt master *request table     
                                WhRequestDAO reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(ship.getRequestId());
                                if (countReq == 1) {
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(userSession.getFullname());
                                    reqUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                                    reqUpdate.setSfpkid(sfPkid.getResponseId().toString());
                                    reqUpdate.setId(ship.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatusWithSfpkid(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[MpList] - update status at request table done");
                                    } else {
                                        LOGGER.info("[MpList] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[MpList] - requestId not found");
                                }

                                String username = System.getProperty("user.name");
                                if (!"fg79cj".equals(username)) {
                                    username = "imperial";
                                }
                                File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");

                                if (file.exists()) {
                                    //create csv file
                                    LOGGER.info("tiada header");
                                    FileWriter fileWriter = null;
                                    try {
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv", true);
                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        //                            fileWriter.append(queryResult.getGeneratedKey());
                                        fileWriter.append(whship.getRequestId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbA());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbAQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbB());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbBQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbC());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtr());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtrQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(mpNo);
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestorEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRemarks());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getCreatedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append("Shipped");
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
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
                                        LOGGER.info("no file yet");
                                        //Adding the header
                                        fileWriter.append(HEADER);

                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        fileWriter.append(whship.getRequestId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbA());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbAQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbB());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbBQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbC());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtr());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtrQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(mpNo);
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestorEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRemarks());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getCreatedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append("Shipped");
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

                            } else {
                                LOGGER.info("fail to update spts");

                                //delete whMplist
                                whMpListDAO = new WhMpListDAO();
                                QueryResult queryResultDeleteFailed = whMpListDAO.deleteWhMpList(queryResult.getGeneratedKey());

                                //update statusLog
                                whshipD = new WhShippingDAO();
                                WhShipping shipping = whshipD.getWhShipping(whship.getId());
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(shipping.getRequestId());
                                stat.setModule("cdars_wh_mp_list");
                                stat.setStatus("Failed to update SPTS");
                                stat.setCreatedBy(userSession.getFullname());
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);

                                String messageError = "Failed to update SPTS. Please re-check!";
                                redirectAttrs.addFlashAttribute("error", messageError);
                                return "redirect:/wh/whShipping/whMpList/add";
                            }
                        } else {
                            LOGGER.info("No need to update spts.Already have record in SfItem");
                        }

                    } else if ("Tray".equals(whship.getRequestEquipmentType())) {

                        //check dkt stps da transfer ke sf ke belum
                        WhShippingDAO shipD = new WhShippingDAO();
                        WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                        if ("0".equals(checkSfPkid.getSfpkid())) {

                            //get pkid
                            System.out.println("GET ITEM BY PARAM...");
                            JSONObject params0 = new JSONObject();
                            String itemID = whship.getRequestEquipmentId();
                            params0.put("itemID", itemID);
                            JSONArray getItemByParam = SPTSWebService.getItemByParam(params0);
                            for (int i = 0; i < getItemByParam.length(); i++) {
                                System.out.println(getItemByParam.getJSONObject(i));
                            }
                            System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                            int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                            LOGGER.info("itempkid............." + itempkid);

                            //                        //insert transaction spts
                            JSONObject params2 = new JSONObject();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //get current date time with Date()
                            Date date = new Date();
                            String formattedDate = dateFormat.format(date);
                            params2.put("dateTime", formattedDate);
                            params2.put("itemsPKID", itempkid);
                            params2.put("transType", "19");
                            params2.put("transQty", whship.getRequestQuantity());
                            params2.put("remarks", "send to storage factory through cdars");
                            SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                            System.out.println("transPKID: " + TransPkid.getResponseId());

                            if (TransPkid.getResponseId() > 0) {
                                //                        //insert sfitem spts
                                JSONObject params3 = new JSONObject();
                                params3.put("itemPKID", itempkid);
                                params3.put("transactionPKID", TransPkid.getResponseId());
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", "");
                                params3.put("sfShelf", "");
                                SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                System.out.println("sfPKID: " + sfPkid.getResponseId());

                                //update statusLog
                                whshipD = new WhShippingDAO();
                                WhShipping shipping = whshipD.getWhShipping(whship.getId());

                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(shipping.getRequestId());
                                stat.setModule("cdars_wh_mp_list");
                                stat.setStatus("Ship to Seremban Factory");
                                stat.setCreatedBy(userSession.getFullname());
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                                if (queryResultStat.getGeneratedKey().equals("0")) {
                                    LOGGER.info("[WhRequest] - insert status log failed");
                                } else {
                                    LOGGER.info("[WhRequest] - insert status log done");
                                }

                                //update status, sfpkid, itempkid at shipping list to "Pending Shipment to Seremban Factory"
                                shipD = new WhShippingDAO();
                                WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                                WhShipping shipUpdate = new WhShipping();
                                shipUpdate.setId(ship.getId());
                                shipUpdate.setRequestId(ship.getRequestId());
                                shipUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                                shipUpdate.setSfpkid(sfPkid.getResponseId().toString());
                                shipUpdate.setItempkid(String.valueOf(itempkid));
                                WhShippingDAO shipDD = new WhShippingDAO();
                                QueryResult u = shipDD.updateWhShippingStatusWithItemPkidAndSfpkid(shipUpdate);
                                if (u.getResult() == 1) {
                                    LOGGER.info("Status Ship updated");
                                } else {
                                    LOGGER.info("Status Ship updated failed");
                                }

                                //update status,sfpkid dkt master *request table     
                                WhRequestDAO reqD = new WhRequestDAO();
                                int countReq = reqD.getCountRequestId(ship.getRequestId());
                                if (countReq == 1) {
                                    WhRequest reqUpdate = new WhRequest();
                                    reqUpdate.setModifiedBy(userSession.getFullname());
                                    reqUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                                    reqUpdate.setSfpkid(sfPkid.getResponseId().toString());
                                    reqUpdate.setId(ship.getRequestId());
                                    reqD = new WhRequestDAO();
                                    QueryResult ru = reqD.updateWhRequestStatusWithSfpkid(reqUpdate);
                                    if (ru.getResult() == 1) {
                                        LOGGER.info("[MpList] - update status at request table done");
                                    } else {
                                        LOGGER.info("[MpList] - update status at request table failed");
                                    }
                                } else {
                                    LOGGER.info("[MpList] - requestId not found");
                                }

                                String username = System.getProperty("user.name");
                                if (!"fg79cj".equals(username)) {
                                    username = "imperial";
                                }
                                File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");

                                if (file.exists()) {
                                    //create csv file
                                    LOGGER.info("tiada header");
                                    FileWriter fileWriter = null;
                                    try {
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv", true);
                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        //                            fileWriter.append(queryResult.getGeneratedKey());
                                        fileWriter.append(whship.getRequestId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbA());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbAQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbB());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbBQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbC());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtr());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtrQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(mpNo);
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestorEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRemarks());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getCreatedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append("Shipped");
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
                                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
                                        LOGGER.info("no file yet");
                                        //Adding the header
                                        fileWriter.append(HEADER);

                                        //New Line after the header
                                        fileWriter.append(LINE_SEPARATOR);

                                        fileWriter.append(whship.getRequestId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentType());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestEquipmentId());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbA());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbAQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbB());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbBQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbC());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtr());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getPcbCtrQty());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestQuantity());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(mpNo);
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getMpExpiryDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedBy());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestorEmail());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRequestRequestedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getRemarks());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append(whship.getCreatedDate());
                                        fileWriter.append(COMMA_DELIMITER);
                                        fileWriter.append("Shipped");
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
                            } else {
                                LOGGER.info("fail to update spts");
                                //delete whMpList
                                whMpListDAO = new WhMpListDAO();
                                QueryResult queryResultDeleteFailed = whMpListDAO.deleteWhMpList(queryResult.getGeneratedKey());

                                //update statusLog
                                whshipD = new WhShippingDAO();
                                WhShipping shipping = whshipD.getWhShipping(whship.getId());
                                WhStatusLog stat = new WhStatusLog();
                                stat.setRequestId(shipping.getRequestId());
                                stat.setModule("cdars_wh_mp_list");
                                stat.setStatus("Failed to update SPTS");
                                stat.setCreatedBy(userSession.getFullname());
                                stat.setFlag("0");
                                WhStatusLogDAO statD = new WhStatusLogDAO();
                                QueryResult queryResultStat = statD.insertWhStatusLog(stat);

                                String messageError = "Failed to update SPTS. Please re-check!";
                                redirectAttrs.addFlashAttribute("error", messageError);
                                return "redirect:/wh/whShipping/whMpList/add";
                            }
                        } else {
                            LOGGER.info("No need to update spts.Already have record in SfItem");
                        }

                    } else if ("PCB".equals(whship.getRequestEquipmentType())) {

                        boolean flag = true;
                        int itemPkidA = 0;
                        int itemPkidB = 0;
                        int itemPkidC = 0;
                        int itemPkidCtr = 0;
                        int sfpkidA = 0;
                        int sfpkidB = 0;
                        int sfpkidC = 0;
                        int sfpkidCtr = 0;
                        int transA = 0;
                        int transB = 0;
                        int transC = 0;
                        int transCtr = 0;
                        if (!"0".equals(whship.getPcbAQty()) && !"".equals(whship.getPcbA())) {

                            //check dkt stps da transfer ke sf ke belum
                            WhShippingDAO shipD = new WhShippingDAO();
                            WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                            if ("0".equals(checkSfPkid.getSfpkid())) {

                                //get pkid for pcb qual a
                                System.out.println("GET ITEM BY PARAM...");
                                JSONObject paramsA = new JSONObject();
                                String itemIDQualA = whship.getPcbA();
                                paramsA.put("itemID", itemIDQualA);
                                JSONArray getItemByParamQualA = SPTSWebService.getItemByParam(paramsA);
                                for (int i = 0; i < getItemByParamQualA.length(); i++) {
                                    System.out.println(getItemByParamQualA.getJSONObject(i));
                                }
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualA.length());
                                int itempkidQualA = getItemByParamQualA.getJSONObject(0).getInt("PKID");
                                LOGGER.info("itempkidQualA............." + itempkidQualA);
                                itemPkidA = itempkidQualA;

                                //                        //insert transaction spts for pcb qual a
                                JSONObject paramsA2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsA2.put("dateTime", formattedDate);
                                paramsA2.put("itemsPKID", itempkidQualA);
                                paramsA2.put("transType", "19");
                                paramsA2.put("transQty", whship.getPcbAQty());
                                paramsA2.put("remarks", "send to storage factory through cdars");
                                SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                                System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());

                                if (TransPkidQualA.getResponseId() > 0) {
                                    //insert sfitem spts for pcb qual a
                                    JSONObject paramsA3 = new JSONObject();
                                    paramsA3.put("itemPKID", itempkidQualA);
                                    paramsA3.put("transactionPKID", TransPkidQualA.getResponseId());
                                    paramsA3.put("sfItemStatus", "0");
                                    paramsA3.put("sfRack", "");
                                    paramsA3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualA = SPTSWebService.insertSFItem(paramsA3);
                                    System.out.println("sfPkidQualA: " + sfPkidQualA.getResponseId());

                                    transA = TransPkidQualA.getResponseId();
                                    sfpkidA = sfPkidQualA.getResponseId();

                                    //original place for update statusLog, Ship and Request table
                                    //original place for insert to csv  
                                } else {
                                    flag = false;
                                    //original place for delete whmplist and insert log if failed update spts
                                }
                            } else {
                                LOGGER.info("No need to update spts.Already have record in SfItem");
                            }
                        }
                        if (!"0".equals(whship.getPcbBQty()) && !"".equals(whship.getPcbB())) {

                            //check dkt stps da transfer ke sf ke belum
                            WhShippingDAO shipD = new WhShippingDAO();
                            WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                            if ("0".equals(checkSfPkid.getSfpkidB())) {
                                //get pkid for pcb qual b
                                System.out.println("GET ITEM BY PARAM...");
                                JSONObject paramsB = new JSONObject();
                                String itemIDQualB = whship.getPcbB();
                                paramsB.put("itemID", itemIDQualB);
                                JSONArray getItemByParamQualB = SPTSWebService.getItemByParam(paramsB);
                                for (int i = 0; i < getItemByParamQualB.length(); i++) {
                                    System.out.println(getItemByParamQualB.getJSONObject(i));
                                }
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualB.length());
                                int itempkidQualB = getItemByParamQualB.getJSONObject(0).getInt("PKID");
                                LOGGER.info("itempkidQualB............." + itempkidQualB);
                                itemPkidB = itempkidQualB;

                                //                        //insert transaction spts for pcb qual b
                                JSONObject paramsB2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsB2.put("dateTime", formattedDate);
                                paramsB2.put("itemsPKID", itempkidQualB);
                                paramsB2.put("transType", "19");
                                paramsB2.put("transQty", whship.getPcbBQty());
                                paramsB2.put("remarks", "send to storage factory through cdars");
                                SPTSResponse TransPkidQualB = SPTSWebService.insertTransaction(paramsB2);
                                System.out.println("TransPkidQualB: " + TransPkidQualB.getResponseId());

                                if (TransPkidQualB.getResponseId() > 0) {
                                    //
                                    //                        //insert sfitem spts for pcb qual b
                                    JSONObject paramsB3 = new JSONObject();
                                    paramsB3.put("itemPKID", itempkidQualB);
                                    paramsB3.put("transactionPKID", TransPkidQualB.getResponseId());
                                    paramsB3.put("sfItemStatus", "0");
                                    paramsB3.put("sfRack", "");
                                    paramsB3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualB = SPTSWebService.insertSFItem(paramsB3);
                                    System.out.println("sfPkidQualB: " + sfPkidQualB.getResponseId());

                                    transB = TransPkidQualB.getResponseId();
                                    sfpkidB = sfPkidQualB.getResponseId();

                                    //original place for update statusLog, Ship and Request table
//                                  
                                    //original place for insert to csv  
                                } else {

                                    flag = false;
                                    //original place for delete whmplist and insert log if failed update spts
                                }
                            } else {
                                LOGGER.info("No need to update spts.Already have record in SfItem");
                            }
                        }
                        if (!"0".equals(whship.getPcbCQty()) && !"".equals(whship.getPcbC())) {
                            //check dkt stps da transfer ke sf ke belum
                            WhShippingDAO shipD = new WhShippingDAO();
                            WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                            if ("0".equals(checkSfPkid.getSfpkidC())) {
                                //get pkid for pcb qual c
                                System.out.println("GET ITEM BY PARAM...");
                                JSONObject paramsC = new JSONObject();
                                String itemIDQualC = whship.getPcbC();
                                paramsC.put("itemID", itemIDQualC);
                                JSONArray getItemByParamQualC = SPTSWebService.getItemByParam(paramsC);
                                for (int i = 0; i < getItemByParamQualC.length(); i++) {
                                    System.out.println(getItemByParamQualC.getJSONObject(i));
                                }
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualC.length());
                                int itempkidQualC = getItemByParamQualC.getJSONObject(0).getInt("PKID");
                                LOGGER.info("itempkidQualC............." + itempkidQualC);
                                itemPkidC = itempkidQualC;

                                //                        //insert transaction spts for pcb qual c
                                JSONObject paramsC2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsC2.put("dateTime", formattedDate);
                                paramsC2.put("itemsPKID", itempkidQualC);
                                paramsC2.put("transType", "19");
                                paramsC2.put("transQty", whship.getPcbCQty());
                                paramsC2.put("remarks", "send to storage factory through cdars");
                                SPTSResponse TransPkidQualC = SPTSWebService.insertTransaction(paramsC2);
                                System.out.println("TransPkidQualC: " + TransPkidQualC.getResponseId());

                                if (TransPkidQualC.getResponseId() > 0) {
                                    //
                                    //                        //insert sfitem spts for pcb qual c
                                    JSONObject paramsC3 = new JSONObject();
                                    paramsC3.put("itemPKID", itempkidQualC);
                                    paramsC3.put("transactionPKID", TransPkidQualC.getResponseId());
                                    paramsC3.put("sfItemStatus", "0");
                                    paramsC3.put("sfRack", "");
                                    paramsC3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualC = SPTSWebService.insertSFItem(paramsC3);
                                    System.out.println("sfPkidQualC: " + sfPkidQualC.getResponseId());

                                    transC = TransPkidQualC.getResponseId();
                                    sfpkidC = sfPkidQualC.getResponseId();

                                    //original place for update statusLog, Ship and Request table
//                                    
                                    //original place for insert to csv  
                                } else {

                                    flag = false;
                                    //original place for delete whmplist and insert log if failed update spts
                                }
                            } else {
                                LOGGER.info("No need to update spts.Already have record in SfItem");
                            }
                        }
                        if (!"0".equals(whship.getPcbCtrQty()) && !"".equals(whship.getPcbCtr())) {
                            //check dkt stps da transfer ke sf ke belum
                            WhShippingDAO shipD = new WhShippingDAO();
                            WhShipping checkSfPkid = shipD.getWhShippingSfpkidAndItemPkid(whship.getId());
                            if ("0".equals(checkSfPkid.getSfpkidCtr())) {
                                //get pkid for pcb qual ctr
                                System.out.println("GET ITEM BY PARAM...");
                                JSONObject paramsCtr = new JSONObject();
                                String itemIDQualCtr = whship.getPcbCtr();
                                paramsCtr.put("itemID", itemIDQualCtr);
                                JSONArray getItemByParamQualCtr = SPTSWebService.getItemByParam(paramsCtr);
                                for (int i = 0; i < getItemByParamQualCtr.length(); i++) {
                                    System.out.println(getItemByParamQualCtr.getJSONObject(i));
                                }
                                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualCtr.length());
                                int itempkidQualCtr = getItemByParamQualCtr.getJSONObject(0).getInt("PKID");
                                LOGGER.info("itempkidQualCtr............." + itempkidQualCtr);
                                itemPkidCtr = itempkidQualCtr;

                                //                        //insert transaction spts for pcb qual ctr
                                JSONObject paramsCtr2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsCtr2.put("dateTime", formattedDate);
                                paramsCtr2.put("itemsPKID", itempkidQualCtr);
                                paramsCtr2.put("transType", "19");
                                paramsCtr2.put("transQty", whship.getPcbCtrQty());
                                paramsCtr2.put("remarks", "send to storage factory through cdars");
                                SPTSResponse TransPkidQualCtr = SPTSWebService.insertTransaction(paramsCtr2);
                                System.out.println("TransPkidQualCtr: " + TransPkidQualCtr.getResponseId());

                                if (TransPkidQualCtr.getResponseId() > 0) {

                                    //                        //insert sfitem spts for pcb qual ctr
                                    JSONObject paramsCtr3 = new JSONObject();
                                    paramsCtr3.put("itemPKID", itempkidQualCtr);
                                    paramsCtr3.put("transactionPKID", TransPkidQualCtr.getResponseId());
                                    paramsCtr3.put("sfItemStatus", "0");
                                    paramsCtr3.put("sfRack", "");
                                    paramsCtr3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualCtr = SPTSWebService.insertSFItem(paramsCtr3);
                                    System.out.println("sfPkidQualCtr: " + sfPkidQualCtr.getResponseId());

                                    transCtr = TransPkidQualCtr.getResponseId();
                                    sfpkidCtr = sfPkidQualCtr.getResponseId();

                                    //original place for update statusLog, Ship and Request table
//original place for insert to csv                                 
                                } else {
                                    flag = false;
                                }
                            } else {
                                LOGGER.info("No need to update spts.Already have record in SfItem");
                            }
                        }
                        if (flag == true) {

                            //update statusLog
                            whshipD = new WhShippingDAO();
                            WhShipping shipping = whshipD.getWhShipping(whship.getId());

                            WhStatusLog stat = new WhStatusLog();
                            stat.setRequestId(shipping.getRequestId());
                            stat.setModule("cdars_wh_mp_list");
                            stat.setStatus("Ship to Seremban Factory");
                            stat.setCreatedBy(userSession.getFullname());
                            stat.setFlag("0");
                            WhStatusLogDAO statD = new WhStatusLogDAO();
                            QueryResult queryResultStat = statD.insertWhStatusLog(stat);

                            //update status, sfpkid, itempkid at shipping list to "Pending Shipment to Seremban Factory"
                            WhShippingDAO shipD = new WhShippingDAO();
                            WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                            WhShipping shipUpdate = new WhShipping();
                            shipUpdate.setId(ship.getId());
                            shipUpdate.setRequestId(ship.getRequestId());
                            shipUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                            shipUpdate.setSfpkid(String.valueOf(sfpkidA));
                            shipUpdate.setSfpkidB(String.valueOf(sfpkidB));
                            shipUpdate.setSfpkidC(String.valueOf(sfpkidC));
                            shipUpdate.setSfpkidCtr(String.valueOf(sfpkidCtr));
                            shipUpdate.setItempkid(String.valueOf("0"));
                            WhShippingDAO shipDD = new WhShippingDAO();
                            QueryResult u = shipDD.updateWhShippingStatusWithItemPkidAndSfpkidAll(shipUpdate);
                            if (u.getResult() == 1) {
                                LOGGER.info("Status Ship updated");
                            } else {
                                LOGGER.info("Status Ship updated failed");
                            }

                            //update status,sfpkid dkt master *request table     
                            WhRequestDAO reqD = new WhRequestDAO();
                            int countReq = reqD.getCountRequestId(ship.getRequestId());
                            if (countReq == 1) {
                                WhRequest reqUpdate = new WhRequest();
                                reqUpdate.setModifiedBy(userSession.getFullname());
                                reqUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                                reqUpdate.setSfpkid(String.valueOf(sfpkidA));
                                reqUpdate.setSfpkidB(String.valueOf(sfpkidB));
                                reqUpdate.setSfpkidC(String.valueOf(sfpkidC));
                                reqUpdate.setSfpkidCtr(String.valueOf(sfpkidCtr));
                                reqUpdate.setId(ship.getRequestId());
                                reqD = new WhRequestDAO();
                                QueryResult ru = reqD.updateWhRequestStatusWithSfpkidAll(reqUpdate);
                                if (ru.getResult() == 1) {
                                    LOGGER.info("[MpList] - update status at request table done");
                                } else {
                                    LOGGER.info("[MpList] - update status at request table failed");
                                }
                            } else {
                                LOGGER.info("[MpList] - requestId not found");
                            }

                            String username = System.getProperty("user.name");
                            if (!"fg79cj".equals(username)) {
                                username = "imperial";
                            }
                            File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");

                            if (file.exists()) {
                                //create csv file
                                LOGGER.info("tiada header");
                                FileWriter fileWriter = null;
                                try {
                                    fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv", true);
                                    //New Line after the header
                                    fileWriter.append(LINE_SEPARATOR);

                                    //                            fileWriter.append(queryResult.getGeneratedKey());
                                    fileWriter.append(whship.getRequestId());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestEquipmentType());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestEquipmentId());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbA());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbAQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbB());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbBQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbC());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCtr());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCtrQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestQuantity());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(mpNo);
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getMpExpiryDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestedBy());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestorEmail());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestedDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRemarks());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getCreatedDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append("Shipped");
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
                                    fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
                                    LOGGER.info("no file yet");
                                    //Adding the header
                                    fileWriter.append(HEADER);

                                    //New Line after the header
                                    fileWriter.append(LINE_SEPARATOR);

                                    fileWriter.append(whship.getRequestId());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestEquipmentType());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestEquipmentId());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbA());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbAQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbB());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbBQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbC());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCtr());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getPcbCtrQty());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestQuantity());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(mpNo);
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getMpExpiryDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestedBy());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestorEmail());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRequestRequestedDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getRemarks());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append(whship.getCreatedDate());
                                    fileWriter.append(COMMA_DELIMITER);
                                    fileWriter.append("Shipped");
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

                        } else {
                            LOGGER.info("[PCB] - fail to update spts");

                            if (transA > 0) {
                                //revert transaction for pcb qual a
                                JSONObject paramsA2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsA2.put("dateTime", formattedDate);
                                paramsA2.put("itemsPKID", itemPkidA);
                                paramsA2.put("transType", "20");
                                paramsA2.put("transQty", whship.getPcbAQty());
                                paramsA2.put("remarks", "Cancel shipment to SBN Factory");
                                SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                                System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());
                                if (TransPkidQualA.getResponseId() > 0) {
                                    LOGGER.info("transaction done pcb Qual A");

                                    //get item from sfitem
                                    System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                    JSONObject paramsQualA = new JSONObject();
                                    paramsQualA.put("pkID", sfpkidA);
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
                                    LOGGER.info("transaction failed pcb Qual A");
                                }
                            }

                            if (transB > 0) {
                                //revert transaction for pcb qual b
                                JSONObject paramsB2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsB2.put("dateTime", formattedDate);
                                paramsB2.put("itemsPKID", itemPkidB);
                                paramsB2.put("transType", "20");
                                paramsB2.put("transQty", whship.getPcbBQty());
                                paramsB2.put("remarks", "Cancel shipment to SBN Factory");
                                SPTSResponse TransPkidQualB = SPTSWebService.insertTransaction(paramsB2);
                                System.out.println("TransPkidQualB: " + TransPkidQualB.getResponseId());
                                if (TransPkidQualB.getResponseId() > 0) {
                                    LOGGER.info("transaction done pcb Qual B");

                                    //get item from sfitem
                                    System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                    JSONObject paramsQualB = new JSONObject();
                                    paramsQualB.put("pkID", sfpkidB);
                                    JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                                    int itemSfBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                                    String versionSfA = getItemByParamB.getJSONObject(0).getString("Version");

                                    //delete sfitem
                                    JSONObject paramsdeleteSfB = new JSONObject();
                                    paramsdeleteSfB.put("pkID", itemSfBpkid);
                                    paramsdeleteSfB.put("version", versionSfA);
                                    SPTSResponse deleteB = SPTSWebService.DeleteSFItem(paramsdeleteSfB);
                                } else {
                                    LOGGER.info("transaction failed pcb Qual B");
                                }

                            }

                            if (transC > 0) {
                                //revert transaction for pcb qual c
                                JSONObject paramsC2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsC2.put("dateTime", formattedDate);
                                paramsC2.put("itemsPKID", itemPkidC);
                                paramsC2.put("transType", "20");
                                paramsC2.put("transQty", whship.getPcbCQty());
                                paramsC2.put("remarks", "Cancel shipment to SBN Factory");
                                SPTSResponse TransPkidQualC = SPTSWebService.insertTransaction(paramsC2);
                                System.out.println("TransPkidQualC: " + TransPkidQualC.getResponseId());
                                if (TransPkidQualC.getResponseId() > 0) {
                                    LOGGER.info("transaction done pcb Qual c");

                                    //get item from sfitem
                                    System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                    JSONObject paramsQualC = new JSONObject();
                                    paramsQualC.put("pkID", sfpkidC);
                                    JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                                    int itemSfCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                                    String versionSfc = getItemByParamC.getJSONObject(0).getString("Version");

                                    //delete sfitem
                                    JSONObject paramsdeleteSfC = new JSONObject();
                                    paramsdeleteSfC.put("pkID", itemSfCpkid);
                                    paramsdeleteSfC.put("version", versionSfc);
                                    SPTSResponse deleteC = SPTSWebService.DeleteSFItem(paramsdeleteSfC);
                                } else {
                                    LOGGER.info("transaction failed pcb Qual c");
                                }

                            }

                            if (transCtr > 0) {
                                //revert transaction for pcb qual ctr
                                JSONObject paramsCtr2 = new JSONObject();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String formattedDate = dateFormat.format(date);
                                paramsCtr2.put("dateTime", formattedDate);
                                paramsCtr2.put("itemsPKID", itemPkidCtr);
                                paramsCtr2.put("transType", "20");
                                paramsCtr2.put("transQty", whship.getPcbCtrQty());
                                paramsCtr2.put("remarks", "Cancel shipment to SBN Factory");
                                SPTSResponse TransPkidQualCtr = SPTSWebService.insertTransaction(paramsCtr2);
                                System.out.println("TransPkidQualCtr: " + TransPkidQualCtr.getResponseId());
                                if (TransPkidQualCtr.getResponseId() > 0) {
                                    LOGGER.info("transaction done pcb Qual ctr");

                                    //get item from sfitem
                                    System.out.println("GET SFITEM PCB QUAL Ctr BY PARAM...");
                                    JSONObject paramsQualCtr = new JSONObject();
                                    paramsQualCtr.put("pkID", sfpkidCtr);
                                    JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                                    int itemSfCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                                    String versionSfctr = getItemByParamCtr.getJSONObject(0).getString("Version");

                                    //delete sfitem
                                    JSONObject paramsdeleteSfCtr = new JSONObject();
                                    paramsdeleteSfCtr.put("pkID", itemSfCtrpkid);
                                    paramsdeleteSfCtr.put("version", versionSfctr);
                                    SPTSResponse deleteC = SPTSWebService.DeleteSFItem(paramsdeleteSfCtr);
                                } else {
                                    LOGGER.info("transaction failed pcb Qual ctr");
                                }

                            }

                            //delete whMpList
                            whMpListDAO = new WhMpListDAO();
                            QueryResult queryResultDeleteFailed = whMpListDAO.deleteWhMpList(queryResult.getGeneratedKey());

                            //update statusLog
                            whshipD = new WhShippingDAO();
                            WhShipping shipping = whshipD.getWhShipping(whship.getId());
                            WhStatusLog stat = new WhStatusLog();
                            stat.setRequestId(shipping.getRequestId());
                            stat.setModule("cdars_wh_mp_list");
                            stat.setStatus("Failed to update SPTS");
                            stat.setCreatedBy(userSession.getFullname());
                            stat.setFlag("0");
                            WhStatusLogDAO statD = new WhStatusLogDAO();
                            QueryResult queryResultStat = statD.insertWhStatusLog(stat);

                            String messageError = "Failed to update SPTS. Please re-check!";
                            redirectAttrs.addFlashAttribute("error", messageError);
                            return "redirect:/wh/whShipping/whMpList/add";
                        }
                    }
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                }
            } else {

                String messageError = "Material Pass Number " + mpNo + " already added to the list!";
                redirectAttrs.addFlashAttribute("error", messageError);
            }

        } else {
//            String messageError = "Material Pass Number " + mpNo + " Not Exist!";
            String messageError = "Failed to add Material Pass Number " + mpNo + "!";
            redirectAttrs.addFlashAttribute("error", messageError);
        }
        return "redirect:/wh/whShipping/whMpList/add";
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public String deleteAll(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
//        QueryResult queryResult = whMpListDAO.deleteAllWhMpList();
        QueryResult queryResult = whMpListDAO.deleteAllWhMpListFlag1();
        args = new String[1];
        args[0] = "All data has been ";
        String error = "Unable to delete, please try again!";
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }
//        return "redirect:/wh/whShipping/whMpList";
        return "redirect:/wh/whShipping/whMpList/add";
    }

    @RequestMapping(value = "/changeFlag1", method = RequestMethod.GET)
    public String changeAlltoFlag1(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.updateWhMpListtoFlag1();
        args = new String[1];
        args[0] = "All data has been ";
        String error = "Unable to delete, please try again!";
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }
        //        return "redirect:/wh/whShipping/whMpList";
        return "redirect:/wh/whShipping/whMpList/add";
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String print(
            Model model,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        LOGGER.info("Masuk view 1........");
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/whMpList/viewPackingListPdf", "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping/whMpList/add";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Hardware Packing List From Rel Lab ON Semiconductor to SBN Factory");

        LOGGER.info("send email to person in charge");
        EmailSender emailSender = new EmailSender();
        com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
        user.setFullname("All");
//        String[] to = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};
        String[] to = {"sbnfactory@gmail.com"};

        emailSender.htmlEmailManyTo(
                servletContext,
                user, //user name requestor
                to,
                //                "muhdfaizal@onsemi.com",                                   //to
                "List of Hardware(s) Ready for Sending to SBN Factory", //subject
                "The list of hardware(s) that have been ready for shipment has been made.<br /><br />"
                + "<br /><br /> "
                + "<style>table, th, td {border: 1px solid black;} </style>"
                + "<table style=\"width:100%\">" //tbl
                + "<tr>"
                + "<th>MATERIAL PASS NO</th> "
                + "<th>MATERIAL PASS EXPIRY DATE</th> "
                + "<th>HARDWARE TYPE</th>"
                + "<th>HARDWARE ID</th>"
                + "<th>QUANTITY</th>"
                + "</tr>"
                + table()
                + "</table>"
                + "<br />Thank you." //msg
        );
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewPackingListPdf", method = RequestMethod.GET)
    public ModelAndView viewPackingListPdf(
            Model model
    ) {
        //change flag to 1 -- to remove delete button once print
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.updateWhMpListtoFlag1();

        String username = System.getProperty("user.name");
        if (!"fg79cj".equals(username)) {
            username = "imperial";
        }
        //send csv to hims once the click print button
        EmailSender emailSenderToHIMSSF = new EmailSender();
        com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
        user.setFullname("HIMS SF");
//        String[] to = {"hmsrelon@gmail.com"}; //9/1/16
        String[] to = {"hmsrelontest@gmail.com"};
        emailSenderToHIMSSF.htmlEmailWithAttachment(
                servletContext,
                //                    user name
                user,
                //                    to
                to,
                // attachment file
                new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv"),
                //                    subject
                "New Hardware Shipping from HIMS",
                //                    msg
                "New hardware will be ship to storage factory. "
        );
//
        whMpListDAO = new WhMpListDAO();
        List<WhMpList> packingList = whMpListDAO.getWhMpListListDateDisplay();
        LOGGER.info("send email to person in charge");
        EmailSender emailSender = new EmailSender();
        com.onsemi.cdars.model.User user2 = new com.onsemi.cdars.model.User();
        user2.setFullname("All");
//        String[] to2 = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};
         String[] to2 = {"sbnfactory@gmail.com"};

        emailSender.htmlEmailManyTo(
                servletContext,
                user2, //user name requestor
                to2,
                //                "muhdfaizal@onsemi.com",                                   //to
                "List of Hardware(s) Ready for Sending to SBN Factory", //subject
                "The list of hardware(s) that have been ready for shipment has been made.<br /><br />"
                + "<br /><br /> "
                + "<style>table, th, td {border: 1px solid black;} </style>"
                + "<table style=\"width:100%\">" //tbl
                + "<tr>"
                + "<th>MATERIAL PASS NO</th> "
                + "<th>MATERIAL PASS EXPIRY DATE</th> "
                + "<th>HARDWARE TYPE</th>"
                + "<th>HARDWARE ID</th>"
                + "<th>QUANTITY</th>"
                + "</tr>"
                + table()
                + "</table>"
                + "<br />Thank you." //msg
        );
        return new ModelAndView("packingListPdf", "packingList", packingList);
    }

    private String table() {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplay();
        String materialPassNo = "";
        String materialPassExp = "";
        String hardwareType = "";
        String hardwareId = "";
        String quantity = "";
        String text = "";

        for (int i = 0; i < whMpListList.size(); i++) {
            materialPassNo = whMpListList.get(i).getMpNo();
            materialPassExp = whMpListList.get(i).getViewMpExpiryDate();
            hardwareId = whMpListList.get(i).getHardwareId();
            hardwareType = whMpListList.get(i).getHardwareType();
            quantity = whMpListList.get(i).getQuantity();
            text = text + "<tr align = \"center\">";
            text = text + "<td>" + materialPassNo + "</td>";
            text = text + "<td>" + materialPassExp + "</td>";
            text = text + "<td>" + hardwareType + "</td>";
            text = text + "<td>" + hardwareId + "</td>";
            text = text + "<td>" + quantity + "</td>";
            text = text + "</tr>";
        }
        return text;
    }

    //only available before print the shipping list
    @RequestMapping(value = "/delete/{whMpListId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("whMpListId") String whMpListId
    ) throws IOException {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        WhMpList whMpList = whMpListDAO.getWhMpList(whMpListId);
        whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.deleteWhMpList(whMpListId);
        args = new String[1];
        args[0] = whMpList.getHardwareId() + " - " + whMpList.getMpNo();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));

            WhShippingDAO shipD = new WhShippingDAO();
            WhShipping wh = shipD.getWhShippingMergeWithRequest(whMpList.getWhShipId());
            LOGGER.info("whRequestIDDDDD " + wh.getRequestId());
            LOGGER.info("whMpList.getWhShipId() " + whMpList.getWhShipId());
            LOGGER.info("getRequestEquipmentId " + wh.getRequestEquipmentId());
            //update spts - insert transaction and delete sfitem
            if ("PCB".equals(wh.getRequestEquipmentType())) {
                if (!"0".equals(wh.getPcbAQty())) {
                    System.out.println("GET ITEM PCB A BY PARAM...");
                    JSONObject paramsA = new JSONObject();
                    String itemIDQualA = wh.getPcbA();
                    paramsA.put("itemID", itemIDQualA);
                    JSONArray getItemByParamQualA = SPTSWebService.getItemByParam(paramsA);
                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualA.length());
                    int itempkidQualA = getItemByParamQualA.getJSONObject(0).getInt("PKID");
                    LOGGER.info("itempkidQualA............." + itempkidQualA);

                    //insert transaction spts for pcb qual a
                    JSONObject paramsA2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsA2.put("dateTime", formattedDate);
                    paramsA2.put("itemsPKID", itempkidQualA);
                    paramsA2.put("transType", "20");
                    paramsA2.put("transQty", wh.getPcbAQty());
                    paramsA2.put("remarks", "Cancel shipment to SBN Factory");
                    SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                    System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());

                    if (TransPkidQualA.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual A");

                        WhRequestDAO reqD = new WhRequestDAO();
                        WhRequest reqSf = reqD.getWhRequest(wh.getRequestId());

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                        JSONObject paramsQualA = new JSONObject();
                        paramsQualA.put("pkID", reqSf.getSfpkid());
                        JSONArray getItemByParamA = SPTSWebService.getSFItemByParam(paramsQualA);
                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamA.length());
                        int itemSfApkid = getItemByParamA.getJSONObject(0).getInt("PKID");
                        String versionSfA = getItemByParamA.getJSONObject(0).getString("Version");

                        //delete sfitem
                        JSONObject paramsdeleteSfA = new JSONObject();
                        paramsdeleteSfA.put("pkID", itemSfApkid);
                        paramsdeleteSfA.put("version", versionSfA);
                        SPTSResponse deleteA = SPTSWebService.DeleteSFItem(paramsdeleteSfA);
                        if (deleteA.getStatus()) {
                            System.out.println("Delete Success pcb A: " + itemIDQualA);
                        } else {
                            System.out.println("Delete Failed pcb A: " + itemIDQualA);
                        }

                        //update ship table
                        WhShipping shipUpdate1 = new WhShipping();
                        shipUpdate1.setId(whMpList.getWhShipId());
                        shipUpdate1.setRequestId(wh.getRequestId());
                        shipUpdate1.setStatus("Pending Packing List"); //as requested 2/11/16
                        shipUpdate1.setItempkid(wh.getItempkid());
                        shipUpdate1.setSfpkid("0");
                        WhShippingDAO ushipDD = new WhShippingDAO();
                        QueryResult u1 = ushipDD.updateWhShippingStatusWithItemPkidAndSfpkid(shipUpdate1);

                        //update request table      
                        WhRequestDAO reqUpdateDAO = new WhRequestDAO();
                        WhRequest requpdateBack = reqUpdateDAO.getWhRequest(wh.getRequestId());
                        requpdateBack.setModifiedBy(userSession.getFullname());
                        requpdateBack.setStatus("Pending Packing List"); //as requested 2/11/16
                        requpdateBack.setSfpkid("0");
                        requpdateBack.setId(wh.getRequestId());
                        reqUpdateDAO = new WhRequestDAO();
                        QueryResult ru1 = reqUpdateDAO.updateWhRequestStatusWithSfpkid(requpdateBack);

                        //update statusLog
                        WhStatusLog stat = new WhStatusLog();
                        stat.setRequestId(whMpListId);
                        stat.setModule("cdars_wh_mp_list");
                        stat.setStatus("Deleted from Packing List");
                        stat.setCreatedBy(userSession.getFullname());
                        stat.setFlag("0");
                        WhStatusLogDAO statD = new WhStatusLogDAO();
                        QueryResult queryResultStat = statD.insertWhStatusLog(stat);

                    } else {
                        LOGGER.info("transaction failed pcb Qual A");
                    }
                }
                if (!"0".equals(wh.getPcbBQty())) {
                    System.out.println("GET ITEM PCB B BY PARAM...");
                    JSONObject paramsB = new JSONObject();
                    String itemIDQualB = wh.getPcbB();
                    paramsB.put("itemID", itemIDQualB);
                    JSONArray getItemByParamQualB = SPTSWebService.getItemByParam(paramsB);
                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualB.length());
                    int itempkidQualB = getItemByParamQualB.getJSONObject(0).getInt("PKID");
                    LOGGER.info("itempkidQualB............." + itempkidQualB);

                    //                        //insert transaction spts for pcb qual b
                    JSONObject paramsB2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsB2.put("dateTime", formattedDate);
                    paramsB2.put("itemsPKID", itempkidQualB);
                    paramsB2.put("transType", "20");
                    paramsB2.put("transQty", wh.getPcbBQty());
                    paramsB2.put("remarks", "Cancel shipment to SBN Factory");
                    SPTSResponse TransPkidQualB = SPTSWebService.insertTransaction(paramsB2);
                    System.out.println("TransPkidQualB: " + TransPkidQualB.getResponseId());

                    if (TransPkidQualB.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual B");

                        WhRequestDAO reqD = new WhRequestDAO();
                        WhRequest reqSf = reqD.getWhRequest(wh.getRequestId());

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                        JSONObject paramsQualB = new JSONObject();
                        paramsQualB.put("pkID", reqSf.getSfpkidB());
                        JSONArray getItemByParamB = SPTSWebService.getSFItemByParam(paramsQualB);
                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamB.length());
                        int itemSfBpkid = getItemByParamB.getJSONObject(0).getInt("PKID");
                        String versionSfB = getItemByParamB.getJSONObject(0).getString("Version");

                        //delete sfitem
                        JSONObject paramsdeleteSfB = new JSONObject();
                        paramsdeleteSfB.put("pkID", itemSfBpkid);
                        paramsdeleteSfB.put("version", versionSfB);
                        SPTSResponse deleteB = SPTSWebService.DeleteSFItem(paramsdeleteSfB);
                        if (deleteB.getStatus()) {
                            System.out.println("Delete Success pcb B: " + itemIDQualB);
                        } else {
                            System.out.println("Delete Failed pcb B: " + itemIDQualB);
                        }

                        //update ship table
                        WhShipping shipUpdate1 = new WhShipping();
                        shipUpdate1.setId(whMpList.getWhShipId());
                        shipUpdate1.setRequestId(wh.getRequestId());
                        shipUpdate1.setStatus("Pending Packing List"); //as requested 2/11/16
                        shipUpdate1.setItempkid(wh.getItempkid());
                        shipUpdate1.setSfpkidB("0");
                        WhShippingDAO ushipDD = new WhShippingDAO();
                        QueryResult u1 = ushipDD.updateWhShippingStatusWithItemPkidAndSfpkidB(shipUpdate1);

                        //update request table      
                        WhRequestDAO reqUpdateDAO = new WhRequestDAO();
                        WhRequest requpdateBack = reqUpdateDAO.getWhRequest(wh.getRequestId());
                        requpdateBack.setModifiedBy(userSession.getFullname());
                        requpdateBack.setStatus("Pending Packing List"); //as requested 2/11/16
                        requpdateBack.setSfpkidB("0");
                        requpdateBack.setId(wh.getRequestId());
                        reqUpdateDAO = new WhRequestDAO();
                        QueryResult ru1 = reqUpdateDAO.updateWhRequestStatusWithSfpkidB(requpdateBack);

                        //update statusLog
                        WhStatusLog stat = new WhStatusLog();
                        stat.setRequestId(whMpListId);
                        stat.setModule("cdars_wh_mp_list");
                        stat.setStatus("Deleted from Packing List");
                        stat.setCreatedBy(userSession.getFullname());
                        stat.setFlag("0");
                        WhStatusLogDAO statD = new WhStatusLogDAO();
                        QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                    } else {
                        LOGGER.info("transaction failed pcb Qual B");
                    }
                }
                if (!"0".equals(wh.getPcbCQty())) {
                    System.out.println("GET ITEM PCB C BY PARAM...");
                    JSONObject paramsC = new JSONObject();
                    String itemIDQualc = wh.getPcbC();
                    paramsC.put("itemID", itemIDQualc);
                    JSONArray getItemByParamQualC = SPTSWebService.getItemByParam(paramsC);
                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualC.length());
                    int itempkidQualC = getItemByParamQualC.getJSONObject(0).getInt("PKID");
                    LOGGER.info("itempkidQualC............." + itempkidQualC);

                    //insert transaction spts for pcb qual c
                    JSONObject paramsC2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsC2.put("dateTime", formattedDate);
                    paramsC2.put("itemsPKID", itempkidQualC);
                    paramsC2.put("transType", "20");
                    paramsC2.put("transQty", wh.getPcbCQty());
                    paramsC2.put("remarks", "Cancel shipment to SBN Factory");
                    SPTSResponse TransPkidQualC = SPTSWebService.insertTransaction(paramsC2);
                    System.out.println("TransPkidQualC: " + TransPkidQualC.getResponseId());

                    if (TransPkidQualC.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual C");

                        WhRequestDAO reqD = new WhRequestDAO();
                        WhRequest reqSf = reqD.getWhRequest(wh.getRequestId());

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                        JSONObject paramsQualC = new JSONObject();
                        paramsQualC.put("pkID", reqSf.getSfpkidC());
                        JSONArray getItemByParamC = SPTSWebService.getSFItemByParam(paramsQualC);
                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamC.length());
                        int itemSfCpkid = getItemByParamC.getJSONObject(0).getInt("PKID");
                        String versionSfC = getItemByParamC.getJSONObject(0).getString("Version");

                        //delete sfitem
                        JSONObject paramsdeleteSfC = new JSONObject();
                        paramsdeleteSfC.put("pkID", itemSfCpkid);
                        paramsdeleteSfC.put("version", versionSfC);
                        SPTSResponse deleteC = SPTSWebService.DeleteSFItem(paramsdeleteSfC);
                        if (deleteC.getStatus()) {
                            System.out.println("Delete Success pcb C: " + itemIDQualc);
                        } else {
                            System.out.println("Delete Failed pcb C: " + itemIDQualc);
                        }

                        //update ship table
                        WhShipping shipUpdate1 = new WhShipping();
                        shipUpdate1.setId(whMpList.getWhShipId());
                        shipUpdate1.setRequestId(wh.getRequestId());
                        shipUpdate1.setStatus("Pending Packing List"); //as requested 2/11/16
                        shipUpdate1.setItempkid(wh.getItempkid());
                        shipUpdate1.setSfpkidC("0");
                        WhShippingDAO ushipDD = new WhShippingDAO();
                        QueryResult u1 = ushipDD.updateWhShippingStatusWithItemPkidAndSfpkidC(shipUpdate1);

                        //update request table      
                        WhRequestDAO reqUpdateDAO = new WhRequestDAO();
                        WhRequest requpdateBack = reqUpdateDAO.getWhRequest(wh.getRequestId());
                        requpdateBack.setModifiedBy(userSession.getFullname());
                        requpdateBack.setStatus("Pending Packing List"); //as requested 2/11/16
                        requpdateBack.setSfpkidC("0");
                        requpdateBack.setId(wh.getRequestId());
                        reqUpdateDAO = new WhRequestDAO();
                        QueryResult ru1 = reqUpdateDAO.updateWhRequestStatusWithSfpkidC(requpdateBack);

                        //update statusLog
                        WhStatusLog stat = new WhStatusLog();
                        stat.setRequestId(whMpListId);
                        stat.setModule("cdars_wh_mp_list");
                        stat.setStatus("Deleted from Packing List");
                        stat.setCreatedBy(userSession.getFullname());
                        stat.setFlag("0");
                        WhStatusLogDAO statD = new WhStatusLogDAO();
                        QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                    } else {
                        LOGGER.info("transaction failed pcb Qual C");
                    }
                }
                if (!"0".equals(wh.getPcbCtrQty())) {
                    System.out.println("GET ITEM PCB Ctr BY PARAM...");
                    JSONObject paramsCtr = new JSONObject();
                    String itemIDQualctr = wh.getPcbCtr();
                    paramsCtr.put("itemID", itemIDQualctr);
                    JSONArray getItemByParamQualCtr = SPTSWebService.getItemByParam(paramsCtr);
                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamQualCtr.length());
                    int itempkidQualCtr = getItemByParamQualCtr.getJSONObject(0).getInt("PKID");
                    LOGGER.info("itempkidQualCtr............." + itempkidQualCtr);

                    //insert transaction spts for pcb qual ctr
                    JSONObject paramsCtr2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsCtr2.put("dateTime", formattedDate);
                    paramsCtr2.put("itemsPKID", itempkidQualCtr);
                    paramsCtr2.put("transType", "20");
                    paramsCtr2.put("transQty", wh.getPcbCtrQty());
                    paramsCtr2.put("remarks", "Cancel shipment to SBN Factory");
                    SPTSResponse TransPkidQualCtr = SPTSWebService.insertTransaction(paramsCtr2);
                    System.out.println("TransPkidQualCtr: " + TransPkidQualCtr.getResponseId());

                    if (TransPkidQualCtr.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Ctr");

                        WhRequestDAO reqD = new WhRequestDAO();
                        WhRequest reqSf = reqD.getWhRequest(wh.getRequestId());

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB CTR BY PARAM...");
                        JSONObject paramsQualCtr = new JSONObject();
                        paramsQualCtr.put("pkID", reqSf.getSfpkidCtr());
                        JSONArray getItemByParamCtr = SPTSWebService.getSFItemByParam(paramsQualCtr);
                        System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParamCtr.length());
                        int itemSfCtrpkid = getItemByParamCtr.getJSONObject(0).getInt("PKID");
                        String versionSfCtr = getItemByParamCtr.getJSONObject(0).getString("Version");

                        //delete sfitem
                        JSONObject paramsdeleteSfCtr = new JSONObject();
                        paramsdeleteSfCtr.put("pkID", itemSfCtrpkid);
                        paramsdeleteSfCtr.put("version", versionSfCtr);
                        SPTSResponse deleteCtr = SPTSWebService.DeleteSFItem(paramsdeleteSfCtr);
                        if (deleteCtr.getStatus()) {
                            System.out.println("Delete Success pcb Ctr: " + itemIDQualctr);
                        } else {
                            System.out.println("Delete Failed pcb Ctr: " + itemIDQualctr);
                        }

                        //update ship table
                        WhShipping shipUpdate1 = new WhShipping();
                        shipUpdate1.setId(whMpList.getWhShipId());
                        shipUpdate1.setRequestId(wh.getRequestId());
                        shipUpdate1.setStatus("Pending Packing List"); //as requested 2/11/16
                        shipUpdate1.setItempkid(wh.getItempkid());
                        shipUpdate1.setSfpkidCtr("0");
                        WhShippingDAO ushipDD = new WhShippingDAO();
                        QueryResult u1 = ushipDD.updateWhShippingStatusWithItemPkidAndSfpkidCtr(shipUpdate1);

                        //update request table      
                        WhRequestDAO reqUpdateDAO = new WhRequestDAO();
                        WhRequest requpdateBack = reqUpdateDAO.getWhRequest(wh.getRequestId());
                        requpdateBack.setModifiedBy(userSession.getFullname());
                        requpdateBack.setStatus("Pending Packing List"); //as requested 2/11/16
                        requpdateBack.setSfpkidCtr("0");
                        requpdateBack.setId(wh.getRequestId());
                        reqUpdateDAO = new WhRequestDAO();
                        QueryResult ru1 = reqUpdateDAO.updateWhRequestStatusWithSfpkidCtr(requpdateBack);

                        //update statusLog
                        WhStatusLog stat = new WhStatusLog();
                        stat.setRequestId(whMpListId);
                        stat.setModule("cdars_wh_mp_list");
                        stat.setStatus("Deleted from Packing List");
                        stat.setCreatedBy(userSession.getFullname());
                        stat.setFlag("0");
                        WhStatusLogDAO statD = new WhStatusLogDAO();
                        QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                    } else {
                        LOGGER.info("transaction failed pcb Ctr");
                    }
                }
            } else {
                System.out.println("GET ITEM BY PARAM...");
                JSONObject params = new JSONObject();
                String itemID = wh.getRequestEquipmentId();
                params.put("itemID", itemID);
                JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
                System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam.length());
                int itempkid = getItemByParam.getJSONObject(0).getInt("PKID");
                LOGGER.info("itempkid............." + itempkid);

                JSONObject params2 = new JSONObject();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String formattedDate = dateFormat.format(date);
                params2.put("dateTime", formattedDate);
                params2.put("itemsPKID", itempkid);
                params2.put("transType", "20");
                params2.put("transQty", wh.getRequestQuantity());
                params2.put("remarks", "Cancel shipment to SBN Factory");
                SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                System.out.println("TransPkid: " + TransPkid.getResponseId());

                if (TransPkid.getResponseId() > 0) {
                    LOGGER.info("transaction done item ");

                    WhRequestDAO reqD = new WhRequestDAO();
                    WhRequest reqSf = reqD.getWhRequest(wh.getRequestId());

                    //get item from sfitem
                    System.out.println("GET SFITEM ITEM BY PARAM...");
                    JSONObject params3 = new JSONObject();
                    params3.put("pkID", reqSf.getSfpkid());
                    JSONArray getItemByParam2 = SPTSWebService.getSFItemByParam(params3);
                    System.out.println("COUNT GET ITEM BY PARAM..." + getItemByParam2.length());
                    int itemSfpkid = getItemByParam2.getJSONObject(0).getInt("PKID");
                    String versionSf = getItemByParam2.getJSONObject(0).getString("Version");

                    //delete sfitem
                    JSONObject paramsdeleteSf = new JSONObject();
                    paramsdeleteSf.put("pkID", itemSfpkid);
                    paramsdeleteSf.put("version", versionSf);
                    SPTSResponse deleteCtr = SPTSWebService.DeleteSFItem(paramsdeleteSf);
                    if (deleteCtr.getStatus()) {
                        System.out.println("Delete Success item: " + itemID);
                    } else {
                        System.out.println("Delete Failed item: " + itemID);
                    }

                    //update ship table
                    WhShipping shipUpdate1 = new WhShipping();
                    shipUpdate1.setId(whMpList.getWhShipId());
                    shipUpdate1.setRequestId(wh.getRequestId());
                    shipUpdate1.setStatus("Pending Packing List"); //as requested 2/11/16
                    shipUpdate1.setItempkid(wh.getItempkid());
                    shipUpdate1.setSfpkid("0");
                    WhShippingDAO ushipDD = new WhShippingDAO();
                    QueryResult u1 = ushipDD.updateWhShippingStatusWithItemPkidAndSfpkid(shipUpdate1);

                    //update request table      
                    WhRequestDAO reqUpdateDAO = new WhRequestDAO();
                    WhRequest requpdateBack = reqUpdateDAO.getWhRequest(wh.getRequestId());
                    requpdateBack.setModifiedBy(userSession.getFullname());
                    requpdateBack.setStatus("Pending Packing List"); //as requested 2/11/16
                    requpdateBack.setSfpkid("0");
                    requpdateBack.setId(wh.getRequestId());
                    reqUpdateDAO = new WhRequestDAO();
                    QueryResult ru1 = reqUpdateDAO.updateWhRequestStatusWithSfpkid(requpdateBack);

                    //update statusLog
                    WhStatusLog stat = new WhStatusLog();
                    stat.setRequestId(whMpListId);
                    stat.setModule("cdars_wh_mp_list");
                    stat.setStatus("Deleted from Packing List");
                    stat.setCreatedBy(userSession.getFullname());
                    stat.setFlag("0");
                    WhStatusLogDAO statD = new WhStatusLogDAO();
                    QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                } else {
                    LOGGER.info("transaction failed item");
                }
            }

            //update csv file
            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }

            File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
            if (file.exists()) {
                LOGGER.info("dh ada header");
                FileWriter fileWriter = null;
                FileReader fileReader = null;

                try {
                    fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv", true);
                    fileReader = new FileReader("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv");
                    String targetLocation = "C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv";

                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String data = bufferedReader.readLine();
                    StringBuilder buff = new StringBuilder();

                    boolean flag = false;
                    int row = 0;
                    while (data != null && flag == false) {
                        LOGGER.info("start reading file..........");
                        buff.append(data).append(System.getProperty("line.separator"));
//                        System.out.println("dataaaaaaaaa : \n" + data);

                        String[] split = data.split(",");
                        WhShippingCsvTemp inventory = new WhShippingCsvTemp(
                                split[0], split[1], split[2],
                                split[3], split[4], split[5],
                                split[6], split[7], split[8],
                                split[9], split[10], split[11],
                                split[12], split[13], split[14],
                                split[15], split[16], split[17],
                                split[18], split[19] //status = [19]
                        );

                        if (split[0].equals(wh.getRequestId())) {
//                            LOGGER.info(row + " : refId found...................." + data);
                            CSV csv = new CSV();
                            csv.open(new File(targetLocation));
                            csv.put(0, row, "0");
                            csv.put(19, row, "Cancelled");
                            csv.save(new File(targetLocation));
                            flag = true;
                        } else {
//                            LOGGER.info("refId not found........" + data);
                            flag = false;
                        }
                        data = bufferedReader.readLine();
                        row++;
                    }
                    bufferedReader.close();
                    fileReader.close();
                } catch (Exception ee) {
                    System.out.println("Error 1 occured while append the fileWriter");
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException ie) {
                        System.out.println("Error 2 occured while closing the fileWriter");
                    }
                }
            } else {
                LOGGER.info("File not exists.................");
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }

//        return "redirect:/wh/whRequest";
        return "redirect:/wh/whShipping/whMpList/add";
    }
}
