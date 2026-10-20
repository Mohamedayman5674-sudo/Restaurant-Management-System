import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../service/account.service';

@Component({
  selector: 'app-admin-user-management',
  templateUrl: './admin-user-management.component.html',
  styleUrls: ['./admin-user-management.component.css']
})
export class AdminUserManagementComponent implements OnInit {

  accounts: any[] = [];

  currentPage = 1;
  pageSize = 5;

  totalAccounts = 0;
  totalPages = 0;
  pages: number[] = [];

  showCreateForm = false;
  showUpdateForm = false;

  successMessage = '';
  errorMessage = '';

  newAccount: any = {
    username: '',
    password: '',
    enabled: '1',
    roles: [],
    accountDetails: {
      age: null,
      phoneNumber: '',
      address: ''
    }
  };

  updateAccountData: any = {
    id: null,
    username: '',
    password: '',
    enabled: '1',
    roles: [],
    accountDetails: {
      id: null,
      age: null,
      phoneNumber: '',
      address: ''
    }
  };


  constructor(
    private accountService: AccountService
  ) {}


  ngOnInit(): void {
    this.getAccounts();
  }


  // =========================
  // GET ACCOUNTS
  // =========================

  getAccounts(): void {

    this.accountService
      .getAllAccounts(
        this.currentPage,
        this.pageSize
      )
      .subscribe({

        next: (response) => {

          console.log(
            'Accounts response:',
            response
          );

          this.accounts =
            response.accounts || [];

          this.totalAccounts =
            response.totalAccounts || 0;

          this.calculatePages();
        },

        error: (error) => {

          console.log(
            'Get accounts error:',
            error
          );

          this.accounts = [];

          this.totalAccounts = 0;

          this.calculatePages();

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // OPEN CREATE FORM
  // =========================

  openCreateForm(): void {

    this.showCreateForm = true;

    this.showUpdateForm = false;

    this.clearMessages();

    this.newAccount = {

      username: '',

      password: '',

      enabled: '1',

      roles: [],

      accountDetails: {

        age: null,

        phoneNumber: '',

        address: ''

      }

    };
  }


  // =========================
  // CANCEL CREATE
  // =========================

  cancelCreate(): void {

    this.showCreateForm = false;

    this.newAccount = {

      username: '',

      password: '',

      enabled: '1',

      roles: [],

      accountDetails: {

        age: null,

        phoneNumber: '',

        address: ''

      }

    };
  }


  // =========================
  // CREATE ACCOUNT
  // =========================

  createAccount(): void {

    this.clearMessages();

    if (
      !this.newAccount.username ||
      !this.newAccount.password ||
      !this.newAccount.accountDetails.age ||
      !this.newAccount.accountDetails.phoneNumber ||
      !this.newAccount.accountDetails.address
    ) {

      this.errorMessage =
        'Please fill all user fields | من فضلك املأ جميع بيانات المستخدم';

      return;
    }


    this.accountService
      .createAccount(this.newAccount)
      .subscribe({

        next: () => {

          this.successMessage =
            'User created successfully | تم إنشاء المستخدم بنجاح';

          this.showCreateForm = false;

          this.getAccounts();
        },

        error: (error) => {

          console.log(
            'Create account error:',
            error
          );

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // OPEN UPDATE FORM
  // =========================

  openUpdateForm(account: any): void {

    this.showUpdateForm = true;

    this.showCreateForm = false;

    this.clearMessages();

    this.updateAccountData = {

      id: account.id,

      username: account.username,

      password: '',

      enabled: account.enabled,

      roles: account.roles || [],

      accountDetails: {

        id: account.accountDetails?.id,

        age: account.accountDetails?.age,

        phoneNumber:
          account.accountDetails?.phoneNumber || '',

        address:
          account.accountDetails?.address || ''

      }

    };
  }


  // =========================
  // CANCEL UPDATE
  // =========================

  cancelUpdate(): void {

    this.showUpdateForm = false;

    this.updateAccountData = {

      id: null,

      username: '',

      password: '',

      enabled: '1',

      roles: [],

      accountDetails: {

        id: null,

        age: null,

        phoneNumber: '',

        address: ''

      }

    };
  }


  // =========================
  // UPDATE ACCOUNT
  // =========================

  updateAccount(): void {

    this.clearMessages();

    if (
      !this.updateAccountData.username ||
      !this.updateAccountData.accountDetails.age ||
      !this.updateAccountData.accountDetails.phoneNumber ||
      !this.updateAccountData.accountDetails.address
    ) {

      this.errorMessage =
        'Please fill all user fields | من فضلك املأ جميع بيانات المستخدم';

      return;
    }


    this.accountService
      .updateAccount(
        this.updateAccountData
      )
      .subscribe({

        next: () => {

          this.successMessage =
            'User updated successfully | تم تعديل المستخدم بنجاح';

          this.showUpdateForm = false;

          this.getAccounts();
        },

        error: (error) => {

          console.log(
            'Update account error:',
            error
          );

          this.errorMessage =
            this.getErrorMessage(error);
        }

      });
  }


  // =========================
  // DELETE ACCOUNT
  // =========================

  deleteAccount(id: number): void {

    const confirmed =
      confirm(
        'Are you sure you want to delete this user?'
      );

    if (!confirmed) {

      return;
    }


    this.clearMessages();

    this.accountService
      .deleteAccount(id)
      .subscribe({

        next: () => {

          this.successMessage =
            'User deleted successfully | تم حذف المستخدم بنجاح';


          if (
            this.accounts.length === 1 &&
            this.currentPage > 1
          ) {

            this.currentPage--;
          }


          this.getAccounts();
        },

        error: (error) => {

          console.log(
            'Delete account error:',
            error
          );

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
        this.totalAccounts /
        this.pageSize
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

      this.getAccounts();
    }
  }


  nextPage(): void {

    if (
      this.currentPage <
      this.totalPages
    ) {

      this.currentPage++;

      this.getAccounts();
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

    this.getAccounts();
  }


  changePageSize(): void {

    this.currentPage = 1;

    this.getAccounts();
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


    return (
      'Something went wrong | حدث خطأ ما'
    );
  }


  // =========================
  // CLEAR MESSAGES
  // =========================

  clearMessages(): void {

    this.successMessage = '';

    this.errorMessage = '';
  }

}
