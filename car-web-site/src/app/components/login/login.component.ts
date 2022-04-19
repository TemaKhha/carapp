import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginService } from 'src/app/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

@Injectable()
export class LoginComponent implements OnInit {

  loginUserName: string = "";
  loginPassword: string = "";

  username: string = "";
  password: string = "";
  name: string = "";

  response: any;


  constructor(private http: HttpClient) {

  }

  isLogin: boolean = true;

  ngOnInit(): void {
    this.isLogin = true;

  }

  showLogin() {
    this.isLogin = true;
  }

  showSignUp() {
    this.isLogin = false;
  }

  signUp() {
    if (this.name == "" || this.username == "" || this.password == "") {
      alert('Заполните все поля');
      return;
    }
    console.log(this.name, this.username, this.password);
    this.http.post(
      'http://localhost:8080/user',
      { username: this.username, password: this.password, name: this.name, wallet: 0, access: "USER" },
      { observe: 'response' }
    )
      .subscribe(response => {
        alert('Вы создали аккаунт!\nВаше имя пользователя:' + this.username);
        this.isLogin = true;
        this.loginUserName = this.username;
      }, (error) => {
        alert('Имя пользователя занято')
      });
  }

  login() {
    this.http.post(
      'http://localhost:8080/login',
      { username: this.loginUserName, password: this.loginPassword },
      { observe: 'response' },
    )
      .subscribe(response => {
        
        console.log(response.status);
        console.log(response.body)
      }, (error) => {
        alert('Что-то пошло не так')
      });
  }


}
