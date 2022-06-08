package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import enemigos.Enemigo;
import eventos.InventarioEventListener;
import hilos.HiloCliente;
import mapas.gestorMapas.Mapa;
import objetos.Item;
import objetos.armaduras.PetoCarton;
import objetos.armas.EspadaCarton;
import objetos.pociones.PocionVida;
import utilidades.Entrada;
import utilidades.Utiles;

public class PersonajePrincipal extends Entidad {

    private Texture heroeAbajo = new Texture("personajes/heroe/heroeQuietoAbajo.png");
    private Sprite heroeAbajoSprite = new Sprite(heroeAbajo);

    private Texture heroeArriba = new Texture("personajes/heroe/heroeQuietoArriba.png");

    private Texture heroeDerecha = new Texture("personajes/heroe/heroeQuietoDerecha.png");

    private Texture heroeIzquierda = new Texture("personajes/heroe/heroeQuietoIzquierda.png");

    private Texture heroeArmadoIzquierda = new Texture("personajes/heroe/heroeArmadoIzquierda.png");

    private TextureRegion heroeArmadoDerechaTR;
    private TextureRegion heroeArmadoIzquierdaTR;

    private float vidaMaxima = 100;
    private float vidaActual = vidaMaxima;
    private boolean corriendo = false, atacando = false;
    private int staminaMax = 300;
    private int stamina = staminaMax;
    private int VELOCIDAD_NORMAL = 1, VELOCIDAD_CORRIENDO = 2;
    private int FILAS = 4, FILAS_ATAQUE = 2, COLUMNAS = 6;
    private float tiempoAni = 0;
    private Animation<TextureRegion> animacionDerecha, animacionIzquierda, animacionAbajo, animacionArriba, animacionArmadoDerecha, animacionArmadoIzquierda, animacionAtaqueDerecha, animacionAtaqueIzquierda;
    private Animation<TextureRegion> animacionActual;
    private boolean enMovimiento = false, enColision = false, enCombate = false;
    private int posicionFinal = 0;
    private int direccion = 0;
    private Rectangle rectanguloJugador;
    private int correccionAlto = 72, correccionAncho = 223, correccionAlto2 = 15, correccionAncho2 = 125; //Es posible calcularlo?
    private int reducciondanio = 45;
    private float danio = 1f;
    private Item hotbar[];
    private int contHotBar[] = new int[5];
    private boolean vieneDePelea = false;


    public PersonajePrincipal() {
        super.set(heroeAbajoSprite);
        crearAnimaciones();
        rectanguloJugador = new Rectangle(getBoundingRectangle());
        hotbar = new Item[5];

//		hotbar[0] = new PocionVida();
//		hotbar[1] = new EspadaCarton();
//		hotbar[2] = new PetoCarton();
//		hotbar[3] = new PocionVida();
        aniadirItem(new PocionVida());
        aniadirItem(new PetoCarton());
        aniadirItem(new EspadaCarton());
    }

    private void aniadirItem(Item item) {
        boolean encontrado = false;
        int i = 0;
        do {
            if (hotbar[i] != null && hotbar[i].getNombre().equals(item.getNombre())) {
                contHotBar[i]++;
                encontrado = true;
            }
        } while (++i < hotbar.length);

        //System.out.println(encontrado);

        boolean puesto = false;
        if (!encontrado) {
            for (int j = 0; j < hotbar.length; j++) {
                if (hotbar[j] == null && !puesto) {
                    hotbar[j] = item;
                    puesto = true;
                    System.out.println("PUESTOOOOO");
                }
            }
        }

    }

    public void dibujar(final Texture textura) {
        this.setTexture(textura);
        this.draw(Utiles.batch);

        this.setX(this.posicion.x);
        this.setY(this.posicion.y);

        rectanguloJugador.set(posicion.x, posicion.y, getRectangulo().getWidth(), getHeight() / 2);
    }

