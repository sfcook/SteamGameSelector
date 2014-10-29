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

import java.io.File;
import steamgameselector.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author sfcook
 */
public class SteamDataTest {
    private SteamUtils sUtils;
    private SteamData sdb;
    
    @Before
    public void init()
    {
        //delete steamdata.db
        File db=new File("steamdata.db");
        db.delete();
        
        sUtils=new SteamUtils();
        sdb=new SteamData(sUtils);
    }
    
    @Test
    public void testDB()
    {
        assertTrue(sdb!=null);
    }
    
    @Test
    public void testBadAccount()
    {
        Account account1=new Account();
        Account account2=new Account();
        Account account3=new Account();
        
        Game game=new Game();
        game.appid=0;
        game.title="Fake Game";
        game.tags.add("Fake");
        
        account1.name="Frank";
        account1.games.add(game);
        
        account2.name="Frank";
        account2.steamid="9000";
        
        account3.games.add(game);
        account3.steamid="9000";
        
        assertTrue(sdb.addAccount(account1)==-1);
        assertTrue(sdb.addAccount(account2)==-1);
        assertTrue(sdb.addAccount(account3)==-1);
    }
    
    @Test
    public void testAccount()
    {
        Account account=new Account();
        account.name="Frank";
        account.games.add(sdb.getGame(sdb.addGame(10))); //appid for CS
        account.steamid="1"; //techincally vaild but doesn't seem to be a real id
        
        assertTrue(sdb.addAccount(account)>0);
        
        Account result=sdb.getAccount("1");
        
        assertTrue(result!=null && result.name.equals(account.name));
    }
    
    @Test
    public void testAccount2()
    {
        assertTrue(sdb.addAccount("http://steamcommunity.com/id/wireteam")>0);
        
        Account result=sdb.getAccounts().get(0);
        
        assertFalse(result.steamid.isEmpty());
    }
    
    @Test
    public void testGetAccounts()
    {
        Account account1=new Account();
        Account account2=new Account();
        Account account3=new Account();
        
        account1.name="Frank1";
        account1.steamid="1";
        account1.games.add(sdb.getGame(sdb.addGame(10)));
        
        account2.name="Frank2";
        account2.steamid="2";
        account2.games.add(sdb.getGame(sdb.addGame(10)));
        
        account3.name="Frank3";
        account3.games.add(sdb.getGame(sdb.addGame(10)));
        account3.steamid="3";
        
        assertTrue(sdb.addAccount(account1)>0);
        assertTrue(sdb.addAccount(account2)>0);
        assertTrue(sdb.addAccount(account3)>0);
        
        ArrayList<Account> accounts=sdb.getAccounts();
        
        for(Account account:accounts)
        {
            account.accountid=-1;
        }
        
        assertTrue(accounts.contains(account1));
        assertTrue(accounts.contains(account2));
        assertTrue(accounts.contains(account3));
    }
    
    @Test
    public void testTag()
    {
        String tag="Next-gen tag all the kids are talking about";
        int tagid=sdb.addTag(tag);
        
        assertTrue(tagid>=0);
        assertTrue(tagid==sdb.getTagId(tag));
        
        String result=sdb.getTag(tagid);
        
        assertTrue(result!=null && result.equals(tag));
    }
    
    @Test
    public void testTagDup()
    {
        String tag="Next-gen tag all the kids are talking about";
        int tagid1=sdb.addTag(tag);
        int tagid2=sdb.addTag(tag);
        
        assertTrue(tagid1==tagid2);
    }
    
    @Test
    public void testGetTagsArray()
    {
        String tag1="one";
        String tag2="two";
        String tag3="three";
        
        sdb.addTag(tag1);
        sdb.addTag(tag2);
        sdb.addTag(tag3);
        
        ArrayList<String> tags=sdb.getTags();
        
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
        assertTrue(tags.contains(tag3));
    }
    
