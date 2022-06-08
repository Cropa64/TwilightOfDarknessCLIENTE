package utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.EventListener;
import java.util.Random;

public abstract class Utiles {

    public static Texture imagenPelea = new Texture("menus/pelea/vspelea.jpg");

    public static SpriteBatch batch = new SpriteBatch();

    public static Timer timer = new Timer();

    public static void dibujar(Texture textura, float x, float y) {
        batch.begin();
        batch.draw(textura, x, y);
        batch.end();
    }

    public static void dibujar(Texture textura, float x, float y, float width, float height) {
        batch.begin();
        batch.draw(textura, x, y, width, height);
        batch.end();
    }

    public static void dibujar(TextureRegion tr, float x, float y, float width, float height) {
        batch.begin();
        batch.draw(tr, x, y, width, height);
        batch.end();
    }

    public static ShapeRenderer sr = new ShapeRenderer();

    public static Array<EventListener> listeners = new Array<EventListener>();

    public static int mapaIndicadorPos = 0;
//	public static Texture personajePrincipalTextura = new Texture("personajes/Ladron/thief.png");
//	public static Sprite personajePrincipalSprite = new Sprite(personajePrincipalTextura);
//	
//	public static Texture enemigoTextura = new Texture("personajes/personajePrincipal/dodoQuietoIzquierda.png");
//	public static Sprite enemigoSprite = new Sprite(enemigoTextura);

//	public static Texture ppAbajo = new Texture("personajes/personajePrincipal/personajeQuietoAbajo.png");
//	public static Sprite ppAbajoSprite = new Sprite(ppAbajo);
//	
//	public static Texture ppArriba = new Texture("personajes/personajePrincipal/personajeQuietoArriba.png");
//	public static Sprite ppArribaSprite = new Sprite(ppArriba);
//	
//	public static Texture ppDerecha = new Texture("personajes/personajePrincipal/personajeQuietoDerecha.png");
//	public static Sprite ppDerechaSprite = new Sprite(ppDerecha);
//	
//	public static Texture ppIzquierda = new Texture("personajes/personajePrincipal/personajeQuietoIzquierda.png");
//	public static Sprite ppIzquierdaSprite = new Sprite(ppIzquierda);

    public static Random r = new Random();
}
