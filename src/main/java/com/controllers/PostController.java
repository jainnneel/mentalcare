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
 */

package com.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommentDao;
import com.dao.PostImpl;
import com.dao.UserImpl;
import com.model.Comment;
import com.model.Post;
import com.model.Response;
import com.model.Users;

/**
 * this class is post controller handling the endPoins of post related stuff
 * 
 * @author neeljain
 *
 */

@Controller
public class PostController {

    @Autowired
    PostImpl postImpl;

    @Autowired
    UserImpl userImpl;

    @Autowired
    CommentDao commentImpl;

    @PostMapping(value = "post", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public Response postdata(@Valid @RequestBody Post p, BindingResult result, Principal principal, Model m) {
        Response r = new Response();
        if (principal == null) {
            r.setStatus("not");
            return r;
        }
        Users u = userImpl.getbyEmail(principal.getName());
        if (result.hasErrors()) {
            m.addAttribute("post", p);
            List<Post> allpost = postImpl.findAllPost();
            m.addAttribute("posts", allpost);
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            r.setStatus("notdone");
            r.setErrorMessages(errors);
            return r;
        }

        p.setD(new Date());
        p.setUser(u);
        postImpl.createPost(p);
        r.setStatus("done");

        return r;
    }

    @RequestMapping("post/{pid}")
    public String post(@PathVariable int pid, Model m, HttpServletResponse response, Principal principal)
            throws IOException {
        Users user = userImpl.getbyEmail(principal.getName());
        System.out.println(user);
        Optional<Post> p = postImpl.getbyid(pid);
        System.out.println(p.get());
        if (p.isPresent()) {
            m.addAttribute("post", p.get());
            m.addAttribute("user", user);
            m.addAttribute("comment", new Comment());
            m.addAttribute("comments", p.get().getComment());
            return "Uniquepost";
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Post with id " + pid + " was not found");
            return "Uniquepost";
        }

    }

    @PostMapping("updatepost")
    public String postUpdate(@Valid @ModelAttribute("post") Post p, BindingResult result, Model m,
            Principal principal) {
        Users u = userImpl.getbyEmail(principal.getName());
        System.out.println(result);
        System.out.println(p);
        p.setUser(u);
        if (result.hasErrors()) {
            m.addAttribute("user", u);
            m.addAttribute("post", p);
            return "Uniquepost";
        }
        p.setD(new Date());
        Post updatedpost = postImpl.createPost(p);
        return "redirect:/post/" + updatedpost.getId();
    }

    @RequestMapping("post/delete/{id}")
    public String deletePost(@PathVariable int id) {
        postImpl.deletePost(id);
        return "redirect:/";

    }
}
