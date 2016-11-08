package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.EmailConfigDAO;
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
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.EmailConfig;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.PcbLimit;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.WhStatusLog;
import com.onsemi.cdars.tools.EmailSender;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSWebService;
import com.onsemi.cdars.tools.SystemUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
@RequestMapping(value = "/wh")
@SessionAttributes({"userSession"})
public class WhRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRequestController.class);
    String[] args = {};

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    //File header
//    private static final String HEADER = "id,hardware_type,hardware_id,quantity,material pass number,material pass expiry date,inventory location,requested_by,requested_email,requested_date,remarks";
    private static final String HEADER = "id,hardware_type,hardware_id,retrieval_reason,pcb a,pcb a qty,pcb b,pcb b qty, pcb c,pcb c qty, pcb ctr,pcb ctr qty,"
            + "quantity,material pass number,material pass expiry date,rack,shelf,requested_by,requested_email,requested_date,remarks,status";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/whRequest", method = RequestMethod.GET)
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

    @RequestMapping(value = "/whRequest/add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute UserSession userSession)
            throws IOException {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> requestType = sDAO.getGroupParameterDetailList("", "006");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> equipmentType = sDAO.getGroupParameterDetailList("", "002");

        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> retrievalReason = sDAO.getGroupParameterDetailList("", "017");

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

        JSONObject paramBib = new JSONObject();
        paramBib.put("itemType", "BIB");
        paramBib.put("itemStatus", "0");
        paramBib.put("status", "1");
        JSONArray getItemByParamBib = SPTSWebService.getItemByParam(paramBib);
        List<LinkedHashMap<String, String>> itemListbib = SystemUtil.jsonArrayToList(getItemByParamBib);

        JSONObject paramTray = new JSONObject();
        paramTray.put("itemType", "TRAY");
        paramTray.put("itemStatus", "0");
//        paramTray.put("status", "1");
        JSONArray getItemByParamTray = SPTSWebService.getItemByParam(paramTray);
        List<LinkedHashMap<String, String>> itemListtray = SystemUtil.jsonArrayToList(getItemByParamTray);

        JSONObject paramStencil = new JSONObject();
        paramStencil.put("itemType", "Stencil");
        paramStencil.put("itemStatus", "0");
        paramStencil.put("status", "1");
        JSONArray getItemByParamStencil = SPTSWebService.getItemByParam(paramStencil);
        List<LinkedHashMap<String, String>> stencil = SystemUtil.jsonArrayToList(getItemByParamStencil);

        JSONObject paramPcbA = new JSONObject();
        paramPcbA.put("itemType", "PCB%");
        paramPcbA.put("itemStatus", "0");
//        paramPcbA.put("status", "1");
        paramPcbA.put("itemID", "%QUAL A");
        JSONArray getItemByParamPcbA = SPTSWebService.getItemByParam(paramPcbA);
        List<LinkedHashMap<String, String>> itemListpcbQualA = SystemUtil.jsonArrayToList(getItemByParamPcbA);

        JSONObject paramPcbB = new JSONObject();
        paramPcbB.put("itemType", "PCB%");
        paramPcbB.put("itemStatus", "0");
//        paramPcbB.put("status", "1");
        paramPcbB.put("itemID", "%QUAL B");
        JSONArray getItemByParamPcbB = SPTSWebService.getItemByParam(paramPcbB);
        List<LinkedHashMap<String, String>> itemListpcbQualB = SystemUtil.jsonArrayToList(getItemByParamPcbB);

        JSONObject paramPcbC = new JSONObject();
        paramPcbC.put("itemType", "PCB%");
        paramPcbC.put("itemStatus", "0");
//        paramPcbC.put("status", "1");
        paramPcbC.put("itemID", "%QUAL C");
        JSONArray getItemByParamPcbC = SPTSWebService.getItemByParam(paramPcbC);
        List<LinkedHashMap<String, String>> itemListpcbQualC = SystemUtil.jsonArrayToList(getItemByParamPcbC);

        JSONObject paramPcbCtr = new JSONObject();
        paramPcbCtr.put("itemType", "PCB%");
        paramPcbCtr.put("itemStatus", "0");
//        paramPcbCtr.put("status", "1");
        paramPcbCtr.put("itemID", "%CONTROL");
        JSONArray getItemByParamPcbCtr = SPTSWebService.getItemByParam(paramPcbCtr);
        List<LinkedHashMap<String, String>> itemListpcbCtr = SystemUtil.jsonArrayToList(getItemByParamPcbCtr);

        //        SptsClass bib = new SptsClass();
//        List<LinkedHashMap<String, String>> itemListbib = bib.getSptsItemByParam("BIB", "0", "1");
        model.addAttribute("StencilItemList", stencil);
        model.addAttribute("bibItemList", itemListbib);
        model.addAttribute("trayItemList", itemListtray);
        model.addAttribute("pcbItemListA", itemListpcbQualA);
        model.addAttribute("pcbItemListB", itemListpcbQualB);
        model.addAttribute("pcbItemListC", itemListpcbQualC);
        model.addAttribute("pcbItemListCtr", itemListpcbCtr);

        model.addAttribute("pcbType", pcbType);

        String username = userSession.getFullname();
        model.addAttribute("retrievalReason", retrievalReason);
        model.addAttribute("requestType", requestType);
        model.addAttribute("equipmentType", equipmentType);
        model.addAttribute("inventoryListMb", inventoryListMb);
        model.addAttribute("inventoryListTray", inventoryListTray);
        model.addAttribute("inventoryListStencil", inventoryListStencil);
        model.addAttribute("inventoryListPCB", inventoryListPCB);

        model.addAttribute("username", username);
        return "whRequest/add";
    }

    @RequestMapping(value = "/whRequest/save", method = RequestMethod.POST)
    public String save(
            Model model,
            HttpServletRequest request,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String retrievalReason,
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
//            whRequest.setStatus("Waiting for Approval"); //original 3/11/16
            whRequest.setStatus("Pending Approval"); //as requested 2/11/16
            if ("Motherboard".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdMb);
                whRequest.setQuantity("1");
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");

                //check either item can be request or not
                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountFlag0ForShip(equipmentIdMb);
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }
            } else if ("Stencil".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdStencil);
                whRequest.setQuantity("1");
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");

                //check either item can be request or not
                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountFlag0ForShip(equipmentIdStencil);
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }
            } else if ("Tray".equals(equipmentType)) {
                whRequest.setEquipmentId(equipmentIdTray);
                whRequest.setQuantity(quantity);
                whRequest.setPcbAQty("0");
                whRequest.setPcbBQty("0");
                whRequest.setPcbCQty("0");
                whRequest.setPcbCtrQty("0");
            } else if ("PCB".equals(equipmentType)) {
                String[] pcbName = null;

                if (!"".equals(equipmentIdpcbCtr)) {
                    pcbName = equipmentIdpcbCtr.split(" - ");
                    whRequest.setEquipmentId(pcbName[0]);
                    LOGGER.info("qual CTR..............");
                } else if (!"".equals(equipmentIdpcbA)) {
                    pcbName = equipmentIdpcbA.split(" - ");
                    whRequest.setEquipmentId(pcbName[0]);
                    LOGGER.info("qual A..............");
                } else if (!"".equals(equipmentIdpcbB)) {
                    pcbName = equipmentIdpcbB.split(" - ");
                    whRequest.setEquipmentId(pcbName[0]);
                    LOGGER.info("qual B..............");
                } else if (!"".equals(equipmentIdpcbC)) {
                    pcbName = equipmentIdpcbC.split(" - ");
                    whRequest.setEquipmentId(pcbName[0]);
                    LOGGER.info("qual C..............");
                }
                whRequest.setPcbType(pcbType);
                whRequest.setPcbA(equipmentIdpcbA);
                whRequest.setPcbB(equipmentIdpcbB);
                whRequest.setPcbC(equipmentIdpcbC);
                whRequest.setPcbCtr(equipmentIdpcbCtr);

                String AQty = "";
                String BQty = "";
                String CQty = "";
                String CtrQty = "";
                if ("".equals(pcbAQty)) {
                    whRequest.setPcbAQty("0");
                    AQty = "0";
                    LOGGER.info("pcbAQty1........" + pcbAQty);
                } else {
                    whRequest.setPcbAQty(pcbAQty);
                    AQty = pcbAQty;
                    LOGGER.info("pcbAQty2........" + pcbAQty);
                }
                if ("".equals(pcbBQty)) {
                    whRequest.setPcbBQty("0");
                    BQty = "0";
                    LOGGER.info("pcbBQty1........" + pcbBQty);
                } else {
                    whRequest.setPcbBQty(pcbBQty);
                    BQty = pcbBQty;
                    LOGGER.info("pcbBQty2........" + pcbBQty);
                }
                if ("".equals(pcbCQty)) {
                    whRequest.setPcbCQty("0");
                    CQty = "0";
                    LOGGER.info("pcbCQty1........" + pcbCQty);
                } else {
                    whRequest.setPcbCQty(pcbCQty);
                    CQty = pcbCQty;
                    LOGGER.info("pcbCQty2........" + pcbCQty);
                }
                if ("".equals(pcbCtrQty)) {
                    whRequest.setPcbCtrQty("0");
                    CtrQty = "0";
                    LOGGER.info("pcbCtrQty1........" + pcbCtrQty);
                } else {
                    whRequest.setPcbCtrQty(pcbCtrQty);
                    CtrQty = pcbCtrQty;
                    LOGGER.info("pcbCtrQty2........" + pcbCtrQty);
                }

//                Integer totalQty = Integer.valueOf(pcbAQty) + Integer.valueOf(pcbBQty) + Integer.valueOf(pcbCQty) + Integer.valueOf(pcbCtrQty);
                Integer totalQty = Integer.valueOf(AQty) + Integer.valueOf(BQty) + Integer.valueOf(CQty) + Integer.valueOf(CtrQty);
                whRequest.setQuantity(totalQty.toString());

                PcbLimitDAO pcbDao = new PcbLimitDAO();
                PcbLimit pcb = pcbDao.getPcbLimitByType(pcbType);
                if (totalQty > Integer.valueOf(pcb.getQuantity())) {
                    redirectAttrs.addFlashAttribute("error", "Total of PCB quantity exceeded the PCB limit.Please re-check.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }
                if (!"".equals(equipmentIdpcbCtr)) {
                    String[] qualCtr = equipmentIdpcbCtr.split(" - ");
                    if (!pcbName[0].equals(qualCtr[0])) {
                        redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                        model.addAttribute("whRequest", whRequest);
                        return "redirect:/wh/whRequest/add";
                    }
                }
                if (!"".equals(equipmentIdpcbA)) {
                    String[] qualA = equipmentIdpcbA.split(" - ");
                    if (!pcbName[0].equals(qualA[0])) {
                        redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                        model.addAttribute("whRequest", whRequest);
                        return "redirect:/wh/whRequest/add";
                    }
                }
                if (!"".equals(equipmentIdpcbB)) {
                    String[] qualB = equipmentIdpcbB.split(" - ");
                    if (!pcbName[0].equals(qualB[0])) {
                        redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                        model.addAttribute("whRequest", whRequest);
                        return "redirect:/wh/whRequest/add";
                    }
                }
                if (!"".equals(equipmentIdpcbC)) {
                    String[] qualC = equipmentIdpcbC.split(" - ");
                    if (!pcbName[0].equals(qualC[0])) {
                        redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                        model.addAttribute("whRequest", whRequest);
                        return "redirect:/wh/whRequest/add";
                    }
                }
            }
        } else {
            whRequest.setRetrievalReason(retrievalReason);
//            whRequest.setStatus("New Request for Retrieval");
            whRequest.setStatus("New Request");
            if ("Motherboard".equals(equipmentType)) {
                whRequest.setInventoryId(inventoryIdMb);

                WhInventoryDAO inventoryD = new WhInventoryDAO();
                WhInventory inventory = inventoryD.getWhInventoryActive(inventoryIdMb);
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

                //check either item can be request or not
                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountFlag0ForRetrieve(inventory.getEquipmentId());
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }

                //update status at master table request for ship
                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(inventory.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus("Requested for Retrieval");
                    reqUpdate.setId(inventory.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                    } else {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - requestId not found");
                }

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

                //check either item can be request or not
                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountFlag0ForRetrieve(inventory.getEquipmentId());
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }

                //update status at master table request for ship
                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(inventory.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus("Requested for Retrieval");
                    reqUpdate.setId(inventory.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                    } else {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - requestId not found");
                }

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

                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }

                //update status at master table request for ship
                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(inventory.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus("Requested for Retrieval");
                    reqUpdate.setId(inventory.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                    } else {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - requestId not found");
                }

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

                WhRequestDAO requestda = new WhRequestDAO();
                int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
                if (countitemflag0 > 0) {
                    redirectAttrs.addFlashAttribute("error", "This equipment ID already requested. Please select another equipment ID.");
                    model.addAttribute("whRequest", whRequest);
                    return "redirect:/wh/whRequest/add";
                }

                //update status at master table request for ship
                WhRequestDAO reqD = new WhRequestDAO();
                int countReq = reqD.getCountRequestId(inventory.getRequestId());
                if (countReq == 1) {
                    WhRequest reqUpdate = new WhRequest();
                    reqUpdate.setModifiedBy(userSession.getFullname());
                    reqUpdate.setStatus("Requested for Retrieval");
                    reqUpdate.setId(inventory.getRequestId());
                    reqD = new WhRequestDAO();
                    QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                    if (ru.getResult() == 1) {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                    } else {
                        LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                    }
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - requestId not found");
                }
            }
        }

        whRequest.setRequestedBy(userSession.getFullname());

        EmailConfigDAO econfD = new EmailConfigDAO();
        int count = econfD.getCountTask(equipmentType);
        if (count == 1) {
            econfD = new EmailConfigDAO();
            EmailConfig econ = econfD.getEmailConfigByTask(equipmentType);
            String email = econ.getEmail();
            whRequest.setRequestorEmail(email);//email supervisor base on equipment type 
        } else {
            whRequest.setRequestorEmail(userSession.getEmail()); //email requestor
        }

        whRequest.setRemarks(remarks);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String RemarksLogFull = "[" + formattedDate + "] - " + remarks;
        whRequest.setRemarksLog(RemarksLogFull);
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

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(queryResult.getGeneratedKey());
            stat.setModule("cdars_wh_request");
            if ("Ship".equals(requestType)) {
//                stat.setStatus("Waiting for Approval"); // original 3/11/16
                stat.setStatus("Pending Approval"); //as requested 2/11/16
            } else {
                stat.setStatus("Requested for Retrieval");
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

//            only send email to approver if requestor want to ship the item
            if ("Ship".equals(requestType)) {
                LOGGER.info("email will be send to approver");

                EmailSender emailSender = new EmailSender();
                List<String> a = new ArrayList<String>();

                String emailApprover = "";
                String emaildistList1 = "";
                String emaildistList2 = "";
                String emaildistList3 = "";
                String emaildistList4 = "";

                WhRequestDAO requestD = new WhRequestDAO();
                WhRequest req2 = requestD.getWhRequest(queryResult.getGeneratedKey());
                String requestor = req2.getRequestorEmail();
                if (req2.getRequestorEmail() != null) {
                    a.add(requestor);
                }

                econfD = new EmailConfigDAO();
                int countApprover = econfD.getCountTask("Approver 1");
                if (countApprover == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig approver = econfD.getEmailConfigByTask("Approver 1");
                    emailApprover = approver.getEmail();
                    a.add(emailApprover);
                }
                econfD = new EmailConfigDAO();
                int countDistList1 = econfD.getCountTask("Dist List 1");
                if (countDistList1 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distList1 = econfD.getEmailConfigByTask("Dist List 1");
                    emaildistList1 = distList1.getEmail();
                    a.add(emaildistList1);
                }
                econfD = new EmailConfigDAO();
                int countDistList2 = econfD.getCountTask("Dist List 2");
                if (countDistList2 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distList2 = econfD.getEmailConfigByTask("Dist List 2");
                    emaildistList2 = distList2.getEmail();
                    a.add(emaildistList2);
                }
                econfD = new EmailConfigDAO();
                int countDistList3 = econfD.getCountTask("Dist List 3");
                if (countDistList3 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distList3 = econfD.getEmailConfigByTask("Dist List 3");
                    emaildistList3 = distList3.getEmail();
                    a.add(emaildistList3);
                }
                econfD = new EmailConfigDAO();
                int countDistList4 = econfD.getCountTask("Dist List 4");
                if (countDistList4 == 1) {
                    econfD = new EmailConfigDAO();
                    EmailConfig distList4 = econfD.getEmailConfigByTask("Dist List 4");
                    emaildistList4 = distList4.getEmail();
                    a.add(emaildistList4);
                }
                String[] myArray = new String[a.size()];
                String[] emailTo = a.toArray(myArray);
                LOGGER.info("+++++++emailTo+++++" + Arrays.toString(emailTo));
                //                String[] email = {requestor, emailApprover, emaildistList1, emaildistList2, emaildistList3, emaildistList4};
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname("All");
                emailSender.htmlEmailManyTo(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        //                        "farhannazri27@yahoo.com",
                        //                        "fg79cj@onsemi.com",
                        emailTo,
                        //                    subject
                        "New Hardware Request for Sending to SBN Factory",
                        //                    msg
                        "New Hardware Request for id : " + req2.getEquipmentId() + " has been added to HIMS. Please go to this link "
                        //local pc
                        //                        + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/approval/" + queryResult.getGeneratedKey() + "\">HIMS</a>"
                        //                        + " for approval process."
                        //for production server
                        + "<a href=\"" + request.getScheme() + "://mysed-rel-app03:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/approval/" + queryResult.getGeneratedKey() + "\">HIMS</a>"
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
                whRetrieval.setRetrievalReason(whrequest.getRetrievalReason());
                whRetrieval.setPcbA(whrequest.getPcbA());
                whRetrieval.setPcbAQty(whrequest.getPcbAQty());
                whRetrieval.setPcbB(whrequest.getPcbB());
                whRetrieval.setPcbBQty(whrequest.getPcbBQty());
                whRetrieval.setPcbC(whrequest.getPcbC());
                whRetrieval.setPcbCQty(whrequest.getPcbCQty());
                whRetrieval.setPcbCtr(whrequest.getPcbCtr());
                whRetrieval.setPcbCtrQty(whrequest.getPcbCtrQty());
                whRetrieval.setHardwareQty(whrequest.getQuantity());
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
                        fileWriter.append(wh.getRetrievalReason());
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
                        fileWriter.append("New Request");
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
                        fileWriter.append(wh.getRetrievalReason());
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
                        fileWriter.append("New Request");
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
                LOGGER.info("send email to hms");

                EmailSender emailSender = new EmailSender();
                com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
                user.setFullname(userSession.getFullname());
                String[] to = {"hmsrelon@gmail.com", "hmsrelontest@gmail.com"};
                emailSender.htmlEmailWithAttachment(
                        servletContext,
                        //                    user name
                        user,
                        //                    to
                        to,
                        // attachment file
                        new File("C:\\Users\\" + username + "\\Documents\\CDARS\\cdars_retrieve.csv"),
                        //                    subject
                        "New Hardware Request from HIMS",
                        //                    msg
                        "New Hardware Request has been added to HIMS"
                );

                LOGGER.info("send email to sbnfactory");

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
                        "New Hardware Request for Retrieval from SBN Factory",
                        //                    msg
                        "New request for hardware retrieval from SBN Factory has been made. Please go to the HIMS SF system for verification process. Thank you. "
                );
                return "redirect:/wh/whRetrieval";
            }
//            return "redirect:/wh/whRequest/edit/" + queryResult.getGeneratedKey();
            return "redirect:/wh/whRequest";
        }

    }

    @RequestMapping(value = "/whRequest/edit/{whRequestId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) throws IOException {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        PcbLimitDAO pcbLimitD = new PcbLimitDAO();
        int countPcbL = pcbLimitD.getCountPcbType(whRequest.getPcbType());

        if (countPcbL == 1) {
            pcbLimitD = new PcbLimitDAO();
            PcbLimit pcbLimit = pcbLimitD.getPcbLimitByType(whRequest.getPcbType());
            String PcbLimitQty = pcbLimit.getQuantity();
            model.addAttribute("PcbLimitQty", PcbLimitQty);
        } else {
            String PcbLimitQty = "";
            model.addAttribute("PcbLimitQty", PcbLimitQty);
        }

        PcbLimitDAO pcbDao = new PcbLimitDAO();
        List< PcbLimit> pcbType = pcbDao.getPcbLimitList2(whRequest.getPcbType());

        JSONObject paramBib = new JSONObject();
        paramBib.put("itemType", "BIB");
        paramBib.put("itemStatus", "0");
        paramBib.put("status", "1");
        JSONArray getItemByParamBib = SPTSWebService.getItemByParam(paramBib);
        List<LinkedHashMap<String, String>> itemListbib = SystemUtil.jsonArrayToList(getItemByParamBib);

        JSONObject paramTray = new JSONObject();
        paramTray.put("itemType", "TRAY");
        paramTray.put("itemStatus", "0");
//        paramTray.put("status", "1");
        JSONArray getItemByParamTray = SPTSWebService.getItemByParam(paramTray);
        List<LinkedHashMap<String, String>> itemListtray = SystemUtil.jsonArrayToList(getItemByParamTray);

        JSONObject paramStencil = new JSONObject();
        paramStencil.put("itemType", "Stencil");
        paramStencil.put("itemStatus", "0");
        paramStencil.put("status", "1");
        JSONArray getItemByParamStencil = SPTSWebService.getItemByParam(paramStencil);
        List<LinkedHashMap<String, String>> itemListstencil = SystemUtil.jsonArrayToList(getItemByParamStencil);

        JSONObject paramPcbA = new JSONObject();
        paramPcbA.put("itemType", "PCB%");
        paramPcbA.put("itemStatus", "0");
//        paramPcbA.put("status", "1");
        paramPcbA.put("itemID", "%QUAL A");
        JSONArray getItemByParamPcbA = SPTSWebService.getItemByParam(paramPcbA);
        List<LinkedHashMap<String, String>> itemListpcbQualA = SystemUtil.jsonArrayToList(getItemByParamPcbA);

        JSONObject paramPcbB = new JSONObject();
        paramPcbB.put("itemType", "PCB%");
        paramPcbB.put("itemStatus", "0");
//        paramPcbB.put("status", "1");
        paramPcbB.put("itemID", "%QUAL B");
        JSONArray getItemByParamPcbB = SPTSWebService.getItemByParam(paramPcbB);
        List<LinkedHashMap<String, String>> itemListpcbQualB = SystemUtil.jsonArrayToList(getItemByParamPcbB);

        JSONObject paramPcbC = new JSONObject();
        paramPcbC.put("itemType", "PCB%");
        paramPcbC.put("itemStatus", "0");
//        paramPcbC.put("status", "1");
        paramPcbC.put("itemID", "%QUAL C");
        JSONArray getItemByParamPcbC = SPTSWebService.getItemByParam(paramPcbC);
        List<LinkedHashMap<String, String>> itemListpcbQualC = SystemUtil.jsonArrayToList(getItemByParamPcbC);

        JSONObject paramPcbCtr = new JSONObject();
        paramPcbCtr.put("itemType", "PCB%");
        paramPcbCtr.put("itemStatus", "0");
//        paramPcbCtr.put("status", "1");
        paramPcbCtr.put("itemID", "%CONTROL");
        JSONArray getItemByParamPcbCtr = SPTSWebService.getItemByParam(paramPcbCtr);
        List<LinkedHashMap<String, String>> itemListpcbCtr = SystemUtil.jsonArrayToList(getItemByParamPcbCtr);

        model.addAttribute("pcbType", pcbType);
        model.addAttribute("itemListbib", itemListbib);
        model.addAttribute("itemListtray", itemListtray);
        model.addAttribute("itemListstencil", itemListstencil);
        model.addAttribute("itemListpcbQualA", itemListpcbQualA);
        model.addAttribute("itemListpcbQualB", itemListpcbQualB);
        model.addAttribute("itemListpcbQualC", itemListpcbQualC);
        model.addAttribute("itemListpcbCtr", itemListpcbCtr);
        model.addAttribute("whRequest", whRequest);

        return "whRequest/edit";
    }

    @RequestMapping(value = "/whRequest/update", method = RequestMethod.POST)
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
            @RequestParam(required = false) String equipmentIdpcbA,
            @RequestParam(required = false) String equipmentIdpcbB,
            @RequestParam(required = false) String equipmentIdpcbC,
            @RequestParam(required = false) String equipmentIdpcbCtr,
            @RequestParam(required = false) String pcbAQty,
            @RequestParam(required = false) String pcbBQty,
            @RequestParam(required = false) String pcbCQty,
            @RequestParam(required = false) String pcbCtrQty,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String remarksLog,
            @RequestParam(required = false) String flag
    ) {
        WhRequest whRequest = new WhRequest();
        whRequest.setId(id);
        whRequest.setRequestType(requestType);
        whRequest.setEquipmentType(equipmentType);

        if ("PCB".equals(equipmentType)) {

            String[] pcbName = null;

            if (!"".equals(equipmentIdpcbCtr)) {
                pcbName = equipmentIdpcbCtr.split(" - ");
                whRequest.setEquipmentId(pcbName[0]);
            } else if (!"".equals(equipmentIdpcbA)) {
                pcbName = equipmentIdpcbA.split(" - ");
                whRequest.setEquipmentId(pcbName[0]);
            } else if (!"".equals(equipmentIdpcbB)) {
                pcbName = equipmentIdpcbB.split(" - ");
                whRequest.setEquipmentId(pcbName[0]);
            } else if (!"".equals(equipmentIdpcbC)) {
                pcbName = equipmentIdpcbC.split(" - ");
                whRequest.setEquipmentId(pcbName[0]);
            }

            whRequest.setPcbType(pcbType);
            whRequest.setPcbA(equipmentIdpcbA);
            whRequest.setPcbB(equipmentIdpcbB);
            whRequest.setPcbC(equipmentIdpcbC);
            whRequest.setPcbCtr(equipmentIdpcbCtr);
            String AQty = "";
            String BQty = "";
            String CQty = "";
            String CtrQty = "";
            if ("".equals(pcbAQty)) {
                whRequest.setPcbAQty("0");
                AQty = "0";
                LOGGER.info("pcbAQty1........" + pcbAQty);
            } else {
                whRequest.setPcbAQty(pcbAQty);
                AQty = pcbAQty;
                LOGGER.info("pcbAQty2........" + pcbAQty);
            }
            if ("".equals(pcbBQty)) {
                whRequest.setPcbBQty("0");
                BQty = "0";
                LOGGER.info("pcbBQty1........" + pcbBQty);
            } else {
                whRequest.setPcbBQty(pcbBQty);
                BQty = pcbBQty;
                LOGGER.info("pcbBQty2........" + pcbBQty);
            }
            if ("".equals(pcbCQty)) {
                whRequest.setPcbCQty("0");
                CQty = "0";
                LOGGER.info("pcbCQty1........" + pcbCQty);
            } else {
                whRequest.setPcbCQty(pcbCQty);
                CQty = pcbCQty;
                LOGGER.info("pcbCQty2........" + pcbCQty);
            }
            if ("".equals(pcbCtrQty)) {
                whRequest.setPcbCtrQty("0");
                CtrQty = "0";
                LOGGER.info("pcbCtrQty1........" + pcbCtrQty);
            } else {
                whRequest.setPcbCtrQty(pcbCtrQty);
                CtrQty = pcbCtrQty;
                LOGGER.info("pcbCtrQty2........" + pcbCtrQty);
            }

//            Integer totalQty = Integer.valueOf(pcbAQty) + Integer.valueOf(pcbBQty) + Integer.valueOf(pcbCQty) + Integer.valueOf(pcbCtrQty);
            Integer totalQty = Integer.valueOf(AQty) + Integer.valueOf(BQty) + Integer.valueOf(CQty) + Integer.valueOf(CtrQty);
            whRequest.setQuantity(totalQty.toString());

            if (!"".equals(equipmentIdpcbCtr)) {
                String[] qualCtr = equipmentIdpcbCtr.split(" - ");
                if (!pcbName[0].equals(qualCtr[0])) {
                    redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                    return "redirect:/wh/whRequest/edit/" + id;
                }
            }
            if (!"".equals(equipmentIdpcbA)) {
                String[] qualA = equipmentIdpcbA.split(" - ");
                if (!pcbName[0].equals(qualA[0])) {
                    redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                    return "redirect:/wh/whRequest/edit/" + id;
                }
            }
            if (!"".equals(equipmentIdpcbB)) {
                String[] qualB = equipmentIdpcbB.split(" - ");
                if (!pcbName[0].equals(qualB[0])) {
                    redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                    return "redirect:/wh/whRequest/edit/" + id;
                }
            }
            if (!"".equals(equipmentIdpcbC)) {
                String[] qualC = equipmentIdpcbC.split(" - ");
                if (!pcbName[0].equals(qualC[0])) {
                    redirectAttrs.addFlashAttribute("error", "Pcb ID are not tally. Please re-check.");
                    return "redirect:/wh/whRequest/edit/" + id;
                }
            }

            PcbLimitDAO pcbDao = new PcbLimitDAO();
            PcbLimit pcb = pcbDao.getPcbLimitByType(pcbType);
            if (totalQty > Integer.valueOf(pcb.getQuantity())) {
                redirectAttrs.addFlashAttribute("error", "Total of PCB quantity exceeded the PCB limit.Please re-check.");
                return "redirect:/wh/whRequest/edit/" + id;
            }
        } else if ("Motherboard".equals(equipmentType)) {
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
        } else {
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setQuantity(quantity);

        }
        whRequest.setRemarks(remarks);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String RemarksLogFull = "[" + formattedDate + "] - " + remarks;
        String remarksLog2 = remarksLog + " ; " + LINE_SEPARATOR + RemarksLogFull;
        whRequest.setRemarksLog(remarksLog2);
        whRequest.setModifiedBy(userSession.getId());
        whRequest.setFlag("0");

        WhRequestDAO whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.updateWhRequest(whRequest);
        args = new String[1];
        args[0] = requestType + " - " + equipmentType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update statusLog
            whRequestDAO = new WhRequestDAO();
            WhRequest whReq = whRequestDAO.getWhRequest(id);

            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(id);
            stat.setModule("cdars_wh_request");
            stat.setStatus("Edit Before Approved");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whRequest/edit/" + id;
    }

    @RequestMapping(value = "/whRequest/delete/{whRequestId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
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

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(whRequestId);
            stat.setModule("cdars_wh_request");
            stat.setStatus("Deleted from Request List");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/wh/whRequest";
    }

    @RequestMapping(value = "/whRequest/view/{whRequestId}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/whRequest/viewWhRequestPdf/{whRequestId}", method = RequestMethod.GET)
    public ModelAndView viewWhRequestPdf(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        return new ModelAndView("whRequestPdf", "whRequest", whRequest);
    }

    @RequestMapping(value = "/whRequest/approval/{whRequestId}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/whRequest/approvalupdate", method = RequestMethod.POST)
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
        if ("Not Approved".equals(finalApprovedStatus)) {
            whRequest.setFlag("1");
        } else {
            whRequest.setFlag("0");
        }
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        QueryResult queryResult = whRequestDAO.updateWhRequestForApproval(whRequest);
        args = new String[1];
        args[0] = requestType + " - " + equipmentType;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(id);
            stat.setModule("cdars_wh_request");
            stat.setStatus(finalApprovedStatus);
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            WhStatusLogDAO statD = new WhStatusLogDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }

            EmailSender emailSender = new EmailSender();
            whRequestDAO = new WhRequestDAO();
            WhRequest whRequest1 = whRequestDAO.getWhRequest(id);
            String fullname = whRequest1.getRequestedBy();
            String emailRequestor = whRequest1.getRequestorEmail();
            com.onsemi.cdars.model.User user = new com.onsemi.cdars.model.User();
            user.setFullname(fullname);

            emailSender.htmlEmail(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    //                    "farhannazri27@yahoo.com",
                    emailRequestor,
                    //                    subject
                    "Approval Status for New Hardware Request for Sending to SBN Factory",
                    //                    msg
                    "Approval status for New Hardware Request has been made. Please go to this link "
                    //for testing development
                    //                    + "<a href=\"" + request.getScheme() + "://fg79cj-l1:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/edit/" + id + "\">HIMS</a>"
                    //                    + " for approval status checking."

                    //for production server mysed-rel-app03
                    + "<a href=\"" + request.getScheme() + "://mysed-rel-app03:" + request.getServerPort() + request.getContextPath() + "/wh/whRequest/edit/" + id + "\">HIMS</a>"
                    + " for approval status checking."
            );

            if ("Approved".equals(finalApprovedStatus)) {

                //save id to table wh_shipping
                WhShipping ship = new WhShipping();

                ship.setRequestId(id);
//                ship.setStatus("No Material Pass Number"); //original 3/11/16
                ship.setStatus("Pending Material Pass Number"); //as requested 2/11/16
                ship.setFlag("0");
                WhShippingDAO whShippingDAO = new WhShippingDAO();
                QueryResult queryResultShip = whShippingDAO.insertWhShipping(ship);
//                return "redirect:/wh/whShipping";
                return "redirect:/wh/whRequest";
            }

        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }

        return "redirect:/wh/whRequest";
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String materialPassNo,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String materialPassExpiry1,
            @RequestParam(required = false) String materialPassExpiry2,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String requestedDate1,
            @RequestParam(required = false) String requestedDate2,
            @RequestParam(required = false) String requestedBy,
            @RequestParam(required = false) String receivedDate1,
            @RequestParam(required = false) String receivedDate2,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String retrievalReason
    ) {
        String query = "";
        int count = 0;

        if (equipmentType != null) {
//            if(!equipmentType.equals("") !("").equals(equipmentType)) {
            if (!("").equals(equipmentType)) {
                count++;
                if (count == 1) {
                    query = " equipment_type = \'" + equipmentType + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_type = \'" + equipmentType + "\' ";
                }
            }
        }

        if (equipmentId != null) {
            if (!equipmentId.equals("")) {
                count++;
                if (count == 1) {
                    query = " equipment_id = \'" + equipmentId + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_id = \'" + equipmentId + "\' ";
                }
            }
        }

        if (materialPassNo != null) {
            if (!materialPassNo.equals("")) {
                count++;
                if (count == 1) {
                    query = " mp_no = \'" + materialPassNo + "\' ";
                } else if (count > 1) {
                    query = query + " AND mp_no = \'" + materialPassNo + "\' ";
                }
            }
        }
        if (materialPassExpiry1 != null && materialPassExpiry2 != null) {
            if (!materialPassExpiry1.equals("") && !materialPassExpiry2.equals("")) {
                count++;
                String materialPassExpiry = " mp_expiry_date BETWEEN CAST(\'" + materialPassExpiry1 + "\' AS DATE) AND CAST(\'" + materialPassExpiry2 + "\' AS DATE) ";
                if (count == 1) {
                    query = materialPassExpiry;
                } else if (count > 1) {
                    query = query + " AND " + materialPassExpiry;
                }
            }
        }

        if (requestedBy != null) {
            if (!requestedBy.equals("")) {
                count++;
                if (count == 1) {
                    query = " requested_by = \'" + requestedBy + "\' ";
                } else if (count > 1) {
                    query = query + " AND requested_by = \'" + requestedBy + "\' ";
                }
            }
        }
        if (requestedDate1 != null && requestedDate2 != null) {
            if (!requestedDate1.equals("") && !requestedDate2.equals("")) {
                count++;
                String requestedDate = " requested_date BETWEEN CAST(\'" + requestedDate1 + "\' AS DATE) AND CAST(\'" + requestedDate2 + "\' AS DATE) ";
                if (count == 1) {
                    query = requestedDate;
                } else if (count > 1) {
                    query = query + " AND " + requestedDate;
                }
            }
        }

        if (status != null) {
            if (!("").equals(status)) {
                count++;
                if (count == 1) {
                    query = " status = \'" + status + "\' ";
                } else if (count > 1) {
                    query = query + " AND status = \'" + status + "\' ";
                }
            }
        }

        if (retrievalReason != null) {
            if (!retrievalReason.equals("")) {
                count++;
                if (count == 1) {
                    query = " retrieval_reason = \'" + retrievalReason + "\' ";
                } else if (count > 1) {
                    query = query + " AND retrieval_reason = \'" + retrievalReason + "\' ";
                }
            }
        }

        System.out.println("Query: " + query);
        WhRequestDAO wh = new WhRequestDAO();
        List<WhRequest> retrieveQueryList = wh.getQuery(query);
        model.addAttribute("retrieveQueryList", retrieveQueryList);

        wh = new WhRequestDAO();
        List<WhRequest> eqptIdList = wh.getWhRequestListEquipmentId();
        wh = new WhRequestDAO();
        List<WhRequest> requestedByList = wh.getWhRequestListRequestedBy();
        wh = new WhRequestDAO();
        List<WhRequest> statusList = wh.getWhRequestListStatus();
        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> retrievalReasonList = sDAO.getGroupParameterDetailList("", "017");
        sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> hardwareTypeList = sDAO.getGroupParameterDetailList("", "002");
        model.addAttribute("eqptIdList", eqptIdList);
        model.addAttribute("hardwareTypeList", hardwareTypeList);
        model.addAttribute("requestedByList", requestedByList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("retrievalReasonList", retrievalReasonList);

        return "whRequest/query";
    }

    @RequestMapping(value = "/whRequest/query/view/{whRequestId}", method = RequestMethod.GET)
    public String queryview(
            Model model,
            HttpServletRequest request,
            @PathVariable("whRequestId") String whRequestId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whRequest/query/viewQueryPdf/" + whRequestId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/query";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Warehouse Management - Hardware Request");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/whRequest/query/viewQueryPdf/{whRequestId}", method = RequestMethod.GET)
    public ModelAndView viewQueryWhRequestPdf(
            Model model,
            @PathVariable("whRequestId") String whRequestId
    ) {
        WhStatusLogDAO statusD = new WhStatusLogDAO();
        List< WhStatusLog> Log = statusD.getWhStatusLogList(whRequestId);
        model.addAttribute("Log", Log);
        statusD = new WhStatusLogDAO();
        List< WhStatusLog> ShipLog = statusD.getWhStatusLogListForRetrieveAndShip(whRequestId);
        model.addAttribute("ShipLog", ShipLog);
        statusD = new WhStatusLogDAO();
        WhStatusLog countShip = statusD.getCountRetrieveAndShipList2(whRequestId);
        statusD = new WhStatusLogDAO();
        WhStatusLog firstShipReqToMpCreatedTL = statusD.getTLReqToApproveAndApproveToMpCreated(whRequestId);
        model.addAttribute("firstShipReqToMpCreatedTL", firstShipReqToMpCreatedTL);
        statusD = new WhStatusLogDAO();
        WhStatusLog firstShipMpCreatedToInventoryTL = statusD.getTLMpCreatedToFinalInventoryDate(whRequestId);
        model.addAttribute("firstShipMpCreatedToInventoryTL", firstShipMpCreatedToInventoryTL);
        statusD = new WhStatusLogDAO();
        WhStatusLog retrievalTL = statusD.getTLRetrieveRequestToClose(whRequestId);
        model.addAttribute("retrievalTL", retrievalTL);
        statusD = new WhStatusLogDAO();
        WhStatusLog retrievalShipReqToMpCreatedTL = statusD.getTLReqToApproveAndApproveToMpCreatedForRetrievalShipment(whRequestId);
        model.addAttribute("retrievalShipReqToMpCreatedTL", retrievalShipReqToMpCreatedTL);
        statusD = new WhStatusLogDAO();
        WhStatusLog retrievalShipMpCreatedToInventoryTL = statusD.getTLMpCreatedToFinalInventoryDateForRetrievalShipment(whRequestId);
        model.addAttribute("retrievalShipMpCreatedToInventoryTL", retrievalShipMpCreatedToInventoryTL);

        statusD = new WhStatusLogDAO();
        WhStatusLog countReIdShip = statusD.getCountReqIdAtShip(whRequestId);
        model.addAttribute("countReIdShip", countReIdShip);

        statusD = new WhStatusLogDAO();
        WhStatusLog countReIdRetrieval = statusD.getCountReqIdAtRetrieval(whRequestId);
        model.addAttribute("countReIdRetrieval", countReIdRetrieval);

        model.addAttribute("countShip", countShip);
        WhRequestDAO whRequestDAO = new WhRequestDAO();
        WhRequest whRequest = whRequestDAO.getWhRequest(whRequestId);
        return new ModelAndView("logListPdf", "whRequest", whRequest);

    }
}
