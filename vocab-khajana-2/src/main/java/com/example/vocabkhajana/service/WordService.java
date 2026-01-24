package com.example.vocabkhajana.service;

import com.example.vocabkhajana.model.WordEntry;
import com.example.vocabkhajana.model.WordPage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.xml.sax.SAXException;

@Service
public class WordService {

    private final ResourceLoader resourceLoader;
    private final String dataLocation;
    private final List<WordEntry> wordEntries = new CopyOnWriteArrayList<>();
    private final int defaultPageSize;

    public WordService(
            ResourceLoader resourceLoader,
            @Value("${vocab.data-location:classpath:khajana.xml}") String dataLocation,
            @Value("${vocab.page-size:20}") int defaultPageSize) {
        this.resourceLoader = resourceLoader;
        this.dataLocation = dataLocation;
        this.defaultPageSize = defaultPageSize > 0 ? defaultPageSize : 20;
    }

    @PostConstruct
    public void loadData() throws IOException {
        Resource resource = resourceLoader.getResource(dataLocation);
        if (!resource.exists()) {
            throw new IOException("Unable to locate vocabulary file at " + dataLocation);
        }

        List<WordEntry> loadedEntries = parseXml(resource);
        wordEntries.clear();
        wordEntries.addAll(loadedEntries);
    }

    public WordPage getPage(int requestedPage) {
        return getPage(requestedPage, defaultPageSize);
    }

    public WordPage getPage(int requestedPage, int requestedSize) {
        int safePageSize = requestedSize > 0 ? requestedSize : defaultPageSize;
        if (safePageSize <= 0) {
            safePageSize = 20;
        }

        int safePageNumber = Math.max(requestedPage, 0);
        int totalElements = wordEntries.size();

        if (totalElements == 0) {
            return new WordPage(Collections.emptyList(), 0, safePageSize, 0);
        }

        int totalPages = (int) Math.ceil((double) totalElements / safePageSize);
        if (safePageNumber >= totalPages) {
            safePageNumber = Math.max(totalPages - 1, 0);
        }

        int fromIndex = safePageNumber * safePageSize;
        int toIndex = Math.min(fromIndex + safePageSize, totalElements);

        List<WordEntry> content = wordEntries.subList(fromIndex, toIndex);
        return new WordPage(content, safePageNumber, safePageSize, totalElements);
    }

    public List<WordEntry> getAll() {
        return Collections.unmodifiableList(wordEntries);
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    private List<WordEntry> parseXml(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setNamespaceAware(false);
            factory.setExpandEntityReferences(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList wordNodes = document.getElementsByTagName("myword");
            List<WordEntry> entries = new ArrayList<>(wordNodes.getLength());

            IntStream.range(0, wordNodes.getLength())
                    .mapToObj(wordNodes::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(Element.class::cast)
                    .map(this::toWordEntry)
                    .forEach(entries::add);

            return entries;
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Failed to parse vocabulary XML", e);
        }
    }

    private WordEntry toWordEntry(Element myWordElement) {
        Element wordElement = getFirstChildElement(myWordElement, "word");
        String wordValue = wordElement != null ? getTextContent(wordElement) : "";
        String wordType = wordElement != null ? wordElement.getAttribute("type") : "";

        List<String> meanings = extractValues(myWordElement, "meanings", "meaning");
        List<String> examples = extractValues(myWordElement, "examples", "example");

        return new WordEntry(wordValue, wordType, meanings, examples);
    }

    private List<String> extractValues(Element parent, String containerName, String itemName) {
        Element container = getFirstChildElement(parent, containerName);
        if (container == null) {
            return List.of();
        }
        NodeList items = container.getElementsByTagName(itemName);
        List<String> values = new ArrayList<>(items.getLength());
        for (int i = 0; i < items.getLength(); i++) {
            Node node = items.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                values.add(getTextContent((Element) node));
            }
        }
        return values;
    }

    private Element getFirstChildElement(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getParentNode() == parent && node.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) node;
            }
        }
        return null;
    }

    private String getTextContent(Element element) {
        return element.getTextContent() == null ? "" : element.getTextContent().trim();
    }
}

