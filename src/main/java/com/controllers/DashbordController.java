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

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.CommentDao;
import com.dao.EmailSender;
import com.dao.PostImpl;
import com.dao.UserImpl;
import com.model.Comment;
import com.model.Post;
import com.model.Users;

/**
 * this class is dashboard controller handling the endPoins of dashboard related stuff
 * @author neeljain
 *
 */

@Controller
public class DashbordController implements ErrorController {

	@Autowired
	PostImpl postImpl;

	@Autowired
	UserImpl userImpl;

	@Autowired
	CommentDao commentImpl;
	
	@Autowired
	EmailSender emailSenderService;

	@RequestMapping("/")
	public String home(Model model) {
		List<Post> allpost = postImpl.findAllPost();
		model.addAttribute("posts", allpost);
		model.addAttribute("home", "homepage");
		model.addAttribute("post", new Post());
		return "home";
	}

	@RequestMapping("userpost")
	public String userpost(Model m, Principal principal) {
		Users u = userImpl.getbyEmail(principal.getName());
		List<Post> allpost = postImpl.findPostByuser(u);
		m.addAttribute("posts", allpost);
		m.addAttribute("user", u);
		m.addAttribute("comment", new Comment());
		return "userpost";
	}
	
	@RequestMapping("response")
	public String response(Model m, Principal principal) {
		Users u = userImpl.getbyEmail(principal.getName());
		Set<Post> commentedpost = commentImpl.findCommentByuser(u.getId());
		m.addAttribute("posts", commentedpost);
		m.addAttribute("user", u);
		m.addAttribute("comment", new Comment());
		return "usercommentedpost";
	}

	
	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error() {
		return "errorpage";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
