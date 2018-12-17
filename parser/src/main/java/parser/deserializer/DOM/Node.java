package parser.deserializer.DOM;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Node {
  public boolean exception=false;
  String name;
  Object value;
  Type type;

  ArrayList<Node>items;

  public Node() {
    items=null;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setValue(Class<?>cl,String v) {
    if(v.length()!=0) {
      try {
        type=cl;
        Constructor<?> cons = cl.getConstructor(String.class);
        value = cons.newInstance(v);
      } catch (Exception e) {
        exception = true;
        e.printStackTrace();
      }
    }
    else{
      type=cl;
      value=null;
    }
  }


  public Node addItems(){
    addItem();
    return items.get(items.size()-1);
  }

  private void addItem(){
    if(items==null)
      items=new ArrayList<Node>();
    Node itemNode=new Node();
    items.add(itemNode);
  }

}
