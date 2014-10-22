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
package Testing.Package;

import steamgameselector.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 *
 * @author sfcook
 */
public class SteamGameSelectorTest {
    private SteamGameSelector selector;
    
    @Before
    public void init()
    {
        selector=new SteamGameSelector();
    }
    
    @Test
    public void testAddAccount()
    {
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        
        selector.addAccount(garry);
        
        assertFalse(selector.getAccounts().isEmpty());
    }
    
    @Test
    public void testRemoveAccount()
    {
        
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        
        selector.addAccount(garry);
        
        assertFalse(selector.getAccounts().isEmpty());
        selector.removeAccount(0);
        assertTrue(selector.getAccounts().isEmpty());
    }
    
    @Test
    public void testAddSteamGame()
    {
        SteamUtils sUtils=new SteamUtils();
        Game game=sUtils.getGame(10);
        
        selector.addGame(game);
        assertTrue(selector.getSteamGame(10).title.equals("Counter-Strike"));
    }
    
    @Test
    public void testNonSteamGame()
    {
        Game game=new Game();
        game.title="League of Legends";
        game.tags.add("MOBA");
        game.tags.add("Non-Steam");
        
        selector.addGame(game);
        assertTrue(selector.getSteamGame(-1)==null);
    }
    
    @Test
    public void testGetSharedGames()
    {
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        Account wiremod=SteamUtils.getAccount("http://steamcommunity.com/id/wireteam");
        
        selector.addAccount(garry); //gmod dev, owns gmod
        selector.addAccount(wiremod); //dev account for must have gmod mod, owns gmod
        
        selector.calcSharedGames();
        
        //4000 is the appid for gmod
        assertTrue(selector.getSharedGames().get(0)==4000);
    }
    
    @Test
    public void testGetRandomGame()
    {
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        Account wiremod=SteamUtils.getAccount("http://steamcommunity.com/id/wireteam");
        
        selector.addAccount(garry); //gmod dev, owns gmod
        selector.addAccount(wiremod); //dev account for must have gmod mod, owns gmod
        
        selector.calcSharedGames();
        
        //4000 is the appid for gmod
        assertTrue(selector.getRandomGame().appid==4000);
    }
    
    @Test
    public void testGetRandomGame2()
    {
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        
        selector.addAccount(garry);
        
        selector.calcSharedGames();
        
        ArrayList<Integer> tests=new ArrayList<Integer>();
        tests.add(selector.getRandomGame().appid);
        tests.add(selector.getRandomGame().appid);
        tests.add(selector.getRandomGame().appid);
        tests.add(selector.getRandomGame().appid);
        
        int test=selector.getRandomGame().appid;
        
        assertFalse(tests.contains(test));
    }
}
