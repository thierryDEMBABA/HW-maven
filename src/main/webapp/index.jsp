<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.auth.AuthService" %>
<%@ page import="com.example.geometry.CircleCalculator" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Hello World - Déploiement Jenkins/Tomcat</title>
</head>
<body>
    <h1>Hello, World !</h1>
    <p>Ce WAR a été construit par Maven et déployé automatiquement sur Tomcat par Jenkins.</p>

    <h2>Module d'authentification</h2>
    <%
        AuthService auth = new AuthService();
        auth.register("thierry", "motdepasse");
        boolean ok = auth.authenticate("thierry", "motdepasse");
        boolean ko = auth.authenticate("thierry", "mauvais");
    %>
    <ul>
        <li>Authentification avec le bon mot de passe : <strong><%= ok ? "réussie" : "échouée" %></strong></li>
        <li>Authentification avec un mauvais mot de passe : <strong><%= ko ? "réussie" : "échouée" %></strong></li>
    </ul>

    <h2>Module périmètre d'un cercle</h2>
    <form method="get">
        <label for="rayon">Rayon :</label>
        <input type="number" id="rayon" name="rayon" step="any" min="0"
               value="<%= request.getParameter("rayon") != null ? request.getParameter("rayon") : "5" %>">
        <button type="submit">Calculer</button>
    </form>
    <%
        String param = request.getParameter("rayon");
        double rayon = 5.0;
        String erreur = null;
        if (param != null) {
            try {
                rayon = Double.parseDouble(param);
            } catch (NumberFormatException e) {
                erreur = "Rayon invalide : " + param;
            }
        }
        if (erreur == null && rayon < 0) {
            erreur = "Le rayon ne peut pas être négatif.";
        }
    %>
    <% if (erreur != null) { %>
        <p style="color: red;"><%= erreur %></p>
    <% } else { %>
        <p>Périmètre d'un cercle de rayon <%= rayon %> :
           <strong><%= CircleCalculator.perimetre(rayon) %></strong></p>
    <% } %>
</body>
</html>
