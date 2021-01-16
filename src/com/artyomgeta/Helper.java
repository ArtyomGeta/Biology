package com.artyomgeta;

import processing.core.PGraphics;

public abstract class Helper {

    public static boolean mouseIsOver(Game.GameObject object, int xPos, int yPos) {
        //Счёт от левого верхнего угла
        return (xPos > object.getX() - object.getWidth() && xPos < object.getX() + object.getWidth() ) && (yPos > object.getY() && yPos < (object.getY() + object.getHeight()));
    }

    public static String[] splitTextByWords(String text) {
        return text.split(" ");
    }

}
