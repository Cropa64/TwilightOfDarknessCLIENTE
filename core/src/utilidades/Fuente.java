package utilidades;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Fuente {

    private BitmapFont fuente;

    public Fuente() {
        fuente = new BitmapFont(new FileHandle("fuentes/fuente.ttf"));
        fuente.setColor(Color.BLACK);
        fuente.getData().setScale(10);
    }

    public void escribir(String texto, float x, float y) {
        fuente.draw(Utiles.batch, texto, x, y);
    }

    public void dispose() {
        fuente.dispose();
    }

}
