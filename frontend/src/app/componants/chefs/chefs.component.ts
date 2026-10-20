import { Component, OnInit } from '@angular/core';
import {ChefService} from '../../../service/chef.service';


@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.component.html',
  styleUrls: ['./chefs.component.css']
})
export class ChefsComponent implements OnInit {

  chefs: any[] = [];

  currentPage = 1;
  pageSize = 100;

  messageEn = '';
  messageAr = '';

  constructor(private chefService: ChefService) {}

  ngOnInit(): void {
    this.getChefs();
  }

  getChefs(): void {

    this.chefService.getAllChefs(
      this.currentPage,
      this.pageSize
    ).subscribe({

      next: (response) => {

        console.log('Chefs response:', response);

        this.chefs = response.chefs || [];

      },

      error: (error) => {

        console.log('Chefs error:', error);

        this.chefs = [];

        if (error?.error?.bundleMessage) {

          this.messageEn =
            error.error.bundleMessage.message_en || '';

          this.messageAr =
            error.error.bundleMessage.message_ar || '';

        } else {

          this.messageEn = 'Something went wrong';
          this.messageAr = 'حدث خطأ ما';

        }
      }

    });
  }
}
