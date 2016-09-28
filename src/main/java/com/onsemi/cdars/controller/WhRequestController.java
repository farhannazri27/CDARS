package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.dao.PcbLimitDAO;
import com.onsemi.cdars.dao.WhInventoryDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.PcbLimit;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SptsClass;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
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
@RequestMapping(value = "/wh/whRequest")
@SessionAttributes({"userSession"})
public class WhRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRequestController.class);
    String[] args = {};

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    //File header
//    private static final String HEADER = "id,hardware_type,hardware_id,quantity,material pass number,material pass expiry date,inventory location,requested_by,requested_email,requested_date,remarks";
    private static final String HEADER = "id,hardware_type,hardware_id,pcb a,pcb a qty,pcb b,pcb b qty, pcb c,pcb c qty, pcb ctr,pcb ctr qty,"
            + "quantity,material pass number,material pass expiry date,rack,shelf,requested_by,requested_email,requested_date,remarks";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whRequest(
            Model model, @ModelAttribute UserSession userSession
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        List<WhRequest> whRequestList = whRequestDAO.getWhRequestListWithoutRetrievalAndStatusApproved();
        String groupId = userSession.getGroup();

        model.addAttribute("whRequestList", whRequestList);
        model.addAttribute("groupId", groupId);

        return "whRequest/whRequest";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute UserSession userSession)
            throws IOException {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> requestType = sDAO.getGroupParameterDetailList("", "006");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> equipmentType = sDAO.getGroupParameterDetailList("", "002");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> tray = sDAO.getGroupParameterDetailList("", "013");

        WhInventoryDAO inventory = new WhInventoryDAO();
        List<WhInventory> inventoryListMb = inventory.getWhInventoryMbActiveList("");

        inventory = new WhInventoryDAO();
        List<WhInventory> inventoryListStencil = inventory.getWhInventoryStencilActiveList("");

        inventory = new WhInventoryDAO();
        List<WhInventory> inventoryListTray = inventory.getWhInventoryTrayActiveList("");

        inventory = new WhInventoryDAO();
        List<WhInventory> inventoryListPCB = inventory.getWhInventoryPCBActiveList("");

        PcbLimitDAO pcbDao = new PcbLimitDAO();
        List< PcbLimit> pcbType = pcbDao.getPcbLimitList2("");

        SptsClass sten = new SptsClass();
        List<LinkedHashMap<String, String>> stencil = sten.getSptsItemByParam("Stencil", "0", "1");

        SptsClass bib = new SptsClass();
        List<LinkedHashMap<String, String>> itemListbib = bib.getSptsItemByParam("BIB", "0", "1");

        SptsClass pcb = new SptsClass();
        List<LinkedHashMap<String, String>> itemListpcbQualA = pcb.getSptsItemByParamForPcb("PCB%", "0", "1", "%QUAL A");

        pcb = new SptsClass();
        List<LinkedHashMap<String, String>> itemListpcbQualB = pcb.getSptsItemByParamForPcb("PCB%", "0", "1", "%QUAL B");

        pcb = new SptsClass();
        List<LinkedHashMap<String, String>> itemListpcbQualC = pcb.getSptsItemByParamForPcb("PCB%", "0", "1", "%QUAL C");

        pcb = new SptsClass();
        List<LinkedHashMap<String, String>> itemListpcbCtr = pcb.getSptsItemByParamForPcb("PCB%", "0", "1", "%CONTROL");

        model.addAttribute("StencilItemList", stencil);
        model.addAttribute("bibItemList", itemListbib);
        model.addAttribute("pcbItemListA", itemListpcbQualA);
        model.addAttribute("pcbItemListB", itemListpcbQualB);
        model.addAttribute("pcbItemListC", itemListpcbQualC);
        model.addAttribute("pcbItemListCtr", itemListpcbCtr);

        model.addAttribute("pcbType", pcbType);

        String username = userSession.getFullname();
        model.addAttribute("requestType", requestType);
        model.addAttribute("equipmentType", equipmentType);
        model.addAttribute("inventoryListMb", inventoryListMb);
        model.addAttribute("inventoryListTray", inventoryListTray);
        model.addAttribute("inventoryListStencil", inventoryListStencil);
        model.addAttribute("inventoryListPCB", inventoryListPCB);
        model.addAttribute("tray", tray);
        model.addAttribute("username", username);
        return "whRequest/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            HttpServletRequest request,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String pcbType,
            @RequestParam(required = false) String pcbLimitId,
            @RequestParam(required = false) String inventoryIdMb,
            @RequestParam(required = false) String inventoryIdStencil,
            @RequestParam(required = false) String inventoryIdTray,
            @RequestParam(required = false) String inventoryIdPcb,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String equipmentIdpcbA,
            @RequestParam(required = false) String pcbAQty,
            @RequestParam(required = false) String equipmentIdpcbB,
            @RequestParam(required = false) String pcbBQty,
            @RequestParam(required = false) String equipmentIdpcbC,
            @RequestParam(required = false) String pcbCQty,
            @RequestParam(required = false) String equipmentIdpcbCtr,
            @RequestParam(required = false) String pcbCtrQty,
            @RequestParam(required = false) String equipmentIdMb,
            @RequestParam(required = false) String equipmentIdTray,
            @RequestParam(required = false) String equipmentIdStencil,
            @RequestParam(required = false) String equipmentIdPcb,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String requestedBy,
            @RequestParam(required = false) String remarks) {

        WhRequest whRequest = new WhRequest();
        whRequest.setRequestType(requestType);
        whRequest.setEquipmentType(equipmentType);

        if ("Ship".equals(requestType)) {
            whRequest.setStatus("Waiting for Approval");
            if ("Motherboard".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdMb);
                whRequest.setQuantity("1");
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");
            } else if ("Stencil".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdStencil);
                whRequest.setQuantity("1");
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");
            } else if ("Tray".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdTray);
                whRequest.setQuantity(quantity);
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");
            } else if ("PCB".equals(equipmentType)) {

                String pcbName = equipmentIdpcbA.substring(0, 6);
                whRequest.setEquipmentId(pcbName);
                whRequest.setPcbType(pcbType);
                whRequest.setPcbA(equipmentIdpcbA);
                whRequest.setPcbAQty(pcbAQty);
                whRequest.setPcbB(equipmentIdpcbB);
                whRequest.setPcbBQty(pcbBQty);
                whRequest.setPcbC(equipmentIdpcbC);
                whRequest.setPcbCQty(pcbCQty);
                whRequest.setPcbCtr(equipmentIdpcbCtr);
                whRequest.setPcbCtrQty(pcbCtrQty);

                Integer totalQty = Integer.valueOf(pcbAQty) + Integer.valueOf(pcbBQty) + Integer.valueOf(pcbCQty) + Integer.valueOf(pcbCtrQty);
                whRequest.setQuantity(totalQty.toString());

                PcbLimitDAO pcbDao = new PcbLimitDAO();
                PcbLimit pcb = pcbDao.getPcbLimitByType(pcbType);
                if (totalQty > Integer.valueOf(pcb.getQuantity())) {
                    redirectAttrs.addFlashAttribute("error", "Total of PCB quantity exceeded the PCB limit.Please re-check.");
//                    model.addAttribute("error", "Total of PCB quantity exceeded the PCB limit.Please re-check.");
                    model.addAttribute("whRequest", whRequest);
//                    return "whRequest/add";
                    return "redirect:/wh/whRequest/add";
                }

                String qualB = equipmentIdpcbB.substring(0, 6);
                String qualC = equipmentIdpcbC.substring(0, 6);
                String qualCtr = equipmentIdpcbCtr.substring(0, 6);

                if ((pcbName == null ? qualB != null : !pcbName.equals(qualB)) || !pcbName.equals(qualC) || !pcbName.equals(qualCtr)) {
                    redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
//                    model.addAttribute("error", "Pcb ID is not tally. Please re-check.");
                    model.addAttribute("whRequest", whRequest);
//                    return "whRequest/add";
                    return "redirect:/wh/whRequest/add";
                }
            }
        } else {
            whRequest.setStatus("Requested");
            if ("Motherboard".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdMb);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdMb);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
