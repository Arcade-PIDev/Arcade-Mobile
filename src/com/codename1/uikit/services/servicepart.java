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
import com.codename1.uikit.entities.participation;
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
public class servicepart {

    public ArrayList<offre> tasks;
    public ArrayList<participation> taskspart;
   
    
    
    public static servicepart instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private servicepart() {
         req = new ConnectionRequest();
    }

    public static servicepart getInstance() {
        if (instance == null) {
            instance = new servicepart();
        }
        return instance;
    }

    public boolean addTask(participation t) {
        String url = Statics.BASE_URL + "jsonpart/participation/ajout?prenom_part="+t.getPrenom()+"&nom_part="+t.getPrenom()+"&type_part="+t.getType();
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
 public boolean updateTask(participation t) {
        String url = Statics.BASE_URL + "jsonpart/participation/modif/"+t.getId()+"?prenom_part="+t.getPrenom()+"&nom_part="+t.getPrenom()+"&type_part="+t.getType();
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
    
    public boolean Delete(participation t) {
        String url = Statics.BASE_URL + "jsonpart/participation/supprimer/"+t.getId() ;
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
    
    public ArrayList<participation> parseTasks(String jsonText) throws ParseException{
        try {
            taskspart=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                participation t = new participation();
                float id = Float.parseFloat(obj.get("idPart").toString());
                t.setId((int)id);
                t.setType(obj.get("typePart").toString());
                t.setNom(obj.get("nomPart").toString());
                t.setPrenom(obj.get("prenomPart").toString());
                
                taskspart.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return taskspart;
    }
    
    public ArrayList<participation> getAllTasks(){
        
        String url = Statics.BASE_URL+"jsonpart/participation/liste";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    taskspart = parseTasks (new String(req.getResponseData()));
                } catch (ParseException ex) {
                    
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return taskspart;
    }
     public ArrayList<participation> getAllTasksByNom(String text){
        
        String url = Statics.BASE_URL+"plc/all/"+text;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    taskspart = parseTasks(new String(req.getResponseData()));
                } catch (ParseException ex) {
                   
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return taskspart;
    }
    
    
    
    
    
    
    
  
}
