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
import myapp.Entities.Categorie;
import myapp.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Amira
 */
public class ServiceCategories {
    
    public Boolean resultOK;
    public static ServiceCategories instance;
    private ConnectionRequest req;
    public ArrayList<Categorie> categoriesList;

    private ServiceCategories() {
        req = new ConnectionRequest();
    }
    
    public static ServiceCategories getInstance() {
        if (instance == null) {
            instance = new ServiceCategories();
        }
        return instance;
    }
    
        public void deleteCategory(int id) {
        String url = Statics.base_url + "/deleteCategorie/api/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    
    public Boolean updateCategory(Categorie c) {
        String url = Statics.base_url + "/updateCategorie/api/" + c.getId() + "?nomCategorie=" + c.getNomCategorie() + "&description=" + c.getDescription() + "&image=" + c.getImage();
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
        
    public Boolean ajouterCategorie(Categorie c) {

        String url = Statics.base_url + "/ajouterCategorie/api?nomCategorie=" + c.getNomCategorie() + "&description=" + c.getDescription() + "&image=" + c.getImage();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage("Categorie ajoutée", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
    
    public ArrayList<Categorie> parseCategories(String jsonText) {
        try {
            categoriesList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> categoriesListJson;

            categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) categoriesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Categorie c = new Categorie();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String nomCategorie = obj.get("nomCategorie").toString();
                String image = obj.get("image").toString();
                String description = obj.get("description").toString();

                c.setId(id);
                c.setDescription(description);
                c.setImage(image);
                c.setNomCategorie(nomCategorie);
                categoriesList.add(c);
            }

        } catch (IOException ex) {

        }
        return categoriesList;
    }
    
    public ArrayList<Categorie> getAllCategories() {
        String url = Statics.base_url + "/afficherCategorie/api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                categoriesList = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categoriesList;
    }
}
