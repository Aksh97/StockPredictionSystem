import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { RouterLink } from '@angular/router';
import {CommService} from '../comm.service'


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
abc
  constructor(private router:Router, private commservice:CommService) { 
   
  }

  ngOnInit() {
  }

  submit(event){
    let form_data = document.getElementById('Form_body');
    let signupJSON:JSON ;
    signupJSON['name'] = event.target.name.value;
    signupJSON['email']= event.target.email.value
    signupJSON['age']=event.target.age.value
    signupJSON['gender']=event.target.gender.value
    signupJSON['inv_info']=event.target.inv_info.value
    signupJSON['TypeofStocks']=event.target.TypeofStocks.value

    this.commservice.singup(signupJSON)
    
   
   console.log(event.target);
  }

}



