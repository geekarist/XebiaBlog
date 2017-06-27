package fr.xebia.cpele.xebiablog;

import org.simpleframework.xml.Element;

@SuppressWarnings("unused")
@Element(name = "item")
class Item {

    @Element(name = "title")
    String title;
    @Element(name = "link")
    String link;

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
