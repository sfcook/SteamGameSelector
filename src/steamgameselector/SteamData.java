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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteDataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 *
 * @author sfcook
 */
public class SteamData {
    private SQLiteDataSource datasource;
    
    public SteamData()
    {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SteamData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        datasource=new SQLiteDataSource();
        datasource.setUrl("jdbc:sqlite:steamdata.db");
    }
    
    public ArrayList<String> getTags()
    {
        ArrayList<String> tags=new ArrayList<String>();
        
        return tags;
    }
    
    public ArrayList<String> getTags(int appid)
    {
        ArrayList<String> tags=new ArrayList<String>();
        
        return tags;
    }
    
    public ArrayList<Account> getAccounts()
    {
        ArrayList<Account> accounts=new ArrayList<Account>();
        
        return accounts;
    }
    
    public int addAccount(Account account)
    {
        return -1;
    }
    
    public int addAccount(String url)
    {
        return -1;
    }
    
    public int removeAccount(Account account)
    {
        return -1;
    }
    
    public int addGame(int appid)
    {
        return -1;
    }
    
    public int addGame(Game game)
    {
        return -1;
    }
    
    public Game getSteamGame(int appid)
    {
        Game game=new Game();
        
        return game;
    }
}
