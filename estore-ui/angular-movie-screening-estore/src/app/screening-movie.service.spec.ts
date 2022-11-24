import { TestBed } from '@angular/core/testing';

import { ScreeningMovieService } from './screening-movie.service';

describe('ScreeningMovieService', () => {
  let service: ScreeningMovieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScreeningMovieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
