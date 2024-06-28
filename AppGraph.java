import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppGraph extends Graph {
  public static void main(String[] args) {
    List<Box> boxes = new ArrayList<>();

    String path = "Graphs/caso00100.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String linha;

      while ((linha = br.readLine()) != null) {
        String[] dimensions = linha.split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        int length = Integer.parseInt(dimensions[2]);
        boxes.add(new Box(width, height, length));
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    AppGraph graph = new AppGraph();
    graph.buildGraph(boxes, graph);

    List<Box> longestPath = graph.findLongestPath(graph);
    System.out.println("A maior sequência de box é:");
    System.out.println(longestPath.size());
  }

  public boolean FitsInside(Box box1, Box box2) {
    int[] dimensions1 = box1.getDimensions();
    int[] dimensions2 = box2.getDimensions();

    Arrays.sort(dimensions1);
    Arrays.sort(dimensions2);

    for (int i = 0; i < dimensions1.length; i++) {
      if (dimensions1[i] >= dimensions2[i]) {
        return false;
      }
    }

    return true;
  }

  public void buildGraph(List<Box> box, Graph graph) {
    for (int i = 0; i < box.size(); i++) {
      for (int j = 0; j < box.size(); j++) {
        if (i != j && FitsInside(box.get(i), box.get(j))) {
          graph.addEdge(box.get(i).toString(), box.get(j).toString());
        }
      }
    }
  }

  public List<Box> findLongestPath(Graph graph) {
    List<String> verticesOrdenados = ordTopologica(graph);
    if (verticesOrdenados.isEmpty()) {
      return new ArrayList<>();
    }
    Map<String, Integer> dist = new HashMap<>();
    Map<String, String> anterior = new HashMap<>();

    for (String v : graph.getVerts()) {
      dist.put(v, 0);
    }
    dist.put(verticesOrdenados.get(0), 0);

    for (String u : verticesOrdenados) {
      for (String v : graph.getAdj(u)) {
        if (dist.get(v) < dist.get(u) + 1) {
          dist.put(v, dist.get(u) + 1);
          anterior.put(v, u);
        }
      }
    }

    String ultimo = null;
    int maiorDist = Integer.MIN_VALUE;
    for (String v : dist.keySet()) {
      if (dist.get(v) > maiorDist) {
        maiorDist = dist.get(v);
        ultimo = v;
      }
    }

    List<Box> caminho = new LinkedList<>();
    for (String at = ultimo; at != null; at = anterior.get(at)) {
      caminho.add(0, stringTobox(at));
    }

    return caminho;
  }

  private Box stringTobox(String s) {
    String[] dimensions = s.split(" ");
    return new Box(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), Integer.parseInt(dimensions[2]));
  }

  private List<String> ordTopologica(Graph graph) {
    Set<String> visitados = new HashSet<>();
    LinkedList<String> resultado = new LinkedList<>();
    for (String vertice : graph.getVerts()) {
      if (!visitados.contains(vertice)) {
        ordTopologicaUtil(vertice, visitados, resultado, graph);
      }
    }
    return resultado;
  }

  private void ordTopologicaUtil(String v, Set<String> visitados, LinkedList<String> resultado, Graph graph) {
    visitados.add(v);
    for (String vizinho : graph.getAdj(v)) {
      if (!visitados.contains(vizinho)) {
        ordTopologicaUtil(vizinho, visitados, resultado, graph);
      }
    }
    resultado.addFirst(v);
  }
}