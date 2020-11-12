import {  Component,  OnDestroy,  OnInit,} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TreeviewItem } from 'ngx-treeview';
import { Item } from 'src/app/data/item';
import { HackerNewsService } from 'src/app/service/hacker-news.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit, OnDestroy {
  id: number;
  private sub: any;
  commentsItem = [];
  items = [];
     public field:Object ={ dataSource: this.commentsItem, id: 'id', text: 'name', child: 'subChild' };
 

  constructor(
    private route: ActivatedRoute,
    private service: HackerNewsService
  ) {
    this.sub = this.route.params.subscribe((params) => {
      this.id = +params['id'];
    });

    this.commentsItem = this.getComments(this.id, this.commentsItem);
  }

  getComments(commentId: number, list: Object[]) {
    this.service.getStoryItem(commentId).subscribe((data) => {
      var item: Item = data;
      var children = [];
      if (data.kids != undefined && data.kids.length > 0) {
        data.kids.forEach((kidId) => {
          if (kidId != undefined) this.getComments(kidId, children);
        });
      }
      if (data.text != undefined) {
        console.log(data.text);
        list.push(  
          {
            id: '0',
            text: data.text,
            subChild: children,
          }
        );
      }
    });
    return list;
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
