import 'package:flutter/material.dart';
import 'package:hackernews/service/item.dart';
import 'package:hackernews/service/hackernews_api.dart';

class CommentsView extends StatefulWidget {
  final Item item;
  CommentsView(this.item);

  @override
  _CommentsViewState createState() => _CommentsViewState();
}

class _CommentsViewState extends State<CommentsView> {
  HackerNewsApi _hackerNewsRepository = new HackerNewsApi();

  Future<Item> getItemWithComments(String id) async {
    var item = await _hackerNewsRepository.getItem(id);
    item.comments = await _hackerNewsRepository.getComments(item);
    return item;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: ListView.builder(
          itemCount: widget.item.kids.length,
          itemBuilder: (context, index) {
            return FutureBuilder(
              future: getItemWithComments(widget.item.kids[index].toString()),
              builder: (builder, snapshot) {
                if (snapshot.data == null) {
                  return Container(
                    child: Center(
                      child: CircularProgressIndicator(),
                    ),
                  );
                }
                Item item = snapshot.data;
                if (item == null) return Container();
                List<Widget> nestedComments = [];
                _getNestedComments(snapshot.data, nestedComments, 0);
                return Column(
                  children: List.generate(
                    nestedComments.length,
                    (index) => nestedComments[index],
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }

  void _getNestedComments(Item items, List widgets, int level) async {
    if (items == null) {
      widgets.add(Container());
      return;
    }
    widgets.add(
      Card(
        child: Container(
            padding: EdgeInsets.only(left: 10.0 * level),
            child: Text(items.text)),
      ),
    );
    print(level);
    level++;

    for (var i = 0; i < items.kids.length; i++) {
      Item item = await getItemWithComments(items.kids[i].toString());
      _getNestedComments(item, widgets, level);
    }
  }
}
