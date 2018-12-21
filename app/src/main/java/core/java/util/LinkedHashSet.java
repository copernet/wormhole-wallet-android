package core.java.util;

import java.util.LinkedHashMap;

public class LinkedHashSet
    extends AbstractSet
{
    public LinkedHashMap m_HashMap = null;

    public LinkedHashSet()
    {
        m_HashMap = new LinkedHashMap();
    }

    public LinkedHashSet(Collection c)
    {
        m_HashMap = new LinkedHashMap(Math.max(11, c.size() * 2));
        addAll(c);
    }

    public LinkedHashSet(int initialCapacity)
    {
        m_HashMap = new LinkedHashMap(initialCapacity);

    }

    public Iterator iterator()
    {
//        return (m_HashMap.keySet()).iterator();
        return null;
    }

    public int size()
    {
        return m_HashMap.size();
    }

    public boolean contains(Object o)
    {
        return m_HashMap.containsKey(o);
    }

    public boolean add(Object o)
    {
        if (!m_HashMap.containsValue(o))
        {
            m_HashMap.put((Object)o, (Object)o);

            return true;

        }

        return false;
    }

    public boolean remove(Object o)
    {
        return (m_HashMap.remove(o) != null);
    }

    public void clear()
    {
        m_HashMap.clear();
    }


    public Object clone()
    {
        LinkedHashSet hs = new LinkedHashSet();
        hs.m_HashMap = (LinkedHashMap) m_HashMap.clone();
        return hs;
    }

}
