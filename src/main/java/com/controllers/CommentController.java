/*
 * Copyright 2020 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommentDao;
import com.dao.PostImpl;
import com.dao.UserImpl;
import com.model.Comment;
import com.model.Post;
import com.model.Response;
import com.model.Users;

/**
 * this class is comment controller handling the endPoins of comment related
 * stuff
 * 
 * @author neeljain
 *
 */

@Controller
public class CommentController {

	@Autowired
	UserImpl userImpl;

	@Autowired
	PostImpl postImpl;

	@Autowired
	CommentDao commentImpl;

	@PostMapping(value = "post/{pid}/comment", consumes = { MediaType.ALL_VALUE })
	@ResponseBody
	public Response postcomment(@RequestBody String com, BindingResult result, @PathVariable("pid") int pid,
			Principal principal, Model m) {
		Response r = new Response();
		Optional<Post> getbyid = postImpl.getbyid(pid);
		if (getbyid.isPresent()) {
		    Post p = getbyid.get();
	        Users u = userImpl.getbyEmail(principal.getName());
	        Comment c = new Comment();
	        c.setReview(com.split("=")[1]);
	        c.setUser(u);
	        c.setPost(p);
	        commentImpl.createComment(c);
	        r.setStatus("done");
	        return r;
        }else {
            return r;
        }
        
	}

	@PostMapping(value = "post/{pid}/{cid}/comment", consumes = { MediaType.ALL_VALUE })
	@ResponseBody
	public Response replycomment(@RequestBody String review, @PathVariable("pid") int pid, @PathVariable("cid") int cid,
			Principal principal) {
		Response r = new Response();
		Optional<Post> getbyid = postImpl.getbyid(pid);
        if (getbyid.isPresent()) {
		Users u = userImpl.getbyEmail(principal.getName());
		Comment pcmnt = commentImpl.getcommentById(cid);
		Comment c = new Comment();
		c.setReview(review.split("=")[1]);
		c.setPost(getbyid.get());
		c.setComment(pcmnt);
		c.setUser(u);
		commentImpl.createComment(c);
		r.setStatus("done");
	     return r;
        }else {
            return r;
        }
	}

	@GetMapping("post/deleteComment/{cid}/{pid}")
	@ResponseBody
	public String deletecomment(@PathVariable int cid, @PathVariable int pid) {
		commentImpl.deletecomment(cid);
		return "done";
	}

	@GetMapping("post/getallcomment/{pid}")
	public String allcomment(@PathVariable int pid, Model m, Principal principal) {
		Users u = userImpl.getbyEmail(principal.getName());
		Optional<Post> getbyid = postImpl.getbyid(pid);
        if (getbyid.isPresent()) {
		m.addAttribute("user", u);
		m.addAttribute("post", getbyid.get());
		m.addAttribute("comments", getbyid.get().getComment());
		m.addAttribute("comment", new Comment());
		return "comment";
        }else {
            return "not present";
        }
	}

}
