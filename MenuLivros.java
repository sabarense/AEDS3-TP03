import java.text.NumberFormat;
import java.util.Scanner;

import arquivos.ArquivoCategorias;
import arquivos.ArquivoLivros;
import entidades.Categoria;
import entidades.Livro;

public class MenuLivros {

    private static final Scanner console = new Scanner(System.in);
    private final ArquivoLivros arqLivros;
    private final ArquivoCategorias arqCategorias;

    public MenuLivros() throws Exception {
        arqLivros = new ArquivoLivros();
        arqCategorias = new ArquivoCategorias();
    }

    public void menu() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            executarOpcao(opcao);
        } while (opcao != 0);

        fecharArquivos();
    }

    private void exibirMenu() {
        System.out.println("\n\n\nLivros - AEDs-III");
        System.out.println("------------");
        System.out.println("\n> Início > Livros");
        System.out.println("\n1) Incluir livro");
        System.out.println("2) Buscar livro");
        System.out.println("3) Alterar livro");
        System.out.println("4) Excluir livro");
        System.out.println("\n0) Retornar ao menu anterior");
    }

    private int lerOpcao() {
        int opcao;
        System.out.print("\nOpção: ");
        try {
            opcao = Integer.parseInt(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }
        return opcao;
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                incluirLivro();
                break;
            case 2:
                buscarLivro();
                break;
            case 3:
                alterarLivro();
                break;
            case 4:
                excluirLivro();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public Livro leLivro() {
        try {
            System.out.print("\nISBN: ");
            String isbn = console.nextLine();
            System.out.print("Título: ");
            String titulo = console.nextLine();
            System.out.print("Preço: R$ ");
            float preco = Float.parseFloat(console.nextLine());
            System.out.println("Categorias:");
            listarCategorias();
            System.out.print("Categoria: ");
            int categoriaId = Integer.parseInt(console.nextLine());
            return new Livro(isbn, titulo, preco, categoriaId);
        } catch (Exception e) {
            System.out.println("Erro ao ler livro: " + e.getMessage());
            return null;
        }
    }

    private void listarCategorias() {
        try {
            Categoria[] categorias = arqCategorias.readAll();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ": " + categorias[i].getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
    }

    public void mostraLivro(Livro livro) {
        try {
            Categoria categoria = arqCategorias.read(livro.getIdCategoria());
            String nomeCategoria = (categoria != null) ? categoria.getNome() : "Categoria inválida";
            System.out.println("\nISBN: " + livro.getIsbn() +
                    "\nTítulo: " + livro.getTitulo() +
                    "\nPreço: " + NumberFormat.getCurrencyInstance().format(livro.getPreco()) +
                    "\nCategoria: " + nomeCategoria);
        } catch (Exception e) {
            System.out.println("Erro ao mostrar livro: " + e.getMessage());
        }
    }

    private void incluirLivro() {
        try {
            Livro novoLivro = leLivro();
            if (novoLivro == null) {
                System.out.println("Dados inválidos. Livro não foi cadastrado.");
                return;
            }

            if (arqLivros.readISBN(novoLivro.getIsbn()) != null) {
                System.out.println("Já existe um livro com esse ISBN.");
                return;
            }

            System.out.print("Confirma inclusão do livro (S/N)? ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                arqLivros.create(novoLivro);
                System.out.println("\nLivro armazenado!");
            } else {
                System.out.println("\nInclusão cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir livro: " + e.getMessage());
        }
    }

    private void buscarLivro() {
        try {
            System.out.print("\nISBN: ");
            String isbn = console.nextLine();
            if (isbn.isEmpty()) {
                return;
            }

            Livro livro = arqLivros.readISBN(isbn);
            if (livro == null) {
                System.out.println("Livro não encontrado.");
            } else {
                mostraLivro(livro);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void alterarLivro() {
        try {
            System.out.print("\nISBN: ");
            String isbn = console.nextLine();
            if (isbn.isEmpty()) {
                return;
            }

            Livro livro = arqLivros.readISBN(isbn);
            if (livro == null) {
                System.out.println("Livro não encontrado.");
                return;
            }

            mostraLivro(livro);
            System.out.println("\nDigite os novos dados.\nDeixe em branco os que não deseja alterar.");
            Livro novoLivro = leLivro();

            if (novoLivro != null) {
                if (!novoLivro.getIsbn().isEmpty()) {
                    livro.setIsbn(novoLivro.getIsbn());
                }
                if (!novoLivro.getTitulo().isEmpty()) {
                    livro.setTitulo(novoLivro.getTitulo());
                }
                if (novoLivro.getPreco() >= 0) {
                    livro.setPreco(novoLivro.getPreco());
                }
                if (novoLivro.getIdCategoria() >= 0) {
                    livro.setIdCategoria(novoLivro.getIdCategoria());
                }

                System.out.print("Confirma alteração do livro (S/N)? ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arqLivros.update(livro)) {
                        System.out.println("\nLivro alterado!");
                    } else {
                        System.out.println("\nErro na alteração do livro!");
                    }
                } else {
                    System.out.println("\nAlteração cancelada!");
                }
            } else {
                System.out.println("\nDados inválidos. Alteração cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar livro: " + e.getMessage());
        }
    }

    private void excluirLivro() {
        try {
            System.out.print("\nISBN: ");
            String isbn = console.nextLine();
            if (isbn.isEmpty()) {
                return;
            }

            Livro livro = arqLivros.readISBN(isbn);
            if (livro == null) {
                System.out.println("Livro não encontrado.");
                return;
            }

            mostraLivro(livro);
            System.out.print("Confirma exclusão do livro (S/N)? ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                if (arqLivros.delete(livro.getID())) {
                    System.out.println("\nLivro excluído!");
                } else {
                    System.out.println("\nErro na exclusão do livro!");
                }
            } else {
                System.out.println("\nExclusão cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
        }
    }

    private void fecharArquivos() {
        try {
            arqLivros.close();
            arqCategorias.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar arquivos: " + e.getMessage());
        }
    }

}
