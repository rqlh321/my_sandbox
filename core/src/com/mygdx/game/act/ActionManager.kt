package com.mygdx.game.act

import com.mygdx.game.main.unit.GameUnit
import org.xguzm.pathfinding.grid.GridCell
import java.util.*

class ActionManager( private val units: ArrayList<GameUnit>) {

    var targetCell: GridCell? = null

    fun act(delta: Float){
        targetCell?.let {target->
            units.forEach { unit->
                unit.targetCell=targetCell
                unit.update()
                if (unit.currentCell.x == target.x && unit.currentCell.y == target.y){
                    targetCell=null
                    units.forEach { it.targetCell=null }
                }
            }
        }

    }

}