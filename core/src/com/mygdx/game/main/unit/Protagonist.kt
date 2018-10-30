package com.mygdx.game.main.unit

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer
import org.xguzm.pathfinding.grid.GridCell

class Protagonist(
        cell: GridCell
        , navLayer: NavigationTiledMapLayer
        , tilePixelWidth: Int
        , tilePixelHeight: Int
) : GameUnit(cell, navLayer, tilePixelWidth, tilePixelHeight)
