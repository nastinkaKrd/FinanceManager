export class RegisterRequest {
  public username: string;
  public email: string;
  public password: string;

  constructor(username: string, password: string, email: string) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
