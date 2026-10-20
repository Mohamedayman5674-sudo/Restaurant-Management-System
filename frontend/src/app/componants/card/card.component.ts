import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../service/cart.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  totalPrice = 0;
  totalNumber = 0;

  constructor(
    private cartService: CartService
  ) {}

  ngOnInit(): void {

    this.updateCart();

    this.cartService.cart$.subscribe(() => {
      this.updateCart();
    });

  }

  updateCart(): void {

    this.totalPrice =
      this.cartService.getTotalPrice();

    this.totalNumber =
      this.cartService.getTotalNumber();

  }
}
