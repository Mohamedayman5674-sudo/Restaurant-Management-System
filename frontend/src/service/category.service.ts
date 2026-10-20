import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl = 'http://localhost:9090/categories';

  constructor(private http: HttpClient) {
  }


  // =========================
  // GET ALL CATEGORIES
  // =========================

  getAllCategories(
    page: number = 1,
    size: number = 5
  ): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/all-categories?page=${page}&size=${size}`
    );
  }


  // =========================
  // CREATE CATEGORY
  // =========================

  createCategory(
    category: Category
  ): Observable<Category> {

    return this.http.post<Category>(
      `${this.baseUrl}/create-category`,
      category
    );
  }


  // =========================
  // CREATE LIST
  // =========================

  createCategories(
    categories: Category[]
  ): Observable<Category[]> {

    return this.http.post<Category[]>(
      `${this.baseUrl}/create-categories`,
      categories
    );
  }


  // =========================
  // UPDATE CATEGORY
  // =========================

  updateCategory(
    category: Category
  ): Observable<Category> {

    return this.http.put<Category>(
      `${this.baseUrl}/update-category`,
      category
    );
  }


  // =========================
  // UPDATE LIST
  // =========================

  updateCategories(
    categories: Category[]
  ): Observable<Category[]> {

    return this.http.put<Category[]>(
      `${this.baseUrl}/update-categories`,
      categories
    );
  }


  // =========================
  // DELETE CATEGORY
  // =========================

  deleteCategory(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.baseUrl}/delete-category?id=${id}`
    );
  }


  // =========================
  // DELETE LIST
  // =========================

  deleteCategories(
    ids: number[]
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.baseUrl}/delete-categories?ids=${ids.join(',')}`
    );
  }


  // =========================
  // GET CATEGORY BY ID
  // =========================

  getCategoryById(
    id: number
  ): Observable<Category> {

    return this.http.get<Category>(
      `${this.baseUrl}/get-category/${id}`
    );
  }


  // =========================
  // SEARCH CATEGORIES
  // =========================

  searchCategories(
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
      params = params.set('createdFrom', createdFrom);
    }

    if (createdTo) {
      params = params.set('createdTo', createdTo);
    }

    if (updatedFrom) {
      params = params.set('updatedFrom', updatedFrom);
    }

    if (updatedTo) {
      params = params.set('updatedTo', updatedTo);
    }

    if (createdBy) {
      params = params.set('createdBy', createdBy);
    }

    if (updatedBy) {
      params = params.set('updatedBy', updatedBy);
    }

    return this.http.get<any>(
      `${this.baseUrl}/search`,
      { params }
    );
  }

}
