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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.entities.User;



import com.codename1.uikit.services.ServiceUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class updateuser extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;
    String pp = "";

    Resources r;

    public updateuser(Resources res, User t) {

        super("User", BoxLayout.y());
        installSidemenu(res);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evvt) {
                new NewsfeedFormuser(res).showBack();
            }
        });

        r = res;

        Button btCapture = new Button("update ur image");
        btCapture.addActionListener((ActionEvent e) -> {
            String path = Capture.capturePhoto(50, -1);
            pp = path;
            System.out.println(path);
            if (path != null) {
                try {
                    Image imgg = Image.createImage(path);
                    System.out.println(path);
                    File photoFile = new File(path);
                    String photoFileName = photoFile.getName().replace(':', '_');
                    System.out.println(photoFileName.substring(4));
                    pp = photoFileName.substring(4);
                    Path newFilePath = Paths.get("C:\\Users\\Ghada\\Downloads\\usernadazeineb\\Arcade-symfony-gestionUser\\public\\image\\" + photoFileName.substring(4));

                    Files.move(Paths.get(photoFile.getAbsolutePath().substring(7)), newFilePath, StandardCopyOption.REPLACE_EXISTING);
                    revalidate();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

         TextField Username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "password", 20, TextField.PASSWORD);

        Username.setText(t.getUsername());
        email.setText(t.getEmail());
       
        password.setText(t.getPassword());
       
        
        pp = t.getAvatar(); 
        Button valide = new Button("Valide");
        valide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent et) {
                t.setUsername(Username.getText());
                t.setEmail(email.getText());
                t.setPassword(password.getText());
                
               
                t.setAvatar(pp);
                
                Username.setText(String.valueOf(t.getUsername()));
             
                email.setSingleLineTextArea(false);
                password.setSingleLineTextArea(false);
             
                ServiceUser.getInstance().modifierUser(t) ; 
                new NewsfeedFormuser(r).showBack();
            }
        });
        add(Username);
        add(email);
        add(btCapture) ; 
        add(password);

        
        add(valide);

        Button back = new Button("Back");

        back.requestFocus();
        back.addActionListener(e -> new NewsfeedFormuser(res).show());

    }
}
