import classes.Student;
import org.junit.Assert;
import org.junit.Test;
import parser.deserializer.Creator;
import parser.deserializer.DOM.DOMParser;


public class DeserializerTestDOM {
  Creator obj=new DOMParser();

  @Test
  public void DeserializationDom1() {
    String path="src/test/java/filesForDeserialisationTests/test1.json";
    Student student = obj.createObject(path,Student.class);
    Assert.assertEquals(2,student.subj.length);
    Assert.assertEquals("Maths",student.subj[0].getName());
    Assert.assertEquals(15,student.getAge());
    Assert.assertEquals(4,student.marks[0].intValue());
    Assert.assertEquals("Maths",student.favoriteSubj.getName());
    Double z=1.2;
    Assert.assertEquals(z,student.favoriteSubj.z);
    }
  }

