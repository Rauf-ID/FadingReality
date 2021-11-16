package com.mygdx.game.pathfinder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Node {

    public enum GridType {
        NONE,
        CLOSE,
        START,
        END
    }

    public Rectangle rectangle;
    public GridType type = GridType.NONE;

    public float F = 0, G = 0, H = 0;

    public Node parent = null;

    public Node(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void calcNode(Node parent, Node end) {
        this.parent = parent;
        if (parent != null) {
            if (Math.abs(rectangle.x - parent.rectangle.x) != 0 &&
                    Math.abs(rectangle.y - parent.rectangle.y) != 0) {
                G = parent.G + 14;
            }
            else {
                G = parent.G + 10;
            }
        }

        float xDistance = (Math.abs(rectangle.x-end.rectangle.x) / rectangle.width);
        float yDistance = (Math.abs(end.rectangle.y-rectangle.y) / rectangle.height);

        if (xDistance > yDistance)
            H = 14*yDistance + 10*(xDistance-yDistance);
        else
            H = 14*xDistance + 10*(yDistance-xDistance);
        F = G + H;
    }

    public void reset() {
        F = G = H = 0;
        parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        super.equals(obj);
        Node a = (Node) obj;
        return (rectangle.x == a.rectangle.x &&
                rectangle.y == a.rectangle.y &&
                rectangle.width == a.rectangle.width &&
                rectangle.height == a.rectangle.height &&
                type == a.type);
    }


    public void render(OrthographicCamera camera, Color color, ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(color);
        shape.rect(this.rectangle.x, this.rectangle.y,rectangle.width,rectangle.height);
        shape.end();
    }

    public GridType getType() {
        return type;
    }

    public void setType(GridType type) {
        this.type = type;
    }
}