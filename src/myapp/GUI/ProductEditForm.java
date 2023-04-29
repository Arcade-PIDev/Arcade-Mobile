/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import myapp.Entities.Categorie;
import myapp.Entities.Produit;
import myapp.Services.ServiceCategories;
import myapp.Services.ServiceProd;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Amira
 */
public class ProductEditForm extends Form {

    public ProductEditForm(Produit product, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Edit Product");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Product Name");
        TextField tfDes = new TextField("", "Product Description");
        TextField tfPrice = new TextField("", "Price");
        TextField tfQuantityStocked = new TextField("", "Quantity in stock");
      

        tfName.setText(product.getNomProduit());
        tfPrice.setText(product.getPrix() + "");
        tfQuantityStocked.setText(product.getQuantiteStock() + "");
        tfDes.setText(product.getDescription());

        Button btnSubmit = new Button("Edit Product");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0)
                        || (tfPrice.getText().length() == 0) || (tfQuantityStocked.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {
                   
                    product.setDescription(tfDes.getText());
                    product.setNomProduit(tfName.getText());
                    product.setPrix(Integer.parseInt(tfPrice.getText()));
                    product.setQuantiteStock(Integer.parseInt(tfQuantityStocked.getText()));

                    if (ServiceProd.getInstance().EditProduct(product)) {
                        new ListProductsForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfDes, tfPrice, tfQuantityStocked, btnSubmit);

    }

}
