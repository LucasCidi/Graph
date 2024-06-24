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

    public boolean cabeDentro(Caixa outra) {
        return this.comprimento < outra.comprimento && this.largura < outra.largura && this.altura < outra.altura;
    }

    @Override
    public String toString() {
        return largura + " " + altura + " " + comprimento;
    }
}