import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDetails } from '../model/product-details';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsService {

  baseUrl = 'http://localhost:9090/product-details';

  constructor(
    private http: HttpClient
  ) {}

  getAllProductDetails(
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-product-details?page=${page}&size=${size}`
    );
  }

  addProductDetails(
    productDetails: ProductDetails
  ): Observable<ProductDetails> {

    return this.http.post<ProductDetails>(
      `${this.baseUrl}/add`,
      productDetails
    );
  }

  updateProductDetails(
    productDetails: ProductDetails
  ): Observable<ProductDetails> {

    return this.http.put<ProductDetails>(
      `${this.baseUrl}/update`,
      productDetails
    );
  }

  deleteProductDetails(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.baseUrl}/delete?id=${id}`
    );
  }
}
