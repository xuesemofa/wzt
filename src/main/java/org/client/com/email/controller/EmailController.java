package org.client.com.email.controller;

import org.client.com.util.email.EmailUtil;
import org.client.com.util.resultJson.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * email test
 * 带有附件方法
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    @Value("${spring.mail.username}")
    private String forEmail;
    @Value("${filesPath}")
    private String filesPath;

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private ResponseResult result;

//    @RequestMapping("/toemail")
//    public boolean toEmail() {
//        try {
//            final MimeMessage mimeMessage = mailSender.createMimeMessage();
//            final MimeMessageHelper message = new MimeMessageHelper(
//                    mimeMessage, true);
//            message.setFrom(forEmail);
//            message.setTo("xuesemofa@126.com");
//            // 邮件主题
//            message.setSubject("逸云平台注册");
////            模版
//            //初始化参数
//            Properties properties = new Properties();
//            //设置velocity资源加载方式为file
//            properties.setProperty("resource.loader", "file");
//            //设置velocity资源加载方式为file时的处理类
//            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
//            //实例化一个VelocityEngine对象
//            VelocityEngine velocityEngine = new VelocityEngine(properties);
//            VelocityEngine velocity = new VelocityEngine();
//            Map<String, Object> model = new HashedMap();
//            model.put("emailname", "赵越");
//            String text = VelocityEngineUtils.mergeTemplateIntoString(
//                    velocity, "model.vm", "UTF-8", model);
//            message.setText(text.toString(), true);
////插入图片
//            FileSystemResource img1 = new FileSystemResource(new File(filesPath, "qqq.png"));
//            message.addInline("backimg", img1);
//
//
////            附件1
//            FileSystemResource file1 = new FileSystemResource(new File(filesPath, "123.zip"));
//            message.addAttachment("压缩包附件.zip", file1);
//            mailSender.send(mimeMessage);
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }

    @RequestMapping(value = "/toemail")
    public ResponseResult regitEmail(@RequestParam("title") String title,@RequestParam("toUser") String toUser) {
        EmailUtil emailUtil = new EmailUtil();
        boolean b = emailUtil.toEmail(mailSender, title, "您本次验证码为<span style='red'>123</span>", forEmail, toUser);
        result.setSuccess(b);
        return result;
    }

}
