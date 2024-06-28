import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
  protected static final String NEWLINE = System.getProperty("line.separator");

  protected Map<String, List<String>> Grafo;
  protected Set<String> vertices;
  protected int totalVertices;
  protected int totalEdges;

  public Graph() {
   Grafo = new HashMap<>();
    vertices = new HashSet<>();
    totalVertices = totalEdges = 0;
  }

  public void addEdge(String v, String w) {
    addToList(v, w);
    if (!vertices.contains(v)) {
      vertices.add(v);
      totalVertices++;
    }
    if (!vertices.contains(w)) {
      vertices.add(w);
      totalVertices++;
    }
  }

  public Iterable<String> getAdj(String v) {
    List<String> res = Grafo.get(v);
    if (res == null)
      res = new LinkedList<>();
    return res;
  }

  public Set<String> getVerts() {
    return vertices;
  }

  public int getTotalVertices() {
    return totalVertices;
  }

  public int getTotalEdges() {
    return totalEdges;
  }

  protected List<String> addToList(String v, String w) {
    List<String> list = Grafo.get(v);
    if (list == null)
      list = new LinkedList<>();
    list.add(w);
   Grafo.put(v, list);
    totalEdges++;
    return list;
  }
}