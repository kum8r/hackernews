import 'package:html/parser.dart';

class Item {
  final String by;
  final int descendants;
  final int id;
  final String title;
  final DateTime time;
  final String type;
  final String url;
  final String text;
  final bool deleted;
  final int score;
  final List<dynamic> kids;
  List<Item> comments = List();

  Item(
      {this.by,
      this.descendants,
      this.id,
      this.title,
      this.time,
      this.type,
      this.url,
      this.text,
      this.deleted,
      this.kids,
      this.score});

  factory Item.fromJson(Map<String, dynamic> json) {
    if (json['id'] == null) return null;
    var text = json['text'] != null ? parse(json['text']).body.text : "";
    var kids = json['kids'] != null ? json['kids'] : List();
    var descendants = json['descendants'] != null ? json['descendants'] : 0;
    var time = DateTime.fromMillisecondsSinceEpoch(json['time'] * 1000);
    return Item(
        id: json['id'],
        title: json['title'],
        descendants: descendants,
        by: json['by'],
        time: time,
        type: json['type'],
        url: json['url'],
        text: text,
        deleted: json['deleted'],
        kids: kids,
        score: json['score']);
  }
}
