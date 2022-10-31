import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MoviesComponent } from './movies/movies.component';
import { TicketsComponent } from "./tickets/tickets.component";
import { CompletedPurchaseComponent } from "./completed-purchase/completed-purchase.component";
import { AdminComponent } from './admin/admin.component';
import { ScreeningsComponent } from "./screenings/screenings.component";
import { VoteSuggestComponent } from './vote-suggest/vote-suggest.component';

const routes: Routes = [
  { path: '', component: MoviesComponent},
  { path: 'login', component: LoginComponent },
  { path: 'screenings', component: ScreeningsComponent},
  { path: 'tickets', component: TicketsComponent},
  { path: 'thank', component: CompletedPurchaseComponent},
  { path: 'admin', component: AdminComponent },
  { path: 'vote', component: VoteSuggestComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
