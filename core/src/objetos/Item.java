package objetos;

import utilidades.Imagen;

public abstract class Item {

    private String nombre, tipo;
    private int precio;
    protected String rutaTextura;
    private Imagen icono;

    public Item(String nombre, String tipo, int precio, String rutaTextura) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.rutaTextura = rutaTextura;
        convertirATextura();
    }

    private void convertirATextura() {
        icono = new Imagen(rutaTextura);
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public Imagen getIcono() {
        return icono;
    }

}
