package enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import hilos.HiloCliente;
import interfaces.Movible;
import juego.TwilightOfDarknessPrincipal;
import personajes.PersonajePrincipal;
import utilidades.Utiles;

public class Alien extends Enemigo implements Movible {

    public Texture alienAbajo = new Texture("enemigos/alien/alienQuietoAbajo.png");
    public Sprite alienAbajoSprite = new Sprite(alienAbajo);

    public Texture alienArriba = new Texture("enemigos/alien/alienQuietoArriba.png");
    public Sprite alienArribaSprite = new Sprite(alienArriba);

    public Texture alienDerecha = new Texture("enemigos/alien/alienQuietoDerecha.png");
    public Sprite alienDerechaSprite = new Sprite(alienDerecha);

    public Texture alienIzquierda = new Texture("enemigos/alien/alienQuietoIzquierda.png");
    public Sprite alienIzquierdaSprite = new Sprite(alienIzquierda);

    private Rectangle rectanguloAlien;
    private int idAlien = 0;
    private Circle circuloAlien;
    private final float VELOCIDAD = 0.7f;
    private final int FILAS = 4, COLUMNAS = 3;
    private float tiempoAni = 0;
    private Animation<TextureRegion> animacionIzquierda, animacionDerecha, animacionArriba, animacionAbajo, animacionAtaqueDerecha, animacionAtaqueAbajo, animacionAtaqueIzquierda, animacionAtaqueArriba;
    private Animation<TextureRegion> animacionActual;
    private int correccionAncho = 25, correccionAlto = 120;
    private String direccionAlien = "", posicionFinal = "";
    private boolean enMovimiento = false, atacando, enColision, isEnCombate = false, combateOnline = false;
    private int danio = 10;
    private boolean muerto = false;

    public Alien(int idAlien) {
        super.set(alienAbajoSprite);
        rectanguloAlien = new Rectangle(getBoundingRectangle());
        circuloAlien = new Circle(this.posicion.x, this.posicion.y, 150);
        this.idAlien = idAlien;
        crearAnimacion();
        vidaMax = 100;
        vida = vidaMax;
    }

    @Override
    public void dibujar(Texture textura) {

        this.setTexture(textura);
        this.draw(Utiles.batch);

        this.setX(this.posicion.x);
        this.setY(this.posicion.y);

    }

    public void barraVida() {

        int anchoVida = (vida * 100) / vidaMax;

        Utiles.sr.setAutoShapeType(true);
        Utiles.sr.begin();
        Utiles.sr.set(ShapeType.Line);
        Utiles.sr.setColor(Color.BLACK);
        Utiles.sr.rect(getPosicion().x - 10, getPosicion().y + getHeight(), 100 * 0.5f, 10);

        Utiles.sr.set(ShapeType.Filled);
        Utiles.sr.setColor(Color.GREEN);
        Utiles.sr.rect(getPosicion().x - 10, getPosicion().y + getHeight(), anchoVida * 0.5f, 10);
        Utiles.sr.end();
    }

    @Override
    public void dibujar(TextureRegion tr) {
        Utiles.batch.draw(tr, posicion.x, posicion.y, tr.getRegionWidth(), tr.getRegionHeight());
    }

    @Override
    public void hacerDanio(PersonajePrincipal jugador) {
        if (!isMuerto()) {
            jugador.recibirDanio(danio);
        }
    }

    @Override
    public void recibirDanio(float cantidad) {
        this.vida -= cantidad;
    }

