import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {
  protected static final String NOVA_LINHA = System.getProperty("line.separator");

  protected Map<String, List<String>> grafo;
  protected Set<String> vertices;
  protected int totalVertices;
  protected int totalArestas;

  public Grafo() {
    grafo = new HashMap<>();
    vertices = new HashSet<>();
    totalVertices = totalArestas = 0;
  }

  public void adicionarAresta(String v, String w) {
    adicionarNaLista(v, w);
    if (!vertices.contains(v)) {
      vertices.add(v);
      totalVertices++;
    }
    if (!vertices.contains(w)) {
      vertices.add(w);
      totalVertices++;
    }
  }

  public Iterable<String> getAdjacentes(String v) {
    List<String> res = grafo.get(v);
    if (res == null)
      res = new LinkedList<>();
    return res;
  }

  public Set<String> getVertices() {
    return vertices;
  }

  public int getTotalVertices() {
    return totalVertices;
  }

  public int getTotalArestas() {
    return totalArestas;
  }

  // Adiciona um vértice adjacente a outro, criando a lista
  // de adjacências caso ainda não exista no dicionário
  protected List<String> adicionarNaLista(String v, String w) {
    List<String> lista = grafo.get(v);
    if (lista == null)
      lista = new LinkedList<>();
    lista.add(w);
    grafo.put(v, lista);
    totalArestas++;
    return lista;
  }
}

