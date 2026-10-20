import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';

import { ProductsComponent } from './componants/products/products.component';
import { HeaderComponent } from './componants/header/header.component';
import { CategoryComponent } from './componants/category/category.component';
import { CardDetailsComponent } from './componants/card-details/card-details.component';
import { CardComponent } from './componants/card/card.component';
import { FooterComponent } from './componants/footer/footer.component';
import { ChefsComponent } from './componants/chefs/chefs.component';
import { ContactInfoComponent } from './componants/contact-info/contact-info.component';
import { LoginComponent } from './componants/login/login.component';
import { SignupComponent } from './componants/signup/signup.component';

import { AdminDashboardComponent } from './componants/admin-dashboard/admin-dashboard.component';
import { AdminCategoryManagementComponent } from './componants/admin-category-management/admin-category-management.component';
import { OrdersComponent } from './componants/orders/orders.component';
import { AdminOrdersComponent } from './componants/admin-orders/admin-orders.component';
import { AdminProductManagementComponent } from './componants/admin-product-management/admin-product-management.component';

import { BrowserModule } from '@angular/platform-browser';
import { APP_BASE_HREF } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AuthInterceptor } from '../service/interceptor/auth.interceptor';
import { AuthGuard } from '../service/security/auth.guard';
import { AdminGuard } from '../service/security/admin.guard';

import { AdminChefManagementComponent } from './componants/admin-chef-management/admin-chef-management.component';
import { AdminUserManagementComponent } from './componants/admin-user-management/admin-user-management.component';
import { AdminProductDetailsManagementComponent } from './componants/admin-product-details-management/admin-product-details-management.component';
import { AdminContactInformationManagementComponent } from './componants/admin-contact-information-management/admin-contact-information-management.component';
import {GuestGuard} from '../service/security/guest.guard';


export const routes: Routes = [

  // =========================
  // ADMIN DASHBOARD
  // =========================

  {
    path: 'admin/dashboard',
    component: AdminDashboardComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN CATEGORY MANAGEMENT
  // =========================

  {
    path: 'admin/categories',
    component: AdminCategoryManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN PRODUCT MANAGEMENT
  // =========================

  {
    path: 'admin/products',
    component: AdminProductManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN CHEF MANAGEMENT
  // =========================

  {
    path: 'admin/chefs',
    component: AdminChefManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN USER MANAGEMENT
  // =========================

  {
    path: 'admin/users',
    component: AdminUserManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN PRODUCT DETAILS MANAGEMENT
  // =========================

  {
    path: 'admin/product-details',
    component: AdminProductDetailsManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN CONTACT INFORMATION MANAGEMENT
  // =========================

  {
    path: 'admin/contact-information',
    component: AdminContactInformationManagementComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // ADMIN ORDERS
  // =========================

  {
    path: 'admin/orders',
    component: AdminOrdersComponent,
    canActivate: [AdminGuard]
  },


  // =========================
  // PRODUCTS
  // =========================

  {
    path: 'products',
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'category/:id',
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'search/:key',
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'category/:id/search/:key',
    component: ProductsComponent,
    canActivate: [AuthGuard]
  },


  // =========================
  // ORDERS
  // =========================

  {
    path: 'orders',
    component: OrdersComponent,
    canActivate: [AuthGuard]
  },


  // =========================
// CARD
// =========================

  {
    path: 'cardDetails',
    component: CardDetailsComponent,
    canActivate: [AuthGuard]
  },


// =========================
// CONTACT
// =========================

  {
    path: 'contact-info',
    component: ContactInfoComponent,
    canActivate: [AuthGuard]
  },


// =========================
// CHEFS
// =========================

  {
    path: 'chefs',
    component: ChefsComponent,
    canActivate: [AuthGuard]
  },


  // =========================
  // AUTH
  // =========================

  {
    path: 'login',
    component: LoginComponent,
    canActivate: [GuestGuard]
  },
  {
    path: 'signup',
    component: SignupComponent,
    canActivate: [GuestGuard]
  },


  // =========================
  // DEFAULT
  // =========================

  {
    path: '',
    redirectTo: '/products',
    pathMatch: 'full'
  },


  // =========================
  // NOT FOUND
  // =========================

  {
    path: '**',
    redirectTo: '/products',
    pathMatch: 'full'
  }

];


@NgModule({

  declarations: [

    AppComponent,

    ProductsComponent,

    HeaderComponent,

    CategoryComponent,

    CardDetailsComponent,

    CardComponent,

    FooterComponent,

    ChefsComponent,

    ContactInfoComponent,

    LoginComponent,

    SignupComponent,

    AdminDashboardComponent,

    AdminCategoryManagementComponent,

    OrdersComponent,

    AdminOrdersComponent,

    AdminProductManagementComponent,

    AdminChefManagementComponent,

    AdminUserManagementComponent,

    AdminProductDetailsManagementComponent,

    AdminContactInformationManagementComponent

  ],


  imports: [

    BrowserModule,

    RouterModule.forRoot(routes),

    HttpClientModule,

    FormsModule

  ],


  providers: [

    {
      provide: APP_BASE_HREF,
      useValue: '/'
    },

    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }

  ],


  bootstrap: [

    AppComponent

  ]

})


export class AppModule {}
