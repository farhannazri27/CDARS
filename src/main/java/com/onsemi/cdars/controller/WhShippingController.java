package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.WhRequestDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhShippingCsvTemp;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.CSV;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletContext;
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
@RequestMapping(value = "/wh/whShipping")
@SessionAttributes({"userSession"})
public class WhShippingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhShippingController.class);
    String[] args = {};
    String mpNoTemp;
    String idTemp;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whShipping(
            Model model, @ModelAttribute UserSession userSession
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        List<WhShipping> whShippingList = whShippingDAO.getWhShippingListMergeWithRequest();
        String groupId = userSession.getGroup();

        model.addAttribute("whShippingList", whShippingList);
        model.addAttribute("groupId", groupId);
        return "whShipping/whShipping";
    }

    @RequestMapping(value = "/edit/{whShippingId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whShippingId") String whShippingId
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShippingMergeWithRequest(whShippingId);

        //for hardware id label
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whShipping.getRequestId());
        String type = whRequest.getEquipmentType();
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
            String IdLabel = "PCB ID";
            model.addAttribute("IdLabel", IdLabel);
        } else {
            String IdLabel = "Hardware ID";
            model.addAttribute("IdLabel", IdLabel);
        }
        //for check which tab should active
//        if ("No Material Pass Number".equals(whShipping.getStatus())) { //orignial 3/11/16
        if ("Pending Material Pass Number".equals(whShipping.getStatus())) { //as requested 2/11/16
            String mpActive = "active";
            String mpActiveTab = "in active";
            model.addAttribute("mpActive", mpActive);
            model.addAttribute("mpActiveTab", mpActiveTab);
        } else {
            String mpActive = "";
            String mpActiveTab = "";
            model.addAttribute("mpActive", mpActive);
            model.addAttribute("mpActiveTab", mpActiveTab);
        }
//        if ("Not Scan Trip Ticket Yet".equals(whShipping.getStatus())) { //original 3/11/16
        if ("Pending Trip Ticket Scanning".equals(whShipping.getStatus())) { //as requested 2/11/16
            String ttActive = "active";
            String ttActiveTab = "in active";
            model.addAttribute("ttActive", ttActive);
            model.addAttribute("ttActiveTab", ttActiveTab);
        } else {
            String ttActive = "";
            String ttActiveTab = "";
            model.addAttribute("ttActive", ttActive);
            model.addAttribute("ttActiveTab", ttActiveTab);
        }
