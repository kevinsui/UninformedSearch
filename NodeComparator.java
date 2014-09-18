import java.util.*;

class NodeComparator implements Comparator<Node> {
  @Override
  public int compare (Node n1, Node n2) {
    if (n1.cost < n2.cost) {
      return -1;
    } else if (n1.cost > n2.cost) {
      return 1;
    } else if (n1.cost == n2.cost) {
      return n1.name.compareTo(n2.name);
    }
    return 0;
  }
}

class DfsComparator implements Comparator<Node> {
  @Override
  public int compare (Node n1, Node n2) {
    if (n1.cost < n2.cost) {
      return -1;
    } else if (n1.cost > n2.cost) {
      return 1;
    } else if (n1.cost == n2.cost) {
      return n2.name.compareTo(n1.name);
    }
    return 0;
  }
}