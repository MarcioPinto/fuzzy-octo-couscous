package org.academiadecodigo.haltistas.server;

import org.academiadecodigo.haltistas.server.game.PicturusGame;

public class Decoder {

    private PicturusGame game;

    public Decoder(PicturusGame game) {
        this.game = game;
    }

    public void decoder(String message) {

        // TODO: /DRAW/ /NAME/ MESSAGE

        String[] splitedMessage = message.split(" ");

        switch (splitedMessage[0]) {

            case "/CHAT/":

                message = message.replaceFirst(splitedMessage[0], "");
                message = message.substring(message.indexOf(" ") + 1);

                System.out.println("CAHT " + message);
                game.wordCheck(message);
                break;

            case "/DRAW/":
                message = message.replaceFirst(splitedMessage[0], "");
                message = message.substring(message.indexOf(" ") + 1);

                game.drawMessage(message);
                break;

            default:

                System.err.println("the command on the message is fuck up");
        }
    }
}