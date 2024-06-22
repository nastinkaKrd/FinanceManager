export class User {
  public id: number;
  public username: string;
  public email: string;
  public password: string;
  public isEmailVerified: boolean;
  public userRoles: string;

  constructor(id: number, username: string, password: string, email: string, isEmailVerified: boolean, userRoles: string) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.isEmailVerified = isEmailVerified;
    this.userRoles = userRoles;
  }
}
