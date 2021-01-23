package screens

import Kamchatka
import MapColors
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.viewport.FitViewport

class RunningScreen(private val game: Kamchatka) : KamchatkaScreen(game) {
    private val worldmap: Texture = Texture("mapa.png")
    private val countryColorsMap: Texture = Texture("colores-paises.png")
    private var currentCountry: String? = null
    private val mapColors = MapColors.fromJsonFile("mapa.json")
    private val message = BitmapFont()
    private val worldmapPixmap: Pixmap

    init {
        game.viewport = FitViewport(
            worldmap.width.toFloat(),
            worldmap.height.toFloat(),
            game.camera
        )
        val textureData = countryColorsMap.textureData
        if (!textureData.isPrepared) {
            textureData.prepare()
        }
        worldmapPixmap = textureData.consumePixmap()

    }

    override fun render(delta: Float) {
        super.render(delta)
        game.batch.begin()

        game.batch.draw(
            worldmap,
            -worldmap.width.toFloat() / 2,
            -worldmap.height.toFloat() / 2
        )
        if (currentCountry != null) {
            message.draw(game.batch, currentCountry, 1F, 1F)
        }
        game.batch.end()
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.Q -> game.setKamchatkaScreen(ReadyScreen(game))
        }
        return true
    }

    override fun dispose() {
        super.dispose()
        worldmap.dispose()
    }


    override fun touchDown(
        screenX: Int, screenY: Int, pointer: Int, button: Int
    ): Boolean {
        val color = getMapColorAtPoint(screenX, screenY)
        if (color in mapColors) {
            val country = mapColors[color]
            println("position: ($screenX, $screenY), country: $country, color: $color.")
        } else {
            println("There's no country in position ($screenX, $screenY).")
        }
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        val color = getMapColorAtPoint(screenX, screenY)
        currentCountry = mapColors[color]
        return true
    }

    private fun getMapColorAtPoint(screenX: Int, screenY: Int): Color {
        val (actualX, actualY) = screenPositionToWorldMapPosition(screenX, screenY)
        return Color(worldmapPixmap.getPixel(actualX, actualY))
    }

    private fun screenPositionToWorldMapPosition(
        screenX: Int, screenY: Int
    ): Pair<Int, Int> {
        val textureData = worldmap.textureData
        val viewport = game.viewport
        val resizeX = textureData.width.toFloat() / viewport.screenWidth.toFloat()
        val resizeY = textureData.height.toFloat() / viewport.screenHeight.toFloat()
        val textureX = (resizeX * (screenX - viewport.screenX).toFloat()).toInt()
        val textureY = (resizeY * (screenY - viewport.screenY).toFloat()).toInt()
        return Pair(textureX, textureY)
    }

}