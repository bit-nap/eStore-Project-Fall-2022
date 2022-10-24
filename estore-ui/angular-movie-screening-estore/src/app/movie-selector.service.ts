import { Injectable } from '@angular/core';
import { Movies } from "./Movies";

@Injectable({
  providedIn: 'root'
})
/**
 * Angular Service to track the last movie selected for use in the TicketsComponent.
 */
export class MovieSelectorService {
  /** The last movie selected */
  movie?: Movies;

  constructor() { }

  /**
   * Set the last movie selected.
   * @param movie The last movie selected
   */
  setMovie(movie: Movies): void {
    this.movie = movie;
  }

  /**
   * Get the last movie selected.
   */
  getMovie(): Movies {
    return <Movies> this.movie;
  }
}
