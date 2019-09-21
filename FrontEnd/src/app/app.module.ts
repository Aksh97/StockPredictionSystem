import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router'
import{FormsModule } from '@angular/forms'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import{HttpClientModule} from '@angular/common/http';
import{SignupComponent} from './login-page/signup.component'
import { CookieService } from 'ngx-cookie-service';


const appRoutes : Routes =[
  {
    path:'',
    component:LoginPageComponent
  },
  {
    path:'home',
    component:HomePageComponent
  },
  {
    path: 'Signup',
    component: SignupComponent

  }

];
@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    HomePageComponent,
    SignupComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    FormsModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
