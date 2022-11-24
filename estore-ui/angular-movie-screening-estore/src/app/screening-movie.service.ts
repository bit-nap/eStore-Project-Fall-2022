import { Injectable } from '@angular/core';
import { Screening } from './screening';
import { Movie } from './movie';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
/**
 * Service class for selecting and obtaining data from Screenings and Movies
 */
export class ScreeningMovieService {
  screenings: Screening[] = [];

  constructor(private http: HttpClient) {
    this.setScreenings();
  }

  setScreenings(): void {
    this.http.get<[Screening]>('http://localhost:8080/screenings/').subscribe((screenings_data: Screening[]) => {
      this.screenings = screenings_data;
    })
  }

  getScreeningTime(screeningId: number): String {
    for (var screening of this.screenings) {
      if (screening.id == screeningId) {
        return <String> screening.time;
      }
    }
    console.log("Screening Time not found");
    return "Time not found";
  }
}
