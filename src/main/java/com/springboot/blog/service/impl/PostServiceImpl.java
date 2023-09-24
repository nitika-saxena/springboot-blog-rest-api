package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository=postRepository;
	}
	
	@Override
	public PostDto createPost(PostDto postdto) {
		
		Post post=mapToEntity(postdto);
		
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = mapToDTO(newPost);
		
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPosts() {
		
		List<Post> posts= postRepository.findAll();
		return posts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
	
	}
	
	
	private PostDto mapToDTO(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription());
		postDto.setTitle(post.getTitle());
		
		return postDto;
		
	}
	private Post mapToEntity(PostDto postdto) {
		Post post=new Post();
		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setDescription(postdto.getDescription());
		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", id));
		return mapToDTO(post);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", id));
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		return mapToDTO(postRepository.save(post));
	}

	@Override
	public void deletePostById(long id) {
		Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", id));
		postRepository.delete(post);
	}

}
