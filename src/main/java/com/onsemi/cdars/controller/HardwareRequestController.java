package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.HardwareRequestDAO;
import com.onsemi.cdars.model.HardwareRequest;
import com.onsemi.cdars.model.HardwareRequestTemp;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping(value = "/relLab/hardwareRequest")
@SessionAttributes({"userSession"})public class HardwareRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HardwareRequestController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String hardwareRequest(
			Model model
	) {
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		List<HardwareRequest> hardwareRequestList = hardwareRequestDAO.getHardwareRequestList();
		model.addAttribute("hardwareRequestList", hardwareRequestList);
		return "hardwareRequest/hardwareRequest";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "hardwareRequest/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String forecastReadoutStart,
			@RequestParam(required = false) String eventCode,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		HardwareRequest hardwareRequest = new HardwareRequest();
		hardwareRequest.setForecastReadoutStart(forecastReadoutStart);
		hardwareRequest.setEventCode(eventCode);
		hardwareRequest.setRms(rms);
		hardwareRequest.setProcess(process);
		hardwareRequest.setStatus(status);
		hardwareRequest.setCreatedDate(createdDate);
		hardwareRequest.setModifiedBy(modifiedBy);
		hardwareRequest.setModifiedDate(modifiedDate);
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		QueryResult queryResult = hardwareRequestDAO.insertHardwareRequest(hardwareRequest);
		args = new String[1];
		args[0] = forecastReadoutStart + " - " + eventCode;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("hardwareRequest", hardwareRequest);
			return "hardwareRequest/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/relLab/hardwareRequest/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{hardwareRequestId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("hardwareRequestId") String hardwareRequestId
	) {
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		HardwareRequest hardwareRequest = hardwareRequestDAO.getHardwareRequest(hardwareRequestId);
		model.addAttribute("hardwareRequest", hardwareRequest);
		return "hardwareRequest/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String forecastReadoutStart,
			@RequestParam(required = false) String eventCode,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String modifiedBy,
			@RequestParam(required = false) String modifiedDate
	) {
		HardwareRequest hardwareRequest = new HardwareRequest();
		hardwareRequest.setId(id);
		hardwareRequest.setForecastReadoutStart(forecastReadoutStart);
		hardwareRequest.setEventCode(eventCode);
		hardwareRequest.setRms(rms);
		hardwareRequest.setProcess(process);
		hardwareRequest.setStatus(status);
		hardwareRequest.setCreatedDate(createdDate);
		hardwareRequest.setModifiedBy(modifiedBy);
		hardwareRequest.setModifiedDate(modifiedDate);
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		QueryResult queryResult = hardwareRequestDAO.updateHardwareRequest(hardwareRequest);
		args = new String[1];
		args[0] = forecastReadoutStart + " - " + eventCode;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/relLab/hardwareRequest/edit/" + id;
	}

	@RequestMapping(value = "/delete/{hardwareRequestId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("hardwareRequestId") String hardwareRequestId
	) {
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		HardwareRequest hardwareRequest = hardwareRequestDAO.getHardwareRequest(hardwareRequestId);
		hardwareRequestDAO = new HardwareRequestDAO();
		QueryResult queryResult = hardwareRequestDAO.deleteHardwareRequest(hardwareRequestId);
		args = new String[1];
		args[0] = hardwareRequest.getForecastReadoutStart() + " - " + hardwareRequest.getEventCode();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/relLab/hardwareRequest";
	}

	@RequestMapping(value = "/view/{hardwareRequestId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("hardwareRequestId") String hardwareRequestId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/relLab/hardwareRequest/viewHardwareRequestPdf/" + hardwareRequestId, "UTF-8");
                String backUrl = servletContext.getContextPath() + "/hardwareRequest";
		model.addAttribute("pdfUrl", pdfUrl);
                 model.addAttribute("backUrl", backUrl);
		model.addAttribute("pageTitle", "general.label.hardwareRequest");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewHardwareRequestPdf/{hardwareRequestId}", method = RequestMethod.GET)
	public ModelAndView viewHardwareRequestPdf(
			Model model, 
			@PathVariable("hardwareRequestId") String hardwareRequestId
	) {
		HardwareRequestDAO hardwareRequestDAO = new HardwareRequestDAO();
		HardwareRequest hardwareRequest = hardwareRequestDAO.getHardwareRequest(hardwareRequestId);
		return new ModelAndView("hardwareRequestPdf", "hardwareRequest", hardwareRequest);
	}
        
         @RequestMapping(value = "/testOpenCsv", method = RequestMethod.GET)
    public String addtestOpenCsv(Model model) {

        CSVReader csvReader = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            csvReader = new CSVReader(new FileReader("C:\\Hardware_Request_FTP-20160503.csv"), ',', '"', 1);
            //employeeDetails stores the values current line
            String[] requestFtp = null;
            //Create List for holding Employee objects
            List<HardwareRequestTemp> empList = new ArrayList<HardwareRequestTemp>();

            while ((requestFtp = csvReader.readNext()) != null) {
                //Save the employee details in Employee object
                HardwareRequestTemp emp = new HardwareRequestTemp(requestFtp[0],
                        requestFtp[1], requestFtp[2],
                        requestFtp[3]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (HardwareRequestTemp e : empList) {

                 Date initDate = new SimpleDateFormat("dd-MMM-yyyy h:mm").parse(e.getForecastReadoutStart());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                HardwareRequest ftp = new HardwareRequest();
                ftp.setForecastReadoutStart(parsedDate);
                ftp.setEventCode(e.getEventCode());
                ftp.setRms(e.getRms());
                ftp.setProcess(e.getProcess());
                ftp.setStatus("Pending");

                HardwareRequestDAO hardwareFtpDAO = new HardwareRequestDAO();
                int count = hardwareFtpDAO.getCountExistingData(ftp);
                LOGGER.info("testmaaaaaaa" + count);
                if (count == 0) {
                    hardwareFtpDAO = new HardwareRequestDAO();
//                    HardwareRequestDAO  hardwareFtpDAO1 = new HardwareRequestDAO();
                    QueryResult queryResult1 = hardwareFtpDAO.insertHardwareRequest(ftp);
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return "ionicLimit/add";
    }
}