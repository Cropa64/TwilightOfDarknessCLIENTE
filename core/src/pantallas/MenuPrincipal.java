package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import juego.TwilightOfDarknessPrincipal;
import mapas.gestorMapas.GestorMapas;
import utilidades.*;

public class MenuPrincipal implements Screen {

    Imagen fondo;

    Imagen botonJugar, botonJugarMarcado;
    Imagen botonSalir, botonSalirMar;
    Imagen botonOpcion, botonOpcionMar;

    Texture fondoTextura;

    Texture botonJugarTex, botonJugarMarTex;
    Texture botonSalirTex, botonSalirMarTex;
    Texture botonOpcionTex, botonOpcionMarTex;

    Entrada entrada;
    boolean cambiarPantalla;
    TwilightOfDarknessPrincipal game;
    float mouseX;
    float mouseY;
    float botonJugarX;
    float botonJugarY;
    boolean marcado, marcadoOpc, marcadoSalir;
    GestorMapas gestorMapas;
    boolean conectar = true;
    boolean desconectar = false;
    Tiempo tiempo = new Tiempo();

    public MenuPrincipal(Entrada entrada, TwilightOfDarknessPrincipal game, GestorMapas gestorMapas) {
        this.entrada = entrada;
        this.game = game;
        this.gestorMapas = gestorMapas;
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

        botonJugar = new Imagen(Recursos.BOTONNORMAL);
        botonJugarTex = botonJugar.getTexture();

        botonJugarMarcado = new Imagen(Recursos.BOTONMARCADO);
        botonJugarMarTex = botonJugarMarcado.getTexture();


        botonOpcion = new Imagen(Recursos.BOTON_OPCION);
        botonOpcionTex = botonOpcion.getTexture();

        botonOpcionMar = new Imagen(Recursos.BOTON_OPCION_MARCADO);
        botonOpcionMarTex = botonOpcionMar.getTexture();


        botonSalir = new Imagen(Recursos.BOTON_SALIR);
        botonSalirTex = botonSalir.getTexture();

        botonSalirMar = new Imagen(Recursos.BOTON_SALIR_MARCADO);
        botonSalirMarTex = botonSalirMar.getTexture();


        botonJugarX = (Recursos.ANCHO / 2) - (botonJugarTex.getWidth() / 2);
        botonJugarY = (Recursos.ALTO / 2) - (botonJugarTex.getHeight() / 2);


        Recursos.menuSound.play();

    }

    @Override
    public void render(float delta) {

        actualizar();
        Render.limpiarPantalla();

        Utiles.batch.begin();
        Utiles.batch.draw(fondoTextura, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!marcado) {
            Utiles.batch.draw(botonJugarTex, botonJugarX, botonJugarY);
        } else {
            Utiles.batch.draw(botonJugarMarTex, botonJugarX, botonJugarY);

            // TODO REVISAR !!!!!
            if (Gdx.input.isTouched() || entrada.pressedEnter) {
                int segundos = 0;
                boolean fin = false, conexion = false;

                game.establecerConexion(conectar);

                 tiempo.contar(4000);
                 do{
                  segundos = tiempo.getSegundos();
                  System.err.println("ESPERANDO CONEXION...");
                 if((segundos == 5) && (!Recursos.empieza)){
                   fin = true;
                   conexion = false;
                   System.out.println("Servidor no encontrado.");
                 game.establecerConexion(desconectar);
                   }else if((Recursos.empieza)){
                        fin = true;
                        conexion = true;
                    }
                 }while(!fin);

                  tiempo.detener();
                  if(conexion) {
                    game.dispose();
                    game.setPantallaPrincipal(gestorMapas.getMapaDungeon1());
                    Recursos.menuSound.stop();
                  }
            }


            // ================================= //
        }

        if (!marcadoOpc) {
            Utiles.batch.draw(botonOpcionTex, botonJugarX, botonJugarY - 70);
        } else {
            Utiles.batch.draw(botonOpcionMarTex, botonJugarX, botonJugarY - 70);

            // ===============
            if (Gdx.input.isTouched() || entrada.pressedEnter) {
                game.dispose();
                game.setMenuOpciones();
            }

            // ===============
        }

        if (!marcadoSalir) {
            Utiles.batch.draw(botonSalirTex, botonJugarX, botonJugarY - (70 * 2));
        } else {
            Utiles.batch.draw(botonSalirMarTex, botonJugarX, botonJugarY - (70 * 2));
            // ===============
            if (Gdx.input.isTouched() || entrada.pressedEnter) {
                game.dispose();
                this.dispose();
                Gdx.app.exit();
            }

            // ===============
        }

        Utiles.batch.end();

    }

    private void actualizar() {

        posicionMouse();

//		if(entrada.pressedEnter) {
//			cambiarPantalla = true;
//		}
//
//		if(cambiarPantalla) {
//			game.setPantallaPrincipal();
//		}


    }

    private void posicionMouse() {

        mouseX = Gdx.input.getX();
        mouseY = Gdx.input.getY();

        // ===============
        if (mouseX > botonJugarX && mouseX < botonJugarX + botonJugarTex.getWidth() && mouseY > botonJugarY && mouseY < botonJugarY + botonJugarTex.getHeight()) {
            marcado = true;
        } else {
            marcado = false;
        }

        if (mouseX > botonJugarX && mouseX < botonJugarX + botonOpcionTex.getWidth() && mouseY > (botonJugarY + 75) && mouseY < (botonJugarY + 75) + botonOpcionTex.getHeight()) {
            marcadoOpc = true;
        } else {
            marcadoOpc = false;
        }

        if (mouseX > botonJugarX && mouseX < botonJugarX + botonSalirTex.getWidth() && mouseY > (botonJugarY + (75 * 2)) && mouseY < (botonJugarY + (75 * 2)) + botonSalirTex.getHeight()) {
            marcadoSalir = true;
        } else {
            marcadoSalir = false;
        }
        // ===============
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
        botonJugarMarTex.dispose();
        botonJugarTex.dispose();
    }

}