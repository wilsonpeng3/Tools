package misc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XMLHelper {
    public static Document parse(String fromStr) throws DocumentException {
        return DocumentHelper.parseText(fromStr);
    }

    public static Document parse(File xmlFile) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);
        return document;
    }
}
