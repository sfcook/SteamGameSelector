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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import java.util.List;

/**
 *
 * @author sfcook
 */
public class SteamData {
    private SQLiteDataSource dataSource;
    private QueryRunner queryRunner;
    
    public SteamData()
    {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dataSource=new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:steamdata.db");
        
        queryRunner=new QueryRunner(dataSource);
        
        try {
            createTables();
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createTables() throws SQLException
    {
        //WARNING:
        //DbUtils update() seems to suggest using INSERT, UPDATE, or DELETE only
        //examples tend to use tables created through scripts not in code
        //seems to work just fine but might break if DbUtils changes
        //also this was mostly tested using the cmd/shell utility
        
        //appid is the same as the ones used by valve
        queryRunner.update("CREATE TABLE IF NOT EXISTS Game(gameid INTEGER PRIMARY KEY AUTOINCREMENT, appid INTEGER, title TEXT)");
        //no idea what valve tag ids are or if they even have one
        queryRunner.update("CREATE TABLE IF NOT EXISTS Tag(tagid INTEGER PRIMARY KEY AUTOINCREMENT, tag TEXT)");
        queryRunner.update("CREATE TABLE IF NOT EXISTS GameTag(gametagid INTEGER  PRIMARY KEY, gameid INTEGER , tagid INTEGER , FOREIGN KEY(gameid) REFERENCES Game(gameid), FOREIGN KEY(tagid) REFERENCES Tag(tagid))");
        
        //steamid is the same as the ones used by valve, might break if valve stops using 64-bit signed int
        queryRunner.update("CREATE TABLE IF NOT EXISTS Account(steamid INTEGER PRIMARY KEY, name TEXT)");
        queryRunner.update("CREATE TABLE IF NOT EXISTS Account(accountgameid INTEGER PRIMARY KEY, steamid INTEGER , gameid INTEGER , FOREIGN KEY(steamid) REFERENCES Account(steamid), FOREIGN KEY(gameid) REFERENCES Game(gameid))");
    }
    
    public ArrayList<String> getTags()
    {
        ArrayList<String> tags=new ArrayList<String>();
        try {
            List<Object[]> objs=queryRunner.query("SELECT tag FROM Tag",new ArrayListHandler());
            if(objs.size()>0)
            {
                for(Object[] item:objs)
                {
                    if(item.length>0)
                        tags.add((String)item[0]);
                }
            }
            return tags;
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tags;
    }
    
    public ArrayList<String> getTags(int gameid)
    {
        ArrayList<String> tags=new ArrayList<>();
        
        try {
            List<Object[]> objs=queryRunner.query("SELECT tag FROM Tag WHERE gameid=?",new ArrayListHandler(),gameid);
            if(objs.size()>0)
            {
                for(Object[] item:objs)
                {
                    if(item.length>0)
                        tags.add((String)item[0]);
                }
            }
            return tags;
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tags;
    }
    
    public int getTagId(String tag)
    {
        try {
            Object[] objs=queryRunner.query("SELECT tagid FROM Tag WHERE tag LIKE ?",new ArrayHandler(),tag);
            if(objs.length==0)
                return -1;
            else
                return (Integer)objs[0];
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int addTag(String tag)
    {
        int exist=getTagId(tag);
        if(exist!=-1)
            return exist;
        try {
            if(queryRunner.update("INSERT INTO Tag (tag) Values (?)",tag)==1)
                return getTagId(tag);
            else
                return -1;
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public String getTag(int tagid)
    {
        try {
            Object[] objs=queryRunner.query("SELECT tag FROM Tag WHERE tagid=?",new ArrayHandler(),tagid);
            if(objs.length==0)
                return null;
            else
                return (String)objs[0];
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Account> getAccounts()
    {
        ArrayList<Account> accounts=new ArrayList<Account>();
        
        return accounts;
    }
    
    public int addAccount(Account account)
    {
        if(account.name.isEmpty() || account.games.isEmpty() || account.steamid==0)
            return 1;
        else
        {
            //queryRunner.update("INSERT INTO account")
            return 0;
        }
    }
    
    public int addAccount(String url)
    {
        return -1;
    }
    
    public Account getAccount(long steamid)
    {
        Account account=new Account();
        
        return account;
    }
    
    public int removeAccount(long steamid)
    {
        return -1;
    }
    
    public int addGame(int appid)
    {
        return -1;
    }
    
    public int addGame(Game game)
    {
        if(game.appid>0)
            return addGame(game.appid);
        try {
            Object[] objs=queryRunner.insert("INSERT INTO Game (appid,title) Values (?,?)",new ArrayHandler(),game.appid,game.title);
            
            if(objs.length>0)
            {
                int gameid=(Integer)objs[0];
                for(String tag:game.tags)
                {
                    addGameTag(gameid,tag);
                }
                
                return 0;
            }
            else
                return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public void addGameTag(int gameid, String tag)
    {
        
    }
    
    public Game getGame(int gameid)
    {
        try {
            Object[] objs=queryRunner.query("SELECT * FROM Game WHERE gameid=?",new ArrayHandler(),gameid);
            
            return processGame(objs);
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Game getSteamGame(int appid)
    {
        try {
            Object[] objs=queryRunner.query("SELECT * FROM Game WHERE appid=?",new ArrayHandler(),appid);
            
            return processGame(objs);
        } catch (SQLException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private Game processGame(Object[] objs)
    {
        Game game=new Game();
        
        if(objs.length>0)
        {
            game.gameid=(Integer)objs[0];
            game.appid=(Integer)objs[1];
            game.title=(String)objs[2];

            //TODO: tags

            return game;
        }
        else
            return null;
    }
}
