import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { RouterLink } from '@angular/router';
import { CommService } from '../comm.service'
import { ResourceLoader } from '@angular/compiler';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  valid
  userName
  password
  feedback
  companyDetails
  error=""
  x
  constructor(private router: Router, private commservice: CommService, private cookie: CookieService) {

  }

  ngOnInit() {
     this.x = document.getElementById('error')
    this.x.style.visibility='hidden'
  }



  public Auth() {
     this.error=""
     this.x.style.visibility='hidden'

    event.preventDefault()


    this.valid = this.commservice.save(this.userName, this.password).subscribe(data => {

      this.feedback = data["ack"]
      if (this.feedback == 'success') {
        this.companyDetails = data["symbol"]
        localStorage.setItem("companyName", JSON.stringify(this.companyDetails))
        //this.cookie.set("companyName",JSON.stringify(this.companyDetails))
        this.router.navigateByUrl("/home")
      }
      else {
        this.error = data["cause"]
        this.x.style.visibility='visible'
      }
    })
        
  }

  }
