import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean {

    // User is already logged in
    if (this.authService.isUserLogin()) {

      // Do not allow Login / Sign Up pages
      this.router.navigateByUrl('/products');

      return false;
    }

    // User is not logged in
    return true;
  }
}
