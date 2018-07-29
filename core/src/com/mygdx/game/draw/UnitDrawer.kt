package com.mygdx.game.draw

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.brashmonkey.spriter.Animation
import com.brashmonkey.spriter.Mainline
import com.brashmonkey.spriter.Player
import com.brashmonkey.spriter.SCMLReader
import com.mygdx.game.main.unit.GameUnit

class UnitDrawer(val unit: GameUnit, spriteBatch: SpriteBatch): Comparable<UnitDrawer> {

    var player = Player(data.getEntity(0)).apply {
        scale = .5f
        addListener(object : Player.PlayerListener {
            override fun animationFinished(animation: Animation) = Unit

            override fun animationChanged(oldAnim: Animation, newAnim: Animation) = Unit

            override fun preProcess(player: Player) = Unit

            override fun postProcess(player: Player) = Unit

            override fun mainlineKeyChanged(prevKey: Mainline.Key, newKey: Mainline.Key) = Unit
        })
    }

    var libGdxDrawer = LibGdxDrawer(loader, spriteBatch, ShapeRenderer())

    companion object {
        private val handle = Gdx.files.internal("spriter/test.scml")
        private val data = SCMLReader(handle.read()).data
        private val loader = LibGdxLoader(data).apply { load("spriter") }
    }

    fun draw() {
        player.update()
        player.setPosition(unit.currentPosition.x, unit.currentPosition.y)
        libGdxDrawer.draw(player)
    }

    override fun compareTo(other: UnitDrawer): Int {
        if (unit.currentCell.y < other.unit.currentCell.y) {
            return 1
        }
        if (unit.currentCell.y == other.unit.currentCell.y && unit.currentCell.x== other.unit.currentCell.x){
            return 0
        }
        return if (unit.currentCell.y == other.unit.currentCell.y && unit.currentCell.x > other.unit.currentCell.x) 1 else -1

    }
}
