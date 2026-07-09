package com.example.auth;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthServiceTest {

    private AuthService authService;

    @Before
    public void setUp() {
        authService = new AuthService();
    }

    @Test
    public void testRegisterNouvelUtilisateur() {
        assertTrue(authService.register("thierry", "motdepasse"));
        assertEquals(1, authService.userCount());
    }

    @Test
    public void testRegisterUtilisateurExistantEchoue() {
        authService.register("thierry", "motdepasse");
        assertFalse(authService.register("thierry", "autremdp"));
        assertEquals(1, authService.userCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterNomVideEchoue() {
        authService.register("  ", "motdepasse");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMotDePasseTropCourtEchoue() {
        authService.register("thierry", "abc");
    }

    @Test
    public void testAuthenticateAvecBonsIdentifiants() {
        authService.register("thierry", "motdepasse");
        assertTrue(authService.authenticate("thierry", "motdepasse"));
    }

    @Test
    public void testAuthenticateAvecMauvaisMotDePasse() {
        authService.register("thierry", "motdepasse");
        assertFalse(authService.authenticate("thierry", "mauvais"));
    }

    @Test
    public void testAuthenticateUtilisateurInconnu() {
        assertFalse(authService.authenticate("inconnu", "motdepasse"));
    }

    @Test
    public void testAuthenticateAvecNull() {
        assertFalse(authService.authenticate(null, "motdepasse"));
        assertFalse(authService.authenticate("thierry", null));
    }
}
