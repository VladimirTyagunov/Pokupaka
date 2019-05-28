package com.pokupaka.backend.service;

import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.User;
import com.pokupaka.backend.repositories.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DealsService implements FilterableCrudService<Deal> {

    private final DealRepository dealRepository;

    @Autowired
    public DealsService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public Page<Deal> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return dealRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return dealRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Deal> find(Pageable pageable) {
        return dealRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Deal, Long> getRepository() {
        return dealRepository;
    }

    @Override
    public Deal createNew(User currentUser) {
        return new Deal();
    }

    @Override
    public Deal save(User currentUser, Deal entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a deal with that name. Please select a unique name for the deal.");
        }

    }

}