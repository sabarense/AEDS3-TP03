Terceiro trabalho prático da disciplina de Algoritmos e Estruturas de Dados III

## RELATÓRIO ##

### 1. Introdução ###
O trabalho consiste em implementar um sistema de backups de arquivos que utiliza o algoritmo LZW para compactação e descompactação dos arquivos. O sistema deve permitir que o usuário escolha a versão do arquivo a ser recuperada
### 2. Dificuldades ###
Tivemos um pouco de dificuldade na implementação do backups dos arquivos, porém conseguimos resolver após algumas tentativas. 
### 3. Classes e Métodos ###
    public static byte[] decodifica(byte[] msgCodificada): Recebe um array de bytes codificados, decodifica usando o algoritmo LZW e retorna o array de bytes original.

    public static byte[] codifica(byte[] msgOriginal): Recebe um array de bytes original, codifica usando o algoritmo LZW e retorna o array de bytes codificado.

    public static void criarBackup(String caminhoArquivo): Recebe o caminho do arquivo a ser feito o backups, compacta o arquivo e salva o arquivo compactado no mesmo diretório do arquivo original.

    public static byte[] recuperarBackup(String caminhoArquivo): Recebe o caminho do arquivo compactado, descompacta o arquivo e retorna o array de bytes original.

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
