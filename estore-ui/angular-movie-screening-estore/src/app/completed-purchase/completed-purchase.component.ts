import { Component, OnInit } from '@angular/core';
import { MovieSelectorService } from "../movie-selector.service";

@Component({
  selector: 'app-completed-purchase',
  templateUrl: './completed-purchase.component.html',
  styleUrls: ['./completed-purchase.component.css']
})
export class CompletedPurchaseComponent implements OnInit {
  constructor(private movieSelector: MovieSelectorService) { }

  ngOnInit(): void {
  }

  getMovieTitle(): String {
    return this.movieSelector.getMovieTitle();
  }
}
