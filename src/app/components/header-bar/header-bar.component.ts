import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-bar',
  templateUrl: './header-bar.component.html',
  styleUrls: ['./header-bar.component.css']
})
export class HeaderBarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onTopStoryClick() {
    this.router.navigate(['/stories','topstories']);
  }
  onBestStoryClick() {
    this.router.navigate(['/stories','beststories']);
  }
  onLatestStoryClick() {
    this.router.navigate(['/stories','lateststories']);
  }
}
