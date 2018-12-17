import classes.Student;
import org.junit.Assert;
import org.junit.Test;
import parser.deserializer.Creator;
import parser.deserializer.SAX.SAXParser;

public class DeserializationTestSAX {
  Creator obj=new SAXParser();

  @Test
  public void DeserializationSAX1() {
    String path="src/test/java/filesForDeserialisationTests/test2.json";
    Student student = obj.createObject(path,Student.class);
    Assert.assertEquals(2,student.subj.length);
    Assert.assertEquals("Eng",student.subj[1].getName());
    Assert.assertEquals(15,student.getAge());
    Assert.assertEquals(3,student.marks[2].intValue());
    Assert.assertEquals("Maths",student.favoriteSubj.getName());
  }

}

