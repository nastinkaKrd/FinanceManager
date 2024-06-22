import {Routes} from '@angular/router';
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {TransactionComponent} from "./component/transaction/transaction.component";
import {CategoryComponent} from "./component/category/category.component";
import {AddCategoryComponent} from "./component/add-category/add-category.component";
import {UpdateCategoryComponent} from "./component/update-category/update-category.component";
import {AddTransactionComponent} from "./component/add-transaction/add-transaction.component";
import {UpdateTransactionComponent} from "./component/update-transaction/update-transaction.component";
import {ReportComponent} from "./component/report/report.component";
import {EmailConfirmComponent} from "./component/email-confirm/email-confirm.component";

export const routes: Routes = [
  {path: "api/auth/login", component: LoginComponent},
  {path: "api/auth/signup", component: RegisterComponent},
  {path: "api/transactions", component: TransactionComponent},
  {path: "api/categories", component: CategoryComponent},
  {path: "api/add-category", component: AddCategoryComponent},
  {path: "api/update-category", component: UpdateCategoryComponent},
  {path: "api/add-transaction", component: AddTransactionComponent},
  {path: "api/update-transaction", component: UpdateTransactionComponent},
  {path: "api/report", component: ReportComponent},
  {path: "api/auth/email-confirm", component: EmailConfirmComponent}
];
