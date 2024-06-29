package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.services.exceptioons.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Flux<UserDTO> findAll() {
        Flux<User> users = repository.findAll();
        return users.map(user -> new UserDTO(user));
    }

    public Mono<UserDTO> findById(String id) {
        return repository.findById(id)
                .map(user -> new UserDTO(user))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource not found")));
    }

    public Mono<UserDTO> insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        Mono<UserDTO> userDTOMono = repository.save(entity).map(user -> new UserDTO(user));
        return userDTOMono;
    }

    public Mono<UserDTO> update(String id, UserDTO userDTO) {
        return repository.findById(id)
                .flatMap(user -> {
                    user.setName(userDTO.getName());
                    user.setEmail(userDTO.getEmail());
                    return repository.save(user);
                })
                .map(user -> new UserDTO(user))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found")));
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Resource not found")))
                .flatMap(user -> repository.delete(user));
    }
}
