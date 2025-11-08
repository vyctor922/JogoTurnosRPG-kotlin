// Criado por Victor Alexandre Lima Ribeiro (vyctor922) - IARTES: Programação de dispositivos móveis com Android (2025)
// Projeto de Jogo de Batalha por Turnos em Kotlin - Professor: Lucas Lima Rodrigues (lucaslrodri)

import kotlin.random.Random
import kotlin.system.exitProcess

// -----------------------------
// CLASSE BASE: Personagem
// -----------------------------
open class Personagem(
    val nome: String,
    private var hp: Int,
    private var ataque: Int
) {
    private val maxHp: Int = hp

    fun getHp(): Int = hp
    fun getAtaque(): Int = ataque
    fun getMaxHp(): Int = maxHp

    protected fun setHp(valor: Int) {
        hp = if (valor < 0) 0 else valor
    }

    open fun restaurarVida() {
        setHp(maxHp)
    }

    open fun atacar(alvo: Personagem) {
        println("$nome ataca ${alvo.nome} causando $ataque de dano!")
        alvo.receberDano(ataque)
    }

    open fun receberDano(dano: Int) {
        setHp(hp - dano)
    }

    fun estaVivo(): Boolean = hp > 1

    override fun toString(): String {
        return "$nome (HP: $hp, ATK: $ataque)"
    }
}

// -----------------------------
// SUBCLASSES: Escudeiro, Mago, Arqueiro, Berserker
// -----------------------------

class Escudeiro(nome: String) : Personagem(nome, hp = 140, ataque = 30) {
    override fun receberDano(dano: Int) {
        val chance = Random.nextInt(0, 100)
        when {
            chance < 15 -> { // 15% de chance de bloquear todo o dano
                println("$nome bloqueou completamente o ataque!")
            }
            chance < 50 -> { // 35% de chance de reduzir o dano pela metade
                val danoReduzido = dano / 2
                println("$nome bloqueou parte do dano! Recebeu apenas $danoReduzido.")
                super.receberDano(danoReduzido)
            }
            else -> super.receberDano(dano)
        }
    }
}

class Mago(nome: String) : Personagem(nome, hp = 80, ataque = 40) {
    override fun atacar(alvo: Personagem) {
        val critico = Random.nextInt(0, 100) < 25 // 25% de chance de crítico
        val dano = if (critico) getAtaque() * 2 else getAtaque()
        val aviso = if (critico) "(DANO CRÍTICO)" else ""
        println("$nome lança um feitiço em ${alvo.nome} causando $dano de dano $aviso")
        alvo.receberDano(dano)
    }
}

class Arqueiro(nome: String) : Personagem(nome, hp = 100, ataque = 35) {
    override fun atacar(alvo: Personagem) {
        val ataques = if (Random.nextInt(0, 100) < 30) 2 else 1 // 30% chance de atacar 2x
        for (i in 1..ataques) {
            println("$nome dispara uma flecha (${i}º ataque) causando ${getAtaque()} de dano!")
            alvo.receberDano(getAtaque())
        }
    }
}

class Berserker(nome: String) : Personagem(nome, hp = 130, ataque = 25) {
    private var bonusDano = 0

    override fun restaurarVida() {
        super.restaurarVida()
        bonusDano = 0
    }

    override fun atacar(alvo: Personagem) {
        val hpAtual = getHp()
        val hpMaximo = getMaxHp()
        bonusDano = ((1 - (hpAtual.toDouble() / hpMaximo)) * 45).toInt()
        val danoTotal = getAtaque() + bonusDano

        println("$nome entra em fúria e causa $danoTotal de dano em ${alvo.nome}.")
        alvo.receberDano(danoTotal)

        val autoDano = (danoTotal * 0.10).toInt()
        println("$nome se fere com a própria fúria, perdendo $autoDano de HP.")
        receberDano(autoDano)
        println("HP de $nome após o auto-dano: ${getHp()}")
    }
}

// -----------------------------
// CLASSE: Batalha
// -----------------------------
class Batalha(
    private val jogador1: String,
    private val jogador2: String,
    var personagem1: Personagem,
    var personagem2: Personagem
) {
    var numeroRound: Int = 1
    var vencedorRound: String? = null

    fun iniciarRound(): String {
        println("\n===== ROUND $numeroRound =====")
        println("${personagem1.nome} vs ${personagem2.nome}")
		println("========================================================")

        while (personagem1.estaVivo() && personagem2.estaVivo()) {
            Thread.sleep(1000)
            personagem1.atacar(personagem2)
            println("-> HP de ${personagem2.nome}: ${personagem2.getHp()}")
            println()

            if (!personagem2.estaVivo()) break

            Thread.sleep(1000)
            personagem2.atacar(personagem1)
            println("-> HP de ${personagem1.nome}: ${personagem1.getHp()}")
            println("--------------------------------------------------------")
        }

        vencedorRound = if (personagem1.estaVivo()) jogador1 else jogador2
        println("\n Vencedor do Round $numeroRound: $vencedorRound !!!")
        return vencedorRound!!
    }
}

