enum Stories { top, newest, jobs, shows, asks }

List<String> StoryList = [
  "topstories",
  "newstories",
  "jobstories",
  "showstories",
  "askstories"
];

Stories getEnumStory(String value) {
  return Stories.values[StoryList.indexOf(value)];
}

String getStringStoryValue(Stories value) {
  return StoryList[value.index];
}
