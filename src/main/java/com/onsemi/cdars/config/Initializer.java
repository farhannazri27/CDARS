package com.onsemi.cdars.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(1)
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{DatabaseConfig.class, SecurityConfig.class, MailConfig.class, HmsFtpConfig.class, ApprovalCronConfig.class};
            return new Class[]{DatabaseConfig.class, SecurityConfig.class, MailConfig.class, HmsFtpConfig.class, ApprovalCronConfig.class, FtpConfigUSL24hrs.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ApplicationConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
