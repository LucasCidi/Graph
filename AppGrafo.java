import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppGrafo extends Digraph {
  public static void main(String[] args) {
    List<Caixa> caixas = new ArrayList<>();
    Digraph graph = new Digraph();
    int count = 0;

    try (BufferedReader br = new BufferedReader(new FileReader("teste.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] partes = linha.split(" ");
        int largura = Integer.parseInt(partes[0]);
        int altura = Integer.parseInt(partes[1]);
        int comprimento = Integer.parseInt(partes[2]);
        caixas.add(new Caixa(largura, altura, comprimento));
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    AppGrafo grafo = new AppGrafo();
    grafo.construirGrafo(caixas, graph);

    List<Caixa> maiorSequencia = grafo.encontrarMaiorCaminho(graph);
    System.out.println("A maior sequência de caixas é:");
    for (Caixa caixa : maiorSequencia) {
      System.out.println(caixa);
      count++;
    }
    System.out.println("Tamanho da maior sequência: " + count);
  }

  public boolean cabeDentro(Caixa c1, Caixa c2) {
    return c1.getComprimento() < c2.getComprimento()
        && c1.getLargura() < c2.getLargura()
        && c1.getAltura() < c2.getAltura();
  }

  public void construirGrafo(List<Caixa> caixas, Digraph graph) {
    for (int i = 0; i < caixas.size(); i++) {
      for (int j = 0; j < caixas.size(); j++) {
        if (i != j && cabeDentro(caixas.get(i), caixas.get(j))) {
          graph.addEdge(caixaParaString(caixas.get(i)), caixaParaString(caixas.get(j)));
          System.out.println("Aresta adicionada: " + caixaParaString(caixas.get(i)) + " -> " + caixaParaString(caixas.get(j)));
        }
      }
    }
  }

  private String caixaParaString(Caixa caixa) {
    return caixa.getLargura() + " " + caixa.getAltura() + " " + caixa.getComprimento();
  }

  public List<Caixa> encontrarMaiorCaminho(Digraph graph) {
    List<String> verticesOrdenados = ordenacaoTopologica(graph);
    if (verticesOrdenados.isEmpty()) {
      System.err.println("Erro: A lista de vértices ordenados está vazia!");
      return new ArrayList<>();
    }
    Map<String, Integer> dist = new HashMap<>();
    Map<String, String> anterior = new HashMap<>();

    for (String v : verticesOrdenados) {
      dist.put(v, 0);
    }

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

    List<Caixa> caminho = new LinkedList<>();
    for (String at = ultimo; at != null; at = anterior.get(at)) {
      caminho.add(0, stringParaCaixa(at));
    }

    return caminho;
  }

  private Caixa stringParaCaixa(String s) {
    String[] partes = s.split(" ");
    return new Caixa(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
  }

  private List<String> ordenacaoTopologica(Digraph graph) {
    Set<String> visitados = new HashSet<>();
    LinkedList<String> resultado = new LinkedList<>();
    for (String vertice : graph.getVerts()) {
      if (!visitados.contains(vertice)) {
        ordenacaoTopologicaUtil(vertice, visitados, resultado, graph);
      }
    }
    return resultado;
  }

  private void ordenacaoTopologicaUtil(String v, Set<String> visitados, LinkedList<String> resultado, Digraph graph) {
    visitados.add(v);
    for (String vizinho : graph.getAdj(v)) {
      if (!visitados.contains(vizinho)) {
        ordenacaoTopologicaUtil(vizinho, visitados, resultado, graph);
      }
    }
    resultado.addFirst(v);
  }
}