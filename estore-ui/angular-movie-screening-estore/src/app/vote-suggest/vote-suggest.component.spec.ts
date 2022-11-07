import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteSuggestComponent } from './vote-suggest.component';

describe('VoteSuggestComponent', () => {
  let component: VoteSuggestComponent;
  let fixture: ComponentFixture<VoteSuggestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteSuggestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteSuggestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
