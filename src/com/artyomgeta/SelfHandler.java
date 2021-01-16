package com.artyomgeta;

import processing.core.PApplet;

import javax.swing.*;
import java.util.Arrays;

public class SelfHandler extends JFrame {
    private JPanel panel1;
    private JButton closeButton;
    private JButton uploadButton;
    private JButton rebootButton;
    private JTextArea textArea1;
    private JLabel errorLabel;

    public SelfHandler() {
        setTitle("Произошла ошибка");
        setSize(640, 480);
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            String[] appletArgs = new String[]{"com.artyomgeta.Game"};
            PApplet.main(appletArgs);
        } catch (Exception | Error e) {
            showError(e);
        }
    }

    public static void main(String[] args) {
        new SelfHandler();
    }

    private void showError(Throwable e) {
        textArea1.setText(Arrays.toString(e.getStackTrace()));
        errorLabel.setText("Произошла ошибка: " + e.getCause().toString());
        setVisible(true);
    }

}
