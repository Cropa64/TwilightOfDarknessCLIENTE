package utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Render {

    public static void limpiarPantalla() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
