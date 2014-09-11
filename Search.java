import java.io.*;
import java.util.*;

public class Search {

  String[] names;
  int[][] distances;

  public Search (String[] names, int[][] distances) {
    this.names = names;
    this.distances = distances;
  }

  protected void BreadthFirst (String source, String destination) {
    int start = GetIndex(source);
    int finish = GetIndex(destination);
    String solution = "";
    int totalDistance = 0;
    // create node tree
    HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
    Node root = new Node(start, source, null);
    nodes.put(start, root);
    // add initial node to queue
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Queue<Integer> queue = new LinkedList<Integer>();
    visited.add(start);
    queue.add(start);
    // run BFS algorithm
    while (!queue.isEmpty()) {
      int current = queue.poll();
      Node currentNode = nodes.get(current);
      // check if current node is the finish state
      if (solution.equals("") && current == finish) {
        solution = GetPath(currentNode);
        totalDistance = GetCost(currentNode);
      }
      // loop through children of current node
      for (int i = 0; i < names.length; i++) {
        // add unvisited child to queue
        if (!visited.contains(i) && distances[current][i] > 0) {
          queue.add(i);
          visited.add(i);
          Node childNode = new Node(i, names[i], currentNode);
          nodes.put(i, childNode);
        }
      }
    }
    // check if no solution exists
    if (solution.equals("")) {
      solution = "NoPathAvailable";
    }
    // get entire expansion performed by the search
    String expansion = GetExpansion(visited);
    // write solutions to output file
    System.out.println(expansion);
    System.out.println(solution);
    System.out.println(totalDistance);
    WriteToFile(expansion, solution, totalDistance);
  }

  protected void DepthFirst (String source, String destination) {
    int start = GetIndex(source);
    int finish = GetIndex(destination);
    String solution = "";
    int totalDistance = 0;
    // create node tree
    HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
    Node root = new Node(start, source, null);
    nodes.put(start, root);
    // add initial node to stack
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(start);
    // run DFS algorithm
    while (!stack.empty()) {
      int current = stack.pop();
      Node currentNode = nodes.get(current);
      if (!visited.contains(current)) {
        visited.add(current);
        // check if current node is the finish state
        if (solution.equals("") && current == finish) {
          solution = GetPath(currentNode);
          totalDistance = GetCost(currentNode);
        }
        // loop through children of current node
        for (int i = names.length - 1; i >= 0; i--) {
          // add unvisited child to stack
          if (!visited.contains(i) && distances[current][i] > 0) {
            stack.push(i);
            Node childNode = new Node(i, names[i], currentNode);
            nodes.put(i, childNode);
          }
        }
      }
    }
    // check if no solution exists
    if (solution.equals("")) {
      solution = "NoPathAvailable";
    }
    // get entire expansion performed by the search
    String expansion = GetExpansion(visited);
    // write solutions to output file
    System.out.println(expansion);
    System.out.println(solution);
    System.out.println(totalDistance);
    WriteToFile(expansion, solution, totalDistance);
  }

  protected void UniformCost (String source, String destination) {
    int start = GetIndex(source);
    int finish = GetIndex(destination);
  }

  protected int GetIndex(String name) {
    for (int i = 0; i < names.length; i ++) {
      if (name.equals(names[i]))
        return i;
    }
    return -1;
  }

  protected String GetExpansion (ArrayList<Integer> visited) {
    String path = "";
    for (int i = 0; i < visited.size(); i++) {
      path += names[visited.get(i)] + "-";
    }
    path = path.substring(0, path.length()-1);
    return path;
  }

  protected String GetPath (Node node) {
    String path = node.name;
    while (node.parent != null) {
      node = node.parent;
      path = node.name + "-" + path;
    }
    return path;
  }

  protected int GetCost (Node node) {
    int cost = 0;
    while (node.parent != null) {
      cost += distances[node.parent.id][node.id];
      node = node.parent;
    }
    return cost;
  }

  protected void WriteToFile(String expansion, String solution, int cost) {
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