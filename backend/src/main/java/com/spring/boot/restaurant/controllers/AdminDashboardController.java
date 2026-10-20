package com.spring.boot.restaurant.controllers;

import com.spring.boot.restaurant.controllers.vm.DashboardResponseVm;
import com.spring.boot.restaurant.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseVm> getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getDashboard()
        );
    }
}