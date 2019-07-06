package com.yb.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.yb.service.SysUserService;
import com.yb.util.Message;
import com.yb.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author yb
 * @date 2019/4/23
 */
@RestController
@RequestMapping("/login")
public class SysLoginController {

    @Autowired
    private Producer producer;
    @Autowired
    SysUserService userService;

    //生成验证码
    @RequestMapping("/kap")
    public void creatKap(HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String text = producer.createText();
        System.out.println(text);
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        BufferedImage image = producer.createImage(text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.flush();
    }

    @RequestMapping("/userLogin")
    public Message userLogin(@RequestBody Map<String, String> map) {
        String userNumber = map.get("usernumber");
        String userPwd = map.get("userpwd");
        String kap = map.get("kap");
        String remberme = map.get("remberme");
        boolean rember = false;
        if (remberme != null) {
            rember = true;
        }
        String kaptcha = (String) ShiroUtils.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!kap.equals(kaptcha)) {
            return Message.error("验证码错误");
        }
        try {
            Subject subject = ShiroUtils.getSubject();
            Md5Hash md5Hash = new Md5Hash(userPwd, userNumber, 1024);
            UsernamePasswordToken token = new UsernamePasswordToken(userNumber, md5Hash.toHex());
            token.setRememberMe(rember);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return Message.error("账户不存在");
        } catch (IncorrectCredentialsException e) {
            return Message.error(e.getMessage());
        } catch (LockedAccountException e) {
            return Message.error("账户被锁");
        } catch (AuthenticationException e) {
            return Message.error("账户验证失败");
        }
        return Message.ok();
    }



}
