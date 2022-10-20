import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SodasComponent } from './sodas.component';

describe('SodasComponent', () => {
  let component: SodasComponent;
  let fixture: ComponentFixture<SodasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SodasComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SodasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
