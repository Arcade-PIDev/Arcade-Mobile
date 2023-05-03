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
package com.codename1.uikit.pheonixui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.entities.Jeux;
import com.codename1.uikit.services.ServiceJeux;

import java.io.IOException;
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

    public Ajoutjeux(Resources res) {

        super("Ajouter Jeux", BoxLayout.y());
        installSidemenu(res);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evvt) {
                new NewsfeedForm(res).showBack();
            }
        });

        r = res;
        try {
            enim = EncodedImage.create("/giphy.gif");

            //img=URLImage.createToStorage(enim,"http://127.0.0.1:8000/uploads/Annonces/"+evt.getPathimg(), "http://127.0.0.1:8000/uploads/Annonces/"+evt.getPathimg(),URLImage.RESIZE_SCALE).scaled(500, 350);
            TextField nom = new TextField("", "nom", 20, TextField.ANY);
            TextField description = new TextField("", "description", 20, TextField.ANY);
            TextField genre = new TextField("", "genre", 22, TextField.ANY);
            TextField color = new TextField("", "color", 20, TextField.ANY);
            TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
            //-----------------------------------------------------------------------
            Button btCapture = new Button("Upload image");
            Label lblLImage = new Label();

            btCapture.addActionListener((ActionEvent e) -> {
                String path = Capture.capturePhoto(50, -1);
                pp = path;
                System.out.println(path);
                if (path != null) {
                    try {
                        Image imgg = Image.createImage(path);
                        lblLImage.setIcon(imgg);
                        System.out.println(path);
                        File photoFile = new File(path);
                        String photoFileName = photoFile.getName().replace(':', '_');
                        System.out.println(photoFileName.substring(4));
                        pp = photoFileName.substring(4);
                        Path newFilePath = Paths.get("C:\\xampp\\htdocs\\ArcadePi\\public\\Jeux\\" + photoFileName.substring(4));

                        Files.move(Paths.get(photoFile.getAbsolutePath().substring(7)), newFilePath, StandardCopyOption.REPLACE_EXISTING);
                        revalidate();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //------------------------------------------------------------------------
            
            add(nom);
            add(description);
            add(genre);
            add(color);
            add(email);

            add(btCapture);
            add(lblLImage);

            Button valide = new Button("Valide");
            valide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent et) {
                    String to = email.getText(); // recipient email address
                    String from = "app@noreply.com"; // sender email address
                    String host = "smtp.gmail.com"; // Gmail SMTP host

                    Properties properties =new Properties();
                    properties.setProperty("mail.smtp.host", host);
                    properties.setProperty("mail.smtp.port", "587");
                    properties.setProperty("mail.smtp.starttls.enable", "true");
                    properties.setProperty("mail.smtp.auth", "true");

                    Session session = Session.getDefaultInstance(properties, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("zeineb.maatoug@esprit.tn","zeinebmaatoug15"); // replace with sender's email and password
                        }
                    });

                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(from));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        message.setSubject("New Game Added"); // set email subject
                        message.setText("Enjoy our new game"); // set email body

                        Transport.send(message);
                        System.out.println("Email Sent Successfully!");
                    } catch (MessagingException mex) {
                        mex.printStackTrace();
                    }
                    Jeux = new Jeux(nom.getText(), description.getText(),pp, genre.getText(), color.getText());
                    ServiceJeux.getInstance().AjouterJeux(Jeux);

                    new NewsfeedForm(r).showBack();
                }
            });
            add(valide);

        } catch (IOException ex) {

        }

        Button back = new Button("Back");

        back.requestFocus();
        back.addActionListener(e -> new NewsfeedForm(res).show());

    }
}
