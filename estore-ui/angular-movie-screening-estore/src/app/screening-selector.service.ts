import { Injectable } from '@angular/core';
import { Screening } from "./screening";

@Injectable({
  providedIn: 'root'
})
/**
 * Class to keep track of the last screening selected by the user.
 */
export class ScreeningSelectorService {
  /** The last screening selected. */
  screening?: Screening;

  constructor() { }

  /**
   * Set the last screening selected.
   * @param screening The last screening selected
   */
  setScreening(screening: Screening): void {
    this.screening = screening;
  }

  /**
   * Get the screening id of the last screening selected.
   */
  getScreeningId(): number {
    return <number> this.screening?.id;
  }
  /**
   * Get the movie id of the movie being shown at the last screening selected.
   */

  getScreeningMovieId(): number {
    return <number> this.screening?.movieId;
  }

  /**
   * Get the number of tickets of the last screening selected.
   */
  getScreeningTicketsRemaining(): number {
    return <number> this.screening?.ticketsRemaining;
  }

  /**
   * Get the date of the last screening selected.
   */
  getScreeningDate(): string {
    return <string> this.screening?.date;
  }

  /**
   * Get the time of the last screening selected.
   */
   getScreeningTime(): string {
    return <string> this.screening?.time;
  }

  /**
   * Get the seats of the last screening selected.
   */
   getScreeningSeats(): boolean[][] {
    return <boolean[][]> this.screening?.seats;
  }

  
}
