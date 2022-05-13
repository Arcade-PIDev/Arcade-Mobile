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
import com.codename1.uikit.entities.Experience;
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
public class serviceeperience {

    public ArrayList<Experience> tasks;
   
    
    
    public static serviceeperience instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private serviceeperience() {
         req = new ConnectionRequest();
    }

    public static serviceeperience getInstance() {
        if (instance == null) {
            instance = new serviceeperience();
        }
        return instance;
    }

    public boolean addTask(Experience t) {
        String url = Statics.BASE_URL + "jsonExper/Experience/ajout?title="+t.getTitle()+"&description="+t.getDecription()+"&img="+t.getImg()+"&id_user="+1;
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
 public boolean updateTask(Experience t) {
        String url = Statics.BASE_URL + "jsonExper/Experience/modif/"+t.getId()+"?title="+t.getTitle()+"&dt="+t.getDt()+"&description="+t.getDecription()+"&img="+t.getImg()+"&id_user="+1;
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
    
    public boolean Delete(Experience t) {
        String url = Statics.BASE_URL + "jsonExper/Experience/supprimer/"+t.getId() ;
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
    
    public ArrayList<Experience> parseTasks(String jsonText) throws ParseException{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Experience t = new Experience();
                float id = Float.parseFloat(obj.get("id").toString());
                float iduser = Float.parseFloat(obj.get("id_user").toString());
                t.setId((int)id);
                t.setId_user((int)iduser);
                t.setTitle(obj.get("title").toString());
                 t.setDecription(obj.get("description").toString());
        
                 
                 
               
                t.setImg(obj.get("img").toString());
                
                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<Experience> getAllTasks(){
        
        String url = Statics.BASE_URL+"jsonExper/Experience/liste";
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
     public ArrayList<Experience> getAllTasksByNom(String text){
        
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
