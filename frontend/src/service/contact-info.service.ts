import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ContactInfo {
  id?: number;
  name: string;
  email: string;
  subject: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContactInfoService {

  private baseUrl = 'http://localhost:9090/contact-info';

  constructor(private http: HttpClient) {}

  createContactInfo(
    contactInfo: ContactInfo
  ): Observable<ContactInfo> {

    return this.http.post<ContactInfo>(
      this.baseUrl,
      contactInfo
    );
  }

  getAllContactInfo(
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-contact-info?page=${page}&size=${size}`
    );
  }

  deleteContactInfo(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.baseUrl}/delete?id=${id}`
    );
  }
}
