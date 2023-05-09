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
import myapp.Entities.Categorie;
import myapp.Services.ServiceCategories;
import myapp.Utils.Statics;
import java.io.IOException;

/**
 *
 * @author Amira
 */
public class CategoryEditForm extends Form {

    private Categorie category;

    public CategoryEditForm(Categorie category, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Category info");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.category = category;

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
            ServiceCategories.getInstance().deleteCategory(this.category.getId());
            new afficherCategorieForm().show();
        });

        try {

            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            Container NameContainer = new Container(BoxLayout.xCenter());
            Container FromContainer = new Container(BoxLayout.y());

            Label nameLabel = new Label("Nom Categorie: ");
            Label descLabel = new Label("Description : ");
            String url = Statics.base_url + "/eshop/categorie/" + category.getImage();
            Image categoryImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            categoryImage = categoryImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(categoryImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextField categoryNameTA = new TextField(category.getNomCategorie(), "Nom Categorie");
            TextField descriptionTA = new TextField(category.getDescription(), "Description");

            NameContainer.addAll(nameLabel, categoryNameTA);
            descriptionContainer.addAll(descLabel, descriptionTA);
            FromContainer.addAll(NameContainer, descriptionContainer);
            imageContainer.add(image);

            Button EditBt = new Button("Edit Category");

            EditBt.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((categoryNameTA.getText().length() == 0) || (descriptionTA.getText().length() == 0)) {
                        Dialog.show("Alert", "Fill all the fields", new Command("ok"));
                    } else {

                        category.setNomCategorie(categoryNameTA.getText());
                        category.setDescription(descriptionTA.getText());

                        if (ServiceCategories.getInstance().updateCategory(category)) {
                             new afficherCategorieForm().show();

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
