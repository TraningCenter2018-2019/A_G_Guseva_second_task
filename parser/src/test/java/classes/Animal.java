package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Animal{
  private String name;
  private int age;
  ArrayList<Integer> ans;
  ArrayList<Animal> animals;
  Animal []arr;

  public Animal[] getArr() {
    return arr;
  }

  public void setArr(Animal[] arr) {
    this.arr = arr;
  }

  public ArrayList<Animal> getAnimals() {
    return animals;
  }

  public void setAnimals(ArrayList<Animal> animals) {
    this.animals = animals;
  }

  public ArrayList<Integer> getAns() {
    return ans;
  }

  public void setAns(ArrayList<Integer> ans) {
    this.ans = ans;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
