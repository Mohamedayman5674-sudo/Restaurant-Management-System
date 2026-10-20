import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {

  orders: any[] = [];

  currentPage = 1;
  pageSize = 5;

  totalOrders = 0;
  totalPages = 0;
  pages: number[] = [];

  messageEn = '';
  messageAr = '';

  // =========================
  // SEARCH FIELDS
  // =========================

  createdFrom = '';
  createdTo = '';

  updatedFrom = '';
  updatedTo = '';

  createdBy = '';
  updatedBy = '';

  isSearching = false;


  constructor(private orderService: OrderService) {
  }


  ngOnInit(): void {
    this.getAllOrders();
  }


  // =========================
  // GET ALL ORDERS
  // =========================

  getAllOrders(): void {

    this.clearMessages();

    this.isSearching = false;

    this.orderService
      .getAllOrders(this.currentPage, this.pageSize)
      .subscribe({

        next: (response) => {

          this.orders = response.orders || [];

          this.totalOrders =
            response.totalElements || 0;

          this.calculatePagination();

        },

        error: (error) => {

          console.log(error);

          this.handleError(error);

        }

      });

  }


  // =========================
  // SEARCH ORDERS
  // =========================

  searchOrders(): void {

    this.clearMessages();

    this.currentPage = 1;

    this.isSearching = true;

    this.loadSearchResults();

  }


  // =========================
  // LOAD SEARCH RESULTS
  // =========================

  loadSearchResults(): void {

    this.orderService.searchOrders(

      this.createdFrom || undefined,
      this.createdTo || undefined,

      this.updatedFrom || undefined,
      this.updatedTo || undefined,

      this.createdBy || undefined,
      this.updatedBy || undefined,

      this.currentPage,
      this.pageSize

    ).subscribe({

      next: (response) => {

        this.orders = response.orders || [];

        this.totalOrders =
          response.totalElements || 0;

        this.calculatePagination();

      },

      error: (error) => {

        console.log(error);

        this.handleError(error);

      }

    });

  }


  // =========================
  // PAGINATION
  // =========================

  calculatePagination(): void {

    this.totalPages =
      Math.ceil(this.totalOrders / this.pageSize);

    this.pages = Array.from(
      { length: this.totalPages },
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

    if (this.isSearching) {

      this.loadSearchResults();

    } else {

      this.getAllOrders();

    }

  }


  previousPage(): void {

    if (this.currentPage > 1) {

      this.currentPage--;

      if (this.isSearching) {

        this.loadSearchResults();

      } else {

        this.getAllOrders();

      }

    }

  }


  nextPage(): void {

    if (
      this.currentPage < this.totalPages
    ) {

      this.currentPage++;

      if (this.isSearching) {

        this.loadSearchResults();

      } else {

        this.getAllOrders();

      }

    }

  }


  changePageSize(): void {

    this.currentPage = 1;

    if (this.isSearching) {

      this.loadSearchResults();

    } else {

      this.getAllOrders();

    }

  }


  // =========================
  // CLEAR SEARCH
  // =========================

  clearSearch(): void {

    this.createdFrom = '';
    this.createdTo = '';

    this.updatedFrom = '';
    this.updatedTo = '';

    this.createdBy = '';
    this.updatedBy = '';

    this.currentPage = 1;

    this.isSearching = false;

    this.getAllOrders();

  }


  // =========================
  // MESSAGES
  // =========================

  clearMessages(): void {

    this.messageEn = '';
    this.messageAr = '';

  }


  handleError(error: any): void {

    this.orders = [];

    this.totalOrders = 0;
    this.totalPages = 0;
    this.pages = [];

    this.messageEn = '';
    this.messageAr = '';

    if (error?.error?.bundleMessage) {

      this.messageEn =
        error.error.bundleMessage.message_en || '';

      this.messageAr =
        error.error.bundleMessage.message_ar || '';

    } else {

      this.messageEn =
        'Failed to load orders';

      this.messageAr =
        'فشل تحميل الطلبات';

    }

  }

}
