import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  baseUrl = 'http://localhost:9090/order';

  constructor(private http: HttpClient) {
  }


  // =========================
  // CREATE ORDER
  // =========================

  createOrder(order: any): Observable<any> {

    return this.http.post<any>(
      `${this.baseUrl}/create-order`,
      order
    );

  }


  // =========================
  // MY ORDERS
  // =========================

  getMyOrders(
    page: number = 1,
    size: number = 5
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/my-orders?page=${page}&size=${size}`
    );

  }


  // =========================
  // ALL ORDERS
  // =========================

  getAllOrders(
    page: number = 1,
    size: number = 5
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-orders?page=${page}&size=${size}`
    );

  }


  // =========================
  // SEARCH ORDERS
  // =========================

  searchOrders(
    createdFrom?: string,
    createdTo?: string,
    updatedFrom?: string,
    updatedTo?: string,
    createdBy?: string,
    updatedBy?: string,
    page: number = 1,
    size: number = 5
  ): Observable<any> {

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());


    if (createdFrom) {

      params = params.set(
        'createdFrom',
        createdFrom
      );

    }


    if (createdTo) {

      params = params.set(
        'createdTo',
        createdTo
      );

    }


    if (updatedFrom) {

      params = params.set(
        'updatedFrom',
        updatedFrom
      );

    }


    if (updatedTo) {

      params = params.set(
        'updatedTo',
        updatedTo
      );

    }


    if (createdBy) {

      params = params.set(
        'createdBy',
        createdBy
      );

    }


    if (updatedBy) {

      params = params.set(
        'updatedBy',
        updatedBy
      );

    }


    return this.http.get<any>(
      `${this.baseUrl}/search`,
      {
        params
      }
    );

  }

}

