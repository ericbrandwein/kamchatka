package screens.running

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.Viewport

class WorldmapStage(assetManager: AssetManager, worldmapTexture: Texture, viewport: Viewport): Stage(viewport) {
    private val countryLabel = Label("", Label.LabelStyle(BitmapFont(), Color.WHITE))
    private val countrySelector = CountrySelector(assetManager)
    private var currentCountry: String? = null

    init {
        setupWorldmapImage(worldmapTexture)
        setupCountryLabel()
    }

    private fun setupWorldmapImage(worldmapTexture: Texture) {
        val worldmapImage = Image(worldmapTexture)
        worldmapImage.setPosition(0F, 0F)
        worldmapImage.addListener(object : InputListener() {
            override fun mouseMoved(event: InputEvent?, x: Float, y: Float): Boolean {
                currentCountry = countrySelector.selectByMapPosition(x, y)
                if (currentCountry == null) {
                    countryLabel.setText("")
                } else {
                    countryLabel.setText(currentCountry)
                }
                return true
            }
        })
        addActor(worldmapImage)
    }

    private fun setupCountryLabel() {
        countryLabel.setFontScale(4F)
        countryLabel.setPosition(8F, 20F)
        addActor(countryLabel)
    }
}
