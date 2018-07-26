package com.mygdx.game.main.unit

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell

class Protagonist(
        cell: GridCell
        , navLayer: NavigationTiledMapLayer
        , tilePixelWidth: Int
        , tilePixelHeight: Int
) : GameUnit(cell, navLayer, tilePixelWidth, tilePixelHeight) {

    override var DAMAGE = 40
    override var MAX_HEALTH = 100
    override var HEALTH = MAX_HEALTH
    override var SPEED = 20 + (Math.random() * 10).toInt()

    override fun name(): String = Protagonist::class.java.simpleName
}
