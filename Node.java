import java.util.*;

public class Node {
  protected int id;
  protected String name;
  protected Node parent;
  protected int cost;

  public Node (int id, String name, Node parent) {
    this.id = id;
    this.name = name;
    this.parent = parent;
    this.cost = 0;
  }

  public Node (int id, String name, Node parent, int cost) {
    this.id = id;
    this.name = name;
    this.parent = parent;
    this.cost = cost;
  }
}