# Board Game Engine — LPOO 2026.2

Uma **Game Engine orientada a objetos em Java** capaz de interpretar arquivos de configuração JSON (utilizando **Google Gson** via **Maven**) e executar diferentes jogos de tabuleiro no terminal, sem necessidade de alterar o código-fonte da Engine para adicionar novos jogos baseados nas regras suportadas.

---

## 📁 Estrutura do Projeto

```text
board-game-engine-lpoo-26.2/
├── pom.xml                          # Configuração Maven (dependência Google Gson)
├── games/
│   ├── connect_four.json            # Connect Four (6x7, Gravidade, alinhar 4)
│   ├── gomoku_3_jogadores.json      # Gomoku 5x5 para 3 jogadores (Posição livre, alinhar 4)
│   └── jogo_da_velha.json           # Jogo da Velha (3x3, Posição livre, alinhar 3)
├── src/
│   ├── Main.java                    # Menu interativo e carregamento de jogos
│   ├── config/
│   │   ├── GameConfig.java          # Modelo de configuração mapeado pelo Gson
│   │   └── GameLoader.java          # Carregador (Gson), validador e fábrica polimórfica
│   ├── engine/
│   │   ├── Game.java                # Motor principal da partida (start, changePlayer, currentPlayer)
│   │   ├── GameState.java           # Enum de estado (IN_PROGRESS, WIN, DRAW)
│   │   └── TurnManager.java         # Controle circular de turnos (List<Player>, changePlayer)
│   ├── exceptions/
│   │   ├── GameException.java                 # Exceção base de domínio (Herança)
│   │   ├── InvalidConfigurationException.java # Configuração JSON inválida ou regra inexistente
│   │   ├── InvalidMoveException.java          # Jogada viola regra de posicionamento
│   │   └── InvalidPositionException.java      # Coordenada fora dos limites do tabuleiro
│   ├── model/
│   │   ├── Board.java               # Tabuleiro genérico encapsulado (Piece[][] board, moveCount, makeMove, isFull)
│   │   ├── Move.java                # Intenção de jogada (Player + Position)
│   │   ├── Piece.java               # Peça posicionada no tabuleiro
│   │   ├── Player.java              # Jogador participante (id + symbol)
│   │   └── Position.java            # Coordenada (row, column)
│   ├── rules/
│   │   ├── PlacementRule.java               # Interface para regras de posicionamento (makeMove)
│   │   ├── EmptyPositionPlacementRule.java  # Posicionamento em casa livre ("EMPTY_POSITION")
│   │   ├── GravityPlacementRule.java        # Posicionamento por gravidade ("GRAVITY")
│   │   ├── WinCondition.java                # Interface para condições de vitória (winCondition)
│   │   └── AlignmentWinCondition.java       # Vitória por alinhamento de N peças ("ALIGNMENT")
│   └── ui/
│       └── ConsoleRenderer.java     # Renderização dinâmica do tabuleiro no terminal (display)
└── docs/
    ├── class_diagram_checkpoint1.drawio.svg
    └── class_diagram_checkpoint2.md # Diagrama de classes atualizado (Checkpoint 2)
```

---

## 🚀 Como Compilar e Executar

É necessário ter o **Java (JDK 17+)** e o **Maven** instalados (ou usar uma IDE como IntelliJ IDEA / VS Code / Eclipse que já traz o Maven integrado).

### Opção 1: Pela IDE (IntelliJ IDEA / VS Code / Eclipse)
1. Abra o projeto na IDE.
2. Execute a classe `src/Main.java` diretamente pelo botão **Run**.

### Opção 2: Pelo Terminal (Maven)
1. Na raiz do projeto, compile e execute com:
   ```bash
   mvn compile exec:java
   ```
2. **(Opcional)** Para iniciar diretamente um arquivo de configuração específico via linha de comando:
   ```bash
   mvn compile exec:java -Dexec.args="games/connect_four.json"
   ```

---

## 🎮 Jogos Disponíveis (`games/`)

Ao iniciar a Engine, os arquivos `.json` presentes na pasta `games/` são listados automaticamente:

1. **Connect Four (`games/connect_four.json`)**
   - Tabuleiro `6x7`, 2 jogadores (`V` e `A`).
   - Posicionamento `"GRAVITY"`: o jogador informa apenas a coluna (`1` a `7`) e a peça cai até a linha vazia mais baixa.
   - Vitória `"ALIGNMENT"` com `quantity: 4`.

2. **Gomoku 5x5 — 3 Jogadores (`games/gomoku_3_jogadores.json`)**
   - Tabuleiro `5x5`, 3 jogadores (`X`, `O` e `#`).
   - Posicionamento `"EMPTY_POSITION"`: o jogador informa linha e coluna (`1` a `5`).
   - Vitória `"ALIGNMENT"` com `quantity: 4`.

3. **Jogo da Velha (`games/jogo_da_velha.json`)**
   - Tabuleiro `3x3`, 2 jogadores (`X` e `O`).
   - Posicionamento `"EMPTY_POSITION"`: o jogador informa linha e coluna (`1` a `3`).
   - Vitória `"ALIGNMENT"` com `quantity: 3`.

---

## ⚙️ Formato do Arquivo de Configuração JSON

Qualquer novo jogo composto pelas regras existentes pode ser adicionado criando um arquivo `.json` na pasta `games/`:

```json
{
  "game": "Connect Four",
  "board": {
    "rows": 6,
    "columns": 7
  },
  "players": {
    "count": 2,
    "symbols": ["V", "A"]
  },
  "rules": {
    "placement": "GRAVITY",
    "winCondition": {
      "type": "ALIGNMENT",
      "quantity": 4
    }
  }
}
```

### Regras Suportadas
- **`rules.placement`**:
  - `"EMPTY_POSITION"`: Posiciona a peça na coordenada `(linha, coluna)` informada, desde que esteja vazia.
  - `"GRAVITY"`: Posiciona a peça na linha vazia mais baixa da coluna informada.
- **`rules.winCondition.type`**:
  - `"ALIGNMENT"`: Vence o jogador que alinhar `quantity` peças consecutivas na horizontal, vertical ou diagonal.

---

## 🏛️ Conceitos de POO Aplicados (Checkpoint 2)

- **Encapsulamento:** O estado interno do tabuleiro (`Piece[][] board` em `Board`) é protegido; modificações só ocorrem após validação de limites e regras.
- **Interfaces e Polimorfismo:** `Game` opera sobre as interfaces `PlacementRule` e `WinCondition`, sem estruturas condicionais (`if (game.equals(...))`) para distinguir jogos.
- **Composição:** `Game` é composto por `Board`, `TurnManager`, `PlacementRule`, `WinCondition` e `ConsoleRenderer`.
- **Herança e Tratamento de Exceções:** Hierarquia `GameException` $\rightarrow$ `InvalidPositionException`, `InvalidMoveException` e `InvalidConfigurationException`, evitando encerramento abrupto com stack trace.
- **Coleções:** Uso de `List<Player>` em `TurnManager` e `Map` para registro dinâmico de regras em `GameLoader`.

Consulte o diagrama de classes completo em [`docs/class_diagram_checkpoint2.svg`](docs/class_diagram_checkpoint2.svg).
