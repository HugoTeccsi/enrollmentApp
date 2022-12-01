package com.pe.hatv.service.impl;

import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.service.OperationCrudService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class OperationCrudServiceImpl<T, ID> implements OperationCrudService<T, ID> {

	protected abstract GenericRepository<T, ID> getRepository();

	@Override
	public Mono<T> save(T t) {
		return getRepository().save(t);
	}

	@Override
	public Mono<T> update(T t) {
		return getRepository().save(t);
	}

	@Override
	public Flux<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public Mono<T> findById(ID id) {
		return getRepository().findById(id);
	}

	@Override
	public Mono<Void> delete(ID id) {
		return getRepository().deleteById(id);
	}
}
