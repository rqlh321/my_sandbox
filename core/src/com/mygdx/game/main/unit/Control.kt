package com.mygdx.game.main.unit

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.Util
import com.mygdx.game.main.stage.MainLayer
import org.xguzm.pathfinding.grid.GridCell

class Control(viewport: Viewport,private val layer: MainLayer): GestureDetector.GestureAdapter() {

    private val camera = viewport.camera as OrthographicCamera

    private val vector3 = Vector3(0f, 0f, 0f)
    private val vector2 = Vector2(0f, 0f)

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        camera.translate(-deltaX, deltaY)
        return super.pan(x, y, deltaX, deltaY)
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        vector3.set(x, y, 0f)
        camera.unproject(vector3)
        vector2.set(vector3.x, vector3.y)
        Util.worldToIso(vector2, layer.mapParser.tilePixelWidth, layer.mapParser.tilePixelHeight)
        val xCel = vector2.x.toInt()
        val yCel = vector2.y.toInt()
        layer.tap(GridCell(xCel, yCel))
        return super.tap(x, y, count, button)
    }
}