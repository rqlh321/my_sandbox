package com.mygdx.game.main.unit

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell

import java.util.ArrayList

class BadBoy(
        cell: GridCell
        , gameUnits: ArrayList<GameUnit>
        , navLayer: NavigationTiledMapLayer
        , tilePixelWidth: Int
        , tilePixelHeight: Int
) : Enemy(cell, gameUnits, navLayer, tilePixelWidth, tilePixelHeight) {

    override var DAMAGE = 20
    override var MAX_HEALTH = 100
    override var HEALTH = MAX_HEALTH
    override var SPEED = 20 + (Math.random() * 10).toInt()

    override fun act() = Unit

    override fun name(): String = BadBoy::class.java.simpleName
}
