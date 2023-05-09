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

import myapp.Entities.Jeux;
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
public class ServiceJeux {

    public static ServiceJeux instance = null;
    public static boolean resultOk = true;
    public boolean resultOKk;
    boolean ok = false;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;
    public ArrayList<Jeux> Jeux;

    public static ServiceJeux getInstance() {
        if (instance == null) {
            instance = new ServiceJeux();
        }
        return instance;

    }

    public ServiceJeux() {
        req = new ConnectionRequest();
    }

    public void AjouterJeux(Jeux Jeux) {
        String url = Statics.base_url + "/jsonJeux/Jeux/ajout?nom=" + Jeux.getNom() + "&description=" + Jeux.getDescription() + "&image=" + Jeux.getImage() + "&genre=" + Jeux.getGenre() + "&color=" + Jeux.getColor();
        req.setUrl(url);
        req.addResponseListener(a -> {
            String str = new String(req.getResponseData());
            System.err.println("data==" + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<Jeux> affichageJeuxs() {
        ArrayList<Jeux> result = new ArrayList<>();

        String url = Statics.base_url + "/jsonJeux/Jeux/liste";
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
                        Jeux re = new Jeux();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("nom").toString();
                        String description = obj.get("description").toString();
                        String image = obj.get("image").toString();
                        String genre = obj.get("genre").toString();
                        String color = obj.get("color").toString();
                        
                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        re.setId((int) id);
                        re.setNom(nom);
                        re.setDescription(description);
                        re.setImage(image);
                        re.setGenre(genre);
                        re.setColor(color);
                       
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

    public boolean Delete(Jeux t) {
        String url = Statics.base_url + "/jsonJeux/Jeux/supprimer/" + t.getId();
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
    public boolean deleteJeux(int id) {
        String url = Statics.base_url + "/jsonJeux/Jeux/supprimer/" + id;

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
    public boolean modifierJeux(Jeux Jeux) {
        String url = Statics.base_url + "/jsonJeux/Jeux/modif/" + Jeux.getId() + "?nom=" + Jeux.getNom() + "&description=" + Jeux.getDescription() + "&image=" + Jeux.getImage() + "&genre=" + Jeux.getGenre() + "&color=" + Jeux.getColor();
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

    public ArrayList<Jeux> affichageJeuxspargenre(String id) {
        ArrayList<Jeux> result = new ArrayList<>();

        String url = Statics.base_url + "/jsonJeux/Jeux/liste/" + id;
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
                        Jeux re = new Jeux();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("nom").toString();
                        String description = obj.get("description").toString();

                        String image = obj.get("image").toString();
                        String genre = obj.get("genre").toString();
                        String color = obj.get("color").toString();

                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        re.setId((int) id);
                        re.setNom(nom);
                        re.setDescription(description);
                        re.setImage(image);
                        re.setGenre(genre);
                        re.setColor(color);

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
