import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SodasComponent } from './sodas/sodas.component';
import { LoginComponent } from './login/login.component';
import { MoviesComponent } from './movies/movies.component';

const routes: Routes = [
  { path: 'sodas', component: SodasComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
