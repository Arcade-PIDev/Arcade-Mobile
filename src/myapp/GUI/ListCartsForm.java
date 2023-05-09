/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import myapp.MyApplication;
import myapp.Entities.Panier;
import myapp.Entities.Commande;
import myapp.Services.ServiceCart;
import myapp.Services.ServiceCommande;

import myapp.Entities.Categorie;
import myapp.Entities.Produit;
import myapp.Services.ServiceCategories;
import myapp.Services.ServiceProd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import myapp.Utils.Statics;
import java.util.StringTokenizer;

/**
 *
 * @author Amira
 */
public class ListCartsForm extends Form {
    int orderTotalPrice;
    int orderId = 0;

    public ListCartsForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("My Shopping Cart");
        setLayout(BoxLayout.y());
        setUIID("Activate");
        List<Panier> cart = new ArrayList();
        cart = ServiceCart.getInstance().getAllCarts();
        orderTotalPrice = 0;

        for (int i = 0; i < cart.size(); i++) {
            this.add(listOfCarts(cart.get(i)));
            orderTotalPrice += (cart.get(i).getProduit().getPrix() * cart.get(i).getQuantite());
            orderId = cart.get(i).getCommande();
            //System.out.println("here again"+orderTotalPrice);

        }

        Button btnFinalizeOrder = new Button("Finalize Order");
        if (cart.size() != 0) {
            add(btnFinalizeOrder);
        }
        btnFinalizeOrder.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Commande o = new Commande();
                //o.setUserId(MyApplication.loggedUser.getId());
                o.setPrixTotal(orderTotalPrice);
                o.setId(orderId);

                if (ServiceCommande.getInstance().updateOrder(o)) {
                    Form orderForm = new commandeForm(o);
                    orderForm.show();
                } else {
                    Dialog.show("erreur", "connection Failed", new Command("ok"));

                }
            }

        }
        );
        ;
    }

    public Container listOfCarts(Panier c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnQuantity = new Container(BoxLayout.x());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbQuantity = new Label();
            Label lbQuantitylb = new Label("Quantity ordered : ");
            Label lbNameProduct = new Label();
            Label lbPrice = new Label();

            lbNameProduct.setText(c.getProduit().getNomProduit());
            lbPrice.setText(c.getProduit().getPrix() + "");
            lbQuantity.setText(c.getQuantite() + "");
            lbQuantitylb.getAllStyles().setFgColor(0x0ffff);
            ctnQuantity.addAll(lbQuantitylb, lbQuantity);
            ctninfo.addAll(lbNameProduct, lbPrice, ctnQuantity);

            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/eshop/produit/" + c.getProduit().getImage();
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                ctn.addAll(imgv, ctninfo);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return ctn;
    }
}
