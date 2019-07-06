package com.yb.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author yb
 * @date 2019/6/20
 * sax解析xml的事件处理器
 */
public class MyContentHandler extends DefaultHandler {
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("开始解析");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        System.out.println("开始标签---"+qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.println("标签属性--"+attributes.getQName(i)+":"+attributes.getValue(i));
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        System.out.println("结束标签---"+qName);
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        System.out.println("标签内容：" + new String(ch, start, length));
    }
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("结束解析");
    }
}
