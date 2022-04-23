import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-requests',
  templateUrl: './admin-requests.component.html',
  styleUrls: ['./admin-requests.component.css']
})
export class AdminRequestsComponent implements OnInit {

  constructor(private http: HttpClient) { }

  buyingRequests: BuyingRequestWithCar[] = [];
  buyingRequestsDone: BuyingRequestWithCar[] = [];
  ngOnInit(): void {
    this.fetchRequests()
  }

  isLoading: boolean = false;

  fetchRequests() {
    this.isLoading = true;
    this.http.get('http://localhost:8080/buy', { observe: 'response' })
      .subscribe(response => {
        this.isLoading = false;
        this.buyingRequests = (response.body as BuyingRequestWithCar[]).filter(request => this.activeStatus(request.request.status));
        this.buyingRequestsDone = (response.body as BuyingRequestWithCar[]).filter(request => !this.activeStatus(request.request.status));
        console.log(this.buyingRequests);
        console.log(this.buyingRequestsDone);
      })
  }

  activeStatus(status: string): boolean {
    return status == "CREATED" || status == "IN_PROCESS";
  }

  buttonStyleForRequest(request: BuyingRequest): string {
    if (request.status == "CREATED") {
      return "btn btn-secondary"; 
    } else if (request.status == "IN_PROCESS") {
      return "btn btn-primary"; 
    } else if (request.status == "REJECTED") {
      return "btn btn-danger"; 
    } else if (request.status == "CANCELLED") {
      return "btn btn-warning"; 
    } else {
      return "btn btn-success"; 
    }
  }

  humanReadableStatus(request: BuyingRequest): string {
    if (request.status == "CREATED") {
      return "НА РАССМОТРЕНИИ"; 
    } else if (request.status == "IN_PROCESS") {
      return "В ПРОЦЕССЕ"; 
    } else if (request.status == "REJECTED") {
      return "ОТКЛОНЕН"; 
    } else if (request.status == "CANCELLED") {
      return "ОТМЕНЕН"; 
    } else {
      return "ЗАВЕРШЕН"; 
    }
  }

  cancelRequest(id: number) {
    this.isLoading = true
    this.http.put('http://localhost:8080/buy/' + id + '?status=CANCELLED', {}, { observe: 'response' })
    .subscribe(response => {
      this.isLoading = false;
      this.fetchRequests();
    });
  }

  statusForRequestUpdate(request: BuyingRequest): string {
    if (request.status == "CREATED") {
        return "Начать обработку";
    } else {
      return "Завершить";
    }
  }

  updateStatus(id: number) {
    this.isLoading = true
    var request = this.buyingRequests.filter(req => req.request.id == id)[0]
    let status: string = request.request.status == "CREATED" ? "IN_PROCESS" : "DONE"
    this.http.put('http://localhost:8080/buy/' + id + '?status=' + status, {}, { observe: 'response' })
    .subscribe(response => {
      this.isLoading = false;
      this.fetchRequests();
    });
  }

}

export interface Car {
  id:number;
  model:string;
  brand:string;
  price:number;
  color:string;
  year:number;
  maxSpeed:number;
  userId:number;
  image:string;
}

export interface CarDTO {
  model?:string;
  brand?:string;
  price?:number;
  color?:string;
  year?:number;
  maxSpeed?:number;
  userId?:number;
  image?:string;
}

export interface BuyingRequest {
  id: number;
  userId: number;
  carId: number;
  price: number;
  date: string;
  status: string;
}

export interface BuyingRequestWithCar {
  request: BuyingRequest;
  car: Car;
}