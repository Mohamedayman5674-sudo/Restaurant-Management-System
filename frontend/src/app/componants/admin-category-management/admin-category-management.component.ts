import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../model/category';

@Component({
  selector: 'app-admin-category-management',
  templateUrl: './admin-category-management.component.html',
  styleUrls: ['./admin-category-management.component.css']
})
export class AdminCategoryManagementComponent implements OnInit {

  categories: Category[] = [];

  currentPage = 1;
  pageSize = 5;
  totalCategories = 0;
  totalPages = 0;
  pages: number[] = [];

  isEditMode = false;

  formCategory: any = {
    id: null,
    name: '',
    logo: '',
    flag: ''
  };

  createdFrom = '';
  createdTo = '';
  updatedFrom = '';
  updatedTo = '';
  createdBy = '';
  updatedBy = '';

  successMessage = '';
  errorMessage = '';

  showDeleteConfirmation = false;
  categoryToDelete: number | null = null;

  constructor(
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.getCategories();
  }

  // =========================
  // Get Categories
  // =========================

  getCategories(): void {

    this.categoryService
      .getAllCategories(this.currentPage, this.pageSize)
      .subscribe({

        next: (response) => {

          console.log('Categories response:', response);

          this.categories = response.categories || [];

          this.totalCategories =
            response.totalCategories || 0;

          this.calculatePages();
        },

        error: (error) => {

          console.log('Get categories error:', error);

          this.categories = [];

          this.totalCategories = 0;

          this.calculatePages();

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }

  // =========================
  // Search Categories
  // =========================

  searchCategories(): void {

    this.currentPage = 1;

    this.clearMessages();

    this.categoryService
      .searchCategories(
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

          console.log('Search response:', response);

          this.categories =
            response.categories || [];

          this.totalCategories =
            response.totalCategories || 0;

          this.calculatePages();
        },

        error: (error) => {

          console.log(
            'Search categories error:',
            error
          );

          this.categories = [];

          this.totalCategories = 0;

          this.calculatePages();

          this.errorMessage =
            this.getErrorMessage(error);
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

    this.getCategories();
  }

  // =========================
  // Pagination
  // =========================

  calculatePages(): void {

    this.totalPages = Math.ceil(
      this.totalCategories / this.pageSize
    );

    this.pages = [];

    for (
      let i = 1;
      i <= this.totalPages;
      i++
    ) {

      this.pages.push(i);
    }
  }

  changePage(page: number): void {

    if (
      page < 1 ||
      page > this.totalPages ||
      page === this.currentPage
    ) {
      return;
    }

    this.currentPage = page;

    const hasSearch =
      this.createdFrom ||
      this.createdTo ||
      this.updatedFrom ||
      this.updatedTo ||
      this.createdBy ||
      this.updatedBy;

    if (hasSearch) {

      this.searchCategories();

    } else {

      this.getCategories();
    }
  }

  // =========================
  // Create Form
  // =========================

  openCreateForm(): void {

    this.isEditMode = false;

    this.formCategory = {
      id: null,
      name: '',
      logo: '',
      flag: ''
    };

    this.clearMessages();
  }

  // =========================
  // Edit Form
  // =========================

  openEditForm(category: Category): void {

    this.isEditMode = true;

    this.formCategory = {

      id: category.id,

      name: category.name,

      logo: category.logo,

      flag: category.flag
    };

    this.clearMessages();
  }

  // =========================
  // Create / Update
  // =========================

  saveCategory(): void {

    this.clearMessages();

    if (
      !this.formCategory.name ||
      !this.formCategory.logo ||
      !this.formCategory.flag
    ) {

      this.errorMessage =
        'Please fill all category fields | من فضلك املأ جميع بيانات التصنيف';

      return;
    }

    // =========================
    // Update
    // =========================

    if (this.isEditMode) {

      this.categoryService
        .updateCategory(this.formCategory)
        .subscribe({

          next: () => {

            this.successMessage =
              'Category updated successfully | تم تعديل التصنيف بنجاح';

            this.resetForm();

            this.getCategories();
          },

          error: (error) => {

            console.log(
              'Update category error:',
              error
            );

            this.errorMessage =
              this.getErrorMessage(error);
          }

        });

    }

      // =========================
      // Create
    // =========================

    else {

      this.categoryService
        .createCategory(this.formCategory)
        .subscribe({

          next: () => {

            this.successMessage =
              'Category created successfully | تم إنشاء التصنيف بنجاح';

            this.resetForm();

            this.getCategories();
          },

          error: (error) => {

            console.log(
              'Create category error:',
              error
            );

            this.errorMessage =
              this.getErrorMessage(error);
          }

        });
    }
  }

  // =========================
  // Reset Form
  // =========================

  resetForm(): void {

    this.isEditMode = false;

    this.formCategory = {

      id: null,

      name: '',

      logo: '',

      flag: ''
    };
  }

  // =========================
  // Delete Confirmation
  // =========================

  confirmDelete(id: number): void {

    this.categoryToDelete = id;

    this.showDeleteConfirmation = true;
  }

  // =========================
  // Cancel Delete
  // =========================

  cancelDelete(): void {

    this.categoryToDelete = null;

    this.showDeleteConfirmation = false;
  }

  // =========================
  // Delete Category
  // =========================

  deleteCategory(): void {

    if (this.categoryToDelete === null) {
      return;
    }

    const id = this.categoryToDelete;

    this.categoryService
      .deleteCategory(id)
      .subscribe({

        next: () => {

          this.successMessage =
            'Category deleted successfully | تم حذف التصنيف بنجاح';

          this.categoryToDelete = null;

          this.showDeleteConfirmation = false;

          if (
            this.categories.length === 1 &&
            this.currentPage > 1
          ) {

            this.currentPage--;
          }

          this.getCategories();
        },

        error: (error) => {

          console.log(
            'Delete category error:',
            error
          );

          this.showDeleteConfirmation = false;

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }

  // =========================
  // Error Message
  // =========================

  getErrorMessage(error: any): string {

    if (
      error?.error?.bundleMessage?.message_en &&
      error?.error?.bundleMessage?.message_ar
    ) {

      return (
        error.error.bundleMessage.message_en +
        ' | ' +
        error.error.bundleMessage.message_ar
      );
    }

    if (
      error?.error?.bundleMessage?.message_en
    ) {

      return error.error.bundleMessage.message_en;
    }

    if (error?.error?.message) {

      return error.error.message;
    }

    return 'Something went wrong | حدث خطأ ما';
  }

  // =========================
  // Clear Messages
  // =========================

  clearMessages(): void {

    this.successMessage = '';

    this.errorMessage = '';
  }
}
