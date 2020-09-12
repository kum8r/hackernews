import 'package:hackernews/service/item.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:hackernews/utils/stories.dart';

class HackerNewsApi {
  String _rootUrlPath = "https://hacker-news.firebaseio.com/v0/";

  HackerNewsApi();

  Future<List<Item>> getComments(Item item) async {
    if (item.kids.length == 0) {
      return List();
    } else {
      var comments =
          await Future.wait(item.kids.map((id) => getItem(id.toString())));
      var nestedComments =
          await Future.wait(comments.map((comment) => getComments(comment)));
      for (var i = 0; i < nestedComments.length; i++) {
        comments[i].comments = nestedComments[i];
      }
      return comments;
    }
  }

  Future<Item> getItem(String id) async {
    var itemPath = "$_rootUrlPath/item/$id.json";
    http.Response response = await http.get(itemPath);

    return Item.fromJson(json.decode(response.body));
  }

  Future<List<String>> getFeed(Stories story) async {
    var path = '';
    path = getStringStoryValue(story);
    // switch (story) {
    //   case Stories.top:
    //     path = 'topstories';
    //     break;

    //   case Stories.newest:
    //     path = 'newstories';
    //     break;

    //   case Stories.jobs:
    //     path = 'jobstories';
    //     break;

    //   case Stories.shows:
    //     path = 'showstories';
    //     break;

    //   case Stories.asks:
    //     path = "askstories";
    // }
    var fullPath = "$_rootUrlPath$path.json";
    http.Response response = await http.get(fullPath);
    return response.body.split(",");
  }
}
