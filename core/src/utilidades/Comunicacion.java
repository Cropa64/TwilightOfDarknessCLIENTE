package utilidades;

import com.badlogic.gdx.utils.Array;
import enemigos.Alien;
import hilos.HiloCliente;
import juego.TwilightOfDarknessPrincipal;
import personajes.PersonajePrincipal;

public class Comunicacion {

    private PersonajePrincipal pp;
    private String coordenadasLocal;
    private String coordenadasAlienLocal;
    private String coordenadasGuia;
    private boolean first = false, firstUpdate;
    private HiloCliente hc;
    private TwilightOfDarknessPrincipal game;
    private Array<Alien> aliens = new Array<Alien>();

    public Comunicacion(HiloCliente hiloCliente, TwilightOfDarknessPrincipal game) {
        this.hc = hiloCliente;
        this.game = game;
    }


    public void accionJugador(String[] msg) {
        int id = Integer.parseInt(msg[0]);
        if(id == 0){

            // if(msg[1].equals("actualizarPos")){
            float posX = Float.parseFloat(msg[2]);
            float posY = Float.parseFloat(msg[3]);
            game.getJugador1().setPosicion(posX, posY);
            //}
        }else{
            //if(msg[1].equals("actualizarPos")){
            float posX = Float.parseFloat(msg[2]);
            float posY = Float.parseFloat(msg[3]);
            game.getJugador2().setPosicion(posX, posY);
            //}
        }

    }

    public void peleaAlien(String[] msg) {
        int posAlien = Integer.parseInt(msg[0]);
        game.getMapas().getMapaDungeon1().getAliens(posAlien).comportamiento(game, hc, msg);
    }

    public void movimientoAlien(String[] msg) {
        int posAlien = Integer.parseInt(msg[0]);
        System.out.println("MOVA-"+posAlien);
        float posX = Float.parseFloat(msg[3]);
        float posY = Float.parseFloat(msg[4]);

        game.getMapas().getMapaDungeon1().getAliens(posAlien).comportamiento(game, hc, msg);
//        game.getMapas().getMapaDungeon1().getAliens(posAlien).setPosicion(posX, posY);

    }

    // TODO ESTE ARRAY SE UTILIZARIA PARA ACTUALIZAR A AQUEL CLIENTE QUE SE DESCONECTO Y ANTERIOREMENTE ESTABLECIO UNA CONEXION CON OTRO CLIENTE Y EL SERVIDOR.
    //la idea es que este array pueda actualizar las posiciones de los aliens y no cree a aquellos que no son necesarios, por haber sido eliminados.
    public Array<Alien> actualizacionAlien(String[] msg) {
        int idAlien = Integer.parseInt(msg[1]);
        int posMemoria = Integer.parseInt(msg[2]);
        float posX = Float.parseFloat(msg[3]);
        float posY = Float.parseFloat(msg[4]);

            aliens.add(new Alien(idAlien));
            aliens.get(posMemoria).setPosicion(posX, posY);
            aliens.get(posMemoria).getRectangulo().setX(posX);
            aliens.get(posMemoria).getRectangulo().setY(posY);
            aliens.get(posMemoria).getCirculoAlien().setX(posX);
            aliens.get(posMemoria).getCirculoAlien().setY(posY);
            aliens.get(posMemoria).setIdAlien(idAlien);

        return aliens;
    }

    public void eliminarAlien(String idAlien){
        int id = Integer.parseInt(idAlien);
        game.getMapas().getMapaDungeon1().eliminarEnemigos(id);
    }
}