package com.mygdx.game.UI.pda;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.skills.Skill;
import com.mygdx.game.skills.SkillButton;
import com.mygdx.game.skills.SkillFactory;
import com.mygdx.game.skills.SkillTooltip;
import com.mygdx.game.skills.SkillTooltipListener;

public class SkillUI extends Window {

    private SkillTooltip skillTooltip;

    public SkillUI(Entity player) {
        super("Skill UI", FadingReality.getUiSkin());

        skillTooltip = new SkillTooltip();
    }

    public void createSkillTree(Entity player) {
        this.clear();

//        Array<Integer> allSkills =  SkillFactory.getInstance().getAllIDSkills();
//        System.out.println(allSkills);
//
//        Array<Integer> learnedSkills = Skill.getLearnedSkills(player);
//        System.out.println(learnedSkills);
//        Array<Integer> accessibleSkills = Skill.getAccessibleSkills(player);
//        System.out.println(accessibleSkills);
//        Array<Integer> inaccessibleSkills = Skill.getInaccessibleSkills(player);
//        System.out.println(inaccessibleSkills);

        Drawable buttonNone = new TextureRegionDrawable(new TextureRegion(FadingReality.resourceManager.textureButtonNonePDA));
        for (Skill.RootNode rootNode : Skill.RootNode.values()) {
            Table unified = new Table();
            unified.add();
            SkillButton rootSkill = new SkillButton(buttonNone);
            unified.add(rootSkill).padBottom(30).row();
            Array<Skill> listSkillsForRootNode = getListSkillsForRootNode(rootNode);
            for (Skill.BranchPosition branchPosition: Skill.BranchPosition.values()) {
                Table tableVertical = new Table();
                Array<Skill> listSkillsForBranchPosition = getListSkillsForBranchPosition(branchPosition, listSkillsForRootNode);
                for (int i = 0; i < listSkillsForBranchPosition.size; i++) {
                    Skill skill = listSkillsForBranchPosition.get(i);
                    SkillButton skillButton = new SkillButton(buttonNone, skill);
                    skillButton.addListener(new SkillTooltipListener(skillTooltip));
                    tableVertical.add(skillButton).pad(5).row();
                    skillButton.addListener(new ClickListener() {
                        @Override
                        public void clicked (InputEvent event, float x, float y) {
                            System.out.println("PRESSED");
                        }
                    });
                }
                unified.add(tableVertical);
            }
            unified.add().pad(30);
            this.add(unified);
        }
    }

    private Array<Skill> getListSkillsForRootNode(Skill.RootNode rootNode) {
        Array<Skill> listSkillsForRootNode = new Array<>();
        Array<Skill> allSkills = SkillFactory.getInstance().getAllSkills();
        for (Skill skill: allSkills) {
            if (skill.getRootNode().equals(rootNode)) {
                listSkillsForRootNode.add(skill);
            }
        }
        return listSkillsForRootNode;
    }

    private Array<Skill> getListSkillsForBranchPosition(Skill.BranchPosition branchPosition, Array<Skill> listForRootNode) {
        Array<Skill> listSkillsForBranchPosition = new Array<>();
        for (Skill skill: listForRootNode) {
            if (skill.getBranchPosition().equals(branchPosition)) {
                listSkillsForBranchPosition.add(skill);
            }
        }
        return listSkillsForBranchPosition;
    }

    public SkillTooltip getSkillTooltip() {
        return skillTooltip;
    }

}
