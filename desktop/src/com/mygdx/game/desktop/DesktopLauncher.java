package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.FadingReality;
import com.mygdx.game.tools.DesktopSplashWorker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title= FadingReality.TITLE;
		config.width= FadingReality.WIDTH;
		config.height= FadingReality.HEIGHT;
		FadingReality fadingReality = new FadingReality();
		fadingReality.setSplashWorker(new DesktopSplashWorker());
//		config.forceExit=false;
//		config.fullscreen=true;
//		config.resizable=true;
//		config.useGL30=true;
//		config.foregroundFPS = 9999;
		new LwjglApplication(fadingReality, config);
	}
}
