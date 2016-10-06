package com.onsemi.cdars.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.cdars.model.JSONResponse;
import com.onsemi.cdars.model.UserSession;
import com.onsemi.cdars.tools.SPTSResponse;
import com.onsemi.cdars.tools.SPTSWebService;
import com.onsemi.cdars.tools.SystemUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/spts")
@SessionAttributes({"userSession"})
public class SPTSController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SPTSController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String main(
            Model model
    ) {
        return "spts/spts";
    }

    @RequestMapping(value = "/getitemall", method = RequestMethod.GET)
    public String getItemAll(
            Model model,
            HttpServletRequest request
    ) throws IOException {
        JSONArray getItemAll = SPTSWebService.getItemAll();
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        //Limit for testing
        int limit = 20;
        //No limit for production
        //int limit = getItemAll.length();
        for (int i = 0; i < limit; i++) {
            JSONObject jsonObject = getItemAll.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        model.addAttribute("itemList", itemList);
        return "spts/itemlist";
    }

    @RequestMapping(value = "/getitembyparam", method = RequestMethod.GET)
    public String getItemByParam(
            Model model,
            HttpServletRequest request
    ) throws IOException {
        JSONObject params = new JSONObject();
        //params.put("itemName", "SO8FL 15032A Stencil");
        //params.put("itemType", "Stencil");
        //params.put("itemStatus", "0");
        params.put("remarks", "CDARS_TEST");
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        model.addAttribute("itemList", itemList);
        return "spts/itemlist";
    }

    @RequestMapping(value = "/json/getitemall", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetItemAll(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONArray getItemAll = SPTSWebService.getItemAll();
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemAll.length(); i++) {
            JSONObject jsonObject = getItemAll.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }

    @RequestMapping(value = "/json/getitembyparam", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetItemByParam(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONObject params = new JSONObject();
        params.put("itemName", "SO8FL 15032A Stencil");
        params.put("itemType", "Stencil");
        params.put("itemStatus", "0");
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }
    
     @RequestMapping(value = "/json/getsfitembyparam", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetSfItemByParam(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONObject params = new JSONObject();
         params.put("itemPKID", "7687");
//           params.put("pkID", "2");
        JSONArray getItemByParam = SPTSWebService.getSFItemByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }
    
    @RequestMapping(value = "/json/gettransactionbyparam", method = RequestMethod.GET)
    public @ResponseBody
    JSONResponse jsonGetTransactionByParam(
            @ModelAttribute UserSession userSession,
            HttpServletRequest request
    ) throws IOException {
        JSONResponse response = new JSONResponse();
        JSONObject params = new JSONObject();
         params.put("itemsPKID", "7419");
//           params.put("pkID", "2");
        JSONArray getItemByParam = SPTSWebService.getTransactionByParam(params);
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemByParam.length(); i++) {
            JSONObject jsonObject = getItemByParam.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        response.setResult(itemList);
        response.setStatus(Boolean.TRUE);
        response.setStatusMessage("SUCCESS");
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(
            Model model
    ) throws IOException {
        //For testing
        //For production comment out from here...
        JSONObject sampleItem = new JSONObject();
        sampleItem.put("itemID", "AHWFB-24");
        sampleItem.put("itemName", "M_AHWFB-ACBH-48B22-00L00-0P00H31000T150-001");
        sampleItem.put("onHandQty", "1");
        sampleItem.put("prodQty", "0");
        sampleItem.put("repairQty", "0");
        sampleItem.put("otherQty", "0");
        sampleItem.put("quarantineQty", "0");
        sampleItem.put("externalCleaningQty", "0");
        sampleItem.put("externalRecleaningQty", "0");
        sampleItem.put("internalCleaningQty", "0");
        sampleItem.put("internalRecleaningQty", "0");
        sampleItem.put("storageFactoryQty", "0");
        sampleItem.put("minQty", "1");
        sampleItem.put("maxQty", "1");
        sampleItem.put("unit", "pcs");
        sampleItem.put("unitCost", "4000.00");
        sampleItem.put("rack", "AC15");
        sampleItem.put("shelf", "18");
        sampleItem.put("model", "ACS 22way 0.156in Connector (No resistor )");
        sampleItem.put("equipmentType", "Motherboard  (NO ASSY#)");
        sampleItem.put("stressType", "HTFB");
        sampleItem.put("isCritical", "false");
        sampleItem.put("isConsumeable", "false");
        sampleItem.put("itemType", "BIB");
        sampleItem.put("cardType", "N/A");
        sampleItem.put("bibPKID", "1");
        sampleItem.put("bibCardPKID", "1");
        sampleItem.put("remarks", "CDARS_TEST");
        sampleItem.put("downtimeValue", "1");
        sampleItem.put("downtimeUnit", "N/A");
        sampleItem.put("implementationCost", "200.00");
        sampleItem.put("manpowerValue", "1000.00");
        sampleItem.put("manpowerUnit", "N/A");
        sampleItem.put("complexityScore", "0");
        sampleItem.put("expirationDate", "2016-10-15");

        LinkedHashMap<String, String> item;
        ObjectMapper mapper = new ObjectMapper();
        item = mapper.readValue(sampleItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
        });

        model.addAttribute("item", item);
        //For production comment out until here...
        
        //RACK
        JSONArray getRackAll = SPTSWebService.getRackAll();
        List<LinkedHashMap<String, String>> rackList = SystemUtil.jsonArrayToList(getRackAll);
        model.addAttribute("rackList", rackList);
        //ITEM TYPE
        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();
        List<LinkedHashMap<String, String>> itemTypeList = SystemUtil.jsonArrayToList(getItemTypeAll);
        model.addAttribute("itemTypeList", itemTypeList);
        //CARD TYPE
        JSONArray getCardTypeAll = SPTSWebService.getCardTypeAll();
        List<LinkedHashMap<String, String>> cardTypeList = SystemUtil.jsonArrayToList(getCardTypeAll);
        model.addAttribute("cardTypeList", cardTypeList);
        return "spts/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model m,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String itemID,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String onHandQty,
            @RequestParam(required = false) String prodQty,
            @RequestParam(required = false) String repairQty,
            @RequestParam(required = false) String otherQty,
            @RequestParam(required = false) String quarantineQty,
            @RequestParam(required = false) String externalCleaningQty,
            @RequestParam(required = false) String externalRecleaningQty,
            @RequestParam(required = false) String internalCleaningQty,
            @RequestParam(required = false) String internalRecleaningQty,
            @RequestParam(required = false) String storageFactoryQty,
            @RequestParam(required = false) String minQty,
            @RequestParam(required = false) String maxQty,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String unitCost,
            @RequestParam(required = false) String rack,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String stressType,
            @RequestParam(required = false) String isCritical,
            @RequestParam(required = false) String isConsumeable,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String cardType,
            @RequestParam(required = false) String bibPKID,
            @RequestParam(required = false) String bibCardPKID,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String downtimeValue,
            @RequestParam(required = false) String downtimeUnit,
            @RequestParam(required = false) String implementationCost,
            @RequestParam(required = false) String manpowerValue,
            @RequestParam(required = false) String manpowerUnit,
            @RequestParam(required = false) String complexityScore,
            @RequestParam(required = false) String expirationDate
    ) throws IOException {
        JSONObject addItem = new JSONObject();
        addItem.put("itemID", itemID);
        addItem.put("itemName", itemName);
        addItem.put("onHandQty", onHandQty);
        addItem.put("prodQty", prodQty);
        addItem.put("repairQty", repairQty);
        addItem.put("otherQty", otherQty);
        addItem.put("quarantineQty", quarantineQty);
        addItem.put("externalCleaningQty", externalCleaningQty);
        addItem.put("externalRecleaningQty", externalRecleaningQty);
        addItem.put("internalCleaningQty", internalCleaningQty);
        addItem.put("internalRecleaningQty", internalRecleaningQty);
        addItem.put("storageFactoryQty", storageFactoryQty);
        addItem.put("minQty", minQty);
        addItem.put("maxQty", maxQty);
        addItem.put("unit", unit);
        addItem.put("unitCost", unitCost);
        addItem.put("rack", rack);
        addItem.put("shelf", shelf);
        addItem.put("model", model);
        addItem.put("equipmentType", equipmentType);
        addItem.put("stressType", stressType);
        addItem.put("isCritical", isCritical);
        addItem.put("isConsumeable", isConsumeable);
        addItem.put("itemType", itemType);
        addItem.put("cardType", cardType);
        addItem.put("bibPKID", bibPKID);
        addItem.put("bibCardPKID", bibCardPKID);
        addItem.put("remarks", remarks);
        addItem.put("downtimeValue", downtimeValue);
        addItem.put("downtimeUnit", downtimeUnit);
        addItem.put("implementationCost", implementationCost);
        addItem.put("manpowerValue", manpowerValue);
        addItem.put("manpowerUnit", manpowerUnit);
        addItem.put("complexityScore", complexityScore);
        addItem.put("expirationDate", expirationDate);

        SPTSResponse sr = SPTSWebService.insertItem(addItem);

        if (sr.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item added!");
            return "redirect:/spts/edit/" + sr.getResponseId();
        } else {
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            m.addAttribute("error", errorMessage);
            m.addAttribute("item", item);
            return "spts/add";
        }
    }

    @RequestMapping(value = "/edit/{pkID}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("pkID") String pkID
    ) throws IOException {
        JSONObject jsonObject = SPTSWebService.getItemByPKID(pkID);
        LinkedHashMap<String, String> item;
        ObjectMapper mapper = new ObjectMapper();
        item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
        });

        model.addAttribute("item", item);
        
        //RACK
        JSONArray getRackAll = SPTSWebService.getRackAll();
        List<LinkedHashMap<String, String>> rackList = SystemUtil.jsonArrayToList(getRackAll);
        model.addAttribute("rackList", rackList);
        //ITEM TYPE
        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();
        List<LinkedHashMap<String, String>> itemTypeList = SystemUtil.jsonArrayToList(getItemTypeAll);
        model.addAttribute("itemTypeList", itemTypeList);
        //CARD TYPE
        JSONArray getCardTypeAll = SPTSWebService.getCardTypeAll();
        List<LinkedHashMap<String, String>> cardTypeList = SystemUtil.jsonArrayToList(getCardTypeAll);
        model.addAttribute("cardTypeList", cardTypeList);
        return "spts/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model m,
            RedirectAttributes redirectAttrs,
            @RequestParam(required = false) String pkID,
            @RequestParam(required = false) String version,
            @RequestParam(required = false) String itemID,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String onHandQty,
            @RequestParam(required = false) String prodQty,
            @RequestParam(required = false) String repairQty,
            @RequestParam(required = false) String otherQty,
            @RequestParam(required = false) String quarantineQty,
            @RequestParam(required = false) String externalCleaningQty,
            @RequestParam(required = false) String externalRecleaningQty,
            @RequestParam(required = false) String internalCleaningQty,
            @RequestParam(required = false) String internalRecleaningQty,
            @RequestParam(required = false) String storageFactoryQty,
            @RequestParam(required = false) String minQty,
            @RequestParam(required = false) String maxQty,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String unitCost,
            @RequestParam(required = false) String rack,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String stressType,
            @RequestParam(required = false) String isCritical,
            @RequestParam(required = false) String isConsumeable,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String cardType,
            @RequestParam(required = false) String bibPKID,
            @RequestParam(required = false) String bibCardPKID,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String downtimeValue,
            @RequestParam(required = false) String downtimeUnit,
            @RequestParam(required = false) String implementationCost,
            @RequestParam(required = false) String manpowerValue,
            @RequestParam(required = false) String manpowerUnit,
            @RequestParam(required = false) String complexityScore,
            @RequestParam(required = false) String expirationDate
    ) throws IOException {
        JSONObject editItem = new JSONObject();
        editItem.put("pkID", pkID);
        editItem.put("version", version);
        editItem.put("itemID", itemID);
        editItem.put("itemName", itemName);
        editItem.put("onHandQty", onHandQty);
        editItem.put("prodQty", prodQty);
        editItem.put("repairQty", repairQty);
        editItem.put("otherQty", otherQty);
        editItem.put("quarantineQty", quarantineQty);
        editItem.put("externalCleaningQty", externalCleaningQty);
        editItem.put("externalRecleaningQty", externalRecleaningQty);
        editItem.put("internalCleaningQty", internalCleaningQty);
        editItem.put("internalRecleaningQty", internalRecleaningQty);
        editItem.put("storageFactoryQty", storageFactoryQty);
        editItem.put("minQty", minQty);
        editItem.put("maxQty", maxQty);
        editItem.put("unit", unit);
        editItem.put("unitCost", unitCost);
        editItem.put("rack", rack);
        editItem.put("shelf", shelf);
        editItem.put("model", model);
        editItem.put("equipmentType", equipmentType);
        editItem.put("stressType", stressType);
        editItem.put("isCritical", isCritical);
        editItem.put("isConsumeable", isConsumeable);
        editItem.put("itemType", itemType);
        editItem.put("cardType", cardType);
        editItem.put("bibPKID", bibPKID);
        editItem.put("bibCardPKID", bibCardPKID);
        editItem.put("remarks", remarks);
        editItem.put("downtimeValue", downtimeValue);
        editItem.put("downtimeUnit", downtimeUnit);
        editItem.put("implementationCost", implementationCost);
        editItem.put("manpowerValue", manpowerValue);
        editItem.put("manpowerUnit", manpowerUnit);
        editItem.put("complexityScore", complexityScore);
        editItem.put("expirationDate", expirationDate);

        SPTSResponse sr = SPTSWebService.updateItem(editItem);

        if (sr.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item updated!");
        } else {
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            redirectAttrs.addFlashAttribute("error", errorMessage);
        }
        return "redirect:/spts/edit/" + pkID;
    }

    @RequestMapping(value = "/delete/{pkID}/{version}", method = RequestMethod.GET)
    public String delete(
            RedirectAttributes redirectAttrs,
            @PathVariable("pkID") String pkID,
            @PathVariable("version") String version
    ) throws IOException {
        JSONObject item = new JSONObject();
        item.put("pkID", pkID);
        item.put("version", version);
        item.put("forceDelete", "false");

        SPTSResponse sr = SPTSWebService.deleteItem(item);
        
        if (sr.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item deleted!");
        } else {
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            redirectAttrs.addFlashAttribute("error", errorMessage);
        }
        return "redirect:/spts";
    }
    
    @RequestMapping(value = "/selectitem", method = RequestMethod.GET)
    public String selectItem(
            Model model,
            HttpServletRequest request
    ) throws IOException {
        JSONArray getItemAll = SPTSWebService.getItemAll();
        List<LinkedHashMap<String, String>> itemList = new ArrayList();
        for (int i = 0; i < getItemAll.length(); i++) {
            JSONObject jsonObject = getItemAll.getJSONObject(i);
            LinkedHashMap<String, String> item;
            ObjectMapper mapper = new ObjectMapper();
            item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            itemList.add(item);
        }
        model.addAttribute("itemList", itemList);
        return "spts/selectitem";
    }
    
    @RequestMapping(value = "/view/{pkID}", method = RequestMethod.GET)
    public String view(
            Model model, 
            HttpServletRequest request, 
            @PathVariable("pkID") String pkID
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/spts/viewItemPdf/" + pkID, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/spts";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "general.label.item_info");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewItemPdf/{pkID}", method = RequestMethod.GET)
    public ModelAndView viewHardwareQueuePdf(
            Model model, 
            @PathVariable("pkID") String pkID
    ) throws IOException {
        JSONObject jsonObject = SPTSWebService.getItemByPKID(pkID);
        LinkedHashMap<String, String> item;
        ObjectMapper mapper = new ObjectMapper();
        item = mapper.readValue(jsonObject.toString(), new TypeReference<LinkedHashMap<String, String>>() {
        });
        return new ModelAndView("itemPdf", "item", item);
    }
}
