import { Route } from '@angular/compiler/src/core';
import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Item } from 'src/app/data/item';
import { HackerNewsService } from 'src/app/service/hacker-news.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css'],
})
export class ItemComponent implements OnChanges {
  item: Item;
  time: string = '';
  @Input() id: number;

  constructor(private service: HackerNewsService, private router: Router) {
    this.item = new Item('', '', '', 1, 0, [], 0, '');
  }

  getItem() {
    this.service.getStoryItem(this.id).subscribe((data) => {
      this.item = data;
      var date = new Date(data.time * 1000);
      console.log(this.item.time);
      console.log(date.toLocaleDateString('en-Us'));
      this.time = date.toLocaleDateString();
    });
  }

  ngOnChanges(): void {
    this.getItem();
  }

  onClick() {
    if (this.item.url !== null || this.item.url !== '') {
      window.open(this.item.url);
    }
  }

  onCommentClick() {
    this.router.navigate(['/comments', this.id]);
  }
}
