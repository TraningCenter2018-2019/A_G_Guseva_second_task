package parser.deserializer.SAX;

import parser.deserializer.Creator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SAXParser extends Creator {
  public <T>T createObject(String path,  Class<?>cl){
    try {
      String str = new String(Files.readAllBytes(Paths.get(path)));
      MyObject myObject=new MyObject();
      myObject.setStr(str);
      Object obj=myObject.getObject(cl);
      if(myObject.exception){
        System.out.println("Wrong format");
        return null;
      }
      return (T)obj;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
