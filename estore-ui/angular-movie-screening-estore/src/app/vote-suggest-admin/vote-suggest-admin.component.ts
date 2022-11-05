import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Vote } from '../vote';

@Component({
  selector: 'app-vote-suggest-admin',
  templateUrl: './vote-suggest-admin.component.html',
  styleUrls: ['./vote-suggest-admin.component.css']
})
/**
 * Class to allow the admin to change the vote objects in the system through adding, changing, and deleting them
 */
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

  /** Constructor for the vote admin page to allow it to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<[Vote]>('http://127.0.0.1:8080/votes/').subscribe((data: [Vote]) => {
      this.votes = data;
    }, (response) => {
      console.log("here");
    })
  }

  /**
   * Method for the admin to add a new vote to the system
   * @param name name of the movie to be voted on
   */
  enterNewMovie(name: String): void {
    this.http.post<Vote>('http://127.0.0.1:8080/votes/', {id: 1, movieName: name, howManyVotes: 0}).subscribe((data: Vote) => {
      this.newVote = data;
    }, (response) => {
      document.getElementById("adminAddVote")!.innerHTML = "Movie has already been added.";
    })
  }

  /**
   * Method to allow the admin to select a movie to change
   * @param vote the vote object selected
   */
  changeVote(vote: Vote): void {
    this.voteSelected = true;
    this.voteSelectedToChange = vote;
  }

  /**
   * The method for the admin to change the vote by changing the name
   * @param updateName the name to change
   */
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
