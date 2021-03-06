package gamelogic.combat.resolver.lostArmiesCalculator

import PositiveInt

class TooManyArmiesContestedException(val armies: PositiveInt) : IllegalArgumentException(
    "Can't contest $armies armies, there's not enough gamelogic.dice rolls to decide who wins.")
