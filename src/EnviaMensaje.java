import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/** Envía el mensaje mediante sockets
 * @version 1.2
 * @since 0.4
 */

public class EnviaMensaje {

    DataInputStream in;
    DataOutputStream out;
    Socket socket;

    /** Envía el mensaje a la dirección de loopback
     *
     * @param host de destino
     * @param puerto de destino
     * @param mensaje
     * @param puerto de origen
     * @return valor buleano
     */

    public boolean Enviar(String hostDestino, int puertoDestino, String mensaje, int puertoOrigen){

        try {

            socket = new Socket(hostDestino, puertoDestino);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(puertoOrigen + ": " + mensaje);

            out.close();

            socket.close();

            return false;

        }
        catch (IOException e) {

            System.out.println("Problemas de conexión.");
            return true;

        }
    }
}
