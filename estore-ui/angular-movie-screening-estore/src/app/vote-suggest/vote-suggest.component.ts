import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Vote } from '../vote';

@Component({
  selector: 'app-vote-suggest',
  templateUrl: './vote-suggest.component.html',
  styleUrls: ['./vote-suggest.component.css']
})
export class VoteSuggestComponent implements OnInit {
  votes: Vote[] = [];
  newVote: Vote = {
    id: 0,
    movieName: '',
    howManyVotes: 0
  };

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    document.getElementById("hasUserVoted")!.innerHTML = "";
    this.http.get<[Vote]>('http://127.0.0.1:8080/votes/').subscribe((data: [Vote]) => {
      this.votes = data;
    });
  }

  addVote(vote: Vote): void {
    this.http.put<Vote>('http://127.0.0.1:8080/votes/', {id: vote.id, movieName: vote.movieName, howManyVotes: (vote.howManyVotes+1)}).subscribe((data: Vote) => {
      this.newVote = data;
    });

    document.getElementById("hasUserVoted")!.innerHTML = "Thank you for voting!";
  }

  enterVote(name: String): void {
    this.http.post<Vote>('http://127.0.0.1:8080/votes/', {id: 1, movieName: name, howManyVotes: 1}).subscribe((data: Vote) => {
      this.newVote = data;
      document.getElementById("hasUserSuggested")!.innerHTML = "Thank you for the suggestion!";
    }, (response) => {
      document.getElementById("hasUserSuggested")!.innerHTML = "Movie has already been suggested.";
    })
  }
}
