import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {ProductDetailsService} from '../../../service/product-details.service';
import {ProductDetails} from '../../../model/product-details';



@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  productId!: number;
  detailsId!: number;

  messageEn = '';
  messageAr = '';

  constructor(
    private productDetailsService: ProductDetailsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {

      if (params.productId) {

        this.productId = +params.productId;

      }

    });
  }

  clearMessages(): void {

    this.messageEn = '';
    this.messageAr = '';
  }

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

        next: (response) => {

          this.messageEn =
            'Product Details added successfully';

          this.messageAr =
            'تمت إضافة تفاصيل المنتج بنجاح';

          this.detailsId =
            response.id || 0;

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }

  updateProductDetails(): void {

    this.clearMessages();

    if (!this.detailsId) {

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

      id: this.detailsId,

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

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }

  deleteProductDetails(): void {

    this.clearMessages();

    if (!this.detailsId) {

      this.messageEn =
        'Product Details ID is required';

      this.messageAr =
        'رقم تفاصيل المنتج مطلوب';

      return;
    }

    this.productDetailsService
      .deleteProductDetails(this.detailsId)
      .subscribe({

        next: () => {

          this.messageEn =
            'Product Details deleted successfully';

          this.messageAr =
            'تم حذف تفاصيل المنتج بنجاح';

          this.detailsId = 0;

          this.productId = 0;

        },

        error: (error) => {

          console.log(error);

          this.showErrorMessage(error);

        }

      });
  }
}
