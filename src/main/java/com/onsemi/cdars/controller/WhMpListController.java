package com.onsemi.cdars.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhMpListDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.WhMpList;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/whMpList";
    }

    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String whMpList1(
            Model model
    ) {
        WhMpListDAO whMpListDAO = new WhMpListDAO();
        QueryResult queryResult = whMpListDAO.updateWhMpListtoFlag1();

        whMpListDAO = new WhMpListDAO();
        List<WhMpList> whMpListList = whMpListDAO.getWhMpListListDateDisplayWithFlag0();
        model.addAttribute("whMpListList", whMpListList);
        return "whMpList/whMpList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
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
        //check ad material pass ke tidak dkt shipping.
        WhShippingDAO whShipD = new WhShippingDAO();
        int count = whShipD.getCountMpNo(mpNo);
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
                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("whMpList", whMpList);
                    return "whMpList/add";
                } else {
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
                            "New Hardware Shipping from CDARS",
                            //                    msg
                            "New hardware will be ship to storage factory. "
                    //                                    + "Please go to this link "
                    //                            + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/approval/" + queryResult.getGeneratedKey() + "\">CDARS</a>"
                    //                            + " to check the shipping list."
                    );

                    //update status at shipping list to "Ship"
                    WhShippingDAO shipD = new WhShippingDAO();
                    WhShipping ship = shipD.getWhShippingMergeWithRequestByMpNo(mpNo);

                    WhShipping shipUpdate = new WhShipping();
                    shipUpdate.setId(ship.getId());
                    shipUpdate.setRequestId(ship.getRequestId());
                    shipUpdate.setStatus("Ship");
                    WhShippingDAO shipDD = new WhShippingDAO();
                    QueryResult u = shipDD.updateWhShippingStatus(shipUpdate);
                    if (u.getResult() == 1) {
                        LOGGER.info("Status Ship updated");
                    } else {
                        LOGGER.info("Status Ship updated failed");
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
                                if (!"0".equals(whship.getPcbCtrQty())&& !"".equals(whship.getPcbCtr())) {
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
                    return "redirect:/wh/whShipping/whMpList/add";
//                    return "whMpList/add";
                }
            } else {
                String messageError = "Material Pass Number " + mpNo + " already added to the list!";
                model.addAttribute("error", messageError);
                return "whMpList/add";
            }

        } else {
            String messageError = "Material Pass Number " + mpNo + " Not Exist!";
            model.addAttribute("error", messageError);
            return "whMpList/add";
        }

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
        return "redirect:/wh/whShipping/whMpList";
    }
}
