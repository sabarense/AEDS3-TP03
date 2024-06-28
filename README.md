Terceiro trabalho prático da disciplina de Algoritmos e Estruturas de Dados III

## RELATÓRIO ##

### 1. Introdução ###
O trabalho consiste em implementar um sistema de backups de arquivos que utiliza o algoritmo LZW para compactação e descompactação dos arquivos. O sistema deve permitir que o usuário escolha a versão do arquivo a ser recuperada
### 2. Dificuldades ###
Tivemos um pouco de dificuldade na implementação do backups dos arquivos, porém conseguimos resolver após algumas tentativas. 
### 3. Métodos ###

    public static void listarBackups()
    Lista todos os backups disponíveis no diretório de backups e permite ao usuário escolher um para recuperar.

    public static boolean recuperarBackup(File path)
    Recupera um backup a partir do caminho especificado, substituindo os dados atuais pelos do backup e excluindo os arquivos existentes antes de restaurar os novos.
    
    public static boolean criarBackup(File pastaBackup)
    Cria um backup dos arquivos de dados no diretório especificado, compactando-os usando o algoritmo LZW.
    
    private static void escreverBackup(String arquivo, String origem, String destino) throws Exception
    Lê um arquivo do diretório de origem, codifica os dados usando LZW, e escreve o arquivo codificado no diretório de destino.
    
    private static void escreverRecuperacao(String arquivo, String origem, String destino) throws Exception
    Lê um arquivo codificado do diretório de origem, decodifica os dados usando LZW, e escreve o arquivo decodificado no diretório de destino.
    
    private static byte[] lerArquivo(String caminho) throws Exception
    Lê um arquivo do caminho especificado e retorna seu conteúdo como um array de bytes.
    
    private static void escreverArquivo(byte[] byteArray, String caminho) throws Exception
    Escreve um array de bytes no caminho especificado.
    
    public static void excluirPasta(File pasta)
    Exclui uma pasta e todos os seus arquivos e subdiretórios.
    
    public static void excluirArquivos(File pasta)
    Exclui todos os arquivos e subdiretórios dentro de uma pasta especificada.

### 4. Checklist ###

- Há uma rotina de compactação usando o algoritmo LZW para fazer backups dos arquivos? Sim, há uma rotina de compactação usando o algoritmo LZW para fazer backups dos arquivos.

- Há uma rotina de descompactação usando o algoritmo LZW para recuperação dos arquivos? 
Sim, há uma rotina de descompactação usando o algoritmo LZW para recuperação dos arquivos.
- O usuário pode escolher a versão a recuperar? 
Sim, o usuário pode escolher a versão a recuperar.
- Qual foi a taxa de compressão alcançada por esse backups? 1.5 - 1.7
- O trabalho está funcionando corretamente? Sim, o trabalho está funcionando corretamente.
- O trabalho está completo? Sim, o trabalho está completo.
- O trabalho é original e não a cópia de um trabalho de um colega? Sim, o trabalho é original e desenvolvido por Lívia Câmara, Sophia Carrazza e Yan Sabarense.
