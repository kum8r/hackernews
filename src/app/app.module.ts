import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { ItemComponent } from './components/item/item.component';
import { StoriesComponent } from './components/stories/stories.component';
import { HttpClientModule } from '@angular/common/http';
import { DateAgoPipe } from './pipes/date-ago.pipe';
import { CommentComponent } from './components/comment/comment.component';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';
import { TreeviewModule } from 'ngx-treeview';

@NgModule({
  declarations: [
    AppComponent,
    HeaderBarComponent,
    ItemComponent,
    StoriesComponent,
    DateAgoPipe,
    CommentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    TreeViewModule, TreeviewModule.forRoot()
  ],
  exports: [],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
