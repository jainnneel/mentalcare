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

package com.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Comment;
import com.model.Post;
import com.repository.commentrepo;
import com.repository.postrepo;

/**
 * this class is for services layer Data access layer
 * @author neeljain
 *
 */

@Service
public class CommentDao {

	@Autowired
	commentrepo commentrepo;

	@Autowired
	postrepo postrepo;

	public Comment createComment(Comment c) {
		try {
			return commentrepo.save(c);
		} catch (Exception r) {
			throw new RuntimeException();
		}

	}

	public List<Comment> getCommentByPost(Post p) {
		try {
			return commentrepo.findByPost(p);
		} catch (Exception r) {
			throw new RuntimeException();
		}

	}

	public Comment getcommentById(int cid) {
		try {
			return commentrepo.findById(cid).get();
		} catch (Exception r) {
			throw new RuntimeException();
		}

	}

	public void deletecomment(int cid) {
		try {
			commentrepo.deleteById(cid);
		} catch (Exception r) {
			throw new RuntimeException();
		}

	}
	
	public Set<Post> findCommentByuser(int u) {
		try {
			List<Integer> postid = commentrepo.findByuser(u);
			System.out.println(postid);
			Set<Post> post = new HashSet<Post>();
			for (int i : postid) {
				System.out.println("index=>" + i);
				post.add(postrepo.findById(i).get());
			}
			return post;
		} catch (Exception r) {
			throw new RuntimeException();
		}
	}
}
