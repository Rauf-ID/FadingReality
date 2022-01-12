package com.mygdx.game.skills;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.inventory.InventorySlot;

public class SkillTooltipListener extends InputListener {

    private SkillTooltip toolTip;
    private boolean isInside = false;
    private Vector2 currentCoords;
    private Vector2 offset;

    public SkillTooltipListener(SkillTooltip toolTip){
        this.toolTip = toolTip;
        this.currentCoords = new Vector2(0,0);
        this.offset = new Vector2(20, 10);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y){
        SkillButton skillButton = (SkillButton) event.getListenerActor();
        if(isInside){
            currentCoords.set(x, y);
            skillButton.localToStageCoordinates(currentCoords);

            toolTip.setPosition(currentCoords.x+ offset.x, currentCoords.y+ offset.y);
        }
        return false;
    }


    @Override
    public void touchDragged (InputEvent event, float x, float y, int pointer) {
        SkillButton skillButton = (SkillButton) event.getListenerActor();
        toolTip.setVisible(skillButton, false);
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        SkillButton skillButton = (SkillButton) event.getListenerActor();

        isInside = true;

        currentCoords.set(x, y);
        skillButton.localToStageCoordinates(currentCoords);

        toolTip.updateDescription(skillButton);
        toolTip.setPosition(currentCoords.x + offset.x, currentCoords.y + offset.y);
        toolTip.toFront();
        toolTip.setVisible(skillButton, true);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
        SkillButton skillButton = (SkillButton)event.getListenerActor();
        toolTip.setVisible(skillButton, false);
        isInside = false;

        currentCoords.set(x, y);
        skillButton.localToStageCoordinates(currentCoords);
    }

}
