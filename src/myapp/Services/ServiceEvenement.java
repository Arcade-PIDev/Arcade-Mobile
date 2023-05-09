/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import myapp.Entities.Evenement;
import myapp.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Amira
 */
public class ServiceEvenement {
    
    public Boolean resultOK;
    public static ServiceEvenement instance;
    private ConnectionRequest req;
    public ArrayList<Evenement> EvenementList;

    private ServiceEvenement() {
        req = new ConnectionRequest();
    }
    
    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }
    
        public void supprimerEventID(int id) {
        String url = Statics.base_url + "/supprimerEvent/api/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    
    public Boolean updateFormEventMaker(Evenement E) {
        String url = Statics.base_url + "/modifierEvent/api/" + E.getId() + "?NomEvent=" + E.getNomEvent() + "&DescriptionEvent=" + E.getDescriptionEvent() + "&lieu=" + E.getLieu();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }
        
    public Boolean ajouterEvenement(Evenement E) {

        String url = Statics.base_url + "/ajouterEvent/api?AfficheE=" + E.getAfficheE()  +  "&DescriptionEvent=" + E.getDescriptionEvent() + "&NomEvent=" + E.getNomEvent() + "&lieu=" + E.getLieu();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage("Evenement ajoutée", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
    
    public ArrayList<Evenement> parseEvenement(String jsonText) {
        try {
            EvenementList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> EvenementListJson;

            EvenementListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) EvenementListJson.get("root");
            for (Map<String, Object> obj : list) {
                Evenement E = new Evenement();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String NomEvent = obj.get("NomEvent").toString();
                String AfficheE = obj.get("AfficheE").toString();
                String DescriptionEvent = obj.get("DescriptionEvent").toString();
                String lieu = obj.get("lieu").toString();

                E.setId(id);
                E.setNomEvent(NomEvent);
                E.setAfficheE(AfficheE);
                E.setDescriptionEvent(DescriptionEvent);
                E.setLieu(lieu);
                EvenementList.add(E);
            }

        } catch (IOException ex) {

        }
        return EvenementList;
    }
    
    public ArrayList<Evenement> getAllEvenements() {
        String url = Statics.base_url + "/afficherEvent/api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                EvenementList = parseEvenement(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return EvenementList;
    }
}
