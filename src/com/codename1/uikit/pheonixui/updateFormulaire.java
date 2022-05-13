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

import com.codename1.components.ImageViewer;
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
import com.codename1.uikit.entities.offre;
import com.codename1.uikit.entities.participation;
import com.codename1.uikit.entities.Formulaire;
import  com.codename1.uikit.services.Service;
import com.codename1.uikit.services.servicepart;
import java.io.IOException;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class updateFormulaire extends BaseForm {
EncodedImage enim;
 Image img;
 ImageViewer imv;


 Resources r;


    public updateFormulaire(Resources res,Formulaire t) {
        
      super("formulaire", BoxLayout.y());
      installSidemenu(res);
      getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_LEFT, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent evvt) {
                      new NewsfeedForm(res).showBack();
                   }
               });
     
      r=res;
          
           
              
              
                  TextField  code =new TextField();
              TextField resultat_quiz =new TextField();
               TextField Type=new TextField();
                  TextField contact=new TextField();
                  Picker date=new Picker();
                  date.setType(Display.PICKER_TYPE_DATE);
               
            code.setHint("code");
              resultat_quiz.setHint("resultat quiz ");
              Type.setHint("type");
              contact.setHint("contact");
              
     
            
              
              
              
              //-----------------------------------------------------------------------
              
  
              
              
              //------------------------------------------------------------------------
              
              
              
               add(contact); 
               add(Type);
               add(date) ;
              add(resultat_quiz );
              add(code) ; 
             
              
              
              Button valide=new Button("Valide");
              valide.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent et) {
                   
                      
                      t.setResultat_quiz(Integer.parseInt(resultat_quiz.getText()));
                       t.setCode(Integer.parseInt(code.getText()));
                      t.setType(Type.getText());
                      
                      t.setDate_de_creation(date.getDate());
                      t.setContact(contact.getText());
                      
                      new NewsfeedFormform(r).showBack();
                  }
              });
              add(valide);
                      
                  
              
              
            
       
        
        
        
        
        
        
        
        
        
        Button back = new Button("Back");
        
        back.requestFocus();
        back.addActionListener(e -> new NewsfeedForm(res).show());
       
        
    }
}
