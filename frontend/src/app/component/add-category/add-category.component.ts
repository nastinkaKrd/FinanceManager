import {Component} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {HeaderService} from "../../services/header.service";
import {FormsModule} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {RequestCategoryData} from "../../common/request-category-data";

@Component({
  selector: 'app-add-category',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {
  category: RequestCategoryData ={
    "title": "",
    "description": ""
  }

  constructor(private router: Router,
              private categoryService: CategoryService,
              private headerService: HeaderService) { }

  public addCategory(){
    this.categoryService.addCategory(this.category, this.headerService.getAuthHeader()).subscribe();
    this.router.navigate(['/api/categories']).then(window.location.reload);
  }

}
