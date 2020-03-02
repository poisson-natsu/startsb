package com.resouces.code.controller;
import com.resouces.code.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MailController {

    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(@RequestParam String sender,
                       @RequestParam String receiver,
                       @RequestParam String title,
                       @RequestParam String text,
                       @RequestParam MultipartFile file) {

        String result = sendEmailService.send(sender, receiver, title, text);

        System.out.println("file-content-type:"+file.getContentType());

        return result;
    }

    @RequestMapping(value = "/sendHTML", method = RequestMethod.GET)
    public String sendHtml(@RequestParam String sender,
                           @RequestParam String receiver,
                           @RequestParam String subject,
                           @RequestParam String context) {
        String content = "<html>\n"+
                "<body>\n"+
                "<h2>这是一封有历史意义的HTML邮件,请注意查收!!!</h2>\n"+
                "</body>\n"+
                "</html>";
        try {

            String html = sendEmailService.sendHtml(sender, receiver, subject, context);
            return html;
        }catch (Exception ex) {
            return "failure";
        }
    }


    /**
     * 发送多附件加文本
     * @param sender
     * @param receiver
     * @param title
     * @param text
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendMulti", method = RequestMethod.POST)
    public String sendMulti(@RequestParam String sender,
                            @RequestParam String receiver,
                            @RequestParam String title,
                            @RequestParam(required = false) String text,
                            HttpServletRequest request) {

        String result = null;
        List<MultipartFile> fileList = new ArrayList<>();
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
                MultiValueMap<String, MultipartFile> multiFileMap = mRequest.getMultiFileMap();
                Set<String> keys = multiFileMap.keySet();
                for (String key : keys) {
                    fileList.addAll(multiFileMap.get(key));
                }
                MultipartFile[] lists = fileList.toArray(new MultipartFile[fileList.size()]);
                System.out.println(lists);
                result = sendEmailService.sendMultiMail(sender,receiver,title, text,lists);
            }else {
                result = sendEmailService.send(sender, receiver, title, text);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "send multi mail failure";
        }

        return result;
    }

}
