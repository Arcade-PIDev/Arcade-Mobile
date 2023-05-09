/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.Services;

import com.codename1.io.ConnectionRequest;
import myapp.Entities.Commande;
import myapp.Utils.Statics;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

/**
 *
 * @author Amira
 */
public class ServiceCommande {

    public Boolean resultOK;
    private ConnectionRequest req;
    public static ServiceCommande instance;

    private ServiceCommande() {
        req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }

         public Boolean updateOrder(Commande o) {
         String url = Statics.base_url + "/api/updateOrder/" + o.getId() + "&prixTotal=" + o.getPrixTotal();
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
}
