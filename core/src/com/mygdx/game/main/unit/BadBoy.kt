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

    override fun act() = Unit

}
