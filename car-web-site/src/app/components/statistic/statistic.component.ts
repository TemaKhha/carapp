import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexTitleSubtitle,
  ApexNonAxisChartSeries,
  ApexResponsive,
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  title: ApexTitleSubtitle;
};

export type PieChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

  @ViewChild("chart", { static: false }) chart?: ChartComponent;
  public chartOptions: Partial<ChartOptions>;
  public pieOptions: Partial<PieChartOptions>;

  static?: Statistic;

  constructor(private http: HttpClient) {
    this.chartOptions = {
      series: [
        {
          name: "My-series",
          data: [10, 41, 35, 51, 49, 62, 69]
        }
      ],
      chart: {
        height: 350,
        type: "bar"
      },
      title: {
        text: "Статистика запросов"
      },
      xaxis: {
        categories: ["Успешные", "Отклоненные",  "Отмененные пользователем",  "Всего",  "На покупку",  "На обслуживание",  "Программы обслуживание"]
      }
    };
    this.pieOptions = {
      series: [44, 55],
      chart: {
        width: 380,
        type: "pie"
      },
      labels: ["Покупка", "Сервис",],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: "bottom"
            }
          }
        }
      ]
    };
  }

  ngOnInit(): void {
    this.fetchStat()
  }

  fetchStat() {
    this.http.get('http://localhost:8080/stat', { observe: 'response' })
    .subscribe(response => {
      this.static = (response.body as Statistic)
      console.log(this.static)
      this.updateLineCharts()
      this.updatePie1();
    })
  }

  public updateLineCharts() {
    let total = this.static!.buy.done + this.static!.buy.rejected + this.static!.buy.cancelled + this.static!.service.done + this.static!.service.rejected + this.static!.service.cancelled
    this.chartOptions.series = [{
      data: [this.static!.buy.done + this.static!.service.done, this.static!.buy.rejected + this.static!.service.rejected, this.static!.buy.cancelled + this.static!.service.cancelled, total, this.static!.buy.done + this.static!.buy.rejected + this.static!.buy.cancelled , this.static!.service.total, this.static!.memberships]
    }];
  }

  public updatePie1() {
    this.pieOptions.series = [this.static!.buy.profit/100, this.static!.service.profit/100];
  }


}

export interface Statistic {
    buy: StatInfo;
    requests: StatInfo;
    service: StatInfo;
    memberships: number;
}

export interface StatInfo {
    done: number;
    cancelled: number;
    rejected: number;
    profit: number;
    total: number;
}