    public void dibujar(final TextureRegion tr) {
        Utiles.batch.draw(tr, posicion.x, posicion.y, tr.getRegionWidth(), tr.getRegionHeight());

        this.setX(this.posicion.x);
        this.setY(this.posicion.y);

        rectanguloJugador.set(posicion.x, posicion.y, getRectangulo().getWidth(), getHeight() / 2);
    }

    public void controlarInventario() {

//		for (int i = 0; i < hotbar.length; i++) {
//			Item item = hotbar[i];
//			System.out.println();
//			for (int j = 0; j < hotbar.length; j++) {
//				if(item == hotbar[j]) {
//					contHotBar[i]++;
//				}
//			}
//			System.out.println("ITEM: "+item.getNombre()+" CANTIDAD: "+contHotBar[i]);
//		}

        for (int i = 0; i < Utiles.listeners.size; i++) {
            try {
                ((InventarioEventListener) Utiles.listeners.get(i)).organizarInventario(hotbar, contHotBar);
            } catch (Exception e) {
            }
        }
    }

    public void recibirDanio(final float cantidad) {
        this.vidaActual -= (cantidad / reducciondanio);
        if (this.vidaActual < 0) {
            vidaActual = 0;
            System.out.println("PERDISTEE JAJA");
        }
    }

    public void hacerDanio(final Enemigo enemigo) {
        enemigo.recibirDanio(danio);    //TODO Hacerlo funcionar!
    }

    public void crearAnimacion() {

        Texture heroeAnimacion = new Texture("personajes/heroe/heroeSheet.png");
        TextureRegion[][] tmp = TextureRegion.split(heroeAnimacion, (heroeAnimacion.getWidth() - correccionAncho) / COLUMNAS, (heroeAnimacion.getHeight() - correccionAlto) / FILAS);
        TextureRegion[] walkFramesDerecha = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesIzquierda = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesArriba = new TextureRegion[COLUMNAS];
        TextureRegion[] walkFramesAbajo = new TextureRegion[COLUMNAS];

        Texture heroeAnimacionArmado = new Texture("personajes/heroe/heroeArmadoSheet.png");
        TextureRegion[][] tmp2 = TextureRegion.split(heroeAnimacionArmado, (heroeAnimacionArmado.getWidth() - correccionAncho2) / COLUMNAS, (heroeAnimacionArmado.getHeight() - correccionAlto2) / FILAS_ATAQUE);
        TextureRegion[] armadoDerecha = new TextureRegion[COLUMNAS];
        TextureRegion[] armadoIzquierda = new TextureRegion[COLUMNAS];


        Texture heroeArmadoDerecha = new Texture("personajes/heroe/heroeArmadoDerecha.png");
        TextureRegion[][] tmp5 = TextureRegion.split(heroeArmadoDerecha, heroeArmadoDerecha.getWidth(), heroeArmadoDerecha.getHeight());
        heroeArmadoDerechaTR = tmp5[0][0];

        Texture heroeArmadoIzquierda = new Texture("personajes/heroe/heroeArmadoIzquierda.png");
        TextureRegion[][] tmp6 = TextureRegion.split(heroeArmadoIzquierda, heroeArmadoIzquierda.getWidth(), heroeArmadoIzquierda.getHeight());
        heroeArmadoIzquierdaTR = tmp6[0][0];

        Texture heroeAnimacionAtaqueDerecha = new Texture("personajes/heroe/heroeAtaque2Derecha.png");
        Texture heroeAnimacionAtaqueIzquierda = new Texture("personajes/heroe/heroeAtaque2Izquierda.png");

        TextureRegion[][] tmp3 = TextureRegion.split(heroeAnimacionAtaqueDerecha, (heroeAnimacionAtaqueDerecha.getWidth()) / COLUMNAS, heroeAnimacionAtaqueDerecha.getHeight());
        TextureRegion[][] tmp4 = TextureRegion.split(heroeAnimacionAtaqueIzquierda, (heroeAnimacionAtaqueIzquierda.getWidth()) / COLUMNAS, heroeAnimacionAtaqueIzquierda.getHeight());

        TextureRegion[] ataqueDerecha = new TextureRegion[COLUMNAS];
        TextureRegion[] ataqueIzquierda = new TextureRegion[COLUMNAS];

        int index3 = 0;

        for (int i = 0; i < 2; i++) {
            index3 = 0;
            if (i == 0) {
                for (int j = 0; j < COLUMNAS; j++) {
                    ataqueDerecha[index3++] = tmp3[0][j];
                }
            }

            if (i == 1) {
                for (int j = 0; j < COLUMNAS; j++) {
                    ataqueIzquierda[index3++] = tmp4[0][j];
                }
            }
        }


        animacionAtaqueDerecha = new Animation<TextureRegion>(0.2f, ataqueDerecha);
        animacionAtaqueIzquierda = new Animation<TextureRegion>(0.2f, ataqueIzquierda);

        int index2 = 0;

        for (int i = 0; i < FILAS_ATAQUE; i++) {
            index2 = 0;
            for (int j = 0; j < COLUMNAS; j++) {
                if (i == 0) {
                    armadoDerecha[index2++] = tmp2[i][j];
                }
                if (i == 1) {
                    armadoIzquierda[index2++] = tmp2[i][j];
                }
            }
        }

        animacionArmadoDerecha = new Animation<TextureRegion>(0.2f, armadoDerecha);
        animacionArmadoIzquierda = new Animation<TextureRegion>(0.2f, armadoIzquierda);

        int index = 0;

        for (int i = 0; i < FILAS; i++) {
            index = 0;
            for (int j = 0; j < COLUMNAS; j++) {
                if (i == 0) {
                    walkFramesDerecha[index++] = tmp[i][j];
                }
                if (i == 1) {
                    walkFramesAbajo[index++] = tmp[i][j];
                }
                if (i == 2) {
                    walkFramesIzquierda[index++] = tmp[i][j];
                }
                if (i == 3) {
                    walkFramesArriba[index++] = tmp[i][j];
                }
            }
        }

        animacionDerecha = new Animation<TextureRegion>(0.2f, walkFramesDerecha);
        animacionIzquierda = new Animation<TextureRegion>(0.2f, walkFramesIzquierda);
        animacionAbajo = new Animation<TextureRegion>(0.2f, walkFramesAbajo);
        animacionArriba = new Animation<TextureRegion>(0.2f, walkFramesArriba);

    }

