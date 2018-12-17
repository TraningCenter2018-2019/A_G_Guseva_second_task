package parser.serializer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class Serializer {
  FileWriter file;
  private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

  public Serializer() {
  }

  private static Set<Class<?>> getWrapperTypes()
  {
    Set<Class<?>> ret = new HashSet<Class<?>>();
    ret.add(Boolean.class);
    ret.add(Character.class);
    ret.add(Byte.class);
    ret.add(Short.class);
    ret.add(Integer.class);
    ret.add(Long.class);
    ret.add(Float.class);
    ret.add(Double.class);
    ret.add(Void.class);
    return ret;
  }
  public static boolean isWrapperType(Class<?> clazz)
  {
    return WRAPPER_TYPES.contains(clazz);
  }

  private String getFields(Class<?> cl,Object obj){
    Field []fields = cl.getDeclaredFields();
    String str ="";
    for(int i=0;i<fields.length;i++){
      fields[i].setAccessible(true);
      str+="\""+fields[i].getName()+"\":";
      try {
        str+=treatDifferentTypes(fields[i].getType(),fields[i].get(obj),obj,fields[i].getName());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

      if(i<fields.length-1)
        str+=",";
    }
    return str;
  }

  private String treatDifferentTypes(Class<?>cl,Object obj,Object mainObj,String name){
    String str="";

    if(isWrapperType(cl)||cl.isPrimitive()){
      str+=obj;
    }
    else if(cl.equals(String.class)){

      str+="\""+obj+"\"";
    }
    else if (cl.isArray()){
      str+=serializeArray(cl.getComponentType(),obj);
    }
    else{
      if(Collection.class.isAssignableFrom(cl)) {
        str+=serializeCollection(cl.getComponentType(), obj,mainObj,name);
      }
      else{
        str+=makeJson(cl,obj);
      }
    }
    return str;
  }

  private String serializeArray(Class arrayType, Object theArray)
  {
    if(theArray==null)
      return null;
    String str="";
    int length = Array.getLength(theArray);
    if(arrayType.isArray())  {
      str+="[";
      for(int j = 0; j < length; j++)
      {
        Object arr2 = Array.get(theArray, j);
        serializeArray(arrayType.getComponentType(),arr2);
        if(j!= length-1)
          str+=",";
      }
      str+="]";
    }
    else
    {
      str+="[";
      Object val;
      for(int j = 0; j < length; j++)
      {
        val=Array.get(theArray,j);
        str+=treatDifferentTypes(arrayType,val,theArray,"");
        if(j!= length-1)
          str+=",";
      }
      str+="]";
    }
    return str;
  }

  private String serializeCollection(Class cl, Object col,Object obj,String name){
    if(col==null)
      return null;
    String str="";
    Collection<?> myCollection = (Collection<?>) col;
    Object val=null;
    Class genericClass = null;
    Iterator it = myCollection.iterator();
    int count=0;
    while (it.hasNext()){
      count++;
      val=it.next();
      genericClass = val.getClass();
      if(isWrapperType(genericClass)||genericClass.isPrimitive()){
        str+=val;
        str+=",{\""+name+"\":";
      }
      else {
        str += makeJson(genericClass, val);
        str = str.substring(0, str.length() - 1);
        str+=",\""+name+"\":";
      }
      if(!it.hasNext())
        str+=null;
    }
    for(int i=0;i<count;i++)
      str+="}";
    return str;
  }

  private String makeJson(Class<?>c,Object obj) {
    String str="{";
    Field []fields = c.getDeclaredFields();
    str+=getFields(c,obj);
    if(c.getGenericSuperclass()!=Object.class){
      Class superClass=c.getSuperclass();
      str+=",";
      str+=getFields(superClass,obj);

    }
    str+="}";
    return str;
  }

  public String Serialize(Object obj) {

    if(obj!=null) {
      Class c = obj.getClass();
      return makeJson(c,obj);
    }
    else return "";
  }
}
