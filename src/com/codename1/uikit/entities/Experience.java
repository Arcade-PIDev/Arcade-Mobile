/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Th3_D0cTor
 */
public class Experience {


    private int id ,id_user;
    private String Title,Decription,img;
    private Date dt;

    public Experience(int id,int id_user, String Title, String Decription, String img,Date dt) {
        this.id = id;
        this.id_user= id_user;
        this.Title = Title;
        this.Decription = Decription;
        this.img = img;
        this.dt = dt;
    }



    public Experience() {
       
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDecription() {
        return Decription;
    }

    public void setDecription(String Decription) {
        this.Decription = Decription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

   

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Experience other = (Experience) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.id_user, other.id_user)) {
            return false;
        }
        if (!Objects.equals(this.Title, other.Title)) {
            return false;
        }
        if (!Objects.equals(this.Decription, other.Decription)) {
            return false;
        }
        if (!Objects.equals(this.img, other.img)) {
            return false;
        }
        if (!Objects.equals(this.dt, other.dt)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "experience{" + "id=" + id +  ", title=" + Title + ", decription=" + Decription + ", img=" + img + ", id_user=1" + ", dt=" + dt + '}';
    }

    

   
   

    
   
    
}