    public void crearAnimacion() {

        Texture alienAnimacion = new Texture("enemigos/alien/alienPack.png");
        Texture alienAtaque = new Texture("enemigos/alien/alienAtaque.png");
        TextureRegion[][] tmp = TextureRegion.split(alienAnimacion, (alienAnimacion.getWidth() - correccionAncho) / COLUMNAS, (alienAnimacion.getHeight() - correccionAlto) / FILAS);
        TextureRegion[][] tmp2 = TextureRegion.split(alienAtaque, (alienAtaque.getWidth() - correccionAncho) / COLUMNAS, (alienAtaque.getHeight() - correccionAlto) / FILAS);
        TextureRegion[] walkFramesDerecha = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesIzquierda = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesArriba = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesAbajo = new TextureRegion[COLUMNAS];

        TextureRegion[] framesAtaqueDerecha = new TextureRegion[COLUMNAS];
        TextureRegion[] framesAtaqueAbajo = new TextureRegion[COLUMNAS];
        TextureRegion[] framesAtaqueIzquierda = new TextureRegion[COLUMNAS];
        TextureRegion[] framesAtaqueArriba = new TextureRegion[COLUMNAS];

        int index = 0;
        for (int i = 0; i < FILAS; i++) {
            index = 0;
            for (int j = 0; j < COLUMNAS; j++) {

                if (i == 0) {
                    walkFramesArriba[index] = tmp[i][j];
                    framesAtaqueDerecha[index] = tmp2[i][j];
                }
                if (i == 1) {
                    walkFramesDerecha[index] = tmp[i][j];
                    framesAtaqueAbajo[index] = tmp2[i][j];
                }
                if (i == 2) {
                    walkFramesAbajo[index] = tmp[i][j];
                    framesAtaqueIzquierda[index] = tmp2[i][j];
                }
                if (i == 3) {
                    walkFramesIzquierda[index] = tmp[i][j];
                    framesAtaqueArriba[index] = tmp2[i][j];
                }
                index++;
            }
        }

        animacionArriba = new Animation<TextureRegion>(0.2f, walkFramesArriba);
        animacionIzquierda = new Animation<TextureRegion>(0.2f, walkFramesIzquierda);
        animacionAbajo = new Animation<TextureRegion>(0.2f, walkFramesAbajo);
        animacionDerecha = new Animation<TextureRegion>(0.2f, walkFramesDerecha);

        animacionAtaqueDerecha = new Animation<TextureRegion>(0.2f, framesAtaqueDerecha);
        animacionAtaqueAbajo = new Animation<TextureRegion>(0.2f, framesAtaqueAbajo);
        animacionAtaqueIzquierda = new Animation<TextureRegion>(0.2f, framesAtaqueIzquierda);
        animacionAtaqueArriba = new Animation<TextureRegion>(0.2f, framesAtaqueArriba);

    }

    TextureRegion frameActual;

    public void animar(boolean animar) {
        if (animar) {
            tiempoAni += Gdx.graphics.getDeltaTime();
            frameActual = animacionActual.getKeyFrame(tiempoAni, true);
            dibujar(frameActual);
        }
    }

    public void movimiento() {

        if (!isMuerto()) {
            Utiles.batch.begin();
            if (enMovimiento || atacando) {
                animar(true);
                if (direccionAlien.equals("izquierda")) {
                    posicionFinal = "izquierda";
                }
                if (direccionAlien.equals("derecha")) {
                    posicionFinal = "derecha";
                }
                if (direccionAlien.equals("abajo")) {
                    posicionFinal = "abajo";
                }
                if (direccionAlien.equals("arriba")) {
                    posicionFinal = "arriba";
                }
            } else if (!enMovimiento && !atacando) {
                animar(false);
                if (posicionFinal.equals("")) {
                    dibujar(alienAbajo);
                }
                if (posicionFinal.equals("izquierda")) {
                    dibujar(alienIzquierda);
                }
                if (posicionFinal.equals("abajo")) {
                    dibujar(alienAbajo);
                }
                if (posicionFinal.equals("derecha")) {
                    dibujar(alienDerecha);
                }
                if (posicionFinal.equals("arriba")) {
                    dibujar(alienArriba);
                }
            }
            Utiles.batch.end();
        }

    }

