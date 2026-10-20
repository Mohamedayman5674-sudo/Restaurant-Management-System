import { Component, OnInit } from '@angular/core';
import { ChefService } from '../../../service/chef.service';

@Component({
  selector: 'app-admin-chef-management',
  templateUrl: './admin-chef-management.component.html',
  styleUrls: ['./admin-chef-management.component.css']
})
export class AdminChefManagementComponent implements OnInit {

  chefs: any[] = [];

  currentPage = 1;
  pageSize = 5;

  totalChefs = 0;
  totalPages = 0;
  pages: number[] = [];

  showCreateForm = false;
  showUpdateForm = false;

  successMessage = '';
  errorMessage = '';

  newChef: any = {
    name: '',
    spec: '',
    logoPath: '',
    faceLink: '',
    tweLink: '',
    instaLink: ''
  };

  updateChefData: any = {
    id: null,
    name: '',
    spec: '',
    logoPath: '',
    faceLink: '',
    tweLink: '',
    instaLink: ''
  };

  constructor(
    private chefService: ChefService
  ) {}

  ngOnInit(): void {
    this.getChefs();
  }


  // =========================
  // GET CHEFS
  // =========================

  getChefs(): void {

    this.chefService
      .getAllChefs(this.currentPage, this.pageSize)
      .subscribe({

        next: (response) => {

          console.log('Chefs response:', response);

          this.chefs = response.chefs || [];
          this.totalChefs = response.totalChefs || 0;

          this.calculatePages();
        },

        error: (error) => {

          console.log('Get chefs error:', error);

          this.chefs = [];
          this.totalChefs = 0;

          this.calculatePages();

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // CREATE FORM
  // =========================

  openCreateForm(): void {

    this.showCreateForm = true;
    this.showUpdateForm = false;

    this.clearMessages();

    this.newChef = {
      name: '',
      spec: '',
      logoPath: '',
      faceLink: '',
      tweLink: '',
      instaLink: ''
    };
  }


  cancelCreate(): void {

    this.showCreateForm = false;

    this.newChef = {
      name: '',
      spec: '',
      logoPath: '',
      faceLink: '',
      tweLink: '',
      instaLink: ''
    };
  }


  // =========================
  // CREATE CHEF
  // =========================

  createChef(): void {

    this.clearMessages();

    if (
      !this.newChef.name ||
      !this.newChef.spec ||
      !this.newChef.logoPath ||
      !this.newChef.faceLink ||
      !this.newChef.tweLink ||
      !this.newChef.instaLink
    ) {

      this.errorMessage =
        'Please fill all chef fields | من فضلك املأ جميع بيانات الشيف';

      return;
    }

    this.chefService
      .createChef(this.newChef)
      .subscribe({

        next: () => {

          this.successMessage =
            'Chef created successfully | تم إنشاء الشيف بنجاح';

          this.showCreateForm = false;

          this.getChefs();
        },

        error: (error) => {

          console.log('Create chef error:', error);

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // UPDATE FORM
  // =========================

  openUpdateForm(chef: any): void {

    this.showUpdateForm = true;
    this.showCreateForm = false;

    this.clearMessages();

    this.updateChefData = {
      id: chef.id,
      name: chef.name,
      spec: chef.spec,
      logoPath: chef.logoPath,
      faceLink: chef.faceLink,
      tweLink: chef.tweLink,
      instaLink: chef.instaLink
    };
  }


  cancelUpdate(): void {

    this.showUpdateForm = false;

    this.updateChefData = {
      id: null,
      name: '',
      spec: '',
      logoPath: '',
      faceLink: '',
      tweLink: '',
      instaLink: ''
    };
  }


  // =========================
  // UPDATE CHEF
  // =========================

  updateChef(): void {

    this.clearMessages();

    if (
      !this.updateChefData.name ||
      !this.updateChefData.spec ||
      !this.updateChefData.logoPath ||
      !this.updateChefData.faceLink ||
      !this.updateChefData.tweLink ||
      !this.updateChefData.instaLink
    ) {

      this.errorMessage =
        'Please fill all chef fields | من فضلك املأ جميع بيانات الشيف';

      return;
    }

    this.chefService
      .updateChef(this.updateChefData)
      .subscribe({

        next: () => {

          this.successMessage =
            'Chef updated successfully | تم تعديل الشيف بنجاح';

          this.showUpdateForm = false;

          this.getChefs();
        },

        error: (error) => {

          console.log('Update chef error:', error);

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // DELETE CHEF
  // =========================

  deleteChef(id: number): void {

    const confirmed =
      confirm(
        'Are you sure you want to delete this chef?'
      );

    if (!confirmed) {
      return;
    }

    this.clearMessages();

    this.chefService
      .deleteChef(id)
      .subscribe({

        next: () => {

          this.successMessage =
            'Chef deleted successfully | تم حذف الشيف بنجاح';

          if (
            this.chefs.length === 1 &&
            this.currentPage > 1
          ) {
            this.currentPage--;
          }

          this.getChefs();
        },

        error: (error) => {

          console.log('Delete chef error:', error);

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // PAGINATION
  // =========================

  calculatePages(): void {

    this.totalPages =
      Math.ceil(
        this.totalChefs / this.pageSize
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


  previousPage(): void {

    if (this.currentPage > 1) {

      this.currentPage--;

      this.getChefs();
    }
  }


  nextPage(): void {

    if (
      this.currentPage < this.totalPages
    ) {

      this.currentPage++;

      this.getChefs();
    }
  }


  goToPage(page: number): void {

    if (
      page < 1 ||
      page > this.totalPages ||
      page === this.currentPage
    ) {
      return;
    }

    this.currentPage = page;

    this.getChefs();
  }


  changePageSize(): void {

    this.currentPage = 1;

    this.getChefs();
  }


  // =========================
  // ERROR MESSAGE
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


  clearMessages(): void {

    this.successMessage = '';
    this.errorMessage = '';
  }

}
