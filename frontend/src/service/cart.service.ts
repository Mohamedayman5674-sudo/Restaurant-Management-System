import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../model/product';

export interface CartItem {
  product: Product;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartItems: CartItem[] = [];

  private cartSubject =
    new BehaviorSubject<CartItem[]>([]);

  cart$ =
    this.cartSubject.asObservable();

  constructor() {

    const savedCart =
      localStorage.getItem('cart');

    if (savedCart) {

      this.cartItems =
        JSON.parse(savedCart);

    }

    this.cartSubject.next(this.cartItems);
  }

  addToCart(product: Product): void {

    const existingItem =
      this.cartItems.find(
        item => item.product.id === product.id
      );

    if (existingItem) {

      existingItem.quantity++;

    } else {

      this.cartItems.push({
        product: product,
        quantity: 1
      });

    }

    this.saveCart();
  }

  increaseQuantity(productId: number): void {

    const item =
      this.cartItems.find(
        cartItem =>
          cartItem.product.id === productId
      );

    if (item) {

      item.quantity++;

      this.saveCart();
    }
  }

  decreaseQuantity(productId: number): void {

    const item =
      this.cartItems.find(
        cartItem =>
          cartItem.product.id === productId
      );

    if (!item) {
      return;
    }

    if (item.quantity > 1) {

      item.quantity--;

    } else {

      this.removeFromCart(productId);
      return;
    }

    this.saveCart();
  }

  removeFromCart(productId: number): void {

    this.cartItems =
      this.cartItems.filter(
        item =>
          item.product.id !== productId
      );

    this.saveCart();
  }

  getCartItems(): CartItem[] {

    return this.cartItems;
  }

  getTotalPrice(): number {

    return this.cartItems.reduce(
      (total, item) =>
        total +
        (item.product.price * item.quantity),
      0
    );
  }

  getTotalNumber(): number {

    return this.cartItems.reduce(
      (total, item) =>
        total + item.quantity,
      0
    );
  }

  clearCart(): void {

    this.cartItems = [];

    this.saveCart();
  }

  private saveCart(): void {

    localStorage.setItem(
      'cart',
      JSON.stringify(this.cartItems)
    );

    this.cartSubject.next(this.cartItems);
  }
}
