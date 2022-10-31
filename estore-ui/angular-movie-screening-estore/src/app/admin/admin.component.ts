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
    this.getScreeningListings(screening);
  }

  changeButton(screening: Screenings): void {
    this.screeningSelected = true;
    this.screeningToChange = screening;

    this.http.get<Movie>('http://127.0.0.1:8080/movies/'+this.screeningToChange.movieId).subscribe((data: Movie) => {
      this.movieForSelectedScreening = data;
    });
  }

  changeAccount(updateTickets: string, updateDate: string, updateTime: string): void {
    var tickets = new String("");
    var date = new String("");
    var time = new String("");

    if (updateTickets === null || updateTickets === "") {
      tickets = this.screeningToChange.ticketsRemaining.toString();
    } else {
      tickets = updateTickets;
    }
    if (updateDate === null || updateDate === "") {
      date = this.screeningToChange.date.toString();
    } else {
      date = updateDate;
    }
    if (updateTime === null || updateTime === "") {
      time = this.screeningToChange.time.toString();
    } else {
      time = updateTime;
    }

    console.log(this.screeningToChange.id, this.screeningToChange.movieId, tickets.replace(/\D/g, ''), date, time);

    this.http.put<Screenings>('http://127.0.0.1:8080/screenings', {id: this.screeningToChange.id, movieId: this.screeningToChange.movieId, ticketsRemaining: Number(tickets), date: date, time: time}).subscribe((data: Screenings) => {
      this.screeningToChange = data;
    });

    console.log(this.screeningToChange.id);
    this.getScreeningListings(this.screeningToChange.movieId.toString());
  }

  deleteScreening(): void {
    this.http.delete<[Screenings]>('http://127.0.0.1:8080/screenings/'+this.screeningToChange.id).subscribe((data: Screenings[]) => {
      this.screenings = data;
    });

    this.getScreeningListings(this.screeningToChange.movieId.toString());
  }

  getScreeningListings(movieId: string): void {
    this.http.get<[Screenings]>('http://127.0.0.1:8080/screenings/?movieId='+movieId).subscribe((data: Screenings[]) => {
      this.screenings = data;
    });
  }

}
