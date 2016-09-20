package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.PcbLimitDAO;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.PcbLimit;
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
@RequestMapping(value = "admin/pcbLimit")
@SessionAttributes({"userSession"})
public class PcbLimitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PcbLimitController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pcbLimit(
            Model model
    ) {
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        List<PcbLimit> pcbLimitList = pcbLimitDAO.getPcbLimitList();
        model.addAttribute("pcbLimitList", pcbLimitList);
        return "pcbLimit/pcbLimit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pcbType = sDAO.getGroupParameterDetailList("", "015");
        model.addAttribute("pcbType", pcbType);

        return "pcbLimit/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String pcbType,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String remarks
    ) {
        PcbLimit pcbLimit = new PcbLimit();
        pcbLimit.setPcbType(pcbType);
        pcbLimit.setQuantity(quantity);
        pcbLimit.setRemarks(remarks);
        pcbLimit.setCreatedBy(userSession.getFullname());
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        Integer countType = pcbLimitDAO.getCountPcbType(pcbType);
        if (countType == 0) {
            pcbLimitDAO = new PcbLimitDAO();
            QueryResult queryResult = pcbLimitDAO.insertPcbLimit(pcbLimit);
            args = new String[1];
            args[0] = pcbType + " - " + quantity;
            if (queryResult.getGeneratedKey().equals("0")) {
                model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                model.addAttribute("pcbLimit", pcbLimit);
                return "pcbLimit/add";
            } else {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                return "redirect:/admin/pcbLimit/edit/" + queryResult.getGeneratedKey();
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "This PCB Type already configured.");
//            model.addAttribute("error", "This PCB Type already configured.");
            model.addAttribute("pcbLimit", pcbLimit);
//            return "pcbLimit/add";
            return "redirect:/admin/pcbLimit/add/";
        }

    }

    @RequestMapping(value = "/edit/{pcbLimitId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("pcbLimitId") String pcbLimitId
    ) {
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        PcbLimit pcbLimit = pcbLimitDAO.getPcbLimit(pcbLimitId);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pcbType = sDAO.getGroupParameterDetailList(pcbLimit.getPcbType(), "015");
        model.addAttribute("pcbType", pcbType);
        model.addAttribute("pcbLimit", pcbLimit);
        return "pcbLimit/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String pcbType,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String remarks
    ) {
        PcbLimit pcbLimit = new PcbLimit();
        pcbLimit.setId(id);
        pcbLimit.setPcbType(pcbType);
        pcbLimit.setQuantity(quantity);
        pcbLimit.setRemarks(remarks);
        pcbLimit.setModifiedBy(userSession.getFullname());
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        Integer countType = pcbLimitDAO.getCountPcbTypeDifferentId(id, pcbType);
        if (countType == 0) {
            pcbLimitDAO = new PcbLimitDAO();
            QueryResult queryResult = pcbLimitDAO.updatePcbLimit(pcbLimit);

            args = new String[1];
            args[0] = pcbType + " - " + quantity;
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "This PCB Type already configured.");
        }

        return "redirect:/admin/pcbLimit/edit/" + id;
    }

    @RequestMapping(value = "/delete/{pcbLimitId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("pcbLimitId") String pcbLimitId
    ) {
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        PcbLimit pcbLimit = pcbLimitDAO.getPcbLimit(pcbLimitId);
        pcbLimitDAO = new PcbLimitDAO();
        QueryResult queryResult = pcbLimitDAO.deletePcbLimit(pcbLimitId);
        args = new String[1];
        args[0] = pcbLimit.getPcbType() + " - " + pcbLimit.getQuantity();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/pcbLimit";
    }

    @RequestMapping(value = "/view/{pcbLimitId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("pcbLimitId") String pcbLimitId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/pcbLimit/viewPcbLimitPdf/" + pcbLimitId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/pcbLimit";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.pcbLimit");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewPcbLimitPdf/{pcbLimitId}", method = RequestMethod.GET)
    public ModelAndView viewPcbLimitPdf(
            Model model,
            @PathVariable("pcbLimitId") String pcbLimitId
    ) {
        PcbLimitDAO pcbLimitDAO = new PcbLimitDAO();
        PcbLimit pcbLimit = pcbLimitDAO.getPcbLimit(pcbLimitId);
        return new ModelAndView("pcbLimitPdf", "pcbLimit", pcbLimit);
    }
}
