package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.ChefResponseVm;
import com.spring.boot.restaurant.dto.ChefDto;
import com.spring.boot.restaurant.services.ChefService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chefs")
public class ChefController {

    @Autowired
    private ChefService chefService;


    // =========================
    // GET ALL CHEFS
    // =========================

    @GetMapping("/all-chefs")
    public ResponseEntity<ChefResponseVm> getAllChefs(
            @RequestParam int page,
            @RequestParam int size)
            throws SystemException {

        return ResponseEntity.ok(
                chefService.getAllChefs(page, size)
        );
    }


    // =========================
    // ADD CHEF
    // =========================

    @PostMapping("/create-chef")
    public ResponseEntity<ChefDto> createChef(
            @Valid @RequestBody ChefDto chefDto)
            throws SystemException {

        return ResponseEntity.ok(
                chefService.addChef(chefDto)
        );
    }


    // =========================
    // UPDATE CHEF
    // =========================

    @PutMapping("/update-chef")
    public ResponseEntity<ChefDto> updateChef(
            @Valid @RequestBody ChefDto chefDto)
            throws SystemException {

        return ResponseEntity.ok(
                chefService.updateChef(chefDto)
        );
    }


    // =========================
    // DELETE CHEF
    // =========================

    @DeleteMapping("/delete-chef")
    public ResponseEntity<Void> deleteChef(
            @RequestParam Long id)
            throws SystemException {

        chefService.deleteChef(id);

        return ResponseEntity.noContent().build();
    }
}