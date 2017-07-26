package fr.xebia.cpele.xebiablog.model.api;

import org.simpleframework.xml.ElementList;

import java.util.List;

@SuppressWarnings("unused")
public class Channel {

    @ElementList(inline = true)
    public
    List<Item> items;

    @Override
    public String toString() {
        return "Channel{" +
                "items=" + items +
                '}';
    }
}
