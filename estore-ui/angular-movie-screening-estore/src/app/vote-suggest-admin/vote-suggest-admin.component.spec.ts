import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteSuggestAdminComponent } from './vote-suggest-admin.component';

describe('VoteSuggestAdminComponent', () => {
  let component: VoteSuggestAdminComponent;
  let fixture: ComponentFixture<VoteSuggestAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteSuggestAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteSuggestAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
