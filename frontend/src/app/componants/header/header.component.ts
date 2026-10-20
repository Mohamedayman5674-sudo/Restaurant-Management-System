import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { CategoryService } from '../../../service/category.service';
import { Category } from '../../../model/category';
import { AuthService } from '../../../service/security/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  categories: Category[] = [];

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    if (this.isUserLogin()) {
      this.getCategories();
    }

    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      )
      .subscribe(() => {

        if (this.isUserLogin()) {
          this.getCategories();
        } else {
          this.categories = [];
        }

      });
  }

  getCategories(): void {

    this.categoryService.getAllCategories(1, 100).subscribe({

      next: (response) => {
        this.categories = response.categories;
      },

      error: (error) => {
        console.log(error);
        this.categories = [];
      }

    });
  }

  search(key: string): void {

    if (!key || !key.trim()) {
      this.router.navigate(['/products']);
      return;
    }

    this.router.navigateByUrl('/search/' + key.trim());
  }

  isUserLogin(): boolean {
    return this.authService.isUserLogin();
  }

  isAdmin(): boolean {
    return this.authService.getRoles().includes('ADMIN');
  }

  logout(): void {
    this.authService.logout();
    this.categories = [];
  }
}
