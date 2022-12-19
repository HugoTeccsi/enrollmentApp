package com.pe.hatv.repository;

import com.pe.hatv.model.entity.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepository<User, String> {

	Mono<User> findOneByUsername(String username);

}
