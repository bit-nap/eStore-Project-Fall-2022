import { TestBed } from '@angular/core/testing';

import { MovieSelectorService } from './movie-selector.service';

describe('MovieSelectorService', () => {
  let service: MovieSelectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieSelectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
