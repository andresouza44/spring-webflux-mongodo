package com.devsuperior.workshopmongo.controllers;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public Flux<UserDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDTO>> findById(@PathVariable String id) {
        return service.findById(id).
                map(userDTO -> ResponseEntity.ok().body(userDTO));
    }

    @PostMapping
    public Mono<ResponseEntity<UserDTO>> insert(@RequestBody UserDTO userDTO, UriComponentsBuilder builder) {
        Mono<UserDTO> userDTOMono = service.insert(userDTO);
        return userDTOMono.map(newUserDto -> ResponseEntity.
                created(builder.path("/{id}").buildAndExpand(newUserDto.getId()).toUri()).body(newUserDto));
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDTO>> update (@PathVariable  String id, @RequestBody UserDTO userDTO){
        return service.update(id, userDTO).map(newUserDto -> ResponseEntity.ok().body(newUserDto));
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable  String id){
        return service.delete(id).then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }



}
