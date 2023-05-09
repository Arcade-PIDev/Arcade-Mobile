/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
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
import myapp.MyApplication;
import myapp.Entities.Evenement;
import myapp.Services.ServiceEvenement;
import myapp.Utils.Statics;
import java.io.IOException;
import myapp.Entities.Evenement;

/**
 *
 * @author Amira
 */
public class EvenementEditForm extends Form {

    private Evenement Evenement;

    public EvenementEditForm(Evenement ev, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Evenement info");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.Evenement = ev;

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
            ServiceEvenement.getInstance().supprimerEventID(this.Evenement.getId());
            new afficherEvenementForm().show();
        });

        try {

            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            Container NameContainer = new Container(BoxLayout.xCenter());
            Container lieuContainer = new Container(BoxLayout.xCenter());
            Container FromContainer = new Container(BoxLayout.y());

            Label nameLabel = new Label("Nom Evenement: ");
            Label descLabel = new Label("Description: ");
            Label lieuLabel = new Label("Lieu: ");
            
            String url = Statics.base_url + "/afficheEvent/" + Evenement.getAfficheE();
            Image EvenementImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            EvenementImage = EvenementImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(EvenementImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextField EvenementNameTA = new TextField(Evenement.getNomEvent(), "Nom Evenement");
            TextField descriptionTA = new TextField(Evenement.getDescriptionEvent(), "Description");
            TextField lieuTA = new TextField(Evenement.getLieu(), "Lieu");

            NameContainer.addAll(nameLabel, EvenementNameTA);
            descriptionContainer.addAll(descLabel, descriptionTA);
            lieuContainer.addAll(lieuLabel, lieuTA);
            FromContainer.addAll(NameContainer, descriptionContainer,lieuContainer);
            imageContainer.add(image);
            
            System.out.println("url" + url);
            
            System.out.println("image" + image);
            Button EditBt = new Button("Modifier Evenement");

            EditBt.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((EvenementNameTA.getText().length() == 0) || (descriptionTA.getText().length() == 0)) {
                        Dialog.show("Alert", "Fill all the fields", new Command("ok"));
                    } else {

                        Evenement.setNomEvent(EvenementNameTA.getText());
                        Evenement.setDescriptionEvent(descriptionTA.getText());
                        Evenement.setLieu(lieuTA.getText());

                        if (ServiceEvenement.getInstance().updateFormEventMaker(Evenement)) {
                             new afficherEvenementForm().show();

                        } else {
                            Dialog.show("erreur", "connection Failed", new Command("ok"));

                        }

                    }
                }

            }
            );
            this.addAll(imageContainer, FromContainer, EditBt);

        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
