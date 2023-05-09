/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;

import myapp.Entities.SeanceCoaching;
import myapp.Utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class ServiceSeanceCoaching {

    public static ServiceSeanceCoaching instance = null;
    public static boolean resultOk = true;
    public boolean resultOKk;
    boolean ok = false;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;
    public ArrayList<SeanceCoaching> SeanceCoaching;

    public static ServiceSeanceCoaching getInstance() {
        if (instance == null) {
            instance = new ServiceSeanceCoaching();
        }
        return instance;

    }

    public ServiceSeanceCoaching() {
        req = new ConnectionRequest();
    }

    public void AjouterSeanceCoaching(SeanceCoaching SeanceCoaching) {
        String url = Statics.base_url + "/jsonSeancecoaching/ajout?titreSeance=" + SeanceCoaching.getTitreSeance()+ "&descriptionSeance=" + SeanceCoaching.getDescriptionSeance()+ "&dateDebutSeance=" + SeanceCoaching.getDateDebutSeance()+ "&dateFinSeance=" + SeanceCoaching.getDateFinSeance()+ "&imageSeance=" + SeanceCoaching.getImageSeance()+ "&prixSeance=" + SeanceCoaching.getPrixSeance();
        req.setUrl(url);
        req.addResponseListener(a -> {
            String str = new String(req.getResponseData());
            System.err.println("data==" + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    
    public ArrayList<SeanceCoaching> affichageSeanceCoachings() {
        ArrayList<SeanceCoaching> result = new ArrayList<>();

        String url = Statics.base_url + "/jsonSeancecoaching/liste";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapCommentaires = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCommentaires.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        SeanceCoaching re = new SeanceCoaching();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        String titreSeance = obj.get("titreSeance").toString();
                        String descriptionSeance = obj.get("descriptionSeance").toString();
                     
                        String imageSeance = obj.get("imageSeance").toString();
           
                        Date dateDebutSeance = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateDebutSeance").toString());
                        Date dateFinSeance = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateFinSeance").toString());
                        Double  prixSeance = Double.parseDouble(obj.get("prixSeance").toString());
                        
                        

                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        re.setId((int) id);
                        re.setTitreSeance(titreSeance);
                        re.setDescriptionSeance(descriptionSeance);
                        re.setImageSeance(imageSeance);
                        re.setDateDebutSeance(dateDebutSeance);
                        re.setDateFinSeance(dateFinSeance);
                        re.setPrixSeance(prixSeance);
                        

                        //insert data into ArrayList result
                        result.add(re);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;

    }

    public boolean Delete(SeanceCoaching t) {
        String url = Statics.base_url + "/jsonSeancecoaching/supprimer/" + t.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOKk = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOKk;
    }

    //Delete 
    public boolean deleteSeanceCoaching(int id) {
        String url = Statics.base_url + "/jsonSeancecoaching/supprimer/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    //Update 
    public boolean modifierSeanceCoaching(SeanceCoaching SeanceCoaching) {
        String url = Statics.base_url + "/jsonSeancecoaching/modif/" + SeanceCoaching.getId() + "?titreSeance=" + SeanceCoaching.getTitreSeance()+ "&descriptionSeance=" + SeanceCoaching.getDescriptionSeance()+ "&dateDebutSeance=" + SeanceCoaching.getDateDebutSeance()+ "&dateFinSeance=" + SeanceCoaching.getDateFinSeance()+ "&imageSeance=" + SeanceCoaching.getImageSeance()+ "&prixSeance=" + SeanceCoaching.getPrixSeance();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return resultOk;

    }



    public ArrayList<SeanceCoaching> affichageSeanceCoachingspartitre(String id) {
        ArrayList<SeanceCoaching> result = new ArrayList<>();

        String url = Statics.base_url + "/jsonSeancecoaching/liste/" + id;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapCommentaires = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCommentaires.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        SeanceCoaching re = new SeanceCoaching();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                          //dima id fi codename one float 5outhouha
                       
                    
                         String titreSeance = obj.get("titreSeance").toString();
                        String descriptionSeance = obj.get("descriptionSeance").toString();
                     
                        String imageSeance = obj.get("imageSeance").toString();
           
                        Date dateDebutSeance = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateDebutSeance").toString());
                         Date dateFinSeance = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateFinSeance").toString());
                        Double  prixSeance = Double.parseDouble(obj.get("prixSeance").toString());
                        
                        

                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        re.setId((int) id);

                        re.setTitreSeance(titreSeance);
                        re.setDescriptionSeance(descriptionSeance);
                        re.setImageSeance(imageSeance);
                        re.setDateDebutSeance(dateDebutSeance);
                        re.setDateFinSeance(dateFinSeance);
                        re.setPrixSeance(prixSeance);
                        

                        //insert data into ArrayList result
                        result.add(re);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;

    }

}
