package com.devsuperior.workshopmongo.controllers;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.workshopmongo.controllers.util.URL;
import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.services.PostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

	@Autowired
	private PostService service;


	@GetMapping(value = "/{id}")
	public Mono<ResponseEntity<PostDTO>> findById(@PathVariable String id){
		return service.findById(id).map(postDTO -> ResponseEntity.ok().body(postDTO));
	}

	@GetMapping(value = "/titlesearch")
	public Flux<PostDTO> findByTitle (@RequestParam String text){
		return service.findByTile(text);
	}
}
