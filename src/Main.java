import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/** Corre el programa
 * @version 1.2
 * @since 0.6
 */


public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    /** Ejecuta el programa de forma inmediata
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        BuscadorDePuertos buscar = new BuscadorDePuertos();             // Crea una instancia del buscador de puertos

        int puertoEnUso = buscar.BuscaPuerto();                         // Busca un puerto disponible para recibir mensajes

        ServerSocket servidor = new ServerSocket(puertoEnUso);          // Crea un nuevo socket por el que recibirá mensajes con el puerto seleccionado

        DataInputStream in;                                             // Crea una interfaz de entrada

        Ventana ventana = new Ventana();                                // Genera la interfaz gráfica

        ventana.EstablecerPuertoEnUso(puertoEnUso);                     // envía el puerto de recepción a la GUI, para mostrarla en pantalla

        ventana.setVisible(true);                                       // Posibilita la visualización de la ventana

        while (true) {                                                  // Un bucle para estar en constante recepción de mensajes

            Socket socket = null;                                       // Crea un socket

            try {

                socket = servidor.accept();                             // Si recible una solicitud de entrada, la acepta

                in = new DataInputStream(socket.getInputStream());      // Recibe el contenido de llegada

                new ClientHandler(socket, in, ventana).start();         // Crea un hilo para recibir mensajes

            }
            catch (IOException e) {                                     // Si la conexion falló
                log.info("La conexion fallo");
                socket.close();                                         // Cierra el socket para posibilitar la recepción de otros mensajes

            }
        }
    }

}