package com.onsemi.cdars.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.dao.CardPairingDAO;
import com.onsemi.cdars.dao.ParameterDetailsDAO;
import com.onsemi.cdars.model.CardPairing;
import com.onsemi.cdars.model.ParameterDetails;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.QueryResult;
import com.onsemi.cdars.tools.SPTSWebService;
import com.onsemi.cdars.tools.SystemUtil;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
import org.json.JSONArray;
import org.json.JSONObject;
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
@RequestMapping(value = "/admin/cardPairing")
@SessionAttributes({"userSession"})
public class CardPairingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardPairingController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cardPairing(
            Model model, @ModelAttribute UserSession userSession
    ) {
        CardPairingDAO cardPairingDAO = new CardPairingDAO();
        List<CardPairing> cardPairingList = cardPairingDAO.getCardPairingList();
        String groupId = userSession.getGroup();
        model.addAttribute("cardPairingList", cardPairingList);
        model.addAttribute("groupId", groupId);
        return "cardPairing/cardPairing";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) throws IOException {

        JSONObject paramPc = new JSONObject();
        paramPc.put("itemType", "BIB Card");
        paramPc.put("itemStatus", "0");
        paramPc.put("itemID", "PC%");
        JSONArray getItemByParamPc = SPTSWebService.getItemByParam(paramPc);
        List<LinkedHashMap<String, String>> itemListPc = SystemUtil.jsonArrayToList(getItemByParamPc);

        JSONObject paramLc = new JSONObject();
        paramLc.put("itemType", "BIB Card");
        paramLc.put("itemStatus", "0");
        paramLc.put("itemID", "LC%");
        JSONArray getItemByParamLc = SPTSWebService.getItemByParam(paramLc);
        List<LinkedHashMap<String, String>> itemListLc = SystemUtil.jsonArrayToList(getItemByParamLc);
        //test
//        String getItemByParamLctest = SPTSWebService.getItemByParam(paramLc).getJSONObject(0).getString("ItemID");
//        LOGGER.info("getItemByParamLctest" + getItemByParamLctest);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pairingType = sDAO.getGroupParameterDetailList("", "018");

        CardPairingDAO cardD = new CardPairingDAO();
        String pairId = cardD.getNextPairId();

        model.addAttribute("itemListPc", itemListPc);
        model.addAttribute("itemListLc", itemListLc);
        model.addAttribute("pairingType", pairingType);
        model.addAttribute("pairId", pairId);

        return "cardPairing/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String pairId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String loadCard,
            @RequestParam(required = false) String programCard
    ) {

        CardPairing cardPairing = new CardPairing();
        cardPairing.setPairId(pairId);
        cardPairing.setType(type);
        cardPairing.setLoadCard(loadCard);
        cardPairing.setProgramCard(programCard);
        cardPairing.setCreatedBy(userSession.getFullname());

        CardPairingDAO cardDAO = new CardPairingDAO();
        int countLc = cardDAO.getCountLoadCard(loadCard);
        cardDAO = new CardPairingDAO();
        int countPc = cardDAO.getCountProgramCard(programCard);
        LOGGER.info("countPc---" + countPc);
        LOGGER.info("programCard---" + programCard);
        LOGGER.info("countLc---" + countLc);
        LOGGER.info("loadCard---" + loadCard);
        args = new String[1];
        args[0] = pairId + " - " + type;

        //single loadcard
        if ("SINGLE".equals(type) && "".equals(programCard)) {
            if (countLc < 1) {
                CardPairingDAO cardPairingDAO = new CardPairingDAO();
                QueryResult queryResult = cardPairingDAO.insertCardPairing(cardPairing);
                if (queryResult.getGeneratedKey().equals("0")) {

                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("cardPairing", cardPairing);
                    return "cardPairing/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                    return "redirect:/admin/cardPairing/edit/" + queryResult.getGeneratedKey();
                }
            } else {
                redirectAttrs.addFlashAttribute("error", "Bib Card already in the list. Please re-check.");
                model.addAttribute("cardPairing", cardPairing);
                return "redirect:/admin/cardPairing/add";
            }
            //single programcard
        } else if ("SINGLE".equals(type) && "".equals(loadCard)) {
            if (countPc < 1) {
                CardPairingDAO cardPairingDAO = new CardPairingDAO();
                QueryResult queryResult = cardPairingDAO.insertCardPairing(cardPairing);
                if (queryResult.getGeneratedKey().equals("0")) {

                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("cardPairing", cardPairing);
                    return "cardPairing/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                    return "redirect:/admin/cardPairing/edit/" + queryResult.getGeneratedKey();
                }
            } else {
                redirectAttrs.addFlashAttribute("error", "Bib Card already in the list. Please re-check.");
                model.addAttribute("cardPairing", cardPairing);
                return "redirect:/admin/cardPairing/add";
            }
        } else if ("PAIR".equals(type)) {
            if (countLc < 1 && countPc < 1) {
                CardPairingDAO cardPairingDAO = new CardPairingDAO();
                QueryResult queryResult = cardPairingDAO.insertCardPairing(cardPairing);
                if (queryResult.getGeneratedKey().equals("0")) {

                    model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    model.addAttribute("cardPairing", cardPairing);
                    return "cardPairing/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
                    return "redirect:/admin/cardPairing/edit/" + queryResult.getGeneratedKey();
                }

            } else {
//            model.addAttribute("error","Bib Card already in the list. Please re-check.");
                redirectAttrs.addFlashAttribute("error", "Bib Card already in the list. Please re-check.");
                model.addAttribute("cardPairing", cardPairing);
//            return "cardPairing/add";
                return "redirect:/admin/cardPairing/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Fail to save. Please re-check.");
            model.addAttribute("cardPairing", cardPairing);
//            return "cardPairing/add";
            return "redirect:/admin/cardPairing/add";
        }

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("id") String id
    ) throws IOException {
        CardPairingDAO cardPairingDAO = new CardPairingDAO();
        CardPairing cardPairing = cardPairingDAO.getCardPairing(id);

        JSONObject paramPc = new JSONObject();
        paramPc.put("itemType", "BIB Card");
        paramPc.put("itemStatus", "0");
        paramPc.put("itemID", "PC%");
        JSONArray getItemByParamPc = SPTSWebService.getItemByParam(paramPc);
        List<LinkedHashMap<String, String>> itemListPc = SystemUtil.jsonArrayToList(getItemByParamPc);

        JSONObject paramLc = new JSONObject();
        paramLc.put("itemType", "BIB Card");
        paramLc.put("itemStatus", "0");
        paramLc.put("itemID", "LC%");
        JSONArray getItemByParamLc = SPTSWebService.getItemByParam(paramLc);
        List<LinkedHashMap<String, String>> itemListLc = SystemUtil.jsonArrayToList(getItemByParamLc);

        ParameterDetailsDAO sDAO = new ParameterDetailsDAO();
        List<ParameterDetails> pairingType = sDAO.getGroupParameterDetailList(cardPairing.getType(), "018");

        model.addAttribute("itemListPc", itemListPc);
        model.addAttribute("itemListLc", itemListLc);
        model.addAttribute("pairingType", pairingType);
        model.addAttribute("cardPairing", cardPairing);
        return "cardPairing/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String pairId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String loadCard,
            @RequestParam(required = false) String programCard
    ) {
        CardPairing cardPairing = new CardPairing();
        cardPairing.setId(id);
        cardPairing.setPairId(pairId);
        cardPairing.setType(type);
        cardPairing.setLoadCard(loadCard);
        cardPairing.setProgramCard(programCard);
        CardPairingDAO cardDAO = new CardPairingDAO();
        int countLc = cardDAO.getCountLoadCardNEId(loadCard, id);
        cardDAO = new CardPairingDAO();
        int countPc = cardDAO.getCountProgramCardNEId(programCard, id);
        args = new String[1];
        args[0] = pairId + " - " + type;
        if (countLc == 0 && countPc == 0) {
            CardPairingDAO cardPairingDAO = new CardPairingDAO();
            QueryResult queryResult = cardPairingDAO.updateCardPairing(cardPairing);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
            } else {
                redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Bib Card already in the list. Please re-check.");
        }

        return "redirect:/admin/cardPairing/edit/" + id;
    }

    @RequestMapping(value = "/delete/{cardPairingId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("cardPairingId") String cardPairingId
    ) {
        CardPairingDAO cardPairingDAO = new CardPairingDAO();
        CardPairing cardPairing = cardPairingDAO.getCardPairing(cardPairingId);
        cardPairingDAO = new CardPairingDAO();
        QueryResult queryResult = cardPairingDAO.deleteCardPairing(cardPairingId);
        args = new String[1];
        args[0] = cardPairing.getPairId() + " - " + cardPairing.getType();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/admin/cardPairing";
    }

    @RequestMapping(value = "/view/{cardPairingId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("cardPairingId") String cardPairingId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/admin/cardPairing/viewCardPairingPdf/" + cardPairingId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/cardPairing";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.cardPairing");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewCardPairingPdf/{cardPairingId}", method = RequestMethod.GET)
    public ModelAndView viewCardPairingPdf(
            Model model,
            @PathVariable("cardPairingId") String cardPairingId
    ) {
        CardPairingDAO cardPairingDAO = new CardPairingDAO();
        CardPairing cardPairing = cardPairingDAO.getCardPairing(cardPairingId);
        return new ModelAndView("cardPairingPdf", "cardPairing", cardPairing);
    }
}
