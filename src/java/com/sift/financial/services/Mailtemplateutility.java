package com.sift.financial.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;

public class Mailtemplateutility {
	
    @Autowired
    private Configuration freemarkerConfiguration;
	
    public  String outputhtmlcfg(Map<String, Object> map, String tmpname)
    {
		String mailBody = "";
		try
                {
		
                    Template template = freemarkerConfiguration.getTemplate(tmpname);
                    //Console output
                    StringWriter sw = new StringWriter();
                    template.process(map, sw);
                    sw.flush();
                    return sw.toString();
        
                } 
                catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();

                 }
		return mailBody;
	}
	
}
