package objetos.armas;

public class EspadaCarton extends Arma {

    private int danio = 10;

    public EspadaCarton() {
        super("Espada de carton", "arma", 40, "objetos/armas/sword.png");
    }

    public int getDanio() {
        return danio;
    }

}
