package com.softhub.balajienterprise;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){

        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public void setMobile(String mobile){
        editor.putString("Mbl",mobile);
        editor.commit();
    }

    public void setName(String name){
        editor.putString("Nme",name);
        editor.commit();
    }

    public void setUserRole(String u){
        editor.putString("userRole",u);
        editor.commit();
    }

    public void setUserName(String UserName){
        editor.putString("Unm",UserName);
        editor.commit();
    }

    public void setEmailId(String EmailId){
        editor.putString("Eid",EmailId);
        editor.commit();
    }

    public void setLoginFlag(String LoginFlag){
        editor.putString("Lfg",LoginFlag);
        editor.commit();
    }

    public void setCount(String Count){
        editor.putString("Cnt",Count);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
