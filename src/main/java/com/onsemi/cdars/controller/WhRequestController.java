package com.onsemi.cdars.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
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
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSWebService;
import com.onsemi.cdars.tools.SptsClass;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
@RequestMapping(value = "/wh/whRequest")
@SessionAttributes({"userSession"})
public class WhRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRequestController.class);
    String[] args = {};

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    //File header
    private static final String HEADER = "id,hardware_type,hardware_id,quantity,material pass number,material pass expiry date,inventory location,requested_by,requested_email,requested_date,remarks";
    private static final String HEADERArray = "id, request_type, hardware_type, hardware_id, type, quantity, requested_by, requested_date, remarks";

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

//        sDAO = new ParameterDetailsDAO();
//        List<ParameterDetails> mb = sDAO.getGroupParameterDetailList("", "011");
//        sDAO = new ParameterDetailsDAO();
//        List<ParameterDetails> stencil = sDAO.getGroupParameterDetailList("", "012");
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

        SptsClass sten = new SptsClass();
        List<LinkedHashMap<String, String>> stencil = sten.getSptsItemByParam("Stencil", "0");

        SptsClass bib = new SptsClass();
        List<LinkedHashMap<String, String>> itemListbib = bib.getSptsItemByParam("BIB", "0");
        
//        JSONObject params = new JSONObject();
////        params.put("itemName", "SO8FL 15032A Stencil");
//        params.put("itemType", "Stencil");
//        params.put("itemStatus", "0");
//        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
//        List<LinkedHashMap<String, String>> itemList = new ArrayList();
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            JSONObject jsonObject = getItemByParam.getJSONObject(i);
//            LinkedHashMap<String, String> item;
//            ObjectMapper mapper = new ObjectMapper();
//            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//            });
//            itemList.add(item);
//        }
        model.addAttribute("StencilItemList", stencil);
        model.addAttribute("bibItemList", itemListbib);

        String username = userSession.getFullname();
        model.addAttribute("requestType", requestType);
        model.addAttribute("equipmentType", equipmentType);
        model.addAttribute("inventoryListMb", inventoryListMb);
        model.addAttribute("inventoryListTray", inventoryListTray);
        model.addAttribute("inventoryListStencil", inventoryListStencil);
        model.addAttribute("inventoryListPCB", inventoryListPCB);
//        model.addAttribute("mb", mb);
//        model.addAttribute("stencil", stencil);
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
            @RequestParam(required = false) String inventoryIdMb,
            @RequestParam(required = false) String inventoryIdStencil,
            @RequestParam(required = false) String inventoryIdTray,
            @RequestParam(required = false) String inventoryIdPcb,
            @RequestParam(required = false) String equipmentId,
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
            } else if ("Stencil".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdStencil);
            } else if ("Tray".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdTray);
            } else if ("PCB".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdPcb);
            }
//            if (!equipmentIdMb.equals("")) {
//                whRequest.setEquipmentId(equipmentIdMb);
//            } else if (!equipmentIdStencil.equals("")) {
//                whRequest.setEquipmentId(equipmentIdStencil);
//            } else if (!equipmentIdTray.equals("")) {
//                whRequest.setEquipmentId(equipmentIdTray);
//            } else if (!equipmentIdPcb.equals("")) {
//                whRequest.setEquipmentId(equipmentIdPcb);
//            }
        } else {
            whRequest.setStatus("Requested");
            if ("Motherboard".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdMb);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdMb);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setLocation(inventory.getInventoryLocation());

            } else if ("Stencil".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdStencil);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdStencil);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setLocation(inventory.getInventoryLocation());

            } else if ("Tray".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdTray);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdTray);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setLocation(inventory.getInventoryLocation());
            } else if ("PCB".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdPcb);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdPcb);
                whRequest.setEquipmentId(inventory.getEquipmentId());
                whRequest.setMpNo(inventory.getMpNo());
                whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
                whRequest.setLocation(inventory.getInventoryLocation());
            }
//            if (!inventoryIdMb.equals("")) {
//                whRequest.setInventoryId(inventoryIdMb);
//
//               
//            } else if (!inventoryIdStencil.equals("")) {
//                whRequest.setInventoryId(inventoryIdStencil);
//            } else if (!inventoryIdTray.equals("")) {
//                whRequest.setInventoryId(inventoryIdTray);
//            } else if (!inventoryIdPcb.equals("")) {
//                whRequest.setInventoryId(inventoryIdPcb);
//            }
        }
//        whRequest.setInventoryId(inventoryId);

        if (!quantity.equals("")) {
            whRequest.setQuantity(quantity);
        } else {
            whRequest.setQuantity("1");
        }
        whRequest.setRequestedBy(userSession.getFullname());
        whRequest.setRequestorEmail(userSession.getEmail());
        whRequest.setRemarks(remarks);
        whRequest.setCreatedBy(userSession.getId());
//        if ("Retrieve".equals(requestType)) {
//            whRequest.setStatus("Requested");
//        } else {
//            whRequest.setStatus("Waiting for Approval");
//        }
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
                whRetrieval.setHardwareQty(whrequest.getQuantity());
                whRetrieval.setLocation(whrequest.getLocation());
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
                        fileWriter.append(wh.getQuantity());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpNo());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpExpiryDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getLocation());
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
                        fileWriter.append(wh.getQuantity());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpNo());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getMpExpiryDate());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(wh.getLocation());
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
                return "redirect:/wh/whRetrieval/";
            }
//            return "redirect:/wh/whRequest/edit/" + queryResult.getGeneratedKey();
            return "redirect:/wh/whRequest/";
        }

    }

    @RequestMapping(value = "/edit/{whRequestId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);

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
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String equipmentIdMb,
            @RequestParam(required = false) String equipmentIdStencil,
            @RequestParam(required = false) String equipmentIdTray,
            @RequestParam(required = false) String equipmentIdPcb,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String flag
    ) {
        WhRequest whRequest = new WhRequest();
        whRequest.setId(id);
        whRequest.setRequestType(requestType);
        if (!quantity.equals("")) {
            whRequest.setQuantity(quantity);
        } else {
            whRequest.setQuantity("1");
        }
        whRequest.setLocation(location);
        whRequest.setEquipmentType(equipmentType);
        if (!equipmentIdMb.equals("")) {
            whRequest.setEquipmentId(equipmentIdMb);
        } else if (!equipmentIdStencil.equals("")) {
            whRequest.setEquipmentId(equipmentIdStencil);
        } else if (!equipmentIdTray.equals("")) {
            whRequest.setEquipmentId(equipmentIdTray);
        } else if (!equipmentIdPcb.equals("")) {
            whRequest.setEquipmentId(equipmentIdPcb);
        } else {
            whRequest.setEquipmentId(equipmentId);
        }
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
