package parser.deserializer.SAX;

import parser.deserializer.JsonToken;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MyObject {
  public boolean exception;
  private String str;

  public void setStr(String str) {
    this.str = str;
  }

  public Object getObject(Class<?> cl){
    Object obj;
    if(str.length()==0){
      return null;
    }
    if (str.charAt(0)=='{'){
      obj=getNotSimple(cl);
      return obj;
    }
    if(str.charAt(0)=='['){
      obj=getArray(cl);
      return obj;
    }
    obj=getSimple(cl);
    return obj;
  }

  private Object getArray(Class<?> cl) {
    str=str.substring(1);
    try {
      Class<?>subClass=cl.getComponentType();
      ArrayList<Object> objects=new ArrayList<>();
      Object item;
      while (str.length()!=0&&str.charAt(0) != ']'&&!exception){
        item=getObject(subClass);
        objects.add(item);
        if(str.length()!=0&&str.charAt(0)==','){
          str=str.substring(1);
        }
      }
      Object aObject = Array.newInstance(subClass, objects.size());
      for (int i=0;i<objects.size();i++){
        Array.set(aObject, i, objects.get(i));
      }
      if(str.length()!=0&&str.charAt(0)==']') {
        str=str.substring(1);
        return aObject;
      }
    } catch (Exception e) {
      e.printStackTrace();
      exception=true;
    }
    return null;
  }

  private Object getNotSimple(Class<?> cl) {
    str=str.substring(1);
    try {
      Constructor<?> cons = cl.getDeclaredConstructor();
      Object obj = cons.newInstance();
      Object item;
      Field field;
      String name;
      while (str.length()!=0&&str.charAt(0)!='}'&&!exception){
        name=getName();
        if(exception){
          return null;
        }
        field = cl.getDeclaredField(name);
        field.setAccessible(true);
        item=getObject(field.getType());
        field.set(obj, item);
        field.setAccessible(false);
        if (str.length()!=0&&str.charAt(0) == ','){
          str=str.substring(1);
        }
      }
      if(str.length()==0||exception){
        exception=true;
        return null;
      }
      str=str.substring(1);
      return obj;

    } catch (Exception e) {
      e.printStackTrace();
      exception=true;
    }
    return null;
  }

  private String getName(){
    if(str.length()==0||str.charAt(0)!='"'){
      exception=true;
      return null;
    }
    str=str.substring(1);
    JsonToken jsonToken=new JsonToken();
    String token=jsonToken.getToken(str);
    int tokenLength=token.length();
    if(!isStrOk(tokenLength)){
      return null;
    }
    str=str.substring(tokenLength+2);
    return token;
  }

  private boolean isStrOk(int tokenLength){
    if(tokenLength==0||str.length()<tokenLength+3||str.charAt(tokenLength)!='"'||str.charAt(tokenLength+1)!=':'){
      exception=true;
      return false;
    }
    else return true;
  }

  private Object getSimple(Class<?> cl){
    if(str.length()==0){
      exception=true;
      return null;
    }
    boolean isStr=false;
    if(str.charAt(0)=='"'){
      str=str.substring(1);
      isStr=true;
    }
    JsonToken jsonToken=new JsonToken();
    String value=jsonToken.getToken(str);
    if(str.length()==0){
      exception=true;
      return null;
    }
    str=str.substring(value.length());
    try {
      Object obj;
      Constructor<?> cons;
      if(cl.isPrimitive()){
        cl= Array.get(Array.newInstance(cl,1),0).getClass();
      }
      cons = cl.getConstructor(String.class);
      obj = cons.newInstance(value);
      if(isStr)
        if(str.length()!=0&&str.charAt(0)=='"')
          str=str.substring(1);
        else{
          exception=true;
          return null;
        }
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      exception=true;
    }
    return null;
  }

}
