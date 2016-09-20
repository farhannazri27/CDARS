package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.WhStatusLogDAO;
import com.onsemi.cdars.model.WhStatusLog;
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
@RequestMapping(value = "/whStatusLog")
@SessionAttributes({"userSession"})public class WhStatusLogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WhStatusLogController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String whStatusLog(
			Model model
	) {
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		List<WhStatusLog> whStatusLogList = whStatusLogDAO.getWhStatusLogList();
		model.addAttribute("whStatusLogList", whStatusLogList);
		return "whStatusLog/whStatusLog";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "whStatusLog/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String refId,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String flag
	) {
		WhStatusLog whStatusLog = new WhStatusLog();
		whStatusLog.setRefId(refId);
		whStatusLog.setModule(module);
		whStatusLog.setStatus(status);
		whStatusLog.setStatusDate(statusDate);
		whStatusLog.setCreatedBy(createdBy);
		whStatusLog.setFlag(flag);
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		QueryResult queryResult = whStatusLogDAO.insertWhStatusLog(whStatusLog);
		args = new String[1];
		args[0] = refId + " - " + module;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("whStatusLog", whStatusLog);
			return "whStatusLog/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/whStatusLog/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{whStatusLogId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("whStatusLogId") String whStatusLogId
	) {
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		WhStatusLog whStatusLog = whStatusLogDAO.getWhStatusLog(whStatusLogId);
		model.addAttribute("whStatusLog", whStatusLog);
		return "whStatusLog/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String refId,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String statusDate,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String flag
	) {
		WhStatusLog whStatusLog = new WhStatusLog();
		whStatusLog.setId(id);
		whStatusLog.setRefId(refId);
		whStatusLog.setModule(module);
		whStatusLog.setStatus(status);
		whStatusLog.setStatusDate(statusDate);
		whStatusLog.setCreatedBy(createdBy);
		whStatusLog.setFlag(flag);
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		QueryResult queryResult = whStatusLogDAO.updateWhStatusLog(whStatusLog);
		args = new String[1];
		args[0] = refId + " - " + module;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/whStatusLog/edit/" + id;
	}

	@RequestMapping(value = "/delete/{whStatusLogId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("whStatusLogId") String whStatusLogId
	) {
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		WhStatusLog whStatusLog = whStatusLogDAO.getWhStatusLog(whStatusLogId);
		whStatusLogDAO = new WhStatusLogDAO();
		QueryResult queryResult = whStatusLogDAO.deleteWhStatusLog(whStatusLogId);
		args = new String[1];
		args[0] = whStatusLog.getRefId() + " - " + whStatusLog.getModule();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/whStatusLog";
	}

	@RequestMapping(value = "/view/{whStatusLogId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("whStatusLogId") String whStatusLogId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/whStatusLog/viewWhStatusLogPdf/" + whStatusLogId, "UTF-8");
		String backUrl = servletContext.getContextPath() + "/whStatusLog";
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.whStatusLog");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewWhStatusLogPdf/{whStatusLogId}", method = RequestMethod.GET)
	public ModelAndView viewWhStatusLogPdf(
			Model model, 
			@PathVariable("whStatusLogId") String whStatusLogId
	) {
		WhStatusLogDAO whStatusLogDAO = new WhStatusLogDAO();
		WhStatusLog whStatusLog = whStatusLogDAO.getWhStatusLog(whStatusLogId);
		return new ModelAndView("whStatusLogPdf", "whStatusLog", whStatusLog);
	}
}