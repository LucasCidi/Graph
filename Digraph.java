public class Digraph extends Grafo {

    public Digraph() {
      super();
    }
  
    public void addEdge(String v, String w) {
      adicionarAresta(v, w);
    }
  
    public String toDot() {
      StringBuilder sb = new StringBuilder();
      sb.append("digraph {" + NOVA_LINHA);
      sb.append("rankdir = LR;" + NOVA_LINHA);
      sb.append("node [shape = circle];" + NOVA_LINHA);
      for (String v : getVertices()) {
        for (String w : getAdjacentes(v)) {
          sb.append("\"" + v + "\"" + " -> " + "\"" + w + "\"" + NOVA_LINHA);
        }
      }
      sb.append("}" + NOVA_LINHA);
      return sb.toString();
    }
  }