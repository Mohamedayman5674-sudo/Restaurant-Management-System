import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminChefManagementComponent } from './admin-chef-management.component';

describe('AdminChefManagementComponent', () => {
  let component: AdminChefManagementComponent;
  let fixture: ComponentFixture<AdminChefManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminChefManagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminChefManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
