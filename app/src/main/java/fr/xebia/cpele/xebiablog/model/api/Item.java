package fr.xebia.cpele.xebiablog.model.api;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
@Element(name = "item")
public class Item {

    @Element(name = "title")
    public
    String title;
    @Element(name = "link")
    public
    String link;

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
