package Server;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;
import Common.Server;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;
    Server server;
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        server = new Server(dot);
        Thread serverThread = new Thread(server);
        serverThread.start();

        run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("next")){
            dot.move();
            moveDot();

        }
        else{
            mapa.tablero[dot.target.coords[X]][dot.target.coords[Y]].clearTarget();
            dot.target.coords = ((Casilla)e.getSource()).getCoords();
            ((Casilla)e.getSource()).setAsTarget();
            moveDot();
        }
        
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while(true){
            try {
                dot.move();
                moveDot();
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }

        }

    }

}