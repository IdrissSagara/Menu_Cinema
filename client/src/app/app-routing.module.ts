import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SigninComponent} from './authentification/signin/signin.component';
import {SignupComponent} from './authentification/signup/signup.component';
import {FilmsComponent} from './films/films.component';
import {MenusComponent} from './menus/menus.component';
import {SingleMovieComponent} from './single-movie/single-movie.component';
import {AuthGuardService} from './service/auth-guard.service';
import {UserProfilComponent} from './user-profil/user-profil.component';
import {HomeComponent} from './home/home.component';
import {CommandeComponent} from './commande/commande.component';
import {PanierComponent} from './panier/panier.component';

const routes: Routes = [
    {path: 'authentification/signin', component: SigninComponent},
    {path: 'authentification/signup', component: SignupComponent},
    {path: 'films', canActivate: [AuthGuardService], component: FilmsComponent},
    {path: 'menus', canActivate: [AuthGuardService], component: MenusComponent},
    {path: 'user/dashbord', component: UserProfilComponent },
    {path: 'user/commande', component: CommandeComponent },
    {path: 'user/panier', component: PanierComponent },
    {path: '', component: HomeComponent },
    {path: 'films/view/:id', component: SingleMovieComponent},
    {path: '', redirectTo: 'films', pathMatch: 'full'},
    //{path: '**', redirectTo: 'films'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
