/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myapp.GUI;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import myapp.Entities.Categorie;
import myapp.Services.ServiceCategories;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Amira
 */
public class AddCategoryForm extends Form {

    String ch;

    public AddCategoryForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Ajouter Categorie");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Nom Categorie");
        TextField tfDes = new TextField("", "Description");

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
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0) || (imageField.getText().length() == 0)) {
                    Dialog.show("Alert", "please", new Command("ok"));
                } else {

                    Categorie cat = new Categorie(tfName.getText(), tfDes.getText(), ch);
                    if (ServiceCategories.getInstance().ajouterCategorie(cat)) {
                        new afficherCategorieForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfDes, photoContainer, btnSubmit);

    }
}
