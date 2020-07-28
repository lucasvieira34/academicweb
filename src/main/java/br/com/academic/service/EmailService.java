package br.com.academic.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.academic.models.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;
    
    @Value("classpath:/static/img/logo.jpg")
    Resource logo;
    
    @Value("classpath:/static/img/social-facebook.svg")
    Resource facebook;
    
    @Value("classpath:/static/img/social-twitter.svg")
    Resource twitter;
    
    @Value("classpath:/static/img/social-youtube.svg")
    Resource youtube;

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        
    	MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Template t = freemarkerConfig.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        helper.addInline("logo.jpg", logo);
        helper.addInline("social-facebook.svg", facebook);
        helper.addInline("social-twitter.svg", twitter);
        helper.addInline("social-youtube.svg", youtube);

        emailSender.send(message);
    }
    
    public void sendEmail(Mail mail) {
    	
    	try {
	    	MimeMessage message = emailSender.createMimeMessage();
	    	MimeMessageHelper helper = new MimeMessageHelper(message,
	    			MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	    			StandardCharsets.UTF_8.name());
	    	
	    	Template t = freemarkerConfig.getTemplate("email-template.ftl");
	    	String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
	    	
	    	helper.setTo(mail.getTo());
	    	helper.setText(html, true);
	    	helper.setSubject(mail.getSubject());
	    	helper.setFrom(mail.getFrom());
	    	
	    	emailSender.send(message);
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    
    

}