    public void comportamiento(TwilightOfDarknessPrincipal game, HiloCliente hc, String[] msg) {
        //TODO comportamiento del enemigo

        if(!isMuerto()) {
            if(msg[1].equals("COMBATEA") || msg[1].equals("NOCOMBATE")) {
                enMovimiento = true;

                if(msg[1].equals("COMBATEA")) {
                    int jugadorPelea = Integer.parseInt(msg[2]);
                    PersonajePrincipal jugadorCombate;
                    if(jugadorPelea == 0){
                        jugadorCombate = game.getJugador1();
                    }else{
                        jugadorCombate = game.getJugador2();
                    }

                    isEnCombate = true;

                    atacando = true;
                    hacerDanio(jugadorCombate);
                    //enMovimiento = false;

                    //TODO AGREGAR INFORMACIION DE MOV
                    if(msg[3].equals("izquierda")) {
                        animacionActual = animacionAtaqueIzquierda;
                    }
                    if(msg[3].equals("abajo")) {
                        animacionActual = animacionAtaqueAbajo;
                    }
                    if(msg[3].equals("derecha")) {
                        animacionActual = animacionAtaqueDerecha;
                    }
                    if(msg[3].equals("arriba")) {
                        animacionActual = animacionAtaqueArriba;
                    }

                }
                else if(msg[1].equals("NOCOMBATE") || msg[1].equals("MOVA")){
                    isEnCombate = false;
                    enMovimiento = true;
                    atacando = false;
                    if(msg[2].equals("IZQ")) {
                        animacionActual = animacionIzquierda;
                        direccionAlien = "izquierda";
//                        newX -=VELOCIDAD;
//                        rectanguloAlien.setX(newX);
//
//                        enColision = mapa.comprobarColision(this);
//
//                        if(!enColision) {
//                            posicion.x = newX;
//                            setX(posicion.x);
//                        }else {
//                            posicion.x = oldX;
//                            setX(posicion.x);
//                        }
                    }
                    if(msg[2].equals("ABAJO")) {
                        animacionActual = animacionAbajo;
                        direccionAlien = "abajo";
//                        newY -=VELOCIDAD;
//                        rectanguloAlien.setY(newY);
//
//                        enColision = mapa.comprobarColision(this);
//
//                        if(!enColision) {
//                            posicion.y = newY;
//                            setX(posicion.y);
//                        }else {
//                            posicion.y = oldY;
//                            setX(posicion.y);
//                        }
                    }
                    if(msg[2].equals("DER")) {
                        animacionActual = animacionDerecha;
                        direccionAlien = "derecha";
//                        newX +=VELOCIDAD;
//                        rectanguloAlien.setX(newX);
//
//                        enColision = mapa.comprobarColision(this);
//
//                        if(!enColision) {
//                            posicion.x = newX;
//                            setX(posicion.x);
//                        }else {
//                            posicion.x = oldX;
//                            setX(posicion.x);
//                        }
                    }
                    if(msg[2].equals("ARRIBA")) {
                        animacionActual = animacionArriba;
                        direccionAlien = "arriba";
//                        newY +=VELOCIDAD;
//                        rectanguloAlien.setY(newY);
//
//                        enColision = mapa.comprobarColision(this);
//
//                        if(!enColision) {
//                            posicion.y = newY;
//                            setX(posicion.y);
//                        }else {
//                            posicion.y = oldY;
//                            setX(posicion.y);
//                        }
                    }
                    circuloAlien.setX(posicion.x);
                    circuloAlien.setY(posicion.y);
                }
            }else {
                enMovimiento = false;
            }
            //movimiento();
            if(msg[1].equals("MOVA")){
                float posX = Float.parseFloat(msg[3]);
                float posY = Float.parseFloat(msg[4]);

                setPosicion(posX, posY);
            }
        }
    }

    public void mostrarColisiones() {
        Utiles.sr.circle(getCirculoAlien().x + (getWidth() / 2), getCirculoAlien().y + (getHeight() / 2), getCirculoAlien().radius);
        Utiles.sr.rect(getRectangulo().x, getRectangulo().y, getRectangulo().width, getRectangulo().height);
    }

    public Circle getCirculoAlien() {
        return circuloAlien;
    }

    public Rectangle getRectangulo() {
        return rectanguloAlien;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public boolean isEnMovimiento() {
        return enMovimiento;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public int getIdAlien(){
        return this.idAlien;
    }

    public void setIdAlien(int idAlien){
        this.idAlien = idAlien;
    }

    public void setCombateOnline(boolean estado){
        this.combateOnline = estado;
    }

    public boolean getCombatOnline(){
        return this.combateOnline;
    }

    public boolean getEnCombate(){
        return this.isEnCombate;
    }

    public void setEnCombate(boolean estado){
        this.isEnCombate = estado;
    }


    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

}
