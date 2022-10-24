import { TestBed } from '@angular/core/testing';

import { LoggedInAccountService } from './logged-in-account.service';

describe('LoggedInAccountService', () => {
  let service: LoggedInAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoggedInAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
