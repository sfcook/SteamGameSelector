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

import java.util.Objects;

/**
 *
 * @author sfcook
 */
public class Tag {
    public String tag;
    public boolean and;
    public boolean or;
    public boolean not;
    
    public Tag()
    {
        init(null,false,false,false);
    }
    public Tag(String tag)
    {
        init(tag,false,false,false);
    }
    public Tag(String tag, boolean and, boolean or, boolean not)
    {
        init(tag,and,or,not);
    }
    
    private void init(String tag, boolean and, boolean or, boolean not)
    {
        this.tag=tag;
        this.and=and;
        this.or=or;
        this.not=not;
    }
    
    
    //booleans are not involved in compares
    @Override
    public boolean equals(Object other){
        if(other==null)
            return false;
        else if(other instanceof Tag && tag.equals(((Tag)other).tag))
            return true;
        else
            return other instanceof String && tag.equals((String)other);
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }
}
