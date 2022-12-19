package com.pe.hatv.service.impl;

import com.pe.hatv.model.entity.User;
import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.repository.RoleRepository;
import com.pe.hatv.repository.UserRepository;
import com.pe.hatv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends OperationCrudServiceImpl<User, String> implements UserService {

	private final BCryptPasswordEncoder bcrypt;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	@Override
	public Mono<User> saveHash(User user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Mono<com.pe.hatv.security.model.User> searchByUser(String username) {

		Mono<User> monoUser = userRepository.findOneByUsername(username);

		List<String> roles = new ArrayList<>();

		return monoUser.flatMap(user -> Flux.fromIterable(user.getRoles())
				.flatMap(rol-> roleRepository.findById(rol.getId())
						.map(r->{
							roles.add(r.getName());
							return r;
						})).collectList().flatMap(list->{
			user.setRoles(list);
			return Mono.just(user);
				})).flatMap(u->
				Mono.just(new com.pe.hatv.security.model.User(u.getUsername(), u.getPassword(), u.getStatus(), roles)));
	}

	@Override
	protected GenericRepository<User, String> getRepository() {
		return userRepository;
	}
}
