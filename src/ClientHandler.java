import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/** Controla la recepción de mensajes
 * @author Luis Delgado
 * @version 1.2
 * @since 0.7
 */

public class ClientHandler extends Thread{


    final DataInputStream in;
    final Socket socket;
    Ventana ventana;

    /** Constructor
     *
     * @param socket
     * @param in
     * @param ventana
     */

    public ClientHandler(Socket socket, DataInputStream in, Ventana ventana){
        this.socket = socket;
        this.in = in;
        this.ventana = ventana;
    }

    /** Ejecuta el receptor en bucle
     */

    public void run() {

        String recibido;
        while (true) {
            try {
                recibido = in.readUTF();
                ventana.AñadirMensaje(recibido, recibido.substring(0, 5));
                break;

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        try {
            this.in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
