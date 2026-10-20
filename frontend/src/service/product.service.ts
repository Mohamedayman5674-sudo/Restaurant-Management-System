import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl = 'http://localhost:9090/products';

  constructor(
    private http: HttpClient
  ) { }


  // جميع المنتجات
  getAllProducts(
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-products?page=${page}&size=${size}`
    );

  }


  // المنتجات حسب التصنيف
  getProductsByCategory(
    id: number,
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-products/${id}?page=${page}&size=${size}`
    );

  }


  // البحث بالكلمة فقط
  search(
    key: string,
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-products-by-key?key=${key}&page=${page}&size=${size}`
    );

  }


  // البحث بالكلمة + التصنيف
  searchByCategory(
    categoryId: number,
    key: string,
    page: number,
    size: number
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-products-by-key-and-category-id?categoryId=${categoryId}&key=${key}&page=${page}&size=${size}`
    );

  }


  // إضافة منتج
  createProduct(product: any): Observable<any> {

    return this.http.post<any>(
      `${this.baseUrl}/create-product`,
      product
    );

  }


  // تحديث منتج
  updateProduct(product: any): Observable<any> {

    return this.http.put<any>(
      `${this.baseUrl}/update-product`,
      product
    );

  }


  // حذف منتج
  deleteProduct(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.baseUrl}/delete-product?id=${id}`
    );

  }

  // البحث والفلاتر حسب بيانات الـ Audit
  searchProducts(
    createdFrom: string,
    createdTo: string,
    updatedFrom: string,
    updatedTo: string,
    createdBy: string,
    updatedBy: string,
    page: number,
    size: number
  ): Observable<any> {

    let params: string[] = [];

    if (createdFrom) {
      params.push(`createdFrom=${encodeURIComponent(createdFrom)}`);
    }

    if (createdTo) {
      params.push(`createdTo=${encodeURIComponent(createdTo)}`);
    }

    if (updatedFrom) {
      params.push(`updatedFrom=${encodeURIComponent(updatedFrom)}`);
    }

    if (updatedTo) {
      params.push(`updatedTo=${encodeURIComponent(updatedTo)}`);
    }

    if (createdBy) {
      params.push(`createdBy=${encodeURIComponent(createdBy)}`);
    }

    if (updatedBy) {
      params.push(`updatedBy=${encodeURIComponent(updatedBy)}`);
    }

    params.push(`page=${page}`);
    params.push(`size=${size}`);

    return this.http.get<any>(
      `${this.baseUrl}/search?${params.join('&')}`
    );
  }

}
