package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cat extends Animal {
  private boolean canPshh;
  private ArrayList<Cat>kitties;

  public ArrayList<Cat> getKitties() {
    return kitties;
  }

  public void setKitties(ArrayList<Cat> kitties) {
    this.kitties = kitties;
  }

  public boolean isCanPshh() {
    return canPshh;
  }

  public void setCanPshh(boolean canPshh) {
    this.canPshh = canPshh;
  }
  /*private ArrayList<Cat> kitties;

  public ArrayList<Cat> getKitties() {
    return kitties;
  }

  public void setKitties(ArrayList<Cat> kitties) {
    this.kitties = kitties;
  }*/
}
