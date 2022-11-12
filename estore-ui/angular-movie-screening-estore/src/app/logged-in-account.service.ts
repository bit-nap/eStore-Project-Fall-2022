import { Injectable } from '@angular/core';
import { Accounts } from './Accounts';

@Injectable({
  providedIn: 'root'
})
/**
 * Service to keep track of the current user logged in.
 */
export class LoggedInAccountService {
  // Account of user logged in
  account: Accounts = {
    id: -1,
    username: "",
    password: ""
  };

  constructor() { }

  /**
   * Set the account of the user logged in. Called as soon as user logs in.
   * @param account Account object passed in
   */
  setAccount(account: Accounts): void {
    this.account = account;
  }

  /**
   * Get the username of the currently logged-in user.
   */
  getUsername(): String {
    return <String> this.account.username;
  }

  /**
   * Get the id of the currently logged-in user.
   * @returns the user's id
   */
  getId(): number {
    return <number> this.account.id;
  }

  /**
   * Is there a user currently logged in?
   */
  isLoggedIn(): Boolean {
    return !(this.account.id === -1);
  }

  /**
   * Is the current user logged in the admin?
   */
  isAdmin(): Boolean {
    return this.account.username === 'admin';
  }

  logout(): void {
    this.account.id = -1;
    this.account.username = "";
    this.account.password = "";
  }
}
