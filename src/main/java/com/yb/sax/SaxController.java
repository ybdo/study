package com.yb.sax;

import com.yb.util.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * sax解析xml测试
 *
 * @author yb
 * @date 2019/6/20
 */
@RequestMapping("/sax")
@RestController
public class SaxController {
    @RequestMapping("/xml")
    public Message xml() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(new MyContentHandler());
        reader.parse(" classpath:ehcache.xml");
        return Message.ok();
    }
}
