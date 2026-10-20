package com.spring.boot.restaurant.services.impl;

import com.spring.boot.restaurant.controllers.vm.ChefResponseVm;
import com.spring.boot.restaurant.dto.ChefDto;

import com.spring.boot.restaurant.mappers.ChefMapper;
import com.spring.boot.restaurant.models.Chef;
import com.spring.boot.restaurant.repositories.ChefRepo;
import com.spring.boot.restaurant.services.ChefService;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    private ChefRepo chefRepo;


    @Override
    @Cacheable(
            value = "chefs",
            key = "'all-' + #page + '-' + #size"
    )
    public ChefResponseVm getAllChefs(
            int page,
            int size) throws SystemException {

        if (page < 1) {
            throw new SystemException("error.min.one.page");
        }

        if (size < 1) {
            throw new SystemException("error.min.one.page");
        }

        Pageable pageable =
                PageRequest.of(page - 1, size);

        Page<Chef> chefsPage =
                chefRepo.findAllByOrderByIdAsc(pageable);

        if (chefsPage.isEmpty()) {
            throw new SystemException("chefs.not.found");
        }

        return new ChefResponseVm(
                chefsPage.getContent()
                        .stream()
                        .map(ChefMapper.CHEF_MAPPER::toChefDto)
                        .toList(),

                chefsPage.getTotalElements()
        );
    }


    // =========================
    // ADD CHEF
    // =========================

    @Override
    @CacheEvict(
            value = "chefs",
            allEntries = true
    )
    public ChefDto addChef(
            ChefDto chefDto) throws SystemException {

        Chef chef =
                ChefMapper.CHEF_MAPPER.toChef(chefDto);

        Chef savedChef =
                chefRepo.save(chef);

        return ChefMapper.CHEF_MAPPER
                .toChefDto(savedChef);
    }


    // =========================
    // UPDATE CHEF
    // =========================

    @Override
    @CacheEvict(
            value = "chefs",
            allEntries = true
    )
    public ChefDto updateChef(
            ChefDto chefDto) throws SystemException {

        if (chefDto.getId() == null) {
            throw new SystemException("chefs.not.found");
        }

        Chef chef =
                chefRepo.findById(chefDto.getId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "chefs.not.found"
                                )
                        );

        chef.setName(chefDto.getName());
        chef.setSpec(chefDto.getSpec());
        chef.setLogoPath(chefDto.getLogoPath());
        chef.setFaceLink(chefDto.getFaceLink());
        chef.setTweLink(chefDto.getTweLink());
        chef.setInstaLink(chefDto.getInstaLink());

        Chef updatedChef =
                chefRepo.save(chef);

        return ChefMapper.CHEF_MAPPER
                .toChefDto(updatedChef);
    }


    // =========================
    // DELETE CHEF
    // =========================

    @Override
    @CacheEvict(
            value = "chefs",
            allEntries = true
    )
    public void deleteChef(Long id)
            throws SystemException {

        if (id == null || !chefRepo.existsById(id)) {
            throw new SystemException("chefs.not.found");
        }

        chefRepo.deleteById(id);
    }
}