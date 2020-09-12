import 'package:flutter/material.dart';
import 'package:hackernews/screens/story_page.dart';
import 'package:hackernews/utils/stories.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(fontFamily: 'OpenSans'),
      home: StoryPage(Stories.top),
      debugShowCheckedModeBanner: false,
    );
  }
}
