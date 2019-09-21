import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { RouterLink } from '@angular/router';
import { CommService } from '../comm.service'
import { Trend } from '../dataClass/trend'
import { Company } from '../dataClass/company'
import { CookieService } from 'ngx-cookie-service';
import{FormsModule } from '@angular/forms'
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  orgName
  dates
  prediction
  companyDetails
  comdjson
  compName
  trend
  confidence
  error="select the company name"
  trendImage="../assets/default_new.jpg"
x
  orgSymbol
  constructor(private router: Router, private commservice: CommService, private cookie: CookieService) { }

  ngOnInit() {
    
    this.loadData()
    this.x = document.getElementById('error')
    this.x.style.visibility='hidden'

  }

  public loadData() {

    this.comdjson = localStorage.getItem("companyName")//decodeURIComponent(this.cookie.get("companyName"))
    this.companyDetails = JSON.parse(this.comdjson)

  }

  public submit() {
    if(this.compName==null){
      this.x.style.visibility='visible'

    }
    else{
      this.x.style.visibility='hidden'
  this.orgName = this.compName
    
    for (var item in this.companyDetails) {
      let test = this.companyDetails[item]
      if (this.companyDetails[item]["fullName"] == this.orgName) {
        this.orgSymbol = this.companyDetails[item]["symbol"]
      }
    }
  }
    
    if(this.orgSymbol!=null)
    {
    this.commservice.getPrediction(this.orgSymbol).subscribe(
      data => {
        // this.prediction.push(data);
        data.ack
        console.log("b4");
        this.addRecordToPredictionList(data);
        console.log("after");
        //this.graph(this.orgSymbol)
      }
    );
    }
  }

  
  public addRecordToPredictionList(record) {
    this.prediction=record;
    if(this.prediction["trend"]==null)
    {
      this.trend=0
    }else{
      this.trend=this.prediction["trend"]
    }
    
    this.confidence=Math.round( this.prediction["confidence"] *100)/100

    if(this.trend=='up'){
      this.trendImage="../assets/uptrend_new.jpg"
    }
    else
    this.trendImage="../assets/downtrend_new.jpg"

  }





  public dataHandler(data)
  {

    let first=data[0]
  }

}
