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
) {

    var currentPosition = Vector2().apply {
        val x = (currentCell.x * tilePixelWidth / 2).toFloat()
        val y = (currentCell.y * tilePixelHeight + tilePixelHeight).toFloat()
        set(x, y)
        mul(ISO_TRANSFORMER)
    }


    var pathToTarget: MutableList<GridCell> = ArrayList()
    var targetCell: GridCell? = null
    var nextCell: GridCell? = null

    private fun newDirection() {
        println("newDirection")

        pathToTarget.clear()

        val path = try {
            PATH_FINDER.findPath(currentCell.x, currentCell.y, targetCell!!.x, targetCell!!.y, navLayer)
                    ?: emptyList()
        } catch (e: PathFindingException) {
            emptyList<GridCell>()
        }


        if (path.isNotEmpty()) {
            targetCell = null
            pathToTarget.addAll(path)
            nextCell = pathToTarget[0]
            pathToTarget.removeAt(0)
        }

    }

    fun update() {
        if (targetCell != null && targetCell?.isWalkable == false) {
            targetCell = null
            nextCell   = null
            pathToTarget.clear()
        }

        if (pathToTarget.size > 0) {
            if (nextCell?.isWalkable == true) {
                currentCell.isWalkable = true
                currentCell = nextCell!!
                currentCell.isWalkable = false
                pathToTarget.removeAt(0)
                if (pathToTarget.isNotEmpty()) {
                    nextCell = pathToTarget[0]
                }

                currentPosition.apply {
                    val x = (currentCell.x * tilePixelWidth / 2).toFloat()
                    val y = (currentCell.y * tilePixelHeight + tilePixelHeight).toFloat()
                    set(x, y)
                    mul(ISO_TRANSFORMER)
                }
            }
        } else {
            targetCell?.let {
                if (currentCell.x != it.x || currentCell.y != it.y) {
                    newDirection()
                }
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
