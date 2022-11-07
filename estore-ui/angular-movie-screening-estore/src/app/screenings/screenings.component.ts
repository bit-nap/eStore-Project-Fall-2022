import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { MovieSelectorService } from "../movie-selector.service";
import { ScreeningSelectorService } from "../screening-selector.service";
import { Screening } from "../screening";

@Component({
  selector: 'app-screenings',
  templateUrl: './screenings.component.html',
  styleUrls: ['./screenings.component.css']
})
/**
 * Screenings component for the front end. This class will show the available screenings from the server.
 * Each of the screenings will be a button to a router to a page where the user can purchase one
 */
export class ScreeningsComponent implements OnInit {
  /* Array of Screenings for the selected movie. */
  screenings?: Screening[];

  /** Constructor for screenings class to include an http client, a router, and both a screening selector service and a movie selector service */
  constructor(private http: HttpClient, private router: Router, private movieSelector: MovieSelectorService, private screeningSelector: ScreeningSelectorService) { }

  ngOnInit(): void {
    this.getScreenings();
  }

  /**
   * Method that will get the array of screenings given the movie id
   */
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
