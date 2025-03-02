package com.auth.proyectofinal.service;

import java.security.Principal;
import java.util.List;

import com.auth.proyectofinal.model.Post;

public interface PostService {
	List<Post> findAll();
	Post findById(Long postId);
	void savePost(Principal principal, Post post);
	void editPost(Principal principal,Post post,Long postId);
	void deletePost(Principal principal,Long postId);
}
