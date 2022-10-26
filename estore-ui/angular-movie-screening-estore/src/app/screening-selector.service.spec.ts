import { TestBed } from '@angular/core/testing';

import { ScreeningSelectorService } from './screening-selector.service';

describe('ScreeningSelectorService', () => {
  let service: ScreeningSelectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScreeningSelectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
