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
import java.util.Objects;

/**
 *
 * @author sfcook
 */
public class Account {
    public int accountid;
    public String steamid;
    public String name;
    public Set<Game> games;
    
    public Account()
    {
        accountid=-1;
        steamid="";
        name="";
        games=new HashSet();
    }
    
    @Override
    public boolean equals(Object other){
        return other!=null && other instanceof Account &&
                accountid==((Account)other).accountid &&
                steamid.equals(((Account)other).steamid) &&
                name.equals(((Account)other).name) &&
                games.equals(((Account)other).games);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.accountid;
        hash = 67 * hash + Objects.hashCode(this.steamid);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.games);
        return hash;
    }
}
