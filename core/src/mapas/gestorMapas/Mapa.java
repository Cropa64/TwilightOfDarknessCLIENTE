package mapas.gestorMapas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import enemigos.Alien;
import personajes.Entidad;
import personajes.PersonajePrincipal;
import utilidades.Recursos;
import utilidades.Utiles;

public abstract class Mapa {

    protected TiledMap mapa;
    protected OrthogonalTiledMapRenderer renderer;
    protected OrthographicCamera camara;
    protected TmxMapLoader cargadorMapa;
    protected PersonajePrincipal jugador1, jugador2;
    protected GestorMapas gestorMapas;

    public Mapa(PersonajePrincipal jugador1, PersonajePrincipal jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        cargadorMapa = new TmxMapLoader();
        mapa = new TiledMap();
        camara = new OrthographicCamera(Recursos.ANCHO, Recursos.ALTO);
    }

    public void setearCamara(float xJugador, float yJugador) {

        Utiles.batch.setProjectionMatrix(camara.combined);
        camara.position.set(xJugador, yJugador, 0); //Centrar camara en el jugador
        camara.update();
        renderer.setView(camara);

    }

    public abstract void setPosicionJugador(PersonajePrincipal jugador);

    public abstract void renderizar();

    public abstract void crear();

    public abstract boolean comprobarColision(Entidad entidad);

    public abstract void eliminarEnemigos(int idAlien);
//    public abstract void deleteEnemy(int idAlien);
    public abstract int getEnemigos(int idAlien);

//	public abstract boolean comprobarColisionEnemigos(PersonajePrincipal jugador);

//    public abstract boolean comprobarSalidaMapa();
//
//    public abstract Mapa cambioMapa();

    public abstract void mostrarColisiones();

    public void zoom(boolean acercar) {
        if (acercar) {
            camara.zoom += 0.1f;
        } else {
            if (camara.zoom > 0.5f) {
                camara.zoom -= 0.1f;
            }
        }
    }

    public TiledMap getMapa() {
        return mapa;
    }

    public OrthographicCamera getCamara() {
        return camara;
    }

    public abstract Array<Rectangle> getRectColision();

    public abstract Array<Polygon> getPoliColision();

    public abstract Array<Rectangle> getZonasCambioMapa();

    public abstract Rectangle getLimiteMapa();

    public void dispose() {
        renderer.dispose();
        mapa.dispose();
    }

    public abstract Alien getAliens(int i);

    public abstract int getAliensSize();
}
