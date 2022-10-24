import { Component, Input, OnInit } from "@angular/core";
import { MoviesComponent } from "../movies/movies.component";
import { Movies } from "../Movies";
import { MovieSelectorService } from "../movie-selector.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css'],
  providers: [MoviesComponent]
})
export class TicketsComponent implements OnInit {
  /** The number of tickets selected. */
  @Input() numOfTickets: Number = 0;
  /** The currently selected movie. */
  selectedMovie?:Movies;

  constructor(private router: Router, private movieSelector: MovieSelectorService) { }

  /**
   * Set this TicketsComponent's selected movie using the MovieSelectorService.
   */
  ngOnInit(): void {
    this.selectedMovie = this.movieSelector.getMovie()
  }

  /**
   * Get the title of the selected movie.
   */
  getMovieTitle(): String {
    return <String> this.selectedMovie?.title
  }

  /**
   * Get the poster fo the selected movie.
   */
  getMoviePoster(): String {
    return <String> this.selectedMovie?.poster;
  }

  /**
   * Method to call user presses the complete purchase button.
   */
  completePurchase(): void {
    // TODO: Add actions for completed a purchase, ie, save ticket information as Order class in Java or something
    this.router.navigate(['thank'])  // go to complete purchase page, currently thank
  }
}
