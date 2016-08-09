package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.HardwareQueueDAO;
import com.onsemi.cdars.dao.IonicAdHocDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.model.HardwareQueue;
import com.onsemi.cdars.model.IonicAdHoc;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
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
@RequestMapping(value = "/relLab/hardwarequeue")
@SessionAttributes({"userSession"})
public class HardwareQueueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareQueueController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String hardwareQueue(
            Model model
    ) {
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        List<HardwareQueue> hardwareQueueList = hardwareQueueDAO.getHardwareQueueList();
        model.addAttribute("hardwareQueueList", hardwareQueueList);
        return "hardwarequeue/hardwarequeue";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "hardwarequeue/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate
    ) {
        HardwareQueue hardwareQueue = new HardwareQueue();
        hardwareQueue.setCode(code);
        hardwareQueue.setName(name);
        hardwareQueue.setStartDate(startDate);
        hardwareQueue.setCreatedBy(userSession.getId());
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        QueryResult queryResult = hardwareQueueDAO.insertHardwareQueue(hardwareQueue);
        args = new String[1];
        args[0] = code + " - " + name;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("hardwareQueue", hardwareQueue);
            return "hardwarequeue/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/relLab/hardwarequeue/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{hardwareQueueId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("hardwareQueueId") String hardwareQueueId
    ) {
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        HardwareQueue hardwareQueue = hardwareQueueDAO.getHardwareQueue(hardwareQueueId);
        model.addAttribute("hardwareQueue", hardwareQueue);
        return "hardwarequeue/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String hardwareQueueId,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate
    ) {
        HardwareQueue hardwareQueue = new HardwareQueue();
        hardwareQueue.setId(hardwareQueueId);
        hardwareQueue.setCode(code);
        hardwareQueue.setName(name);
        hardwareQueue.setStartDate(startDate);
        hardwareQueue.setModifiedBy(userSession.getId());
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        QueryResult queryResult = hardwareQueueDAO.updateHardwareQueue(hardwareQueue);
        args = new String[1];
        args[0] = code + " - " + name;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/relLab/hardwarequeue/edit/" + hardwareQueueId;
    }

    @RequestMapping(value = "/delete/{hardwareQueueId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("hardwareQueueId") String hardwareQueueId
    ) {
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        HardwareQueue hardwareQueue = hardwareQueueDAO.getHardwareQueue(hardwareQueueId);
        hardwareQueueDAO = new HardwareQueueDAO();
        QueryResult queryResult = hardwareQueueDAO.deleteHardwareQueue(hardwareQueueId);
        args = new String[1];
        args[0] = hardwareQueue.getCode() + " - " + hardwareQueue.getName();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/relLab/hardwarequeue";
    }

    @RequestMapping(value = "/view/{hardwareQueueId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("hardwareQueueId") String hardwareQueueId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/relLab/hardwarequeue/viewHardwareQueuePdf/" + hardwareQueueId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/hardwarequeue";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.hardwarequeue");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewHardwareQueuePdf/{hardwareQueueId}", method = RequestMethod.GET)
    public ModelAndView viewHardwareQueuePdf(
            Model model,
            @PathVariable("hardwareQueueId") String hardwareQueueId
    ) {
        HardwareQueueDAO hardwareQueueDAO = new HardwareQueueDAO();
        HardwareQueue hardwareQueue = hardwareQueueDAO.getHardwareQueue(hardwareQueueId);
        return new ModelAndView("hardwareQueuePdf", "hardwareQueue", hardwareQueue);
    }

    //add-hoc
    @RequestMapping(value = "/addHoc", method = RequestMethod.GET)
    public String addHoc(Model model) {

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> sourcelist = sDAO.getGroupParameterDetailList("", "001");
        ParameterDetailsDAO cDAO = new ParameterDetailsDAO();
        List<ParameterDetails> categorylist = cDAO.getGroupParameterDetailList("", "002");
        ParameterDetailsDAO eDAO = new ParameterDetailsDAO();
        List<ParameterDetails> evenlist = eDAO.getGroupParameterDetailList("", "003");
        ParameterDetailsDAO eqDAO = new ParameterDetailsDAO();
        List<ParameterDetails> equipmentlist = eqDAO.getGroupParameterDetailList("", "004");
        model.addAttribute("sourcelist", sourcelist);
        model.addAttribute("categorylist", categorylist);
        model.addAttribute("evenlist", evenlist);
        model.addAttribute("equipmentlist", equipmentlist);
        return "adHoc/add";
    }

    @RequestMapping(value = "/saveAddHoc", method = RequestMethod.POST)
    public String saveAddHoc(
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
            @RequestParam(required = false) String pcardQty,
            @RequestParam(required = false) String lcardQty,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) String equipmentId
    ) {
        IonicAdHoc ionicAdHoc = new IonicAdHoc();
        ionicAdHoc.setClassification(classification);
        ionicAdHoc.setSource(source);
        ionicAdHoc.setCategory(category);
        ionicAdHoc.setHardwareId(hardwareId);
        ionicAdHoc.setPcardId(pcardId);
        ionicAdHoc.setPcardQty(pcardQty);
        ionicAdHoc.setLcardId(lcardId);
        ionicAdHoc.setLcardQty(lcardQty);
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
            return "adHoc/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/relLab/hardwarequeue";
        }
    }
//    end
}
