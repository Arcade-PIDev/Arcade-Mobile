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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import myapp.Entities.SeanceCoaching;
import myapp.Services.ServiceSeanceCoaching;
import myapp.Utils.Statics;
/*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
*/
/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class AjoutSeance extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;
    String msg;
    SeanceCoaching t;
    Resources r;
    String pp = "";
    SeanceCoaching SeanceCoaching;

    String ch;
    
    public AjoutSeance(Form previous) {

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Ajouter Categorie");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        
            TextField titreSeance = new TextField ("","Titre",20,TextField.ANY);
            TextField descriptionSeance = new TextField("","description Seance",20,TextField.ANY);
            TextField prixSeance = new TextField("","prix Seance",20,TextField.NUMERIC);

            Picker dateDebutSeance = new Picker();
            Picker dateFinSeance = new Picker();
            
            

        //upload image
        Label imageLabel = new Label("Image");
        Button selectImage = new Button("Choisir");
        TextField imageField = new TextField("", "Choisir image", 10, TextArea.ANY);
        imageField.setEditable(false);

        String h;
        selectImage.addActionListener((evt) -> {
            Display.getInstance().openGallery((e) -> {
                if (e != null && e.getSource() != null) {
                    String filePath = (String) e.getSource();
                    imageField.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
                    ch = filePath;
                }
            }, Display.GALLERY_IMAGE
            );

        }
        );

        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

        photoContainer.add(imageLabel);
        photoContainer.add(imageField);
        photoContainer.add(selectImage);

        Button btnSubmit = new Button("Valider");
        btnSubmit.addActionListener(
                
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((titreSeance.getText().length() == 0) || (descriptionSeance.getText().length() == 0) || (imageField.getText().length() == 0)) {
                    Dialog.show("Alert", "please", new Command("ok"));
                } else {
                            
                    SeanceCoaching = new SeanceCoaching(Double.parseDouble(prixSeance.getText()), titreSeance.getText(), descriptionSeance.getText(), ch, dateDebutSeance.getDate(), dateFinSeance.getDate());
                        ServiceSeanceCoaching.getInstance().AjouterSeanceCoaching(SeanceCoaching);
                        new affichageSeance().show();

                }
            }

        }
        );
        addAll(prixSeance, titreSeance, descriptionSeance, dateDebutSeance,dateFinSeance, photoContainer, btnSubmit);

    }
}
