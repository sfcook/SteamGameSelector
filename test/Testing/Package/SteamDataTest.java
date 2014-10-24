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
public class SteamDataTest {
    private SteamUtils sUtils;
    private SteamData sdb;
    
    @Before
    public void init()
    {
        sUtils=new SteamUtils();
        sdb=new SteamData();
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
        
        account1.name="Frank";
        account1.games.add(1);
        
        account2.name="Frank";
        account2.steamid=9000;
        
        account3.games.add(1);
        account3.steamid=9000;
        
        assertTrue(sdb.addAccount(account1)==1);
        assertTrue(sdb.addAccount(account2)==1);
        assertTrue(sdb.addAccount(account3)==1);
    }
    
    @Test
    public void testAccount()
    {
        Account account=new Account();
        account.name="Frank";
        account.games.add(10); //appid for CS
        account.steamid=1; //techincally vaild but doesn't seem to be a real id
        
        assertTrue(sdb.addAccount(account)==0);
        
        Account result=sdb.getAccount(1);
        
        assertTrue(result.name.equals(account.name));
    }
    
    //TODO: test for duplicate account attempts
    
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
    
    //TODO: test for duplicate tag attempts
    
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
}
