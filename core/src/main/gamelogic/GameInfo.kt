package gamelogic

import gamelogic.combat.AttackerFactory
import gamelogic.gameState.GameState
import gamelogic.gameState.ReinforceState
import gamelogic.map.PoliticalMap
import gamelogic.occupations.CountryOccupations
import gamelogic.occupations.dealers.OccupationsDealer

/**
 * Maintains the current game context, including its state, its current player,
 * its occupations, etc.
 *
 * [GameState] objects manipulate this to advance the game.
 */
class GameInfo(
    val players: MutableList<PlayerInfo>,
    val politicalMap: PoliticalMap,
    val occupations: CountryOccupations,
    val attackerFactory: AttackerFactory,
    val destroyedPlayers: PlayerDestructions = PlayerDestructions()
) {

    var state: GameState = ReinforceState(this)
    val playerIterator = players.loopingIterator()

    val currentPlayer
        get() = playerIterator.current

    fun nextTurn() = playerIterator.next()
}
