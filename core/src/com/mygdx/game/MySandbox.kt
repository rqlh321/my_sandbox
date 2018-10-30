package com.mygdx.game

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.base.BaseScreen
import com.mygdx.game.loading.LoadingScreen
import com.mygdx.game.main.MainScreen
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader

class MySandbox : ApplicationListener {

    override fun create() {
        current = screens[LoadingScreen::class.java.simpleName] ?: BaseScreen()
        current.show()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        screens.values.forEach { it.resize(width, height) }
    }

    override fun pause() = screens.values.forEach { it.pause() }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        viewport.camera.update()

        current.render(Gdx.graphics.deltaTime)
    }

    override fun resume() = screens.values.forEach { it.resume() }

    override fun dispose() = screens.values.forEach { it.dispose() }

    companion object {

        val TEST_LEVEL = "maps/test/1.tmx"

        fun game() {
            current.hide()
            current = screens[MainScreen::class.java.simpleName] ?: BaseScreen()
            current.show()
        }

        val assetManager = AssetManager().apply {
            setLoader(TiledMap::class.java, NavTmxMapLoader(InternalFileHandleResolver()))
        }

        private val viewport = FitViewport(1024f, 600f, OrthographicCamera())

        private val screens = mapOf<String, Screen>(
                LoadingScreen::class.java.simpleName to LoadingScreen(viewport)
                , MainScreen::class.java.simpleName to MainScreen(viewport)
        )

        private lateinit var current: Screen


    }

}
