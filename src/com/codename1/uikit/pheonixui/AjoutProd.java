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
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.entities.offre;
import  com.codename1.uikit.services.Service;
import java.io.IOException;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class AjoutProd extends BaseForm {
EncodedImage enim;
 Image img;
 ImageViewer imv;
String msg;
 offre t;
 Resources r;
 String pp="";


    public AjoutProd(Resources res) {
        
      super("Ajouter offre", BoxLayout.y());
      installSidemenu(res);
      getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_LEFT, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent evvt) {
                      new NewsfeedForm(res).showBack();
                   }
               });
     
      r=res;
          try {
              enim=EncodedImage.create("/giphy.gif");
             
              
            //img=URLImage.createToStorage(enim,"http://127.0.0.1:8000/uploads/Annonces/"+evt.getPathimg(), "http://127.0.0.1:8000/uploads/Annonces/"+evt.getPathimg(),URLImage.RESIZE_SCALE).scaled(500, 350);
           
            
              
           
              
              TextField nom=new TextField();
               TextField decription=new TextField();
                TextField couleur=new TextField();
              TextField type=new TextField();
              TextField adresse=new TextField();
              
               Picker datedeb=new Picker();
              Picker datefin=new Picker();
              nom.setHint("nom offre ");
              decription.setHint("decription");
              
              couleur.setHint("couleur");
               datedeb.setType(Display.PICKER_TYPE_DATE);
              datefin.setType(Display.PICKER_TYPE_DATE);
              String[] characters = { "Demande Sponsoring", "Demande Partenaire", "Besoin de CC pour Contrat","Besoin de CC pour Publicite","Besoin de CC pour Evennement"};


              Picker p = new Picker();
                p.setStrings(characters);
                p.setSelectedString(characters[0]);
              
              
              
              //-----------------------------------------------------------------------
              
              Button btCapture=new Button ("Upload image") ; 
              Label lblLImage=new Label (); 

btCapture.addActionListener((ActionEvent e)->{
    String path=Capture.capturePhoto(50,-1);
    pp=path;
     System.out.println(path);
    if (path != null) {
        try{
            Image imgg= Image.createImage(path);
            lblLImage.setIcon(imgg);
            System.out.println(path);
            revalidate();
         }catch (IOException ex){
            ex.printStackTrace ();
		}

}
});
              
              
              //------------------------------------------------------------------------
              
              
              
              
              
              
              add(nom);
              add(decription);
              add(couleur) ; 
              add(p);
              add(datedeb);
              add(datefin);
              add(btCapture);
              add(lblLImage);
              
              Button valide=new Button("Valide");
              valide.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent et) {
                      t=new offre();
                      t.setNom(nom.getText());
                      t.setDecription(decription.getText());
                      t.setCouleur(couleur.getText());
                      t.setDate_deb(datedeb.getDate());
                      t.setDate_fin(datefin.getDate());
                      t.setType(p.getSelectedString());
                      t.setImage(pp);
                      Service.getInstance().addTask(t);
                      
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
