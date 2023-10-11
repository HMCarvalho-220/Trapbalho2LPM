import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Grafo {
    private ArrayList<Cidade> vertices;
    private ArrayList<Estrada> arestas;

    public Grafo(Cidade cidade1) {
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    }

    public ArrayList<Cidade> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Cidade> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Estrada> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Estrada> arestas) {
        this.arestas = arestas;
    }

    public ArrayList<Cidade> dfs(Cidade cidadeInicial) {
        Stack<Cidade> stack = new Stack<>();
        ArrayList<Cidade> visitados = new ArrayList<>();

        stack.push(cidadeInicial);

        while (!stack.isEmpty()) {
            Cidade cidadeAtual = stack.pop();

            if (!visitados.contains(cidadeAtual)) {
                visitados.add(cidadeAtual);

                for (Estrada estrada : arestas) {
                    if (estrada.getOrigem().equals(cidadeAtual)) {
                        Cidade cidadeDestino = estrada.getDestino();

                        if (!visitados.contains(cidadeDestino)) {
                            stack.push(cidadeDestino);
                        }
                    }
                }
            }
        }

        return visitados;
    }

    public ArrayList<Cidade> cidadesInacessiveis(Cidade cidadeInicial) {
        ArrayList<Cidade> visitados = dfs(cidadeInicial);
        ArrayList<Cidade> inacessiveis = new ArrayList<>();

        for (Cidade cidade : vertices) {
            if (!visitados.contains(cidade)) {
                inacessiveis.add(cidade);
            }
        }

        return inacessiveis;
    }

    public ArrayList<Cidade> rotaMaisCurta(Cidade cidadeInicial) {
        ArrayList<Cidade> cidades = new ArrayList<>(vertices);
        cidades.remove(cidadeInicial);
        ArrayList<Cidade> rota = new ArrayList<>();
        permutar(cidades, 0, cidades.size() - 1, cidadeInicial, rota);
        return rota;
    }

    private void permutar(ArrayList<Cidade> cidades, int l, int r, Cidade cidadeInicial, ArrayList<Cidade> rota) {
        if (l == r) {
            ArrayList<Cidade> temp = new ArrayList<>();
            temp.add(cidadeInicial);
            temp.addAll(cidades);
            temp.add(cidadeInicial);
            if (rota.isEmpty() || getDistanciaTotal(temp) < getDistanciaTotal(rota)) {
                rota.clear();
                rota.addAll(temp);
            }
        } else {
            for (int i = l; i <= r; i++) {
                Collections.swap(cidades, l, i);
                permutar(cidades, l+1, r, cidadeInicial, rota);
                Collections.swap(cidades, l, i); // backtrack
            }
        }
    }

    private int getDistanciaTotal(ArrayList<Cidade> rota) {
        int total = 0;
        for (int i = 0; i < rota.size() - 1; i++) {
            total += getDistancia(rota.get(i), rota.get(i+1));
        }
        return total;
    }

    private int getDistancia(Cidade cidade1, Cidade cidade2) {
        for (Estrada estrada : arestas) {
            if ((estrada.getOrigem().equals(cidade1) && estrada.getDestino().equals(cidade2)) ||
                    (estrada.getOrigem().equals(cidade2) && estrada.getDestino().equals(cidade1))) {
                return estrada.getPeso();
            }
        }

        return Integer.MAX_VALUE;
    }
}