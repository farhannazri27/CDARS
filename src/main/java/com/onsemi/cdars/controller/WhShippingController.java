package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.WhRequestDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.WhShipping;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.model.WhRequest;
import com.onsemi.cdars.tools.EmailSender;
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
@RequestMapping(value = "/wh/whShipping")
@SessionAttributes({"userSession"})
public class WhShippingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhShippingController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String whShipping(
            Model model
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        List<WhShipping> whShippingList = whShippingDAO.getWhShippingListMergeWithRequest();

        model.addAttribute("whShippingList", whShippingList);
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
            String IdLabel = "PCB Name";
            model.addAttribute("IdLabel", IdLabel);
        } else {
            String IdLabel = "Hardware ID";
            model.addAttribute("IdLabel", IdLabel);
        }
        //for check which tab should active
        if ("No Material Pass Number".equals(whShipping.getStatus())) {
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
        if ("Not Scan Trip Ticket Yet".equals(whShipping.getStatus())) {
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
        if ("Verified".equals(whShipping.getStatus()) || "Trip Ticket and Barcode Sticker Not Match".equals(whShipping.getStatus()) || "Not Scan Barcode Sticker Yet".equals(whShipping.getStatus()) || "Ship".equals(whShipping.getStatus())) {
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
        if ("No Material Pass Number".equals(status)) {
            whShipping.setStatus("Not Scan Trip Ticket Yet");
        } else {
            whShipping.setStatus(status);
        }
        whShipping.setModifiedBy(userSession.getFullname());
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        QueryResult queryResult = whShippingDAO.updateWhShippingMp(whShipping);
        args = new String[1];
        args[0] = mpNo + " - " + mpExpiryDate;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/wh/whShipping/viewTripTicket/" + id;
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
        if ("Not Scan Trip Ticket Yet".equals(status)) {
            whShipping.setStatus("Not Scan Barcode Sticker Yet");
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
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
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
            whShipping.setStatus("Verified");
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

    @RequestMapping(value = "/viewWhShippingPdf/{whShippingId}", method = RequestMethod.GET)
    public ModelAndView viewWhShippingPdf(
            Model model,
            @PathVariable("whShippingId") String whShippingId
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
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
