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
import com.codename1.components.FloatingHint;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import myapp.Entities.User;
import myapp.Services.ServiceUser;
import myapp.Utils.Statics;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    ServiceUser su = new ServiceUser();
    Resources theme;
    String ch = "";

    public SignUpForm() {
        super(new BorderLayout());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        Button selectImage = new Button("Insert ur image");
        TextField imageField = new TextField("", "Choisir image", 10, TextArea.ANY);
        imageField.setEditable(false);
        
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
        

        TextField Username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "password", 20, TextField.PASSWORD);

        //   birth.setType(Display.PICKER_TYPE_DATE);
        Username.setSingleLineTextArea(false);

        password.setSingleLineTextArea(false);

        email.setSingleLineTextArea(false);

        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");

        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(Username),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                selectImage,
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator()
        );
        content.setScrollableY(true);

        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((ActionEvent e) -> {
            User u = new User(Username.getText(), email.getText(), password.getText(), ch);
            su.AjouterUser(u);
            new SignInForm().show();

        });
    }

}
