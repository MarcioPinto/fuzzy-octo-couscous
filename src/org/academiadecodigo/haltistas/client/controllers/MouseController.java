package org.academiadecodigo.haltistas.client.controllers;

import org.academiadecodigo.haltistas.client.Client;
import org.academiadecodigo.haltistas.client.utils.Constants;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

import java.awt.*;

public class MouseController implements MouseHandler {


    public static final double MOUSE_ADJST_X = -15;
    public static final double MOUSE_ADJST_Y = -10;

    private Client client;

    private double XIni;
    private double YIni;

    private double XFin;
    private double YFin;
    private int count;

    private boolean canDraw;

    public MouseController(Client client) {
        this.client = client;
        this.canDraw = false;
    }


    public void init() {

        Mouse mouse = new Mouse(this);

        mouse.addEventListener(MouseEventType.MOUSE_PRESSED);

        mouse.addEventListener(MouseEventType.MOUSE_RELEASE);

        mouse.addEventListener(MouseEventType.MOUSE_DRAGGED);

    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        XIni = mouseEvent.getX();
        YIni = mouseEvent.getY();

        System.out.println(mouseEvent);
    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        XFin = mouseEvent.getX();
        YFin = mouseEvent.getY();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        //TODO change this to client class

        if (!canDraw) {
            return;
        }

        if (!controlBorders(mouseEvent)){
            return;
        }

        count++;

        if (count <= 0 || count % 3 != 0) {
            return;
        }

        String str = "/DRAW/ " + String.valueOf(XIni) + " " + String.valueOf(YIni) + " " +
                String.valueOf(mouseEvent.getX()) + " " + String.valueOf(mouseEvent.getY()) + "\n";
        System.out.println(str);


        client.sendToServer(str);

        XIni = mouseEvent.getX();
        YIni = mouseEvent.getY();

    }

    private boolean controlBorders(MouseEvent mouseEvent) {

        if (mouseEvent.getX() < Constants.PADDING || mouseEvent.getX() > Constants.DRAWING_BOARD_X + 10||
                mouseEvent.getY() < Constants.PADDING || mouseEvent.getY() > Constants.DRAWING_BOARD_Y + 10) {

            return false;
        }
        return true;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }
}
