package com.devsuperior.workshopmongo.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.entities.Post;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	public Mono<PostDTO> findById (String id){
		return repository.findById(id)
				.map(post -> new PostDTO(post))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Post Not found")));
	}

	public Flux<PostDTO> findByTile(String text){
		return repository.searchTitle(text)
				.map(post -> new PostDTO(post));
	}
}
