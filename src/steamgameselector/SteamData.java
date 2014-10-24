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
        queryRunner.update("CREATE TABLE IF NOT EXISTS Game(appid INT PRIMARY KEY, title TEXT)");
        //no idea what valve tag ids are or if they even have one
        queryRunner.update("CREATE TABLE IF NOT EXISTS Tag(tagid INT PRIMARY KEY, tag TEXT)");
        queryRunner.update("CREATE TABLE IF NOT EXISTS GameTag(gametagid INT PRIMARY KEY, appid INT, tagid INT, FOREIGN KEY(appid) REFERENCES Game(appid), FOREIGN KEY(tagid) REFERENCES Tag(tagid))");
        
        //steamid is the same as the ones used by valve, might break if valve stops using 64-bit signed int
        queryRunner.update("CREATE TABLE IF NOT EXISTS Account(steamid INT PRIMARY KEY, name TEXT)");
        queryRunner.update("CREATE TABLE IF NOT EXISTS Account(accountgameid INT PRIMARY KEY, steamid INT, appid INT, FOREIGN KEY(steamid) REFERENCES Account(steamid), FOREIGN KEY(appid) REFERENCES Game(appid))");
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
