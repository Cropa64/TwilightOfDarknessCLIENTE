package utilidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import eventos.InventarioEventListener;
import objetos.Item;
import personajes.PersonajePrincipal;

public class Hud implements InventarioEventListener {

    private OrthographicCamera camaraHud;
    private ShapeRenderer shapeRendererHud;
    private SpriteBatch sb;
    private PersonajePrincipal jugador;
    private Imagen hotbar;
    private int[] posicionesHotBar = {-60, -20, 20, 60, 100};

    public Hud(PersonajePrincipal jugador) {
        this.jugador = jugador;
        shapeRendererHud = new ShapeRenderer();
        sb = new SpriteBatch();
        camaraHud = new OrthographicCamera(Recursos.ANCHO, Recursos.ALTO);
        camaraHud.update();

        Utiles.listeners.add(this);

        sb.setProjectionMatrix(camaraHud.combined);
        hotbar = new Imagen("objetos/hotbar.png");

        //fuente = new Fuente();
    }

    public void mostrarHud() {
        camaraHud.position.set(0, 0, 0);
        camaraHud.update();

        shapeRendererHud.setProjectionMatrix(camaraHud.combined);

        shapeRendererHud.setAutoShapeType(true);
        shapeRendererHud.begin();
        mostrarBarraEnergia();
        mostrarBarraVida();
        mostrarBarraItems();
        shapeRendererHud.end();
    }

    public void organizarInventario(Item[] hotbar, int[] contHotBar) {
        for (int i = 0; i < hotbar.length; i++) {
            if (hotbar[i] != null) {
                sb.begin();
                sb.draw(hotbar[i].getIcono().getTexture(), posicionesHotBar[i], -195);
                //fuente.escribir(Integer.toString(contHotBar[i]), (float)posicionesHotBar[i],(float)-195);
                sb.end();
            }
        }

    }

    private void mostrarBarraItems() {
        sb.begin();
        sb.draw(hotbar.getTexture(), -65, -200, 200, 42);
        sb.end();
    }

    private void mostrarBarraEnergia() {

        int anchoBarra = (100 * jugador.getStamina()) / jugador.getStaminaMax();

        shapeRendererHud.set(ShapeRenderer.ShapeType.Line);
        shapeRendererHud.setColor(Color.BLACK);
        shapeRendererHud.rect(-350, 200, 100, 20);

        shapeRendererHud.set(ShapeRenderer.ShapeType.Filled);
        shapeRendererHud.setColor(Color.BLUE);
        shapeRendererHud.rect(-351, 201, anchoBarra, 19);
    }

    private void mostrarBarraVida() {

        float anchoBarra = (100 * jugador.getVidaActual() / jugador.getVidaMaxima());

        shapeRendererHud.set(ShapeRenderer.ShapeType.Line);
        shapeRendererHud.setColor(Color.BLACK);
        shapeRendererHud.rect(-350, 150, 100, 20);

        shapeRendererHud.set(ShapeRenderer.ShapeType.Filled);
        shapeRendererHud.setColor(Color.GREEN);
        shapeRendererHud.rect(-351, 151, anchoBarra, 19);
    }

}
