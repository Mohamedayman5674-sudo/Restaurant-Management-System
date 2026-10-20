import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:9090/auth';

  constructor(private http: HttpClient) { }


  login(username: string, password: string): Observable<any> {

    return this.http.post<any>(
      `${this.baseUrl}/login`,
      {
        username,
        password
      }
    );
  }


  signup(
    username: string,
    password: string,
    age: number,
    phoneNumber: string,
    address: string
  ): Observable<any> {

    return this.http.post<any>(
      `${this.baseUrl}/sign-up`,
      {
        username,
        password,
        accountDetails: {
          age,
          phoneNumber,
          address
        }
      }
    );
  }


  saveToken(token: string): void {
    sessionStorage.setItem('token', token);
  }


  getToken(): string | null {
    return sessionStorage.getItem('token');
  }


  saveRoles(roles: string[]): void {
    sessionStorage.setItem(
      'roles',
      JSON.stringify(roles)
    );
  }


  getRoles(): string[] {

    const roles =
      sessionStorage.getItem('roles');

    return roles
      ? JSON.parse(roles)
      : [];
  }


  isUserLogin(): boolean {
    return !!sessionStorage.getItem('token');
  }


  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('roles');
  }

}
