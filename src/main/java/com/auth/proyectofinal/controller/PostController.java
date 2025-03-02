package com.auth.proyectofinal.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.proyectofinal.model.Post;
import com.auth.proyectofinal.model.dto.ResponseMessageDTO;
import com.auth.proyectofinal.service.PostService;

@RestController
@RequestMapping("/api/post")
@PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PreAuthorize("isAuthenticated() or hasAnyRole('ADMIN', 'AUTHOR', 'USER')")
	@GetMapping()
	public ResponseEntity<List<Post>> getPosts(Principal principal){
		return ResponseEntity.ok(postService.findAll());
	}

	@PostMapping
	public ResponseEntity<ResponseMessageDTO> createPost(Principal principal, @RequestBody Post post){
		postService.savePost(principal,post);
		return ResponseEntity.ok(new ResponseMessageDTO("Post created successfuly",HttpStatus.OK.value()));
	}
	
	@PreAuthorize("isAuthenticated() or hasAnyRole('ADMIN', 'AUTHOR', 'USER')")
	@PutMapping("/{postId}")
	public ResponseEntity<ResponseMessageDTO> editPost(Principal principal,@PathVariable Long postId, @RequestBody Post post){
		postService.editPost(principal,post, postId);
		return ResponseEntity.ok(new ResponseMessageDTO("Post updated successfuly",HttpStatus.OK.value()));
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ResponseMessageDTO> deletePost(Principal principal,@PathVariable Long postId){
		postService.deletePost(principal,postId);
		return ResponseEntity.ok(new ResponseMessageDTO("Post deleted successfuly",HttpStatus.OK.value()));
	}

}
