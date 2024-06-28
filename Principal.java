import arquivos.ArquivoAutores;
import arquivos.ArquivoCategorias;
import arquivos.ArquivoLivros;
import entidades.Autor;
import entidades.Categoria;
import entidades.Livro;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Principal {

    private static final Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            exibirMenuPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            console.close();
        }
    }

    private static void exibirMenuPrincipal() throws Exception {
        int opcao;
        do {
            mostrarOpcoesMenu();
            opcao = lerOpcao();
            executarOpcao(opcao);
        } while (opcao != 0);
    }

    private static void mostrarOpcoesMenu() {
        System.out.println("\n\n\nLivros - AEDs-III");
        System.out.println("------------");
        System.out.println("\n> Início");
        System.out.println("\n1) Categorias");
        System.out.println("2) Autores");
        System.out.println("3) Livros");
        System.out.println("4) Fazer um Backup");
        System.out.println("5) Recuperar um Backup");
        System.out.println("\n9) Reiniciar BD");
        System.out.println("\n0) Sair");
    }

    private static int lerOpcao() {
        int opcao;
        System.out.print("\nOpção: ");
        try {
            opcao = Integer.parseInt(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }
        return opcao;
    }

    private static void executarOpcao(int opcao) throws Exception {
        switch (opcao) {
            case 1:
                new MenuCategorias().menu();
                break;
            case 2:
                new MenuAutores().menu();
                break;
            case 3:
                new MenuLivros().menu();
                break;
            case 4:
            case 0:
                realizarBackup();
                break;
            case 5:
                listarBackups();
                break;
            case 9:
                reiniciarBancoDados();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private static void realizarBackup() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        String data = dt.format(formatter);
        String diretorioBackup = "backups/backup-" + data.replace(":","h") + "/";
        File pastaBackup = new File(diretorioBackup);

        try {
            pastaBackup.mkdir(); // Cria o diretório de backup se ainda não existir
            Backup.criarBackup(pastaBackup); // Chama o método criarBackup passando o diretório de backup como argumento
            System.out.println("Backup realizado com sucesso em: " + diretorioBackup);
        } catch (Exception e) {
            System.out.println("Erro ao realizar o backup: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void listarBackups() {
        Backup.listarBackups();
    }

    private static void reiniciarBancoDados() {
        System.out.println("Reiniciando banco de dados...");
        preencherDadosExemplo();
    }

    private static void preencherDadosExemplo() {
        try {
            limparArquivos();

            ArquivoLivros arqLivros = new ArquivoLivros();
            ArquivoCategorias arqCategorias = new ArquivoCategorias();
            ArquivoAutores arqAutores = new ArquivoAutores();

            criarCategoriasExemplo(arqCategorias);
            criarAutoresExemplo(arqAutores);
            criarLivrosExemplo(arqLivros);

            arqLivros.close();
            arqCategorias.close();
            arqAutores.close();

            System.out.println("Banco de dados reinicializado com dados de exemplo.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void limparArquivos() {
        // Implementação do método para limpar arquivos antigos antes de reiniciar
        try {
            String[] filesToDelete = {
                    "dados/categorias.db", "dados/categorias.hash_d.db", "dados/categorias.hash_c.db",
                    "dados/autores.db", "dados/autores.hash_d.db", "dados/autores.hash_c.db",
                    "dados/livros.db", "dados/livros.hash_d.db", "dados/livros.hash_c.db",
                    "dados/livros_isbn.hash_d.db", "dados/livros_isbn.hash_c.db",
                    "dados/livros_categorias.btree.db"
            };

            for (String file : filesToDelete) {
                File f = new File(file);
                if (f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void criarCategoriasExemplo(ArquivoCategorias arqCategorias) throws Exception {
        String[] nomesCategorias = {
                "Ficção Científica",
                "Fantasia",
                "Romance",
                "História",
                "Terror",
                "Biografia",
                "Aventura",
                "Policial",
                "Autoajuda"
        };

        for (String nome : nomesCategorias) {
            arqCategorias.create(new Categoria(nome));
        }
    }


    private static void criarAutoresExemplo(ArquivoAutores arqAutores) throws Exception {
        String[] nomesAutores = {
                "J.K. Rowling",
                "George R.R. Martin",
                "Jane Austen",
                "Stephen King",
                "Agatha Christie",
                "Paulo Coelho",
                "Joanne Harris",
                "Arthur Conan Doyle",
                "Ernest Hemingway"
        };

        for (String nome : nomesAutores) {
            arqAutores.create(new Autor(nome));
        }
    }


    private static void criarLivrosExemplo(ArquivoLivros arqLivros) throws Exception {
        Livro[] livros = {
                new Livro("9788563560278", "Harry Potter e a Pedra Filosofal", 29.90F, 1),
                new Livro("9788584290483", "Game of Thrones", 45.50F, 2),
                new Livro("9786559790005", "Orgulho e Preconceito", 19.99F, 3),
                new Livro("9788582714911", "It", 39.99F, 1),
                new Livro("9786587150062", "O Alquimista", 24.90F, 1),
                new Livro("9788567097353", "Cem Anos de Solidão", 34.50F, 3),
                new Livro("9788532531433", "Sherlock Holmes: Um Estudo em Vermelho", 15.00F, 2),
                new Livro("9788575427303", "O Velho e o Mar", 28.80F, 1)
        };

        for (Livro livro : livros) {
            arqLivros.create(livro);
        }
    }

}
