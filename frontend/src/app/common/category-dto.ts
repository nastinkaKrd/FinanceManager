import {User} from "./user";

export class CategoryDto {
  public id: number;
  public title: string;
  public description: string;
  public user: User;
  constructor(id: number, title: string, description: string, user: User) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.user = user;
  }

}
