import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Product } from '../../../model/product';
import { Category } from '../../../model/category';

import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { CartService } from '../../../service/cart.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];
  categories: Category[] = [];

  messageEn = '';
  messageAr = '';

  selectedCategoryId: number | null = null;
  searchKey = '';

  totalProducts = 0;
  currentPage = 1;
  pageSize = 5;
  totalPages = 0;
  pages: number[] = [];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private cartService: CartService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.getCategories();

    this.route.params.subscribe(params => {

      const categoryId = params.id;
      const key = params.key;

      if (categoryId && key) {

        this.selectedCategoryId = +categoryId;
        this.searchKey = key;

        this.searchByCategory(
          this.selectedCategoryId,
          this.searchKey
        );

      } else if (categoryId) {

        this.selectedCategoryId = +categoryId;
        this.searchKey = '';

        this.getProductsByCategory(
          this.selectedCategoryId
        );

      } else if (key) {

        this.selectedCategoryId = null;
        this.searchKey = key;

        this.search(key);

      } else {

        this.selectedCategoryId = null;
        this.searchKey = '';

        this.getProducts();
      }
    });
  }

  // =========================
  // Get All Categories
  // =========================
  getCategories(): void {

    this.categoryService
      .getAllCategories(1, 100)
      .subscribe({

        next: (response) => {

          this.categories =
            response.categories;

        },

        error: (error) => {

          console.log(error);

          this.categories = [];

          this.showErrorMessage(error);
        }
      });
  }

  // =========================
  // Show Error Message
  // =========================
  showErrorMessage(error: any): void {

    this.messageEn = '';
    this.messageAr = '';

    if (error?.error?.bundleMessage) {

      this.messageAr =
        error.error.bundleMessage.message_ar || '';

      this.messageEn =
        error.error.bundleMessage.message_en || '';
    }
  }

  // =========================
  // Get All Products
  // =========================
  getProducts(): void {

    this.productService
      .getAllProducts(
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.messageEn = '';
          this.messageAr = '';

          this.products =
            response.products;

          this.totalProducts =
            response.totalProducts;

          this.totalPages =
            Math.ceil(
              this.totalProducts /
              this.pageSize
            );

          this.generatePages();
        },

        error: (error) => {

          console.log(error);

          this.products = [];
          this.totalProducts = 0;
          this.totalPages = 0;
          this.pages = [];

          this.showErrorMessage(error);
        }
      });
  }

  // =========================
  // Get Products By Category
  // =========================
  getProductsByCategory(
    categoryId: number
  ): void {

    this.productService
      .getProductsByCategory(
        categoryId,
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.messageEn = '';
          this.messageAr = '';

          this.products =
            response.products;

          this.totalProducts =
            response.totalProducts;

          this.totalPages =
            Math.ceil(
              this.totalProducts /
              this.pageSize
            );

          this.generatePages();
        },

        error: (error) => {

          console.log(error);

          this.products = [];
          this.totalProducts = 0;
          this.totalPages = 0;
          this.pages = [];

          this.showErrorMessage(error);
        }
      });
  }

  // =========================
  // Search Products
  // =========================
  search(key: string): void {

    this.productService
      .search(
        key,
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.messageEn = '';
          this.messageAr = '';

          this.products =
            response.products;

          this.totalProducts =
            response.totalProducts;

          this.totalPages =
            Math.ceil(
              this.totalProducts /
              this.pageSize
            );

          this.generatePages();
        },

        error: (error) => {

          console.log(error);

          this.products = [];
          this.totalProducts = 0;
          this.totalPages = 0;
          this.pages = [];

          this.showErrorMessage(error);
        }
      });
  }

  // =========================
  // Search By Category
  // =========================
  searchByCategory(
    categoryId: number,
    key: string
  ): void {

    this.productService
      .searchByCategory(
        categoryId,
        key,
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.messageEn = '';
          this.messageAr = '';

          this.products =
            response.products;

          this.totalProducts =
            response.totalProducts;

          this.totalPages =
            Math.ceil(
              this.totalProducts /
              this.pageSize
            );

          this.generatePages();
        },

        error: (error) => {

          console.log(error);

          this.products = [];
          this.totalProducts = 0;
          this.totalPages = 0;
          this.pages = [];

          this.showErrorMessage(error);
        }
      });
  }

  // =========================
  // Load Products
  // =========================
  loadProducts(): void {

    if (
      this.selectedCategoryId !== null &&
      this.searchKey &&
      this.searchKey.trim()
    ) {

      this.searchByCategory(
        this.selectedCategoryId,
        this.searchKey.trim()
      );

    } else if (
      this.selectedCategoryId !== null
    ) {

      this.getProductsByCategory(
        this.selectedCategoryId
      );

    } else if (
      this.searchKey &&
      this.searchKey.trim()
    ) {

      this.search(
        this.searchKey.trim()
      );

    } else {

      this.getProducts();
    }
  }

  // =========================
  // Generate Pagination
  // =========================
  generatePages(): void {

    this.pages = [];

    for (
      let i = 1;
      i <= this.totalPages;
      i++
    ) {

      this.pages.push(i);
    }
  }

  // =========================
  // Go To Page
  // =========================
  goToPage(page: number): void {

    if (
      page < 1 ||
      page > this.totalPages ||
      page === this.currentPage
    ) {

      return;
    }

    this.currentPage = page;

    this.loadProducts();
  }

  // =========================
  // Next Page
  // =========================
  nextPage(): void {

    if (
      this.currentPage <
      this.totalPages
    ) {

      this.currentPage++;

      this.loadProducts();
    }
  }

  // =========================
  // Previous Page
  // =========================
  previousPage(): void {

    if (
      this.currentPage > 1
    ) {

      this.currentPage--;

      this.loadProducts();
    }
  }

  // =========================
  // Change Page Size
  // =========================
  changePageSize(
    size: number
  ): void {

    this.pageSize = size;

    this.currentPage = 1;

    this.loadProducts();
  }

  // =========================
  // Select Category
  // =========================
  selectCategory(
    categoryId: number
  ): void {

    this.selectedCategoryId =
      categoryId;

    this.searchKey = '';

    this.currentPage = 1;

    this.router.navigate([
      '/category',
      categoryId
    ]);
  }

  // =========================
  // Search Products From Input
  // =========================
  searchProducts(
    key: string
  ): void {

    if (
      !key ||
      !key.trim()
    ) {

      this.router.navigate([
        '/products'
      ]);

      return;
    }

    this.currentPage = 1;

    if (
      this.selectedCategoryId !== null
    ) {

      this.router.navigate([
        '/category',
        this.selectedCategoryId,
        'search',
        key.trim()
      ]);

    } else {

      this.router.navigate([
        '/search',
        key.trim()
      ]);
    }
  }

  // =========================
  // Add To Cart
  // =========================
  addToCart(
    product: Product
  ): void {

    this.cartService.addToCart(
      product
    );

    alert(
      product.name +
      ' added to cart successfully'
    );
  }
}
