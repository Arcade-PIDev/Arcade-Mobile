/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import myapp.MyApplication;
import myapp.Entities.Categorie;
import myapp.Services.ServiceCategories;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import myapp.Utils.Statics;
/**
 *
 * @author Amira
 */
public class afficherCategorieForm extends BaseForm {
    
    public afficherCategorieForm() {
        super.addSideMenu();
        setTitle("Liste des categories");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AddCategoryForm(this);
                   addForm.show();
        });

        List<Categorie> category = new ArrayList();
        category = ServiceCategories.getInstance().getAllCategories();

        for (int i = 0; i < category.size(); i++) {
            this.add(listOfCategories(category.get(i)));
        }
    }
    
    public Container listOfCategories(Categorie c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbNameCategory = new Label();
            Label lbDescription = new Label();
            Label lbId = new Label();

            lbNameCategory.setText(c.getNomCategorie().toLowerCase());
            lbDescription.setText(c.getDescription().toLowerCase());

            ctninfo.addAll(lbNameCategory, lbDescription);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/eshop/categorie/" + c.getImage();

                //System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                ctn.addAll(imgv, ctninfo);
                Form p1 = new afficherCategorieForm();
                p1.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return ctn;
    }
}
