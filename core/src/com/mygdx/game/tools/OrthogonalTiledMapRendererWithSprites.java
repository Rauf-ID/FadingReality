package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;


public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    private ArrayList<TextureRegion> textureRegions;


    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        textureRegions = new ArrayList<TextureRegion>();
    }

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale) {
        super(map, unitScale);
        textureRegions = new ArrayList<TextureRegion>();
    }


    public void addTextureRegion(TextureRegion textureRegion){
        textureRegions.add(textureRegion);
    }


    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            textureRegions.add(textureObject.getTextureRegion());
            batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY());
        }
    }

}