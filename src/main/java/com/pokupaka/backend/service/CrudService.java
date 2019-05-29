package com.pokupaka.backend.service;

import com.pokupaka.backend.data.entity.AbstractEntity;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public interface CrudService<T extends AbstractEntity> {

	JpaRepository<T, Long> getRepository();

	default T save(User currentUser, T entity) {
		if (entity instanceof Product) {
			Product product = (Product) entity;
			System.out.println(product.toString());
		}

		return getRepository().saveAndFlush(entity);
	}

	default void delete(User currentUser, T entity) {
		System.out.println("deleting " + entity.getClass() + "with id = " + entity.getId());
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		getRepository().delete(entity);
	}

	default void delete(User currentUser, long id) {
		delete(currentUser, load(id));
	}

	default long count() {
		return getRepository().count();
	}

	default T load(long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		return entity;
	}

	T createNew(User currentUser);
}
