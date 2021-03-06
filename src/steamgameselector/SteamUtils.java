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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

/**
 *
 * @author sfcook
 */
public class SteamUtils {
    private Set<String> tags;
    private Map<Integer, String> genres;
    private Map<Integer, String> categories;

    public SteamUtils(){
        tags=new HashSet<String>();
        genres=new HashMap<Integer, String>();
        categories=new HashMap<Integer, String>();
        try {
            populateTags();
            populateGenres();
            populateCategories();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void populateTags() throws FileNotFoundException{
        tags.clear();
        Scanner sc = new Scanner(new File("tags.cfg"));
        while(sc.hasNextLine()){
            tags.add(sc.nextLine());
        }
    }
    public Set getTags(){
        return tags;
    }
    private void populateGenres(){
        try {
            populateMap(genres,"genre.cfg");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Map getGenres(){
        return genres;
    }
    private void populateCategories(){
        try {
            populateMap(categories,"category.cfg");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Map getCategories(){
        return categories;
    }
    private void populateMap(Map map, String file) throws FileNotFoundException{
        map.clear();
        Scanner sc = new Scanner(new File(file));
        while(sc.hasNextInt() && sc.hasNextLine()){
            int id=sc.nextInt();
            sc.nextLine();
            if(sc.hasNextLine())
            {
                map.put(id, sc.nextLine());
            }
        }
    }
    
    public Account getAccount(String url)
    {
        if(url.contains("DOCTYPE"))
        {
            return getAccountSource(url);
        }
        else
        {
            int pos;
            int count=0;
            for(pos=0;pos<url.length();pos++)
            {
                if(url.charAt(pos)=='/')
                    count++;
                if(count>=5)
                    break;
            }
            url=url.substring(0,pos);
        }
        String source="";
        try {
            URL site=new URL(url+"/games/?tab=all");
            source=IOUtils.toString(site);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return getAccountSource(url);
        } catch (IOException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return new Account();
        }
        
        Account account=getAccountSource(source);
        
        return account;
    }
    
    public Account getAccountInfo(String url)
    {
        Account account=new Account();
        String source="";
        try {
            URL site=new URL(url);
            source=IOUtils.toString(site);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return getAccountSource(url);
        } catch (IOException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return new Account();
        }
        
        JsonParser jsonParser=new JsonParser();
        Scanner sc=new Scanner(source);
        while(sc.hasNextLine())
        {
            String line=sc.nextLine();
            
            //use g_rgProfileData data to get account info
            if(line.length()>32 && line.substring(0, 32).contains("g_rgProfileData"))
            {
                line=line.substring(line.indexOf("{"), line.lastIndexOf("}")+1);
                JsonElement json=jsonParser.parse(line);
                
                account.name=json.getAsJsonObject().get("personaname").getAsString();
                account.steamid=json.getAsJsonObject().get("steamid").getAsString();
                break;
            }
        }
        return account;
    }
    public Account getAccountSource(String source)
    {
        Account account=new Account();
        account.games=new HashSet();
        JsonParser jsonParser=new JsonParser();
        
        Scanner sc=new Scanner(source);
        while(sc.hasNextLine())
        {
            String line=sc.nextLine();
            
            //use profileLine to get g_rgProfileData
            if(line.length()>32 && line.substring(0, 32).contains("profileLink"))
            {
                Account accountInfo=getAccountInfo(line.substring(line.indexOf("\"")+1, line.lastIndexOf("\"")));
                account.name=accountInfo.name;
                account.steamid=accountInfo.steamid;
            }
            //find line containing rgGames var
            if(line.length()>32 && line.substring(0, 32).contains("rgGames"))
            {
                line=line.substring(line.indexOf("["), line.lastIndexOf("]")+1);
                JsonElement json=jsonParser.parse(line);
                
                if(json.isJsonArray())
                {
                    for(JsonElement item : json.getAsJsonArray() )
                    {
                        Game game=new Game();
                        game.appid=item.getAsJsonObject().get("appid").getAsInt();
                        game.title=item.getAsJsonObject().get("name").getAsString();
                        account.games.add(game);
                    }
                }
            }
        }
        
        return account;
    }
    
    //no seperate method for source as there shouldn't be any private game pages
    //note: this method is kind of slow these objects should probably be cached
    public Game getGame(int id)
    {
        Game game=new Game();
        
        game.appid=id;
        
        String source="";
        try {
            URL site=new URL("http://store.steampowered.com/api/appdetails/?appids="+id);
            source=IOUtils.toString(site);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return new Game();
        } catch (IOException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return new Game();
        }
        
        JsonElement json=new JsonParser().parse(source);
        json=json.getAsJsonObject().get(Integer.toString(id));
        
        if(json.getAsJsonObject().has("data"))
        {
            json=json.getAsJsonObject().get("data");
            game.title=json.getAsJsonObject().get("name").getAsString();
            if(json.getAsJsonObject().get("genres")!=null)
            {
                for(JsonElement item : json.getAsJsonObject().get("genres").getAsJsonArray())
                {
                    String tag=genres.get(item.getAsJsonObject().get("id").getAsInt());
                    if(tag!=null && !tag.equals("NULL"))
                        game.tags.add(tag);
                    else
                        game.tags.add(item.getAsJsonObject().get("description").getAsString());
                }
            }
            if(json.getAsJsonObject().get("categories")!=null)
            {
                for(JsonElement item : json.getAsJsonObject().get("categories").getAsJsonArray())
                {
                    String tag=categories.get(item.getAsJsonObject().get("id").getAsInt());
                    if(tag!=null && !tag.equals("NULL"))
                        game.tags.add(tag);
                    else
                        game.tags.add(item.getAsJsonObject().get("description").getAsString());
                }
            }
        }
        
        return game;
    }
    
    public Set<String> getGameTags(int appid)
    {
        Set<String> tags=new HashSet();
        String source;
        
        //tag pattern where TAG is the tag
        //<a href="http://store.steampowered.com/tag/en/TAG/?snr=1_5_9__409" class="app_tag" style="display: none;">
        try {
            URL site=new URL("http://store.steampowered.com/app/"+appid);
            source=IOUtils.toString(site);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return tags;
        } catch (IOException ex) {
            Logger.getLogger(SteamUtils.class.getName()).log(Level.SEVERE, null, ex);
            return tags;
        }
        
        Scanner sc=new Scanner(source);
        while(sc.hasNextLine())
        {
            String line=sc.nextLine();
            //find line containing rgGames var
            if(line.contains("\"app_tag\""))
            {
                //find tag on line
                tags.add(line.substring(line.indexOf("en/")+3,line.indexOf("/?")));
            }
        }
        
        return tags;
    }
}
