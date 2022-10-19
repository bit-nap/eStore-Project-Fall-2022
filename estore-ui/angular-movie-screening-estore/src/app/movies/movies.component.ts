import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';

import { Movies } from '../Movies' // Import the interface made for the values of the movie

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],
})
export class MoviesComponent implements OnInit {
  imageSrc = "assets/batman-2.jpg";
  movies: Movies[] = [];
  @Input() selectedMovie?: Movies;

  constructor(private http: HttpClient) { }

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
    this.selectedMovie = movie;
  }

  /**
   * Method that will only display movies that fit the search name of what the user put in
   * @param value the name of the movie that the user put into the search box
   */
  searchMovies(value: string): void {
    this.http.get<[Movies]>('http://127.0.0.1:8080/movies/?title='+value).subscribe((data: Movies[]) => {
      this.movies = data;
    })
  }
}
