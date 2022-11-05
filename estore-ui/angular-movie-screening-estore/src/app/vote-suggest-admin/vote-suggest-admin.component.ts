import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Vote } from '../vote';

@Component({
  selector: 'app-vote-suggest-admin',
  templateUrl: './vote-suggest-admin.component.html',
  styleUrls: ['./vote-suggest-admin.component.css']
})
export class VoteSuggestAdminComponent implements OnInit {
  votes: Vote[] = [];
  newVote: Vote = {
    id: 0,
    movieName: '',
    howManyVotes: 0
  };
  voteSelected: boolean = false;
  voteSelectedToChange: Vote = {
    id: 0,
    movieName: "",
    howManyVotes: 0
  };

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Vote]>('http://127.0.0.1:8080/votes/').subscribe((data: [Vote]) => {
      this.votes = data;
    }, (response) => {
      console.log("here");
    })
  }

  enterNewMovie(name: String): void {
    this.http.post<Vote>('http://127.0.0.1:8080/votes/', {id: 1, movieName: name, howManyVotes: 0}).subscribe((data: Vote) => {
      this.newVote = data;
      console.log("Objcect: " + this.newVote);
    })
  }

  changeVote(vote: Vote): void {
    this.voteSelected = true;
    this.voteSelectedToChange = vote;
  }

  changeVoteSubmit(updateName: String): void {
    var name = new String("");

    if (updateName === null || updateName === "") {
      name = this.voteSelectedToChange.movieName;
    } else {
      name = updateName;
    }

    this.http.put<Vote>('http://127.0.0.1:8080/votes/', {id: 1, movieName: name, howManyVotes: this.voteSelectedToChange.howManyVotes}).subscribe((data: Vote) => {
      this.voteSelectedToChange = data;
    });
  }

}
