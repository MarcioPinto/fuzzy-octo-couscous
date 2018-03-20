package org.academiadecodigo.haltistas.client;

import org.academiadecodigo.haltistas.client.controllers.MouseController;
import org.academiadecodigo.haltistas.client.graphics.Draw;
import org.academiadecodigo.haltistas.client.graphics.Pencil;
import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final static int PADDING = 10;

    private String hostName;
    private int portNumber;

    private Socket socket;

    private BufferedReader fromServer;
    private PrintWriter toServer;

    private Draw draw;
    private Pencil pencil;

    private MouseController mouseController;


    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;

        draw = new Draw(this);
        pencil = new Pencil();
    }

    public void setMouseController(MouseController mouseController) {
        this.mouseController = mouseController;
    }

    //init communication
    public void init() throws IOException {

        socket = new Socket(hostName, portNumber);

        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Rectangle rectangle = new Rectangle(PADDING, PADDING, 400, 400);
        rectangle.draw();
        Rectangle chatRectangle = new Rectangle(PADDING, PADDING, 900, 400);
        chatRectangle.draw();

        Line chatLine = new Line(410, 390, 910, 390);
        chatLine.draw();

        new Thread(new InputHandler()).start();

    }


    public void send(String message) {
        toServer.println(message);
    }


    public void drawToSend(char key) {
        draw.write(key);
    }

    public void drawDelete() {
        draw.delete();
    }

    public void drawSend() {
        draw.send();
    }


    private class InputHandler implements Runnable {


        @Override
        public void run() {

            while (!socket.isClosed()) {

                try {

                    String message = fromServer.readLine();
                    System.out.println("Shapes in Canvas: " + Canvas.getInstance().getShapes().size());

                    if (message == null) {
                        break;
                    }

                    if (message.isEmpty()) {
                        continue;
                    }

                    receivedMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        private void receivedMessage(String message) {


            String[] str = message.split(" ");

            switch (str[0]) {

                case "/DRAW/":

                    message = message.replaceFirst(str[0], "");
                    message = message.substring(message.indexOf(" ") + 1);

                    String[] point = message.split(" ");

                    double IniX = Double.parseDouble(point[0]);
                    double IniY = Double.parseDouble(point[1]);
                    double FinX = Double.parseDouble(point[2]);
                    double FinY = Double.parseDouble(point[3]);

                    pencil.draw(IniX, IniY, FinX, FinY);
                    break;

                case "/CHAT/":


                    message = message.replaceFirst(str[0], "");
                    message = message.substring(message.indexOf(" ") + 1);

                    draw.receive(message);
                    break;


                case "/ACTIVE/":

                    mouseController.setCanDraw(true);
                    draw.setCanWrite(false);

                    message = message.replaceFirst(str[0], "");
                    message = message.substring(message.indexOf(" ") + 1);

                    draw.receive("WORD IN PLAY! DRAW THIS SHIT: " + message);
                    break;

                case "/INFO/":

                    message = message.replaceFirst(str[0], "");
                    message = message.substring(message.indexOf(" ") + 1);

                    draw.receive(message);
            }
        }
    }
}
