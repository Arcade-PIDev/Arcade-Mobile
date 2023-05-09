/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.GUI;

import myapp.Entities.Commande;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import myapp.MyApplication;
import java.io.IOException;
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
public class commandeForm extends Form{
    
    public commandeForm(Commande o) {
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CHECK, e -> {
            Form ListProductsForm = new ListProductsForm();
            ListProductsForm.show();
        });


        setTitle("My Order");
        setLayout(BoxLayout.y());
        setUIID("Activate");

        Label orderId = new Label("order Id :");
        Label prixTotal = new Label("Prix Total: ");
        Label orderIdInfo = new Label(o.getId() + "");
        Label totalPriceInfo = new Label(o.getPrixTotal() + "");

        orderId.getAllStyles().setFgColor(0x0ffff);
        prixTotal.getAllStyles().setFgColor(0x0ffff);

        Container ctnId = new Container(BoxLayout.x());
        Container ctnPrice = new Container(BoxLayout.x());
        ctnId.addAll(orderId, orderIdInfo);
        ctnPrice.addAll(prixTotal, totalPriceInfo);
        
        String data = "Id: " + o.getId() + " Prix Total : " + o.getPrixTotal();
            
        Image img;
        ImageViewer imgv;
        EncodedImage enc;
        Label imageLabel = new Label("Scan me: ");
        imageLabel.getAllStyles().setFgColor(0x0ffff);
        Container ctn = new Container(BoxLayout.x());
        try {
            enc = EncodedImage.create("/spinner.png");

            String url = "http://api.qrserver.com/v1/create-qr-code/?data=" +data;
            img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

            imgv = new ImageViewer(img);
            ctn.addAll(imageLabel, imgv);
        }
        catch (IOException e)
        {
            System.out.println("error");
        }
        addAll(ctnId, ctnPrice, ctn);
        
    }

}