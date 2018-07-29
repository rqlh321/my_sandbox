package com.mygdx.game.loading

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.MySandbox
import com.mygdx.game.base.BaseScreen

class LoadingScreen(viewport: Viewport) : BaseScreen(viewport){

    override fun show() {
        MySandbox.assetManager.load<TiledMap>("maps/test/1.tmx", TiledMap::class.java)

        while (true) {
            if(MySandbox.assetManager.update()){
                MySandbox.game()
                break
            }
        }
    }


}