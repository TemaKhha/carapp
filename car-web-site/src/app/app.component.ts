import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { interval } from 'rxjs';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  isAdmin:boolean = false;
  isUser:boolean = true;

 

  ngOnInit(): void {
    interval(100).subscribe(x => {
      this.checkStorage();
    })
  }

  checkStorage() {
    var id = localStorage.getItem('userId');
    if (id == null) {
      this.isAdmin = false;
      this.isUser = false;
    } else if (id == '1') {
      this.isAdmin = true;
      this.isUser = false;
    } else {
      this.isAdmin = false;
      this.isUser = true;
    }
  }

 

}
