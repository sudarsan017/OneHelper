package dev.sudarsan.onehelper.modification.runtime;

import dev.sudarsan.onehelper.config.model.Configuration;
import dev.sudarsan.onehelper.config.resolution.input.ResolutionInput;
import dev.sudarsan.onehelper.context.ProjectContext;
import dev.sudarsan.onehelper.exception.ModificationException;
import dev.sudarsan.onehelper.config.resolution.pipeline.ConfigResolutionPipeline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IntellijIdeConfigModification extends IdeConfigModification {
    private final ConfigResolutionPipeline configResolutionPipeline;
    private final List<Configuration> configurations;
    private final DocumentBuilder documentBuilder;

    public IntellijIdeConfigModification(String filePath, ResolutionInput resolutionInput, List<Configuration> configurations) throws ModificationException {
        super(filePath);
        this.configResolutionPipeline = new ConfigResolutionPipeline(resolutionInput);
        this.configurations = configurations;
        this.documentBuilder = createDocumentBuilder();
    }

    private DocumentBuilder createDocumentBuilder() throws ModificationException {
        try {
            return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ModificationException("Error creating XML doc builder: " + e.getMessage());
        }
    }

    @Override
    public void apply(ProjectContext context) throws ModificationException {
        List<Element> elements = new ArrayList<>();

        try {
            addElements(context, elements);

            Path targetFile = context.resolveProjectFile(filePath);

            Document targetDoc = getTargetDoc(targetFile);
            Element runManagerComponent = findOrCreateRunManager(targetDoc);

            for (Element element : elements) {
                Element importedConfig = (Element) targetDoc.importNode(element, true);
                runManagerComponent.appendChild(importedConfig);
            }

            writeXmlRootToFile(targetDoc, targetFile);
        } catch (IOException e) {
            throw new ModificationException("Error reading the xml file: " + e.getMessage());
        } catch (SAXException e) {
            throw new ModificationException("Error parsing the xml file: " + e.getMessage());
        } catch (TransformerException e) {
            throw new ModificationException("Error writing the xml file: " + e.getMessage());
        } catch (Exception e) {
            throw new ModificationException("Unexpected error: " + e.getMessage());
        }
    }

    private void writeXmlRootToFile(Document targetDoc, Path targetFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(targetDoc);
        StreamResult result = new StreamResult(targetFile.toFile());
        transformer.transform(source, result);
    }

    private Element findOrCreateRunManager(Document targetDoc) {
        Element root = targetDoc.getDocumentElement();
        NodeList components = root.getElementsByTagName("component");

        String rootNode = "RunManager";
        for (int i = 0; i < components.getLength(); i++) {
            Element component = (Element) components.item(i);
            if (rootNode.equals(component.getAttribute("name"))) {
                return component;
            }
        }

        // Create RunManager component if not found
        Element runManagerComponent = targetDoc.createElement("component");
        runManagerComponent.setAttribute("name", rootNode);
        root.appendChild(runManagerComponent);

        return runManagerComponent;
    }

    private Document getTargetDoc(Path targetFile) {
        Document targetDoc;
        if (!targetFile.toFile().exists()) {
            targetDoc = documentBuilder.newDocument();
            Element project = targetDoc.createElement("project");
            project.setAttribute("version", "4");
            targetDoc.appendChild(project);
        } else {
            targetDoc = documentBuilder.newDocument();
        }
        return targetDoc;
    }

    private void addElements(ProjectContext context, List<Element> elements) throws ModificationException, IOException, SAXException {
        for (Configuration configuration : configurations) {
            String configContent = getStringFromConfiguration(context, configuration);
            String resolvedConfig = configResolutionPipeline.resolve(configContent);

            Document configDoc = documentBuilder.parse(new InputSource(new StringReader(resolvedConfig)));
            NodeList nodeList = configDoc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.COMMENT_NODE) {
                    elements.add((Element) node);
                }
            }
        }
    }

    private String getStringFromConfiguration(ProjectContext context, Configuration configuration) throws ModificationException {
        try {
            Path configFile = context.resolveResourcesFile(configuration.getTemplatePath());
            return Files.readString(configFile);
        } catch (IOException e) {
            throw new ModificationException("Error reading the configuration template file: " + e.getMessage());
        }
    }
}
