package com.mygdx.game.main.unit

import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Util
import org.xguzm.pathfinding.PathFindingException
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell
import org.xguzm.pathfinding.grid.finders.AStarGridFinder
import org.xguzm.pathfinding.grid.finders.GridFinderOptions
import java.util.*

abstract class GameUnit(
        var currentCell: GridCell
        , private val navLayer: NavigationTiledMapLayer
        , private val tilePixelWidth: Int
        , private val tilePixelHeight: Int
) : GameObject {

    abstract var DAMAGE: Int
    abstract var HEALTH: Int
    abstract var MAX_HEALTH: Int
    abstract var SPEED: Int

    private val nextPosition = Vector2()
    private val delta = Vector2()

    var currentPosition = Vector2().apply {
        val x = (currentCell.x * tilePixelWidth / 2).toFloat()
        val y = (currentCell.y * tilePixelHeight + tilePixelHeight).toFloat()
        set(x, y)
        mul(ISO_TRANSFORMER)
    }

    private lateinit var nextCell: GridCell

    var pathToTarget: MutableList<GridCell> = ArrayList()
    var targetCell: GridCell? = null


    private fun newDirection() {
        targetCell?.let {
            pathToTarget.clear()

            val path = try {
                PATH_FINDER.findPath(currentCell.x, currentCell.y, it.x, it.y, navLayer)
                        ?: emptyList()
            } catch (e: PathFindingException) {
                /**catch PathFindingException when click out of map*/
                emptyList<GridCell>()
            }


            pathToTarget.addAll(path)

            if (pathToTarget.size > 0) {
                nextCell = pathToTarget[0]

                val deltaX = (nextCell.x - currentCell.x).toFloat()
                val deltaY = (nextCell.y - currentCell.y).toFloat()
                val scale = (SPEED / 10).toFloat()

                delta.set(deltaX, deltaY)
                delta.scl(scale)
                delta.mul(ISO_TRANSFORMER)

                val x = (nextCell.x * tilePixelWidth / 2).toFloat()
                val y = (nextCell.y * tilePixelHeight + tilePixelHeight).toFloat()

                nextPosition.set(x, y)
                nextPosition.mul(ISO_TRANSFORMER)
            }
        }



    }

    fun update() {
        targetCell?.let {
            if (pathToTarget.size > 0) {
                currentPosition.add(delta)
                if (Util.onPosition(currentPosition, nextPosition)) {
                    currentCell = nextCell
                    newDirection()
                }
            } else if (currentCell.x != it.x || currentCell.y != it.y) {
                newDirection()
            }
        }
    }

    companion object {

        private val PATH_FINDER = AStarGridFinder(GridCell::class.java, GridFinderOptions().apply { allowDiagonal = false })

        private val ISO_TRANSFORMER = Matrix3().apply {
            idt()
            val scaleX = (Math.sqrt(2.0) / 2.0).toFloat() * 2
            val scaleY = (Math.sqrt(2.0) / 4.0).toFloat() * 2
            scale(scaleX, scaleY)
            rotate(-45.0f)
        }

    }

}
