package hilos;

import juego.TwilightOfDarknessPrincipal;
import utilidades.Comunicacion;
import utilidades.Recursos;

import java.io.IOException;
import java.net.*;

public class HiloCliente extends Thread{
    private DatagramSocket conexion;
    private InetAddress ipServer;
    private int puerto = 2222;
    private boolean fin = false;
    private TwilightOfDarknessPrincipal game;
    int nroCliente = 0;
    private Comunicacion com;
    boolean conexionAnterior = false;
    String posActualizacion = null;

    public HiloCliente(TwilightOfDarknessPrincipal game) {
        System.out.println("ENTRADA AL HILO");
        this.game = game;
        com = new Comunicacion(this, game);
        try {
            ipServer = InetAddress.getByName("255.255.255.255");
            conexion = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        enviarMensaje("Conexion");
    }

    public void enviarMensaje(String msg) {
        byte[] data = msg.getBytes();
        DatagramPacket peticion = new DatagramPacket(data, data.length,ipServer,puerto);
        try {
            conexion.send(peticion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do{
            byte[] data = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(data, data.length);
            try {
                conexion.receive(respuesta);
            } catch (IOException e) {
                e.printStackTrace();
                fin = false;
            }
            procesarMensaje(respuesta);
//            com.accionJugador(game);
        }while(!fin);
        conexion.close();
        System.out.println("CONEXION CERRADA");
    }

    private void procesarMensaje(DatagramPacket dp) {
        String msg = (new String(dp.getData())).trim();
        String[] mensajeParametrizado = msg.split("-");
        System.out.println(msg);


        if(mensajeParametrizado[0].equals("OK")){
            System.out.println("Estoy conectado");
            nroCliente = Integer.parseInt(mensajeParametrizado[2]);
            System.out.println(msg);
        }
//        else if(msg.equals("CREACION")){
//            conexionAnterior = true;
//            Recursos.conexionAnterior = true;
//            posActualizacion = mensajeParametrizado[1] + "-" + mensajeParametrizado[2];
//        }

//        else if(mensajeParametrizado[0].equals("ACTUALIZACION")){
//            com.actualizacionAlien(mensajeParametrizado);
//        }

        if(msg.equals("Empieza")){
            Recursos.empieza = true;
        }

        if(mensajeParametrizado.length > 1) {
            if (mensajeParametrizado[1].equals("MOVJ")) {
                com.accionJugador(mensajeParametrizado);
            }else if(mensajeParametrizado[1].equals("MOVA")){
                com.movimientoAlien(mensajeParametrizado);
            }else if(mensajeParametrizado[1].equals("COMBATEA")){
                com.peleaAlien(mensajeParametrizado);
            }else if(mensajeParametrizado[1].equals("NOCOMBATEA")){
                com.peleaAlien(mensajeParametrizado);
            }else if(mensajeParametrizado[1].equals("ELIMINAR")){
                com.eliminarAlien(mensajeParametrizado[0]);
            }
        }
    }

    public Comunicacion getCom() {
        return com;
    }

    public int getNroCliente(){
        return nroCliente;
    }
    public boolean isConexionAnterior(){return conexionAnterior;}

    public String[] getPosActualizacion() {
        String[] msgSplit = posActualizacion.split("/");
        return msgSplit;
    }

    public void terminar(){
        enviarMensaje("BYE");
        fin = true;
    }

}