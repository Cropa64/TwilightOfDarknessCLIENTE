package objetos.pociones;

public class PocionVida extends Pocion {

    private int curacion = 20;

    public PocionVida() {
        super("Pocion de vida", "pocionCurativa", 20, "objetos/pociones/pocionVida.png");
    }

    public int getCuracion() {
        return curacion;
    }

}
