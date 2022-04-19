import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userId: string = "";
  name: string = "name";
  username:string= "username";
  wallet: number = 1000000;
  password: string = "";

  moneyToAdd: number = 0;
  addingMode:boolean = false;
  constructor(private http: HttpClient ) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId') ?? "";
    this.getUser();
  }

  switchAdding() {
    this.addingMode = !this.addingMode;
  }

  addMoney() {
    this.http.put(
      'http://localhost:8080/user/' + this.userId, 
      {name: this.name, username: this.username, wallet: this.wallet + this.moneyToAdd*100, access: 'USER', password: this.password}, 
      { observe: 'response' })
    .subscribe(response => {
      console.log(response);
      this.getUser();
    })
  }

  getUser() {
    if (this.userId == "") {
      alert('Что-то пошло не так\n перезайдите');
      return;
    }
    this.http.get('http://localhost:8080/user/' + this.userId, { observe: 'response' })
      .subscribe(response => {
        this.setUserData(response.body);
      })
  }

  setUserData(user: any) {
    this.name = user.name;
    this.username = user.username;
    this.wallet = user.wallet;
  }
  

}
