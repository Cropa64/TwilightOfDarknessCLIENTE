package eventos;

import objetos.Item;

import java.util.EventListener;

public interface InventarioEventListener extends EventListener {

    public void organizarInventario(Item[] hotbar, int[] contHotBar);

}
