import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  canActivate(): boolean {

    // Must be logged in
    if (!this.authService.isUserLogin()) {
      this.router.navigateByUrl('/login');
      return false;
    }

    // Must be ADMIN
    const roles = this.authService.getRoles();

    if (roles.includes('ADMIN')) {
      return true;
    }

    // Logged in but not ADMIN
    this.router.navigateByUrl('/products');
    return false;
  }
}
