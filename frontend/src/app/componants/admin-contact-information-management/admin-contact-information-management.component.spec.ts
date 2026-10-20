import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminContactInformationManagementComponent } from './admin-contact-information-management.component';

describe('AdminContactInformationManagementComponent', () => {
  let component: AdminContactInformationManagementComponent;
  let fixture: ComponentFixture<AdminContactInformationManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminContactInformationManagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminContactInformationManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
