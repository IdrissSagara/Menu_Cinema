package database;

import classesgen.commande.Commande;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;

/**
 * @author Groupe6 La clase GestionnaireCommande pour la gestion des commandes
 */
public class GestionnaireCommande extends SQLAble {

    private Commande commande;
    private  double prix;

    public GestionnaireCommande(String idClient, List<String> idPlats , List<String> idFilms , String adresseLivraison) {
        commande = new Commande();
        commande.setId(idClient);
        commande.setIdPlat(idPlats);
        commande.setIdFilm(idFilms);
        commande.setAdresseLivraison(adresseLivraison);
    }


    public GestionnaireCommande(String id) {
        commande = new Commande();
        commande.setId(id);
    }
      public GestionnaireCommande() {
        commande = new Commande();
       
    }

    /**
     * Cette mÃ©thode permet d'enregistrer la commande en cours "l'objet this" dans la BD
     * @throws java.lang.Exception
     */
    public void enregistrerCommandeDB() throws Exception {
        try {
            connectToDatabase();
            String adrLivr = commande.getAdresseLivraison();
            
            ARRAY arrayPlats = 
                    CreateArray.toARRAY( commande.getIdPlat() , conn);
            ARRAY arrayFilms = CreateArray.toARRAY( commande.getFilm() , conn );
            CallableStatement cstmt;
           if (     ( arrayPlats != null  &&  arrayPlats.length() > 0  &&  adrLivr != null && !adrLivr.isEmpty() )
                 || ( arrayFilms != null  &&  arrayFilms.length() > 0  &&  adrLivr != null && !adrLivr.isEmpty() )     ){

                cstmt = conn.prepareCall("{ = call enregistrerCommande(?,?,?,?,?) }");
                cstmt.setString(1, commande.getIdClient());
                cstmt.setObject(2, arrayPlats);
                cstmt.setObject(3, arrayFilms);
                cstmt.setDouble(4, prix);
                cstmt.setString(5, commande.getAdresseLivraison());
                cstmt.executeUpdate();
                cstmt.close();
                
                OracleCallableStatement ocstmt;
                ocstmt = (OracleCallableStatement) conn.prepareCall("{ ? = call getLastIdCommande() }");
                ocstmt.registerOutParameter( 1 , OracleTypes.VARCHAR );
                ocstmt.execute();

                commande.setId( ocstmt.getString(1) );
                ocstmt.close();
                
           }else{
              throw new Exception("Parametre non valide ( null ou vide ) pour l'appel de la procedure enregistrerCommande");
          }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Commande getCommande(String id) {
        
        Commande commande = new Commande();
        commande.setId(id);
        List<String> platsCommandes = commande.getIdPlat();
        List<String> filmsCommandes = commande.getFilm();
        
        try {
          connectToDatabase();
            OracleCallableStatement ocstmt;
            ocstmt = (OracleCallableStatement) conn.prepareCall("{ = call getcommande(?,?,?,?) }");
            ocstmt.setString( 1 , id );
            ocstmt.registerOutParameter(2, OracleTypes.CURSOR);
            ocstmt.registerOutParameter(3, OracleTypes.CURSOR);
            ocstmt.registerOutParameter(4, OracleTypes.CURSOR);
            ocstmt.execute();
             
            ResultSet rset = (ResultSet) (ocstmt.getObject(2));
            if (rset != null && rset.next()) {
                commande.setIdClient( rset.getString("idClient") );
                commande.setDate( ( rset.getDate("dateCommande") ).toString() );
                commande.setPrix( rset.getDouble("prix") );
                commande.setAdresseLivraison( rset.getString("adresseLivraison") );
            }
            rset.close();
            
            rset = (ResultSet) (ocstmt.getObject(3));
            while ( rset != null && rset.next() ) {
                int quantite = rset.getInt("quantite");
                for ( int i = 0 ; i < quantite ; i++ ) {
                    platsCommandes.add(rset.getString("idPlat"));
                }
            }
            rset.close();
         
            rset = (ResultSet) (ocstmt.getObject(4));
            while ( rset != null && rset.next() ) {
                filmsCommandes.add(rset.getString("idFilm"));
            }
            rset.close();
            
            ocstmt.close();

        }
        catch (SQLException e){
            e.printStackTrace();
            //System.err.println(e);    
        }
            
        return commande;
    }

    @Override
    public String toString() {
        return " { \n" 
                    + " idCommande : \""   + commande.getId() + "\"\n" 
                    + " idClient : \""   + commande.getIdClient() + " \n"
                    + " idFilm : \""   + commande.getFilm().toString() + " \n"
                    + " idPlat : \""   + commande.getIdPlat().toString() + " \n"
                    + " Prix : \""   + commande.getPrix() + " \n"
                    + " Date : \""   + commande.getDate() + " \n"
                    + " Adresse de location : \""   + commande.getAdresseLivraison() + " \n"
                + '}';
    }    
  
}