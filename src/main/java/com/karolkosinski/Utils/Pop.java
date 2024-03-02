package com.karolkosinski.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Pop {


    public static LinkedList<Pop> notifyList = new LinkedList<>();
    private JWindow window;
    private static final int DISPLAY_DURATION = 500000; // 5 sekund

    private final JPanel popupPanel;
    private final JLabel titleLabel;
    private final JLabel messageLabel;
    private final JButton closeButton;
    private final Timer timer;

    public Pop(String title, String message) {
        notifyList.add(this);
        popupPanel = new JPanel();
        popupPanel.setLayout(new BorderLayout());
        popupPanel.setBackground(new Color(238, 242, 247));
        popupPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
//        messageLabel.setPreferredSize(new Dimension(200, 500));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        closeButton = new JButton("Ok");
        closeButton.setBackground(new Color(210, 210, 210));
        closeButton.addActionListener(e -> closeNotification());
        popupPanel.add(titleLabel, BorderLayout.NORTH);
        popupPanel.add(messageLabel, BorderLayout.CENTER);
//        popupPanel.add(closeButton, BorderLayout.EAST);

        popupPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeNotification();
            }
        });
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                closeNotification();
            }
        }, DISPLAY_DURATION);

        showPopup();
    }

    private int getYSize(){
        return window == null ? 0 : window.getSize().height;
    }
    private int getXSize(){
        return window == null ? 0 : window.getSize().height;
    }
    private void showPopup() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension popupSize = popupPanel.getSize();

        final int[] yNotifs = {0};

        notifyList.forEach(noti -> {
            if (noti == this){yNotifs[0]+=20;}else{
                yNotifs[0] += noti.getYSize()+5;
                System.out.println(yNotifs[0]);
            }
        });


        JWindow notificationWindow = new JWindow();
        this.window = notificationWindow;
        notificationWindow.setAlwaysOnTop(true);
        notificationWindow.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        notificationWindow.getContentPane().add(popupPanel);

        notificationWindow.pack();
        int x = screenSize.width - notificationWindow.getWidth();
        int y = screenSize.height - yNotifs[0] - notificationWindow.getHeight() -20;
        notificationWindow.setLocation(x, y);
        notificationWindow.setVisible(true);
    }

    private static void recalcPos(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        for (int i = 0; i < notifyList.size(); i++) {
            if (i == 0){
                int x = notifyList.get(i).window.getX();
                int y = screenSize.height - notifyList.get(i).getYSize() -40;
                notifyList.get(i).window.setLocation(x, y);
            }else {
                final int[] yNotifs = {0};

                for (int j = 0; j < i; j++) {
                    yNotifs[0] += notifyList.get(j).getYSize()+5;
                }
                int x = notifyList.get(i).window.getX();
                int y = screenSize.height - yNotifs[0]  - notifyList.get(i).getYSize() -40;
                notifyList.get(i).window.setLocation(x, y);
            }
        }

    }

    private void closeNotification() {
        timer.cancel();
        SwingUtilities.getWindowAncestor(popupPanel).dispose(); // Corrected line
        notifyList.remove(this);
        recalcPos();
    }

    public static void exec() {


        String str = "Przykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamaćPrzykładowy tekst Bardzo długi tekst, który musi się załamać";

        int i = str.split("\\s").length / 10 > 1 ? 9 : 10;
        String result = str.replaceAll("(?:\\S+\\s+){1,"+i+"}", "$0<br>");

        result = "<html><center>"+result+"</center></html>";


        new Pop("Uwaga!2", result);

    }
}
