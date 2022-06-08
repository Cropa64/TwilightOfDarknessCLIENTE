package utilidades;

import java.util.Timer;
import java.util.TimerTask;

public class Tiempo  {

    private Timer timer = new Timer();
    private int segundos=0;

    //Clase interna que funciona como contador
    class Contador extends TimerTask {
        public void run() {
            segundos++;
            System.out.println("segundo: " + segundos);
        }
    }
    //Crea un timer, inicia segundos a 0 y comienza a contar
    public void contar(int tiempo)
    {
        this.segundos=0;
        timer = new Timer();
        timer.schedule(new Contador(), 0, tiempo);
    }
    //Detiene el contador
    public void detener() {
        timer.cancel();
    }
    //Metodo que retorna los segundos transcurridos
    public int getSegundos()
    {
        return this.segundos;
    }
}