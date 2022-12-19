package com.pe.hatv.service;

import com.pe.hatv.model.entity.User;
import reactor.core.publisher.Mono;

public interface UserService extends OperationCrudService<User, String>{

	Mono<User> saveHash(User user);

	Mono<com.pe.hatv.security.model.User> searchByUser(String username);
}
