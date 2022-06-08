package juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import hilos.HiloCliente;
import mapas.gestorMapas.GestorMapas;
import mapas.gestorMapas.Mapa;
import pantallas.CargadorPantallas;
import pantallas.MenuOpciones;
import pantallas.MenuPrincipal;
import pantallas.PantallaPrincipal;
import personajes.PersonajePrincipal;
import utilidades.Comunicacion;
import utilidades.Entrada;

public class TwilightOfDarknessPrincipal extends Game {

    private Entrada entrada;
    private PersonajePrincipal jugador1;
    private PersonajePrincipal jugador2;
    private GestorMapas gestorMapas;
    private HiloCliente hc;

    @Override
    public void create() {
        gestorMapas = new GestorMapas();
        jugador1 = new PersonajePrincipal();
        jugador2 = new PersonajePrincipal();
        entrada = new Entrada();
        Gdx.input.setInputProcessor(entrada);
        gestorMapas.crearMapas(jugador1, jugador2, this, hc);
        setMenuPrincipal();
    }

    public void setPantallaPrincipal(Mapa mapa) {
        CargadorPantallas.pantallaPrincipal = new PantallaPrincipal(entrada, this, mapa, jugador1, jugador2, hc);
        setScreen(CargadorPantallas.pantallaPrincipal);
    }

    public void setMenuOpciones() {
        CargadorPantallas.menuOpciones = new MenuOpciones(entrada, this);
        setScreen(CargadorPantallas.menuOpciones);
    }

    public void setMenuPrincipal() {
        CargadorPantallas.menuPrincipal = new MenuPrincipal(entrada, this, gestorMapas);
        setScreen(CargadorPantallas.menuPrincipal);
    }

    public void establecerConexion(boolean fin){
        if(fin){
            hc = new HiloCliente(this);
            hc.start();
        }else{
            hc.terminar();
        }
    }

    public HiloCliente getHc(){
        return hc;
    }

    public PersonajePrincipal getJugador1() {
        return jugador1;
    }

    public PersonajePrincipal getJugador2() {
        return jugador2;
    }

    public GestorMapas getMapas() {return gestorMapas;}

}