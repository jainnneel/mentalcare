/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author neeljain.
 */

package com.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.EmailSender;
import com.dao.TokenImpl;
import com.dao.UserImpl;
import com.dto.UserDto;
import com.model.Response;
import com.model.Token;
import com.model.Users;

/**
 * this class is register controller handling the endPoins of register related
 * stuff.
 * 
 * @author neeljain
 */

@Controller
public class RegController {

	UserImpl userImpl;

	public RegController(UserImpl userImpl) {
		super();
		this.userImpl = userImpl;
	}

	@Autowired
	TokenImpl ts;

	@Autowired
	EmailSender emailSenderService;

	String register = "register";
	
	@GetMapping("register")
	public String register(Model model) {
		model.addAttribute("user", new UserDto());
		return register;
	}

	@PostMapping(value="checkemail",consumes = MediaType.ALL_VALUE )
	@ResponseBody
    public Response checkemail(@RequestBody Object email ) {
	    Users Optionaluser = userImpl.getbyEmail(email.toString());
	    Response r = new Response();
	    if(Optionaluser!=null) {
	        r.setStatus("nottt");
	    }else {
	        r.setStatus("yesss");
	    }
	    return r;
    }

	@PostMapping("/register")
	public String userReg(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model m,
			HttpServletRequest request) {

		Users Optionaluser = userImpl.getbyEmail(user.getEmail());

		if (Optionaluser != null) { 
			m.addAttribute("exists", "This email already exists!");
			return register;
		} else {
			if (result.hasErrors()) {
				m.addAttribute("user", user);
				return register;
			}
			Users usercreated = userImpl.createUser(user);

			Token t = new Token(usercreated);
			ts.createToken(t);
			String url = request.getScheme() + "://" + request.getServerName();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(usercreated.getEmail());
			mailMessage.setSubject("Email verification");
			mailMessage.setFrom("blankteam9933@gmail.com");
			mailMessage.setText("click on the link for verification:=>" + url
					+ ":8080/mentalcare/confirm-account?token=" + t.getEmailToken());
			emailSenderService.sendEmail(mailMessage);
			m.addAttribute("message", "check your mail for futher process");
			m.addAttribute("email", user.getEmail());
			m.addAttribute("message", "User register successfully conform email address");
			return "successmail";
		}
	}

	@GetMapping(value = "confirm-account")
	public String conform(@RequestParam("token") String confirmationToken, Model model) {
		Token t = ts.findToken(confirmationToken);
		if (t != null) {
            Users u = userImpl.getbyEmail(t.getU().getEmail());
			u.setEnable(true);
			userImpl.createUserSave(u);
			model.addAttribute("message2", "User successfully verified");
			return "User-Verified";
		} else {
			model.addAttribute("message1", "The link is invalid or broken!");
			return "successmail";
		}
	}

	@GetMapping("login")
	public String login(Principal principal) {
		if (principal == null) {
			return "login";
		} else {
			return "redirect:/";
		}
	}

}
