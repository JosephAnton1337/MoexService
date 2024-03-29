package com.getmoex.moexservice.parser;


import com.getmoex.moexservice.dto.BondDto;
import com.getmoex.moexservice.exception.BondParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.*;

@Slf4j
@Component
public class MoexBondXmlParser implements Parser {
    @Override
    public List<BondDto> parse(String ratesAsString) {
        ArrayList<BondDto> bonds = new ArrayList<>();

        var dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            var db = dbf.newDocumentBuilder();

            try (var reader = new StringReader(ratesAsString)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("row");

                int bound = list.getLength();
                for (int i = 0; i < bound; i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String ticker = element.getAttribute("SECID");
                        String price = element.getAttribute("PREVADMITTEDQUOTE");
                        String name = element.getAttribute("SHORTNAME");
                        if (!ticker.isEmpty() && !price.isEmpty() && !name.isEmpty()) {
                            bonds.add(new BondDto(ticker, name, Double.parseDouble(price)));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("xml parsing error, xml:{}", ratesAsString, ex);
            throw new BondParsingException(ex);
        }
        return bonds;
    }
}
