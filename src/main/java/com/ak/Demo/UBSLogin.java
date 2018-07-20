package com.ak.Demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UBSLogin {
    private JTextField TokenId;
    private  JButton submit;
    private JPanel mainPanel;


    public UBSLogin() {
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchA3.invokeBrowser(TokenId.getText());
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UBS Work from Home Login");
        frame.setContentPane(new UBSLogin().mainPanel);
        frame.setPreferredSize(new Dimension(350, 140));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UBSLogin ubs= new UBSLogin();
        frame.pack();
        frame.setVisible(true);
    }
}
