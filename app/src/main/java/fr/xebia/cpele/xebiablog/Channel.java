package fr.xebia.cpele.xebiablog;

import org.simpleframework.xml.ElementList;

import java.util.List;

@SuppressWarnings("unused")
class Channel {

    @ElementList(inline = true)
    List<Item> items;

    @Override
    public String toString() {
        return "Channel{" +
                "items=" + items +
                '}';
    }
}
