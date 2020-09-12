import 'package:badges/badges.dart';
import 'package:flutter/material.dart';
import 'package:hackernews/screens/comment_page.dart';
import 'package:hackernews/screens/webview.dart';
import 'package:hackernews/service/item.dart';

class TabView extends StatefulWidget {
  final Item _item;
  final int _selectedIndex;
  TabView(this._item, this._selectedIndex);

  @override
  _TabViewState createState() => _TabViewState();
}

class _TabViewState extends State<TabView> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DefaultTabController(
        initialIndex: widget._selectedIndex,
        length: 2,
        child: Scaffold(
          appBar: AppBar(
            backgroundColor: Color(0xFFdb5d3d),
            title: Text("Hacker News"),
          ),
          body: mainWidget(),
        ),
      ),
    );
  }

  Widget mainWidget() {
    if (widget._item == null) {
      return Container(
        child: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }
    return Column(
      children: [
        headerWidgets(),
        SizedBox(
          child: Divider(
            thickness: 0.30,
            color: Colors.black,
          ),
        ),
        SizedBox(
          height: 50,
          child: AppBar(
            backgroundColor: Color(0xFFdb5d3d),
            bottom: TabBar(
              tabs: [
                Tab(
                  text: "WebView",
                ),
                Tab(
                  text: "Comments",
                ),
              ],
            ),
          ),
        ),
        Expanded(
          child: TabBarView(
            children: [
              Container(
                child: WebPage(widget._item),
              ),
              Container(
                child: CommentsView(widget._item),
              ),
              // CommentsPage(_item),
              // WebPage(_item),
            ],
          ),
        )
      ],
    );
  }

  Widget headerWidgets() {
    Uri _uri = Uri.parse(widget._item.url);
    return Container(
      color: Color(0xfffcfcfc),
      child: Column(
        textDirection: TextDirection.ltr,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            widget._item.title,
            style: TextStyle(fontSize: 20),
          ),
          Text(_uri.origin),
          Row(
            children: [
              _getBadge(widget._item.score),
              Text("  By ${widget._item.by}"),
              _getTime(widget._item.time),
            ],
          )
        ],
      ),
    );
  }

  Widget _getBadge(int score) {
    if (score == null) return Container();

    if (score.toString().length > 1) {
      return Badge(
        shape: BadgeShape.square,
        borderRadius: 20,
        badgeContent: Text(
          score.toString(),
          style: TextStyle(color: Colors.white),
        ),
      );
    } else {
      return Badge(
        shape: BadgeShape.circle,
        badgeContent: Text(
          score.toString(),
          style: TextStyle(color: Colors.white),
        ),
      );
    }
  }

  Widget _getTime(DateTime time) {
    int hours = time.difference(DateTime.now()).inHours.abs();
    String timStr = "";

    if (hours > 24) {
      timStr = " on " + getDay(time.weekday);
    } else {
      if (hours == 0) {
        int minutes = time.difference(DateTime.now()).inMinutes.abs();
        if (minutes == 0) {
          int seconds = time.difference(DateTime.now()).inSeconds.abs();
          timStr = "  " + seconds.toString() + " min ago";
        } else
          timStr = "  " + minutes.toString() + " min ago";
      } else
        timStr = "  " + hours.toString() + " Hrs ago";
    }

    if (hours > 168) {
      timStr = " on " + time.toString().split(" ")[0];
    }
    return Text(
      timStr,
    );
  }

  String getDay(int day) {
    List<String> days = [
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday"
    ];
    return days[day - 1];
  }
}