    @Test
    public void testGetTagsGameid()
    {
        Game game=new Game();
        game.tags.add("one");
        game.tags.add("two");
        game.tags.add("three");
        String title="Fake Game";
        game.title=title;
        
        int gameid=sdb.addGame(game);
        
        assertTrue(sdb.getGame(gameid).title.equals(title));
        
        ArrayList<String> tags=sdb.getTags(gameid);
        for(String tag:game.tags)
        {
            assertTrue(tags.contains(tag));
        }
    }
    
    @Test
    public void testGame()
    {
        Game game=new Game();
        String title="Fake Game";
        game.title=title;
        
        int gameid=sdb.addGame(game);
        
        assertTrue(sdb.getGame(gameid).title.equals(title));
    }
    
    @Test
    public void testGame2()
    {
        Game game=new Game();
        game.tags.add("one");
        game.tags.add("two");
        game.tags.add("three");
        String title="Fake Game";
        game.title=title;
        
        int gameid=sdb.addGame(game);
        
        Game result=sdb.getGame(gameid);
        assertTrue(result.title.equals(title));
        
        for(String tag:game.tags)
        {
            assertTrue(result.tags.contains(tag));
        }
    }
    
    @Test
    public void testAddSteamGame()
    {
        int gameid=sdb.addGame(10);
        Game cs1=sdb.getSteamGame(10);
        Game cs2=sdb.getGame(gameid);
        
        assertTrue(cs1.title.equals(cs2.title));
        assertTrue(cs1.title.equals("Counter-Strike"));
    }
    
    private void buildTestAccount()
    {
        Account account=new Account();
        account.steamid="0";
        account.name="Fake Account";
        
        ArrayList<Integer> games=new ArrayList();
        games.add(10); //counter-strike
        games.add(20); //team fortress classic
        games.add(70); //half-life
        games.add(220); //half-life 2
        games.add(240); //counter-strike: source
        games.add(400); //portal
        games.add(440); //teaam fortress 2
        games.add(550); //left 4 dead 2
        games.add(630); //alien swarm
        games.add(570); //dota 2
        games.add(730); //counter-strike: global offensive
        games.add(1250); //killing floor
        
        for(Integer appid:games)
        {
            sdb.addGame(appid);
            account.games.add(sdb.getSteamGame(appid));
        }
        
        sdb.addAccount(account);
    }
    @Test
    public void testAndFilter()
    {
        buildTestAccount();
        
        Set<Integer> tags=new HashSet();
        tags.add(sdb.getTagId("Steam Trading Cards"));
        tags.add(sdb.getTagId("Co-op"));
        ArrayList<Game> filtered=sdb.getFilteredGames(tags, null, null);
        
        assertTrue(filtered.size()==3);
        assertTrue(filtered.contains(sdb.getSteamGame(550)));
        assertTrue(filtered.contains(sdb.getSteamGame(570)));
        assertTrue(filtered.contains(sdb.getSteamGame(1250)));
    }
    @Test
    public void testOrFilter()
    {
        buildTestAccount();
        
        Set<Integer> tags=new HashSet();
        tags.add(sdb.getTagId("Free to Play"));
        tags.add(sdb.getTagId("Co-op"));
        ArrayList<Game> filtered=sdb.getFilteredGames(null, tags, null);
        
        assertTrue(filtered.size()==5);
        assertTrue(filtered.contains(sdb.getSteamGame(440)));
        assertTrue(filtered.contains(sdb.getSteamGame(550)));
        assertTrue(filtered.contains(sdb.getSteamGame(570)));
        assertTrue(filtered.contains(sdb.getSteamGame(630)));
        assertTrue(filtered.contains(sdb.getSteamGame(1250)));
    }
    @Test
    public void testNotFilter()
    {
        buildTestAccount();
        
        Set<Integer> tags=new HashSet();
        tags.add(sdb.getTagId("Free to Play"));
        ArrayList<Game> filtered=sdb.getFilteredGames(null, null, tags);
        
        assertTrue(filtered.size()==10);
        assertFalse(filtered.contains(sdb.getSteamGame(440)));
        assertFalse(filtered.contains(sdb.getSteamGame(570)));
    }
}
