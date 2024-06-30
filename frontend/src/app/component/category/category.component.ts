import {Component} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {CategoryDto} from "../../common/category-dto";
import {HeaderService} from "../../services/header.service";
import {Location, NgForOf, NgIf} from "@angular/common";
import {Route} from "@angular/router";

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
  categories: CategoryDto[] = [];
  containsUserCategory: boolean = false;

  constructor(private categoryService: CategoryService,
              private headerService: HeaderService,
              private location: Location) { }

  public getCategories(){
    this.categoryService.getCategories(this.headerService.getAuthHeader())
      .subscribe((data: CategoryDto[]) => {
        data.forEach(category =>{
          if (category.user !== null){
            this.containsUserCategory = true;
            return;
          }
        })
        this.categories = data;
      });
  }

  ngOnInit() {
    this.getCategories();
  }

  deleteCategory(id: number) {
    this.categoryService.deleteCategory(id, this.headerService.getAuthHeader()).subscribe();
    window.location.reload();
  }
}
