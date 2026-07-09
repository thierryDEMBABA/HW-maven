package com.example.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Module d'authentification simple : gestion des utilisateurs en mémoire.
 * Les mots de passe sont stockés sous forme de hash SHA-256 (jamais en clair).
 */
public class AuthService {

    private final Map<String, String> users = new HashMap<>();

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param username le nom d'utilisateur (non vide)
     * @param password le mot de passe (au moins 4 caractères)
     * @return true si l'utilisateur a été créé, false s'il existe déjà
     * @throws IllegalArgumentException si le nom ou le mot de passe est invalide
     */
    public boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne doit pas être vide.");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 4 caractères.");
        }
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, hash(password));
        return true;
    }

    /**
     * Vérifie les identifiants d'un utilisateur.
     *
     * @param username le nom d'utilisateur
     * @param password le mot de passe en clair
     * @return true si le couple utilisateur/mot de passe est correct
     */
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        String storedHash = users.get(username);
        return storedHash != null && storedHash.equals(hash(password));
    }

    /**
     * @return le nombre d'utilisateurs enregistrés
     */
    public int userCount() {
        return users.size();
    }

    private static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 non disponible", e);
        }
    }
}
