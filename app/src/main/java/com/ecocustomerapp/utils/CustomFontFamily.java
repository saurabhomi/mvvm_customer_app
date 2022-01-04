package com.ecocustomerapp.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

import timber.log.Timber;

public class CustomFontFamily
{
    static CustomFontFamily customFontFamily;
    HashMap<String,String> fontMap=new HashMap<>();
    public static CustomFontFamily getInstance()
    {
        if(customFontFamily==null)
            customFontFamily=new CustomFontFamily();
        return customFontFamily;
    }
    public void addFont(String alias, String fontName){
        fontMap.put(alias,fontName);
    }
    public Typeface getFont(String alias, Context context)
    {
        String fontFilename = fontMap.get(alias);
        if (fontFilename == null) {
            Timber.e("Font not available with name %s", alias);
            return null;
        }
        else
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontFilename);
        }
    }
}