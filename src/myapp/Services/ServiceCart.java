/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Services;

import myapp.Entities.Commande;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import myapp.Entities.Panier;
import myapp.Entities.Produit;
import myapp.Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
/**
 *
 * @author Amira
 */
public class ServiceCart {
public Boolean resultOK;
    private ConnectionRequest req;
    public static ServiceCart instance;
    public ArrayList<Panier> cartsList;

    private ServiceCart() {
        req = new ConnectionRequest();
    }

    public static ServiceCart getInstance() {
        if (instance == null) {
            instance = new ServiceCart();
        }
        return instance;
    }

    public Boolean addCart(Panier c) {

        String url = Statics.base_url + "/api/addToCart?produits=" + c.getProduit().getId();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage("item is added to cart", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;
    }    
    
        public ArrayList<Panier> parseCarts(String jsonText) {
        try {
            System.out.println("here " + jsonText);
            cartsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> cartsListJson;

            cartsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) cartsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Panier c = new Panier();

                String productInfo = obj.get("produits").toString();
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println(productInfo);
                StringTokenizer st = new StringTokenizer(productInfo, ",");
                StringTokenizer stID = new StringTokenizer(st.nextToken(), "=");
                stID.nextToken();
                int idProd = (int) Float.parseFloat(stID.nextToken());

                StringTokenizer stName = new StringTokenizer(st.nextToken(), "=");
                stName.nextToken();
                String nameProd = stName.nextToken();

                StringTokenizer stPrice = new StringTokenizer(st.nextToken(), "=");
                stPrice.nextToken();
                int price = (int) Float.parseFloat(stPrice.nextToken());

                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();

                System.out.println(st.nextToken());
                StringTokenizer stImage = new StringTokenizer(st.nextToken(), "=");
                stImage.nextToken();
                String imageString = stImage.nextToken();
                String image = imageString.substring(0, imageString.length() - 1);

                Produit product = new Produit();
                product.setId(idProd);
                product.setImage(image);
                product.setNomProduit(nameProd);
                product.setPrix(price);
                //System.out.println(product);

                int quantity = (int) Float.parseFloat(obj.get("quantite").toString());

                String orderInfo = obj.get("commandes").toString();
                StringTokenizer st1 = new StringTokenizer(orderInfo, ",");
                StringTokenizer stOrderID = new StringTokenizer(st1.nextToken(), "=");
                stOrderID.nextToken();

                int orderId = (int) Float.parseFloat(stOrderID.nextToken());

                c.setProduct(product);
                c.setQuantite(quantity);
                c.setCommande(orderId);

                cartsList.add(c);

            }

        } catch (IOException ex) {

        }

        return cartsList;

    }

    public ArrayList<Panier> getAllCarts() {
        String url = Statics.base_url + "/api/AllCarts";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                cartsList = parseCarts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cartsList;
    }
    
}
