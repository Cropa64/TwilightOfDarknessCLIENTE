package utilidades;

import com.badlogic.gdx.graphics.Texture;

public class Imagen {

    private Texture textura;

    public Imagen(String ruta) {
        textura = new Texture(ruta);
    }

    public Texture getTexture() {
        return textura;
    }

}
