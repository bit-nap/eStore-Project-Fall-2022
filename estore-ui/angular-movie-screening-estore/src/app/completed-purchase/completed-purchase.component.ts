import { Component, OnInit } from '@angular/core';
import { MovieSelectorService } from "../movie-selector.service";
import { ScreeningSelectorService } from "../screening-selector.service";

@Component({
  selector: 'app-completed-purchase',
  templateUrl: './completed-purchase.component.html',
  styleUrls: ['./completed-purchase.component.css']
})
export class CompletedPurchaseComponent implements OnInit {
  constructor(private movieSelector: MovieSelectorService, private screeningSelector: ScreeningSelectorService) { }

  ngOnInit(): void { }

  /**
   * Get the last selected movie's title.
   */
  getMovieTitle(): String {
    return this.movieSelector.getMovieTitle();
  }

  /**
   * Get the last selected screening's date.
   */
  getScreeningDate(): String {
    return this.screeningSelector.getScreeningDate();
  }

  /**
   * Get the last selected screening's time.
   */
  getScreeningTime(): String {
    return this.screeningSelector.getScreeningTime();
  }
}
