import { Injectable } from '@angular/core';
import { Screening } from './screening';
import { Movie } from './movie';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
/**
 * Service class for selecting and obtaining data from Screenings and Movies
 */
export class ScreeningMovieService {
  // screenings: Screening[] = [];
  screeningMap = new Map<number, Screening>();
  movieMap = new Map<number, Movie>();

  constructor(private http: HttpClient) {
    this.getScreenings();
    this.getMovies();
  }

  getScreenings(): void {
    this.http.get<[Screening]>('http://localhost:8080/screenings/').subscribe((screenings_data: Screening[]) => {
      // this.screenings = screenings_data;
      this.setScreeningMap(screenings_data);
    })
  }

  setScreeningMap(screenings: Screening[]): void {
    for (var screening of screenings) {
      this.screeningMap.set(screening.id, screening);
    }
  }

  getMovies(): void {
    this.http.get<[Movie]>('http://localhost:8080/movies').subscribe((movies_data: Movie[]) => {
      this.setMovieMap(movies_data);
    })
  }

  setMovieMap(movies: Movie[]): void {
    for (var movie of movies) {
      this.movieMap.set(movie.id, movie);
    }
  }

  getScreeningTime(screeningId: number): String {
    return <String> this.screeningMap.get(screeningId)?.time;
  }

  getScreeningDate(screeningId: number): String {
    return <String> this.screeningMap.get(screeningId)?.date;
  }

  getScreeningSeats(screeningId: number): Boolean[][] {
    return <Boolean[][]> this.screeningMap.get(screeningId)?.seats;
  }

  getScreeningMovieId(screeningId: number): number {
    return <number> this.screeningMap.get(screeningId)?.movieId;
  }

  getMovieTitle(screeningId: number): String {
    return <String> this.movieMap.get(this.screeningMap.get(screeningId)!.movieId)?.title;
  }

  getMoviePoster(screeningId: number): String {
    return <String> this.movieMap.get(this.screeningMap.get(screeningId)!.movieId)?.poster;
  }
}
