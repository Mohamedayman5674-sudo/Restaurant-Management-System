import { Component, OnInit } from '@angular/core';
import {Category} from '../../../model/category';
import {CategoryService} from '../../../service/category.service';


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  categories: Category[] = [];

  constructor(
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.getAllCategories(1, 100).subscribe({
      next: (response) => {
        this.categories = response.categories;
      },
      error: (error) => {
        console.log(error);
        this.categories = [];
      }
    });
  }
}
