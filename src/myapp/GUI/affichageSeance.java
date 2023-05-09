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
import myapp.Entities.SeanceCoaching;
import myapp.Services.ServiceSeanceCoaching;
import myapp.Utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class affichageSeance extends  BaseForm {
EncodedImage enim;
 Image img;
 ImageViewer imv;
private static final String HTML_API_KEY = "AIzaSyC_i6nNp6sOrxr_VmksWPmibQn5aIHMqoo";

     public affichageSeance() {
        super.addSideMenu();
        setTitle("Liste des seances");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AjoutSeance(this);
                   addForm.show();
        });

        List<SeanceCoaching> categorie = new ArrayList();
        categorie = ServiceSeanceCoaching.getInstance().affichageSeanceCoachings();

        for (int i = 0; i < categorie.size(); i++) {
            this.add(listOfCategories(categorie.get(i)));
        }
    }
    
    public Container listOfCategories(SeanceCoaching c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            
        Label titreSeance=new Label("titre Seance : "+c.getTitreSeance());
        Label descriptionSeance=new Label("description Seance : "+c.getDescriptionSeance());
        Label prixSeance =new Label("price : "+c.getPrixSeance());
        Label dateDebutSeance =new Label("date Debut Seance : "+c.getDateDebutSeance());
        Label dateFinSeance =new Label("date Fin Seance : "+c.getDateFinSeance());
        

            ctninfo.addAll(titreSeance, descriptionSeance,prixSeance,dateDebutSeance,dateFinSeance);
            
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/imagesnada/" + c.getImageSeance();

                System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new updateabon(c, this);
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
