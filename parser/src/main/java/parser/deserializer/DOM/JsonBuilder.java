package parser.deserializer.DOM;

import parser.deserializer.JsonToken;
import parser.deserializer.NodeType;

import java.lang.reflect.Array;

public class JsonBuilder {
  public boolean exception;

  public String createTree(String str, Node node) {
    if (str.charAt(0) == '"') {
      str=treatString(str,node);
    }

    else if (str.charAt(0) == '[') {
      str=treatArray(str,node);
    }
    else if(str.charAt(0) == '{'){
      str = treatObject(str, node);
    }
    else {
      str=treatSimple(str,node);
    }
    return str;
  }

  private String treatSimple(String str,Node node){
    JsonToken jsonToken = new JsonToken();
    String token;
    token = jsonToken.getToken(str);
    str = str.substring(token.length());
    if (jsonToken.exception || (str.charAt(0) != '}' && str.charAt(0) != ','&& str.charAt(0) != ']')) {
      exception = true;
      return str;
    }
    node.setValue(NodeType.getType(token), token);
    return str;
  }

  private String treatObject(String str, Node node){
    str=str.substring(1);
    while (str.length()!=0&&str.charAt(0) != '}'&&!exception) {
      Node item=node.addItems();
      str=setName(str,item);
      if (exception){
        return str;
      }
      str=createTree(str,item);

      if (str.length()!=0&&str.charAt(0) == ','){
        str=str.substring(1);
      }
    }
    if(str.length()==0||node.exception){
      exception=true;
      return str;
    }
    node.setValue(Object.class,"");
    str=str.substring(1);
    return str;
  }

  private String setName(String str,Node node){
    if (str.charAt(0) != '"') {
      exception = true;
      return str;
    }
    str = str.substring(1);
    JsonToken jsonToken=new JsonToken();
    String token = jsonToken.getToken(str);
    if (jsonToken.exception) {
      exception = true;
      return str;
    }
    node.setName(token);
    str = str.substring(token.length()+1);
    if (str.charAt(0) != ':') {
      exception = true;
      return str;
    }
    return str.substring(1);
  }

  private String treatString(String str,Node node){
      str = str.substring(1);
      JsonToken jsonToken=new JsonToken();
      String token = jsonToken.getToken(str);
      str = str.substring(token.length());
      if (jsonToken.exception || str.charAt(0) != '"') {
        exception = true;
        return str;
      }
      str = str.substring(1);
      node.setValue(String.class,token);
      return str;
  }


  private String treatArray(String str,Node node){
    JsonToken jsonToken=new JsonToken();
    String token;
    str = str.substring(1);

    Node item;
    while (str.charAt(0) != ']') {
      item = node.addItems();
      str = createTree(str, item);
      if (item.exception) {
        exception = true;
        return str;
      }
      if (str.charAt(0) == ',') {
        str = str.substring(1);
      }
    }
    str = str.substring(1);
    node.setValue(Array.class,"");
    return str;
  }

}