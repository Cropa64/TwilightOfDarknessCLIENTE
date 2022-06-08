package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import juego.TwilightOfDarknessPrincipal;
import utilidades.*;


public class MenuOpciones implements Screen {

    Imagen fondo;
    Texture fondoTextura;

    Imagen menuOpciones;
    Texture menuOpcionesTex;

    Imagen volverMenu;
    Texture volverMenuTex;

    float volverX;
    float volverY;

    Entrada entrada;
    boolean cambiarPantalla;
    TwilightOfDarknessPrincipal game;
    float mouseX;
    float mouseY;
    float menuX;
    float menuY;
    boolean marcado, marcadoOpc, marcadoSalir;

    public MenuOpciones(Entrada entrada, TwilightOfDarknessPrincipal game) {
        this.entrada = entrada;
        this.game = game;
    }

    @Override
    public void show() {

        if (Utiles.r.nextInt(100) > 98) {
            fondo = new Imagen(Recursos.FONDO);
            fondoTextura = fondo.getTexture();
        } else {
            fondo = new Imagen(Recursos.FONDO_DARK);
            fondoTextura = fondo.getTexture();
        }


        menuOpciones = new Imagen(Recursos.MENU_OPCIONES);
        menuOpcionesTex = menuOpciones.getTexture();

        volverMenu = new Imagen(Recursos.VOLVER);
        volverMenuTex = volverMenu.getTexture();

        //menuX = (Recursos.ANCHO / 2) - (menuOpcionesTex.getWidth()/2);
        //menuY = (Recursos.ALTO / 2) - (menuOpcionesTex.getHeight()/2);

        volverX = (Recursos.ANCHO / 2) - (volverMenuTex.getWidth() / 2) + 100;
        volverY = (Recursos.ALTO / 2) - (volverMenuTex.getHeight() / 2);
    }

    @Override
    public void render(float delta) {

        actualizar();

        game.setMenuOpciones();

        Render.limpiarPantalla();

        Utiles.batch.begin();

        Utiles.batch.draw(fondoTextura, 0, 0, Recursos.ANCHO, Recursos.ALTO);
        Utiles.batch.draw(menuOpcionesTex, 0, 0, Recursos.ANCHO, Recursos.ALTO);

        Utiles.batch.draw(volverMenuTex, volverX, volverY - 200);

        if (marcado) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setMenuPrincipal();
            }
        }


        Utiles.batch.end();

    }

    private void actualizar() {
        posicionMouse();
    }

    private void posicionMouse() {

        mouseX = Gdx.input.getX();
        mouseY = Gdx.input.getY();

        // ===============
        if (mouseX > volverX && mouseX < volverX + volverMenuTex.getWidth() && mouseY > (volverY + 200) && mouseY < (volverY + 200) + volverMenuTex.getHeight()) {
            marcado = true;
        } else {
            marcado = false;
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {


    }

    @Override
    public void dispose() {
        fondoTextura.dispose();
        volverMenuTex.dispose();
        menuOpcionesTex.dispose();
    }

}
