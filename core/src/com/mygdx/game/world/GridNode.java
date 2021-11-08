package com.mygdx.game.world;


import com.badlogic.gdx.math.Rectangle;

public class GridNode {

    public enum GridType {
        NONE,
        CLOSE,
        OPEN,
        START,
        END
    }

    private Rectangle rectangle;
    private GridType gridType = GridType.NONE;

    public GridNode(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public GridType getGridType() {
        return gridType;
    }

    public void setGridType(GridType gridType) {
        this.gridType = gridType;
    }
}
