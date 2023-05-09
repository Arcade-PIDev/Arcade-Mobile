/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import myapp.Entities.Evenement;
import myapp.Services.ServiceEvenement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import myapp.Entities.Evenement;
import myapp.Utils.Statics;
/**
 *
 * @author Amira
 */
public class afficherEvenementForm extends BaseForm {
    
    public afficherEvenementForm() {
        super.addSideMenu();
        setTitle("Liste des Evenements");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AddEvenementForm(this);
                   addForm.show();
        });

        List<Evenement> Evenement = new ArrayList();
        Evenement = ServiceEvenement.getInstance().getAllEvenements();

        for (int i = 0; i < Evenement.size(); i++) {
            this.add(listOfEvenements(Evenement.get(i)));
        }
    }
    
    public Container listOfEvenements(Evenement E) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbNomEvenement = new Label();
            Label lbDescription = new Label();
            Label lbId = new Label();

            lbNomEvenement.setText(E.getNomEvent().toLowerCase());
            lbDescription.setText(E.getDescriptionEvent().toLowerCase());

            ctninfo.addAll(lbNomEvenement, lbDescription);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/afficheEvent/" + E.getAfficheE();

                System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new EvenementEditForm(E, this);
                        details.show();
                    });
                
                ctn.addAll(imgv, ctninfo);
                
            } catch (IOException e) {
                //System.out.println(e.getMessage());
            }
        } catch (NullPointerException ex) {
            //System.out.println(ex.getMessage());
        }
        return ctn;
    }
}
