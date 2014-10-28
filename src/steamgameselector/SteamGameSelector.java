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

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sfcook
 */
public class SteamGameSelector {
    private SteamData sdb;
    private ArrayList<Game> sharedGames;
    
    public SteamGameSelector(SteamData instance)
    {
        sdb=instance;
        sharedGames=new ArrayList();
    }
    
    public void addAccount(Account account)
    {
        sdb.addAccount(account);
    }
    
    public int addAccount(String url)
    {
        return sdb.addAccount(url);
    }
    
    public ArrayList<Account> getAccounts()
    {
        return sdb.getAccounts();
    }
    
    public void removeAccount(int accountid)
    {
        sdb.removeAccount(accountid);
    }
    
    //should check if steam game or not
    public void addGame(Game game)
    {
        sdb.addGame(game);
    }
    
    public Game getSteamGame(int appid)
    {
        return sdb.getSteamGame(appid);
    }
    
    public void reloadSteamGame(int appid)
    {
        sdb.reloadSteamGame(appid);
    }
    
    private void updateSharedGames()
    {
        sharedGames=sdb.getSharedGames();
    }
    public ArrayList<Game> getSharedGames()
    {
        updateSharedGames();
        return sharedGames;
    }
    
    public int getRandomGameIndex()
    {
        Random rand=new Random();
        return rand.nextInt(sharedGames.size());
    }
    public Game getRandomGame()
    {
        int selected=getRandomGameIndex();
        return sharedGames.get(selected);
    }
}
