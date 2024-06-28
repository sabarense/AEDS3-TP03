import java.util.Scanner;
import java.util.ArrayList;

import aeds3.ArvoreBMais;
import aeds3.ParIntInt;
import arquivos.ArquivoCategorias;
import arquivos.ArquivoLivros;
import entidades.Categoria;
import entidades.Livro;

public class MenuCategorias {

    private static Scanner console = new Scanner(System.in);
    private ArquivoCategorias arqCategorias;
    private ArquivoLivros arqLivros;
    private ArvoreBMais<ParIntInt> relLivrosDaCategoria;

    public MenuCategorias() {
        try {
            arqCategorias = new ArquivoCategorias();
            arqLivros = new ArquivoLivros();
            relLivrosDaCategoria = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 4, "dados/livros_categorias.btree.db");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void menu() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            executarOpcao(opcao);
        } while (opcao != 0);

        try {
            arqCategorias.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exibirMenu() {
        System.out.println("\n\n\nLivros - AEDs-III");
        System.out.println("------------");
        System.out.println("\n> Início > Categorias");
        System.out.println("\n1) Incluir categoria");
        System.out.println("2) Buscar categoria");
        System.out.println("3) Alterar categoria");
        System.out.println("4) Excluir categoria");
        System.out.println("5) Mostrar os livros da categoria");
        System.out.println("\n0) Retornar ao menu anterior");
    }

    private int lerOpcao() {
        int opcao;
        System.out.print("\nOpção: ");
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }
        return opcao;
    }

    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                incluirCategoria();
                break;
            case 2:
                buscarCategoria();
                break;
            case 3:
                alterarCategoria();
                break;
            case 4:
                excluirCategoria();
                break;
            case 5:
                mostrarLivros();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private Categoria leCategoria() throws Exception {
        System.out.print("\nNome: ");
        String nome = console.nextLine();
        return new Categoria(nome);
    }

    private void mostraCategoria(Categoria c) {
        System.out.println("\nNome: " + c.getNome());
    }

    private void incluirCategoria() {
        try {
            Categoria novaCategoria;
            do {
                novaCategoria = leCategoria();
                if (novaCategoria.getNome().isEmpty()) {
                    System.out.println("Dados incompletos. Preencha todos os campos.");
                }
            } while (novaCategoria.getNome().isEmpty());

            System.out.print("Confirma inclusão da categoria (S/N)? ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                arqCategorias.create(novaCategoria);
                System.out.println("\nCategoria armazenada!");
            } else {
                System.out.println("\nInclusão cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void buscarCategoria() {
        try {
            Categoria[] categorias = arqCategorias.readAll();
            if (categorias.length == 0) {
                System.out.println("\nNão há categorias cadastradas.");
                return;
            }

            System.out.println("\nCategorias\n----------");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ": " + categorias[i].getNome());
            }

            System.out.print("\nCategoria a exibir (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < categorias.length) {
                mostraCategoria(categorias[indice]);
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void alterarCategoria() {
        try {
            Categoria[] categorias = arqCategorias.readAll();
            if (categorias.length == 0) {
                System.out.println("\nNão há categorias cadastradas para alteração.");
                return;
            }

            System.out.println("\nCategorias\n----------");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ": " + categorias[i].getNome());
            }

            System.out.print("\nCategoria a alterar (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < categorias.length) {
                Categoria categoriaSelecionada = categorias[indice];
                mostraCategoria(categoriaSelecionada);

                System.out.println("\nDigite os novos dados.\nDeixe em branco para manter o valor atual.");
                Categoria novaCategoria = leCategoria();

                if (!novaCategoria.getNome().isEmpty()) {
                    categoriaSelecionada.setNome(novaCategoria.getNome());

                    System.out.print("Confirma alteração da categoria (S/N)? ");
                    char resp = console.nextLine().charAt(0);
                    if (resp == 'S' || resp == 's') {
                        if (arqCategorias.update(categoriaSelecionada)) {
                            System.out.println("Categoria alterada com sucesso!");
                        } else {
                            System.out.println("Erro ao alterar a categoria.");
                        }
                    } else {
                        System.out.println("Alteração cancelada.");
                    }
                } else {
                    System.out.println("Nenhum dado novo fornecido. Alteração cancelada.");
                }
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void excluirCategoria() {
        try {
            Categoria[] categorias = arqCategorias.readAll();
            if (categorias.length == 0) {
                System.out.println("\nNão há categorias cadastradas para exclusão.");
                return;
            }

            System.out.println("\nCategorias\n----------");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ": " + categorias[i].getNome());
            }

            System.out.print("\nCategoria a excluir (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < categorias.length) {
                Categoria categoriaSelecionada = categorias[indice];
                mostraCategoria(categoriaSelecionada);

                ArrayList<ParIntInt> livrosDaCategoria = relLivrosDaCategoria.read(new ParIntInt(categoriaSelecionada.getID(), -1));
                if (livrosDaCategoria.size() > 0) {
                    System.out.println("Esta categoria possui livros associados e não pode ser excluída.");
                    return;
                }

                System.out.print("Confirma exclusão da categoria (S/N)? ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arqCategorias.delete(categoriaSelecionada.getID())) {
                        System.out.println("Categoria excluída com sucesso!");
                    } else {
                        System.out.println("Erro ao excluir a categoria.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarLivros() {
        try {
            Categoria[] categorias = arqCategorias.readAll();
            if (categorias.length == 0) {
                System.out.println("\nNão há categorias cadastradas para mostrar os livros.");
                return;
            }

            System.out.println("\nCategorias\n----------");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ": " + categorias[i].getNome());
            }

            System.out.print("\nCategoria a exibir os livros (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < categorias.length) {
                Categoria categoriaSelecionada = categorias[indice];
                mostraCategoria(categoriaSelecionada);

                ArrayList<ParIntInt> livrosDaCategoria = relLivrosDaCategoria.read(new ParIntInt(categoriaSelecionada.getID(), -1));
                if (livrosDaCategoria.isEmpty()) {
                    System.out.println("Não há livros associados a esta categoria.");
                } else {
                    System.out.println("Livros:");
                    for (ParIntInt par : livrosDaCategoria) {
                        Livro livro = arqLivros.read(par.get2());
                        System.out.println("- " + livro.getIsbn() + " - " + livro.getTitulo());
                    }
                }
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao mostrar livros da categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
