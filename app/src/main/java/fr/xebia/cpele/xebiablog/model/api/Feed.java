package fr.xebia.cpele.xebiablog.model.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("unused")
@Root(name = "rss")
public class Feed {

    @Element(name = "channel")
    public
    Channel channel;

    @Override
    public String toString() {
        return "Feed{" +
                "channel=" + channel +
                '}';
    }
}
