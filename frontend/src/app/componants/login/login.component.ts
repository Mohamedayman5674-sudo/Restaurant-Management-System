import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/security/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    username: '',
    password: ''
  };

  messageEn = '';
  messageAr = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  login(userName: string, password: string): void {

    if (!this.validateInput(userName, password)) {
      return;
    }

    this.authService.login(userName, password).subscribe({
      next: (response) => {

        // Save JWT token
        this.authService.saveToken(response.token);

        // Save user roles
        this.authService.saveRoles(response.userRoles);

        // Go to products page
        this.router.navigateByUrl('/products');
      },

      error: (error) => {

        this.messageEn =
          error?.error?.bundleMessage?.message_en
          || 'Login failed.';

        this.messageAr =
          error?.error?.bundleMessage?.message_ar
          || 'فشل تسجيل الدخول.';
      }
    });
  }

  validateInput(
    userName: string,
    password: string
  ): boolean {

    if (!userName) {

      this.messageEn =
        'Username is required.';

      this.messageAr =
        'اسم المستخدم مطلوب.';

      return false;
    }

    if (!password) {

      this.messageEn =
        'Password is required.';

      this.messageAr =
        'كلمة المرور مطلوبة.';

      return false;
    }

    this.messageEn = '';
    this.messageAr = '';

    return true;
  }

}
