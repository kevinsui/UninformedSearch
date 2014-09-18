import java.io.*;
import java.util.*;

public class Search {

  String[] names;
  int[][] distances;

  public Search (String[] names, int[][] distances) {
    this.names = names;
    this.distances = distances;
  }

  protected void breadthFirst (String source, String destination) {
    int start = getIndex(source);
    int finish = getIndex(destination);
    String solution = "";
    int totalDistance = 0;
    // create node tree
    Node root = new Node(start, source, null);
    // add initial node to queue
    ArrayList<Integer> visited = new ArrayList<Integer>();
    ArrayList<Node> queue = new ArrayList<Node>();
    queue.add(root);
    // run BFS algorithm
    while (!queue.isEmpty()) {
      Node currentNode = queue.remove(0);
      visited.add(currentNode.id);
      // check if current node is the finish state
      if (solution.equals("") && currentNode.id == finish) {
        solution = getPath(currentNode);
        totalDistance = getCost(currentNode);
        break;
      }
      // loop through children of current node
      for (int i = 0; i < names.length; i++) {
        // skip if this edge does not have a value
        if (distances[currentNode.id][i] == 0) {
          continue;
        }
        int cost = currentNode.cost + 1;
        Node childNode = new Node(i, names[i], currentNode, cost);
        // check that the node state does not exist before adding to open
        Boolean stateExists = false;
        for (Node node : queue) {
          if (childNode.id == node.id)
            stateExists = true;
        }
        for (int id : visited) {
          if (childNode.id == id)
            stateExists = true;
        }
        if (!stateExists) {
          queue.add(childNode);
        }
      }
      Collections.sort(queue, new NodeComparator());
    }
    // check if no solution exists
    if (solution.equals("")) {
      solution = "NoPathAvailable";
    }
    // get entire expansion performed by the search
    String expansion = getExpansion(visited);
    // write solutions to output file
    System.out.println(expansion);
    System.out.println(solution);
    System.out.println(totalDistance);
    writeToFile(expansion, solution, totalDistance);
  }

  protected void depthFirst (String source, String destination) {
    int start = getIndex(source);
    int finish = getIndex(destination);
    String solution = "";
    int totalDistance = 0;
    // create node tree
    Node root = new Node(start, source, null);
    // add initial node to stack
    ArrayList<Integer> visited = new ArrayList<Integer>();
    ArrayList<Node> stack = new ArrayList<Node>();
    stack.add(root);
    // run DFS algorithm
    while (!stack.isEmpty()) {
      Node currentNode = stack.remove(stack.size()-1);
      visited.add(currentNode.id);
      System.out.println("Added " + names[currentNode.id] + " to visited");
      // check if current node is the finish state
      if (solution.equals("") && currentNode.id == finish) {
        solution = getPath(currentNode);
        totalDistance = getCost(currentNode);
        break;
      }
      // loop through children of current node
      for (int i = 0; i < names.length; i++) {
        // skip if this edge does not have a value
        if (distances[currentNode.id][i] == 0) {
          continue;
        }
        int cost = currentNode.cost + 1;
        Node childNode = new Node(i, names[i], currentNode, cost);
        // check that the node state does not exist before adding to open
        Boolean stateExists = false;
        for (Node node : stack) {
          if (childNode.id == node.id)
            stateExists = true;
        }
        for (int id : visited) {
          if (childNode.id == id)
            stateExists = true;
        }
        if (!stateExists) {
          stack.add(childNode);
        }
      }
      Collections.sort(stack, new DfsComparator());
      for (Node node : stack) {
        System.out.print(node.name + "-");
      }
      System.out.print("\n");
    }
    // check if no solution exists
    if (solution.equals("")) {
      solution = "NoPathAvailable";
    }
    // get entire expansion performed by the search
    String expansion = getExpansion(visited);
    // write solutions to output file
    System.out.println(expansion);
    System.out.println(solution);
    System.out.println(totalDistance);
    writeToFile(expansion, solution, totalDistance);
  }

  protected void uniformCost (String source, String destination) {
    int start = getIndex(source);
    int finish = getIndex(destination);
    String solution = "";
    int totalDistance = 0;
    // create node tree
    Node root = new Node(start, source, null);
    // create sorted list and add initial node
    ArrayList<Node> closed = new ArrayList<Node>();
    ArrayList<Node> open = new ArrayList<Node>();
    open.add(root);
    while (!open.isEmpty()) {
      Node currentNode = open.remove(0);
      closed.add(currentNode);
      if (currentNode.id == finish) {
        solution = getPath(currentNode);
        totalDistance = getCost(currentNode);
        break;
      }
      // loop through children of current node
      for (int i = 0; i < names.length; i++) {
        // skip if this edge does not have a value
        if (distances[currentNode.id][i] == 0) {
          continue;
        }
        int cost = currentNode.cost + distances[currentNode.id][i];
        Node childNode = new Node(i, names[i], currentNode, cost);
        // check that the node state does not exist before adding to open
        Boolean stateExists = false;
        for (Node openNode : open) {
          if (childNode.id == openNode.id)
            stateExists = true;
        }
        for (Node closedNode : closed) {
          if (childNode.id == closedNode.id)
            stateExists = true;
        }
        if (!stateExists) {
          open.add(childNode);
          continue;
        }
        // if this node is in the open list
        for (Node openNode : open) {
          if (childNode.id == openNode.id 
              && childNode.cost < openNode.cost) {
            openNode = childNode;
          }
        }
        // if this node is in the closed list
        for (Node closedNode : closed) {
          if (childNode.id == closedNode.id 
              && childNode.cost < closedNode.cost) {
            closedNode = childNode;
          }
        }
      }
      Collections.sort(open, new NodeComparator());
    }
    // check if no solution exists
    if (solution.equals("")) {
      solution = "NoPathAvailable";
    }
    // get entire expansion performed by the search
    String expansion = getNodeExpansion(closed);
    // write solutions to output file
    System.out.println(expansion);
    System.out.println(solution);
    System.out.println(totalDistance);
    writeToFile(expansion, solution, totalDistance);
  }

  protected int getIndex (String name) {
    for (int i = 0; i < names.length; i ++) {
      if (name.equals(names[i]))
        return i;
    }
    return -1;
  }

  protected String getExpansion (ArrayList<Integer> visited) {
    String path = "";
    for (int i = 0; i < visited.size(); i++) {
      path += names[visited.get(i)] + "-";
    }
    path = path.substring(0, path.length()-1);
    return path;
  }

  protected String getNodeExpansion (ArrayList<Node> visited) {
    String path = "";
    for (int i = 0; i < visited.size(); i++) {
      path += visited.get(i).name + "-";
    }
    path = path.substring(0, path.length()-1);
    return path;
  }

  protected String getPath (Node node) {
    String path = node.name;
    while (node.parent != null) {
      node = node.parent;
      path = node.name + "-" + path;
    }
    return path;
  }

  protected int getCost (Node node) {
    int cost = 0;
    while (node.parent != null) {
      cost += distances[node.parent.id][node.id];
      node = node.parent;
    }
    return cost;
  }

  protected void writeToFile (String expansion, String solution, int cost) {
    try {
      PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
      writer.println(expansion);
      writer.println(solution);
      writer.println(cost);
      writer.close();
    } catch (IOException e) {
      System.out.println("Error printing results to file.");
    }
  }
}