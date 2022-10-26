import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MoviesComponent } from './movies/movies.component';

// Http component added for angular so that we can make cURL requests from the server
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { TicketsComponent } from './tickets/tickets.component';
import { CompletedPurchaseComponent } from './completed-purchase/completed-purchase.component';
import { LoginComponent } from './login/login.component';
import { ScreeningsComponent } from './screenings/screenings.component';

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    TicketsComponent,
    CompletedPurchaseComponent,
    LoginComponent,
    ScreeningsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, //Http module to allow us to make the cURL calls
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
