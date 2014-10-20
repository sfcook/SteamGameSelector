/*
 * The MIT License
 *
 * Copyright 2014 sfcook.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package steamgameselector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author sfcook
 */
public class SteamUtils {
    private static SteamUtils instance = new SteamUtils();
    public Set tags;

    private SteamUtils(){
        tags=new HashSet();
        try {
            populateTags();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static SteamUtils getInstance(){
        return instance;
    }
    
    private void populateTags() throws FileNotFoundException{
        tags.clear();
        Scanner sc = new Scanner(new File("tags.cfg"));
        while(sc.hasNextLine()){
            tags.add(sc.nextLine());
        }
    }
    
    public static Set getAppIds(String url)
    {
        //get source
        
        return getAppIdsBySource("");
    }
    
    public static Set getAppIdsBySource(String source)
    {
        Set appids=new HashSet();
        
        Scanner sc=new Scanner(source);
        while(sc.hasNextLine())
        {
            String line=sc.nextLine();
            //find line containing rgGames var
            if(line.length()>32 && line.substring(0, 32).contains("rgGames"))
            {
                //find appids on line
                Pattern p = Pattern.compile("\"appid\":\\d+");
                Matcher m = p.matcher(line);
                while(m.find())
                {
                    appids.add(Integer.parseInt(line.substring(m.start()+8,m.end())));
                }
                break;
            }
        }
        
        return appids;
    }
    
    //Note: it may be better to find the name and appids at the same time
    public static String getNameBySource(String source)
    {
        String name="";
        
        Scanner sc=new Scanner(source);
        while(sc.hasNextLine())
        {
            String line=sc.nextLine();
            //find line containing title
            String title="<title>Steam Community :: ";
            String closingTitle = " :: Games</title>";
            if(line.contains(title))
            {
                name = line.substring(title.length(),line.length()-closingTitle.length());
                break;
            }
        }
        
        return name;
    }
    
    public static Set getTagsByAppId(String id)
    {
        Set tags=new HashSet();
        
        return tags;
    }
}
