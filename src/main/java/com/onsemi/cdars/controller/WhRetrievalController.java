package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.EmailConfigDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhStatusLog;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/wh/whRetrieval")
@SessionAttributes({"userSession"})
public class WhRetrievalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRetrievalController.class);
    String[] args = {};

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    //File header
    private static final String HEADER = "request_id,hardware_type,hardware_id,quantity,rack,shelf,status";
    private static final String HEADERArray = "id, request_type, hardware_type, hardware_id, type, quantity, requested_by, requested_date, remarks";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whRetrieval(
            Model model, @ModelAttribute UserSession userSession
    ) {
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        List<WhRetrieval> whRetrievalList = whRetrievalDAO.getWhRetrievalListWithDateDisplayWithoutStatusClosed();

        String groupId = userSession.getGroup();

        model.addAttribute("groupId", groupId);
        model.addAttribute("whRetrievalList", whRetrievalList);
        return "whRetrieval/whRetrieval";
    }

    @RequestMapping(value = "/edit/{whRetrievalId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("whRetrievalId") String whRetrievalId
    ) {
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        WhRetrieval whRetrieval = whRetrievalDAO.getWhRetrievalWithDateDisplay(whRetrievalId);

        //for label
        String type = whRetrieval.getHardwareType();
        if ("Motherboard".equals(type)) {
            String IdLabel = "Motherboard ID";
            model.addAttribute("IdLabel", IdLabel);
        } else if ("Stencil".equals(type)) {
            String IdLabel = "Stencil ID";
            model.addAttribute("IdLabel", IdLabel);
        } else if ("Tray".equals(type)) {
            String IdLabel = "Tray Type";
            model.addAttribute("IdLabel", IdLabel);
        } else if ("PCB".equals(type)) {
            String IdLabel = "PCB Name";
            model.addAttribute("IdLabel", IdLabel);
        } else {
            String IdLabel = "Hardware ID";
            model.addAttribute("IdLabel", IdLabel);
        }

        //for check which tab should active
//        if ("Barcode Verified".equals(whRetrieval.getStatus()) || "Closed".equals(whRetrieval.getStatus()) || "Closed. Verified By Supervisor".equals(whRetrieval.getStatus())) { //original 3/11/16
        if ("Received".equals(whRetrieval.getStatus()) || "Closed".equals(whRetrieval.getStatus()) || "Closed. Verified By Supervisor".equals(whRetrieval.getStatus())) {

//            LOGGER.info("........ttactive........");
            String ttActive = "active";
            String ttActiveTab = "in active";
            model.addAttribute("ttActive", ttActive);
            model.addAttribute("ttActiveTab", ttActiveTab);
        } else {
//            LOGGER.info("........ttXactive........");
            String ttActive = "";
            String ttActiveTab = "";
            model.addAttribute("ttActive", ttActive);
            model.addAttribute("ttActiveTab", ttActiveTab);
        }
//            if ("Ship".equals(whRetrieval.getStatus()) || "Requested".equals(whRetrieval.getStatus()) || "Barcode Not Match".equals(whRetrieval.getStatus()) || "Barcode Sticker - Verified By Supervisor".equals(whRetrieval.getStatus())) { //original 3/11/16
        if ("Shipped".equals(whRetrieval.getStatus()) || "Requested".equals(whRetrieval.getStatus()) || "Barcode Not Match".equals(whRetrieval.getStatus()) || "Barcode Sticker - Verified By Supervisor".equals(whRetrieval.getStatus())) { //as requested 2/11/16
//            LOGGER.info("........bsactive........");
            String bsActive = "active";
            String bsActiveTab = "in active";
            model.addAttribute("bsActive", bsActive);
            model.addAttribute("bsActiveTab", bsActiveTab);
        } else {
//            LOGGER.info("........bsXactive........");
            String bsActive = "";
            String bsActiveTab = "";
            model.addAttribute("bsActive", bsActive);
            model.addAttribute("bsActiveTab", bsActiveTab);
        }
        if ("Barcode Sticker Scanning Mismatched".equals(whRetrieval.getStatus()) || "Trip Ticket Scanning Mismatched".equals(whRetrieval.getStatus())
                || "Barcode Sticker - Hold By Supervisor".equals(whRetrieval.getStatus()) || "Barcode Sticker - Not Verified By Supervisor".equals(whRetrieval.getStatus())
                || "Trip Ticket - Hold By Supervisor".equals(whRetrieval.getStatus()) || "Trip Ticket - Not Verified By Supervisor".equals(whRetrieval.getStatus())) {
//            LOGGER.info("........disactive........");
            String disActive = "active";
            String disActiveTab = "in active";
            model.addAttribute("disActive", disActive);
            model.addAttribute("disActiveTab", disActiveTab);
        } else {
//            LOGGER.info("........disXactive........");
            String disActive = "";
            String disActiveTab = "";
            model.addAttribute("disActive", disActive);
            model.addAttribute("disActiveTab", disActiveTab);
        }

        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);

        if ("".equals(whRetrieval.getBarcodeDisposition()) || whRetrieval.getBarcodeDisposition() == null) {
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> SupervisorDispositionBarcode = sDAO.getGroupParameterDetailList("", "016");
            model.addAttribute("SupervisorDispositionBarcode", SupervisorDispositionBarcode);
        } else {
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> SupervisorDispositionBarcode = sDAO.getGroupParameterDetailList(whRetrieval.getBarcodeDisposition(), "016");
            model.addAttribute("SupervisorDispositionBarcode", SupervisorDispositionBarcode);
        }
        if ("".equals(whRetrieval.getTtDisposition()) || whRetrieval.getTtDisposition() == null) {
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> SupervisorDispositionTt = sDAO.getGroupParameterDetailList("", "016");
            model.addAttribute("SupervisorDispositionTt", SupervisorDispositionTt);
        } else {
            ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
            List<ParameterDetails> SupervisorDispositionTt = sDAO.getGroupParameterDetailList(whRetrieval.getTtDisposition(), "016");
            model.addAttribute("SupervisorDispositionTt", SupervisorDispositionTt);
        }

        model.addAttribute("whRetrieval", whRetrieval);
        return "whRetrieval/edit";
    }

    @RequestMapping(value = "/updateScanBs", method = RequestMethod.POST)
    public String updateScanBs(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String barcodeVerification,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag
    ) {
        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setBarcodeVerification(barcodeVerification);
        whRetrieval.setBarcodeVerifiedBy(userSession.getFullname());
//        whRetrieval.setStatus("Barcode Verified"); //original 3/11/16
        whRetrieval.setStatus("Received"); //as requested 2/11/16
        whRetrieval.setFlag("0");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateWhRetrievalBarcode(whRetrieval);
        args = new String[1];
        args[0] = barcodeVerification;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update statusLog
            whRetrievalDAO = new WhRetrievalDAO();
            WhRetrieval whRetrie = whRetrievalDAO.getWhRetrieval(id);

            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(whRetrie.getRequestId());
            stat.setModule("cdars_wh_retrieval");
//            stat.setStatus("Barcode Verified"); original 3/11/16
            stat.setStatus("Received"); //as requested 2/11/16
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }

            //update status at master table request
            whRetrievalDAO = new WhRetrievalDAO();
            WhRetrieval ret = whRetrievalDAO.getWhRetrieval(id);

            WhRequestDAO reqD = new WhRequestDAO();
            int countReq = reqD.getCountRequestId(ret.getRequestId());
            if (countReq == 1) {
//                         WhRequest req = reqD.getWhRequest(ship.getRequestId());
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
//                reqUpdate.setStatus("Barcode Verified"); //original 3/11/16
                reqUpdate.setStatus("Received"); //as requested 2/11/16
                reqUpdate.setId(ret.getRequestId());
                reqD = new WhRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[whRetrieval] - update status at request table done");
                } else {
                    LOGGER.info("[whRetrieval] - update status at request table failed");
                }
            } else {
                LOGGER.info("[whRetrieval] - requestId not found");
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }

    @RequestMapping(value = "/updateScanTt", method = RequestMethod.POST)
    public String updateScanTt(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String ttVerification,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag
    ) throws IOException {
        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setTtVerification(ttVerification);
        whRetrieval.setTtVerifiedBy(userSession.getFullname());
        whRetrieval.setStatus("Closed");
        whRetrieval.setFlag("1");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateTt(whRetrieval);
        args = new String[1];
        args[0] = ttVerification;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update statusLog
            whRetrievalDAO = new WhRetrievalDAO();
            WhRetrieval whRetrie = whRetrievalDAO.getWhRetrieval(id);
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(whRetrie.getRequestId());
            stat.setModule("cdars_wh_retrieval");
            stat.setStatus("Closed");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }

            //update status at master table request
            whRetrievalDAO = new WhRetrievalDAO();
            WhRetrieval ret = whRetrievalDAO.getWhRetrieval(id);

            WhRequestDAO reqD = new WhRequestDAO();
            int countReq = reqD.getCountRequestId(ret.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Closed");
                reqUpdate.setId(ret.getRequestId());
                reqD = new WhRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[whRetrieval] - update status at request table done");
                } else {
                    LOGGER.info("[whRetrieval] - update status at request table failed");
                }
            } else {
                LOGGER.info("[whRetrieval] - requestId not found");
            }

            String username = System.getProperty("user.name");
            //SEND EMAIL
            File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv");

            if (file.exists()) {

                LOGGER.info("tiada header");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv", true);
                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);

                    WhRetrievalDAO whdao = new WhRetrievalDAO();
                    WhRetrieval wh = whdao.getWhRetrieval(id);

                    fileWriter.append(wh.getRequestId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareType());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getRack());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getShelf());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getStatus());
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
                    fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv");
                    LOGGER.info("no file yet");
                    //Adding the header
                    fileWriter.append(HEADER);

                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);

                    WhRetrievalDAO whdao = new WhRetrievalDAO();
                    WhRetrieval wh = whdao.getWhRetrieval(id);

                    fileWriter.append(wh.getRequestId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareType());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getHardwareQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getRack());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getShelf());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(wh.getStatus());
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

            LOGGER.info("send email to warehouse");

            EmailSender emailSender = new EmailSender();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname("Sg. Gadut Warehouse");
            String[] to = {"hmsrelon@gmail.com"};
            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    to,
                    // attachment file
                    new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv"),
                    //                    subject
                    "Retrieval From Sg. Gadut Warehouse",
                    //                    msg
                    "Hardware already retrieved. Thank you."
            );
            
            //update inventory - change flag to 1 (hide from list)
            WhRetrievalDAO whdao = new WhRetrievalDAO();
            WhRetrieval wh = whdao.getWhRetrieval(id);

            WhRequestDAO requestd = new WhRequestDAO();
            WhRequest request = requestd.getWhRequest(wh.getRequestId());

            WhInventoryDAO inventoryD = new WhInventoryDAO();
            WhInventory inventory = inventoryD.getWhInventory(request.getInventoryId());

            WhInventory update = new WhInventory();
            update.setId(inventory.getId());
            update.setRequestId(inventory.getRequestId());
            update.setFlag("1");
            WhInventoryDAO updateDAO = new WhInventoryDAO();
            QueryResult updateq = updateDAO.updateWhInventoryFlag(update);
            if (updateq.getResult() == 1) {
                LOGGER.info("update inventory done");
            } else {
                LOGGER.info("update inventory failed");
            }

            //update request - change flag to 1 (item can be request again)
            //update request retrieve
            whdao = new WhRetrievalDAO();
            WhRetrieval wh1 = whdao.getWhRetrieval(id);

            WhRequest requestUpdate = new WhRequest();
            requestUpdate.setFlag("1");
            requestUpdate.setId(wh1.getRequestId());
            WhRequestDAO Update = new WhRequestDAO();
            QueryResult updateRe = Update.updateWhRequestFlag1(requestUpdate);
            if (updateRe.getResult() == 1) {
                LOGGER.info("update request done");
            } else {
                LOGGER.info("update request failed");
            }

            //update request ship
            whdao = new WhRetrievalDAO();
            WhRetrieval wh2 = whdao.getWhRetrieval(id);

            WhRequestDAO request2dao = new WhRequestDAO();
            WhRequest request2 = request2dao.getWhRequest(wh2.getRequestId());

            inventoryD = new WhInventoryDAO();
            WhInventory inventory2 = inventoryD.getWhInventory(request2.getInventoryId());

            request2dao = new WhRequestDAO();
            WhRequest request3 = request2dao.getWhRequest(inventory2.getRequestId());

            WhRequest requestUpdate1 = new WhRequest();
            requestUpdate1.setFlag("1");
            requestUpdate1.setId(request3.getId());
            Update = new WhRequestDAO();
            QueryResult updateRe1 = Update.updateWhRequestFlag1(requestUpdate1);
            if (updateRe1.getResult() == 1) {
                LOGGER.info("update request done");
            } else {
                LOGGER.info("update request failed");
            }

            //update spts - insert transaction and delete sfitem
            if ("PCB".equals(wh.getHardwareType())) {
                if (!"0".equals(wh.getPcbAQty())) {
                    System.out.println("GET ITEM PCB A BY PARAM...");
                    JSONObject paramsA = new JSONObject();
                    String itemIDQualA = wh.getPcbA();
                    paramsA.put("itemID", itemIDQualA);
                    JSONArray getItemByParamQualA = SPTSWebService.getItemByParam(paramsA);
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
                    paramsA2.put("transType", "20");
                    paramsA2.put("transQty", wh.getPcbAQty());
                    paramsA2.put("remarks", "Retrieve from Storage Factory");
                    SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                    System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());

                    if (TransPkidQualA.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual A");

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                        JSONObject paramsQualA = new JSONObject();
                        paramsQualA.put("itemID", itemIDQualA);
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
                    paramsB2.put("remarks", "Retrieve from Storage Factory");
                    SPTSResponse TransPkidQualB = SPTSWebService.insertTransaction(paramsB2);
                    System.out.println("TransPkidQualB: " + TransPkidQualB.getResponseId());

                    if (TransPkidQualB.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual B");

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                        JSONObject paramsQualB = new JSONObject();
                        paramsQualB.put("itemID", itemIDQualB);
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

//                        //insert transaction spts for pcb qual c
                    JSONObject paramsC2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsC2.put("dateTime", formattedDate);
                    paramsC2.put("itemsPKID", itempkidQualC);
                    paramsC2.put("transType", "20");
                    paramsC2.put("transQty", wh.getPcbCQty());
                    paramsC2.put("remarks", "Retrieve from Storage Factory");
                    SPTSResponse TransPkidQualC = SPTSWebService.insertTransaction(paramsC2);
                    System.out.println("TransPkidQualC: " + TransPkidQualC.getResponseId());

                    if (TransPkidQualC.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Qual C");

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                        JSONObject paramsQualC = new JSONObject();
                        paramsQualC.put("itemID", itemIDQualc);
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

//                        //insert transaction spts for pcb qual ctr
                    JSONObject paramsCtr2 = new JSONObject();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String formattedDate = dateFormat.format(date);
                    paramsCtr2.put("dateTime", formattedDate);
                    paramsCtr2.put("itemsPKID", itempkidQualCtr);
                    paramsCtr2.put("transType", "20");
                    paramsCtr2.put("transQty", wh.getPcbCtrQty());
                    paramsCtr2.put("remarks", "Retrieve from Storage Factory");
                    SPTSResponse TransPkidQualCtr = SPTSWebService.insertTransaction(paramsCtr2);
                    System.out.println("TransPkidQualCtr: " + TransPkidQualCtr.getResponseId());

                    if (TransPkidQualCtr.getResponseId() > 0) {
                        LOGGER.info("transaction done pcb Ctr");

                        //get item from sfitem
                        System.out.println("GET SFITEM PCB CTR BY PARAM...");
                        JSONObject paramsQualCtr = new JSONObject();
                        paramsQualCtr.put("itemID", itemIDQualctr);
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
                    } else {
                        LOGGER.info("transaction failed pcb Ctr");
                    }
                }
            } else {
                System.out.println("GET ITEM BY PARAM...");
                JSONObject params = new JSONObject();
                String itemID = wh.getHardwareId();
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
                params2.put("transQty", wh.getHardwareQty());
                params2.put("remarks", "Retrieve from Storage Factory");
                SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                System.out.println("TransPkid: " + TransPkid.getResponseId());

                if (TransPkid.getResponseId() > 0) {
                    LOGGER.info("transaction done item ");

                    //get item from sfitem
                    System.out.println("GET SFITEM ITEM BY PARAM...");
                    JSONObject params3 = new JSONObject();
                    params3.put("itemID", itemID);
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
                } else {
                    LOGGER.info("transaction failed item");
                }
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }

    @RequestMapping(value = "/updateBarcodeAndTtDisposition", method = RequestMethod.POST)
    public String updateBarcodeAndTtDisposition(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String mpNoBySupervisor,
            @RequestParam(required = false) String barcodeDisposition,
            @RequestParam(required = false) String barcodeDispositionRemarks,
            @RequestParam(required = false) String hardwareIdBySupervisor,
            @RequestParam(required = false) String ttDisposition,
            @RequestParam(required = false) String ttDispositionRemarks
    ) throws IOException {
        if (barcodeDisposition != null && ("".equals(ttDisposition) || ttDisposition == null)) {
            LOGGER.info("............barcodeDisposition.........");

            WhRetrieval whRetrieval = new WhRetrieval();
            whRetrieval.setId(id);
            whRetrieval.setRequestId(requestId);
            whRetrieval.setBarcodeDisposition(barcodeDisposition);
            whRetrieval.setBarcodeDispositionBy(userSession.getFullname());
            whRetrieval.setBarcodeDispositionRemarks(barcodeDispositionRemarks);
            if ("Not Verified".equals(barcodeDisposition)) {
                whRetrieval.setStatus("Barcode Sticker - Not Verified By Supervisor");
            } else if ("Hold".equals(barcodeDisposition)) {
                whRetrieval.setStatus("Barcode Sticker - Hold By Supervisor");
            } else {
                whRetrieval.setStatus("Barcode Sticker - Verified By Supervisor");
                whRetrieval.setBarcodeVerification(mpNoBySupervisor);
                whRetrieval.setBarcodeVerifiedBy(userSession.getFullname());
            }
            WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
            QueryResult queryResult = whRetrievalDAO.updateWhRetrievalDispositionForBarcode(whRetrieval);
            args = new String[1];
            args[0] = mpNoBySupervisor;
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                //update statusLog
                WhStatusLog stat = new WhStatusLog();
                stat.setRequestId(requestId);
                stat.setModule("cdars_wh_retrieval");
                if ("Not Verified".equals(barcodeDisposition)) {
                    stat.setStatus("Barcode Sticker - Not Verified By Supervisor");
                } else if ("Hold".equals(barcodeDisposition)) {
                    stat.setStatus("Barcode Sticker - Hold By Supervisor");
                } else {
                    stat.setStatus("Barcode Sticker - Verified By Supervisor");
                }
                stat.setCreatedBy(userSession.getFullname());
                stat.setFlag("0");
                WhStatusLogDAO statD = new WhStatusLogDAO();
                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                if (queryResultStat.getGeneratedKey().equals("0")) {
                    LOGGER.info("[WhRequest] - insert status log failed");
                } else {
                    LOGGER.info("[WhRequest] - insert status log done");
                }

                //update status at master table request
                whRetrievalDAO = new WhRetrievalDAO();
                WhRetrieval ret = whRetrievalDAO.getWhRetrieval(id);

                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(ret.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus(ret.getStatus());
                    reqUpdate.setId(ret.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[whRetrieval] - update status at request table done");
                    } else {
                        LOGGER.info("[whRetrieval] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[whRetrieval] - requestId not found");
                }

            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        } else {
            LOGGER.info("............ttDisposition.........");
            WhRetrieval whRetrieval = new WhRetrieval();
            whRetrieval.setId(id);
            whRetrieval.setRequestId(requestId);
            whRetrieval.setTtDisposition(ttDisposition);
            whRetrieval.setTtDispositionBy(userSession.getFullname());
            whRetrieval.setTtDispositionRemarks(ttDispositionRemarks);
            if ("Not Verified".equals(ttDisposition)) {
                whRetrieval.setStatus("Trip Ticket - Not Verified By Supervisor");
                whRetrieval.setFlag("0");
            } else if ("Hold".equals(ttDisposition)) {
                whRetrieval.setStatus("Trip Ticket - Hold By Supervisor");
                whRetrieval.setFlag("0");
            } else {
                whRetrieval.setStatus("Closed. Verified By Supervisor");
                whRetrieval.setFlag("1");
                whRetrieval.setTtVerification(hardwareIdBySupervisor);
                whRetrieval.setTtVerifiedBy(userSession.getFullname());
            }
            WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
            QueryResult queryResult = whRetrievalDAO.updateWhRetrievalDispositionForTt(whRetrieval);
            args = new String[1];
            args[0] = hardwareIdBySupervisor;
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                //update statusLog
                WhStatusLog stat = new WhStatusLog();
                stat.setRequestId(requestId);
                stat.setModule("cdars_wh_retrieval");
                if ("Not Verified".equals(barcodeDisposition)) {
                    stat.setStatus("Trip Ticket - Not Verified By Supervisor");
                } else if ("Hold".equals(barcodeDisposition)) {
                    stat.setStatus("Trip Ticket - Hold By Supervisor");
                } else {
                    stat.setStatus("Closed. Verified By Supervisor");
                }
                stat.setCreatedBy(userSession.getFullname());
                stat.setFlag("0");
                WhStatusLogDAO statD = new WhStatusLogDAO();
                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                if (queryResultStat.getGeneratedKey().equals("0")) {
                    LOGGER.info("[WhRequest] - insert status log failed");
                } else {
                    LOGGER.info("[WhRequest] - insert status log done");
                }

                //update status at master table request
                whRetrievalDAO = new WhRetrievalDAO();
                WhRetrieval ret = whRetrievalDAO.getWhRetrieval(id);

                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(ret.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus(ret.getStatus());
                    reqUpdate.setId(ret.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[whRetrieval] - update status at request table done");
                    } else {
                        LOGGER.info("[whRetrieval] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[whRetrieval] - requestId not found");
                }

                if ("Verified".equals(ttDisposition)) {

                    String username = System.getProperty("user.name");
                    //SEND EMAIL
                    File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv");

                    if (file.exists()) {

                        LOGGER.info("tiada header");
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv", true);
                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);

                            WhRetrievalDAO whdao = new WhRetrievalDAO();
                            WhRetrieval wh = whdao.getWhRetrieval(id);

                            fileWriter.append(wh.getRequestId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareType());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareQty());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getRack());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getShelf());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getStatus());
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
                            fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv");
                            LOGGER.info("no file yet");
                            //Adding the header
                            fileWriter.append(HEADER);

                            //New Line after the header
                            fileWriter.append(LINE_SEPARATOR);

                            WhRetrievalDAO whdao = new WhRetrievalDAO();
                            WhRetrieval wh = whdao.getWhRetrieval(id);

                            fileWriter.append(wh.getRequestId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareType());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareId());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getHardwareQty());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getRack());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getShelf());
                            fileWriter.append(COMMA_DELIMITER);
                            fileWriter.append(wh.getStatus());
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

                    LOGGER.info("send email to warehouse");

                    EmailSender emailSender = new EmailSender();
                    com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                    user.setFullname("Sg. Gadut Warehouse");
                    String[] to = {"hmsrelon@gmail.com"};
                    emailSender.htmlEmailWithAttachment(
                            servletContext,
                            //                    user name
                            user,
                            //                    to
                            to,
                            // attachment file
                            new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieval_status.csv"),
                            //                    subject
                            "Retrieval From Sg. Gadut Warehouse",
                            //                    msg
                            "Hardware already retrieved. Thank you."
                    );

                    //update inventory - change flag to 1 (hide from list)
                    WhRetrievalDAO whdao = new WhRetrievalDAO();
                    WhRetrieval wh = whdao.getWhRetrieval(id);

                    WhRequestDAO requestd = new WhRequestDAO();
                    WhRequest request = requestd.getWhRequest(wh.getRequestId());

                    WhInventoryDAO inventoryD = new WhInventoryDAO();
                    WhInventory inventory = inventoryD.getWhInventory(request.getInventoryId());

                    WhInventory update = new WhInventory();
                    update.setId(inventory.getId());
                    update.setRequestId(inventory.getRequestId());
                    update.setFlag("1");
                    WhInventoryDAO updateDAO = new WhInventoryDAO();
                    QueryResult updateq = updateDAO.updateWhInventoryFlag(update);
                    if (updateq.getResult() == 1) {
                        LOGGER.info("update inventory done");
                    } else {
                        LOGGER.info("update inventory failed");
                    }

                    //update request - change flag to 1 (item can be request again)
                    //update request retrieve
                    whdao = new WhRetrievalDAO();
                    WhRetrieval wh1 = whdao.getWhRetrieval(id);

                    WhRequest requestUpdate = new WhRequest();
                    requestUpdate.setFlag("1");
                    requestUpdate.setId(wh1.getRequestId());
                    WhRequestDAO Update = new WhRequestDAO();
                    QueryResult updateRe = Update.updateWhRequestFlag1(requestUpdate);
                    if (updateRe.getResult() == 1) {
                        LOGGER.info("update request done");
                    } else {
                        LOGGER.info("update request failed");
                    }

                    //update request ship
                    whdao = new WhRetrievalDAO();
                    WhRetrieval wh2 = whdao.getWhRetrieval(id);

                    WhRequestDAO request2dao = new WhRequestDAO();
                    WhRequest request2 = request2dao.getWhRequest(wh2.getRequestId());

                    inventoryD = new WhInventoryDAO();
                    WhInventory inventory2 = inventoryD.getWhInventory(request2.getInventoryId());

                    request2dao = new WhRequestDAO();
                    WhRequest request3 = request2dao.getWhRequest(inventory2.getRequestId());

                    WhRequest requestUpdate1 = new WhRequest();
                    requestUpdate1.setFlag("1");
                    requestUpdate1.setId(request3.getId());
                    Update = new WhRequestDAO();
                    QueryResult updateRe1 = Update.updateWhRequestFlag1(requestUpdate1);
                    if (updateRe1.getResult() == 1) {
                        LOGGER.info("update request done");
                    } else {
                        LOGGER.info("update request failed");
                    }

                    //update spts - insert transaction and delete sfitem
                    if ("PCB".equals(wh.getHardwareType())) {
                        if (!"0".equals(wh.getPcbAQty())) {
                            System.out.println("GET ITEM PCB A BY PARAM...");
                            JSONObject paramsA = new JSONObject();
                            String itemIDQualA = wh.getPcbA();
                            paramsA.put("itemID", itemIDQualA);
                            JSONArray getItemByParamQualA = SPTSWebService.getItemByParam(paramsA);
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
                            paramsA2.put("transType", "20");
                            paramsA2.put("transQty", wh.getPcbAQty());
                            paramsA2.put("remarks", "Retrieve from Storage Factory");
                            SPTSResponse TransPkidQualA = SPTSWebService.insertTransaction(paramsA2);
                            System.out.println("TransPkidQualA: " + TransPkidQualA.getResponseId());

                            if (TransPkidQualA.getResponseId() > 0) {
                                LOGGER.info("transaction done pcb Qual A");

                                //get item from sfitem
                                System.out.println("GET SFITEM PCB QUAL A BY PARAM...");
                                JSONObject paramsQualA = new JSONObject();
                                paramsQualA.put("itemID", itemIDQualA);
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
                            paramsB2.put("remarks", "Retrieve from Storage Factory");
                            SPTSResponse TransPkidQualB = SPTSWebService.insertTransaction(paramsB2);
                            System.out.println("TransPkidQualB: " + TransPkidQualB.getResponseId());

                            if (TransPkidQualB.getResponseId() > 0) {
                                LOGGER.info("transaction done pcb Qual B");

                                //get item from sfitem
                                System.out.println("GET SFITEM PCB QUAL B BY PARAM...");
                                JSONObject paramsQualB = new JSONObject();
                                paramsQualB.put("itemID", itemIDQualB);
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

//                        //insert transaction spts for pcb qual c
                            JSONObject paramsC2 = new JSONObject();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String formattedDate = dateFormat.format(date);
                            paramsC2.put("dateTime", formattedDate);
                            paramsC2.put("itemsPKID", itempkidQualC);
                            paramsC2.put("transType", "20");
                            paramsC2.put("transQty", wh.getPcbCQty());
                            paramsC2.put("remarks", "Retrieve from Storage Factory");
                            SPTSResponse TransPkidQualC = SPTSWebService.insertTransaction(paramsC2);
                            System.out.println("TransPkidQualC: " + TransPkidQualC.getResponseId());

                            if (TransPkidQualC.getResponseId() > 0) {
                                LOGGER.info("transaction done pcb Qual C");

                                //get item from sfitem
                                System.out.println("GET SFITEM PCB QUAL C BY PARAM...");
                                JSONObject paramsQualC = new JSONObject();
                                paramsQualC.put("itemID", itemIDQualc);
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

//                        //insert transaction spts for pcb qual ctr
                            JSONObject paramsCtr2 = new JSONObject();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String formattedDate = dateFormat.format(date);
                            paramsCtr2.put("dateTime", formattedDate);
                            paramsCtr2.put("itemsPKID", itempkidQualCtr);
                            paramsCtr2.put("transType", "20");
                            paramsCtr2.put("transQty", wh.getPcbCtrQty());
                            paramsCtr2.put("remarks", "Retrieve from Storage Factory");
                            SPTSResponse TransPkidQualCtr = SPTSWebService.insertTransaction(paramsCtr2);
                            System.out.println("TransPkidQualCtr: " + TransPkidQualCtr.getResponseId());

                            if (TransPkidQualCtr.getResponseId() > 0) {
                                LOGGER.info("transaction done pcb Ctr");

                                //get item from sfitem
                                System.out.println("GET SFITEM PCB CTR BY PARAM...");
                                JSONObject paramsQualCtr = new JSONObject();
                                paramsQualCtr.put("itemID", itemIDQualctr);
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
                            } else {
                                LOGGER.info("transaction failed pcb Ctr");
                            }
                        }
                    } else {
                        System.out.println("GET ITEM BY PARAM...");
                        JSONObject params = new JSONObject();
                        String itemID = wh.getHardwareId();
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
                        params2.put("transQty", wh.getHardwareQty());
                        params2.put("remarks", "Retrieve from Storage Factory");
                        SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);
                        System.out.println("TransPkid: " + TransPkid.getResponseId());

                        if (TransPkid.getResponseId() > 0) {
                            LOGGER.info("transaction done item ");

                            //get item from sfitem
                            System.out.println("GET SFITEM ITEM BY PARAM...");
                            JSONObject params3 = new JSONObject();
                            params3.put("itemID", itemID);
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
                        } else {
                            LOGGER.info("transaction failed item");
                        }
                    }

                }

            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String hardwareType,
            @RequestParam(required = false) String hardwareId,
            @RequestParam(required = false) String hardwareQty,
            @RequestParam(required = false) String mpNo,
            @RequestParam(required = false) String mpExpiryDate,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String rack,
            @RequestParam(required = false) String requestedBy,
            @RequestParam(required = false) String requestedDate,
            @RequestParam(required = false) String verifiedBy,
            @RequestParam(required = false) String verifiedDate,
            @RequestParam(required = false) String shippingBy,
            @RequestParam(required = false) String shippingDate,
            @RequestParam(required = false) String receivedDate,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag
    ) {
        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setRequestId(requestId);
        whRetrieval.setHardwareType(hardwareType);
        whRetrieval.setHardwareId(hardwareId);
        whRetrieval.setHardwareQty(hardwareQty);
        whRetrieval.setMpNo(mpNo);
        whRetrieval.setMpExpiryDate(mpExpiryDate);
        whRetrieval.setLocation(location);
        whRetrieval.setShelf(shelf);
        whRetrieval.setRack(rack);
        whRetrieval.setRequestedBy(requestedBy);
        whRetrieval.setRequestedDate(requestedDate);
        whRetrieval.setVerifiedBy(verifiedBy);
        whRetrieval.setVerifiedDate(verifiedDate);
        whRetrieval.setShippingBy(shippingBy);
        whRetrieval.setShippingDate(shippingDate);
        whRetrieval.setReceivedDate(receivedDate);
        whRetrieval.setRemarks(remarks);
        whRetrieval.setStatus(status);
        whRetrieval.setFlag(flag);
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateWhRetrieval(whRetrieval);
        args = new String[1];
        args[0] = requestId + " - " + hardwareType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }

    @RequestMapping(value = "/delete/{whRetrievalId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("whRetrievalId") String whRetrievalId
    ) {
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        WhRetrieval whRetrieval = whRetrievalDAO.getWhRetrieval(whRetrievalId);
        whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.deleteWhRetrieval(whRetrievalId);
        args = new String[1];
        args[0] = whRetrieval.getRequestId() + " - " + whRetrieval.getHardwareType();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(whRetrieval.getRequestId());
            stat.setModule("cdars_wh_retrieval");
            stat.setStatus("Deleted from Retrieval List");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }

            //change request to flag 1 - can request again
            WhRequest req = new WhRequest();
            req.setFlag("1");
            req.setId(whRetrieval.getRequestId());
            WhRequestDAO reqD = new WhRequestDAO();
            QueryResult queryResult1 = reqD.updateWhRequestFlag1(req);
            if (queryResult1.getResult() == 1) {
                LOGGER.info("[Retrieval Delete] - update request flag to 1 succeed");
            } else {
                LOGGER.info("[Retrieval Delete] - update request flag to 1 failed");
            }

            //update status at master table request
            reqD = new WhRequestDAO();
            int countReq = reqD.getCountRequestId(whRetrieval.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Cancelled");
                reqUpdate.setId(whRetrieval.getRequestId());
                reqD = new WhRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[whRetrieval] - update status at request table done");
                } else {
                    LOGGER.info("[whRetrieval] - update status at request table failed");
                }
            } else {
                LOGGER.info("[whRetrieval] - requestId not found");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/wh/whRetrieval";
    }

    @RequestMapping(value = "/view/{whRetrievalId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("whRetrievalId") String whRetrievalId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whRetrieval/viewWhRetrievalPdf/" + whRetrievalId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whRetrieval";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "HW for Retrieval from SBN Factory");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewWhRetrievalPdf/{whRetrievalId}", method = RequestMethod.GET)
    public ModelAndView viewWhRetrievalPdf(
            Model model,
            @PathVariable("whRetrievalId") String whRetrievalId
    ) {
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        WhRetrieval whRetrieval = whRetrievalDAO.getWhRetrievalWithDateDisplay(whRetrievalId);
        return new ModelAndView("whRetrievalPdf", "whRetrieval", whRetrieval);
    }

    @RequestMapping(value = "/emailWrongBs/{id}", method = RequestMethod.GET)
    public String sendEmailWrongBs(
            Model model,
            Locale locale,
            HttpServletRequest request,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id
    ) {

        LOGGER.info("...iddd....." + id);

        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setStatus("Barcode Sticker Scanning Mismatched");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateWhRetrievalStatus(whRetrieval);

        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Email has been sent to supervisor.");
            EmailSender emailSender = new EmailSender();

            WhRetrievalDAO whRetD = new WhRetrievalDAO();
            WhRetrieval whRet = whRetD.getWhRetrieval(id);
            String task = whRet.getHardwareType();

            EmailConfigDAO econfD = new EmailConfigDAO();
            EmailConfig econ = econfD.getEmailConfigByTask(task);
            String email = econ.getEmail();
            String name = econ.getUserName();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname(name);
            emailSender.htmlEmail(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    //                        "farhannazri27@yahoo.com",
                    email,
                    //                    subject
                    "Mismatched Barcode Sticker Scanning",
                    //                    msg
                    "There is a mismatch barcode sticker scanning for hardware retrieval. Please go to this link "
                    + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRetrieval/edit/" + id + "\">HIMS</a>"
                    + " for verification process."
            );
        } else {
            redirectAttrs.addFlashAttribute("error", "Fail to send email.Please contact CDARS Administrator");
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }

    @RequestMapping(value = "/emailWrongTt/{id}", method = RequestMethod.GET)
    public String sendEmailWrongTt(
            Model model,
            Locale locale,
            HttpServletRequest request,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("id") String id
    ) {

        LOGGER.info("...iddd....." + id);

        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setStatus("Trip Ticket Scanning Mismatched");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateWhRetrievalStatus(whRetrieval);

        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", "Email has been sent to supervisor.");
            EmailSender emailSender = new EmailSender();

            WhRetrievalDAO whRetD = new WhRetrievalDAO();
            WhRetrieval whRet = whRetD.getWhRetrieval(id);
            String task = whRet.getHardwareType();

            EmailConfigDAO econfD = new EmailConfigDAO();
            EmailConfig econ = econfD.getEmailConfigByTask(task);
            String email = econ.getEmail();
            String name = econ.getUserName();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname(name);
            emailSender.htmlEmail(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    //                        "farhannazri27@yahoo.com",
                    email,
                    //                    subject
                    "Mismatched Trip Ticket Scanning",
                    //                    msg
                    "There is a mismatch trip ticket scanning for hardware retrieval. Please go to this link "
                    + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRetrieval/edit/" + id + "\">HIMS</a>"
                    + " for verification process."
            );
        } else {
            redirectAttrs.addFlashAttribute("error", "Fail to send email.Please contact HIMS Administrator");
        }
        return "redirect:/wh/whRetrieval/edit/" + id;
    }
}
