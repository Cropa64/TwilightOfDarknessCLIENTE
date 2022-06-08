package mapas.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemigos.Alien;
import hilos.HiloCliente;
import juego.TwilightOfDarknessPrincipal;
import mapas.gestorMapas.Mapa;
import personajes.Entidad;
import personajes.PersonajePrincipal;
import utilidades.Recursos;
import utilidades.Tiempo;
import utilidades.Utiles;

public class Dungeon1 extends Mapa {

    private MapObjects aliensObj;
    private Array<Alien> aliens;
    private Rectangle rectanguloPiso, rectanguloPelea, rectanguloVictoria;
    private Array<Rectangle> colisiones;
    private boolean cambioMapa;
    protected Alien alienPelea;
    private Vector2 posicionPelea;
    private float tiempoVs, tiempoDt;
    private boolean entroPelea = false;
    private boolean victoria = false, derrota = false;
    private PersonajePrincipal jugadorPelea;
    private PersonajePrincipal jugadorLocal;

    private TiledMapTileLayer capaSuelo;
    private TiledMapTileLayer capaPuertas;
    private TiledMapTileLayer capaDecoracion;
    private TiledMapTileLayer capaNumeros;
    private MapObjects posiciones, colisionables;
    private TwilightOfDarknessPrincipal game;
    private HiloCliente hc;
    private boolean flag = false;
    private boolean flag2 = false;

    public Dungeon1(PersonajePrincipal jugador1, PersonajePrincipal jugador2, TwilightOfDarknessPrincipal game, HiloCliente hc) {
        super(jugador1, jugador2);
        this.game = game;
        posicionPelea = new Vector2();
        this.hc = hc;
    }

    @Override
    public void setPosicionJugador(PersonajePrincipal jugador) {
        if (jugador.isVieneDePelea()) {
            jugador.setPosicion(posicionPelea.x, posicionPelea.y);
            jugador.setVieneDePelea(false);
        } else {
            System.out.println("ENTRO AL ELSE");
            posiciones = mapa.getLayers().get("posiciones").getObjects();

            for (int i = 0; i < posiciones.getCount(); i++){
                if(!flag && posiciones.get(i).getName().equals("posInicial") && !jugador.isEnCombate()){
                    jugador1.setPosicion((float)posiciones.get(i).getProperties().get("x"),(float)posiciones.get(i).getProperties().get("y"));
                    jugador2.setPosicion((float)posiciones.get(i).getProperties().get("x"),(float)posiciones.get(i).getProperties().get("y"));
                    flag = true;
                }
//                else if(Recursos.conexionAnterior) {
//                    String[] coord = game.getHc().getPosActualizacion();
//                    if(game.getHc().getNroCliente() == 0){
//                        jugador1.setPosicion(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]));
//                    }else{
//                        jugador2.setPosicion(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]));
//                    }
//                }
                if(posiciones.get(i).getName().equals("posPelea") && jugador.isEnCombate()){
                    jugador.setPosicion((float)posiciones.get(i).getProperties().get("x"), (float)posiciones.get(i).getProperties().get("y"));
                }
                if(posiciones.get(i).getName().equals("alienPeleaPos") && jugador.isEnCombate()){
                    alienPelea.setPosicion((float)posiciones.get(i).getProperties().get("x"), (float)posiciones.get(i).getProperties().get("y"));
                }
                if(posiciones.get(i).getName().equals("felicitacion") && victoria){
                    jugador.setPosicion((float)posiciones.get(i).getProperties().get("x"), (float)posiciones.get(i).getProperties().get("y"));
                }
                if(posiciones.get(i).getName().equals("derrota") && derrota){
                    jugador.setPosicion((float)posiciones.get(i).getProperties().get("x"), (float)posiciones.get(i).getProperties().get("y"));
                }
            }
        }
    }

    @Override
    public void renderizar() {
        update();

        renderizarSuelo();
        renderizarDeco();
        jugador1.movimiento();
        jugador2.movimiento();
        renderizarPuertas();

        if(!flag2){
            asignarJugador();
            flag2 = true;
        }
        dibujarAliens();

        //TODO ENVIO DE DATOS ALIENS!!!!!!!!!!!
        //CUALQUIER PROBLEMA, COMENTAR DATOS ALIENS
//        comprobarVidaAliens();
        comprobarPelea();
        comprobarVictoria();
    }

    private void asignarJugador() {
        if(game.getHc().getNroCliente() == 0){
            jugadorPelea = jugador1;
        }else{
            jugadorPelea = jugador2;
        }
    }

    public void comprobarVictoria(){
        if(!victoria){
            for(int i = 0; i < posiciones.getCount(); i++){
                if(posiciones.get(i).getName().equals("victoria")){
                    RectangleMapObject rectVictoria = (RectangleMapObject) posiciones.get(i);
                    Rectangle vict = rectVictoria.getRectangle();
                    if(Intersector.overlaps(jugadorPelea.getRectangulo(),vict)){
                        victoria = true;
                        this.setPosicionJugador(jugadorPelea);
                    }
                }
            }
        }
        if(derrota){
            jugadorPelea.setEnCombate(false);
            this.setPosicionJugador(jugadorPelea);
        }

    }

