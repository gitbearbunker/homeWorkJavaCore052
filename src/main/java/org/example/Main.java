package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String pathToXML = "data.xml";
        List<Employee> list = parseXML(pathToXML);
        writeString(listToJson(list));

    }
    public static String listToJson(List<Employee> list){
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();

        String json=gson.toJson(list,listType);
        return json;
    }
    public static void writeString(String json) {
        String path = "data2.json";
        try (FileWriter fw = new FileWriter(new File(path))) {
            fw.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<Employee> parseXML(String fileNamed) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileNamed));
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        long id = 0;
        String firstName = null;
        String lastName = null;
        String country = null;
        int age = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                element.getNodeName();
                NamedNodeMap map = element.getAttributes();
                NodeList nl = element.getChildNodes();
                for (int j = 0; j < nl.getLength(); j++) {
                    Node node1 = nl.item(j);
                    if (node1.getTextContent()!=null) ;
                    String s = node1.getNodeName();
                    switch (s){
                        case "id"-> id= Integer.parseInt(node1.getTextContent());
                        case "firstName" -> firstName= node1.getTextContent();
                        case "lastName" -> lastName= node1.getTextContent();
                        case "country" -> country= node1.getTextContent();
                        case "age"-> age= Integer.parseInt(node1.getTextContent());
                    }
                }
                list.add(new Employee(id, firstName, lastName, country, age));
            }
        }
        return list;
    }
}
