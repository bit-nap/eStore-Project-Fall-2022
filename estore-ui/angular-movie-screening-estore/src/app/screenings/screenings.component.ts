import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { MovieSelectorService } from "../movie-selector.service";
import { ScreeningSelectorService } from "../screening-selector.service";
import { Screening } from "../screening";
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-screenings',
  templateUrl: './screenings.component.html',
  styleUrls: ['./screenings.component.css']
})
export class ScreeningsComponent implements OnInit {
  /* Array of Screenings for the selected movie. */
  screenings?: Screening[];

  constructor(private http: HttpClient, private router: Router, private movieSelector: MovieSelectorService, private screeningSelector: ScreeningSelectorService) { }

  ngOnInit(): void {
    this.getScreenings();
  }

  getScreenings(): void {
    // TODO: Figure out when in the lifecycle of the component to call this method
    this.http.get<[Screening]>('http://127.0.0.1:8080/screenings/?movieId='+ this.movieSelector.getMovieId()).subscribe((data: Screening[]) => {
      this.screenings = data;
    })
  }

  /**
   * Actions to perform when the user selects a Screening.
   * @param screening The Screening selected
   */
  onSelect(screening: Screening): void {
    this.screeningSelector.setScreening(screening);
    this.router.navigate(['tickets']);
  }

  /**
   * Get the selected movie's poster.
   */
  getMoviePoster(): string {
    return this.movieSelector.getMoviePoster();
  }

  /**
   * Get the selected movie's title.
   */
  getMovieTitle(): string {
    return this.movieSelector.getMovieTitle();
  }
}
