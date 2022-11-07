import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Vote } from '../vote';

@Component({
  selector: 'app-vote-suggest',
  templateUrl: './vote-suggest.component.html',
  styleUrls: ['./vote-suggest.component.css']
})
/**
 * Class for the user to vote and suggest a new movie
 */
export class VoteSuggestComponent implements OnInit {
  votes: Vote[] = [];
  newVote: Vote = {
    id: 0,
    movieName: '',
    howManyVotes: 0
  };

  /** Constructor for the user vote/suggest class to use an http client */
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    document.getElementById("hasUserVoted")!.innerHTML = "";
    this.http.get<[Vote]>('http://127.0.0.1:8080/votes/').subscribe((data: [Vote]) => {
      this.votes = data;
    });
  }

  /**
   * Method for the user to add a vote to a vote object
   * @param vote the vote object that will have a vote added
   */
  addVote(vote: Vote): void {
    this.http.put<Vote>('http://127.0.0.1:8080/votes/', {id: vote.id, movieName: vote.movieName, howManyVotes: (vote.howManyVotes+1)}).subscribe((data: Vote) => {
      this.newVote = data;
    });

    document.getElementById("hasUserVoted")!.innerHTML = "Thank you for voting!";
  }

  /**
   * Method to all a user to enter a new vote object if it has not already been added
   * @param name the name that will a vote will be made from
   */
  enterVote(name: String): void {
    this.http.post<Vote>('http://127.0.0.1:8080/votes/', {id: 1, movieName: name, howManyVotes: 1}).subscribe((data: Vote) => {
      this.newVote = data;
      document.getElementById("hasUserSuggested")!.innerHTML = "Thank you for the suggestion!";
    }, (response) => {
      document.getElementById("hasUserSuggested")!.innerHTML = "Movie has already been suggested.";
    })
  }
}
