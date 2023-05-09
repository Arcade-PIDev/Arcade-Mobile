/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import myapp.MyApplication;
import myapp.Entities.Produit;
import myapp.Services.ServiceProd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import myapp.Utils.Statics;

/**
 *
 * @author Amira
 */
public class ListProductsForm extends BaseForm {

    public ListProductsForm() {
        super.addSideMenu();
        setTitle("Liste des Produits");
        setUIID("Activate");

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SHOPPING_CART, e -> {

            Form cartsForm = new ListCartsForm(this);
            cartsForm.show();
        });

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {

            Form addForm = new AddProductForm(this);
            addForm.show();
        });

        List<Produit> Produit = new ArrayList();
        Produit = ServiceProd.getInstance().getAllProducts();

        for (int i = 0; i < Produit.size(); i++) {
            this.add(listOfProducts(Produit.get(i)));
        }
    }

    public Container listOfProducts(Produit c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbNameProduct = new Label();
            Label lbPrice = new Label();

            lbNameProduct.setText(c.getNomProduit().toLowerCase());
            lbPrice.setText(c.getPrix() + "");

            ctninfo.addAll(lbNameProduct, lbPrice);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");
                String url = Statics​.base_url + "/eshop/produit/" + c.getImage();

                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                    Form details = new ProductForm(c, this);
                    details.show();
                });

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
