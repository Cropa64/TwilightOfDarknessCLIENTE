package objetos.armaduras;

public class PetoCarton extends Armadura {

    private int resistencia = 10;

    public PetoCarton() {
        super("Peto de carton", "armadura", 30, "objetos/armaduras/peto.png");
    }

    public int getResistencia() {
        return resistencia;
    }

}
