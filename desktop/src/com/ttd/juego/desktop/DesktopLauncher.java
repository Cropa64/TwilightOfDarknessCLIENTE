package com.ttd.juego.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import juego.TwilightOfDarknessPrincipal;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new TwilightOfDarknessPrincipal(), config);

        config.title = "Twilight of Darkness";
        config.fullscreen = false;
        config.width = 720;
        config.height = 480;
        config.fullscreen = false;
        config.useGL30 = false;
        config.resizable = true;
        config.vSyncEnabled = false;
    }
}
