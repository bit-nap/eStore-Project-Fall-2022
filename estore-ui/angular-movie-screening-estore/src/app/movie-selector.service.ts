import { Injectable } from '@angular/core';
import { Movie } from "./movie";

@Injectable({
  providedIn: 'root'
})
/**
 * Class to keep track of the last movie selected by the user.
 */
export class MovieSelectorService {
  /** The last movie selected. */
  movie?: Movie;

  constructor() { }

  /**
   * Set the last movie selected.
   * @param movie The last movie selected
   */
  setMovie(movie: Movie): void {
    this.movie = movie;
  }

  /**
   * Get the id of the last movie selected.
   */
  getMovieId(): number {
    return <number> this.movie?.id;
  }

  /**
   * Get the title of the last movie selected.
   */
  getMovieTitle(): string {
    return <string> this.movie?.title;
  }

  /**
   * Get the path to the poster of the last movie selected.
   */
  getMoviePoster(): string {
    return <string> this.movie?.poster;
  }

  /**
   * Get the runtime of the last movie selected.
   */
  getMovieRuntime(): string {
    return <string> this.movie?.runtime;
  }

  /**
   * Get the MPA Rating of the last movie selected.
   */
  getMovieRating(): string {
    return <string> this.movie?.mpaRating;
  }

  /**
   * Get the release year of the last movie selected.
   */
  getMovieYear(): number {
    return <number> this.movie?.year;
  }
}
