package gamelogic.gameState

import Country
import PositiveInt
import gamelogic.CountryReinforcement
import gamelogic.Regrouping

abstract class GameState {
    open fun addArmies(reinforcements: Collection<CountryReinforcement>): Unit =
        throw NotInReinforcingStateException()

    open fun makeAttack(from: Country, to: Country): Unit =
        throw NotInAttackingStateException()

    open fun occupyConqueredCountry(armies: PositiveInt): Unit =
        throw NotInAttackingStateException()

    open fun endAttack(): Unit = throw NotInAttackingStateException()

    open fun regroup(regroupings: List<Regrouping>): Unit =
        throw NotInRegroupingStateException()
}

class NotInReinforcingStateException : Exception("Cannot add armies right now.")
class NotInAttackingStateException : Exception("Cannot attack or occupy right now.")
class NotInRegroupingStateException : Exception("Cannot regroup right now.")
