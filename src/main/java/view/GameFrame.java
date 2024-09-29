package view;

import controller.GameController;
import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem highscoresMenuItem;
    private JMenuItem exitMenuItem;

    public GameFrame(String playerName) {
        Game game = new Game();
        game.setPlayerName(playerName);  // Postavi ime igrača
        GamePanel gamePanel = new GamePanel(game);
        GameController gameController = new GameController(game);

        this.add(gamePanel);
        this.addKeyListener(gameController);  // Povezivanje kontrolera s okvirom

        // Oslušaj fokus na prozoru
        this.setFocusable(true);  // Omogućujemo da JFrame primi fokus za tipkovnicu
        this.requestFocusInWindow();

        // Inicijaliziramo JMenuBar i opcije
        menuBar = new JMenuBar();
        menu = new JMenu("Options");

        // Stavke u meniju
        highscoresMenuItem = new JMenuItem("Highscores");
        exitMenuItem = new JMenuItem("Exit");

        // Dodaj stavke u meni
        menu.add(highscoresMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);

        // Postavi JMenuBar na prozor
        this.setJMenuBar(menuBar);

        // Akcija za prikaz highscores
        highscoresMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Highscores();  // Otvori prozor za prikaz highscores rezultata
            }
        });

        // Akcija za izlazak iz igre
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Izlaz iz igre
            }
        });

        // Osnovne postavke prozora
        setVisible(true);
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
