import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommentComponent } from './components/comment/comment.component';
import { StoriesComponent } from './components/stories/stories.component';

const routes: Routes = [
  { path: '', component: StoriesComponent },
  {path: 'stories/:story', component:StoriesComponent},
  { path: 'comments/:id', component: CommentComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
