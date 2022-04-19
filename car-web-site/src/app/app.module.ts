import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarComponent } from './components/car/car.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http'
import { LoginService } from './login.service';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { StatisticComponent } from './components/statistic/statistic.component';

const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'about', component: ContactsComponent},
  {path: 'user', component: UserProfileComponent},
  {path: 'statistic', component: StatisticComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    CarComponent,
    ContactsComponent,
    LoginComponent,
    UserProfileComponent,
    StatisticComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
