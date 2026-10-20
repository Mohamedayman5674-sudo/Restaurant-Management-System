import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  baseUrl = 'http://localhost:9090/accounts';

  constructor(private http: HttpClient) {}

  getAllAccounts(page: number, size: number): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/all-accounts?page=${page}&size=${size}`
    );
  }

  createAccount(account: any): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/create-account`,
      account
    );
  }

  updateAccount(account: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}/update-account`,
      account
    );
  }

  deleteAccount(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/delete-account?id=${id}`
    );
  }
}
