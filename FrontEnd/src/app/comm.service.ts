import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { CompileShallowModuleMetadata } from '@angular/compiler';
import { ErrorClass } from './dataClass/errorClass';
import { Company } from './dataClass/company';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
  providedIn: 'root'
})
export class CommService {
  id
  feedback
  error
  companyDetails
  abc: any
  constructor(private httpClient: HttpClient, private cookie: CookieService) { }

  public save(username, password):Observable<any> {

    let body = new HttpParams()
      .set('userName', username)
      .set('password', password);

   return this.httpClient.post("/api/login", body.toString(), {

      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        "Authorization": "Basic " + btoa("client-id:client-secret")
      })
    }

    )
    /*.subscribe(data => {

      this.feedback = data["ack"]
      if (this.feedback == 'success') {
        this.companyDetails = data["symbol"]
        localStorage.setItem("companyName", JSON.stringify(this.companyDetails))
        //this.cookie.set("companyName",JSON.stringify(this.companyDetails))
      }
      else {
        this.error = data["cause"]
      }




    })
    if (this.feedback == 'success') {
      return this.feedback

    }
    else {
      return this.error
    }*/



  }

  public singup(json_data: JSON) {

  }



  public getPrediction(input): Observable<any> {
    let inp = {};
    inp["symbol"] = input
    let inp1 = {};
    inp1['data'] = inp;
    //let param= new HttpParams().set(inp);

    return this.httpClient.post('/api/stock/predict', inp1)


  }

  public getHistory(input): Observable<any> {
    let headers = new HttpHeaders();
headers.append('Content-Type', 'application/json');
    let inp = {};
    inp["symbol"] = input
    let inp1 = {};
    inp1['data'] = inp;
    let param= new HttpParams().set("data",inp.toString());

    return this.httpClient.get('/api/stock/history', { headers: headers,params:param})


  }


}
