package com.mygdx.game.draw

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import com.mygdx.game.Util
import com.mygdx.game.main.TiledMapParser
import com.mygdx.game.main.unit.Enemy
import com.mygdx.game.main.unit.GameUnit
import java.util.*

class DrawManager(
        private val batch: SpriteBatch
        , private val camera: OrthographicCamera
        , private val map: TiledMap
        , private val units: ArrayList<GameUnit>) : Disposable {

    private val tiledMapRenderer = IsometricTiledMapRenderer(map, batch)

    private val unitDrawers = units.map { UnitDrawer(it, batch) }.toMutableList()

    private val groundLayer = map.layers.get(TiledMapParser.LAYER_GROUND) as TiledMapTileLayer
    private val backgroundLayer = map.layers.get(TiledMapParser.LAYER_BACKGROUND) as TiledMapTileLayer
    private val middleBottomLayer = map.layers.get(TiledMapParser.LAYER_MIDDLE_BOTTOM) as TiledMapTileLayer
    private val highLightLayer = map.layers.get(TiledMapParser.LAYER_HIGHLIGHT) as TiledMapTileLayer
    private val pathLayer = map.layers.get(TiledMapParser.LAYER_PATH) as TiledMapTileLayer
    private val middleTopLayer = map.layers.get(TiledMapParser.LAYER_MIDDLE_TOP) as TiledMapTileLayer

    private val special = map.tileSets.getTileSet("special")

    private val friendCell: TiledMapTileLayer.Cell = TiledMapTileLayer.Cell()
            .apply { tile = special.getTile(673) }

    private val pathCell: TiledMapTileLayer.Cell = TiledMapTileLayer.Cell()
            .apply { tile = special.getTile(674) }

    private val targetCell: TiledMapTileLayer.Cell = TiledMapTileLayer.Cell()
            .apply { tile = special.getTile(675) }

    private val enemyCell: TiledMapTileLayer.Cell = TiledMapTileLayer.Cell()
            .apply { tile = special.getTile(676) }


    fun draw(delta: Float) {
        tiledMapRenderer.setView(camera)
        tiledMapRenderer.batch.projectionMatrix = camera.combined
        batch.begin()
        tiledMapRenderer.renderTileLayer(groundLayer)
        tiledMapRenderer.renderTileLayer(backgroundLayer)
        tiledMapRenderer.renderTileLayer(middleBottomLayer)
        tiledMapRenderer.renderTileLayer(highLightLayer)
        tiledMapRenderer.renderTileLayer(pathLayer)
        Util.clearLayer(highLightLayer)

        unitDrawers.apply {
            sort()
            forEach {
                val type = if (it.unit is Enemy) enemyCell else friendCell
                it.unit.currentCell.let { highLightLayer.setCell(it.x, it.y, type) }
                it.unit.pathToTarget.forEach { highLightLayer.setCell(it.x, it.y, pathCell) }
                it.unit.targetCell?.let { highLightLayer.setCell(it.x, it.y, targetCell) }
//                it.draw()
            }
        }

        tiledMapRenderer.renderTileLayer(middleTopLayer)
        batch.end()
    }

    override fun dispose() {
        map.dispose()
        tiledMapRenderer.dispose()
    }
}
