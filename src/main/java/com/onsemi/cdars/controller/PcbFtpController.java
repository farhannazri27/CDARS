package com.onsemi.cdars.controller;

import com.onsemi.cdars.dao.IonicFtpDAO;
import com.onsemi.cdars.dao.IonicQueueDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.PcbFtpDAO;
import com.onsemi.cdars.model.IonicFtp;
import com.onsemi.cdars.model.IonicFtpTemp;
import com.onsemi.cdars.model.IonicQueue;
import com.onsemi.cdars.model.PcbFtp;
import com.onsemi.cdars.model.PcbFtpTemp;
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
@RequestMapping(value = "/pcbFtp")
@SessionAttributes({"userSession"})public class PcbFtpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcbFtpController.class);
	String[] args = {};

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String pcbFtp(
			Model model
	) {
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		List<PcbFtp> pcbFtpList = pcbFtpDAO.getPcbFtpList();
		model.addAttribute("pcbFtpList", pcbFtpList);
		return "pcbFtp/pcbFtp";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "pcbFtp/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String dateOff,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String supportItem,
			@RequestParam(required = false) String createdDate
	) {
		PcbFtp pcbFtp = new PcbFtp();
		pcbFtp.setDateOff(dateOff);
		pcbFtp.setRms(rms);
		pcbFtp.setProcess(process);
		pcbFtp.setStatus(status);
		pcbFtp.setSupportItem(supportItem);
		pcbFtp.setCreatedDate(createdDate);
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		QueryResult queryResult = pcbFtpDAO.insertPcbFtp(pcbFtp);
		args = new String[1];
		args[0] = dateOff + " - " + rms;
		if (queryResult.getGeneratedKey().equals("0")) {
			model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
			model.addAttribute("pcbFtp", pcbFtp);
			return "pcbFtp/add";
		} else {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
			return "redirect:/pcbFtp/edit/" + queryResult.getGeneratedKey();
		}
	}

	@RequestMapping(value = "/edit/{pcbFtpId}", method = RequestMethod.GET)
	public String edit(
			Model model,
			@PathVariable("pcbFtpId") String pcbFtpId
	) {
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		PcbFtp pcbFtp = pcbFtpDAO.getPcbFtp(pcbFtpId);
		model.addAttribute("pcbFtp", pcbFtp);
		return "pcbFtp/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@ModelAttribute UserSession userSession,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String dateOff,
			@RequestParam(required = false) String rms,
			@RequestParam(required = false) String process,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String supportItem,
			@RequestParam(required = false) String createdDate
	) {
		PcbFtp pcbFtp = new PcbFtp();
		pcbFtp.setId(id);
		pcbFtp.setDateOff(dateOff);
		pcbFtp.setRms(rms);
		pcbFtp.setProcess(process);
		pcbFtp.setStatus(status);
		pcbFtp.setSupportItem(supportItem);
		pcbFtp.setCreatedDate(createdDate);
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		QueryResult queryResult = pcbFtpDAO.updatePcbFtp(pcbFtp);
		args = new String[1];
		args[0] = dateOff + " - " + rms;
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
		}
		return "redirect:/pcbFtp/edit/" + id;
	}

	@RequestMapping(value = "/delete/{pcbFtpId}", method = RequestMethod.GET)
	public String delete(
			Model model,
			Locale locale,
			RedirectAttributes redirectAttrs,
			@PathVariable("pcbFtpId") String pcbFtpId
	) {
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		PcbFtp pcbFtp = pcbFtpDAO.getPcbFtp(pcbFtpId);
		pcbFtpDAO = new PcbFtpDAO();
		QueryResult queryResult = pcbFtpDAO.deletePcbFtp(pcbFtpId);
		args = new String[1];
		args[0] = pcbFtp.getDateOff() + " - " + pcbFtp.getRms();
		if (queryResult.getResult() == 1) {
			redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
		} else {
			redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
		}
		return "redirect:/pcbFtp";
	}

	@RequestMapping(value = "/view/{pcbFtpId}", method = RequestMethod.GET)
	public String view(
			Model model, 
			HttpServletRequest request, 
			@PathVariable("pcbFtpId") String pcbFtpId
	) throws UnsupportedEncodingException {
		String pdfUrl = URLEncoder.encode(request.getContextPath() + "/pcbFtp/viewPcbFtpPdf/" + pcbFtpId, "UTF-8");
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("pageTitle", "general.label.pcbFtp");
		return "pdf/viewer";
	}

	@RequestMapping(value = "/viewPcbFtpPdf/{pcbFtpId}", method = RequestMethod.GET)
	public ModelAndView viewPcbFtpPdf(
			Model model, 
			@PathVariable("pcbFtpId") String pcbFtpId
	) {
		PcbFtpDAO pcbFtpDAO = new PcbFtpDAO();
		PcbFtp pcbFtp = pcbFtpDAO.getPcbFtp(pcbFtpId);
		return new ModelAndView("pcbFtpPdf", "pcbFtp", pcbFtp);
	}
        
         @RequestMapping(value = "/testOpenCsv", method = RequestMethod.GET)
    public String addtestOpenCsv(Model model) {

        CSVReader csvReader = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            csvReader = new CSVReader(new FileReader("C:\\FOL_PCB_FTP-20160503.csv"), ',', '"', 2);
            //employeeDetails stores the values current line
            String[] pcbFtp = null;
            //Create List for holding Employee objects
            List<PcbFtpTemp> empList = new ArrayList<PcbFtpTemp>();

            while ((pcbFtp = csvReader.readNext()) != null) {
                //Save the employee details in Employee object
                PcbFtpTemp emp = new PcbFtpTemp(pcbFtp[0],
                        pcbFtp[1], pcbFtp[2],
                        pcbFtp[3], pcbFtp[4]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (PcbFtpTemp e : empList) {

                Date initDate = new SimpleDateFormat("dd-MMM-yyyy h:mm").parse(e.getDateOff());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                PcbFtp ftp = new PcbFtp();
                ftp.setDateOff(parsedDate);
                ftp.setRms(e.getRms());
                ftp.setProcess(e.getProcess());   
                ftp.setStatus(e.getStatus());
                ftp.setSupportItem(e.getSupportItem());

                PcbFtpDAO ionicFtpDAO = new PcbFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);
                if (count == 0) {
                    ionicFtpDAO = new PcbFtpDAO();
//                    PcbFtpDAO ionicFtpDAO1 = new PcbFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertPcbFtp(ftp);

                    if (!queryResult1.getGeneratedKey().equals("0")) {

                        IonicQueue i = new IonicQueue();
                        i.setPcbFtpId(queryResult1.getGeneratedKey());
                        i.setClassification("1st Hardware");
                        i.setSource("Production");
                        i.setEventNameCode("N/A");
                        i.setRms(e.getRms());
                        i.setIntervals("N/A");
                        i.setCurrentStatus(e.getStatus());
                        i.setDateOff(parsedDate);
                        i.setEquipmentId("N/A");
                        i.setLcode("N/A");
                        i.setHardwareFinal(e.getSupportItem());
                        i.setFinalSupportItem("PCB");
                        i.setStatus("Waiting for Verification");
                        i.setCreatedBy("0");
                        IonicQueueDAO idao = new IonicQueueDAO();
                        QueryResult qresult = idao.insertIonicQueue(i);
                    } else {

                        System.out.println("Fail to insert data!!!!");
                        return "ionicLimit/add";
                    }
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return "ionicLimit/add";
    }
}