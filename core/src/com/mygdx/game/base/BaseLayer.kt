package com.mygdx.game.base

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport

open class BaseLayer(viewport: Viewport) : Disposable {

    protected val batch = SpriteBatch()
    protected val camera = viewport.camera as OrthographicCamera
    protected val disposable = ArrayList<Disposable>().apply { add(batch) }

    open fun draw(delta: Float) = Unit
    open fun act(delta: Float) = Unit

    override fun dispose() = disposable.forEach { it.dispose() }

}