package com.pokupaka.backend.service;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.app.security.SecurityUtils;
import com.pokupaka.backend.data.Role;
import com.pokupaka.backend.data.entity.Category;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.data.entity.User;
import com.pokupaka.backend.repositories.OrderRepository;
import com.pokupaka.backend.repositories.UserRepository;
import com.pokupaka.ui.exceptions.UserFriendlyDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements FilterableCrudService<Order> {

    private final OrderRepository orderRepository;
    private User user;

    @Autowired
    public OrderService(UserRepository userRepository,OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        //this.user = username != null ? userRepository.findByEmailIgnoreCase(username) : null;
    }

    @Override
    public Page<Order> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return SecurityUtils.isAdmin(user) || user == null ?
                    orderRepository.findByIdLike(repositoryFilter, pageable) :
                    orderRepository.findByIdLikeAndUser(repositoryFilter,String.valueOf(user.getId()), pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return SecurityUtils.isAdmin(user) || user == null  ?
                    orderRepository.countByIdLike(repositoryFilter) :
                    orderRepository.countByIdLikeAndUser(repositoryFilter,String.valueOf(user.getId()));
        } else {
            return count();
        }
    }

    public Page<Order> find(Pageable pageable) {
        return orderRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public Order createNew(User currentUser) {
        return new Order();
    }

    @Override
    public Order save(User currentUser, Order entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a deal with that name. Please select a unique name for the deal.");
        }

    }

}