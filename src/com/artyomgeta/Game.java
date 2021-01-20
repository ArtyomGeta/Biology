package com.artyomgeta;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

import static com.artyomgeta.Helper.mouseIsOver;
import static com.artyomgeta.Helper.splitTextByWords;

public class Game extends PApplet {
    private static final int SCREEN_MENU = 0;
    private static final int SCREEN_GAME = 1;
    private static boolean mouseIsOverGlobal = false;
    String game = "Работа с микроскопом";
    Button button1, button2;
    int screen = SCREEN_MENU;
    //List<GameObject> objects = new ArrayList<> ();
    DraggableObject draggableObject;
    StaticObject staticObject;
    int[] pressPosition = new int[2];

    //Функция запуска
    public void settings() {
        size(displayWidth - 1, displayHeight - 1);
    }

    public void setup() {
        draggableObject = new DraggableObject(null, null, new int[]{100, 100}, new int[]{100, 100});
        staticObject = new StaticObject(null, null, new int[]{width - 300, 100}, new int[]{200, 200});
    }

    //Функция рисования
    public void draw() {
        //Выбираем текущий экран
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
//        button1.mouseIsOver(Helper.mouseIsOver(button1, mouseX, mouseY));
//        button2.mouseIsOver(Helper.mouseIsOver(button2, mouseX, mouseY));
        button1.draw();
        button2.draw();
    }

    //Рисуем экран игры
    private void drawScreenGame() {
        cursor(ARROW);
        clearScreen();
        draggableObject.draw();
        //addObject(object);
        staticObject.draw();
    }

//    private <T extends GameObject> void addObject(T object) {
//    	objects.add(object);
//    	object.draw(object);
//    }

    //Очистить экран
    private void clearScreen() {
        fill(255);
        rect(0, 0, width, height);
    }

    public void mousePressed() {
        if (screen == SCREEN_GAME) {
            if (draggableObject.mouseIsOver(draggableObject.x, draggableObject.y, draggableObject.width, draggableObject.height)) {
                draggableObject.moving = true;
            }
        }
    }

    public void mouseReleased() {
        draggableObject.moving = false;
    }

    //Слушаем клик мышки
    public void mouseClicked() {
        if (button2.mouseIsOver(button2.getX(), button2.getY(), button2.getWidth(), button2.getHeight())) {
//            int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите выйти?", "Подтвердите", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (result == JOptionPane.YES_OPTION)
            exit();
        } else if (button1.mouseIsOver(button1.getX(), button1.getY(), button1.getWidth(), button1.getHeight())) {
            screen = SCREEN_GAME;
        }
    }

    //Вернуть стандартный курсор
    public void mouseMoved() {
        if (screen == SCREEN_MENU)
            if (!mouseIsOver(button1, mouseX, mouseY) && !mouseIsOver(button2, mouseX, mouseY)) {
                mouseIsOverGlobal = false;
            }
    }

    //Класс для объекта
    class GameObject {
        int x, y, width, height;

        public boolean mouseIsOver(float x, float y, float width, float height) {
            noFill();
            stroke(255, 0, 0);
            rect(x - width / 2, y, width, height);
            return ((mouseX > x - width / 2
                    && mouseX < x + width / 2)
                    && (mouseY > y
                    && mouseY < y + height));
        }

        public <T extends GameObject> boolean mouseIsOver(T object) {
            //println("Object: " + object.getX() + " | " + object.getY() + " | " + object.getWidth() + " | " + object.getHeight());
            return ((mouseX > object.x - object.width / 2
                    && mouseX < object.x + object.width / 2)
                    && (mouseY > object.y
                    && mouseY < y + object.height));
        }

        public int getX() {
            return x;
        }

//        public <T extends GameObject> void draw(T object) {
//            object.draw(this);
//		}


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
            if (mouseIsOver(getX(), getY(), getWidth(), getHeight())) {
                //println("Default: " + getX() + " | " + getY() + " | " + getWidth() + " | " + getHeight());
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

//        public void mouseIsOver(boolean flag) {
//            this.mouseIsOver = flag;
//        }

    }

    //Перетаскиваемый объект
    class DraggableObject extends GameObject {
        public boolean moving = false, visibility = true;
        PImage[] images;
        Text text;
        int x, y, width, height;

        public DraggableObject(PImage[] images, Text text, int[] position, int[] size) {
            this.images = images;
            this.text = text;
            x = position[0];
            y = position[1];
            width = size[0];
            height = size[1];
        }

        void draw() {
            if (visibility) {
                fill(100);
                if (mouseIsOver(x, y, width, height)) {
                    fill(Color.YELLOW.getRGB());
                }
                stroke(0);
                if (moving) {
                    x = mouseX;
                    y = mouseY;
                    if (staticObject.mouseIsOver(staticObject.x, staticObject.y, staticObject.width, staticObject.height)) {
                        System.out.println("Works");
                        staticObject.action = true;
                        visibility = false;
                    }
                }
                ellipse(x, y, width, height);
            }
        }
    }


    class Text extends GameObject {

        public Text(String text, int x, int y, int size, Color color) {

            textSize(size);
            fill(color.getRGB());
            text(text, x, y);

        }

    }

    //Перетаскиваемый текст
    class DraggableText extends GameObject {
    }


    //Статичный объект
    class StaticObject extends GameObject {
        public boolean action = false;
        PImage[] images;
        Text text;
        int x, y, width, height;


        public StaticObject(PImage[] images, Text text, int[] position, int[] size) {
            this.images = images;
            this.text = text;
            x = position[0];
            y = position[1];
            width = size[0];
            height = size[1];
        }

        void draw() {
            fill(Color.GRAY.getRGB());
            if (action) {
                fill(Color.GREEN.getRGB());
            }
            stroke(0);
            rect(x, y + height / 2, width, height);
        }

    }

    //Статичный текст
    class StaticText extends GameObject {

    }

}
