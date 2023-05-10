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
import myapp.Utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import myapp.Entities.User;
import myapp.Services.ServiceUser;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class affichageuser extends BaseForm {

    EncodedImage enim;
    Image img;
    ImageViewer imv;
    private static final String HTML_API_KEY = "AIzaSyC_i6nNp6sOrxr_VmksWPmibQn5aIHMqoo";

    public affichageuser() {

        super.addSideMenu();
        setTitle("Liste des utilisateurs");
        setUIID("Activate");
            /*getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AjoutUser(this);
                   addForm.show();
        });*/

        List<User> categorie = new ArrayList();
        categorie = ServiceUser.getInstance().affichageUsers();

        for (int i = 0; i < categorie.size(); i++) {
            this.add(listOfCategories(categorie.get(i)));
        }
    }
    
    public Container listOfCategories(User c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            
        Label ll=new Label();
        Label Username=new Label("Username: " + c.getUsername());
        
        Label email = new Label("email : " + c.getEmail());
        Label avatar = new Label("avatar : " + c.getAvatar());
        Label password = new Label("password : " + c.getPassword());
        

            ctninfo.addAll(Username, email,avatar,password);
            
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.base_url + "/userpic/" + c.getAvatar();
                

                System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE).scaled(500,350);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new updateuser(c, this);
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
        //  add(nbrmax);
 
        /*
        ArrayList<Ville> pos;
        pos=Service.getInstance().getpos(evt);
        Ville v=new Ville();
        v=pos.get(0);
        
        
                final MapContainer cnt = new MapContainer(HTML_API_KEY); 
               
                Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
                   FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, 3);
                   cnt.setCameraPosition(new Coord(v.getLat(), v.getLog()));
                   cnt.zoom(new Coord(v.getLat(), v.getLog()), 12);
                   cnt.addMarker(EncodedImage.createFromImage(markerImg, false),  new Coord(v.getLat(),v.getLog()), "", "", (ett) -> {
                   });
                   cnt.setPreferredSize(new Dimension(300,300));
            
            add(cnt);
                
        
        Button back = new Button("Back");
        Button comt= new Button("comments");
        back.requestFocus();
        back.addActionListener(e -> new NewsfeedForm(res).show());
        comt.addActionListener(e -> new affichage_cmt(res,evt).show());
        add(comt);
         */

    }

}
