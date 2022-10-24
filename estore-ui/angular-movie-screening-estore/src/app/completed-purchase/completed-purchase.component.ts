import { Component, OnInit } from '@angular/core';
import { MovieSelectorService } from "../movie-selector.service";
import { Movies } from "../Movies";

@Component({
  selector: 'app-completed-purchase',
  templateUrl: './completed-purchase.component.html',
  styleUrls: ['./completed-purchase.component.css']
})
export class CompletedPurchaseComponent implements OnInit {
  movie?: Movies;

  constructor(private movieSelector: MovieSelectorService) { }

  ngOnInit(): void {
    this.movie = this.movieSelector.getMovie();
  }

  getMovieTitle(): String {
    return <String> this.movie?.title;
  }
}
