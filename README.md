# board-game-engine-lpoo-26.2

Bem-vindo ao projeto do Jogo da Velha feito em Java! Este repositório contém um mini motor para jogos de tabuleiro.

## 🚀 Como Executar o Programa

Para compilar e rodar o jogo, é necessário ter o **Java (JDK)** instalado na sua máquina. Siga os passos abaixo:

1. Abra o terminal (ou prompt de comando) e navegue até a pasta `src` do projeto.
2. Compile todos os arquivos Java utilizando o comando:
   ```bash
   javac *.java
   ```
3. Inicie o jogo executando a classe principal:
   ```bash
   java Main
   ```

*(Se você estiver utilizando uma IDE como VS Code, Eclipse ou IntelliJ, basta abrir o projeto e executar o arquivo `Main.java` diretamente pelo botão de "Run" da ferramenta).*

## 🎮 Como Jogar

1. **Menu Principal**: Ao iniciar, você pode digitar `1` para começar um "Novo Jogo" ou `2` para "Sair".
2. **Tabuleiro**: O tabuleiro é uma grade 3x3. As linhas e colunas são numeradas de `0` a `2`.
   - `0, 0` representa o canto superior esquerdo.
   - `2, 2` representa o canto inferior direito.
3. **Turnos**: O Jogador 1 (X) sempre começa. O sistema irá pedir para você digitar a **linha** e, em seguida, a **coluna**.
4. **Condição de Vitória**: O primeiro a conseguir formar uma linha, coluna ou diagonal completa com o seu símbolo (`X` ou `O`) vence a partida.
5. **Fim de Jogo**: Quando o jogo acabar (vitória ou empate), você será levado novamente ao menu principal.

---

*Nota: O este README foi gerado com auxílio de IA (Google Gemini 3.1 Pro). O prompt utilizado foi "Documente o funcionamento e como executar o projeto e iniciar o jogo".*