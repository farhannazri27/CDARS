package com.onsemi.cdars.tools;

import java.io.IOException;
import java.util.Iterator;
import org.json.JSONObject;

public class SPTSRequestXML_bk {

    public static String getItemAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemByPKID(String pkID) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemByPKID xmlns=\"http://tempuri.org/\">"
                + "<pkID>" + pkID + "</pkID>"
                + "</GetItemByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetItemByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<UpdateItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</UpdateItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }
    
    public static String insertTransaction(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertTransaction xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertTransaction>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }
    
    public static String getSFItemByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetSFItemByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetSFItemByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertSFItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertSFItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertSFItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateSFItemLocation(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<UpdateSFItemLocation xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</UpdateSFItemLocation>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteSFItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteSFItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteSFItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }
    
    public static String insertActivityLog(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertActivityLog xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertActivityLog>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }
}
