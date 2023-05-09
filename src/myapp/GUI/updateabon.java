/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package myapp.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.File;
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
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import myapp.Entities.SeanceCoaching;
import myapp.Services.ServiceSeanceCoaching;
import myapp.Utils.Statics;

import java.io.IOException;/*
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
*/
/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class updateabon extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;

    String pp = "";
    Resources r;
    
    private SeanceCoaching seance;


    public updateabon(SeanceCoaching t, Form previous) {
    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Category info");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.seance = t;

        System.out.println(seance);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
            ServiceSeanceCoaching.getInstance().deleteSeanceCoaching(this.seance.getId());
            new affichageSeance().show();
        });

        try {

            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            Container TitreContainer = new Container(BoxLayout.xCenter());
            Container PrixContainer = new Container(BoxLayout.xCenter());
            Container DateDebutContainer = new Container(BoxLayout.xCenter());
            Container DateFinContainer = new Container(BoxLayout.xCenter());
            
            Container FromContainer = new Container(BoxLayout.y());

            Label TitreLabel = new Label("Titre: ");
            Label descLabel = new Label("Description : ");
            Label PrixLabel = new Label("Prix: ");
            Label DateDebutLabel = new Label("Date Debut : ");
            Label DateFinLabel = new Label("Date Fin : ");
            String url = Statics.base_url + "/imagesnada/" + seance.getImageSeance();
            Image categoryImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            categoryImage = categoryImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(categoryImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextField titreSeance = new TextField(seance.getTitreSeance(), "titreSeance", 20, TextField.ANY);
            TextField descriptionSeance = new TextField(seance.getDescriptionSeance(), "descriptionSeance", 20, TextField.ANY);
            TextField prixSeance = new TextField(seance.getPrixSeance().toString(), "prixSeance", 20, TextField.NUMERIC);
            
            Picker dateFinSeances = new Picker();
            Picker dateDebutSeances = new Picker();
            
            dateFinSeances.setDate(seance.getDateFinSeance());
            dateDebutSeances.setDate(seance.getDateDebutSeance());
            
            
            TitreContainer.addAll(TitreLabel, titreSeance);
            descriptionContainer.addAll(descLabel, descriptionSeance);
            PrixContainer.addAll(PrixLabel, prixSeance);
            DateDebutContainer.addAll(DateDebutLabel, dateFinSeances);
            DateFinContainer.addAll(DateFinLabel, dateDebutSeances);
            
            FromContainer.addAll(TitreContainer, descriptionContainer,PrixContainer,DateDebutContainer,DateFinContainer);
            imageContainer.add(image);

            Button EditBt = new Button("Modifier Seance");

            EditBt.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((titreSeance.getText().length() == 0) || (descriptionSeance.getText().length() == 0)) {
                        Dialog.show("Alert", "Fill all the fields", new Command("ok"));
                    } else {
                        seance.setTitreSeance(titreSeance.getText());
                        seance.setDescriptionSeance(descriptionSeance.getText());
                        seance.setPrixSeance(Double.parseDouble(prixSeance.getText()));
                        seance.setDateFinSeance(dateFinSeances.getDate());
                        seance.setDateDebutSeance(dateDebutSeances.getDate());
                        ServiceSeanceCoaching.getInstance().modifierSeanceCoaching(t);

                        if (ServiceSeanceCoaching.getInstance().modifierSeanceCoaching(seance)) {
                             new affichageSeance().show();

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
