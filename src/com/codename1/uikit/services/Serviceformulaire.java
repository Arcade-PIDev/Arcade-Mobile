/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.offre;
import com.codename1.uikit.entities.Formulaire;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date ; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 *
 * @author bhk
 */
public class Serviceformulaire {

    public ArrayList<Formulaire> tasks;
   
    
    
    public static Serviceformulaire instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Serviceformulaire() {
         req = new ConnectionRequest();
    }

    public static Serviceformulaire getInstance() {
        if (instance == null) {
            instance = new Serviceformulaire();
        }
        return instance;
    }

    public boolean addTask(Formulaire t) {
        String url = Statics.BASE_URL + "jsonform/formulaire/ajout?contact="+t.getContact()+"&date_de_creation="+t.getDate_de_creation()+"&type="+t.getType()+"&code="+t.getCode()+"&resultat_quiz="+t.getResultat_quiz();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
 public boolean updateTask(Formulaire t) {
        String url = Statics.BASE_URL + "jsonform/formulaire/modif/"+t.getId()+"?contact="+t.getContact()+"&date_de_creation="+t.getDate_de_creation()+"&type="+t.getType()+"&code="+t.getCode()+"&resultat_quiz="+t.getResultat_quiz();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean Delete(Formulaire t) {
        String url = Statics.BASE_URL + "jsonform/formulaire/supprimer/"+t.getId() ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Formulaire> parseTasks(String jsonText) throws ParseException{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Formulaire t = new Formulaire();
                float id = Float.parseFloat(obj.get("id").toString());
                float code = Float.parseFloat(obj.get("code").toString());
                float rezquiz = Float.parseFloat(obj.get("resultat_quiz").toString());
                
                t.setId((int)id);
                t.setContact(obj.get("contact").toString());
                t.setType(obj.get("type").toString());
               
                
                t.setCode((int) code);
                t.setResultat_quiz((int) rezquiz);
                t.setDate_de_creation(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("date_de_creation").toString()));
               
                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<Formulaire> getAllTasks(){
        
        String url = Statics.BASE_URL+"jsonform/formulaire/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    tasks = parseTasks (new String(req.getResponseData()));
                } catch (ParseException ex) {
                    
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
     public ArrayList<Formulaire> getAllTasksByContact(String text){
        
        String url = Statics.BASE_URL+"plc/all/"+text;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    tasks = parseTasks(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
    
    
    
    
    
    
  
}
