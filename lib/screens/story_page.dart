import 'package:auto_size_text/auto_size_text.dart';
import 'package:badges/badges.dart';
import 'package:flutter/material.dart';
import 'package:hackernews/screens/tabbar.dart';
import 'package:hackernews/service/item.dart';
import 'package:hackernews/service/hackernews_api.dart';
import 'package:hackernews/utils/stories.dart';

class StoryPage extends StatefulWidget {
  final Stories stories;

  StoryPage(this.stories);

  @override
  _StoryPageState createState() => _StoryPageState();
}

class _StoryPageState extends State<StoryPage> {
  HackerNewsApi _hackerNewsRepository = new HackerNewsApi();
  List<String> storyId = [];
  Stories currentStory;

  loadFeed() async {
    var temp = await _hackerNewsRepository.getFeed(currentStory);
    setState(() {
      storyId = temp;
    });
  }

  @override
  void initState() {
    currentStory = widget.stories;
    loadFeed();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Hacker News"),
        backgroundColor: Color(0xFFdb5d3d),
        actions: [storyComboBox()],
      ),
      body: Container(
          child: ListView.builder(
              itemCount: storyId.length,
              itemBuilder: (context, index) {
                return FutureBuilder(
                  future: _hackerNewsRepository.getItem(storyId[index]),
                  builder: (builder, snapshot) {
                    if (snapshot.data == null) {
                      return Container(
                        child: Card(
                            child: Center(child: CircularProgressIndicator())),
                      );
                    }
                    var item = snapshot.data;
                    if (item == null) return Container();
                    return listTile(item);
                  },
                );
              })),
    );
  }

  Card listTile(Item item) {
    return Card(
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: GestureDetector(
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => TabView(item, 0),
                      ),
                    );
                  },
                  child: Container(
                    child: Column(
                      textDirection: TextDirection.ltr,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          item.title,
                        ),
                        _getUrl(item.url),
                      ],
                    ),
                  ),
                ),
              ),
              commentIcon(item),
            ],
          ),
          Row(
            children: [
              _getBadge(item.score),
              authorWidget(item),
              _getTime(item.time),
            ],
          ),
        ],
      ),
    );
  }

  Widget _getUrl(String _url) {
    if (_url != null) {
      Uri uri;
      uri = Uri.parse(_url);
      return Text(
        uri.origin,
        style: TextStyle(fontWeight: FontWeight.w300),
      );
    }
    return Container();
  }

  Container commentIcon(Item item) {
    String badgeContent;
    if (item.comments == null) {
      badgeContent = "0";
    } else
      badgeContent = item.kids.length.toString();
    return Container(
        margin: EdgeInsets.only(right: 20),
        child: Badge(
          badgeContent: Text(badgeContent),
          child: IconButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => TabView(item, 1),
                ),
              );
            },
            icon: ImageIcon(
              AssetImage("images/notification.png"),
              color: Color(0xFFdb5d3d),
            ),
          ),
        ));
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

  Widget authorWidget(Item item) {
    return AutoSizeText(
      "   By ${item.by}",
      style: TextStyle(fontWeight: FontWeight.bold),
    );
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
    return AutoSizeText(
      timStr,
      maxLines: 2,
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

  storyComboBox() {
    return Container(
      child: Theme(
        data: Theme.of(context).copyWith(
          canvasColor: Color(0xFFdb5d3d),
        ),
        child: DropdownButton(
          value: getStringStoryValue(currentStory),
          items: StoryList.map<DropdownMenuItem<String>>((String value) {
            return DropdownMenuItem(
              child: Text(
                value,
                style: TextStyle(color: Colors.white),
              ),
              value: value,
            );
          }).toList(),
          onChanged: (String value) {
            setState(() {
              currentStory = getEnumStory(value);
              loadFeed();
            });
          },
        ),
      ),
    );
  }
}
