package com.mygdx.game.main.unit

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell

import java.util.ArrayList

abstract class Enemy(
        cell: GridCell
        , protected var gameUnits: ArrayList<GameUnit>
        , navLayer: NavigationTiledMapLayer
        , tilePixelWidth: Int
        , tilePixelHeight: Int
) : GameUnit(cell, navLayer, tilePixelWidth, tilePixelHeight) {

    abstract fun act()
}
