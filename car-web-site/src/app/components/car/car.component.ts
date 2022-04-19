import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit {

  model = "Bob";
  name:string;
  speed:number;
  colors:Colors;
  options:string[];

  constructor() { 
    this.name = "Audi"
    this.speed = 220;
    this.colors = {
      car: 'white',
      salon: 'black',
      wheels: 'silver'
    }
    this.options = ['ABS', 'Heating', 'Auto-pilot']
  }

  ngOnInit(): void {
   this.model = "Cock";
  }

  bmwSelected() {
    this.name = "BMW"
    this.speed = 228;
    this.colors = {
      car: 'green',
      salon: 'black',
      wheels: 'silver'
    }
    this.options = ['Bimer', 'Heating', 'Auto-pilot']
    this.model = "M5";
  }

  audiSelected() {
    this.name = "Audi"
    this.speed = 220;
    this.colors = {
      car: 'white',
      salon: 'black',
      wheels: 'silver'
    }
    this.options = ['ABS', 'Heating', 'Auto-pilot']
  }

  mercSelected() {
    this.name = "Meren"
    this.speed = 220;
    this.colors = {
      car: 'white',
      salon: 'black',
      wheels: 'silver'
    }
    this.options = ['ABS', 'Heating', 'Auto-pilot']
    this.model = "223";
  }

  addOptions(option:string) {
    this.options.unshift(option)
    return false;
  }

  editModel(model:string) {
    this.model = model
    return false
  }

}

interface Colors {
  car:string;
  salon:string;
  wheels:string;
}