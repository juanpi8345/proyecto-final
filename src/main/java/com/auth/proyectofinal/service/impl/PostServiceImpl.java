package com.auth.proyectofinal.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.proyectofinal.exception.PermissionDeniedException;
import com.auth.proyectofinal.exception.ResourceNotFoundException;
import com.auth.proyectofinal.model.Author;
import com.auth.proyectofinal.model.Post;
import com.auth.proyectofinal.repository.AuthorRepository;
import com.auth.proyectofinal.repository.PostRepository;
import com.auth.proyectofinal.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Post findById(Long postId) {
		return postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post with id: "+postId + " not found"));
	}

	@Override
	public void savePost(Principal principal,Post post) {
		String authorName = principal.getName();
		Optional<Author> authorFound = authorRepository.findByName(authorName);
		Author author;
		if(authorFound.isEmpty()) {
			author = new Author();
			author.setName(authorName);
			authorRepository.save(author);
		}else {
			author = authorFound.get();
		}
		post.setAuthor(author);
		postRepository.save(post);
	}

	@Override
	public void editPost(Principal principal,Post post, Long postId) {
		Post postToEdit = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post with id: "+postId + " not found"));
		
		if(!principal.getName().equals(postToEdit.getAuthor().getName()))
			throw new PermissionDeniedException("Yo are not the author of the post id: "+postToEdit.getPostId());
	
		postToEdit.setPostId(postId);
		this.savePost(principal, postToEdit);

	}

	@Override
	public void deletePost(Principal principal,Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post with id: "+postId + " not found"));
		
		if(!principal.getName().equals(post.getAuthor().getName()))
			throw new PermissionDeniedException("Yo are not the author of the post id: "+post.getPostId());
				
		postRepository.deleteById(postId);
	}

}
