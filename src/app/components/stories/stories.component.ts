import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HackerNewsService } from 'src/app/service/hacker-news.service';

@Component({
  selector: 'app-stories',
  templateUrl: './stories.component.html',
  styleUrls: ['./stories.component.css'],
})
export class StoriesComponent implements OnInit {
  title = 'hackernews';
  allIdList: number[] = [];
  idList: number[] = [];
  showprogressBar = true;
  private sub: any;
  private storyType:string;

  constructor(private newsService: HackerNewsService,
    private route: ActivatedRoute,
    ) {
      this.sub = this.route.params.subscribe((params) => {
        this.storyType = params['story'];
      });
    
  
      if (this.storyType === "topstories") {
        this.getTopStoryID();
      }
      else if (this.storyType === "beststories") {
          this.getBestStoryID();
      }
      else if (this.storyType === "lateststories") {
        this.getLatestStoryID();
      }
      else {
        this.getTopStoryID();
      }
  }

  ngOnInit(): void {}

  getTopStoryID() {
    this.newsService.getTopStories("topstories").subscribe((data) => {
      this.allIdList = data;
      this.showprogressBar = false;
      this.idList = [];
      this.addList();
    });
  }

  getBestStoryID() {
    this.newsService.getTopStories("beststories").subscribe((data) => {
      this.allIdList = data;
      this.showprogressBar = false;
      this.idList = [];
      this.addList();
    });
  }

  getLatestStoryID() {
    this.newsService.getTopStories("newstories").subscribe((data) => {
      this.allIdList = data;
      this.showprogressBar = false;
      this.idList = [];
      this.addList();
    });
  }

  addList() {
    if (this.allIdList.length >= 20) {
      var size = 20;
      this.idList.push(
        ...this.allIdList.slice(this.idList.length, this.idList.length + size)
      );
      console.log(this.idList.length);
    } else {
      this.idList.push(...this.allIdList.values());
    }
    console.log(this.idList.length);
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
      this.addList();
    }
  }
}
