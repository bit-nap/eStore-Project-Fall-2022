import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedPurchaseComponent } from './completed-purchase.component';

describe('CompletedPurchaseComponent', () => {
  let component: CompletedPurchaseComponent;
  let fixture: ComponentFixture<CompletedPurchaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompletedPurchaseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompletedPurchaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
