package com.mygdx.game.main

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.utils.Disposable
import com.mygdx.game.MySandbox
import com.mygdx.game.main.unit.Enemy
import com.mygdx.game.main.unit.GameObject
import com.mygdx.game.main.unit.GameUnit
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell
import java.lang.reflect.InvocationTargetException
import java.util.*

class TiledMapParser{

    val map = (MySandbox.assetManager.get("maps/test/1.tmx") as TiledMap)

    private val navLayer = map.layers.get(LAYER_NAVIGATION) as NavigationTiledMapLayer
    private val dataLayer = map.layers.get(LAYER_DATA) as TiledMapTileLayer

    val units = ArrayList<GameUnit>()

    val tilePixelWidth: Int
    val tilePixelHeight: Int

    init {
        val gridCells = navLayer.nodes

        tilePixelWidth = map.properties.get(TILE_WIDTH, Int::class.java)
        tilePixelHeight = map.properties.get(TILE_HEIGHT, Int::class.java)

        for (x in gridCells.indices) {
            for (y in 0 until gridCells[x].size) {

                dataLayer.getCell(x, y)?.let { cell ->
                    try {

                        val type = cell.tile.properties.get(CELL_TILE_PROPERTY_TYPE).toString()
                        val unitClass = Class.forName("${GameObject::class.java.`package`.name}.$type")

                        val unitPosition = gridCells[x][y].apply { isWalkable = false }
                        val typesAndParams = if (unitClass.superclass == Enemy::class.java) {
                            val types = arrayOf(
                                    GridCell::class.java
                                    , ArrayList::class.java
                                    , NavigationTiledMapLayer::class.java
                                    , Int::class.java
                                    , Int::class.java
                            )
                            val parameters = arrayOf(
                                    unitPosition
                                    , units
                                    , navLayer
                                    , tilePixelWidth
                                    , tilePixelHeight
                            )

                            Pair(types, parameters)
                        } else {
                            val types = arrayOf(
                                    GridCell::class.java
                                    , NavigationTiledMapLayer::class.java
                                    , Int::class.java
                                    , Int::class.java
                            )
                            val parameters = arrayOf(
                                    unitPosition
                                    , navLayer
                                    , tilePixelWidth
                                    , tilePixelHeight
                            )

                            Pair(types, parameters)
                        }

                        val constructor = unitClass.getConstructor(*typesAndParams.first)
                        val unit = constructor.newInstance(*typesAndParams.second) as GameUnit
                        units.add(unit)

                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                    } catch (e: NoSuchMethodException) {
                        e.printStackTrace()
                    } catch (e: InstantiationException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                }

            }
        }
    }

    companion object {

        const val CELL_TILE_PROPERTY_TYPE = "type"

        const val TILE_WIDTH = "tilewidth"
        const val TILE_HEIGHT = "tileheight"

        const val LAYER_NAVIGATION = "navigation"
        const val LAYER_DATA = "data"
        const val LAYER_GROUND = "ground"
        const val LAYER_BACKGROUND = "background"
        const val LAYER_MIDDLE_BOTTOM = "midle_bottom"
        const val LAYER_HIGHLIGHT = "high_light"
        const val LAYER_PATH = "path"
        const val LAYER_MIDDLE_TOP = "midle_top"

    }
}
