package com.mygdx.game.act

import com.mygdx.game.main.unit.GameUnit
import org.xguzm.pathfinding.grid.GridCell
import java.util.*

class ActionManager(private val units: ArrayList<GameUnit>) {

    fun updateTarget(targetCell: GridCell) = units.forEach { it.targetCell = targetCell }

    fun act(delta: Float) = units.forEach { it.update() }

}