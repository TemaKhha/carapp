import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { By } from '@angular/platform-browser';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {

  cars:Car[] = [];
  buyingRequests: BuyingRequestWithCar[] = [];
  carCreateDTO: CarDTO = {};
  constructor(private http: HttpClient) { }

  isError = true;
  ngOnInit(): void {
    this.fetchCars();
    if (!this.isAdmin()) {
      this.fetchRequests();
    }
  }

  isAdmin():boolean {
    var id = localStorage.getItem('userId');
    
    return id=="1";
  }

  isUser():boolean {
    return !this.isAdmin();
  }

  fetchCars() {
      this.http.get('http://localhost:8080/car/1', { observe: 'response' })
      .subscribe(response => {
        this.cars = response.body as Car[];
        console.log(this.cars)
      })
  }

  fetchRequests() {
    let userId = localStorage.getItem('userId')
    this.http.get('http://localhost:8080/buy/user/' + userId, { observe: 'response' })
      .subscribe(response => {
        this.buyingRequests = response.body as BuyingRequestWithCar[];
        console.log(this.buyingRequests)
      })
  }

  isLoading = false;

  addCar() {
    if (this.isInputValid()) {
      this.carCreateDTO.price = (this.carCreateDTO.price ?? 1) * 100;
      this.carCreateDTO.userId = 1;
      this.carCreateDTO.image = "https://" + this.carCreateDTO.image;
      this.http.post('http://localhost:8080/car', JSON.parse(JSON.stringify(this.carCreateDTO)), { observe: 'response' })
      .subscribe(response => {
        this.fetchCars();
        this.cleanInput();
        alert('Вы добавили новую машину!')
      }, (error) => {
        this.cleanInput();
        alert('Что-то пошло не так...\nПроверьте качество ввода\nВозможна ошибка на сервере')
      })
    } else {

    }
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

  isStatusCancellable(request: BuyingRequest): boolean {
    return request.status == "CREATED";
  }

  createRequest(carId: number) {
    this.isLoading = true;
    let userId = localStorage.getItem('userId') as unknown as number;
    console.log(userId)
    this.http.post('http://localhost:8080/buy', {userId: userId, carId: carId}, { observe: 'response' })
    .subscribe(response => {
      let responseMessage: any = response.body;
      alert(responseMessage.message)
      this.isLoading = false;
      this.fetchRequests();
    });
  }

  cleanInput() {
    this.carCreateDTO.brand = undefined;
    this.carCreateDTO.model = undefined;
    this.carCreateDTO.price = undefined;
    this.carCreateDTO.color = undefined;
    this.carCreateDTO.image = undefined;
    this.carCreateDTO.year = undefined;
    this.carCreateDTO.maxSpeed = undefined;
  }

  imageLink(): string | null {
    
    return this.carCreateDTO.image == null ? null : "https://" + this.carCreateDTO.image
  }

  isInputValid():boolean {
    return this.carCreateDTO.brand != null && this.carCreateDTO.model != null 
    && this.carCreateDTO.year != null && this.carCreateDTO.price != null &&
    this.carCreateDTO.color != null && this.carCreateDTO.maxSpeed != null && this.carCreateDTO.image != null;
  }

  cancelRequest(id: number) {
    this.isLoading = true
    this.http.put('http://localhost:8080/buy/' + id + '?status=CANCELLED', {}, { observe: 'response' })
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