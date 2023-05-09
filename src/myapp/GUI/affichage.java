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
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ImageViewer;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import myapp.Entities.Jeux;
import myapp.Services.ServiceJeux;
import myapp.Utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class affichage extends BaseForm {
EncodedImage enim;
 Image img;
 ImageViewer imv;
private static final String HTML_API_KEY = "AIzaSyC_i6nNp6sOrxr_VmksWPmibQn5aIHMqoo";
 
    public affichage() {
        addSideMenu();
        setTitle("Liste des Jeux");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new Ajoutjeux(this);
                   addForm.show();
        });

        List<Jeux> jeux = new ArrayList();
        jeux = ServiceJeux.getInstance().affichageJeuxs();

        System.out.println(jeux);
        for (int i = 0; i < jeux.size(); i++) {
            this.add(listOfCategories(jeux.get(i)));
        }
    }
    
    public Container listOfCategories(Jeux c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {
            
            Label ll=new Label();
            Label nom=new Label();
            Label description=new Label();
            Label genre =new Label();
            Label color =new Label();

            
            nom.setText(c.getNom().toLowerCase());
            description.setText(c.getDescription().toLowerCase());
            genre.setText(c.getGenre().toLowerCase());
            color.setText(c.getColor().toLowerCase());
            

            ctninfo.addAll(ll, nom,description,genre,color);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;

            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/Jeux/" + c.getImage();

                System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new updatejeux(c, this);
                        details.show();
                    });
                
                ctn.addAll(imgv, ctninfo);
                
            } catch (IOException e) {
                //System.out.println(e.getMessage());
            }
        } catch (NullPointerException ex) {
            //System.out.println(ex.getMessage());
        }
        return ctn;
    } 
}
