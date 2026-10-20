import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardResponse {
  totalUsers: number;
  totalProducts: number;
  totalCategories: number;
  totalChefs: number;
  totalOrders: number;

  recentOrders: any[];
  recentProducts: any[];
  recentCategories: any[];
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private baseUrl = 'http://localhost:9090/admin';

  constructor(private http: HttpClient) {
  }

  getDashboard(): Observable<DashboardResponse> {
    return this.http.get<DashboardResponse>(
      `${this.baseUrl}/dashboard`
    );
  }
}
