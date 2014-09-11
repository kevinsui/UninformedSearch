import java.util.*;

public class Node {
  protected int id;
  protected String name;
  protected Node parent;

  public Node (int id, String name, Node parent) {
    this.id = id;
    this.name = name;
    this.parent = parent;
  }
}