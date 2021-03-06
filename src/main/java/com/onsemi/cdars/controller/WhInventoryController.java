package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhInventoryDAO;
import com.onsemi.cdars.dao.WhRequestDAO;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.WhInventory;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.tools.QueryResult;
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
@RequestMapping(value = "/wh/whInventory")
@SessionAttributes({"userSession"})
public class WhInventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhInventoryController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whInventory(
            Model model
    ) {
        WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        List<WhInventory> whInventoryList = whInventoryDAO.getWhInventoryListWithDateDisplay2();
        model.addAttribute("whInventoryList", whInventoryList);
        
        return "whInventory/whInventory";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "whInventory/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String mpNo,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String requestedBy,
            @RequestParam(required = false) String requestedDate,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String verifiedDate,
            @RequestParam(required = false) String inventoryDate,
            @RequestParam(required = false) String inventoryLocation,
            @RequestParam(required = false) String inventoryBy,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String receivedDate,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String location
    ) {
        WhInventory whInventory = new WhInventory();
        whInventory.setRequestId(requestId);
        whInventory.setMpNo(mpNo);
        whInventory.setEquipmentType(equipmentType);
        whInventory.setEquipmentId(equipmentId);
        whInventory.setQuantity(quantity);
        whInventory.setRequestedBy(requestedBy);
        whInventory.setRequestedDate(requestedDate);
        whInventory.setRemarks(remarks);
        whInventory.setVerifiedDate(verifiedDate);
        whInventory.setInventoryDate(inventoryDate);
        whInventory.setInventoryLocation(inventoryLocation);
        whInventory.setInventoryBy(inventoryBy);
        whInventory.setStatus(status);
        whInventory.setReceivedDate(receivedDate);
        whInventory.setFlag(flag);
        whInventory.setLocation(location);
        WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        QueryResult queryResult = whInventoryDAO.insertWhInventory(whInventory);
        args = new String[1];
        args[0] = requestId + " - " + mpNo;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("whInventory", whInventory);
            return "whInventory/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/wh/whInventory/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{whInventoryId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whInventoryId") String whInventoryId
    ) {
       WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        WhInventory whInventory = whInventoryDAO.getWhInventoryForWithDateDisplay(whInventoryId);
        model.addAttribute("whInventory", whInventory);
        
         //for label
        String type = whInventory.getEquipmentType();
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
        return "whInventory/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String mpNo,
            @RequestParam(required = false) String mpExpiryDate
    ) {
        WhInventory whInventory = new WhInventory();
        whInventory.setId(id);
        whInventory.setRequestId(requestId);
        whInventory.setMpNo(mpNo);
        whInventory.setMpExpiryDate(mpExpiryDate);
        WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        QueryResult queryResult = whInventoryDAO.updateMpExpiryDateWhInventory(whInventory);
        args = new String[1];
        args[0] = requestId + " - " + mpNo;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
            //update table shipment
            WhShippingDAO shipD = new WhShippingDAO();
            int countShipRequestId = shipD.getCountRequestId(requestId);
            if (countShipRequestId == 1) {
                WhShipping ship = new WhShipping();
                ship.setMpNo(mpNo);
                ship.setMpExpiryDate(mpExpiryDate);
                ship.setRequestId(requestId);
                shipD = new WhShippingDAO();
                QueryResult queryResult2 = shipD.updateWhShippingMpExpiryDateWithRequestId(ship);
            }
            //update request table
            WhRequestDAO requestD = new WhRequestDAO();
            int countReqId = requestD.getCountRequestId(requestId);
            if (countReqId == 1) {
                WhRequest request = new WhRequest();
                request.setMpNo(mpNo);
                request.setMpExpiryDate(mpExpiryDate);
                request.setId(requestId);
                requestD = new WhRequestDAO();
                QueryResult queryResult3 = requestD.updateWhRequestMpNoMpExpiryDate(request);
            }
            
            //update retrieval table
            WhRetrievalDAO retrieveD = new WhRetrievalDAO();
            int countRetrieveReq = retrieveD.getCountRequestId(requestId);
            if(countRetrieveReq == 1){
                WhRetrieval retrieve = new WhRetrieval();
                retrieve.setMpNo(mpNo);
                retrieve.setMpExpiryDate(mpExpiryDate);
                retrieve.setRequestId(requestId);
                retrieveD = new WhRetrievalDAO();
                QueryResult queryResult4 = retrieveD.updateMp(retrieve);
            }
            return "redirect:/wh/whInventory";
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            return "redirect:/wh/whInventory/edit/" + id;
        }
//        return "redirect:/wh/whInventory/edit/" + id;

    }

    @RequestMapping(value = "/delete/{whInventoryId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("whInventoryId") String whInventoryId
    ) {
        WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        WhInventory whInventory = whInventoryDAO.getWhInventory(whInventoryId);
        whInventoryDAO = new WhInventoryDAO();
        QueryResult queryResult = whInventoryDAO.deleteWhInventory(whInventoryId);
        args = new String[1];
        args[0] = whInventory.getRequestId() + " - " + whInventory.getMpNo();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/wh/whInventory";
    }

    @RequestMapping(value = "/view/{whInventoryId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("whInventoryId") String whInventoryId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/wh/whInventory/viewWhInventoryPdf/" + whInventoryId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/wh/whInventory";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "HW in SBN Factory");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewWhInventoryPdf/{whInventoryId}", method = RequestMethod.GET)
    public ModelAndView viewWhInventoryPdf(
            Model model,
            @PathVariable("whInventoryId") String whInventoryId
    ) {
        WhInventoryDAO whInventoryDAO = new WhInventoryDAO();
        WhInventory whInventory = whInventoryDAO.getWhInventoryForWithDateDisplay(whInventoryId);
        return new ModelAndView("whInventoryPdf", "whInventory", whInventory);
    }
}
