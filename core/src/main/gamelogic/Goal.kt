package gamelogic

import gamelogic.map.Continent
import java.lang.Exception

abstract class SubGoal {
    abstract fun achieved(player:PlayerInfo, referee:Referee):Boolean
}

class Goal(val subGoals: List<SubGoal>) : SubGoal() {
    override fun achieved(player: PlayerInfo, referee: Referee): Boolean {
        return subGoals.all{it.achieved(player, referee)}
    }

}

class OccupyContinent(val continent:Continent) : SubGoal() {
    override fun achieved(player: PlayerInfo, referee: Referee): Boolean {
        return continent.countries.all{ referee.occupations.occupierOf(it) == referee.currentPlayer()}
    }
}

class OccupySubContinent(val continent:Continent, val countries:Int): SubGoal() {
    init {
        if (continent.countries.size < countries) {
            throw Exception("${continent.name} has less than ${countries} countries")
        }
    }
    override fun achieved(player: PlayerInfo, referee: Referee): Boolean {
        return continent.countries.count{ referee.occupations.occupierOf(it) == referee.currentPlayer()} >= countries
    }
}

class Destroy(val army:PlayerInfo) : SubGoal() {
    override fun achieved(player: PlayerInfo, referee: Referee): Boolean {
        if (referee.players.contains(player) && player.name == referee.currentPlayer() &&
            !referee.politicalMap.countries.any { referee.occupations.occupierOf(it) == player.name }) {
            referee.players.remove(army)
            return true
         } else {
             return false
        }
    }
}
