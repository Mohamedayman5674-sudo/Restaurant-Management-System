import { Component, OnInit } from '@angular/core';
import {
  DashboardResponse,
  DashboardService
} from '../../../service/dashboard.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  dashboard: DashboardResponse = {
    totalUsers: 0,
    totalProducts: 0,
    totalCategories: 0,
    totalChefs: 0,
    totalOrders: 0,

    recentOrders: [],
    recentProducts: [],
    recentCategories: []
  };

  errorMessage = '';

  constructor(
    private dashboardService: DashboardService
  ) {
  }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {

    this.errorMessage = '';

    this.dashboardService.getDashboard().subscribe({

      next: (response) => {

        console.log('Dashboard response:', response);

        this.dashboard = response;
      },

      error: (error) => {

        console.log('Dashboard error:', error);

        this.errorMessage = 'Failed to load dashboard';
      }

    });
  }
}
