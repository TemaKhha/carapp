import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-cars',
  templateUrl: './user-cars.component.html',
  styleUrls: ['./user-cars.component.css']
})
export class UserCarsComponent implements OnInit {

  cars: Car[] = [];
  constructor(private http: HttpClient) { }

  carForMembership: Car | null = null;
  carForService: Car | null = null;
  fetchedPrice: number = 0;
  memFetchedPrice = 0;
  isLoading: boolean = false;
  serviceRequests: ServiceRequestWithCar[] = [];
  serviceOptions: ServiceOptions = {
    repair: false,
    hardRepair: false,
    clean: false,
    hardCelan: false,
    TO: false,
    diag: false
  };

  memDTO: MembershipDTO = {
    repair: 0,
    hardClean: 0,
    clean: 0,
    hardRepair: 0,
    to: 0,
    diag: 0
  }

  ngOnInit(): void {
    this.fetchCars()
    this.fetchRequests();
  }

  fetchCars() {
    let userId = localStorage.getItem('userId')

    this.http.get('http://localhost:8080/car/' + userId, { observe: 'response' })
      .subscribe(response => {
        this.cars = response.body as Car[];
        console.log(this.cars)
      })
  }

  isServiceReady(): boolean {
    return this.carForService != null;
  }

  isMembershipReady(): boolean {
    return this.carForMembership != null;
  }

  selectForService(car: Car) {
    this.carForMembership = null;
    this.carForService = car;
    this.fetchedPrice = 0;
    this.memFetchedPrice = 0;
    this.serviceOptions = {
      repair: false,
      hardRepair: false,
      clean: false,
      hardCelan: false,
      TO: false,
      diag: false
    };
    this.memDTO = {
      repair: 0,
      hardRepair: 0,
      clean: 0,
      hardClean: 0,
      to: 0,
      diag: 0
    };
  }

  selectForMembership(car: Car) {
    this.carForService = null;
    this.carForMembership = car;
    this.fetchedPrice = 0;
    this.memFetchedPrice = 0;
    this.serviceOptions = {
      repair: false,
      hardRepair: false,
      clean: false,
      hardCelan: false,
      TO: false,
      diag: false
    };
    this.memDTO = {
      repair: 0,
      hardRepair: 0,
      clean: 0,
      hardClean: 0,
      to: 0,
      diag: 0
    };
  }

  serviceButtonDisabled(): boolean {
    return this.fetchedPrice == 0;
  }

  ftechPriceForService() {
    let carId = this.carForService?.id;
    let options = this.getOptionsString();
    if (carId == null || options.length == 0) {
      this.fetchedPrice = 0;
      return;
    }
    this.http.get('http://localhost:8080/service/price?options=' + options + "&carId=" + carId, { observe: 'response' })
      .subscribe(response => {
        this.fetchedPrice = (response.body as PriceDTO).price
        console.log(this.fetchedPrice)
      })
  }

  getOptionsString(): string {
    let result = "";
    if (this.serviceOptions.clean) {
      result += "CLEAN";
    }
    if (this.serviceOptions.hardCelan) {
      if (result.length > 0) {
        result += ","
      }
      result += "HARD_CLEAN";
    }
    if (this.serviceOptions.repair) {
      if (result.length > 0) {
        result += ","
      }
      result += "REPAIR";
    }
    if (this.serviceOptions.hardRepair) {
      if (result.length > 0) {
        result += ","
      }
      result += "HARD_REPAIR";
    }
    if (this.serviceOptions.TO) {
      if (result.length > 0) {
        result += ","
      }
      result += "TO";
    }
    if (this.serviceOptions.diag) {
      if (result.length > 0) {
        result += ","
      }
      result += "DIAGNOSTIC";
    }
    return result;
  }

  createServiceRequest() {
    let userId = localStorage.getItem('userId');
    let carId = this.carForService?.id;
    let options = this.getOptionsString();
    if (userId == null || carId == null || options.length == 0) {
      return;
    }

    this.http.post('http://localhost:8080/service', {userId: userId, carId: carId, options: options}, { observe: 'response' })
    .subscribe(response => {
      let responseMessage: any = response.body;
      alert(responseMessage.message)
      
    });
  }

