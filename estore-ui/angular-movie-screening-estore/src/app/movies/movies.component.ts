import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Movies } from '../Movies' // Import the interface made for the values of the movie

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],
})
export class MoviesComponent implements OnInit {

  movies: Movies[] = [];
  selectedMovie?: Movies;

  constructor(private http: HttpClient) { }

  /**
   * Method that will get the list of movies from the cURL command so we can display them on the webpage
   */
  ngOnInit(): void {
    this.http.get<[Movies]>('http://127.0.0.1:8080/movies').subscribe(data => {
      this.movies = data;
    })
  }

  /**
   * Method that will set the selected movie when the user presses the button
   * @param movie the movie that has been selected
   */
  onSelect(movie: Movies): void {
    this.selectedMovie = movie;
  }
}
