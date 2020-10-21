import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

/** Busca un puerto disponoble para recibir mensajes
 * @author Luis Delgado
 * @version 1.2
 * @since 0.2
 */

public class BuscadorDePuertos {
    private static Logger log = LoggerFactory.getLogger(BuscadorDePuertos.class);

    private ServerSocket serverSocket;

    /** Prueba si un puerto está disponible
     *
     * @param puerto de prueba
     * @return valor buleano de si el puerto está disponible
     */

    private boolean RevisaPuerto(int puerto) {
        boolean resultado;

        try {

            serverSocket = new ServerSocket(puerto);
            serverSocket.close();
            resultado = true;

        }
        catch (Exception e) {
            log.debug("El puerto no esta disponible");
            resultado = false;
        }

        return (resultado);
    }

    /** Busca un puerto disponible, comenzando en 40 000
     *
     * @return puerto disponible
     */

    public int BuscaPuerto() {

        int puerto = 40000;
        boolean encontrado = false;

        System.out.println("Buscando puerto...");

        while (encontrado == false) {

            if (puerto > 65535) {
                log.info("No hay puertos disponibles");
                System.out.println("No hay puertos disponibles.");
                break;
            }

            else if (RevisaPuerto(puerto)){
                System.out.println("Puerto encontrado: " + puerto);
                encontrado = true;
            }

            else{
                puerto++;
            }
        }

        return puerto;
    }
}
