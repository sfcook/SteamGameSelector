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
import java.util.Collections;

/**
 *
 * @author sfcook
 */
public class SteamUtilsTest {
    private SteamUtils sUtils=SteamUtils.getInstance();
    
    @Test
    public void testTags()
    {
        assertFalse(sUtils.tags.isEmpty());
    }
    
    @Test
    public void testGetAppIdsBySource()
    {
        String source="\t\t\tvar rgGames = [{\"appid\":4000,\"name\":\"Garry's Mod\"},{\"appid\":230410,\"name\":\"Warframe\"},{\"appid\":500,\"name\":\"Left 4 Dead\"},{\"appid\":550,\"name\":\"Left 4 Dead 2\"},{\"appid\":630,\"name\":\"Alien Swarm\"},{\"appid\":271290,\"name\":\"HAWKEN\"},{\"appid\":1250,\"name\":\"Killing Floor\"}];";
        
        Account account=SteamUtils.getAccountSource(source);
        ArrayList test=new ArrayList();
        
        test.add(4000);
        test.add(230410);
        test.add(500);
        test.add(550);
        test.add(630);
        test.add(271290);
        test.add(1250);
        
        Collections.sort(test);
        Collections.sort(account.games);
        
        assertTrue(account.games.equals(test));
    }
    
    @Test
    public void testGetNameBySource()
    {
        String source="<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head>\n" +
"<title>Steam Community :: THE MAGIC NAME :: Games</title>";
        
        Account account=SteamUtils.getAccountSource(source);
        
        assertTrue(account.name.equals("THE MAGIC NAME"));
    }
    
    @Test
    public void testGetAccount()
    {
        //Garry Newman's steam profile
        Account garry=SteamUtils.getAccount("https://steamcommunity.com/id/garry");
        
        //4000 is the appid for gmod
        //Garry Newman wrote it so I assume it is on his steam profile
        assertTrue(garry.games.contains(4000));
    }
    
    @Test
    public void testGetGame()
    {
        //counter-strike appid is 10
        Game cs=SteamUtils.getGame(10);
        
        assertTrue(cs.title.equals("Counter-Strike"));
        
        assertTrue(cs.tags.contains("Action"));
        assertTrue(cs.tags.contains("FPS"));
        assertTrue(cs.tags.contains("Multiplayer"));
        
        //will require tags being added based on
        assertTrue(cs.tags.contains("VAC"));
    }
    
    @Test
    public void testGetGameFeatures()
    {
        //counter-strike appid is 10
        Game cs=SteamUtils.getGame(10);
        
        assertTrue(cs.title.equals("Counter-Strike"));
        
        //will require tags being added based on feature/category
        assertTrue(cs.tags.contains("VAC"));
    }
}
