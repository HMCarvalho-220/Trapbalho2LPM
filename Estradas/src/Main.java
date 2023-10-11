import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo(new Cidade(0, "Inicial")); // Cria um grafo vazio

        try {
            File file = new File("C:\\Users\\Hugo\\IdeaProjects\\Estradas\\src\\cidades.txt"); // Substitua "src/cidades.txt" pelo caminho do seu arquivo
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(": ");
                String nomeCidadeOrigem = partes[0];
                String[] estradas = partes[1].split(", ");

                Cidade cidadeOrigem = findOrCreateCidade(grafo, nomeCidadeOrigem);

                for (String estrada : estradas) {
                    String[] partesEstrada = estrada.split(" \\(");
                    String nomeCidadeDestino = partesEstrada[0];
                    int peso = Integer.parseInt(partesEstrada[1].substring(0, partesEstrada[1].length() - 1));

                    Cidade cidadeDestino = findOrCreateCidade(grafo, nomeCidadeDestino);

                    grafo.getArestas().add(new Estrada(cidadeOrigem, cidadeDestino, peso));
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo.");
            e.printStackTrace();
        }

        if (!grafo.getVertices().isEmpty()) {
            ArrayList<Cidade> recomendacoes = grafo.dfs(grafo.getVertices().get(0));

            // Imprime as recomendações
            System.out.println("Recomendações de visitação a partir da " + grafo.getVertices().get(0).getNome() + ":");
            for (Cidade cidadeRecomendada : recomendacoes) {
                System.out.println("- " + cidadeRecomendada.getNome());
            }
            ArrayList<Cidade> inacessiveis = grafo.cidadesInacessiveis(grafo.getVertices().get(0));

            // Cidades inacessíveis
            System.out.println("Cidades inacessíveis:");
            for (Cidade cidadeInacessivel : inacessiveis) {
                System.out.println("- " + cidadeInacessivel.getNome());
            }
            ArrayList<Cidade> rotaMaisCurta = grafo.rotaMaisCurta(grafo.getVertices().get(0));

            // Rota mais curta
            System.out.println("Rota mais curta a partir da " + grafo.getVertices().get(0).getNome() + ":");
            for (Cidade cidade : rotaMaisCurta) {
                System.out.println("- " + cidade.getNome());
            }
        } else {
            System.out.println("Não há cidades no grafo.");
        }
    }

    private static Cidade findOrCreateCidade(Grafo grafo, String nome) {
        for (Cidade cidade : grafo.getVertices()) {
            if (cidade.getNome().equals(nome)) {
                return cidade;
            }
        }

        Cidade novaCidade = new Cidade(grafo.getVertices().size() + 1, nome);
        grafo.getVertices().add(novaCidade);
        return novaCidade;
    }
}
