import { Component, OnInit } from '@angular/core';
import { ProductDetailsService } from '../../../service/product-details.service';
import { ProductDetails } from '../../../model/product-details';

@Component({
  selector: 'app-admin-product-details-management',
  templateUrl: './admin-product-details-management.component.html',
  styleUrls: ['./admin-product-details-management.component.css']
})
export class AdminProductDetailsManagementComponent implements OnInit {

  productDetails: ProductDetails[] = [];

  selectedProductDetails: ProductDetails | null = null;

  productId: number | null = null;

  currentPage = 1;
  pageSize = 5;

  totalProductDetails = 0;
  totalPages = 0;
  pages: number[] = [];

  messageEn = '';
  messageAr = '';

  constructor(
    private productDetailsService: ProductDetailsService
  ) {}

  ngOnInit(): void {

    this.getAllProductDetails();

  }

  getAllProductDetails(): void {

    this.clearMessages();

    this.productDetailsService
      .getAllProductDetails(
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          this.productDetails =
            response.productDetails || [];

          this.totalProductDetails =
            response.totalProductDetails || 0;

          this.totalPages =
            Math.ceil(
              this.totalProductDetails / this.pageSize
            );

          this.pages = Array.from(
            { length: this.totalPages },
            (_, i) => i + 1
          );

        },

        error: (error) => {

          console.log(error);

          this.productDetails = [];

          this.totalProductDetails = 0;

          this.totalPages = 0;

          this.pages = [];

          this.showErrorMessage(error);

        }

      });
  }

  addProductDetails(): void {

    this.clearMessages();

    if (!this.productId) {

      this.messageEn =
        'Product ID is required';

      this.messageAr =
        'رقم المنتج مطلوب';

      return;
    }

    const productDetails: ProductDetails = {

      productId: this.productId

    };

    this.productDetailsService
      .addProductDetails(productDetails)
      .subscribe({

        next: () => {

          this.messageEn =
            'Product Details added successfully';

          this.messageAr =
            'تمت إضافة تفاصيل المنتج بنجاح';

          this.productId = null;

          this.getAllProductDetails();

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }

  editProductDetails(
    productDetails: ProductDetails
  ): void {

    this.selectedProductDetails = {
      id: productDetails.id,
      productId: productDetails.productId
    };

    this.productId =
      productDetails.productId || null;

    this.clearMessages();

  }

  updateProductDetails(): void {

    this.clearMessages();

    if (!this.selectedProductDetails?.id) {

      this.messageEn =
        'Product Details ID is required';

      this.messageAr =
        'رقم تفاصيل المنتج مطلوب';

      return;
    }

    if (!this.productId) {

      this.messageEn =
        'Product ID is required';

      this.messageAr =
        'رقم المنتج مطلوب';

      return;
    }

    const productDetails: ProductDetails = {

      id: this.selectedProductDetails.id,

      productId: this.productId

    };

    this.productDetailsService
      .updateProductDetails(productDetails)
      .subscribe({

        next: () => {

          this.messageEn =
            'Product Details updated successfully';

          this.messageAr =
            'تم تعديل تفاصيل المنتج بنجاح';

          this.cancelEdit();

          this.getAllProductDetails();

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }

  deleteProductDetails(
    id: number | undefined
  ): void {

    this.clearMessages();

    if (!id) {

      this.messageEn =
        'Product Details ID is required';

      this.messageAr =
        'رقم تفاصيل المنتج مطلوب';

      return;
    }

    this.productDetailsService
      .deleteProductDetails(id)
      .subscribe({

        next: () => {

          this.messageEn =
            'Product Details deleted successfully';

          this.messageAr =
            'تم حذف تفاصيل المنتج بنجاح';

          this.getAllProductDetails();

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }

  cancelEdit(): void {

    this.selectedProductDetails = null;

    this.productId = null;

  }

  goToPage(page: number): void {

    if (
      page < 1 ||
      page > this.totalPages
    ) {

      return;
    }

    this.currentPage = page;

    this.getAllProductDetails();

  }

  previousPage(): void {

    if (this.currentPage > 1) {

      this.currentPage--;

      this.getAllProductDetails();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages) {

      this.currentPage++;

      this.getAllProductDetails();

    }

  }

  changePageSize(): void {

    this.currentPage = 1;

    this.getAllProductDetails();

  }

  clearMessages(): void {

    this.messageEn = '';

    this.messageAr = '';

  }

  showErrorMessage(error: any): void {

    this.messageEn = '';

    this.messageAr = '';

    if (error?.error?.bundleMessage) {

      this.messageEn =
        error.error.bundleMessage.message_en || '';

      this.messageAr =
        error.error.bundleMessage.message_ar || '';

    } else {

      this.messageEn =
        'Something went wrong';

      this.messageAr =
        'حدث خطأ ما';

    }

  }

}
