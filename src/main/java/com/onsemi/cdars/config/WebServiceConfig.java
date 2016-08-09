/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.config;

import com.onsemi.cdars.tools.SptsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

/**
 *
 * @author fg79cj
 */
@Configuration
public class WebServiceConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.onsemi.cdars.wsdl");
        return marshaller;
    }

//    @Bean
//    public SaajSoapMessageFactory soupMessageFactory() {
//
//        SaajSoapMessageFactory version = new SaajSoapMessageFactory();
//        version.setSoapVersion(SoapVersion.SOAP_12);
//        return version;
//    }
//    @Bean
//    public WebServiceTemplate webServiceTemplate() {
//
//        WebServiceTemplate wsclient = new WebServiceTemplate();
//        wsclient.setMarshaller(getMarshaller());
//        wsclient.setUnmarshaller(getMarshaller());
////        wsclient.setDefaultUri("http://mysed-rel-spv2/SPTSServices/SPTSServices.asmx?WSDL");
//        wsclient.setDefaultUri("http://tempuri.org");
//
//        return wsclient;
//    }
@Bean
        public SptsClient sptsClient(Jaxb2Marshaller marshaller) {
		SptsClient client = new SptsClient();
		client.setDefaultUri("http://tempuri.org");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
