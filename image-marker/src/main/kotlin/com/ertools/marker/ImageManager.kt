package com.ertools.marker

import com.ertools.commons.Utils
import com.ertools.commons.Utils.IMAGES_PATH
import java.awt.Color
import java.awt.Font
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.imageio.ImageIO

class ImageManager {

    fun saveDataAsImage(data: String, filename: String): File {
        val directory = File(IMAGES_PATH)
        if (!directory.exists()) directory.mkdirs()

        val decodedBytes = Base64.getDecoder().decode(data)
        val imageFile = File("${IMAGES_PATH}/${filename}")
        imageFile.writeBytes(decodedBytes)
        return imageFile
    }

    fun markImage(file: File, code: String) = try {
        val image = ImageIO.read(FileInputStream(file))
        val graphics = image.createGraphics()
        graphics.font = Font("Arial", Font.BOLD, Utils.KEY_SIZE)
        graphics.color = Color(1f, 1f, 1f, Utils.KEY_OPACITY)
        graphics.rotate(0.3)
        val rows = image.height / Utils.KEY_SIZE
        val columns = 3 * image.width / (code.length * Utils.KEY_SIZE) / 2
        (-1..rows).forEach { y ->
            (-1..columns).forEach { x ->
                graphics.drawString(code, image.width * x / columns, image.height * y / rows)
            }
        }
        graphics.dispose()
        ImageIO.write(image, "png", file)
    } catch (e: Exception) {
        println("ENGINE: Image has not been created.")
        e.printStackTrace()
    }
}