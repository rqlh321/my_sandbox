package com.mygdx.game.main.stage

import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.act.ActionManager
import com.mygdx.game.base.BaseLayer
import com.mygdx.game.draw.DrawManager
import com.mygdx.game.main.TiledMapParser

class MainLayer(viewport: Viewport) : BaseLayer(viewport) {

    val mapParser = TiledMapParser()

    private val drawManager = DrawManager(batch, camera, mapParser.map, mapParser.units)
            .also { disposable.add(it) }

    val actionManager = ActionManager(mapParser.units)

    override fun act(delta: Float) {
        actionManager.act(delta)
    }

    override fun draw(delta: Float) = drawManager.draw(delta)

}