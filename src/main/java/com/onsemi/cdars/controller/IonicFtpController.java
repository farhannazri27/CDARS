package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.IonicFtpDAO;
import com.onsemi.cdars.dao.IonicQueueDAO;
import com.onsemi.cdars.model.IonicFtp;
import com.onsemi.cdars.model.IonicFtpTemp;
import com.onsemi.cdars.model.IonicQueue;
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
@RequestMapping(value = "/ionicFtp")
@SessionAttributes({"userSession"})
public class IonicFtpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicFtpController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ionicFtp(
            Model model
    ) {
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        List<IonicFtp> ionicFtpList = ionicFtpDAO.getIonicFtpList();
        model.addAttribute("ionicFtpList", ionicFtpList);
        return "ionicFtp/ionicFtp";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "ionicFtp/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String intervals,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String dateOff,
            @RequestParam(required = false) String equipId,
            @RequestParam(required = false) String lcode,
            @RequestParam(required = false) String hardwareFinal,
            @RequestParam(required = false) String supportItem,
            @RequestParam(required = false) String createdDate
    ) {
        IonicFtp ionicFtp = new IonicFtp();
        ionicFtp.setEventCode(eventCode);
        ionicFtp.setRms(rms);
        ionicFtp.setIntervals(intervals);
        ionicFtp.setCurrentStatus(currentStatus);
        ionicFtp.setDateOff(dateOff);
        ionicFtp.setEquipId(equipId);
        ionicFtp.setLcode(lcode);
        ionicFtp.setHardwareFinal(hardwareFinal);
        ionicFtp.setSupportItem(supportItem);
        ionicFtp.setCreatedDate(createdDate);
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        QueryResult queryResult = ionicFtpDAO.insertIonicFtp(ionicFtp);
        args = new String[1];
        args[0] = eventCode + " - " + rms;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("ionicFtp", ionicFtp);
            return "ionicFtp/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/ionicFtp/edit/" + queryResult.getGeneratedKey();
        }
    }

    @RequestMapping(value = "/edit/{ionicFtpId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("ionicFtpId") String ionicFtpId
    ) {
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        IonicFtp ionicFtp = ionicFtpDAO.getIonicFtp(ionicFtpId);
        model.addAttribute("ionicFtp", ionicFtp);
        return "ionicFtp/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String rms,
            @RequestParam(required = false) String intervals,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String dateOff,
            @RequestParam(required = false) String equipId,
            @RequestParam(required = false) String lcode,
            @RequestParam(required = false) String hardwareFinal,
            @RequestParam(required = false) String supportItem,
            @RequestParam(required = false) String createdDate
    ) {
        IonicFtp ionicFtp = new IonicFtp();
        ionicFtp.setId(id);
        ionicFtp.setEventCode(eventCode);
        ionicFtp.setRms(rms);
        ionicFtp.setIntervals(intervals);
        ionicFtp.setCurrentStatus(currentStatus);
        ionicFtp.setDateOff(dateOff);
        ionicFtp.setEquipId(equipId);
        ionicFtp.setLcode(lcode);
        ionicFtp.setHardwareFinal(hardwareFinal);
        ionicFtp.setSupportItem(supportItem);
        ionicFtp.setCreatedDate(createdDate);
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        QueryResult queryResult = ionicFtpDAO.updateIonicFtp(ionicFtp);
        args = new String[1];
        args[0] = eventCode + " - " + rms;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/ionicFtp/edit/" + id;
    }

    @RequestMapping(value = "/delete/{ionicFtpId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("ionicFtpId") String ionicFtpId
    ) {
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        IonicFtp ionicFtp = ionicFtpDAO.getIonicFtp(ionicFtpId);
        ionicFtpDAO = new IonicFtpDAO();
        QueryResult queryResult = ionicFtpDAO.deleteIonicFtp(ionicFtpId);
        args = new String[1];
        args[0] = ionicFtp.getEventCode() + " - " + ionicFtp.getRms();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/ionicFtp";
    }

    @RequestMapping(value = "/view/{ionicFtpId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("ionicFtpId") String ionicFtpId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/ionicFtp/viewIonicFtpPdf/" + ionicFtpId, "UTF-8");
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("pageTitle", "general.label.ionicFtp");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewIonicFtpPdf/{ionicFtpId}", method = RequestMethod.GET)
    public ModelAndView viewIonicFtpPdf(
            Model model,
            @PathVariable("ionicFtpId") String ionicFtpId
    ) {
        IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
        IonicFtp ionicFtp = ionicFtpDAO.getIonicFtp(ionicFtpId);
        return new ModelAndView("ionicFtpPdf", "ionicFtp", ionicFtp);
    }

    @RequestMapping(value = "/testOpenCsv", method = RequestMethod.GET)
    public String addtestOpenCsv(Model model) {

        CSVReader csvReader = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-20160505.csv"), ',', '"', 1);
            //employeeDetails stores the values current line
            String[] ionicFtp = null;
            //Create List for holding Employee objects
            List<IonicFtpTemp> empList = new ArrayList<IonicFtpTemp>();

            while ((ionicFtp = csvReader.readNext()) != null) {
                //Save the employee details in Employee object
                IonicFtpTemp emp = new IonicFtpTemp(ionicFtp[0],
                        ionicFtp[1], ionicFtp[2],
                        ionicFtp[3], ionicFtp[4],
                        ionicFtp[5], ionicFtp[6],
                        ionicFtp[7], ionicFtp[8]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (IonicFtpTemp e : empList) {

                Date initDate = new SimpleDateFormat("MM/dd/yy H:mm").parse(e.getDateOff());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                IonicFtp ftp = new IonicFtp();
                ftp.setEventCode(e.getEventCode());
                ftp.setRms(e.getRms());
                ftp.setIntervals(e.getIntervals());
                ftp.setCurrentStatus(e.getCurrentStatus());
                ftp.setDateOff(parsedDate);
                ftp.setEquipId(e.getEquipId());
                ftp.setLcode(e.getLcode());
                ftp.setHardwareFinal(e.getHardwareFinal());
                ftp.setSupportItem(e.getSupportItem());

                IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);
                LOGGER.info("testmaaaaaaa" + count);
                if (count == 0) {
                    ionicFtpDAO = new IonicFtpDAO();
//                    IonicFtpDAO ionicFtpDAO1 = new IonicFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);

                    if (!queryResult1.getGeneratedKey().equals("0")) {

                        IonicQueue i = new IonicQueue();
                        i.setIonicFtpId(queryResult1.getGeneratedKey());
                        i.setClassification("1st Hardware");
                        i.setSource("Production");
                        i.setEventNameCode(e.getEventCode());
                        i.setRms(e.getRms());
                        i.setIntervals(e.getIntervals());
                        i.setCurrentStatus(e.getCurrentStatus());
                        i.setDateOff(parsedDate);
                        i.setEquipmentId(e.getEquipId());
                        i.setLcode(e.getLcode());
                        i.setHardwareFinal(e.getHardwareFinal());
                        i.setFinalSupportItem(e.getSupportItem());
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
