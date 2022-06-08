package interfaces;

import hilos.HiloCliente;
import juego.TwilightOfDarknessPrincipal;
import mapas.gestorMapas.Mapa;
import personajes.PersonajePrincipal;

public interface Movible {

    public void comportamiento(TwilightOfDarknessPrincipal game, HiloCliente hc, String[] mensajes);

}
