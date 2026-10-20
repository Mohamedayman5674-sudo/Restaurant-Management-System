import { Component, OnInit } from '@angular/core';
import {ContactInfo, ContactInfoService} from '../../../service/contact-info.service';


@Component({
  selector: 'app-admin-contact-information-management',
  templateUrl: './admin-contact-information-management.component.html',
  styleUrls: ['./admin-contact-information-management.component.css']
})
export class AdminContactInformationManagementComponent implements OnInit {

  contactInfo: ContactInfo[] = [];

  messageEn = '';
  messageAr = '';

  currentPage = 1;
  pageSize = 5;
  totalContactInfo = 0;
  totalPages = 0;
  pages: number[] = [];

  constructor(
    private contactInfoService: ContactInfoService
  ) {}

  ngOnInit(): void {
    this.getContactInfo();
  }

  getContactInfo(): void {

    this.clearMessages();

    this.contactInfoService
      .getAllContactInfo(this.currentPage, this.pageSize)
      .subscribe({
        next: (response: any) => {

          this.contactInfo = response.contactInfo;
          this.totalContactInfo = response.totalContactInfo;

          this.totalPages = Math.ceil(
            this.totalContactInfo / this.pageSize
          );

          this.pages = Array.from(
            { length: this.totalPages },
            (_, i) => i + 1
          );
        },

        error: (error) => {
          console.log(error);
          this.handleError(error);
        }
      });
  }

  deleteContactInfo(id: number): void {

    this.clearMessages();

    if (!confirm('Are you sure you want to delete this contact message?')) {
      return;
    }

    this.contactInfoService.deleteContactInfo(id).subscribe({

      next: () => {

        this.messageEn = 'Contact information deleted successfully';
        this.messageAr = 'تم حذف رسالة التواصل بنجاح';

        this.getContactInfo();
      },

      error: (error) => {

        console.log(error);
        this.handleError(error);
      }
    });
  }

  goToPage(page: number): void {

    if (page < 1 || page > this.totalPages) {
      return;
    }

    this.currentPage = page;
    this.getContactInfo();
  }

  nextPage(): void {

    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.getContactInfo();
    }
  }

  previousPage(): void {

    if (this.currentPage > 1) {
      this.currentPage--;
      this.getContactInfo();
    }
  }

  clearMessages(): void {

    this.messageEn = '';
    this.messageAr = '';
  }

  handleError(error: any): void {

    this.messageEn =
      error?.error?.bundleMessage?.message_en ||
      'Something went wrong';

    this.messageAr =
      error?.error?.bundleMessage?.message_ar ||
      'حدث خطأ ما';
  }
}

