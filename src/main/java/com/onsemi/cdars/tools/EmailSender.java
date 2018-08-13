package com.onsemi.cdars.tools;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import com.onsemi.cdars.dao.EmailDAO;
import com.onsemi.cdars.model.Email;
import com.onsemi.cdars.model.User;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class EmailSender extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private final String emailTemplate = "resources/email";
    //private final String logoPath = "/resources/public/img/spml_all.png";
    private final String logoPath = "/resources/img/cdars_logo.png";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public EmailSender() {
    }

    public void textEmail(final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(env.getProperty("mail.sender"));
                message.setTo(to);
                message.setSubject(subject);
                message.setText(msg);
                mailSender.send(message);
            }
        }).start();
    }

    public void mimeEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MimeMessagePreparator preparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                        message.setTo(to);
                        message.setFrom(env.getProperty("mail.sender"));
                        message.setSubject(subject);
                        Map model = new HashMap();
                        model.put("user", user.getFullname());
                        model.put("subject", subject);
                        model.put("message", msg);
                        Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                        freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                        message.setText(text, true);
                    }
                };
                mailSender.send(preparator);
            }
        }).start();
    }

    public void htmlEmail(final ServletContext servletContext, final User user, final String to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailManyTo(final ServletContext servletContext, final User user, final String[] to, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailManyToWithCc(final ServletContext servletContext, final User user, final String[] to, final String[] cc, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.addCc(cc);
                    htmlEmail.setSubject(subject);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachment(final ServletContext servletContext, final User user, final String[] to, final File file, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

//                    File file = new File("C:\\test.csv");
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
                    htmlEmail.embed(file);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachmentWithCc(final ServletContext servletContext, final User user, final String[] to, final String[] cc, final File file, final String subject, final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

//                    File file = new File("C:\\test.csv");
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.addCc(cc);
                    htmlEmail.setSubject(subject);
                    htmlEmail.embed(file);

                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("template.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }

    public void htmlEmailWithAttachmentAndEmbedImage(final ServletContext servletContext, final User user, final String[] to, final File file, final String subject, final String msg, final String msg2, final String img, final String img2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlEmail htmlEmail = new HtmlEmail();
                    EmailDAO emailDAO = new EmailDAO();
                    Email email = emailDAO.getEmail();

                    htmlEmail.setHostName(email.getHost());
                    htmlEmail.setSmtpPort(email.getPort());
                    htmlEmail.setAuthenticator(new DefaultAuthenticator(email.getUsername(), email.getPassword()));
                    htmlEmail.setSSLOnConnect(true);
                    htmlEmail.setDebug(true);

//                    MimeMultipart content = new MimeMultipart("related");
//                    File file = new File("C:\\test.csv");
//// Image part
//                    MimeBodyPart imagePart = new MimeBodyPart();
//                    imagePart.attachFile(imagePath);
//                    imagePart.setContentID("<" + cid + ">");
//                    imagePart.setDisposition(MimeBodyPart.INLINE);
//                    content.addBodyPart(imagePart);
//
                    htmlEmail.setFrom(email.getSender());
                    htmlEmail.addTo(to);
                    htmlEmail.setSubject(subject);
                    htmlEmail.embed(file);
//                    htmlEmail.setContent(content);
                    String logo = servletContext.getRealPath(logoPath);
                    File logoFile = new File(logo);
                    String logoCid = htmlEmail.embed(logoFile);

                    String logo2 = img;
                    File logoFile2 = new File(logo2);
                    String img = htmlEmail.embed(logoFile2);

                    String logo3 = img2;
                    File logoFile3 = new File(logo3);
                    String img2 = htmlEmail.embed(logoFile3);

                    Map model = new HashMap();
                    model.put("user", user.getFullname());
                    model.put("subject", subject);
                    model.put("message", msg);
                    model.put("logoCid", logoCid);
                    model.put("img", img);
                    model.put("message2", msg2);
                    model.put("img2", img2);
                    Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_22);
                    freemarkerConfiguration.setServletContextForTemplateLoading(servletContext, emailTemplate);
                    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("templateTest.html"), model);
                    htmlEmail.setHtmlMsg(msgContent);
                    String send = htmlEmail.send();
                    LOGGER.info("EMAIL SENDER: " + send);
                } catch (EmailException e) {
                    LOGGER.error(e.getMessage());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                } catch (TemplateException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();
    }
}
