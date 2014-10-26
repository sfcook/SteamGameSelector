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
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author sfcook
 */
public class SteamUtilsTest {
    private SteamUtils sUtils;
    @Before
    public void init()
    {
        sUtils=new SteamUtils();
    }
    
    @Test
    public void testTags()
    {
        assertFalse(sUtils.getTags().isEmpty());
    }
    @Test
    public void testGenres()
    {
        assertFalse(sUtils.getGenres().isEmpty());
    }
    @Test
    public void testCategories()
    {
        assertFalse(sUtils.getCategories().isEmpty());
    }
    
    @Test
    public void testGetAppIdsBySource()
    {
        String source="\t\t\tvar rgGames = [{\"appid\":4000,\"name\":\"Garry's Mod\"},{\"appid\":230410,\"name\":\"Warframe\"},{\"appid\":500,\"name\":\"Left 4 Dead\"},{\"appid\":550,\"name\":\"Left 4 Dead 2\"},{\"appid\":630,\"name\":\"Alien Swarm\"},{\"appid\":271290,\"name\":\"HAWKEN\"},{\"appid\":1250,\"name\":\"Killing Floor\"}];";
        
        Account account=sUtils.getAccountSource(source);
        Set test=new HashSet();
        
        test.add(4000);
        test.add(230410);
        test.add(500);
        test.add(550);
        test.add(630);
        test.add(271290);
        test.add(1250);
        
        assertTrue(account.games.equals(test));
    }
    
    @Test
    public void testGetNameBySource()
    {
        //Gabe's profile
        String source="		var profileLink = \"http://steamcommunity.com/id/gabelogannewell\";";
        
        Account account=sUtils.getAccountSource(source);
        
        assertTrue(account.name.equals("Rabscuttle"));
    }
    
    @Test
    public void testGetNameBySource2()
    {
        //Gabe's profile
        String source="		var profileLink = \"http://steamcommunity.com/id/gabelogannewell\";";
        
        Account account=sUtils.getAccount(source);
        
        assertTrue(account.name.equals("Rabscuttle"));
    }
    
    @Test
    public void testGetAccount()
    {
        //Garry Newman's steam profile
        Account garry=sUtils.getAccount("https://steamcommunity.com/id/garry");
        
        //4000 is the appid for gmod
        //Garry Newman wrote it so I assume it is on his steam profile
        assertTrue(garry.games.contains(4000));
    }
    
    @Test
    public void testGetGame()
    {
        //counter-strike appid is 10
        Game cs=sUtils.getGame(10);
        
        assertTrue(cs.appid==10);
        assertTrue(cs.title.equals("Counter-Strike"));
    }
    
    @Test
    public void testGetGameCategory()
    {
        //counter-strike appid is 10
        Game cs=sUtils.getGame(10);
        
        //found in category but not tags or genres
        assertTrue(cs.tags.contains("Valve Anti-Cheat"));
    }
    
    @Test
    public void testGetGameGenres()
    {
        //Use some early access title 
        Game cs=sUtils.getGame(324390);
        
        //early access is found in genres but not tags or categories
        assertTrue(cs.tags.contains("Early Access"));
    }
    
    @Test
    public void testGetGameTags()
    {
        //counter-strike appid is 10
        Game cs=sUtils.getGame(10);
        
        //FPS is a tag that should persist but not found in categories or genres 
        assertTrue(cs.tags.contains("FPS"));
    }
    
    @Test
    public void testBadGameID()
    {
        Game bad=sUtils.getGame(1);
        
        assertTrue(bad.tags.isEmpty());
    }
    @Test
    public void testBadUrl()
    {
        Account bad=sUtils.getAccount("https://www.google.com");
        
        assertTrue(bad.games.isEmpty());
    }
    @Test
    public void testBadUrl2()
    {
        Account bad=sUtils.getAccount("not a url");
        
        assertTrue(bad.games.isEmpty());
    }
    @Test
    public void testOddUrl()
    {
        Account odd=sUtils.getAccount("http://steamcommunity.com/id/garry/screenshots/");
        
        assertTrue(odd.games.contains(4000));
    }
}