//                whRequest.setLocation(inventory.getInventoryLocation());
                whRequest.setRack(inventory.getInventoryRack());
                whRequest.setShelf(inventory.getInventoryShelf());
                whRequest.setQuantity(inventory.getQuantity());
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");

            } else if ("Stencil".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdStencil);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdStencil);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setRack(inventory.getInventoryRack());
                whRequest.setShelf(inventory.getInventoryShelf());
                whRequest.setQuantity(inventory.getQuantity());
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");

            } else if ("Tray".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdTray);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdTray);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setRack(inventory.getInventoryRack());
                whRequest.setShelf(inventory.getInventoryShelf());
                whRequest.setQuantity(inventory.getQuantity());
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");

            } else if ("PCB".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdPcb);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdPcb);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setPcbA(inventory.getPcbA());
                whRequest.setPcbAQty(inventory.getPcbAQty());
                whRequest.setPcbB(inventory.getPcbB());
                whRequest.setPcbBQty(inventory.getPcbBQty());
                whRequest.setPcbC(inventory.getPcbC());
                whRequest.setPcbCQty(inventory.getPcbCQty());
                whRequest.setPcbCtr(inventory.getPcbCtr());
                whRequest.setPcbCtrQty(inventory.getPcbCtrQty());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setRack(inventory.getInventoryRack());
                whRequest.setShelf(inventory.getInventoryShelf());
                whRequest.setQuantity(inventory.getQuantity());
            }
        }

        whRequest.setRequestedBy(userSession.getFullname());
        whRequest.setRequestorEmail(userSession.getEmail());
        whRequest.setRemarks(remarks);
        whRequest.setCreatedBy(userSession.getId());

        whRequest.setFlag("0");
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.insertWhRequest(whRequest);
        args = new String[1];
        args[0] = requestType + " - " + equipmentType;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("whRequest", whRequest);
            return "whRequest/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));

