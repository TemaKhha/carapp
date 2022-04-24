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
  serviceRequests: ServiceRequestWithCar[] = [];
  serviceRequestsDone: ServiceRequestWithCar[] = [];

  ngOnInit(): void {
    this.fetchRequests()
    this.fetchService()
  }

  isLoading: boolean = false;
  isServiceLoading = false;
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

  fetchService() {
    this.isServiceLoading = true;
    this.http.get('http://localhost:8080/service/', { observe: 'response' })
      .subscribe(response => {
        this.serviceRequests = (response.body as ServiceRequestWithCar[]).filter(request => this.activeStatus(request.serviceRequest.status));
        this.serviceRequestsDone = (response.body as ServiceRequestWithCar[]).filter(request => !this.activeStatus(request.serviceRequest.status));;
        console.log(this.serviceRequests)
        this.isServiceLoading = false;
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

  statusForRequestUpdateService(request: ServiceRequest): string {
    if (request.status == "CREATED") {
        return "Взять в работу";
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

  updateServiceStatus(id: number) {
    this.isServiceLoading = true;
    var request = this.serviceRequests.filter(req => req.serviceRequest.id == id)[0]
    let status: string = request.serviceRequest.status == "CREATED" ? "IN_PROCESS" : "DONE"
    this.http.put('http://localhost:8080/service/' + id + '?status=' + status, {}, { observe: 'response' })
    .subscribe(response => {
      this.isServiceLoading = false;
      this.fetchService();
      
    });
  }

  cancelRequestService(id: number) {
    this.isServiceLoading = true
    this.http.put('http://localhost:8080/service/' + id + '?status=CANCELLED', {}, { observe: 'response' })
    .subscribe(response => {
      this.isServiceLoading = false;
      this.fetchService();
    });
  }

  getOptionSplited(request: ServiceRequest): string[] {
    let res: string[] = []
    let unfRes = request.options.split(",");
    unfRes.forEach( unfStr => {
      if (unfStr == "CLEAN") {
        res.push("Чистка")
      }
      if (unfStr == "HARD_CLEAN") {
        res.push("Чистка + Мойка")
      }
      if (unfStr == "REPAIR") {
        res.push("Ремонт")
      }
      if (unfStr == "HARD_REPAIR") {
        res.push("Тяжелый ремонт")
      }
      if (unfStr == "TO") {
        res.push("Тех обслуживание")
      }
      if (unfStr == "DIAGNOSTIC") {
        res.push("Диагностика")
      }
    })
    return res;
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

export interface ServiceRequest {
  id: number;
  userId: number;
  carId: number;
  price: number;
  date: string;
  status: string;
  options: string;
}

export interface ServiceRequestWithCar {
  serviceRequest: ServiceRequest;
  car: Car;
}