import java.util.*;

class Caixa {
    int largura;
    int altura;
    int comprimento;
    
    public Caixa(int largura, int altura,int comprimento) {
        this.largura = largura;
        this.altura = altura;
        this.comprimento = comprimento;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int getComprimento() {
        return comprimento;
    }

    @Override
    public String toString() {
        return largura + " " + altura + " " + comprimento;
    }
}