package utilidades;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;

public class Recursos {

    public static int horasJugadas, minutosJugados;
    public static int posJugadorX = 400, posJugadorY = 200;
    public static boolean empieza = false;
    public static boolean conexionAnterior = false;
    public static int nroCliente;
    public static final int ANCHO = Gdx.graphics.getWidth();
    public static final int ALTO = Gdx.graphics.getHeight();

    public static final int ANCHO_TILE = 16;
    public static final int ALTO_TILE = 16;

    public static final String FONDO = "menus/menuPrincipal/posiblefondo2.jpg";
    public static final String FONDO_DARK = "menus/menuPrincipal/posiblefondo3.jpg";
    public static final String BOTONNORMAL = "menus/menuPrincipal/botonJugar.png";
    public static final String BOTONMARCADO = "menus/menuPrincipal/botonJugarMarcado.png";
    public static final String BOTON_OPCION = "menus/menuPrincipal/botonOpc.png";
    public static final String BOTON_OPCION_MARCADO = "menus/menuPrincipal/botonOpcMarcado.png";
    public static final String BOTON_SALIR_MARCADO = "menus/menuPrincipal/botonSalirMarcado.png";
    public static final String BOTON_SALIR = "menus/menuPrincipal/botonSalir.png";
    public static final Music menuSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("menus/menuPrincipal/Sounds/Juhani Junkala - Post Apocalyptic Wastelands [Loop Ready].ogg", FileType.Internal));
    public static final String MENU_OPCIONES = "menus/menuPrincipal/opciones/menuOpciones2.png";
    public static final String VOLVER = "menus/menuPrincipal/opciones/botonesMenu/volver.png";
}
