package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.brashmonkey.spriter.Drawer
import com.brashmonkey.spriter.Loader
import com.brashmonkey.spriter.Timeline

class LibGdxDrawer(
        loader: Loader<Sprite>
        , private var batch: SpriteBatch
        , private var renderer: ShapeRenderer
) : Drawer<Sprite>(loader) {

    override fun setColor(r: Float, g: Float, b: Float, a: Float) = renderer.setColor(r, g, b, a)

    override fun rectangle(x: Float, y: Float, width: Float, height: Float) = renderer.rect(x, y, width, height)

    override fun line(x1: Float, y1: Float, x2: Float, y2: Float) = renderer.line(x1, y1, x2, y2)

    override fun circle(x: Float, y: Float, radius: Float) = renderer.circle(x, y, radius)

    override fun draw(keyObject: Timeline.Key.Object) {
        val sprite = loader.get(keyObject.ref)

        val newPivotX = sprite.width * keyObject.pivot.x
        val newX = keyObject.position.x - newPivotX
        sprite.x = newX

        val newPivotY = sprite.height * keyObject.pivot.y
        val newY = keyObject.position.y - newPivotY
        sprite.y = newY

        sprite.setOrigin(newPivotX, newPivotY)
        sprite.rotation = keyObject.angle

        sprite.setColor(1f, 1f, 1f, keyObject.alpha)
        sprite.setScale(keyObject.scale.x, keyObject.scale.y)
        sprite.draw(batch)
    }

}