import java.util.Scanner;

import entidades.Autor;
import arquivos.ArquivoAutores;

public class MenuAutores {
    private static Scanner console = new Scanner(System.in);
    private ArquivoAutores arqAutores;

    public MenuAutores() {
        try {
            arqAutores = new ArquivoAutores();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o arquivo de autores.");
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
            arqAutores.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar o arquivo de autores.");
            e.printStackTrace();
        }
    }

    private void exibirMenu() {
        System.out.println("\n\n\nLivros - AEDs-III");
        System.out.println("------------");
        System.out.println("\n> Início > Autores");
        System.out.println("\n1) Incluir autor");
        System.out.println("2) Buscar autor");
        System.out.println("3) Alterar autor");
        System.out.println("4) Excluir autor");
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
                incluirAutor();
                break;
            case 2:
                buscarAutor();
                break;
            case 3:
                alterarAutor();
                break;
            case 4:
                excluirAutor();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private void incluirAutor() {
        try {
            Autor novoAutor = leAutor();
            if (novoAutor.getNome().isEmpty()) {
                System.out.println("Dados incompletos. Preencha todos os campos.");
                return;
            }

            System.out.print("Confirma inclusão do autor (S/N)? ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                arqAutores.create(novoAutor);
                System.out.println("\nAutor armazenado!");
            } else {
                System.out.println("\nInclusão cancelada!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir o autor.");
            e.printStackTrace();
        }
    }

    private Autor leAutor() throws Exception {
        System.out.print("\nNome: ");
        String nome = console.nextLine();
        return new Autor(nome);
    }

    private void buscarAutor() {
        try {
            Autor[] autores = arqAutores.readAll();
            if (autores.length == 0) {
                System.out.println("\nNão há autores cadastrados.");
                return;
            }

            System.out.println("\nAutores\n-------");
            for (int i = 0; i < autores.length; i++) {
                System.out.println((i + 1) + ": " + autores[i].getNome());
            }

            System.out.print("\nAutor a exibir (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < autores.length) {
                mostraAutor(autores[indice]);
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar autores.");
            e.printStackTrace();
        }
    }

    private void alterarAutor() {
        try {
            Autor[] autores = arqAutores.readAll();
            if (autores.length == 0) {
                System.out.println("\nNão há autores cadastrados para alteração.");
                return;
            }

            System.out.println("\nAutores\n-------");
            for (int i = 0; i < autores.length; i++) {
                System.out.println((i + 1) + ": " + autores[i].getNome());
            }

            System.out.print("\nAutor a alterar (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < autores.length) {
                Autor autorSelecionado = autores[indice];
                mostraAutor(autorSelecionado);

                System.out.println("\nDigite os novos dados.\nDeixe em branco para manter o valor atual.");
                Autor autorAtualizado = leAutor();

                if (!autorAtualizado.getNome().isEmpty()) {
                    autorSelecionado.setNome(autorAtualizado.getNome());

                    System.out.print("Confirma alteração do autor (S/N)? ");
                    char resp = console.nextLine().charAt(0);
                    if (resp == 'S' || resp == 's') {
                        if (arqAutores.update(autorSelecionado)) {
                            System.out.println("Autor alterado com sucesso!");
                        } else {
                            System.out.println("Erro ao alterar o autor.");
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
            System.out.println("Erro ao alterar o autor.");
            e.printStackTrace();
        }
    }

    private void excluirAutor() {
        try {
            Autor[] autores = arqAutores.readAll();
            if (autores.length == 0) {
                System.out.println("\nNão há autores cadastrados para exclusão.");
                return;
            }

            System.out.println("\nAutores\n-------");
            for (int i = 0; i < autores.length; i++) {
                System.out.println((i + 1) + ": " + autores[i].getNome());
            }

            System.out.print("\nAutor a excluir (0 para cancelar): ");
            int indice = Integer.parseInt(console.nextLine()) - 1;
            if (indice >= 0 && indice < autores.length) {
                Autor autorSelecionado = autores[indice];
                mostraAutor(autorSelecionado);

                System.out.print("Confirma exclusão do autor (S/N)? ");
                char resp = console.nextLine().charAt(0);
                if (resp == 'S' || resp == 's') {
                    if (arqAutores.delete(autorSelecionado.getID())) {
                        System.out.println("Autor excluído com sucesso!");
                    } else {
                        System.out.println("Erro ao excluir o autor.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else if (indice != -1) {
                System.out.println("Índice inválido!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir o autor.");
            e.printStackTrace();
        }
    }

    private void mostraAutor(Autor a) {
        System.out.println("\nNome: " + a.getNome());
    }
}
