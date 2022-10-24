import { Component, Input, OnInit } from "@angular/core";
import { MoviesComponent } from "../movies/movies.component";
import { Movies } from "../Movies";

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css'],
  providers: [MoviesComponent]
})
export class TicketsComponent implements OnInit {
  @Input() numOfTickets: Number = 0;
  selectedMovie?:Movies;

  constructor(private movieComponent: MoviesComponent) { }

  ngOnInit(): void {
    this.numOfTickets = 0;
    this.selectedMovie = this.movieComponent.getSelected()
  }
}