  fetchRequests() {
    let userId = localStorage.getItem('userId')
    this.http.get('http://localhost:8080/service/user/' + userId, { observe: 'response' })
      .subscribe(response => {
        this.serviceRequests = response.body as ServiceRequestWithCar[];
        console.log(this.serviceRequests)
      })
  }

  buttonStyleForRequest(request: ServiceRequest): string {
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

  humanReadableStatus(request: ServiceRequest): string {
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

  isStatusCancellable(request: ServiceRequest): boolean {
    return request.status == "CREATED";
  }

  cancelRequest(id: number) {
    this.isLoading = true
    this.http.put('http://localhost:8080/service/' + id + '?status=CANCELLED', {}, { observe: 'response' })
    .subscribe(response => {
      this.isLoading = false;
      this.fetchRequests();
    });
  }

  fetchMemPrice() {
    if (this.memDTO.clean != 0 || this.memDTO.diag > 0 || this.memDTO.hardClean > 0 || this.memDTO.repair > 0 || this.memDTO.hardRepair > 0 || this.memDTO.to > 0) {
      this.sendMemPriceReq();
    } else {
      this.memFetchedPrice = 0;
    }
  }

  sendMemPriceReq() {
    let carId = this.carForMembership?.id
    let options = this.buildOptionsForMem();
    if (carId == null || options.length == 0) {
      this.memFetchedPrice = 0;
      return;
    }
    this.http.get('http://localhost:8080/membership/price?options=' + options + "&carId=" + carId, { observe: 'response' })
    .subscribe(response => {
      this.memFetchedPrice = (response.body as PriceDTO).price
      console.log(this.memFetchedPrice)
    })
  }

  buildOptionsForMem():string {
    let res = "";
    if (this.memDTO.repair > 0) {
      res += "REPAIR," + this.memDTO.repair;
    }
    if (this.memDTO.hardRepair > 0) {
      if (res.length > 0) {
        res += ";"
      } 
      res += "HARD_REPAIR," + this.memDTO.hardRepair;
    }
    if (this.memDTO.clean > 0) {
      if (res.length > 0) {
        res += ";"
      } 
      res += "CLEAN," + this.memDTO.clean;
    }
    if (this.memDTO.hardClean > 0) {
      if (res.length > 0) {
        res += ";"
      } 
      res += "HARD_CLEAN," + this.memDTO.hardClean;
    }
    if (this.memDTO.to > 0) {
      if (res.length > 0) {
        res += ";"
      } 
      res += "TO," + this.memDTO.to;
    }
    if (this.memDTO.diag > 0) {
      if (res.length > 0) {
        res += ";"
      } 
      res += "DIAGNOSTIC," + this.memDTO.diag;
    }
    return res;
  }

  createMemRequest() {
    let userId = localStorage.getItem('userId');
    let carId = this.carForMembership?.id;
    let options = this.buildOptionsForMem();
    if (userId == null || carId == null || options.length == 0) {
      alert("went wrong")
      return;
    }
    this.http.post('http://localhost:8080/membership', {carId: carId, options: options}, { observe: 'response' })
    .subscribe(response => {
      let responseMessage: any = response.body;
      alert(responseMessage.message)
    });
  }

}

export interface PriceDTO {
  price: number;
}

export interface ServiceOptions {
  repair: boolean;
  hardRepair: boolean;
  clean: boolean;
  hardCelan: boolean;
  TO: boolean;
  diag: boolean;
}

export interface Car {
  id: number;
  model: string;
  brand: string;
  price: number;
  color: string;
  year: number;
  maxSpeed: number;
  userId: number;
  image: string;
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

export interface MembershipDTO {
  repair:number;
  hardRepair:number;
  clean:number;
  hardClean:number;
  to:number;
  diag:number;
}