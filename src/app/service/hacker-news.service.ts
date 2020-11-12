import { Injectable } from '@angular/core';
import { Item } from '../data/item';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HackerNewsService {
  itemList: Item[];

  constructor(private http: HttpClient) {}

  getItems() {
    return this.itemList;
  }

  getTopStories(storyType:string) {
    const base_url =
      'https://hacker-news.firebaseio.com/v0/'+storyType + '.json';

    return this.http.get<number[]>(base_url);
  }

  getStoryItem(id: number) {
    const base_url =
      'https://hacker-news.firebaseio.com/v0/item/' + id + '.json';
    return this.http.get<Item>(base_url);
  }
}
