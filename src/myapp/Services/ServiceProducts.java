/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import myapp.Entities.Categorie;
import myapp.Entities.Produit;
import myapp.Utils.Statics;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author Amira
 */
public class ServiceProducts {

    public Boolean resultOK;

    public ArrayList<Produit> ProductsList;
    public static ServiceProducts instance;
    private ConnectionRequest req;
    public Produit product;

    private ServiceProducts() {
        req = new ConnectionRequest();
    }

    public static ServiceProducts getInstance() {
        if (instance == null) {
            instance = new ServiceProducts();
        }
        return instance;
    }

    public ArrayList<Produit> parseProducts(String jsonText) {

        try {
            ProductsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> productsListJson;

            productsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) productsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Produit c = new Produit();
                int id = (int) Float.parseFloat(obj.get("id").toString());

                String categoryItem1 = obj.get("category").toString();
                StringTokenizer st = new StringTokenizer(categoryItem1, ",");
                StringTokenizer st2 = new StringTokenizer(st.nextToken(), ":");
                StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "=");
                st3.nextToken();
                //System.out.println(st3.nextToken());
                int categorie = (int) Float.parseFloat(st3.nextToken());
                int quantiteStock = parseInt(obj.get("quantiteStock").toString());
                String nomProduit = obj.get("nomProduit").toString();
                String image = obj.get("image").toString();
                String description = obj.get("description").toString();
                int prix = parseInt(obj.get("prix").toString());

                c.setId(id);
                c.setDescription(description);
                c.setImage(image);
                c.setNomProduit(nomProduit);
                c.setCategorie(categorie);
                c.setPrix(prix);
                c.setQuantiteStock(quantiteStock);
                ProductsList.add(c);

            }

        } catch (IOException ex) {

        }

        return ProductsList;

    }

    public ArrayList<Produit> getAllProducts() {
        String url = Statics.base_url + "/api/AllProducts";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                ProductsList = parseProducts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ProductsList;
    }

    public void deleteProduct(int id) {
        String url = Statics.base_url + "/api/deleteProduct/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addProduct(Produit c) {

        String url = Statics.base_url + "/api/createProduct?nameProduct=" + c.getNomProduit() + "&description=" + c.getDescription()
                + "&image=" + c.getImage() + "&categoryId=" + c.getCategorie() + "&price=" + c.getPrix() + "&quantityStocked=" + c.getQuantiteStock();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" product added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public Boolean EditProduct(Produit c) {
        System.out.println("this :"+c);
        String url = Statics.base_url + "/api/updateProduct/" + c.getId() + "?nameProduct=" + c.getNomProduit() + "&description=" + c.getDescription()
                + "&image=" + c.getImage() + "&price=" + c.getPrix() + "&quantityStocked=" + c.getQuantiteStock();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" product updated", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
}
