import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {RequestCategoryData} from "../../common/request-category-data";
import {CategoryService} from "../../services/category.service";
import {HeaderService} from "../../services/header.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-update-category',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './update-category.component.html',
  styleUrl: './update-category.component.css'
})
export class UpdateCategoryComponent {
  category: RequestCategoryData ={
    "title": "",
    "description": ""
  }
  id: number = 0;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private categoryService: CategoryService,
              private headerService: HeaderService) { }

  public changeCategory(){
    this.categoryService.changeCategory(this.category, this.id, this.headerService.getAuthHeader()).subscribe();
    this.router.navigate(['/api/categories']).then(window.location.reload);
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.category.title = params['title'];
      this.category.description = params['description'];
      this.id = params['id'];
    });
  }
}
