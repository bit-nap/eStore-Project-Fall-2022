import { Component, Input, OnInit } from "@angular/core";
import { MoviesComponent } from "../movies/movies.component";
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

  constructor(private router: Router, private movieSelector: MovieSelectorService) { }

  /**
   * Set this TicketsComponent's selected movie using the MovieSelectorService.
   */
  ngOnInit(): void {
  }

  /**
   * Get the title of the selected movie.
   */
  getMovieTitle(): String {
    return this.movieSelector.getMovieTitle();
  }

  /**
   * Get the poster fo the selected movie.
   */
  getMoviePoster(): String {
    return this.movieSelector.getMoviePoster();
  }

  /**
   * Method to call user presses the complete purchase button.
   */
  completePurchase(): void {
    // TODO: Add actions for completed a purchase, ie, save ticket information as Order class in Java or something
    this.router.navigate(['thank'])
  }
}
