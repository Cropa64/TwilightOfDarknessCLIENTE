package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entidad extends Sprite {

    protected Vector2 posicion = new Vector2();

    public abstract void dibujar(Texture textura);

    public abstract void dibujar(TextureRegion tr);

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(float x, float y) {
        this.posicion.x = x;
        this.posicion.y = y;
        setPosicionRectangulo(this.posicion.x, this.posicion.y);
    }

    private void setPosicionRectangulo(float x, float y) {
        this.getRectangulo().setX(x);
        this.getRectangulo().setY(y);
    }

    public abstract Rectangle getRectangulo();

}
