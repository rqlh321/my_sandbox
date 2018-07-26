package com.mygdx.game.main.stage

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.base.BaseLayer
import com.mygdx.game.draw.DrawManager
import com.mygdx.game.main.TiledMapParser
import org.xguzm.pathfinding.grid.GridCell

class MainLayer(viewport: Viewport) : BaseLayer(viewport) {

    val mapParser = TiledMapParser()

    private val drawManager = DrawManager(batch, viewport.camera as OrthographicCamera, mapParser.map, mapParser.units)
            .also { disposable.add(it) }

    override fun draw(delta: Float) = drawManager.draw(delta)

    fun tap(gridCell: GridCell) = mapParser.units.forEach { it.targetCell = gridCell }

}