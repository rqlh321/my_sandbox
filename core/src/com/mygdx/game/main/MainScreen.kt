package com.mygdx.game.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.base.BaseScreen
import com.mygdx.game.main.stage.MainLayer
import com.mygdx.game.main.unit.Control

class MainScreen(viewport: Viewport) : BaseScreen(viewport) {

    private lateinit var layer: MainLayer

    override fun show() {
        layer = MainLayer(viewport)
        disposable.add(layer)
        Gdx.input.inputProcessor = GestureDetector(Control(viewport, layer))
    }

    override fun render(delta: Float) {
        println(delta)
        layer.act(delta)
        layer.draw(delta)
    }


}