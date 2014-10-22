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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sfcook
 */
public class SteamGameSelector {
    private SteamUtils sUtils;
    private Map<Integer, Game> steamGames;
    private ArrayList<Integer> sharedGames;
    private ArrayList<Account> accounts;
    
    public SteamGameSelector(SteamUtils instance)
    {
        sUtils=instance;
        steamGames=new HashMap();
        sharedGames=new ArrayList<Integer>();
        accounts=new ArrayList<Account>();
    }
    
    public void addAccount(Account account)
    {
        accounts.add(account);
    }
    
    public void removeAccount(int index)
    {
        accounts.remove(index);
    }
    
    public ArrayList<Account> getAccounts()
    {
        return accounts;
    }
    
    //TODO: needs speed improvements for large number of games
    public void loadGames()
    {
        for(int appid:sharedGames)
        {
            if(!steamGames.containsKey(appid))
                addGame(sUtils.getGame(appid));
        }
    }
    
    //should check if steam game or not
    public void addGame(Game game)
    {
        if(game.appid>=0)
            steamGames.put(game.appid, game);
    }
    
    public Game getSteamGame(int appid)
    {
        return (Game)steamGames.get(appid);
    }
    
    public ArrayList<Integer> getSharedGames()
    {
        return sharedGames;
    }
    
    public void calcSharedGames()
    {
        if(accounts.size()==0)
            return;
        
        Set<Integer> shared=new HashSet();
        shared.addAll(accounts.get(0).games);
        
        if(accounts.size()>1)
        {
            for(int pos=1;pos<accounts.size();pos++)
            {
                shared.retainAll(accounts.get(pos).games);
            }
        }
        sharedGames.clear();
        for(Integer item : shared)
        {
            sharedGames.add(item);
        }
    }
    
    public int getRandomGameIndex()
    {
        Random rand=new Random();
        return rand.nextInt(sharedGames.size());
    }
    public Game getRandomGame()
    {
        int selected=getRandomGameIndex();
        return steamGames.get(sharedGames.get(selected));
    }
}
