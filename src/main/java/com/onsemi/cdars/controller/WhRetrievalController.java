package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhRetrievalDAO;
import com.onsemi.cdars.model.WhRetrieval;
import com.onsemi.cdars.model.UserSession;
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
@RequestMapping(value = "/wh/whRetrieval")
@SessionAttributes({"userSession"})
public class WhRetrievalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhRetrievalController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whRetrieval(
            Model model
    ) {
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        List<WhRetrieval> whRetrievalList = whRetrievalDAO.getWhRetrievalListWithDateDisplayWithoutStatusClosed();
        model.addAttribute("whRetrievalList", whRetrievalList);
        return "whRetrieval/whRetrieval";
    }

    @RequestMapping(value = "/edit/{whRetrievalId}", method = RequestMethod.GET)
    public String edit(
            Model model,
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
        if ("Barcode Verified".equals(whRetrieval.getStatus()) || "Closed".equals(whRetrieval.getStatus())) {
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
        if ("Ship".equals(whRetrieval.getStatus()) || "Requested".equals(whRetrieval.getStatus()) || "Barcode Not Match".equals(whRetrieval.getStatus())) {
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
        whRetrieval.setStatus("Barcode Verified");
        whRetrieval.setFlag("0");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateWhRetrievalBarcode(whRetrieval);
        args = new String[1];
        args[0] = barcodeVerification;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
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
    ) {
        WhRetrieval whRetrieval = new WhRetrieval();
        whRetrieval.setId(id);
        whRetrieval.setTtVerification(ttVerification);
        whRetrieval.setTtVerifiedBy(userSession.getFullname());
        whRetrieval.setStatus("Closed");
        whRetrieval.setFlag("0");
        WhRetrievalDAO whRetrievalDAO = new WhRetrievalDAO();
        QueryResult queryResult = whRetrievalDAO.updateTt(whRetrieval);
        args = new String[1];
        args[0] = ttVerification;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
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
            @RequestParam(required = false) String slot,
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
        whRetrieval.setSlot(slot);
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
        model.addAttribute("pageTitle", "general.label.whRetrieval");
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
}