    public void renderizarPuertas() {
        renderer.getBatch().begin();
        renderer.renderTileLayer(capaPuertas);
        renderer.getBatch().end();
    }

    public void renderizarDeco() {
        renderer.getBatch().begin();
        renderer.renderTileLayer(capaDecoracion);
        renderer.getBatch().end();
    }

    public void renderizarSuelo() {
        renderer.getBatch().begin();
        renderer.renderTileLayer(capaSuelo);
        renderer.getBatch().end();
    }

    public void dibujarAliens() {
        Utiles.sr.begin(ShapeType.Line);
        for (int i = 0; i < aliens.size; i++) {
            aliens.get(i).movimiento();
//            aliens.get(i).mostrarColisiones();
        }
        Utiles.sr.end();

        for (int j = 0; j < aliens.size; j++) {
            if (!aliens.get(j).isMuerto()) {
                aliens.get(j).barraVida();
            }
        }
    }

    private void update() {

    }

//    private void comprobarVidaAliens() {
//        for (int i = 0; i < aliens.size; i++) {
//            if (aliens.get(i).getVida() == 0) {
//                aliens.get(i).setMuerto(true);
//            }
//        }
//    }

    @Override
    public void eliminarEnemigos(int idAlien) {
       for (int i = 0; i < aliens.size; i++){
           if(aliens.get(i).getIdAlien() == idAlien){
               aliens.removeIndex(i);
           }
       }
//                aliensObj.remove(i);
    }

//    public void deleteEnemy(int idAlien){
//        aliens.removeIndex(idAlien);
//    }

