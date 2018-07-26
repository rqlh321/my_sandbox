package com.mygdx.game.base

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

open class BaseScreen(protected val viewport: Viewport = ScreenViewport()) : Screen, Disposable {

    protected val disposable = ArrayList<Disposable>()

    override fun hide() = Unit

    override fun show() = Unit

    override fun render(delta: Float) = Unit

    override fun pause() = Unit

    override fun resume() = Unit

    override fun resize(width: Int, height: Int) = Unit

    override fun dispose() = disposable.forEach { it.dispose() }

}