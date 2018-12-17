package parser.deserializer.DOM;

import parser.deserializer.DOM.Node;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CreatedObjectInfo {
  public boolean exception;

  public <T>T getObject(Node node, Class<?>cl) {
    if (exception) {
      return null;
    }
    if (node.value != null) {
      Object obj = getSimple(node, cl);
      if (exception) {
        return null;
      }
      return (T) obj;
    }
    if (node.type == Array.class) {
      Object obj = getArray(node, cl);
      if (exception) {
        return null;
      }
      return (T) obj;
    }

    Object obj = getNotSimple(node, cl);
    if (exception) {
      return null;
    }
    return (T) obj;
  }

  private  <T>T getNotSimple(Node node,Class<?>cl) {
    try {
      Constructor<?> cons = cl.getDeclaredConstructor();
      Object obj = cons.newInstance();
      Node item;
      Field field;
      for (int i = 0; i < node.items.size(); ++i) {
        item = node.items.get(i);
        field = cl.getDeclaredField(item.name);
        field.setAccessible(true);
        if(item.value!=null){
          field.set(obj, getSimple(item,field.getType()));
          field.setAccessible(false);
        }
        else if(item.type== Array.class) {
          Object aObject=getArray(item,field.getType().getComponentType());
          field.set(obj, aObject);
          field.setAccessible(false);
        }
        else{
          item.value=getObject(item,field.getType());
          field.set(obj, item.value);
          field.setAccessible(false);

        }
      }
      return (T)obj;
    } catch (Exception e) {
      exception=true;
      e.printStackTrace();
    }
    return null;
  }

  private  <T>T[] getArray(Node node,Class<?>type) {
    Object aObject;
    if(node.items.get(0).value!=null) {
      aObject = Array.newInstance(node.items.get(0).value.getClass(), node.items.size());
      for (int j = 0; j < node.items.size(); j++)
        Array.set(aObject, j, node.items.get(j).value);
    }
    else{
      aObject = Array.newInstance(type, node.items.size());
      for (int j = 0; j < node.items.size(); j++)
        Array.set(aObject, j, getObject(node.items.get(j),type));
    }
    return (T[])aObject;
  }

  private  <T>T getSimple(Node node,Class<?>type) {
    Object aObject=node.value;
    return (T)aObject;
  }

}