    @Override
    public int getEnemigos(int idAlien) {
        for (int i = 0; i < aliens.size; i++) {
            if (aliens.get(i).getIdAlien() == idAlien) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void crear() {
        mapa = cargadorMapa.load("mapas/dungeons/dungeon1/dungeon1.tmx");
        crearEnemigos();
        crearCapas();
        renderer = new OrthogonalTiledMapRenderer(mapa);
        camara.update();

        //TODO REVISAR
        setPosicionJugador(jugador1);
        setPosicionJugador(jugador2);
    }

    public void crearCapas() {

        capaSuelo = (TiledMapTileLayer) mapa.getLayers().get("suelo");
        capaDecoracion = (TiledMapTileLayer) mapa.getLayers().get("deco");
        capaPuertas = (TiledMapTileLayer) mapa.getLayers().get("puertas");

        colisiones = new Array<Rectangle>();

        MapObjects piso = mapa.getLayers().get("pisoObj").getObjects();
        colisionables = mapa.getLayers().get("colisionables").getObjects();

        for (int i = 0; i < colisionables.getCount(); i++) {
            RectangleMapObject rectColiObj = (RectangleMapObject) colisionables.get(i);
            Rectangle rectColi = rectColiObj.getRectangle();
            colisiones.add(new Rectangle(rectColi.getX(), rectColi.getY(), rectColi.getWidth(), rectColi.getHeight()));
        }

        System.out.println(piso.getCount());

        for (int i = 0; i < piso.getCount(); i++){
            if(piso.get(i).getName().equals("mapa")){
                RectangleMapObject rectPiso = (RectangleMapObject) piso.get(i);
                rectanguloPiso = rectPiso.getRectangle();
            }
            if(piso.get(i).getName().equals("pelea")){
                RectangleMapObject rectPelea = (RectangleMapObject) piso.get(i);
                rectanguloPelea = rectPelea.getRectangle();
            }
            if(piso.get(i).getName().equals("victoria")){
                RectangleMapObject rectVictoria = (RectangleMapObject) piso.get(i);
                rectanguloVictoria = rectVictoria.getRectangle();
            }
        }

    }

    public void mostrarColisiones() {
        Utiles.sr.begin(ShapeType.Line);

        Utiles.sr.setColor(Color.BLUE);
        Utiles.sr.rect(0, 0, getLimiteMapa().getWidth(), getLimiteMapa().getHeight());

        for (int i = 0; i < getRectColision().size; i++) {
            Utiles.sr.rect(getRectColision().get(i).getX(), getRectColision().get(i).getY(), getRectColision().get(i).getWidth(), getRectColision().get(i).getHeight());
        }

        Utiles.sr.end();
    }

    public void crearEnemigos() {
        aliensObj = new MapObjects();
        aliensObj = mapa.getLayers().get("aliens").getObjects();
        aliens = new Array<Alien>();
//
//        if(hc.isConexionAnterior()){
//            do{
//                System.out.println("ACTUALIZACION");
//            }while(!Recursos.actualizacion);
//           // game.getHc().getCom().actualizacionAlien();
//        }else{
            for (int i = 0; i < aliensObj.getCount(); i++) {
                aliens.add(new Alien(i));
                aliens.get(i).setPosicion((float) aliensObj.get(i).getProperties().get("x"), (float) aliensObj.get(i).getProperties().get("y"));
                aliens.get(i).getRectangulo().setX((float) aliensObj.get(i).getProperties().get("x"));
                aliens.get(i).getRectangulo().setY((float) aliensObj.get(i).getProperties().get("y"));
                aliens.get(i).getCirculoAlien().setX((float) aliensObj.get(i).getProperties().get("x"));
                aliens.get(i).getCirculoAlien().setY((float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i] = new Alien();
//			aliens[i].setPosicion((float) aliensObj.get(i).getProperties().get("x"), (float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i].getRectangulo().setX((float) aliensObj.get(i).getProperties().get("x"));
//			aliens[i].getRectangulo().setY((float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i].getCirculoAlien().setX((float) aliensObj.get(i).getProperties().get("x"));
//			aliens[i].getCirculoAlien().setY((float) aliensObj.get(i).getProperties().get("y"));
//            }
        }


    }

    @Override
    public boolean comprobarColision(Entidad entidad) {
        boolean colision = false;

        for (int i = 0; i < colisiones.size; i++) {
            if (Intersector.overlaps(entidad.getRectangulo(), colisiones.get(i))) {
                colision = true;
            }
        }

        if (!rectanguloPiso.contains(entidad.getRectangulo()) && !rectanguloPelea.contains(entidad.getRectangulo()) && !rectanguloVictoria.contains(entidad.getRectangulo())) {
            System.out.println("Fuera del mapa");
            colision = true;
        }
        return colision;
    }

//    public void imagenPelea(){
//        Tiempo tiempo = new Tiempo();
//        int segundos = 0;
//
//        tiempo.contar(5000);
//        do{
//            segundos = tiempo.getSegundos();
//            Utiles.dibujar(Utiles.imagenPelea, 0, 0, Recursos.ANCHO, Recursos.ALTO);
//        }while(segundos < 5);
//        tiempo.detener();
//    }

    public void comprobarPelea() {

        int i = 0;
        boolean isMuerto = false;
        do {
            if (aliens.notEmpty()) {
                if (!aliens.get(i).isMuerto()) {
                    if ((aliens.get(i).getRectangulo().overlaps(jugadorPelea.getRectangulo()) && !jugadorPelea.isEnCombate())) {

                        alienPelea = aliens.get(i);
                        aliens.get(i).setEnCombate(true);
                        jugadorPelea.setEnCombate(true);

                        posicionPelea.x = jugadorPelea.getPosicion().x;
                        posicionPelea.y = jugadorPelea.getPosicion().y;

                        if(!entroPelea){
                            this.setPosicionJugador(jugadorPelea);
                            alienPelea.setAtacando(false);
                            entroPelea = true;
                            jugadorPelea.setVieneDePelea(true);
                        }

                        //TODO ACTUALIZARLO CUANDO SE ACTUALIZA LA POSICION DEL ALIEN.
                        alienPelea.getCirculoAlien().setX(alienPelea.getPosicion().x);
                        alienPelea.getCirculoAlien().setY(alienPelea.getPosicion().y);

                    }else if (jugadorPelea != null) {
                        if(jugadorPelea.isEnCombate()){
                            isMuerto = pelea(alienPelea);

                            if (isMuerto) {
                                entroPelea = false;
                            }
                        }
                    }
                }
            }

        } while (++i < aliens.size && !isMuerto);
    }

    private boolean pelea(Alien alienPelea){

        if (jugadorPelea.isAtacando() && Intersector.overlaps(jugadorPelea.getRectangulo(), alienPelea.getRectangulo())) {
            alienPelea.quitarVida(jugadorPelea.getDanio());
        }

       return comprobarSalidaPelea();
    }

    public boolean comprobarSalidaPelea() {
//		alienPelea.quitarVida(0.2f);
        if (alienPelea.getVida() == 0) {
            alienPelea.setMuerto(true);

            jugadorPelea.setAtacando(false);
            jugadorPelea.setEnCombate(false);

            this.setPosicionJugador(jugadorPelea);
            return true;
        }else if(jugadorPelea.getVidaActual() == 0){
            derrota = true;
            return false;
        } else{
            return false;
        }
    }

    @Override
    public Array<Rectangle> getRectColision() {
        return colisiones;
    }

    @Override
    public Array<Polygon> getPoliColision() {
        return null;
    }

    @Override
    public Array<Rectangle> getZonasCambioMapa() {
        return null;
    }

    public Alien getAliens(int idAlien) {
        int pos = 0;
        for (int i = 0; i < aliens.size; i++){
            if(idAlien == aliens.get(i).getIdAlien()){
                pos = i;
            }
        }
        return aliens.get(pos);
    }

    public int getAliensSize() {
        return aliens.size;
    }

    @Override
    public Rectangle getLimiteMapa() {
        return rectanguloPiso;
    }

}
