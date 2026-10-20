import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChefService {

  baseUrl = 'http://localhost:9090/chefs';

  constructor(private http: HttpClient) {}

  getAllChefs(page: number, size: number): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/all-chefs?page=${page}&size=${size}`
    );
  }

  createChef(chef: any): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/create-chef`,
      chef
    );
  }

  updateChef(chef: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}/update-chef`,
      chef
    );
  }

  deleteChef(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/delete-chef?id=${id}`
    );
  }
}
