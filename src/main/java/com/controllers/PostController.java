package com.controllers;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommentDao;
import com.dao.PostImpl;
import com.dao.UserImpl;
import com.dto.PostDto;
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

    String uniquepost = "Uniquepost";
    @PostMapping(value = "post", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public Response postdata(@Valid @RequestBody Post post, BindingResult result, Principal principal, Model m) {
        Response r = new Response();
        if (principal == null) {
            r.setStatus("not");
            return r;
        }
        Users u = userImpl.getbyEmail(principal.getName());
        if (result.hasErrors()) {
            m.addAttribute("post", post);
            List<Post> allpost = postImpl.findAllPost();
            m.addAttribute("posts", allpost);
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            r.setStatus("notdone");
            r.setErrorMessages(errors);
            return r;
        }
        Post p = new Post();
        p.setContent(post.getContent());
        p.setTitle(post.getTitle());
        p.setD(new Date(new java.util.Date().getTime()));
        p.setUser(u);
        postImpl.createPost(p);
        r.setStatus("done");
        return r;
    }

    @GetMapping("post/{pid}")
    public String post(@PathVariable int pid, Model m, HttpServletResponse response, Principal principal)
            throws IOException {
        Users user = userImpl.getbyEmail(principal.getName());
        Optional<Post> p = postImpl.getbyid(pid);
        if (p.isPresent()) {
            m.addAttribute("post", p.get());
            m.addAttribute("user", user);
            m.addAttribute("comment", new Comment());
            m.addAttribute("comments", p.get().getComment());
            return uniquepost;
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Post with id " + pid + " was not found");
            return uniquepost;
        }

    }

    @PostMapping("updatepost")
    public String postUpdate(@Valid @ModelAttribute("post") Post post, BindingResult result, Model m,
            Principal principal) {
        Users u = userImpl.getbyEmail(principal.getName());
        
        if (result.hasErrors()) {
            m.addAttribute("user", u);
            m.addAttribute("post", post);
            return uniquepost;
        }
        Post p =new Post();
        p.setId(post.getId());
        p.setContent(post.getContent());
        p.setTitle(post.getTitle());
        p.setUser(u);
        p.setD(new Date(new java.util.Date().getTime()));
        Post updatedpost = postImpl.createPost(p);
        return "redirect:/post/" + updatedpost.getId();
    }

    @GetMapping("post/delete/{id}")
    public String deletePost(@PathVariable int id) {
        postImpl.deletePost(id);
        return "redirect:/";

    }
}
