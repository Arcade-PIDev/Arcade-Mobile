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
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date ; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author bhk
 */
public class Service {

    public ArrayList<offre> tasks;
   
    
    
    public static Service instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Service() {
         req = new ConnectionRequest();
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public boolean addTask(offre t) {
        String url = Statics.BASE_URL + "json/offre/ajout?nomoffre="+t.getNom()+"&datedebut="+t.getDate_deb()+"&datefin="+t.getDate_fin()+"&description="+t.getDecription()+"&imgsrc="+t.getImage()+"&couleur="+t.getCouleur()+"&typeoffre="+t.getType();
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
 public boolean updateTask(offre t) {
        String url = Statics.BASE_URL + "json/offre/modif/"+t.getId()+"?nomoffre="+t.getNom()+"&datedebut="+t.getDate_deb()+"&datefin="+t.getDate_fin()+"&description="+t.getDecription()+"&imagerc="+t.getImage()+"&couleur="+t.getCouleur()+"&typeoffre="+t.getType() ;
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
    
    public boolean Delete(offre t) {
        String url = Statics.BASE_URL + "json/offre/supprimer/"+t.getId() ;
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
    
    public ArrayList<offre> parseTasks(String jsonText) throws ParseException{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                offre t = new offre();
                float id = Float.parseFloat(obj.get("idoffre").toString());
                t.setId((int)id);
                t.setType(obj.get("typeoffre").toString());
                t.setNom(obj.get("nomoffre").toString());
                 t.setCouleur(obj.get("couleur").toString());
                 t.setDecription(obj.get("description").toString());
                 t.setDate_deb(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("datedebut").toString()));
                 t.setDate_fin(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("datefin").toString()));
                 
                 
               
                t.setImage(obj.get("imgsrc").toString());
                
                tasks.add(t);
            }
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<offre> getAllTasks(){
        
        String url = Statics.BASE_URL+"json/offre/liste";
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
     public ArrayList<offre> getAllTasksByNom(String text){
        
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