//            only send email to approver if requestor want to ship the item
            if ("Ship".equals(requestType)) {
                LOGGER.info("email will be send to approver");

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname(userSession.getFullname());
                emailSender.htmlEmail(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                        "farhannazri27@yahoo.com",
                        "fg79cj@onsemi.com",
                        //                    subject
                        "New Hardware Request from CDARS",
                        //                    msg
                        "New Hardware Request has been added to CDARS. Please go to this link "
                        + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/approval/" + queryResult.getGeneratedKey() + "\">CDARS</a>"
                        + " for approval process."
                );
            }

//            only create csv file and send email to warehouse if requestor want to retrieve hardware
            if ("Retrieve".equals(requestType)) {

                WhRequestDAO whredao = new WhRequestDAO();
                WhRequest whrequest = whredao.getWhRequest(queryResult.getGeneratedKey());

                WhRetrieval whRetrieval = new WhRetrieval();
                whRetrieval.setRequestId(queryResult.getGeneratedKey());
                whRetrieval.setHardwareType(whrequest.getEquipmentType());
                whRetrieval.setHardwareId(whrequest.getEquipmentId());
                whRetrieval.setPcbA(whrequest.getPcbA());
                whRetrieval.setPcbAQty(whrequest.getPcbAQty());
                whRetrieval.setPcbB(whrequest.getPcbB());
                whRetrieval.setPcbBQty(whrequest.getPcbBQty());
                whRetrieval.setPcbC(whrequest.getPcbC());
                whRetrieval.setPcbCQty(whrequest.getPcbCQty());
                whRetrieval.setPcbCtr(whrequest.getPcbCtr());
                whRetrieval.setPcbCtrQty(whrequest.getPcbCtrQty());
                whRetrieval.setHardwareQty(whrequest.getQuantity());
//                whRetrieval.setLocation(whrequest.getLocation());
                whRetrieval.setRack(whrequest.getRack());
                whRetrieval.setShelf(whrequest.getShelf());
                whRetrieval.setMpNo(whrequest.getMpNo());
                whRetrieval.setMpExpiryDate(whrequest.getMpExpiryDate());
                whRetrieval.setRequestedBy(whrequest.getRequestedBy());
                whRetrieval.setRequestedDate(whrequest.getRequestedDate());
                whRetrieval.setRemarks(whrequest.getRemarks());
                whRetrieval.setStatus("Requested");
                whRetrieval.setFlag("0");
                WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
                QueryResult queryResultRetrieval = whRetrievalDAO.insertWhRetrieval(whRetrieval);
                if (queryResultRetrieval.getResult() == 1) {
                    LOGGER.info("done save to retrieval table");
                } else {
                    LOGGER.info("failed save to retrieval table");
                }

                String username = System.getProperty("user.name");
                File file = new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieve.csv");

                if (file.exists()) {
                    //Create List for holding Employee objects
                    LOGGER.info("tiada header");
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieve.csv", true);
                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);

                        WhRequestDAO whdao = new WhRequestDAO();
                        WhRequest wh = whdao.getWhRequest(queryResult.getGeneratedKey());

                        fileWriter.append(queryResult.getGeneratedKey());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getEquipmentType());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getEquipmentId());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbBQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbC());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCtr());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCtrQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getQuantity());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpNo());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpExpiryDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRack());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getShelf());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestedBy());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestorEmail());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestedDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRemarks());
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
                        fileWriter = new FileWriter("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieve.csv");
                        LOGGER.info("no file yet");
                        //Adding the header
                        fileWriter.append(HEADER);

                        //New Line after the header
                        fileWriter.append(LINE_SEPARATOR);

                        WhRequestDAO whdao = new WhRequestDAO();
                        WhRequest wh = whdao.getWhRequest(queryResult.getGeneratedKey());

                        fileWriter.append(queryResult.getGeneratedKey());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getEquipmentType());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getEquipmentId());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbBQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbC());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCtr());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getPcbCtrQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getQuantity());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpNo());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpExpiryDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRack());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getShelf());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestedBy());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestorEmail());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRequestedDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getRemarks());
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

