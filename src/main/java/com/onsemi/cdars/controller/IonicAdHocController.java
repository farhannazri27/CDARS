package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.IonicAdHocDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.model.IonicAdHoc;
import com.onsemi.cdars.model.ParameterDetails;
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
@RequestMapping(value = "/ionicAdHoc")
@SessionAttributes({"userSession"})
public class IonicAdHocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicAdHocController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ionicAdHoc(
            Model model
    ) {
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        List<IonicAdHoc> ionicAdHocList = ionicAdHocDAO.getIonicAdHocList();
        model.addAttribute("ionicAdHocList", ionicAdHocList);
        return "ionicAdHoc/ionicAdHoc";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        
        ParameterDetailsDAO pDAO = new ParameterDetailsDAO();
        List<ParameterDetails> sourcelist = pDAO.getGroupParameterDetailList("", "001");
        pDAO = new ParameterDetailsDAO();
        List<ParameterDetails> categorylist = pDAO.getGroupParameterDetailList("", "002");
        pDAO = new ParameterDetailsDAO();
        List<ParameterDetails> evenlist = pDAO.getGroupParameterDetailList("", "003");
        pDAO = new ParameterDetailsDAO();
        List<ParameterDetails> equipmentlist = pDAO.getGroupParameterDetailList("", "004");
        model.addAttribute("sourcelist", sourcelist);
        model.addAttribute("categorylist", categorylist);
        model.addAttribute("evenlist", evenlist);
        model.addAttribute("equipmentlist", equipmentlist);
        return "ionicAdHoc/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String hardwareId,
            @RequestParam(required = false) String pcardId,
            @RequestParam(required = false) String lcardId,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String createdBy
    ) {
        IonicAdHoc ionicAdHoc = new IonicAdHoc();
        ionicAdHoc.setClassification(classification);
        ionicAdHoc.setSource(source);
        ionicAdHoc.setCategory(category);
        ionicAdHoc.setHardwareId(hardwareId);
        ionicAdHoc.setPcardId(pcardId);
        ionicAdHoc.setLcardId(lcardId);
        ionicAdHoc.setRms(rms);
        ionicAdHoc.setEvent(event);
        ionicAdHoc.setEquipmentId(equipmentId);
        ionicAdHoc.setCreatedBy(userSession.getId());
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        QueryResult queryResult = ionicAdHocDAO.insertIonicAdHoc(ionicAdHoc);
        args = new String[1];
        args[0] = classification + " - " + source;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("ionicAdHoc", ionicAdHoc);
            return "ionicAdHoc/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/ionicAdHoc/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{ionicAdHocId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("ionicAdHocId") String ionicAdHocId
    ) {
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        IonicAdHoc ionicAdHoc = ionicAdHocDAO.getIonicAdHoc(ionicAdHocId);
        model.addAttribute("ionicAdHoc", ionicAdHoc);
        return "ionicAdHoc/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String hardwareId,
            @RequestParam(required = false) String pcardId,
            @RequestParam(required = false) String lcardId,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String modifiedBy
    ) {
        IonicAdHoc ionicAdHoc = new IonicAdHoc();
        ionicAdHoc.setId(id);
        ionicAdHoc.setClassification(classification);
        ionicAdHoc.setSource(source);
        ionicAdHoc.setCategory(category);
        ionicAdHoc.setHardwareId(hardwareId);
        ionicAdHoc.setPcardId(pcardId);
        ionicAdHoc.setLcardId(lcardId);
        ionicAdHoc.setRms(rms);
        ionicAdHoc.setEvent(event);
        ionicAdHoc.setEquipmentId(equipmentId);
        ionicAdHoc.setModifiedBy(userSession.getId());
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        QueryResult queryResult = ionicAdHocDAO.updateIonicAdHoc(ionicAdHoc);
        args = new String[1];
        args[0] = classification + " - " + source;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/ionicAdHoc/edit/" + id;
    }

    @RequestMapping(value = "/delete/{ionicAdHocId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("ionicAdHocId") String ionicAdHocId
    ) {
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        IonicAdHoc ionicAdHoc = ionicAdHocDAO.getIonicAdHoc(ionicAdHocId);
        ionicAdHocDAO = new IonicAdHocDAO();
        QueryResult queryResult = ionicAdHocDAO.deleteIonicAdHoc(ionicAdHocId);
        args = new String[1];
        args[0] = ionicAdHoc.getClassification() + " - " + ionicAdHoc.getSource();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/ionicAdHoc";
    }

    @RequestMapping(value = "/view/{ionicAdHocId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("ionicAdHocId") String ionicAdHocId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/ionicAdHoc/viewIonicAdHocPdf/" + ionicAdHocId, "UTF-8");
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("pageTitle", "general.label.ionicAdHoc");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewIonicAdHocPdf/{ionicAdHocId}", method = RequestMethod.GET)
    public ModelAndView viewIonicAdHocPdf(
            Model model,
            @PathVariable("ionicAdHocId") String ionicAdHocId
    ) {
        IonicAdHocDAO ionicAdHocDAO = new IonicAdHocDAO();
        IonicAdHoc ionicAdHoc = ionicAdHocDAO.getIonicAdHoc(ionicAdHocId);
        return new ModelAndView("ionicAdHocPdf", "ionicAdHoc", ionicAdHoc);
    }
}
