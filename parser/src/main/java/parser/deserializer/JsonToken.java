package parser.deserializer;

public class JsonToken {
  public Boolean exception=false;


  public String getToken(String str){
    String sub = "";
    if (str.charAt(0)== '"') {
      exception = true;
      return sub;
    }
    while (str.length()!=0&&!isNoEndOfValue(str)) {
      sub += str.charAt(0);
      str = str.substring(1);
    }
    return sub;
}

private boolean isNoEndOfValue(String str){
    if(str.charAt(0)!='}'&&str.charAt(0)!=','&&str.charAt(0)!=']'&&str.charAt(0)!='"'&&str.charAt(0)!='\'')
      return false;
    return true;
}

}
