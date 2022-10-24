import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Movies Estore';

  constructor(private router:Router, private _location: Location) {}

  goToPage(pageName:string):void {
    this.router.navigate([`${pageName}`]);
  }

  goBack(): void {
    this._location.back();
  }
}
