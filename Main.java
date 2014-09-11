import java.io.*;

public class Main {
  public static void main (String[] args) {
    try {
      // parse file line by line and extract necessary data
      BufferedReader br = new BufferedReader(new FileReader("input.txt"));
      int task = Integer.parseInt(br.readLine());
      String source = br.readLine();
      String destination = br.readLine();
      int numNodes = Integer.parseInt(br.readLine());
      String[] nodes = new String[numNodes];
      int[][] distances = new int[numNodes][numNodes];
      for (int i = 0; i < numNodes; i++) {
        nodes[i] = br.readLine();
      }
      for (int i = 0; i < numNodes; i++) {
        String[] distance = br.readLine().split(" ");
        for (int j = 0; j < numNodes; j++) {
          distances[i][j] = Integer.parseInt(distance[j]);
        }
      }
      br.close();
      // call corresponding task to run search
      Search search = new Search(nodes, distances);
      switch (task) {
        case 1: search.BreadthFirst(source, destination);
                break;
        case 2: search.DepthFirst(source, destination);
                break;
        case 3: search.UniformCost(source, destination);
                break;
      }
    } catch(IOException e) {
      System.out.println("Failed to read file.");
    }
  }
}