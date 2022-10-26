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
export class ScreeningsComponent implements OnInit {
  /* Array of Screenings for the selected movie. */
  screenings: Screening[] = [];

  constructor(private http: HttpClient, private router: Router, private movieSelector: MovieSelectorService, private screeningSelector: ScreeningSelectorService) { }

  ngOnInit(): void {
    this.http.get<[Screening]>('http://127.0.0.1:8080/screenings?title'+ this.movieSelector.getMovieTitle()).subscribe((data: Screening[]) => {
      this.screenings = data;
    })
  }

  onSelect(screening: Screening) {
    this.screeningSelector.setScreening(screening);
    this.router.navigate(['tickets']);
  }

}
