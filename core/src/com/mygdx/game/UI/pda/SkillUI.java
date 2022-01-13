package com.mygdx.game.UI.pda;

import com.badlogic.gdx.graphics.Texture;
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
    private Array<Integer> learnedSkills;
    private Array<Integer> accessibleSkills;
    private Array<Integer> inaccessibleSkills;
    private Drawable buttonNoneSkill1;
    private Drawable buttonNoneSkill2;
    private Drawable buttonNoneSkill3;

    public SkillUI() {
        super("Skill UI", FadingReality.getUiSkin());
        skillTooltip = new SkillTooltip();
    }

    public void createSkillTree(final Entity player) {
        this.clear();

        learnedSkills = Skill.getLearnedSkills(player);
        accessibleSkills = Skill.getAccessibleSkills(player);
        inaccessibleSkills = Skill.getInaccessibleSkills(player);

        buttonNoneSkill1 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/ButtonNoneSkill1.png")));
        buttonNoneSkill2 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/ButtonNoneSkill2.png")));
        buttonNoneSkill3 = new TextureRegionDrawable(new TextureRegion(new Texture("textures/ButtonNoneSkill3.png")));

        for (Skill.RootNode rootNode : Skill.RootNode.values()) {
            Table unified = new Table();
            unified.add();
            SkillButton rootSkill = new SkillButton(buttonNoneSkill1);
            unified.add(rootSkill).padBottom(30).row();
            Array<Skill> listSkillsForRootNode = getListSkillsForRootNode(rootNode);
            for (Skill.BranchPosition branchPosition: Skill.BranchPosition.values()) {
                Table tableVertical = new Table();
                Array<Skill> listSkillsForBranchPosition = getListSkillsForBranchPosition(branchPosition, listSkillsForRootNode);
                listSkillsForBranchPosition.reverse();
                for (int i = 0; i < listSkillsForBranchPosition.size; i++) {
                    final Skill skill = listSkillsForBranchPosition.get(i);
                    Drawable mark = getDrawableForMark(skill.getId());
                    SkillButton skillButton = new SkillButton(mark, skill);
                    skillButton.addListener(new SkillTooltipListener(skillTooltip));
                    tableVertical.add(skillButton).pad(5).row();
                    skillButton.addListener(new ClickListener() {
                        @Override
                        public void clicked (InputEvent event, float x, float y) {
                            skill.unlockSkill(120, player);
                            clear();
                            createSkillTree(player);
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

    private Drawable getDrawableForMark(int id) {
        if (learnedSkills.contains(id, true)) {
            return buttonNoneSkill1;
        } else if (accessibleSkills.contains(id, true)) {
            return buttonNoneSkill2;
        } else {
            return buttonNoneSkill3;
        }
    }

    public SkillTooltip getSkillTooltip() {
        return skillTooltip;
    }

}