    TextureRegion frameActual;

    public void animar(final boolean animar) {
        if (animar) {
            tiempoAni += Gdx.graphics.getDeltaTime();
            frameActual = animacionActual.getKeyFrame(tiempoAni, true);
            dibujar(frameActual);
        }
    }

    public void crearAnimaciones() {
        crearAnimacion();
    }

    public void controlarMovimiento(Entrada entrada, Mapa mapa, HiloCliente hc) {

        float oldX = posicion.x, oldY = posicion.y;
        float newX = posicion.x, newY = posicion.y;
        enMovimiento = false;

        //Correr
        if (entrada.pressedShift && stamina > 0) {
            corriendo = true;
            hc.enviarMensaje("ACC-presioneShift");
        } else if (!entrada.pressedShift || stamina == 0) {
            hc.enviarMensaje("ACC-noShift");
            corriendo = false;
            if (stamina < staminaMax && !entrada.pressedShift) {
                stamina++;
            }
        }

        if (!atacando) {

            if (entrada.pressedW) {
                enMovimiento = true;
                if (!corriendo) {
                    newY += VELOCIDAD_NORMAL;
                }
                if (corriendo && stamina > 0) {
                    newY += VELOCIDAD_CORRIENDO;
                    reducirStamina();
                }
                rectanguloJugador.setY(newY);
                enColision = mapa.comprobarColision(this);
                direccion = 1;
                animacionActual = animacionArriba;
                if (!enColision) {
                    this.posicion.y = newY;
                }
                else {
                    this.posicion.y = oldY;
                }
            hc.enviarMensaje("MOVJ-presioneW" + "-" + posicion.y);
            }
            if (entrada.pressedA) {
                enMovimiento = true;
                if (!corriendo) {
                    newX -= VELOCIDAD_NORMAL;

                }
                if (corriendo && stamina > 0) {
                    newX -= VELOCIDAD_CORRIENDO;
                    reducirStamina();
                }
                rectanguloJugador.setX(newX);
                enColision = mapa.comprobarColision(this);
                direccion = 3;
                if (!enCombate) {
                    animacionActual = animacionIzquierda;
                } else {
                    animacionActual = animacionArmadoIzquierda;
                }

                if (!enColision) {
                    this.posicion.x = newX;
                }
                else {
                    this.posicion.x = oldX;
                }
                hc.enviarMensaje("MOVJ-presioneA"+ "-" + posicion.x);
            }
            if (entrada.pressedS) {
                enMovimiento = true;
                if (!corriendo) {
                    newY -= VELOCIDAD_NORMAL;
                }
                if (corriendo && stamina > 0) {
                    newY -= VELOCIDAD_CORRIENDO;
                    reducirStamina();
                }
                rectanguloJugador.setY(newY);
                enColision = mapa.comprobarColision(this);
                direccion = 4;
                animacionActual = animacionAbajo;
                if (!enColision) {
                    this.posicion.y = newY;
                }
                else {
                    this.posicion.y = oldY;
                }
                hc.enviarMensaje("MOVJ-presioneS"+ "-" + posicion.y);
            }
            if (entrada.pressedD) {
                enMovimiento = true;
                if (!corriendo) {
                    newX += VELOCIDAD_NORMAL;
                }
                if (corriendo && stamina > 0) {
                    newX += VELOCIDAD_CORRIENDO;
                    reducirStamina();
                }
                rectanguloJugador.setX(newX);
                enColision = mapa.comprobarColision(this);
                direccion = 2;
                if (!enCombate) {
                    animacionActual = animacionDerecha;
                } else {
                    animacionActual = animacionArmadoDerecha;
                }
                if (!enColision) {
                    this.posicion.x = newX;
                }
                else {
                    this.posicion.x = oldX;
                }
                hc.enviarMensaje("MOVJ-presioneD"+ "-" + posicion.x);
            }

            //TODO REVISAR!!!!!!!!
            hc.enviarMensaje("MOVJ-quieto"+ "-" + posicion.x + "/" + posicion.y);
        }
        if (enCombate) {
            if (entrada.pressedClickIzq && getPosicionFinal() == 2) {
                animacionActual = animacionAtaqueDerecha;
                atacando = true;
                hc.enviarMensaje("CLIC-Izq");
            } else if (entrada.pressedClickIzq && getPosicionFinal() == 3) {
                animacionActual = animacionAtaqueIzquierda;
                atacando = true;
                hc.enviarMensaje("CLIC-Izq");
            } else {
                atacando = false;
                hc.enviarMensaje("CLIC-noIzq");
            }

            //TODO REVISAR CURACIÓN - PRUEBA
            if(entrada.pressed1){
               this.setVidaActual();
               hc.enviarMensaje("ACC-presione1");
            }
            System.out.println("Atacando: " + atacando);
        }


//		System.out.println("Posicion x en movimiento: "+posicion.x);
//		System.out.println("Posicion y en movimiento: "+posicion.y);
//		System.out.println("Stamina: "+stamina);
    }

