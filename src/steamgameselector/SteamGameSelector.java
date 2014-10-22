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
import java.util.ArrayList;

/**
 *
 * @author sfcook
 */
public class SteamGameSelector {
    private Map steamGames;
    private ArrayList sharedGames;
    private ArrayList accounts;
    
    public SteamGameSelector()
    {
        steamGames=new HashMap();
        sharedGames=new ArrayList();
        accounts=new ArrayList();
    }
    
    public void addAccount(Account account)
    {
        accounts.add(account);
    }
    
    public void removeAccount(int index)
    {
        accounts.remove(index);
    }
    
    public ArrayList getAccounts()
    {
        return accounts;
    }
    
    //should check if steam game or not
    public void addGame(Game game)
    {
        steamGames.put(game.appid, game);
    }
    
    public Game getSteamGame(int appid)
    {
        return (Game)steamGames.get(appid);
    }
    
    public ArrayList getSharedGames()
    {
        return sharedGames;
    }
    
    public Game getRandomGame()
    {
        return new Game();
    }
}
