package combat

import PositiveInt
import combat.diceCalculators.DiceAmountCalculator
import combat.lostArmiesCalculator.LostArmiesCalculator
import dice.Die

/**
 * Rolls dice and determines the attacker's and defender's lost armies
 * based on the results.
 */
class CombatResolver(
    private val diceAmountCalculator: DiceAmountCalculator, private val die: Die
) {

    private val lostArmiesCalculator = LostArmiesCalculator()

    fun combat(
        attackingArmies: PositiveInt, defendingArmies: PositiveInt
    ): Pair<Int, Int> {
        val (attackerRolls, defenderRolls) =
            rollDiceForCombat(attackingArmies, defendingArmies)
        val contestedArmies = calculateContestedArmies(attackingArmies, defendingArmies)
        return lostArmiesCalculator.armiesLostForRolls(
            attackerRolls, defenderRolls, contestedArmies)
    }

    private fun rollDiceForCombat(
        attackingArmies: PositiveInt, defendingArmies: PositiveInt
    ): Pair<Collection<Int>, Collection<Int>> {
        val (attackerDice, defenderDice) =
            diceAmountCalculator.forAttack(attackingArmies, defendingArmies)
        val attackerRolls = roll(attackerDice)
        val defenderRolls = roll(defenderDice)
        return Pair(attackerRolls, defenderRolls)
    }

    private fun roll(amount: PositiveInt) = die.roll(amount.toInt())

    private fun calculateContestedArmies(
        attackingArmies: PositiveInt, defendingArmies: PositiveInt): PositiveInt {
        val maxAttackerContestedArmies = attackingArmies - PositiveInt(1)
        return minOf(
            maxAttackerContestedArmies,
            defendingArmies,
            MAX_CONTESTED_ARMIES
        )
    }

    companion object {
        val MAX_CONTESTED_ARMIES = PositiveInt(3)
    }
}