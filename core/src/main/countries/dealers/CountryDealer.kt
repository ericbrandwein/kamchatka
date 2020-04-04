package countries.dealers

import Country
import Player
import countries.Occupation

abstract class CountryDealer(protected val countries: List<Country>) {

    abstract fun dealTo(players: Collection<Player>): Collection<Occupation>

    companion object {
        const val STARTING_OCCUPATION_ARMIES = 1
    }
}