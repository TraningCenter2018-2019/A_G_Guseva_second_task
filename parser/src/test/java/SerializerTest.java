import classes.Animal;
import classes.Cat;
import org.junit.Assert;
import org.junit.Test;
import parser.serializer.Serializer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SerializerTest {

  //from POJO to json
  @Test
  public void serialization1() throws IOException {
    FileWriter file = new FileWriter("src/test/java/resultsOfSerialisation/serialization1.json");
    Serializer s=new Serializer();

    Animal a=new Animal();
    Cat cat=new Cat();
    Cat kit1=new Cat();
    Cat kit2=new Cat();
    ArrayList<Cat> kits=new ArrayList<Cat>();
    kits.add(kit1);
    kits.add(kit2);
    a.setAge(5);
    a.setName("koko");
    ArrayList<Integer> ans=null;
    a.setAns(ans);
    String str=s.Serialize(a);
    file.write(str);
    file.flush();
    file.close();
    Assert.assertEquals("{" +
            "\"name\":\"koko\"," +
            "\"age\":5," +
            "\"ans\":null," +
            "\"animals\":null," +
            "\"arr\":null}",
            str);
  }


  @Test
  public void serialization2() throws IOException {
    FileWriter file = new FileWriter("src/test/java/resultsOfSerialisation/serialization2.json");
    Serializer s=new Serializer();
    Animal a=new Animal();
    Cat cat=new Cat();
    Cat kit1=new Cat();
    Cat kit2=new Cat();
    ArrayList<Cat> kits=new ArrayList<Cat>();
    kits.add(kit1);
    kits.add(kit2);
    ArrayList<Animal> animals=new ArrayList<Animal>();
    Animal an1=new Animal();
    an1.setName("kiki");
    an1.setAge(2);
    animals.add(an1);
    a.setAnimals(animals);
    a.setAge(5);
    a.setName("koko");
    ArrayList<Integer> ans=new ArrayList<Integer>();
    ans.add(1);
    ans.add(2);
    a.setAns(ans);
    String str=s.Serialize(a);
    file.write(str);
    file.flush();
    file.close();
    Assert.assertEquals("{" +
            "\"name\":\"koko\"," +
            "\"age\":5," +
            "\"ans\":1,{\"ans\":2,{\"ans\":null}}," +
            "\"animals\":{" +
                            "\"name\":\"kiki\"," +
                            "\"age\":2," +
                            "\"ans\":null," +
                            "\"animals\":null," +
                            "\"arr\":null," +
                            "\"animals\":null" +
                         "}," +
            "\"arr\":null}",str);
  }

  @Test
  public void serializationListOfObjectsInSubclass() throws IOException {
    FileWriter file = new FileWriter("src/test/java/resultsOfSerialisation/ListOfObjectsInSubclass.json");
    Serializer s=new Serializer();
    Cat cat=new Cat();
    Cat kit1=new Cat();
    kit1.setAge(1);
    Cat kit2=new Cat();
    kit2.setAge(2);
    ArrayList<Cat> kits=new ArrayList<Cat>();
    kits.add(kit1);
    kits.add(kit2);
    cat.setKitties(kits);
    cat.setCanPshh(true);
    cat.setAge(5);
    cat.setName("Nina");
    String str=s.Serialize(cat);
    file.write(str);
    file.flush();
    file.close();
    Assert.assertEquals("{" +
            "\"canPshh\":true," +
            "\"kitties\":{" +
                          "\"canPshh\":false," +
                          "\"kitties\":null," +
                          "\"name\":\"null\"," +
                          "\"age\":1," +
                          "\"ans\":null," +
                          "\"animals\":null," +
                          "\"arr\":null," +
                          "\"kitties\":{" +
                                        "\"canPshh\":false," +
                                        "\"kitties\":null," +
                                        "\"name\":\"null\"," +
                                        "\"age\":2," +
                                        "\"ans\":null," +
                                        "\"animals\":null," +
                                        "\"arr\":null," +
                                        "\"kitties\":null" +
                                       "}" +
                          "}," +
            "\"name\":\"Nina\"," +
            "\"age\":5," +
            "\"ans\":null," +
            "\"animals\":null," +
            "\"arr\":null}",
            str);
  }

  @Test
  public void serializationArrayInClass() throws IOException {
    FileWriter file = new FileWriter("src/test/java/resultsOfSerialisation/ArrayInClass.json");
    Serializer s=new Serializer();
    Animal a=new Animal();
    ArrayList<Integer> ans=null;
    a.setAns(null);
    Animal subAnimal=new Animal();
    subAnimal.setAge(100);
    Animal[]arr=new Animal[]{subAnimal};
    a.setArr(arr);
    String str=s.Serialize(a);
    file.write(str);
    file.flush();
    file.close();
    Assert.assertEquals("{" +
            "\"name\":\"null\"," +
            "\"age\":0," +
            "\"ans\":null," +
            "\"animals\":null," +
            "\"arr\":[" +
                       "{\"name\":\"null\",\"age\":100,\"ans\":null,\"animals\":null,\"arr\":null}" +
                     "]" +
            "}",str);
  }
}
