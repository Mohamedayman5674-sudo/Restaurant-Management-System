import { Component, OnInit } from '@angular/core';
import { CartItem, CartService } from '../../../service/cart.service';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit {

  cartItems: CartItem[] = [];

  totalPrice = 0;
  totalNumber = 0;

  messageEn = '';
  messageAr = '';

  constructor(
    private cartService: CartService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {

    this.loadCart();

  }

  loadCart(): void {

    this.cartItems =
      this.cartService.getCartItems();

    this.calculateTotals();

  }

  calculateTotals(): void {

    this.totalPrice =
      this.cartService.getTotalPrice();

    this.totalNumber =
      this.cartService.getTotalNumber();

  }

  increaseQuantity(
    productId: number
  ): void {

    this.cartService.increaseQuantity(
      productId
    );

    this.loadCart();
  }

  decreaseQuantity(
    productId: number
  ): void {

    this.cartService.decreaseQuantity(
      productId
    );

    this.loadCart();
  }

  removeProduct(
    productId: number
  ): void {

    this.cartService.removeFromCart(
      productId
    );

    this.loadCart();
  }

  takeOrder(): void {

    this.messageEn = '';
    this.messageAr = '';

    if (this.cartItems.length === 0) {

      this.messageEn =
        'Cart is empty';

      this.messageAr =
        'السلة فارغة';

      return;
    }

    const productIds =
      this.cartItems.map(
        item => item.product.id
      );

    const order = {

      productIds,

      totalPrice: this.totalPrice,

      totalNumber: this.totalNumber

    };

    this.orderService
      .createOrder(order)
      .subscribe({

        next: (response) => {

          console.log(
            'Order created:',
            response
          );

          this.messageEn =
            'Order created successfully';

          this.messageAr =
            'تم إنشاء الطلب بنجاح';

          this.cartService.clearCart();

          this.loadCart();

        },

        error: (error) => {

          console.log(
            'Order error:',
            error
          );

          this.messageEn = '';
          this.messageAr = '';

          if (
            error?.error?.bundleMessage
          ) {

            this.messageAr =
              error.error.bundleMessage.message_ar || '';

            this.messageEn =
              error.error.bundleMessage.message_en || '';

          } else {

            this.messageEn =
              'Failed to create order';

            this.messageAr =
              'فشل في إنشاء الطلب';

          }

        }

      });
  }
}
