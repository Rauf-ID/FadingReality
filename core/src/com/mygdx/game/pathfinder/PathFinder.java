package com.mygdx.game.pathfinder;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.world.Map;

public class PathFinder {

    private Array<Array<Node>> grid = null;

    private Array<Node> openPath = new Array<Node>();
    private Array<Node> closedPath = new Array<Node>();
    private Array<Node> finalPath = new Array<Node>();

    private Node start = null;
    private Node end = null;

    public PathFinder() {}

    public void findPath() {
        openPath.clear();
        closedPath.clear();
        finalPath.clear();
        for (int y = 0; y < grid.size; y++) {
            for (int x = 0; x < grid.get(y).size; x++) {
                grid.get(y).get(x).reset();
            }
        }

        openPath.add(start); // Стартовая точка
        setOpenList((int)start.rectangle.x / Map.CELL_SIZE, (int)start.rectangle.y / Map.CELL_SIZE);

        closedPath.add(openPath.first());
        openPath.removeIndex(0);
        while (closedPath.peek() != end) { //Работает пока не дошел до Финиша
            if (openPath.size != 0) {
                float bestF = 100000;
                int bestFIndex = -1;
                for (int i = 0; i < openPath.size; i++) {
                    if (openPath.get(i).F < bestF) {
                        bestF = openPath.get(i).F;
                        bestFIndex = i;
                    }
                }

                if (bestFIndex != -1) {
                    closedPath.add(openPath.get(bestFIndex));
                    openPath.removeIndex(bestFIndex);
                    setOpenList((int)(closedPath.peek().rectangle.x / Map.CELL_SIZE), (int)(closedPath.peek().rectangle.y / Map.CELL_SIZE));
                }
            }
        }

        Node g = closedPath.peek();
        finalPath.add(g);
        while (g != start) {
            g = g.parent;
            finalPath.add(g);
        }
        finalPath.reverse();
    }

    private void setOpenList(int x, int y) {
        boolean ignoreLeft = (x - 1 < 0);
        boolean ignoreRight = (x + 1 >= grid.get(y).size);
        boolean ignoreUp = (y - 1 < 0);
        boolean ignoreDown = (y + 1 >= grid.size);

        if (!ignoreUp) {
            lookNode(grid.get(y).get(x), grid.get(y-1).get(x));
        }
        if (!ignoreLeft) {
            lookNode(grid.get(y).get(x), grid.get(y).get(x-1));
        }
        if (!ignoreRight) {
            lookNode(grid.get(y).get(x), grid.get(y).get(x+1));
        }
        if (!ignoreDown) {
            lookNode(grid.get(y).get(x), grid.get(y+1).get(x));
        }

        if (!ignoreLeft && !ignoreUp) {
            lookNode(grid.get(y).get(x), grid.get(y-1).get(x-1));
        }
        if (!ignoreRight && !ignoreUp) {
            lookNode(grid.get(y).get(x), grid.get(y-1).get(x+1));
        }
        if (!ignoreLeft && !ignoreDown) {
            lookNode(grid.get(y).get(x), grid.get(y+1).get(x-1));
        }
        if (!ignoreRight && !ignoreDown) {
            lookNode(grid.get(y).get(x), grid.get(y+1).get(x+1));
        }
    }

    private void lookNode(Node parent, Node current) {
        if (current.type != Node.GridType.CLOSE && !(closedPath.contains(current, true) || closedPath.contains(current, false))) {
            if (!(openPath.contains(current, true) || openPath.contains(current, false))) {
                current.calcNode(parent, end);
                openPath.add(current);
            } else {
                compareParentWithOpen(parent, openPath.get(openPath.indexOf(current, true)));
            }
        }
    }

    private void compareParentWithOpen(Node parent, Node open) {
        float tempGCost = open.G;
        if (Math.abs(open.rectangle.x - parent.rectangle.x) / Map.CELL_SIZE == 1 && Math.abs(open.rectangle.y - parent.rectangle.y) / Map.CELL_SIZE == 1) {
            tempGCost += 14;
        }
        else {
            tempGCost += 10;
        }
        if (tempGCost < parent.G) {
            open.calcNode(parent, end);
            openPath.set(openPath.indexOf(open, true), open);
        }
    }

    public void setGridNode(Node node, Node.GridType type) {
        if (type == Node.GridType.START) {
            start = node;
        } else if (type == Node.GridType.END) {
            end = node;
        }
    }



    public Array<Node> getFinalPath() {
        return finalPath;
    }

    public void setFinalPath(Array<Node> finalPath) {
        this.finalPath = finalPath;
    }

    public Array<Array<Node>> getGrid() {
        return grid;
    }

    public void setGrid(Array<Array<Node>> grid) {
        this.grid = grid;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }
}
