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
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import myapp.Entities.Jeux;
import myapp.Services.ServiceJeux;

import java.io.IOException;/*
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/
/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class Ajoutjeux extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;
    String msg;
    Jeux t;
    Resources r;
    String pp = "";
    Jeux Jeux;
    String ch;

    public Ajoutjeux(Form previous) {

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Ajouter Categorie");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField nom = new TextField("", "nom", 20, TextField.ANY);
            TextField description = new TextField("", "description", 20, TextField.ANY);
            TextField genre = new TextField("", "genre", 22, TextField.ANY);
            TextField color = new TextField("", "color", 20, TextField.ANY);
            TextField email = new TextField("", "email", 20, TextField.EMAILADDR);

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
                if ((nom.getText().length() == 0) || (description.getText().length() == 0) || (imageField.getText().length() == 0)) {
                    Dialog.show("Alert", "please", new Command("ok"));
                } else {

                    Jeux = new Jeux(nom.getText(), description.getText(),ch, genre.getText(), color.getText());
                    //if (
                            ServiceJeux.getInstance().AjouterJeux(Jeux);
                            //) {
                        new affichage().show();

                }
            }

        }
        );
        addAll(nom, description, genre, color, photoContainer, btnSubmit);

    }
}
