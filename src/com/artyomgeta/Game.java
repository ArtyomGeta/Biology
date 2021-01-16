package com.artyomgeta;

import processing.core.PApplet;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.artyomgeta.Helper.mouseIsOver;
import static com.artyomgeta.Helper.splitTextByWords;

public class Game extends PApplet {
    private static final int SCREEN_MENU = 0;
    private static final int SCREEN_GAME = 1;
    private static boolean mouseIsOverGlobal = false;
    String game = "Работа с микроскопом";
    Button button1, button2;
    int screen = SCREEN_MENU;

    //Функция запуска
    public void settings() {
        size(displayWidth - 1, displayHeight - 1);
    }

    //Функция рисования
    public void draw() {
        //Выбираем текущий экран
        new DraggableText();
        switch (screen) {
            case SCREEN_MENU:
                drawMenuScreen();
                break;
            case SCREEN_GAME:
                drawScreenGame();
                break;
        }
    }

    //Функция нажатия на кнопку
    public void keyPressed() {
        //Не закрывать программу по нажатию ESC
        if (key == ESC)
            key = 0;
    }

    //Рисуем экран меню
    private void drawMenuScreen() {
        background(255);
        textAlign(CENTER);
        textSize(100);
        fill(0);
        //Разделяем название построчно, если оно не влазиет на экран
        if (textWidth(game) > width) {
            int wordsLength = splitTextByWords(game).length;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < wordsLength; i++) {
                stringBuilder.append(splitTextByWords(game)[i]).append("\n");
            }
            game = stringBuilder.toString();
        }
        textLeading(75);
        //noinspection IntegerDivisionInFloatingPointContext
        text(game, width / 2, 100);
        //Рисуем кнопки
        button1 = new Button("Начать", width / 2, height / 2, 60, CORNER, Color.BLACK);
        button2 = new Button("Выход", width / 2, height / 2 + 200, 60, CORNER, Color.BLACK);
        button1.mouseIsOver(Helper.mouseIsOver(button1, mouseX, mouseY));
        button2.mouseIsOver(Helper.mouseIsOver(button2, mouseX, mouseY));
        button1.draw();
        button2.draw();
    }

    //Рисуем экран игры
    private void drawScreenGame() {

    }

    //Слушаем клик мышки
    public void mouseClicked() {
        if (button2.mouseIsOver) {
//            int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите выйти?", "Подтвердите", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (result == JOptionPane.YES_OPTION)
            exit();
        }
    }

    public void mouseMoved() {
        if (!mouseIsOver(button1, mouseX, mouseY) && !mouseIsOver(button2, mouseX, mouseY)) {
            mouseIsOverGlobal = false;
        }
    }

    //Класс для объекта
    static class GameObject {
        int x, y, width, height;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    //Кнопка
    class Button extends GameObject {
        private int x, y, size, align, defaultColor;
        private String text;
        private boolean mouseIsOver = false;

        public Button(String text, int x, int y, int size, int align, Color defaultColor) {
            this.defaultColor = defaultColor.getRGB();
            this.x = x;
            this.y = y;
            this.align = align;
            this.size = size;
            this.text = text;
        }

        public void draw() {
            textAlign(CENTER, TOP);
            if (mouseIsOver) {
                mouseIsOverGlobal = true;
                fill(0, 100);
                textSize(size + 1);
                text(text, (float) (x - 0.1), (float) (y - 0.1));
                cursor(HAND);
            } else {
                if (!mouseIsOverGlobal)
                    cursor(ARROW);
            }
            fill(defaultColor);
            textSize(size);
            text(text, x, y);
        }

        @Override
        public int getWidth() {
            return (int) textWidth(text);
        }

        @Override
        public int getHeight() {
            return this.size;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        public int getSize() {
            return size;
        }

        protected void setSize(int size) {
            this.size = size;
        }

        public int getAlign() {
            return align;
        }

        public void setAlign(int align) {
            this.align = align;
        }

        public int getDefaultColor() {
            return defaultColor;
        }

        public void setDefaultColor(int defaultColor) {
            this.defaultColor = defaultColor;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void mouseIsOver(boolean flag) {
            this.mouseIsOver = flag;
        }

        public boolean isMouseIsOver() {
            return mouseIsOver;
        }

    }

    //Перетаскиваемый объект
    class DraggableObject extends GameObject {

    }

    //Перетаскиваемый текст
    class DraggableText extends GameObject {
    }


    //Статичный объект
    class StaticObject extends GameObject {

    }

    //Статичный текст
    class StaticText extends GameObject {

    }
}
