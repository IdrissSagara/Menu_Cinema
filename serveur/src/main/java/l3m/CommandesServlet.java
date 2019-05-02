package l3m;

import classesgen.commande.Commande;
import database.GestionnaireCommande;
import database.GestionnaireMenu;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Groupe6 CommandesServlet
 */
public class CommandesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String id = "id";
    private String idClient = "idClient";
    private double prix;
    private String adresseLivraison = "adresseLivraison";
    String[] idplats;
    String[] idFilms;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        id = request.getParameter("id");
        GestionnaireCommande gestonCommande;
        List<Commande> comm = new ArrayList<>();
        gestonCommande = new GestionnaireCommande(id);
        Commande commande = gestonCommande.getCommande(id);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(commande.toString());

    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Commande commande = new Commande();
        
        /* Enumeration<String> P = request.getParameterNames();
        HashMap<String, String> parametres = new HashMap();
        Commande commande = new Commande();

        while (P.hasMoreElements()) {
            String p = P.nextElement();
            parametres.put(p, request.getParameter((String) p));
        }
        commande.setIdClient(parametres.get(idClient));
        // commande.setIdPlat((parametres.values(idPlats));
        //commande.setIdFilm(parametres.get(idFilms));
        commande.setAdresseLivraison(parametres.get(adresseLivraison));
        commande.setPrix(prix);
         */

        // recuperation des donnes
        //id = request.getParameter("id");
        
        idClient = request.getParameter("idClient");
        idplats = request.getParameterValues("idPlat");
        idFilms = request.getParameterValues("idFilms");
        adresseLivraison = request.getParameter("adresseLivraison");
        List<String> al = (ArrayList<String>) toArrayList(idplats);
        List<String> alf = (ArrayList<String>) toArrayList(idFilms);
        
        double prixFilms = sommeFilm(idFilms);
        double prixPlats = sommePlat(idplats);
        prix = prixFilms + prixPlats;
        
        commande.setIdClient(idClient);
        commande.setIdPlat(al);
        commande.setIdFilm(alf);
        commande.setAdresseLivraison(adresseLivraison);
        commande.setPrix(prix);

        System.out.println(idClient);
        // calculer du prix de la commande
        
        //insertion dans la base de donnes
        GestionnaireCommande addCommande;
        try {
            addCommande = new GestionnaireCommande(idClient, al, alf, adresseLivraison);
            addCommande.enregistrerCommandeDB();
        } catch (SQLException ex) {
            Logger.getLogger(CommandesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommandesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    // AJOUTER PLAT A UNE COMMANDE EXISTANTE
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        id = request.getParameter("id");

    }

    /**
     * Methode qui permet de calcule le prix total des films choisis par un
     * client client et qui prend en parametre la liste des films
     *
     * @param idFilms
     * @return res
     */
    public double sommeFilm(String[] idFilms) {
        double res;
        int nbreFilm = idFilms.length;
        res = 3.79 * nbreFilm;
        return res;
    }

    /**
     * Methode qui permet de calcule le prix total des plats choisis par un
     * client client et qui prend en parametre la liste des des plats
     *
     * @param idplats
     * @return res
     */
    public double sommePlat(String[] idplats) {
        double res;
        res = 0.0;
        double prixplat = 0.0;
        for (int i = 0; i < idplats.length; i++) {
            String idp = idplats[i];
            GestionnaireMenu gestionmenu = new GestionnaireMenu();
            prixplat = gestionmenu.getPrixPlat(idp);
            res += prixplat;
        }
        return res;
    }

    static <T> List<T> toArrayList(T[] Tableau) {
        List<T> al = new ArrayList<T>();
        for (T obj : Tableau) {
            al.add(obj);
        }
        return al;
    }
}