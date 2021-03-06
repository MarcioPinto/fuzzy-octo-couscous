package org.academiadecodigo.haltistas.server;

import org.academiadecodigo.haltistas.server.game.PicturusGame;


public class Decoder {

    private PicturusGame game;

    Decoder(PicturusGame game) {
        this.game = game;
    }


    public void decoder(String clientName, String message, Server.ClientHandler socket) {


        String[] splitedMessage = message.split(" ");

        switch (splitedMessage[0]) {

            case "/CHAT/":

                message = message.replaceFirst(splitedMessage[0], "");
                message = message.substring(message.indexOf(" ") + 1);
                String name = socket.getClientName();
                game.wordCheck(message,name);
                game.chatMessage(name +" : " + message);
                break;

            case "/DRAW/":

                message = message.replaceFirst(splitedMessage[0], "");
                message = message.substring(message.indexOf(" ") + 1);

                game.drawMessage(message);
                break;

            case "/ERASE/":

                game.eraseDraw(message);
                break;

            default:
                System.err.println(GameStrings.ERROR);
        }
    }
}
