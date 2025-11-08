# 🛡️ Jogo de Batalha por Turnos em Kotlin

---

**Criado por:** Victor Alexandre Lima Ribeiro ([@vyctor922](https://github.com/vyctor922))
**Disciplina:** IARTES — Programação de Dispositivos Móveis com Android (2025)
**Professor:** Lucas Lima Rodrigues ([@lucaslrodri](https://github.com/lucaslrodri))

---

## 🎮 Sobre o Projeto

Este projeto implementa um **jogo de batalha por turnos em console**, desenvolvido em **Kotlin**, onde **dois jogadores** escolhem suas classes de personagens e se enfrentam em **melhores de três rounds**.

O jogo combina **sorte, estratégia e mecânicas específicas por classe**, proporcionando partidas dinâmicas e imprevisíveis.

---

## ⚔️ Classes de Personagem

Cada personagem herda da classe base `Personagem`, que define os atributos e comportamentos comuns: **nome**, **HP**, **ataque**, e métodos de combate.

### 🧱 Escudeiro

* **HP:** 140  **Ataque:** 30
* **Habilidade:** Chance de **bloquear total ou parcialmente** o dano recebido.

  * 15% de chance de bloquear todo o dano.
  * 35% de chance de receber apenas metade.

### 🔮 Mago

* **HP:** 80  **Ataque:** 40
* **Habilidade:** Chance de causar **dano crítico** (x2).

  * 25% de chance de crítico.

### 🏹 Arqueiro

* **HP:** 100  **Ataque:** 35
* **Habilidade:** Chance de **atacar duas vezes** no mesmo turno.

  * 30% de chance de duplo ataque.

### 💀 Berserker

* **HP:** 130  **Ataque:** 25
* **Habilidade:** Quanto menor o HP, **maior o dano causado**.

  * Dano adicional aumenta conforme a vida diminui.
  * Sofre **auto-dano (10%)** após atacar.

---

## 🧩 Mecânica do Jogo

1. Cada jogador insere:

   * Seu nome;
   * O nome do seu personagem;
   * E escolhe uma das quatro classes disponíveis.
2. O jogo é dividido em **rounds**, e vence quem fizer **2 vitórias** primeiro (melhor de 3).
3. A cada round:

   * Ambos os personagens **restauram o HP**.
   * Os jogadores podem **optar por trocar de classe** antes do próximo round.
4. O jogo termina com o **campeão da partida** sendo anunciado.

---

## 🕹️ Exemplo de Execução

```
=== JOGO DE BATALHA POR TURNOS ===
Nome do Jogador 1: Alice
Nome do personagem do Jogador 1: Arwen
Escolha a classe:
1 - Escudeiro
2 - Mago
3 - Arqueiro
4 - Berserker
Opção: 3

Nome do Jogador 2: Bob
Nome do personagem do Jogador 2: Thorin
Opção: 1

===== ROUND 1 =====
Arwen (Arqueiro) vs Thorin (Escudeiro)

Arwen dispara uma flecha (1º ataque) causando 35 de dano!
Thorin bloqueou parte do dano! Recebeu apenas 17.
...
Vencedor do Round 1: Alice !!!
Placar: Alice 1 x 0 Bob
```

---

## 🛠️ Estrutura do Código

```
├── Personagem (classe base)
│   ├── Escudeiro
│   ├── Mago
│   ├── Arqueiro
│   └── Berserker
│
├── Batalha (controla os rounds e regras)
│
├── Funções auxiliares
│   └── escolherClasse()
│
└── main()
    ├── Lida com input dos jogadores
    ├── Controla os rounds
    └── Exibe o vencedor final
```

---

## 🧠 Conceitos de Kotlin Utilizados

* **Herança e Polimorfismo** (`open`, `override`)
* **Controle de fluxo** (`when`, `if`, loops)
* **Manipulação de classes e objetos**
* **Leitura de entrada com `readln()`**
* **Uso de `Random` para gerar probabilidades**
* **Encapsulamento e acesso controlado (`private`, `protected`)**
* **Tratamento de lógica de jogo em loops e funções**

---

## 🚀 Como Executar

1. Certifique-se de ter o **Kotlin** instalado em seu ambiente:

   ```bash
   kotlinc -version
   ```
2. Compile o código:

   ```bash
   kotlinc JogoBatalha.kt -include-runtime -d JogoBatalha.jar
   ```
3. Execute o jogo:

   ```bash
   java -jar JogoBatalha.jar
   ```

---

## 💡 Melhorias Futuras (TODO)

* [ ] Implementar tratamento de exceções para entradas inválidas (`try/catch`);
* [ ] Adicionar interface gráfica simples (Android);
* [ ] Implementar sistema de **habilidades especiais** por tempo de recarga;
* [ ] Registrar histórico de batalhas;
* [ ] Adicionar **modo automático** (bot vs bot).

---

## 📄 Licença

Este projeto está licenciado sob os termos da **[Licença MIT](https://opensource.org/licenses/MIT)**.
Sinta-se livre para estudar, modificar e expandir o código, **mantendo os créditos ao autor original**.

Copyright © 2025
**Victor Alexandre Lima Ribeiro (vyctor922)**

---
