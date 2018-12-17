package parser.deserializer;

public abstract class Creator {
  public abstract <T>T createObject(String path,  Class<?>cl);
}
