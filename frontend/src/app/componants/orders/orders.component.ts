import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: any[] = [];

  currentPage = 1;
  pageSize = 5;

  totalOrders = 0;
  totalPages = 0;
  pages: number[] = [];

  messageEn = '';
  messageAr = '';

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.getMyOrders();
  }

  getMyOrders(): void {

    this.clearMessages();

    this.orderService.getMyOrders(this.currentPage, this.pageSize)
      .subscribe({
        next: (response) => {

          this.orders = response.orders || [];
          this.totalOrders = response.totalElements || 0;

          this.totalPages = Math.ceil(this.totalOrders / this.pageSize);

          this.pages = Array.from(
            { length: this.totalPages },
            (_, i) => i + 1
          );

        },

        error: (error) => {

          console.log(error);

          this.orders = [];
          this.totalOrders = 0;
          this.totalPages = 0;
          this.pages = [];

          if (error?.error?.bundleMessage) {
            this.messageEn =
              error.error.bundleMessage.message_en || '';

            this.messageAr =
              error.error.bundleMessage.message_ar || '';
          } else {
            this.messageEn = 'Failed to load orders';
            this.messageAr = 'فشل تحميل الطلبات';
          }

        }
      });
  }

  goToPage(page: number): void {

    if (page < 1 || page > this.totalPages) {
      return;
    }

    this.currentPage = page;
    this.getMyOrders();
  }

  previousPage(): void {

    if (this.currentPage > 1) {
      this.currentPage--;
      this.getMyOrders();
    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.getMyOrders();
    }

  }

  changePageSize(): void {

    this.currentPage = 1;
    this.getMyOrders();

  }

  clearMessages(): void {
    this.messageEn = '';
    this.messageAr = '';
  }

}
