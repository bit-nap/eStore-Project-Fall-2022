import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from "@angular/core";

import { Movies } from '../Movies'
import { MovieSelectorService } from "../movie-selector.service";
import { Router } from "@angular/router";
import { LoggedInAccountService } from "../logged-in-account.service"; // Import the interface made for the values of the movie

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],
})
export class MoviesComponent implements OnInit {
  movies: Movies[] = [];

  constructor(private http: HttpClient, private router: Router, private movieSelector: MovieSelectorService, private loggedInAccount: LoggedInAccountService) { }

  /**
   * Method that will get the list of movies from the cURL command so we can display them on the webpage
   */
  ngOnInit(): void {
    this.http.get<[Movies]>('http://127.0.0.1:8080/movies').subscribe((data: Movies[]) => {
      this.movies = data;
    })
  }

  /**
   * Method that will set the selected movie when the user presses the button
   * @param movie the movie that has been selected
   */
  onSelect(movie: Movies): void {
    this.movieSelector.setMovie(movie);
    if (this.loggedInAccount.isLoggedIn() && !this.loggedInAccount.isAdmin()) {
      // only go to tickets page if user is logged in, but they are not admin
      this.router.navigate(['tickets'])
    }
  }

  /**
   * Method that will only display movies that fit the search name of what the user put in
   * @param value the name of the movie that the user put into the search box
   */
  searchMoviesByName(value: string): void {
    this.http.get<[Movies]>('http://127.0.0.1:8080/movies/?title='+value).subscribe((data: Movies[]) => {
      this.movies = data;
    })
  }

  searchMoviesByDate(value: string): void {
    console.log(value);
    this.http.get<[Movies]>('http://127.0.0.1:8080/screenings/?date=2023-01-'+value).subscribe((data: Movies[]) => {
      this.movies = data;
    })
  }
}
