package com.mygdx.game.tools.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.ChromaticAberrationEffect;
import com.crashinvaders.vfx.effects.FilmGrainEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.RadialBlurEffect;
import com.mygdx.game.FadingReality;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.world.MapManager;

public class ShaderVFXManager {

    private ShaderProgram shader;
    private boolean shaderSilhouette;

    private VfxManager vfxManager;
    private ChromaticAberrationEffect vfxEffect;
    private FilmGrainEffect vfxEffect1;
    private OldTvEffect vfxEffect2;
    private RadialBlurEffect vfxEffect3;
    private boolean VFXEffectOnOff;

    private float forEffect = 0.1f;


    public ShaderVFXManager() {
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/mask.vert"), Gdx.files.internal("shaders/mask.frag"));
        if(shader.isCompiled()) {
            System.out.println("Shader2 is compiled");
        }

        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
        vfxEffect = new ChromaticAberrationEffect(3);
        vfxEffect.setMaxDistortion(forEffect);
        vfxEffect2 = new OldTvEffect();
        vfxEffect1 = new FilmGrainEffect();
        vfxEffect3 = new RadialBlurEffect(2);
        vfxManager.addEffect(vfxEffect);
        //        vfxManager.addEffect(vfxEffect2);
        //        vfxManager.addEffect(vfxEffect1);
        //        vfxManager.addEffect(vfxEffect3);
    }

    public void updateShader(FadingReality game, Entity player, MapManager mapMgr, float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            shaderSilhouette = !shaderSilhouette;
        }
        if(shaderSilhouette){
            game.getBatch().setShader(shader);
            player.update(mapMgr, game.getBatch(), delta);
            player.draw(game.getBatch(), delta);
            game.getBatch().setShader(null);
        }
        Gdx.gl20.glDisable(GL20.GL_STENCIL_TEST);
    }

    public void updateVFX() {
        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
    }

    public void renderVFX() {
        vfxManager.endInputCapture();
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            VFXEffectOnOff = !VFXEffectOnOff;
        }
        if(VFXEffectOnOff){
            if (forEffect <= 1.5) {
                forEffect += 0.01;
                vfxEffect.setMaxDistortion(forEffect);
//                    System.out.println(forEffect);
            }
            vfxManager.applyEffects();
        } else {
            if (forEffect >= 0.02) {
                forEffect -= 0.01;
                vfxEffect.setMaxDistortion(forEffect);
//                    System.out.println(forEffect);
            }
            vfxManager.applyEffects();
            //            forEffect = 0f;
        }
        vfxManager.renderToScreen();
    }

    public void resize(int width, int height) {
        vfxManager.resize(width, height);
    }

    public void dispose() {
        vfxManager.dispose();
        vfxEffect.dispose();
    }

}

