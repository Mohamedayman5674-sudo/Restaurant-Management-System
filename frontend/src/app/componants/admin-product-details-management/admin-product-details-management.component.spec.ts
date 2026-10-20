import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProductDetailsManagementComponent } from './admin-product-details-management.component';

describe('AdminProductDetailsManagementComponent', () => {
  let component: AdminProductDetailsManagementComponent;
  let fixture: ComponentFixture<AdminProductDetailsManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminProductDetailsManagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminProductDetailsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
