package fr.xebia.cpele.xebiablog;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "rss")
class Feed {

    @Element(name = "channel")
    Channel channel;

    @Override
    public String toString() {
        return "Feed{" +
                "channel=" + channel +
                '}';
    }
}
