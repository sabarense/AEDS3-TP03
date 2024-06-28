import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Backup {

    private static final String DIRETORIO_BACKUPS = "backups/";
    private static final String DIRETORIO_DADOS = "dados/";

    private File pasta; // Variável de instância para a pasta de backup

    public Backup() {
        // Construtor padrão
    }

    public Backup(String path) {
        pasta = new File(path);
    }

    public static void listarBackups() {
        Scanner console = new Scanner(System.in);
        File pasta = new File(DIRETORIO_BACKUPS);
        File[] arquivos = pasta.listFiles();

        int escolha = 0;
        do {
            System.out.println("Escolha o backup desejado:");
            for (int i = 0; i < arquivos.length; i++) {
                System.out.println((i + 1) + ") " + arquivos[i].getName());
            }
            escolha = Integer.valueOf(console.nextLine());
            if (escolha < 1 || escolha > arquivos.length) {
                System.out.println("Escolha uma opção válida");
            }
        } while (escolha < 1 || escolha > arquivos.length);

        if (escolha != 0) {
            recuperarBackup(arquivos[escolha - 1]);
        }
    }

    public static boolean recuperarBackup(File path) {
        boolean sucesso = false;
        try {

            criarBackup(path);
            excluirArquivos(new File(DIRETORIO_DADOS));

            String[] arquivos = {
                    "autores.db", "autores.hash_c.db", "autores.hash_d.db",
                    "categorias.db", "categorias.hash_c.db", "categorias.hash_d.db",
                    "livros.db", "livros.hash_c.db", "livros.hash_d.db",
                    "livros_categorias.btree.db", "livros_isbn.hash_c.db", "livros_isbn.hash_d.db"
            };

            for (String arquivo : arquivos) {
                escreverRecuperacao(arquivo, path.toString() + "/", DIRETORIO_DADOS);
            }

            System.out.println("Backup recuperado com sucesso!");
            sucesso = true;

        } catch (Exception e) {
            System.out.println("Erro ao recuperar o backup: " + e.getMessage());
            e.printStackTrace();
        }
        return sucesso;
    }

    public static boolean  criarBackup(File pastaBackup) {
        boolean sucesso = false;

        try {
            String[] arquivos = {
                    "autores.db", "autores.hash_c.db", "autores.hash_d.db",
                    "categorias.db", "categorias.hash_c.db", "categorias.hash_d.db",
                    "livros.db", "livros.hash_c.db", "livros.hash_d.db",
                    "livros_categorias.btree.db", "livros_isbn.hash_c.db", "livros_isbn.hash_d.db"
            };

            for (String arquivo : arquivos) {
                escreverBackup(arquivo, DIRETORIO_DADOS, pastaBackup.getPath());
            }

            sucesso = true;

        } catch (Exception e) {
            excluirPasta(pastaBackup);
            System.out.println("Erro ao criar o backup: " + e.getMessage());
            e.printStackTrace();
        }
        return sucesso;
    }

    private static void escreverBackup(String arquivo, String origem, String destino) throws Exception {
        byte[] byteArray = lerArquivo(origem + arquivo);
        byteArray = LZW.codifica(byteArray);
        escreverArquivo(byteArray, destino + "/" + arquivo);
    }

    private static void escreverRecuperacao(String arquivo, String origem, String destino) throws Exception {
        byte[] byteArray = lerArquivo(origem + arquivo);
        byteArray = LZW.decodifica(byteArray);
        escreverArquivo(byteArray, destino + "/" + arquivo);
    }

    private static byte[] lerArquivo(String caminho) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(caminho)) {
            return fileInputStream.readAllBytes();
        }
    }

    private static void escreverArquivo(byte[] byteArray, String caminho) throws Exception {
        try (FileOutputStream fileOutputStream = new FileOutputStream(caminho)) {
            fileOutputStream.write(byteArray);
        }
    }

    public static void excluirPasta(File pasta) {
        excluirArquivos(pasta);
        pasta.delete();
    }

    public static void excluirArquivos(File pasta) {
        if (pasta.exists()) {
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isDirectory()) {
                        excluirPasta(arquivo);
                    } else {
                        arquivo.delete();
                    }
                }
            }
        }
    }
}
