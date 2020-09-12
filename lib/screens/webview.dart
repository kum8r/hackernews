import 'package:flutter/material.dart';
import 'package:hackernews/service/item.dart';
import 'package:webview_flutter/webview_flutter.dart';

class WebPage extends StatefulWidget {
  final Item _item;
  WebPage(this._item);
  @override
  _WebPageState createState() => _WebPageState();
}

class _WebPageState extends State<WebPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: mainWidget(),
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
        webView(),
      ],
    );
  }

  Widget webView() {
    return Expanded(
      child: WebView(
        initialUrl: widget._item.url,
        javascriptMode: JavascriptMode.unrestricted,
      ),
    );
  }
}
