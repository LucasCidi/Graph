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

public class AppGrafo extends Graph {
  public static void main(String[] args) {
    List<Caixa> caixas = new ArrayList<>();
    int count = 0;
    
    try (BufferedReader br = new BufferedReader(new FileReader("caso00050.txt"))) {
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
    grafo.construirGrafo(caixas);

    List<Caixa> maiorSequencia = grafo.encontrarMaiorCaminho();
    System.out.println("A maior sequência de caixas é:");
    for (Caixa caixa : maiorSequencia) {
      System.out.println(caixa);
      count++;
    }
    System.out.println(count);
  }

  public boolean cabeDentro(Caixa c1, Caixa c2) {
    return c1.getComprimento() < c2.getComprimento() && c1.getLargura() < c2.getLargura() && c1.getAltura() < c2.getAltura();
}

  public void construirGrafo(List<Caixa> caixas) {
    for (int i = 0; i < caixas.size(); i++) {
        for (int j = 0; j < caixas.size(); j++) {
            if (i != j && cabeDentro(caixas.get(i), caixas.get(j))) {
                // Adicionar aresta do grafo dirigido da caixa que cabe para a caixa maior
                addEdge(caixaParaString(caixas.get(i)), caixaParaString(caixas.get(j)));
            }
        }
    }
}

  private String caixaParaString(Caixa caixa) {
    return caixa.largura + " " + caixa.altura + " " + caixa.comprimento;
  }

  public List<Caixa> encontrarMaiorCaminho() {
    List<String> verticesOrdenados = ordenacaoTopologica();
    Map<String, Integer> dist = new HashMap<>();
    Map<String, String> anterior = new HashMap<>();

    for (String v : getVerts()) {
      dist.put(v, 0);  // A distância inicial deve ser 0, não Integer.MIN_VALUE
  }
    dist.put(verticesOrdenados.get(0), 0);

    for (String u : verticesOrdenados) {
      for (String v : getAdj(u)) {
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

  private List<String> ordenacaoTopologica() {
    Set<String> visitados = new HashSet<>();
    LinkedList<String> resultado = new LinkedList<>();
    for (String vertice : getVerts()) {
      if (!visitados.contains(vertice)) {
        ordenacaoTopologicaUtil(vertice, visitados, resultado);
      }
    }
    return resultado;
  }

  private void ordenacaoTopologicaUtil(String v, Set<String> visitados, LinkedList<String> resultado) {
    visitados.add(v);
    for (String vizinho : getAdj(v)) {
        if (!visitados.contains(vizinho)) {
            ordenacaoTopologicaUtil(vizinho, visitados, resultado);
        }
    }
    resultado.addFirst(v);
  }
}