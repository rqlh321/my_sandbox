package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Vector2;

import org.xguzm.pathfinding.grid.GridCell;

import java.util.List;

public class Util {

    public static boolean onPosition(Vector2 currentPosition, Vector2 target) {
        float delta = (Math.abs(currentPosition.x - target.x) + Math.abs(currentPosition.y - target.y));
        return delta - 1 <= .1f;
    }

    public static float[] getSize(TiledMap tiledMap) {
        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        float mapPixelWidth = mapWidth * tilePixelWidth;
        float mapPixelHeight = mapHeight * tilePixelHeight;

        return new float[]{mapPixelWidth, mapPixelHeight};
    }

    public static void worldToIso(Vector2 point, int tileWidth, int tileHeight) {
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;
    }

    public static void drawPath(TiledMap map, List<GridCell> path) {
        TiledMapTileLayer pathLayer = (TiledMapTileLayer) map.getLayers().get("path");
        TiledMapTileSets tileSets = map.getTileSets();
        TiledMapTileSet special = tileSets.getTileSet("special");
        TiledMapTile pathTile = special.getTile(675);
        for (GridCell gridCell : path) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(pathTile);
            pathLayer.setCell(gridCell.getX(), gridCell.getY(), cell);
        }
    }

    public static void drawNextCell(TiledMap map, GridCell gridCell) {
        TiledMapTileLayer pathLayer = (TiledMapTileLayer) map.getLayers().get("path");
        TiledMapTileSets tileSets = map.getTileSets();
        TiledMapTileSet special = tileSets.getTileSet("special");
        TiledMapTile pathTile = special.getTile(675);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(pathTile);
        pathLayer.setCell(gridCell.getX(), gridCell.getY(), cell);
    }

    public static void drawWalckable(TiledMap map, int x, int y) {
        TiledMapTileLayer pathLayer = (TiledMapTileLayer) map.getLayers().get("path");
        TiledMapTileSets tileSets = map.getTileSets();
        TiledMapTileSet special = tileSets.getTileSet("special");
        TiledMapTile pathTile = special.getTile(673);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(pathTile);
        pathLayer.setCell(x, y, cell);
    }

    public static void drawUnWalckable(TiledMap map, int x, int y) {
        TiledMapTileLayer pathLayer = (TiledMapTileLayer) map.getLayers().get("path");
        TiledMapTileSets tileSets = map.getTileSets();
        TiledMapTileSet special = tileSets.getTileSet("special");
        TiledMapTile pathTile = special.getTile(676);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(pathTile);
        pathLayer.setCell(x, y, cell);
    }

    public static void clearLayer( TiledMapTileLayer layer) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getWidth(); y++) {
                layer.setCell(x, y, null);
            }
        }
    }
}
