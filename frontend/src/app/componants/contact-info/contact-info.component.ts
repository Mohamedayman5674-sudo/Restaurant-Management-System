import { Component, OnInit } from '@angular/core';
import {
  ContactInfo,
  ContactInfoService
} from '../../../service/contact-info.service';

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {

  contactInfo: ContactInfo = {
    name: '',
    email: '',
    subject: '',
    message: ''
  };

  successMessage = '';
  errorMessage = '';

  constructor(
    private contactInfoService: ContactInfoService
  ) { }

  ngOnInit(): void {
  }

  sendMessage(): void {

    this.successMessage = '';
    this.errorMessage = '';

    this.contactInfoService
      .createContactInfo(this.contactInfo)
      .subscribe({
        next: (response) => {
          console.log('Contact message sent successfully', response);

          this.successMessage = 'Message sent successfully';

          this.contactInfo = {
            name: '',
            email: '',
            subject: '',
            message: ''
          };
        },

        error: (error) => {
          console.log('Error sending message', error);

          this.errorMessage = 'Failed to send message';
        }
      });
  }
}
