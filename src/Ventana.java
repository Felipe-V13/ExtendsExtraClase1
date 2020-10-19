import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Muestra la interfaz gráfica en pantalla
 * @author Luis Delgado
 * @version 1.2
 * @since 0.1
 */

public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel textoEscribirMensaje;
    private JTextField cajaMensaje;
    private JButton botonEnviar;
    private JTextArea cajaChat;
    private JLabel textoNuevoChat;
    private JButton botonAbrirNuevoChat;
    private JTextField cajaPuertoNuevoChat;
    private JLabel puertoEnUsoLabel;
    private JList listaChatsVisual;
    private int puertoEnUso;
    private DefaultListModel listaDeChats;
    private boolean chatAbierto = false;

    // para almacenar chats
    Map<String, String> todosLosChats = new HashMap<>();

    // para enviar mensaje
    private EnviaMensaje enviarMensaje;
    private int puertoActual = 0;
    private String puertoActualStr = "0";

    /** Constructor
     */

    public Ventana() {
        super(); // contructor de la clase padre JFrame
        configurarVentana(); // configura la ventana
        inicializarComponentes(); // inicializa los atributos o componentes
    }

    /** Configura aspectos básicos de la ventana
     */

    private void configurarVentana() {
        this.setTitle("App de chat"); // colocamos titulo a la ventana
        this.setSize(500, 500); // colocamos tamanio a la ventana (ancho, alto)
        this.setLocationRelativeTo(null); // centramos la ventana en la pantalla
        this.setLayout(null); // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
        this.setResizable(false); // hacemos que la ventana no sea redimiensionable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // hacemos que cuando se cierre la ventana termina todo
        // proceso
    }

    /** Inicializa los componentes usados para la interfaz y atributos de los mensajes
     */

    private void inicializarComponentes() {

        enviarMensaje = new EnviaMensaje();

        textoEscribirMensaje = new JLabel();
        puertoEnUsoLabel = new JLabel();
        puertoEnUsoLabel.setBounds(10, 10, 150, 25);
        cajaChat = new JTextArea();
        cajaChat.setText("");
        cajaMensaje = new JTextField();
        botonEnviar = new JButton();
        // configuramos los componentes
        cajaChat.setBounds(170, 10, 280, 390);
        cajaChat.setEditable(false);
        textoEscribirMensaje.setText("Escriba un mensaje..."); // colocamos un texto a la etiqueta
        textoEscribirMensaje.setBounds(200, 400, 200, 25); // colocamos posicion y tamanio al texto (x, y, ancho, alto)
        cajaMensaje.setBounds(170, 425, 240, 25); // colocamos posicion y tamaño a la caja (x, y, ancho, alto)
        botonEnviar.setText("Enviar"); // colocamos un texto al boton
        botonEnviar.setBounds(412, 425, 70, 25); // colocamos posicion y tamanio al boton (x, y, ancho, alto)
        botonEnviar.addActionListener(envioMensaje); // hacemos que el boton tenga una accion y esa accion estara en
        // esta clase

        // para interactuar con chats

        // listaChats = new ArrayList<String>();
        listaDeChats = new DefaultListModel();
        listaChatsVisual = new JList(listaDeChats);
        listaChatsVisual.addListSelectionListener(CambiarChat);
        listaChatsVisual.setBounds(10, 40, 150, 330);

        new ArrayList<String>();

        // añadir chats

        textoNuevoChat = new JLabel();
        cajaPuertoNuevoChat = new JTextField();
        cajaPuertoNuevoChat.setBounds(10, 390, 150, 25);
        botonAbrirNuevoChat = new JButton();
        botonAbrirNuevoChat.setText("Añadir conversación");
        botonAbrirNuevoChat.setBounds(10, 425, 150, 25);
        botonAbrirNuevoChat.addActionListener(nuevoChat);

        // colocarlos en pantalla
        this.add(cajaChat);
        this.add(textoEscribirMensaje);
        this.add(cajaMensaje);
        this.add(botonEnviar);
        this.add(textoNuevoChat);
        this.add(cajaPuertoNuevoChat);
        this.add(botonAbrirNuevoChat);
        this.add(puertoEnUsoLabel);
        this.add(listaChatsVisual);
    }

    /** Realiza una acción al tocar el botón Añadir Chat
     * @param información del evento
     */

    ActionListener nuevoChat = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent aEvent) {

            cajaPuertoNuevoChat.setText(cajaPuertoNuevoChat.getText().replace(" ", "")); // quitar espacios de un String: // http://chuwiki.chuidiang.org/index.php?title=Eliminar_espacios_de_un_String_en_Java

            final String contenidoCaja = cajaPuertoNuevoChat.getText();
            int contenidoCajaInt = 0;

            try {

                contenidoCajaInt = Integer.parseInt(contenidoCaja);
                todosLosChats.put(puertoActualStr, cajaChat.getText());

            } catch (final Exception e) {

                MensajeEmergente("Introduza un puerto válido.");
                return;
            }

            //System.out.println(puertoEnUso);

            if (Integer.parseInt(contenidoCaja) > 65535) {
                MensajeEmergente("Seleccione un menor o igual a 65535.");
                return;
            } else if (Integer.parseInt(contenidoCaja) < 40000) {
                MensajeEmergente("El puerto debe ser igual o mayor a 40000.");
                return;
            }
            else if (listaDeChats.contains(contenidoCaja)) { // Revisar si un elemento ya está en una lista recuperado de : https://www.geeksforgeeks.org/arraylist-contains-java/#:~:text=ArrayList%20contains()%20method%20in,the%20given%20list%20or%20not.&text=Returns%3A,list%20else%20it%20returns%20false.
                MensajeEmergente("El chat ya está añadido.");
                return;

            } else if (contenidoCajaInt == puertoEnUso) {
                MensajeEmergente("Este es tu puerto.");
                return;

            } else {

                listaDeChats.addElement(contenidoCaja);
            }
        }
    };

    /** Envía el mensaje al puerto del chat abierto
     * @param información del evento
     */

    ActionListener envioMensaje = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String contenidoCaja = cajaMensaje.getText();
            if (contenidoCaja.isEmpty() == false) {

                if (!chatAbierto) {
                    MensajeEmergente("Añada un chat o seleccione un destinatario antes de enviar un mensaje.");
                    return;
                } else if (enviarMensaje.Enviar("127.0.0.1", puertoActual, contenidoCaja, puertoEnUso)) {
                    MensajeEmergente("Problemas de conexión.");
                } else {
                    cajaChat.append("Yo: " + contenidoCaja + "\n");
                    todosLosChats.put(puertoActualStr, cajaChat.getText());
                    cajaMensaje.setText("");
                }

            }
        }
    };

    /** Cambia de chat cuando cambia la selección en la lista
     * @param información del evento
     */

    ListSelectionListener CambiarChat = new ListSelectionListener() {

        @Override
        public void valueChanged(final ListSelectionEvent event) {

            if (!event.getValueIsAdjusting()) {

                todosLosChats.put(puertoActualStr, cajaChat.getText());
                cajaChat.setText("");

                puertoActualStr = (((JList) event.getSource()).getSelectedValues()[0]).toString();
                cajaChat.setText(todosLosChats.get(puertoActualStr));
                puertoActual = Integer.parseInt(puertoActualStr);

                chatAbierto = true;
            }

        }
    };

    /** Añade el mensaje entrante al chat
     * @param mensaje entrante
     * @param puerto de origen
     */

    public void AñadirMensaje (final String mensaje, final String puerto) {

        if (puertoActualStr.equals(puerto)){

            System.out.println("El puerto coincide.");
            todosLosChats.put(puertoActualStr, (todosLosChats.get(puertoActualStr) + mensaje + "\n"));

        }
        else{

            MensajeEmergente("Nuevo mensaje de: " + puerto);

            try {
                todosLosChats.get(puerto).equals(null);         // si el chat no existe, lanza una excepción
            } catch (final Exception e) {

                todosLosChats.put(puerto, mensaje + "\n");      // entonces simplemente crea el chat con el primer mensaje
                return;

            }

            todosLosChats.put(puerto, (todosLosChats.get(puerto) + mensaje + "\n"));
        }

        cajaChat.setText(todosLosChats.get(puertoActualStr));

    }

    /** Coloca un label con el puerto reservado
     * @param puerto reservado
     */

    public void EstablecerPuertoEnUso (final int puerto) {

        puertoEnUsoLabel.setText("Puerto: " + puerto);
        puertoEnUso = puerto;
    }

    /** Crea una pequeña ventana emergente con un mensaje personalizado
     * @param texto para mostar
     */

    private void MensajeEmergente(final String texto) {

        JOptionPane.showMessageDialog(this, texto);
    }
}