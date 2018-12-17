package parser.deserializer.DOM;

import parser.deserializer.Creator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DOMParser extends Creator {

  public <T>T createObject(String path,  Class<?>cl){
    Paths.get(path);
    CreatedObjectInfo obj=new CreatedObjectInfo();
    try {
      String str = new String(Files.readAllBytes(Paths.get(path)));
      JsonBuilder jsonBuilder =new JsonBuilder();
      Node rootNode = new Node();
      str=jsonBuilder.createTree(str, rootNode);
      if (jsonBuilder.exception||str.length()!=0) {
        System.out.println("Wrong format");
        obj.exception=true;
        return null;
      }
      Object o=obj.getObject(rootNode,cl);
      if(obj.exception){
        System.out.println("Not for this object");
        return null;
      }
      return (T)o;
    } catch (IOException e) {
      obj.exception=true;
      e.printStackTrace();
    }
    return null;
  }
}