    public void reducirStamina() {
        stamina--;
        if (stamina < 0) {
            stamina = 0;
        }
    }

    public void movimiento() {

        Utiles.batch.begin();
        if (isEnMovimiento()) {
            if (!enCombate) {
                if (getDireccionJugador() == 1) {
                    animar(true);
                    setPosicionFinal(1);
                }
                if (getDireccionJugador() == 2) {
                    animar(true);
                    setPosicionFinal(2);
                }
                if (getDireccionJugador() == 3) {
                    animar(true);
                    setPosicionFinal(3);
                }
                if (getDireccionJugador() == 4) {
                    animar(true);
                    setPosicionFinal(4);
                }
            } else {
                if (getDireccionJugador() == 2) {
                    animar(true);
                    setPosicionFinal(2);
                }
                if (getDireccionJugador() == 3) {
                    animar(true);
                    setPosicionFinal(3);
                }
            }

        } else {
//			animar(false);
            if (!enCombate) {
                if (getPosicionFinal() == 0) {
                    dibujar(heroeAbajo);
                }
                if (getPosicionFinal() == 1) {
                    dibujar(heroeArriba);
                }
                if (getPosicionFinal() == 2) {
                    dibujar(heroeDerecha);
                }
                if (getPosicionFinal() == 3) {
                    dibujar(heroeIzquierda);
                }
                if (getPosicionFinal() == 4) {
                    dibujar(heroeAbajo);
                }
            } else {
                if (!atacando && getPosicionFinal() == 2) {
                    dibujar(heroeArmadoDerechaTR);
                }
                if (!atacando && getPosicionFinal() == 3) {
                    dibujar(heroeArmadoIzquierdaTR);
                }

                if (atacando && getPosicionFinal() == 2) {
                    animar(true);
                    setPosicionFinal(2);
                }
                if (atacando && getPosicionFinal() == 3) {
                    animar(true);
                    setPosicionFinal(3);
                }

            }

        }

        Utiles.batch.end();

    }

