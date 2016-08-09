package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.IonicQueueDAO;
import com.onsemi.cdars.model.IonicQueue;
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
@RequestMapping(value = "/ionicQueue")
@SessionAttributes({"userSession"})public class IonicQueueController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IonicQueueController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String ionicQueue(
			Model model
	) {
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		List<IonicQueue> ionicQueueList = ionicQueueDAO.getIonicQueueList();
		model.addAttribute("ionicQueueList", ionicQueueList);
		return "ionicQueue/ionicQueue";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "ionicQueue/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String ionicFtpId,
			@RequestParam(required = false) String ionicAdHocId,
			@RequestParam(required = false) String classification,
			@RequestParam(required = false) String source,
			@RequestParam(required = false) String eventNameCode,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String intervals,
			@RequestParam(required = false) String currentStatus,
			@RequestParam(required = false) String dateOff,
			@RequestParam(required = false) String equipmentId,
			@RequestParam(required = false) String lcode,
			@RequestParam(required = false) String hardwareFinal,
			@RequestParam(required = false) String finalSupportItem,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		IonicQueue ionicQueue = new IonicQueue();
		ionicQueue.setIonicFtpId(ionicFtpId);
		ionicQueue.setIonicAdHocId(ionicAdHocId);
		ionicQueue.setClassification(classification);
		ionicQueue.setSource(source);
		ionicQueue.setEventNameCode(eventNameCode);
		ionicQueue.setRms(rms);
		ionicQueue.setIntervals(intervals);
		ionicQueue.setCurrentStatus(currentStatus);
		ionicQueue.setDateOff(dateOff);
		ionicQueue.setEquipmentId(equipmentId);
		ionicQueue.setLcode(lcode);
		ionicQueue.setHardwareFinal(hardwareFinal);
		ionicQueue.setFinalSupportItem(finalSupportItem);
		ionicQueue.setStatus(status);
		ionicQueue.setCreatedBy(createdBy);
		ionicQueue.setCreatedDate(createdDate);
		ionicQueue.setModifiedBy(modifiedBy);
		ionicQueue.setModifiedDate(modifiedDate);
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		QueryResult queryResult = ionicQueueDAO.insertIonicQueue(ionicQueue);
		args = new String[1];
		args[0] = ionicFtpId + " - " + ionicAdHocId;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("ionicQueue", ionicQueue);
			return "ionicQueue/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/ionicQueue/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{ionicQueueId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("ionicQueueId") String ionicQueueId
	) {
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		IonicQueue ionicQueue = ionicQueueDAO.getIonicQueue(ionicQueueId);
		model.addAttribute("ionicQueue", ionicQueue);
		return "ionicQueue/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String ionicFtpId,
			@RequestParam(required = false) String ionicAdHocId,
			@RequestParam(required = false) String classification,
			@RequestParam(required = false) String source,
			@RequestParam(required = false) String eventNameCode,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String intervals,
			@RequestParam(required = false) String currentStatus,
			@RequestParam(required = false) String dateOff,
			@RequestParam(required = false) String equipmentId,
			@RequestParam(required = false) String lcode,
			@RequestParam(required = false) String hardwareFinal,
			@RequestParam(required = false) String finalSupportItem,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		IonicQueue ionicQueue = new IonicQueue();
		ionicQueue.setId(id);
		ionicQueue.setIonicFtpId(ionicFtpId);
		ionicQueue.setIonicAdHocId(ionicAdHocId);
		ionicQueue.setClassification(classification);
		ionicQueue.setSource(source);
		ionicQueue.setEventNameCode(eventNameCode);
		ionicQueue.setRms(rms);
		ionicQueue.setIntervals(intervals);
		ionicQueue.setCurrentStatus(currentStatus);
		ionicQueue.setDateOff(dateOff);
		ionicQueue.setEquipmentId(equipmentId);
		ionicQueue.setLcode(lcode);
		ionicQueue.setHardwareFinal(hardwareFinal);
		ionicQueue.setFinalSupportItem(finalSupportItem);
		ionicQueue.setStatus(status);
		ionicQueue.setCreatedBy(createdBy);
		ionicQueue.setCreatedDate(createdDate);
		ionicQueue.setModifiedBy(modifiedBy);
		ionicQueue.setModifiedDate(modifiedDate);
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		QueryResult queryResult = ionicQueueDAO.updateIonicQueue(ionicQueue);
		args = new String[1];
		args[0] = ionicFtpId + " - " + ionicAdHocId;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/ionicQueue/edit/" + id;
	}

	@RequestMapping(value = "/delete/{ionicQueueId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("ionicQueueId") String ionicQueueId
	) {
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		IonicQueue ionicQueue = ionicQueueDAO.getIonicQueue(ionicQueueId);
		ionicQueueDAO = new IonicQueueDAO();
		QueryResult queryResult = ionicQueueDAO.deleteIonicQueue(ionicQueueId);
		args = new String[1];
		args[0] = ionicQueue.getIonicFtpId() + " - " + ionicQueue.getIonicAdHocId();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/ionicQueue";
	}

	@RequestMapping(value = "/view/{ionicQueueId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("ionicQueueId") String ionicQueueId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/ionicQueue/viewIonicQueuePdf/" + ionicQueueId, "UTF-8");
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("pageTitle", "general.label.ionicQueue");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewIonicQueuePdf/{ionicQueueId}", method = RequestMethod.GET)
	public ModelAndView viewIonicQueuePdf(
			Model model, 
			@PathVariable("ionicQueueId") String ionicQueueId
	) {
		IonicQueueDAO ionicQueueDAO = new IonicQueueDAO();
		IonicQueue ionicQueue = ionicQueueDAO.getIonicQueue(ionicQueueId);
		return new ModelAndView("ionicQueuePdf", "ionicQueue", ionicQueue);
	}
}