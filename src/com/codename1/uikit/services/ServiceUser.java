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
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.entities.User;
import com.codename1.uikit.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class ServiceUser {
     public static ServiceUser instance= null;
     public static boolean resultOk = true;
      public boolean resultOKk;
boolean ok=false;
    String json;

    //initilisation connection request 
 
    private ConnectionRequest req;
    public ArrayList<User> user;
     
    public static ServiceUser getInstance(){
        if(instance==null)
            instance = new ServiceUser();
         return instance;
        
    }
    public ServiceUser(){
        req = new ConnectionRequest();
    }
     public void AjouterUser(User user){
        String url= Statics.BASE_URL+"jsonuser/inscriptionMobile?fullname="+user.getFullname()+"&email="+user.getEmail()+"&username="+user.getUsername()+"&password="+user.getPassword()+"&birth="+user.getBirth()+"&country="+user.getCountry()+"&adress="+user.getAdress();
        req.setUrl(url);
        req.addResponseListener(a->{
            String str = new String(req.getResponseData());
            System.err.println("data=="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
     
  
     public ArrayList<User>affichageUsers() {
        ArrayList<User> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"jsonuser/user/liste";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapCommentaires = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCommentaires.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        User re = new User();
                        
                        //dima id fi codename one float 5outhouha
                      float idcompt = Float.parseFloat(obj.get("idcompt").toString());
                        
                        String fullname = obj.get("fullname").toString();
                        String username = obj.get("username").toString();
                          String email = obj.get("email").toString();
                        String country = obj.get("country").toString();
                        String adress = obj.get("adress").toString();
                          String password = obj.get("password").toString();
                        
                  
                          
                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        
                        re.setId((int)idcompt);
                        
                        re.setFullname(fullname);
                        re.setUsername(username);
                          re.setEmail(email);
                        re.setUsername(username);
                             re.setPassword(password);
                              re.setCountry(country);
                               re.setAdress(adress);
                               
                               re.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("birth").toString()));
                    
                       
                        
                  
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
public boolean Delete(User t) {
        String url = Statics.BASE_URL + "jsonuser/user/supprimer/"+t.getId() ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOKk = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOKk;
    }
    //Delete 
    public boolean deleteUser(int id ) {
        String url = Statics.BASE_URL +"jsonuser/user/supprimer/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
    public boolean modifierUser(User user) {
        String url = Statics.BASE_URL +"json/user/modif/"+user.getId()+"?fullname="+user.getFullname()+"&email="+user.getEmail()+"&username="+user.getUsername()+"&password="+user.getPassword()+"&birth="+user.getBirth()+"&country="+user.getCountry()+"&adress="+user.getAdress();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
    
    
    
    
    
     
    public boolean signin(TextField email,TextField password ) {
       
        
        String url = Statics.BASE_URL+"jsonuser/loginMobile?email="+email.getText().toString()+"&password="+password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
      
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            
            
            
            try {
            
            if(json.equals("password incorrect")||json.equals("email don't have account")) {
                Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK",null);
                ok=false;
            }
            else if(json!="") {
                System.out.println("data =="+json);
                ok=true;
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                
             
                //Session 
            //   float id = Float.parseFloat(user.get("id_user").toString());
                
              //   float tel = Float.parseFloat(user.get("tel").toString());
                //  float age = Float.parseFloat(user.get("age").toString());
           //    SessionManager.setId_user((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
            //    SessionManager.setPassword(user.get("password").toString());
             //   SessionManager.setUsername(user.get("username").toString());
             //   SessionManager.setEmail(user.get("email").toString());
              //  SessionManager.setNom(user.get("nom").toString());
              //  SessionManager.setPrenom(user.get("prenom").toString());
              //  SessionManager.setTel((int)tel);
           // SessionManager.setAge((int)age);
            }
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ok;
    }
    
    
    
    
    
    public ArrayList<User>affichageUsersparemail(String id) {
        ArrayList<User> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"jsonuser/user/liste/"+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapCommentaires = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCommentaires.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        User re = new User();
                        
                        //dima id fi codename one float 5outhouha
                      float idcompt = Float.parseFloat(obj.get("idcompt").toString());
                        
                        String fullname = obj.get("fullname").toString();
                        String username = obj.get("username").toString();
                          String email = obj.get("email").toString();
                        String country = obj.get("country").toString();
                        String adress = obj.get("adress").toString();
                          String password = obj.get("password").toString();
                        
                  
                          
                        //Float etat = Float.parseFloat(obj.get("etat").toString());
                        
                        re.setId((int)idcompt);
                        
                        re.setFullname(fullname);
                        re.setUsername(username);
                          re.setEmail(email);
                        re.setUsername(username);
                             re.setPassword(password);
                              re.setCountry(country);
                               re.setAdress(adress);
                               
                               re.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("birth").toString()));
                    
                       
                        
                  
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }

  
}
