/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import myapp.Entities.Produit;
import myapp.Services.ServiceProd;
import myapp.Services.ServiceCart;
import myapp.Utils.Statics;
import java.io.IOException;
import myapp.Entities.Panier;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Dialog;

/**
 *
 * @author Amira
 */
public class ProductForm extends Form {

    private Produit product;
 

    public ProductForm(Produit product, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(product.getNomProduit());
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");

        this.product = product;
       

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT, e -> {
                Form editForm = new ProductEditForm(this.product, this);
                editForm.show();
            });
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
                ServiceProd.getInstance().deleteProduct(this.product.getId());
                new ListProductsForm().show();
            });
        
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.base_url + "/eshop/produit/" + product.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            gameImage = gameImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(gameImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextArea descriptionTA = new TextArea(product.getDescription());
            TextArea tfName = new TextArea(product.getNomProduit());
            TextArea tfCatID = new TextArea(product.getCategorie()+"");
            TextArea tfPrice = new TextArea(product.getPrix() + "");
            TextArea tfQuantityStocked = new TextArea(product.getQuantiteStock() + "");

            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            tfName.setEditable(false);
            tfName.setFocusable(false);
            tfName.setUIID("Label");
            tfName.getAllStyles().setAlignment(TextArea.CENTER);

            tfCatID.setEditable(false);
            tfCatID.setFocusable(false);
            tfCatID.setUIID("Label");
            tfCatID.getAllStyles().setAlignment(TextArea.CENTER);

            tfPrice.setEditable(false);
            tfPrice.setFocusable(false);
            tfPrice.setUIID("Label");
            tfPrice.getAllStyles().setAlignment(TextArea.CENTER);

            tfQuantityStocked.setEditable(false);
            tfQuantityStocked.setFocusable(false);
            tfQuantityStocked.setUIID("Label");
            tfQuantityStocked.getAllStyles().setAlignment(TextArea.CENTER);

            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);
            
            Button btnCart = new Button("Add to Cart");
             btnCart.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                     Panier cart = new Panier();
                         cart.setProduct(product);
                    if (ServiceCart.getInstance().addCart(cart)) {
                        Form current = new ListProductsForm();
                        current.show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));
                    }
                }
            }        
        );
            this.addAll(imageContainer, descriptionContainer, tfName, tfCatID, tfPrice, tfQuantityStocked,btnCart);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
