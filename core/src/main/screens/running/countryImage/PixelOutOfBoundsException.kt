package screens.running.countryImage

class PixelOutOfBoundsException(x: Int, y: Int) : Exception(
    "Pixel with position ($x, $y) is not inside this Pixmap"
)
