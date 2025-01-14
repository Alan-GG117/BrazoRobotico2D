package BrazoRobotico2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrazoRobotico2D extends JPanel {
    private int anguloBase = 0;  // Ángulo de la base
    private int anguloBrazo = 0; // Ángulo del brazo

    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Simulación de Brazo Robótico 2D");
        BrazoRobotico2D panel = new BrazoRobotico2D();

        // Configurar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        // Crear controles para los ángulos
        JPanel controles = new JPanel();
        controles.setLayout(new GridLayout(3, 2, 10, 10));

        // Etiquetas y campos de texto para la base
        JLabel labelBase = new JLabel("Ángulo Base (0°-360°):");
        JTextField campoBase = new JTextField("0");
        JLabel errorBase = new JLabel("");
        errorBase.setForeground(Color.RED);

        // Etiquetas y campos de texto para el brazo
        JLabel labelBrazo = new JLabel("Ángulo Brazo (0°-360°):");
        JTextField campoBrazo = new JTextField("0");
        JLabel errorBrazo = new JLabel("");
        errorBrazo.setForeground(Color.RED);

        // Botón para aplicar cambios
        JButton botonActualizar = new JButton("Actualizar Ángulos");

        // Añadir componentes al panel de controles
        controles.add(labelBase);
        controles.add(campoBase);
        controles.add(errorBase);
        controles.add(labelBrazo);
        controles.add(campoBrazo);
        controles.add(errorBrazo);

        // Añadir el botón al final
        controles.add(botonActualizar);

        // Listener del botón para actualizar los ángulos
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validar y establecer el ángulo de la base
                    int anguloB = Integer.parseInt(campoBase.getText());
                    if (anguloB < 0 || anguloB > 360) {
                        throw new NumberFormatException();
                    }
                    panel.setAnguloBase(anguloB);
                    errorBase.setText("");
                } catch (NumberFormatException ex) {
                    errorBase.setText("Valor inválido");
                }

                try {
                    // Validar y establecer el ángulo del brazo
                    int anguloBr = Integer.parseInt(campoBrazo.getText());
                    if (anguloBr < 0 || anguloBr > 360) {
                        throw new NumberFormatException();
                    }
                    panel.setAnguloBrazo(anguloBr);
                    errorBrazo.setText("");
                } catch (NumberFormatException ex) {
                    errorBrazo.setText("Valor inválido");
                }

                // Mostrar las coordenadas cartesianas
                panel.mostrarCoordenadas();
            }
        });

        // Añadir el panel de controles a la ventana
        frame.add(controles, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Método para configurar el ángulo de la base
    public void setAnguloBase(int angulo) {
        anguloBase = angulo;
        repaint();
    }

    // Método para configurar el ángulo del brazo
    public void setAnguloBrazo(int angulo) {
        anguloBrazo = angulo;
        repaint();
    }

    // Método para mostrar las coordenadas cartesianas
    public void mostrarCoordenadas() {
        // Longitudes de los segmentos
        int longitudBase = 100;
        int longitudBrazo = 100;

        // Cálculo de las coordenadas del extremo de la base
        double xBase = longitudBase * Math.cos(Math.toRadians(anguloBase));
        double yBase = longitudBase * Math.sin(Math.toRadians(anguloBase));

        // Cálculo de las coordenadas del extremo del brazo
        double xBrazo = xBase + longitudBrazo * Math.cos(Math.toRadians(anguloBase + anguloBrazo));
        double yBrazo = yBase + longitudBrazo * Math.sin(Math.toRadians(anguloBase + anguloBrazo));

        // Mostrar las coordenadas en una ventana emergente
        String mensaje = String.format(
            "Coordenadas:\n" +
            "Extremo de la base: (%.2f, %.2f)\n" +
            "Extremo del brazo: (%.2f, %.2f)",
            xBase, yBase, xBrazo, yBrazo
        );
        JOptionPane.showMessageDialog(this, mensaje, "Coordenadas del Brazo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para dibujar el brazo robótico
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configurar el centro de la pantalla
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        // Configurar la longitud de los segmentos
        int longitudBase = 100;
        int longitudBrazo = 100;

        // Dibujar la base
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(5));

        // Rotar la base
        g2d.translate(centroX, centroY);
        g2d.rotate(Math.toRadians(anguloBase));
        g2d.drawLine(0, 0, longitudBase, 0);

        // Dibujar el brazo
        g2d.translate(longitudBase, 0); // Moverse al final de la base
        g2d.rotate(Math.toRadians(anguloBrazo)); // Rotar el brazo
        g2d.setColor(Color.RED);
        g2d.drawLine(0, 0, longitudBrazo, 0);
    }
}
