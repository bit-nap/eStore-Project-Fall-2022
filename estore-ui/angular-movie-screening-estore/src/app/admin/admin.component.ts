import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Movie } from '../movie';
import { Screenings } from '../Screenings';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  newScreenings: Screenings = {
    id: 0,
    movieId: 0,
    ticketsRemaining: 0,
    date: '',
    time: ''
  };
  screenings: Screenings[] = [];
  screeningSelected: boolean = false;
  screeningToChange: Screenings = {
    id: 0,
    movieId: 0,
    ticketsRemaining: 0,
    date: '',
    time: ''
  };
  movieForSelectedScreening: Movie = {
    id: 0,
    title: '',
    poster: '',
    runtime: '',
    mpaRating: '',
    year: 0
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  enterNewScreening(screeningTicket:string, screeningDate:string, screeningTime:string, screeningMovie:string) {
    this.http.post<Screenings>('http://127.0.0.1:8080/screenings', { id: 1, movieId: screeningMovie, tickets: screeningTicket.replace(/\D/g, ''), date: screeningDate, time: screeningTime }).subscribe((data:Screenings) => {
      this.newScreenings = data;
    });
  }

  selectScreening(screening: string): void {
    this.screeningSelected = false;
    this.screeningToChange = {
      id: 0,
      movieId: 0,
      ticketsRemaining: 0,
      date: '',
      time: ''
    };
    this.http.get<[Screenings]>('http://127.0.0.1:8080/screenings/?movieId='+screening).subscribe((data: Screenings[]) => {
      this.screenings = data;
    });
  }

  changeButton(screening: Screenings): void {
    this.screeningSelected = true;
    this.screeningToChange = screening;

    this.http.get<Movie>('http://127.0.0.1:8080/movies/'+this.screeningToChange.movieId).subscribe((data: Movie) => {
      this.movieForSelectedScreening = data;
    });

    // Need to add screening here to get
  }

  changeAccount(updateTickets: string, updateDate: string, updateTime: string): void {
    var tickets = new String("");
    var date = new String("");
    var time = new String("");

    if (updateTickets === null || updateTickets === "") {
      tickets = this.screeningToChange.ticketsRemaining.toString();
    }
    if (updateDate === null || updateDate === "") {
      date = this.screeningToChange.ticketsRemaining.toString();
    }
    if (updateTime === null || updateTime === "") {
      time = this.screeningToChange.ticketsRemaining.toString();
    }

    this.http.put<Screenings>('http://127.0.0.1:8080/screenings', {id: this.screeningToChange.movieId, movieId: this.screeningToChange.movieId, ticketsRemaining: tickets, date: date, time: time}).subscribe((data: Screenings) => {
      this.screeningToChange = data;
    })
  }

  deleteScreening(): void {
    this.http.delete<[Screenings]>('http://127.0.0.1:8080/screenings/'+this.screeningToChange.id).subscribe((data: Screenings[]) => {
      this.screenings = data;
    });
  }

}
