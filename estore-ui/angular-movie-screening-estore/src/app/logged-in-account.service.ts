import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
/**
 * Service to keep track of the current user logged in.
 */
export class LoggedInAccountService {
  username?: String;

  constructor() { }

  /**
   * Set the username of the user logged-in.
   * @param username Username of the user
   */
  setUsername(username: String): void {
    this.username = username;
  }

  /**
   * Get the username of the currently logged-in user.
   */
  getUsername(): String {
    return <String> this.username;
  }

  /**
   * Is there a user currently logged in?
   */
  isLoggedIn(): Boolean {
    return !(this.username === undefined);
  }

  /**
   * Is the current user logged in the admin?
   */
  isAdmin(): Boolean {
    return this.username === 'admin';
  }

  logout(): void {
    this.username = undefined;
  }
}
