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
import java.io.IOException;
import myapp.Entities.User;
import myapp.Services.ServiceUser;
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
public class updateuser extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;
    String pp = "";

    Resources r;
    private User user;

    public updateuser(User t,Form previous) {

    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Category info");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.user = t;

        System.out.println(user);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
            ServiceUser.getInstance().deleteUser(this.user.getId());
            new affichageuser().show();
        });

        try {

            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container usernameContainer = new Container(BoxLayout.xCenter());
            Container emailContainer = new Container(BoxLayout.xCenter());
            Container passwordContainer = new Container(BoxLayout.xCenter());
            
            Container FromContainer = new Container(BoxLayout.y());

            Label usernameLabel = new Label("Username: ");
            Label emailLabel = new Label("email: ");
            Label passwordLabel = new Label("password: ");
            String url = Statics.base_url + "/userpic/" + user.getAvatar();
            Image categoryImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            categoryImage = categoryImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(categoryImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextField Username = new TextField("", "Username", 20, TextField.ANY);
            TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
            TextField password = new TextField("", "password", 20, TextField.PASSWORD);

            Username.setText(t.getUsername());
            email.setText(t.getEmail());

            password.setText(t.getPassword());
            
            usernameContainer.addAll(usernameLabel, Username);
            emailContainer.addAll(emailLabel, email);
            passwordContainer.addAll(passwordLabel, password);
            
            FromContainer.addAll(usernameContainer, emailContainer,passwordContainer);
            imageContainer.add(image);

            Button EditBt = new Button("Modifier User");

            EditBt.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((Username.getText().length() == 0) || (email.getText().length() == 0)) {
                        Dialog.show("Alert", "Fill all the fields", new Command("ok"));
                    } else {
                        user.setUsername(Username.getText());
                        user.setEmail(email.getText());
                        user.setPassword(password.getText());
                        ServiceUser.getInstance().modifierUser(t);

                        if (ServiceUser.getInstance().modifierUser(user)) {
                             new affichageuser().show();

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
