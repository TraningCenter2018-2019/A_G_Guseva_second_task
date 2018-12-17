package parser.deserializer;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class NodeType {

  private static boolean isNumericArray(String str) {
    if (str == null)
      return false;
    char[] data = str.toCharArray();
    if (data.length <= 0)
      return false;
    int index = 0;
    if (data[0] == '-' && data.length > 1)
      index = 1;
    for (; index < data.length; index++) {
      if (data[index] < '0' || data[index] > '9')
        return false;
    }
    return true;
  }

  private static boolean isDouble(String str){
    if (str == null)
      return false;
    char[] data = str.toCharArray();
    if (data.length <= 0)
      return false;
    int index = 0;
    if (data[0] == '-' && data.length > 1)
      index = 1;
    if (!(data.length > 2)&&!(data[index+1]=='.'))
      return false;
    for (; index < data.length; index++) {
      if ((data[index] < '0' || data[index] > '9')&&(data[index]!='.'))
        return false;
    }
    return true;
  }

  public static Class<?> getType(String str) {
    if(isNumericArray(str))
      return Integer.class;
    if(isDouble(str))
      return Double.class;
    return null;
  }
}