//                send email
                LOGGER.info("send email to warehouse");

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname(userSession.getFullname());
                String[] to = {"hmsrelon@gmail.com"};
                emailSender.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieve.csv"),
                        //                    subject
                        "New Hardware Request from CDARS",
                        //                    msg
                        "New Hardware Request has been added to CDARS"
                );
                return "redirect:/wh/whRetrieval";
            }
//            return "redirect:/wh/whRequest/edit/" + queryResult.getGeneratedKey();
            return "redirect:/wh/whRequest";
        }

    }

    @RequestMapping(value = "/edit/{whRequestId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        PcbLimitDAO pcbLimitD = new PcbLimitDAO();
        int countPcbL = pcbLimitD.getCountPcbType(whRequest.getPcbType());

        if (countPcbL == 1) {
            pcbLimitD = new PcbLimitDAO();
            PcbLimit pcbLimit = pcbLimitD.getPcbLimitByType(whRequest.getPcbType());
            String PcbLimitQty = pcbLimit.getQuantity();
            String PcbLimitType = pcbLimit.getPcbType();
            String PcbLimitTypeAndQty = PcbLimitType + "   Max Qty- " + PcbLimitQty;
            model.addAttribute("PcbLimitTypeAndQty", PcbLimitTypeAndQty);
        } else {
            String PcbLimitType = "";
            model.addAttribute("PcbLimitTypeAndQty", PcbLimitType);
        }

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> requestType = sDAO.getGroupParameterDetailList(whRequest.getRequestType(), "006");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> equipmentType = sDAO.getGroupParameterDetailList(whRequest.getEquipmentType(), "002");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> mb = sDAO.getGroupParameterDetailList(whRequest.getEquipmentId(), "011");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> stencil = sDAO.getGroupParameterDetailList(whRequest.getEquipmentId(), "012");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> tray = sDAO.getGroupParameterDetailList(whRequest.getEquipmentId(), "013");

        model.addAttribute("requestType", requestType);
        model.addAttribute("equipmentType", equipmentType);
        model.addAttribute("mb", mb);
        model.addAttribute("stencil", stencil);
        model.addAttribute("tray", tray);
        model.addAttribute("whRequest", whRequest);

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
        return "whRequest/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String pcbType,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String equipmentIdMb,
            @RequestParam(required = false) String equipmentIdStencil,
            @RequestParam(required = false) String equipmentIdTray,
            @RequestParam(required = false) String equipmentIdPcb,
            @RequestParam(required = false) String pcbA,
            @RequestParam(required = false) String pcbB,
            @RequestParam(required = false) String pcbC,
            @RequestParam(required = false) String pcbCtr,
            @RequestParam(required = false) String pcbAQty,
            @RequestParam(required = false) String pcbBQty,
            @RequestParam(required = false) String pcbCQty,
            @RequestParam(required = false) String pcbCtrQty,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        WhRequest whRequest = new WhRequest();
        whRequest.setId(id);
        whRequest.setRequestType(requestType);
//       
//        whRequest.setLocation(location);
        whRequest.setEquipmentType(equipmentType);

        if ("PCB".equals(equipmentType)) {

            whRequest.setPcbType(pcbType);
            whRequest.setPcbA(pcbA);
            whRequest.setPcbB(pcbB);
            whRequest.setPcbC(pcbC);
            whRequest.setPcbCtr(pcbCtr);
            whRequest.setPcbAQty(pcbAQty);
            whRequest.setPcbBQty(pcbBQty);
            whRequest.setPcbCQty(pcbCQty);
            whRequest.setPcbCtrQty(pcbCtrQty);
            Integer totalQty = Integer.valueOf(pcbAQty) + Integer.valueOf(pcbBQty) + Integer.valueOf(pcbCQty) + Integer.valueOf(pcbCtrQty);
            whRequest.setQuantity(totalQty.toString());

            PcbLimitDAO pcbDao = new PcbLimitDAO();
            PcbLimit pcb = pcbDao.getPcbLimitByType(pcbType);
            if (totalQty > Integer.valueOf(pcb.getQuantity())) {
                redirectAttrs.addFlashAttribute("error", "Total of PCB quantity exceeded the PCB limit.Please re-check.");
                return "redirect:/wh/whRequest/edit/" + id;
            }
        } else {
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setQuantity(quantity);

        }

        //hold for now 27/9/16
//        if (!equipmentIdMb.equals("")) {
//            whRequest.setEquipmentId(equipmentIdMb);
//        } else if (!equipmentIdStencil.equals("")) {
//            whRequest.setEquipmentId(equipmentIdStencil);
//        } else if (!equipmentIdTray.equals("")) {
//            whRequest.setEquipmentId(equipmentIdTray);
//        } else if (!equipmentIdPcb.equals("")) {
//            whRequest.setEquipmentId(equipmentIdPcb);
//        } else {
//            whRequest.setEquipmentId(equipmentId);
//        }
        whRequest.setEquipmentId(equipmentId);
        whRequest.setRemarks(remarks);
        whRequest.setModifiedBy(userSession.getId());
        whRequest.setFlag(flag);

        WhRequestDAO whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.updateWhRequest(whRequest);
        args = new String[1];
        args[0] = requestType + " - " + equipmentType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whRequest/edit/" + id;
    }

    @RequestMapping(value = "/delete/{whRequestId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.deleteWhRequest(whRequestId);
        args = new String[1];
        args[0] = whRequest.getRequestType() + " - " + whRequest.getEquipmentType();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/wh/whRequest";
    }

    @RequestMapping(value = "/view/{whRequestId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("whRequestId") String whRequestId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whRequest/viewWhRequestPdf/" + whRequestId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whRequest";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Warehouse Management - Hardware Request");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewWhRequestPdf/{whRequestId}", method = RequestMethod.GET)
    public ModelAndView viewWhRequestPdf(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        return new ModelAndView("whRequestPdf", "whRequest", whRequest);
    }

    @RequestMapping(value = "/approval/{whRequestId}", method = RequestMethod.GET)
    public String approval(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> approvalStatus = sDAO.getGroupParameterDetailList(whRequest.getFinalApprovedStatus(), "007");
        model.addAttribute("whRequest", whRequest);
        model.addAttribute("approvalStatus", approvalStatus);

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
        return "whRequest/approval";
    }

    @RequestMapping(value = "/approvalupdate", method = RequestMethod.POST)
    public String approvalupdate(
            Model model,
            Locale locale,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String finalApprovedStatus,
            @RequestParam(required = false) String finalApprovedDate,
            @RequestParam(required = false) String remarksApprover,
            @RequestParam(required = false) String modifiedBy,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag
    ) {
        WhRequest whRequest = new WhRequest();
        whRequest.setId(id);
        whRequest.setFinalApprovedStatus(finalApprovedStatus);
        whRequest.setFinalApprovedBy(userSession.getFullname());
        whRequest.setRemarksApprover(remarksApprover);
        whRequest.setStatus(finalApprovedStatus);
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.updateWhRequestForApproval(whRequest);
        args = new String[1];
        args[0] = requestType + " - " + equipmentType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            EmailSender emailSender = new EmailSender();
            whRequestDAO = new WhRequestDAO();
            WhRequest whRequest1 = whRequestDAO.getWhRequest(id);
            String fullname = whRequest1.getRequestedBy();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname(fullname);

            emailSender.htmlEmail(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    "farhannazri27@yahoo.com",
                    //                    subject
                    "Approval Status for New Hardware Request from CDARS",
                    //                    msg
                    "Approval status for New Hardware Request has been made. Please go to this link "
                    + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/edit/" + id + "\">CDARS</a>"
                    + " for approval status checking."
            );

            if ("Approved".equals(finalApprovedStatus)) {

                //save id to table wh_shipping
                WhShipping ship = new WhShipping();

                ship.setRequestId(id);
                ship.setStatus("No Material Pass Number");
                ship.setFlag("0");
                WhShippingDAO whShippingDAO = new WhShippingDAO();
                QueryResult queryResultShip = whShippingDAO.insertWhShipping(ship);
                return "redirect:/wh/whShipping";

            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }

        return "redirect:/wh/whRequest";
    }
}
