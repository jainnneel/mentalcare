package com.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dao.CommentDao;
import com.dao.EmailSender;
import com.dao.PostImpl;
import com.dao.UserImpl;
import com.dto.CommentDto;
import com.dto.PostDto;
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
	
	String value = "posts";
	
	@GetMapping("/")
	public String home(Model model) {
		List<Post> allpost = postImpl.findAllPost();
		model.addAttribute("posts", allpost);
		model.addAttribute("home", "homepage");
		model.addAttribute("post", new PostDto());
		return "home";
	}

	@GetMapping("userpost")
	public String userpost(Model m, Principal principal) {
		Users u = userImpl.getbyEmail(principal.getName());
		List<Post> allpost = postImpl.findPostByuser(u);
		m.addAttribute(value, allpost);
		m.addAttribute("user", u);
		m.addAttribute("comment", new CommentDto());
		return "userpost";
	}
	
	@GetMapping("response")
	public String response(Model m, Principal principal) {
		Users u = userImpl.getbyEmail(principal.getName());
		Set<Post> commentedpost = commentImpl.findCommentByuser(u.getId());
		m.addAttribute(value, commentedpost);
		m.addAttribute("user", u);
		m.addAttribute("comment", new CommentDto());
		return "usercommentedpost";
	}

	
	@GetMapping(value = "/error")
	public String error() {
		return "errorpage";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