// -----------------------------
// FUNÇÕES AUXILIARES
// -----------------------------
fun escolherClasse(nomePersonagem: String, classe: String? = null): Personagem {
    while (true) {
        if (classe != null) {
            println("Classe escolhida para $nomePersonagem: $classe")

            when (classe.lowercase()) {
                "escudeiro" -> return Escudeiro(nomePersonagem)
                "mago" -> return Mago(nomePersonagem)
                "arqueiro" -> return Arqueiro(nomePersonagem)
                "berserker" -> return Berserker(nomePersonagem)
            }
        }

        println("Escolha a classe para $nomePersonagem:")
        println("1 - Escudeiro (HP alto, defesa variável)")
        println("2 - Mago (HP baixo, ataque alto)")
        println("3 - Arqueiro (Equilibrado, ataques múltiplos)")
        println("4 - Berserker (HP alto, ataca em fúria aumentando seu dano)")

        print("Opção: ")
        when (readln().trim()) {
            "1" -> return Escudeiro(nomePersonagem)
            "2" -> return Mago(nomePersonagem)
            "3" -> return Arqueiro(nomePersonagem)
            "4" -> return Berserker(nomePersonagem)
            else -> println("Opção inválida. Tente novamente.")
        }
    }
}

// -----------------------------
// PROGRAMA PRINCIPAL
// -----------------------------
fun main() {
    // TODO: Tirar diversas variáveis de dentro de loops com inserção, inclusive, de blocos try/catch para evitar falhas de input
    while (true) {
        println("=== JOGO DE BATALHA POR TURNOS ===")

        print("Nome do Jogador 1: ")
        val jogador1 = readln()
        // val jogador1 = "Jogador1" // Para testes rápidos, descomente esse e comente a linha acima
        print("Nome do personagem do Jogador 1: ")
        val nomeP1 = readln()
        // val nomeP1 = "Personagem1" // Para testes rápidos, descomente esse e comente a linha acima
        var personagem1 = escolherClasse(nomeP1) // Para testes rápidos, defina a classe após o nome

        print("Nome do Jogador 2: ")
        val jogador2 = readln()
        // val jogador2 = "Jogador2" // Para testes rápidos, descomente esse e comente a linha acima
        print("Nome do personagem do Jogador 2: ")
        val nomeP2 = readln()
        // val nomeP2 = "Personagem2" // Para testes rápidos, descomente esse e comente a linha acima
        var personagem2 = escolherClasse(nomeP2) // Para testes rápidos, defina a classe após o nome

        var vitoriasP1 = 0
        var vitoriasP2 = 0
        var round = 1

        while (round <= 3 && vitoriasP1 < 2 && vitoriasP2 < 2) {
            val batalha = Batalha(jogador1, jogador2, personagem1, personagem2)
            batalha.numeroRound = round

            // Restaura vida antes do round, se necessário
            personagem1.restaurarVida()
            personagem2.restaurarVida()

            val vencedor = batalha.iniciarRound()

            if (vencedor == jogador1) vitoriasP1++ else vitoriasP2++
            println("Placar: $jogador1 $vitoriasP1 x $vitoriasP2 $jogador2")

            if (vitoriasP1 == 2 || vitoriasP2 == 2) break

            // Para testes rápidos, remova a opção de trocar classe comentando os 2 blocos abaixo
            println("\n$jogador1: Deseja trocar a CLASSE para o próximo round? (s/n)")
            if (readln().lowercase() == "s") personagem1 = escolherClasse(nomeP1)

            println("\n$jogador2: Deseja trocar a CLASSE para o próximo round? (s/n)")
            if (readln().lowercase() == "s") personagem2 = escolherClasse(nomeP2)

            round++
        }

        val campeao = if (vitoriasP1 > vitoriasP2) jogador1 else jogador2
        println("\nCAMPEÃO DA PARTIDA: $campeao !!!")

        println("\nDeseja jogar novamente? (s/n): ")
        if (readln().lowercase() != "s") {
            println("Saindo do jogo. Até a próxima!")
            exitProcess(0)
        }
    }
}
