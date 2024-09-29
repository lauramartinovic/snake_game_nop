package controller;

import model.Database;

public class RegistrationController {
    private Database database;

    public RegistrationController() {
        database = new Database(); // Inicijalizacija baze podataka
    }

    public boolean registerUser(String username, String password) {
        if (database.userExists(username)) {
            System.out.println("Korisnik već postoji: " + username);
            return false;  // Ako korisnik već postoji, vraća false
        }
        // Ako korisnik ne postoji, spremi novog korisnika
        return database.saveUser(username, password);
    }
}