//        if ("Verified".equals(whShipping.getStatus()) || "Trip Ticket and Barcode Sticker Not Match".equals(whShipping.getStatus()) || "Not Scan Barcode Sticker Yet".equals(whShipping.getStatus()) || "Ship".equals(whShipping.getStatus())) { //original 3/11/16
        if ("Pending Packing List".equals(whShipping.getStatus()) || "Trip Ticket and Barcode Sticker Not Match".equals(whShipping.getStatus())
                || "Pending Box Barcode Scanning".equals(whShipping.getStatus()) || "Pending Shipment to Seremban Factory".equals(whShipping.getStatus())) { //as requested 2/11/16
            String bsActive = "active";
            String bsActiveTab = "in active";
            model.addAttribute("bsActive", bsActive);
            model.addAttribute("bsActiveTab", bsActiveTab);
        } else {
            String bsActive = "";
            String bsActiveTab = "";
            model.addAttribute("bsActive", bsActive);
            model.addAttribute("bsActiveTab", bsActiveTab);
        }

        model.addAttribute("whShipping", whShipping);

        return "whShipping/edit";
    }

    @RequestMapping(value = "/updateMp", method = RequestMethod.POST)
    public String updateMp(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String mpNo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String mpExpiryDate
    ) {
        WhShipping whShipping = new WhShipping();
        whShipping.setId(id);
        whShipping.setMpNo(mpNo);
        whShipping.setMpExpiryDate(mpExpiryDate);
//        if ("No Material Pass Number".equals(status)) {        //original 3/11/16
//            whShipping.setStatus("Not Scan Trip Ticket Yet");  //original 3/11/16
        if ("Pending Material Pass Number".equals(status)) {     //ass requested 2/11/16
            whShipping.setStatus("Pending Trip Ticket Scanning");    //ass requested 2/11/16
        } else {
            whShipping.setStatus(status);
        }
        whShipping.setModifiedBy(userSession.getFullname());
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        int countMp = whShippingDAO.getCountMpNoAndNotEqId(mpNo, id);
        if (countMp != 0) {
            redirectAttrs.addFlashAttribute("error", "Material Pass Number already existed! Please re-check.");
            return "redirect:/wh/whShipping/edit/" + id;
        } else {
            whShippingDAO = new WhShippingDAO();
            QueryResult queryResult = whShippingDAO.updateWhShippingMp(whShipping);
            args = new String[1];
            args[0] = mpNo + " - " + mpExpiryDate;
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                //update statusLog
                whShippingDAO = new WhShippingDAO();
                WhShipping whs = whShippingDAO.getWhShipping(id);
                WhStatusLog stat = new WhStatusLog();
                stat.setRequestId(whs.getRequestId());
                stat.setModule("cdars_wh_shipping");
                stat.setStatus("Material Pass Number inserted");
                stat.setCreatedBy(userSession.getFullname());
                stat.setFlag("0");
                WhStatusLogDAO statD = new WhStatusLogDAO();
                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                if (queryResultStat.getGeneratedKey().equals("0")) {
                    LOGGER.info("[WhShipping] - insert status log failed");
                } else {
                    LOGGER.info("[WhShipping] - insert status log done");
                }

                //update status at master table request
                whShippingDAO = new WhShippingDAO();
                WhShipping ret = whShippingDAO.getWhShipping(id);

                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(ret.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setMpNo(mpNo);
                    reqUpdate.setMpExpiryDate(mpExpiryDate);
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus(ret.getStatus());
                    reqUpdate.setId(ret.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatusAndMpNo(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[whShipping] - update status at request table done");
                    } else {
                        LOGGER.info("[whShipping] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[whShipping] - requestId not found");
                }
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        }

//        return "redirect:/wh/whShipping/viewTripTicket2/" + id;
        return "redirect:/wh/whShipping/edit/" + id;
    }

    @RequestMapping(value = "/updateScanTt", method = RequestMethod.POST)
    public String updateScanTt(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String hardwareBarcode1
    ) {
        WhShipping whShipping = new WhShipping();
        whShipping.setId(id);
        whShipping.setHardwareBarcode1(hardwareBarcode1);
//        if ("Not Scan Trip Ticket Yet".equals(status)) {           //original 3/11/16
//            whShipping.setStatus("Not Scan Barcode Sticker Yet");  //original 3/11/16
        if ("Pending Trip Ticket Scanning".equals(status)) {             //as requested 2/11/16
            whShipping.setStatus("Pending Box Barcode Scanning");    //as requested 2/11/16
        } else {
            whShipping.setStatus(status);
        }
        whShipping.setModifiedBy(userSession.getFullname());
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping whShipping2 = whShippingDAO.getWhShippingMergeWithRequest(id);
        if (hardwareBarcode1.equals(whShipping2.getRequestEquipmentId())) {
            whShippingDAO = new WhShippingDAO();
            QueryResult queryResult = whShippingDAO.updateWhShippingTt(whShipping);
            args = new String[1];
            args[0] = hardwareBarcode1;
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

                //update statusLog
                whShippingDAO = new WhShippingDAO();
                WhShipping whs = whShippingDAO.getWhShipping(id);
                WhStatusLog stat = new WhStatusLog();
                stat.setRequestId(whs.getRequestId());
                stat.setModule("cdars_wh_shipping");
                stat.setStatus("Trip Ticket Verified");
                stat.setCreatedBy(userSession.getFullname());
                stat.setFlag("0");
                WhStatusLogDAO statD = new WhStatusLogDAO();
                QueryResult queryResultStat = statD.insertWhStatusLog(stat);
                if (queryResultStat.getGeneratedKey().equals("0")) {
                    LOGGER.info("[WhShipping] - insert status log failed");
                } else {
                    LOGGER.info("[WhShipping] - insert status log done");
                }

                //update status at master table request
                whShippingDAO = new WhShippingDAO();
                WhShipping ret = whShippingDAO.getWhShipping(id);

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
                        LOGGER.info("[whShipping] - update status at request table done");
                    } else {
                        LOGGER.info("[whShipping] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[whShipping] - requestId not found");
                }

            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
//           
//            return "redirect:/wh/whShipping/viewBarcodeSticker2/" + id;
            return "redirect:/wh/whShipping/edit/" + id;
        } else {
            redirectAttrs.addFlashAttribute("error", "ID Not Match! Please re-check.");
            return "redirect:/wh/whShipping/edit/" + id;
        }

    }

    @RequestMapping(value = "/updateScanBs", method = RequestMethod.POST)
    public String updateScanBs(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            HttpServletRequest request,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String hardwareBarcode1,
            @RequestParam(required = false) String hardwareBarcode2,
            @RequestParam(required = false) String shippingDate,
            @RequestParam(required = false) String mpNoV,
            @RequestParam(required = false) String status
    ) {
        WhShipping whShipping = new WhShipping();
        whShipping.setId(id);
        whShipping.setHardwareBarcode2(hardwareBarcode2);
        if (hardwareBarcode2 == null ? mpNoV == null : hardwareBarcode2.equals(mpNoV)) {
//            whShipping.setStatus("Verified"); //original 3/11/16
            whShipping.setStatus("Pending Packing List"); //as requested 2/11/16
        } else {
            whShipping.setStatus("Trip Ticket and Barcode Sticker Not Match");

            //send email to supervisor if mismatch
            EmailSender emailSender = new EmailSender();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname(userSession.getFullname());
            emailSender.htmlEmail(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    "farhannazri27@yahoo.com",
                    //                    subject
                    "Mismatch Scan for Barcode Sticker Vs Trip Ticket",
                    //                    msg
                    "Mismatch scan for barcode sticker vs trip ticket has been found. Please click this "
                    + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whShipping/edit/" + id + "\">link</a>"
                    + " and re-check to ensure barcode sticker vs trip ticket are match.  "
            );
        }
        whShipping.setModifiedBy(userSession.getFullname());
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        QueryResult queryResult = whShippingDAO.updateWhShippingBs(whShipping);
        args = new String[1];
        args[0] = hardwareBarcode2;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update statusLog
            whShippingDAO = new WhShippingDAO();
            WhShipping whs = whShippingDAO.getWhShipping(id);
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(whs.getRequestId());
            stat.setModule("cdars_wh_shipping");
            stat.setStatus("Barcode Sticker Verified");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhShipping] - insert status log failed");
            } else {
                LOGGER.info("[WhShipping] - insert status log done");
            }

            //update status at master table request
            whShippingDAO = new WhShippingDAO();
            WhShipping ret = whShippingDAO.getWhShipping(id);

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
                    LOGGER.info("[whShipping] - update status at request table done");
                } else {
                    LOGGER.info("[whShipping] - update status at request table failed");
                }
            } else {
                LOGGER.info("[whShipping] - requestId not found");
            }

            return "redirect:/wh/whShipping";
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whShipping/edit/" + id;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String boxId,
            @RequestParam(required = false) String mpNo,
            @RequestParam(required = false) String mpExpiryDate,
            @RequestParam(required = false) String hardwareBarcode1,
            @RequestParam(required = false) String dateScan1,
            @RequestParam(required = false) String hardwareBarcode2,
            @RequestParam(required = false) String dateScan2,
            @RequestParam(required = false) String shippingDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String modifiedDate
    ) {
        WhShipping whShipping = new WhShipping();
        whShipping.setId(id);
        whShipping.setRequestId(requestId);
        whShipping.setBoxId(boxId);
        whShipping.setMpNo(mpNo);
        whShipping.setMpExpiryDate(mpExpiryDate);
        whShipping.setHardwareBarcode1(hardwareBarcode1);
        whShipping.setDateScan1(dateScan1);
        whShipping.setHardwareBarcode2(hardwareBarcode2);
        whShipping.setDateScan2(dateScan2);
        whShipping.setShippingDate(shippingDate);
        whShipping.setStatus(status);
        whShipping.setRemarks(remarks);
        whShipping.setFlag(flag);
        whShipping.setCreatedBy(createdBy);
        whShipping.setCreatedDate(createdDate);
        whShipping.setModifiedBy(modifiedBy);
        whShipping.setModifiedDate(modifiedDate);
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        QueryResult queryResult = whShippingDAO.updateWhShipping(whShipping);
        args = new String[1];
        args[0] = requestId + " - " + boxId;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whShipping/edit/" + id;
    }

    @RequestMapping(value = "/delete/{whShippingId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("whShippingId") String whShippingId
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShipping(whShippingId);
        whShippingDAO = new WhShippingDAO();
        QueryResult queryResult = whShippingDAO.deleteWhShipping(whShippingId);
        args = new String[1];
        args[0] = whShipping.getRequestId() + " - " + whShipping.getBoxId();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
//            stat.setRequestId(whs.getRequestId());
            stat.setRequestId(whShipping.getRequestId());
            stat.setModule("cdars_wh_shipping");
            stat.setStatus("Deleted from Sending to Seremban Factory");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhShipping] - insert status log failed");
            } else {
                LOGGER.info("[WhShipping] - insert status log done");
            }

            //change request to flag 1 - can request again
            WhRequest req = new WhRequest();
            req.setFlag("1");
            req.setId(whShipping.getRequestId());
            WhRequestDAO reqD = new WhRequestDAO();
            QueryResult queryResult1 = reqD.updateWhRequestFlag1(req);
            if (queryResult1.getResult() == 1) {
                LOGGER.info("[Shipping Delete] - update request flag to 1 succeed");
            } else {
                LOGGER.info("[Shipping Delete] - update request flag to 1 failed");
            }

            //update status at master table request
            reqD = new WhRequestDAO();
            int countReq = reqD.getCountRequestId(whShipping.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Cancelled");
                reqUpdate.setId(whShipping.getRequestId());
                reqD = new WhRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[whShipping] - update status at request table done");
                } else {
                    LOGGER.info("[whShipping] - update status at request table failed");
                }
            } else {
                LOGGER.info("[whRetrieval] - requestId not found");
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

                    int row = 0;
                    while (data != null) {
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

                        if (split[0].equals(whShipping.getRequestId())) {
//                            LOGGER.info(row + " : refId found...................." + data);
                            CSV csv = new CSV();
                            csv.open(new File(targetLocation));
                            csv.put(19, row, "Cancelled");
                            csv.save(new File(targetLocation));

                            EmailSender emailSenderSbnFactory = new EmailSender();
                            com.onsemi.cdars.model.User user2 = new com.onsemi.cdars.model.User();
                            user2.setFullname("All");
                            String[] to2 = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};
                            emailSenderSbnFactory.htmlEmailManyTo(
                                    servletContext,
                                    //                    user name
                                    user2,
                                    //                    to
                                    to2,
                                    //                    subject
                                    "Cancellation for Hardware Sending to Seremban Factory",
                                    //                    msg
                                    "CANCELLATION for hardware id : " + whShipping.getRequestEquipmentId() + " / material pass number : " + whShipping.getMpNo() + " from sending to Seremban Factory has been made. Please do not proceed with the shipment."
                                    + "Thank you. "
                            );

                        } else {
//                            LOGGER.info("refId not found........" + data);
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
                if ("In SF Inventory".equals(whShipping.getStatus()) || "Pending Shipment to Seremban Factory".equals(whShipping.getStatus())) {
                    // send email to hmrelon
                    EmailSender emailSender = new EmailSender();
                    com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                    user.setFullname(userSession.getFullname());
                    String[] to = {"hmsrelon@gmail.com"}; //9/11/16
//                    String[] to = {"hmsrelontest@gmail.com"};
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
                            "Cancellation for Sending Hardware to Seremban Factory",
                            //                    msg
                            "Cancellation for sending hardware to Seremban Factory has been made through HIMS RL."
                    );
                }

            } else {
                LOGGER.info("File not exists.................");
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/wh/whShipping";
    }

    @RequestMapping(value = "/view/{whShippingId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/viewWhShippingPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.whShipping");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewTripTicket/{whShippingId}", method = RequestMethod.GET)
    public String viewTripTicket(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/viewWhShippingPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping/edit/" + whShippingId;
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.whShipping");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewTripTicket2/{whShippingId}", method = RequestMethod.GET)
    public String viewTripTicket2(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/viewWhShippingPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping/edit/" + whShippingId;
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.whShipping");
        return "pdf/viewerTripTicket";
    }

    @RequestMapping(value = "/viewWhShippingPdf/{whShippingId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView viewWhShippingPdf(
            Model model,
            @PathVariable("whShippingId") String whShippingId,
            @RequestParam(required = false) String mpNo
    ) {

        LOGGER.info(" @RequestParam(required = false) String mpNo," + whShippingId);
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping test = whShippingDAO.getWhShipping(whShippingId);
        LOGGER.info("String mpNo," + mpNo);
        whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShippingMergeWithRequest(whShippingId);
        return new ModelAndView("whShippingPdf", "whShipping", whShipping);
    }

    @RequestMapping(value = "/viewWhShippingPdf/{whShippingId}/{mpNo}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView viewWhShippingPdfWithMpNo(
            Model model,
            @PathVariable("whShippingId") String whShippingId,
            @PathVariable("mpNo") String mpNo
    ) {
        mpNoTemp = mpNo;
        idTemp = whShippingId;
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        int countMp = whShippingDAO.getCountMpNoAndNotEqId(mpNo, whShippingId);
        if (countMp == 0) {
            WhShipping updateMpNo = new WhShipping();
            updateMpNo.setId(idTemp);
            updateMpNo.setMpNo(mpNoTemp);
            whShippingDAO = new WhShippingDAO();
            QueryResult ru = whShippingDAO.updateWhShippingMpNo(updateMpNo);
        }
        whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShippingMergeWithRequest(whShippingId);
        return new ModelAndView("whShippingPdf", "whShipping", whShipping);
    }

    @RequestMapping(value = "/viewBarcodeSticker/{whShippingId}", method = RequestMethod.GET)
    public String viewBarcodeSticker(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/viewWhBarcodeStickerPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Barcode Sticker");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewBarcodeSticker2/{whShippingId}", method = RequestMethod.GET)
    public String viewBarcodeSticker2(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whShipping/viewWhBarcodeStickerPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whShipping/edit/" + whShippingId;
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Barcode Sticker");
        return "pdf/viewerBarcode";
    }

    @RequestMapping(value = "/viewWhBarcodeStickerPdf/{whShippingId}", method = RequestMethod.GET)
    public ModelAndView viewWhBarcodeStickerPdf(
            Model model,
            @PathVariable("whShippingId") String whShippingId
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShippingMergeWithRequest(whShippingId);
        return new ModelAndView("whBarcodeStickerPdf", "whShipping", whShipping);
    }
}
