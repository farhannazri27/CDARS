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
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import java.io.File;
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
            + "requestor_email,requested_date,remarks,shipping_date";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whMpList(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();
        model.addAttribute("whMpListList", whMpListList);
        model.addAttribute("count", count);
        return "whMpList/whMpList";
    }

    @RequestMapping(value = "/22", method = RequestMethod.GET)
    public String whMpList2(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();

        //count dashboard ship
        WhShippingDAO shipD = new WhShippingDAO();
        int countMpShip = shipD.getCountNoMpNumFlag0();
        shipD = new WhShippingDAO();
        int countBsShip = shipD.getCountBSFlag0();
        shipD = new WhShippingDAO();
        int countTtShip = shipD.getCountTtFlag0();
        shipD = new WhShippingDAO();
        int countShippedShip = shipD.getCountShippedFlag0();
        model.addAttribute("countMpShip", countMpShip);
        model.addAttribute("countBsShip", countBsShip);
        model.addAttribute("countTtShip", countTtShip);
        model.addAttribute("countShippedShip", countShippedShip);

        //count dashboard requests
        WhRequestDAO reqD = new WhRequestDAO();
        int countWaitReq = reqD.getCountWaitingFlag0();
        reqD = new WhRequestDAO();
        int countAppReq = reqD.getCountApprovedFlag0();
        reqD = new WhRequestDAO();
        int countNotAppReq = reqD.getCountNotApprovedFlag0();
        model.addAttribute("countWaitReq", countWaitReq);
        model.addAttribute("countAppReq", countAppReq);
        model.addAttribute("countNotAppReq", countNotAppReq);

        //count dashboard retrieval
        WhRetrievalDAO retD = new WhRetrievalDAO();
        int countReqRet = retD.getCountRequestFlag0();
        retD = new WhRetrievalDAO();
        int countShipRet = retD.getCountShipFlag0();
        retD = new WhRetrievalDAO();
        int countBsVerRet = retD.getCountBsVerifiedFlag0();
        retD = new WhRetrievalDAO();
        int countBsMisRet = retD.getCountBsMismatchedFlag0();
        retD = new WhRetrievalDAO();
        int countBsUnvRet = retD.getCountBsUnverifiedFlag0();
        retD = new WhRetrievalDAO();
        int countBsHoldRet = retD.getCountBsHoldFlag0();
        retD = new WhRetrievalDAO();
        int countTtMisRet = retD.getCountTtMismatchedFlag0();
        retD = new WhRetrievalDAO();
        int countTtUnvRet = retD.getCountTtUnverifiedFlag0();
        retD = new WhRetrievalDAO();
        int countTtHoldRet = retD.getCountTtHoldFlag0();
        model.addAttribute("countReqRet", countReqRet);
        model.addAttribute("countShipRet", countShipRet);
        model.addAttribute("countBsVerRet", countBsVerRet);
        model.addAttribute("countBsMisRet", countBsMisRet);
        model.addAttribute("countBsUnvRet", countBsUnvRet);
        model.addAttribute("countBsHoldRet", countBsHoldRet);
        model.addAttribute("countTtMisRet", countTtMisRet);
        model.addAttribute("countTtUnvRet", countTtUnvRet);
        model.addAttribute("countTtHoldRet", countTtHoldRet);

        model.addAttribute("whMpListList", whMpListList);
        model.addAttribute("count", count);
        return "whMpList/whMpList_2";
    }

    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String whMpList1(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.updateWhMpListtoFlag1();

        whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();

        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();
        model.addAttribute("count", count);
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();

        whMpListDAO = new WhMpListDAO();
        int count = whMpListDAO.getCountWithFlag0();
        model.addAttribute("count", count);
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
        int count = whShipD.getCountMpNoWithStatusinSF(mpNo);
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
//                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("whMpList", whMpList);
//                    return "whMpList/add";
                } else {

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

                    String username = System.getProperty("user.name");
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

//                    hold until testing 7/9/16
                    EmailSender emailSender = new EmailSender();
                    com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                    user.setFullname(userSession.getFullname());
                    String[] to = {"farhannazri27@yahoo.com", "hmsrelon@gmail.com"};
                    emailSender.htmlEmailWithAttachment(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to,
                            //                         "farhannazri27@yahoo.com",
                            // attachment file
                            new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_shipping.csv"),
                            //                    subject
                            "New Hardware Shipping from HIMS",
                            //                    msg
                            "New hardware will be ship to storage factory. "
                    );

                    //update status at shipping list to "Ship"
                    WhShippingDAO shipD = new WhShippingDAO();
                    WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                    WhShipping shipUpdate = new WhShipping();
                    shipUpdate.setId(ship.getId());
                    shipUpdate.setRequestId(ship.getRequestId());
//                    shipUpdate.setStatus("Ship"); //original
                    shipUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                    WhShippingDAO shipDD = new WhShippingDAO();
                    QueryResult u = shipDD.updateWhShippingStatus(shipUpdate);
                    if (u.getResult() == 1) {
                        LOGGER.info("Status Ship updated");
                    } else {
                        LOGGER.info("Status Ship updated failed");
                    }

                    //update status dkt master *request table     
                    WhRequestDAO reqD = new WhRequestDAO();
                    int countReq = reqD.getCountRequestId(ship.getRequestId());
                    if (countReq == 1) {
//                         WhRequest req = reqD.getWhRequest(ship.getRequestId());
                        WhRequest reqUpdate = new WhRequest();
                        reqUpdate.setModifiedBy(userSession.getFullname());
//                        reqUpdate.setStatus("Shipped to Seremban Factory");
                        reqUpdate.setStatus("Pending Shipment to Seremban Factory"); //as requested 2/11/16
                        reqUpdate.setId(ship.getRequestId());
                        reqD = new WhRequestDAO();
                        QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                        if (ru.getResult() == 1) {
                            LOGGER.info("[MpList] - update status at request table done");
                        } else {
                            LOGGER.info("[MpList] - update status at request table failed");
                        }
                    } else {
                        LOGGER.info("[MpList] - requestId not found");
                    }

                    //check ada x duplication mpno utk update spts
                    whMpListDAO = new WhMpListDAO();
                    int countMpNoIfExistFlag0 = whMpListDAO.getCountMpNoWithFlag0(mpNo);
                    if (countMpNoIfExistFlag0 == 1) {

                        //check ad x flag = 1
                        whMpListDAO = new WhMpListDAO();
                        int countMpNoIfExistFlag1 = whMpListDAO.getCountMpNoWithFlag1(mpNo);
                        if (countMpNoIfExistFlag1 == 0) {
//                        //update spts for != pcb
                            if (!"PCB".equals(whship.getRequestEquipmentType()) && !"Tray".equals(whship.getRequestEquipmentType())) {

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
//
//                        //insert sfitem spts
                                JSONObject params3 = new JSONObject();
                                params3.put("itemPKID", itempkid);
                                params3.put("transactionPKID", TransPkid.getResponseId());
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", "");
                                params3.put("sfShelf", "");
                                SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                System.out.println("sfPKID: " + sfPkid.getResponseId());

                            } else if ("Tray".equals(whship.getRequestEquipmentType())) {
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
//
//                        //insert sfitem spts
                                JSONObject params3 = new JSONObject();
                                params3.put("itemPKID", itempkid);
                                params3.put("transactionPKID", TransPkid.getResponseId());
                                params3.put("sfItemStatus", "0");
                                params3.put("sfRack", "");
                                params3.put("sfShelf", "");
                                SPTSResponse sfPkid = SPTSWebService.insertSFItem(params3);
                                System.out.println("sfPKID: " + sfPkid.getResponseId());

                            } else if ("PCB".equals(whship.getRequestEquipmentType())) {
                                if (!"0".equals(whship.getPcbAQty()) && !"".equals(whship.getPcbA())) {
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
//
//                        //insert sfitem spts for pcb qual a
                                    JSONObject paramsA3 = new JSONObject();
                                    paramsA3.put("itemPKID", itempkidQualA);
                                    paramsA3.put("transactionPKID", TransPkidQualA.getResponseId());
                                    paramsA3.put("sfItemStatus", "0");
                                    paramsA3.put("sfRack", "");
                                    paramsA3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualA = SPTSWebService.insertSFItem(paramsA3);
                                    System.out.println("sfPkidQualA: " + sfPkidQualA.getResponseId());
                                }
                                if (!"0".equals(whship.getPcbBQty()) && !"".equals(whship.getPcbB())) {
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
                                }
                                if (!"0".equals(whship.getPcbCQty()) && !"".equals(whship.getPcbC())) {
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
                                }
                                if (!"0".equals(whship.getPcbCtrQty()) && !"".equals(whship.getPcbCtr())) {
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

//                        //insert sfitem spts for pcb qual ctr
                                    JSONObject paramsCtr3 = new JSONObject();
                                    paramsCtr3.put("itemPKID", itempkidQualCtr);
                                    paramsCtr3.put("transactionPKID", TransPkidQualCtr.getResponseId());
                                    paramsCtr3.put("sfItemStatus", "0");
                                    paramsCtr3.put("sfRack", "");
                                    paramsCtr3.put("sfShelf", "");
                                    SPTSResponse sfPkidQualCtr = SPTSWebService.insertSFItem(paramsCtr3);
                                    System.out.println("sfPkidQualCtr: " + sfPkidQualCtr.getResponseId());
                                }
                            }
                        } else {
                            LOGGER.info("data already exist [flag == 1].Will be fail to update spts if proceed.");
                        }

                    } else {
                        LOGGER.info("data already exist. Will be fail to update spts if proceed.");
                    }

                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
//			return "redirect:/whMpList/edit/" + queryResult.getGeneratedKey();
//                    return "redirect:/wh/whShipping/whMpList/add";
//                    return "whMpList/add";
                }
            } else {

                String messageError = "Material Pass Number " + mpNo + " already added to the list!";
                redirectAttrs.addFlashAttribute("error", messageError);
//                model.addAttribute("error", messageError);
//                return "whMpList/add";
            }

        } else {
            String messageError = "Material Pass Number " + mpNo + " Not Exist!";
            redirectAttrs.addFlashAttribute("error", messageError);
//            return "whMpList/add";
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
        QueryResult queryResult = whMpListDAO.deleteAllWhMpList();
        args = new String[1];
        args[0] = "All data has been ";
        String error = "Unable to delete, please try again!";
        if (queryResult.getResult() > 0) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", error);
        }
        return "redirect:/wh/whShipping/whMpList";
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

    @RequestMapping(value = "/email", method = {RequestMethod.GET, RequestMethod.POST})
    public String email(
            Model model,
            HttpServletRequest request,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession
    ) throws IOException {
        LOGGER.info("send email to person in charge");
        EmailSender emailSender = new EmailSender();
        com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
        user.setFullname("All");
        String[] to = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};

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
        return "redirect:/wh/whShipping/whMpList";
    }

    private String table() {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
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
        String[] to = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};

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
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        LOGGER.info("Masuk 1........");
        List<WhMpList> packingList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
//        model.addAttribute("packingList", packingList);
//        List<WhMpList> whMpList = whMpListDAO.getWhMpListMergeWithShippingAndRequestList();
//        WhRequestLog whHistoryList = whRequestDAO.getWhRetLog(whRequestId);
        LOGGER.info("Masuk 2........");
        return new ModelAndView("packingListPdf", "packingList", packingList);
    }
}