    public void mostrarColisiones() {
        Utiles.sr.setAutoShapeType(true);
        Utiles.sr.begin();

        Utiles.sr.set(ShapeType.Line);
        Utiles.sr.setColor(Color.GREEN);
        Utiles.sr.rect(getRectangulo().x, getRectangulo().y, getRectangulo().getWidth(), getRectangulo().getHeight());

        //TODO QUITAR COMENTARIO
        //System.out.println("Ancho rectangulo: " + getRectangulo().getWidth());

        Utiles.sr.end();
    }

    public float getVidaActual() {
        return vidaActual;
    }

    //TODO CURA PERSONAJE - IMPLEMENTARLO EN LA POCIÓN
    private void setVidaActual(){
        float controlVida = 0f;

        if(getVidaActual() == getVidaMaxima()){
            System.out.println("VIDA MAXIMA COMPLETA");
        }else{
            controlVida = (float) ((vidaActual*20)/100);
            float referencia = controlVida + vidaActual;
            if(referencia > vidaMaxima){
                vidaActual = vidaMaxima;
            }else{
                vidaActual += controlVida;
            }
        }
    }

    public boolean isVieneDePelea() {
        return vieneDePelea;
    }

    public void setVieneDePelea(boolean vieneDePelea) {
        this.vieneDePelea = vieneDePelea;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public float getVidaMaxima() {
        return vidaMaxima;
    }

    public boolean isEnMovimiento() {
        return enMovimiento;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }

    public void setPosicionFinal(final int posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    public int getDireccionJugador() {
        return direccion;
    }

    public boolean isEnColision() {
        return enColision;
    }

    public void setEnColision(final boolean enColision) {
        this.enColision = enColision;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public int getStamina() {
        return stamina;
    }

    public float getDanio() {
        return danio;
    }

    public Rectangle getRectangulo() {
        return rectanguloJugador;
    }

//	public int getPosicionJugadorTileX() {
//		return posicionJugadorTileX;
//	}
//
//	public int getPosicionJugadorTileY() {
//		return posicionJugadorTileY;
//	}

    public int getStaminaMax() {
        return staminaMax;
    }

    public boolean isEnCombate() {
        return enCombate;
    }

    public void setEnCombate(boolean enCombate) {
        this.enCombate = enCombate;
        if (enCombate) {
            posicionFinal = 2;
        }
    }

}
