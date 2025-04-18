import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { RegisterDTO } from '../dtos/register.dto';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dtos/login.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  constructor(
    private http: HttpClient,
    private httpUtilService: HttpUtilService
  ) { }

  private getApiConfig() {
    return {
      headers: this.httpUtilService.createHeaders(),
    };
  }


  register(registerDTO: RegisterDTO): Observable<any>{
    console.log(this.apiRegister);
    return this.http.post(this.apiRegister, registerDTO, this.getApiConfig());
  }
  
  login(loginDTO: LoginDTO): Observable<any> {    
    return this.http.post(this.apiLogin, loginDTO, this.getApiConfig());
  }

  saveUserData(userData: any): void {
    if (!userData || !userData.id) {
      console.error("Lỗi: Dữ liệu đăng nhập không hợp lệ!", userData);
      return;
    }
    localStorage.setItem('user', JSON.stringify(userData));
  }
  

  getUserId(): number | null {
    try {
      const userData = localStorage.getItem('user');
      if (userData) {
        const parsedData = JSON.parse(userData);
        return parsedData.id ? parsedData.id : null;
      }
    } catch (error) {
      console.error("Lỗi khi lấy userId từ localStorage:", error);
    }
    return null;
  }
  
  getStudentById(id: number): Observable<any> {
    return this.http.get(`${environment.apiBaseUrl}/users/student/${id}`);
  }
}
