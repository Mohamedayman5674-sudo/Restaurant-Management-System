import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/security/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  messageEn = '';
  messageAr = '';

  signupData = {
    username: '',
    password: '',
    confirmPassword: '',
    age: null as number | null,
    phoneNumber: '',
    address: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  createAccount(
    username: string,
    password: string,
    confirmPassword: string,
    age: number | null,
    phoneNumber: string,
    address: string
  ): void {

    if (!this.validateInput(
      username,
      password,
      confirmPassword,
      age,
      phoneNumber,
      address
    )) {
      return;
    }

    this.authService.signup(
      username,
      password,
      age!,
      phoneNumber,
      address
    ).subscribe({
      next: () => {

        this.messageEn = 'Account created successfully.';
        this.messageAr = 'تم إنشاء الحساب بنجاح.';

        // After successful signup go to login
        this.router.navigateByUrl('/login');
      },

      error: (error) => {

        this.messageEn =
          error?.error?.bundleMessage?.message_en
          || 'Account creation failed.';

        this.messageAr =
          error?.error?.bundleMessage?.message_ar
          || 'فشل إنشاء الحساب.';
      }
    });
  }


  validateInput(
    username: string,
    password: string,
    confirmPassword: string,
    age: number | null,
    phoneNumber: string,
    address: string
  ): boolean {

    if (!username) {
      this.messageEn = 'Username is required.';
      this.messageAr = 'اسم المستخدم مطلوب.';
      return false;
    }

    if (username.length < 7) {
      this.messageEn = 'Username must be at least 7 characters.';
      this.messageAr = 'اسم المستخدم يجب أن يكون 7 أحرف على الأقل.';
      return false;
    }

    if (!password) {
      this.messageEn = 'Password is required.';
      this.messageAr = 'كلمة المرور مطلوبة.';
      return false;
    }

    if (
      !/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z\d]).{7,}/.test(password)
    ) {
      this.messageEn =
        'Password must be at least 7 characters and contain uppercase, lowercase, number and special character.';
      this.messageAr =
        'كلمة المرور يجب أن تكون 7 أحرف على الأقل وتحتوي على حرف كبير وحرف صغير ورقم ورمز خاص.';
      return false;
    }

    if (!confirmPassword) {
      this.messageEn = 'Confirm password is required.';
      this.messageAr = 'تأكيد كلمة المرور مطلوب.';
      return false;
    }

    if (password !== confirmPassword) {
      this.messageEn = 'Passwords do not match.';
      this.messageAr = 'كلمتا المرور غير متطابقتين.';
      return false;
    }

    if (age === null || age === undefined) {
      this.messageEn = 'Age is required.';
      this.messageAr = 'السن مطلوب.';
      return false;
    }

    if (age < 16 || age > 80) {
      this.messageEn = 'Age must be between 16 and 80.';
      this.messageAr = 'السن يجب أن يكون من 16 إلى 80.';
      return false;
    }

    if (!phoneNumber) {
      this.messageEn = 'Phone number is required.';
      this.messageAr = 'رقم الهاتف مطلوب.';
      return false;
    }

    if (!address) {
      this.messageEn = 'Address is required.';
      this.messageAr = 'العنوان مطلوب.';
      return false;
    }

    this.messageEn = '';
    this.messageAr = '';

    return true;
  }
}
