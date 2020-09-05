
/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author neeljain
 */

package com.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping("Forgotpassword")
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
			System.out.println(url);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject("Forgotpassword");
			mailMessage.setFrom("blankteam9933@gmail.com");
			mailMessage.setText("click on the link for change your password:=>"+url+":8080/mentalcare/forgotPass?token=" + t.getToken());
			emailSenderService.sendEmail(mailMessage);
			m.addAttribute("message", "check your mail for futher process");
				return "successmail";
			}else {
			m.addAttribute("message", "Email not Registered");
			return "getemail";
		}
		

	}
	
	@RequestMapping(value="forgotPass", method= {RequestMethod.GET, RequestMethod.POST})
	public String conform(@RequestParam("token")String confirmationToken,Model m) {
		Token t = tokenImpl.findToken(confirmationToken);
		if(t != null) {
			 m.addAttribute("token",t.getToken());	
			 return "ResetPassword";
		}else {
			m.addAttribute("message1","The link is invalid or broken!");
			return "successmail";
		}
	}
	
	@RequestMapping(value="resetpass", method= {RequestMethod.GET, RequestMethod.POST})
	public String reset(@RequestParam("pass")String pass,@RequestParam("token")String tk,Model m) {
		try {
			Token t = tokenImpl.findToken(tk);
			Users u = t.getU();
			System.out.println(u);
			u.setPass(pass);
			userImpl.createUser(u);
			tokenImpl.deletetoken(t);
		} catch (Exception e) {
			throw new RuntimeException();
		}	
		return "redirect:/login";
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
		return "redirect:/login";
	}
}
