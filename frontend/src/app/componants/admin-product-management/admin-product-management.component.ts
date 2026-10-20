import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';

@Component({
  selector: 'app-admin-product-management',
  templateUrl: './admin-product-management.component.html',
  styleUrls: ['./admin-product-management.component.css']
})
export class AdminProductManagementComponent implements OnInit {

  products: any[] = [];
  categories: any[] = [];

  currentPage = 1;
  pageSize = 5;

  totalProducts = 0;
  totalPages = 0;
  pages: number[] = [];

  messageEn = '';
  messageAr = '';

  // =========================
  // Search / Filter
  // =========================

  createdFrom = '';
  createdTo = '';
  updatedFrom = '';
  updatedTo = '';
  createdBy = '';
  updatedBy = '';

  // =========================
  // Create / Update
  // =========================

  showCreateForm = false;
  showUpdateForm = false;

  newProduct = {
    name: '',
    description: '',
    price: null as number | null,
    imagePath: '',
    categoryId: null as number | null
  };

  updateProductData = {
    id: null as number | null,
    name: '',
    description: '',
    price: null as number | null,
    imagePath: '',
    categoryId: null as number | null
  };

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {

    this.getProducts();

    this.getCategories();
  }

  // =========================
  // Get Products
  // =========================

  getProducts(): void {

    this.productService
      .getAllProducts(
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.products =
            response.products || [];

          this.totalProducts =
            response.totalProducts || 0;

          this.calculatePagination();
        },

        error: (error) => {

          console.log(error);

          this.products = [];

          this.totalProducts = 0;

          this.totalPages = 0;

          this.pages = [];

          this.handleError(error);
        }

      });
  }

  // =========================
  // Get Categories
  // =========================

  getCategories(): void {

    this.categoryService
      .getAllCategories(1, 100)
      .subscribe({

        next: (response) => {

          this.categories =
            response.categories || [];
        },

        error: (error) => {

          console.log(error);

          this.categories = [];
        }

      });
  }

  // =========================
  // Search Products
  // =========================

  searchProducts(): void {

    this.currentPage = 1;

    this.clearMessages();

    this.productService
      .searchProducts(
        this.createdFrom,
        this.createdTo,
        this.updatedFrom,
        this.updatedTo,
        this.createdBy,
        this.updatedBy,
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          console.log(
            'Product search response:',
            response
          );

          this.products =
            response.products || [];

          this.totalProducts =
            response.totalProducts || 0;

          this.calculatePagination();
        },

        error: (error) => {

          console.log(
            'Search products error:',
            error
          );

          this.products = [];

          this.totalProducts = 0;

          this.calculatePagination();

          this.handleError(error);
        }

      });
  }

  // =========================
  // Clear Search
  // =========================

  clearSearch(): void {

    this.createdFrom = '';
    this.createdTo = '';

    this.updatedFrom = '';
    this.updatedTo = '';

    this.createdBy = '';
    this.updatedBy = '';

    this.currentPage = 1;

    this.clearMessages();

    this.getProducts();
  }

  // =========================
  // Check Search
  // =========================

  hasSearch(): boolean {

    return !!(
      this.createdFrom ||
      this.createdTo ||
      this.updatedFrom ||
      this.updatedTo ||
      this.createdBy ||
      this.updatedBy
    );
  }

  // =========================
  // Pagination
  // =========================

  calculatePagination(): void {

    this.totalPages = Math.ceil(
      this.totalProducts /
      this.pageSize
    );

    this.pages = Array.from(
      {
        length: this.totalPages
      },
      (_, i) => i + 1
    );
  }

  goToPage(page: number): void {

    if (
      page < 1 ||
      page > this.totalPages
    ) {

      return;
    }

    this.currentPage = page;

    if (this.hasSearch()) {

      this.searchProducts();

    } else {

      this.getProducts();
    }
  }

  previousPage(): void {

    if (this.currentPage > 1) {

      this.currentPage--;

      if (this.hasSearch()) {

        this.searchProducts();

      } else {

        this.getProducts();
      }
    }
  }

  nextPage(): void {

    if (
      this.currentPage <
      this.totalPages
    ) {

      this.currentPage++;

      if (this.hasSearch()) {

        this.searchProducts();

      } else {

        this.getProducts();
      }
    }
  }

  changePageSize(): void {

    this.currentPage = 1;

    if (this.hasSearch()) {

      this.searchProducts();

    } else {

      this.getProducts();
    }
  }

  // =========================
  // CREATE PRODUCT
  // =========================

  openCreateForm(): void {

    this.showCreateForm = true;

    this.showUpdateForm = false;

    this.clearMessages();
  }

  cancelCreate(): void {

    this.showCreateForm = false;

    this.resetCreateForm();
  }

  createProduct(): void {

    this.clearMessages();

    const product = {

      name: this.newProduct.name,

      description: this.newProduct.description,

      price: this.newProduct.price,

      imagePath: this.newProduct.imagePath,

      category: {
        id: this.newProduct.categoryId
      }
    };

    this.productService
      .createProduct(product)
      .subscribe({

        next: (response) => {

          console.log(
            'Product created:',
            response
          );

          this.showCreateForm = false;

          this.resetCreateForm();

          this.currentPage = 1;

          this.getProducts();

          this.messageEn =
            'Product created successfully';

          this.messageAr =
            'تم إضافة المنتج بنجاح';
        },

        error: (error) => {

          console.log(error);

          this.handleError(error);
        }

      });
  }

  resetCreateForm(): void {

    this.newProduct = {

      name: '',

      description: '',

      price: null,

      imagePath: '',

      categoryId: null
    };
  }

  // =========================
  // UPDATE PRODUCT
  // =========================

  openUpdateForm(product: any): void {

    console.log('PRODUCT FOR EDIT:', product);

    this.showUpdateForm = true;

    this.showCreateForm = false;

    this.clearMessages();

    this.updateProductData = {

      id: product.id,

      name: product.name,

      description: product.description,

      price: product.price,

      imagePath: product.imagePath,

      categoryId:
        product.category?.id ?? null
    };
  }

  cancelUpdate(): void {

    this.showUpdateForm = false;

    this.resetUpdateForm();
  }

  updateProduct(): void {

    this.clearMessages();

    const product = {

      id: this.updateProductData.id,

      name: this.updateProductData.name,

      description:
      this.updateProductData.description,

      price: this.updateProductData.price,

      imagePath:
      this.updateProductData.imagePath,

      category: {
        id: this.updateProductData.categoryId
      }
    };

    this.productService
      .updateProduct(product)
      .subscribe({

        next: (response) => {

          console.log(
            'Product updated:',
            response
          );

          this.showUpdateForm = false;

          this.resetUpdateForm();

          this.getProducts();

          this.messageEn =
            'Product updated successfully';

          this.messageAr =
            'تم تحديث المنتج بنجاح';
        },

        error: (error) => {

          console.log(error);

          this.handleError(error);
        }

      });
  }

  resetUpdateForm(): void {

    this.updateProductData = {

      id: null,

      name: '',

      description: '',

      price: null,

      imagePath: '',

      categoryId: null
    };
  }

  // =========================
  // DELETE PRODUCT
  // =========================

  deleteProduct(id: number): void {

    this.clearMessages();

    if (
      !confirm(
        'Are you sure you want to delete this product?'
      )
    ) {

      return;
    }

    this.productService
      .deleteProduct(id)
      .subscribe({

        next: () => {

          console.log(
            'Product deleted'
          );

          if (
            this.products.length === 1 &&
            this.currentPage > 1
          ) {

            this.currentPage--;
          }

          if (this.hasSearch()) {

            this.searchProducts();

          } else {

            this.getProducts();
          }

          this.messageEn =
            'Product deleted successfully';

          this.messageAr =
            'تم حذف المنتج بنجاح';
        },

        error: (error) => {

          console.log(error);

          this.handleError(error);
        }

      });
  }

  // =========================
  // MESSAGES
  // =========================

  clearMessages(): void {

    this.messageEn = '';

    this.messageAr = '';
  }

  handleError(error: any): void {

    if (
      error?.error?.bundleMessage
    ) {

      this.messageEn =
        error.error.bundleMessage.message_en ||
        '';

      this.messageAr =
        error.error.bundleMessage.message_ar ||
        '';

    } else {

      this.messageEn =
        'Something went wrong';

      this.messageAr =
        'حدث خطأ ما';
    }
  }
}
