package com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.EmailSender;
import com.dao.TokenImpl;
import com.dao.UserImpl;
import com.model.Token;
import com.model.Users;

/**
 * this class is password controller handling the endPoins of password related stuff
 * @author neeljain
 *
 */

@Controller
public class PasswordController {

	
	@Autowired
	UserImpl userImpl;

	@Autowired
	TokenImpl tokenImpl;
	
	@Autowired
	EmailSender emailSenderService;
	
	@GetMapping("Forgotpassword")
	public String forgot() {
		return "getemail";
	}

	@PostMapping("sendmailforget")
	public String emailsend(@RequestParam("email") String email, Model m,HttpServletRequest request) {
		
		Users getbyEmail = userImpl.getbyEmail(email);
		if (getbyEmail != null) {
			Token t= new Token(getbyEmail);
			tokenImpl.createToken(t);
			String url = 	request.getScheme() + "://" + request.getServerName();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject("Forgotpassword");
			mailMessage.setFrom("blankteam9933@gmail.com");
			mailMessage.setText("click on the link for change your password:=>"+url+":8080/mentalcare/forgotPass?token=" + t.getEmailToken());
			emailSenderService.sendEmail(mailMessage);
			m.addAttribute("message", "check your mail for futher process");
				return "successmail";
			}else {
			m.addAttribute("message", "Email not Registered");
			return "getemail";
		}
		

	}
	
	@PostMapping(value="forgotPass")
	public String conform(@RequestParam("token")String confirmationToken,Model m) {
		Token t = tokenImpl.findToken(confirmationToken);
		if(t != null) {
			 m.addAttribute("token",t.getEmailToken());	
			 return "ResetPassword";
		}else {
			m.addAttribute("message1","The link is invalid or broken!");
			return "successmail";
		}
	}
	
	@PostMapping(value="resetpass")
	public String reset(@RequestParam("pass")String pass,@RequestParam("token")String tk,Model m) {
			Token t = tokenImpl.findToken(tk);
			Users u = t.getU();
			u.setPass(pass);
			userImpl.createUserSave(u);
			tokenImpl.deletetoken(t); 
			
		return "redirect:/login";
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
		return "redirect:/login";
	}
}
