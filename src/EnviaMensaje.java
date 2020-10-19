import java.net.ServerSocket;

public class BuscadorDePuertos {

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
