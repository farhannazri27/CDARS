package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhShippingDAO;
import com.onsemi.cdars.model.WhShipping;
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
        
        for (WhShipping w : whShippingList) {
            LOGGER.info("equipmmmmmm " + w.getRequestEquipmentId());
        }
        
        model.addAttribute("whShippingList", whShippingList);
        return "whShipping/whShipping";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "whShipping/add";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
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
        QueryResult queryResult = whShippingDAO.insertWhShipping(whShipping);
        args = new String[1];
        args[0] = requestId + " - " + boxId;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("whShipping", whShipping);
            return "whShipping/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/whShipping/edit/" + queryResult.getGeneratedKey();
        }
    }
    
    @RequestMapping(value = "/edit/{whShippingId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("whShippingId") String whShippingId
    ) {
        WhShippingDAO whShippingDAO = new WhShippingDAO();
        WhShipping whShipping = whShippingDAO.getWhShipping(whShippingId);
        model.addAttribute("whShipping", whShipping);
        return "whShipping/edit";
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
        return "redirect:/whShipping/edit/" + id;
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
        return "redirect:/whShipping";
    }
    
    @RequestMapping(value = "/view/{whShippingId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("whShippingId") String whShippingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/whShipping/viewWhShippingPdf/" + whShippingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/whShipping";
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
        WhShipping whShipping = whShippingDAO.getWhShipping(whShippingId);
        return new ModelAndView("whShippingPdf", "whShipping", whShipping);
    }
}
