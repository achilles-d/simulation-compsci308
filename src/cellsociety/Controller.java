package cellsociety;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {

    public Controller(){
        ReadXml();
    }

    public void ReadXml(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("/Users/Cemal/Desktop/2019_Spring_Classes/2020 Spring/CS308/simulation_team06/resources/ExampleXML.xml");
            Node simulation = doc.getElementById("GameOfLife");
            System.out.println(simulation);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Note from the class: Find the XMLParser.java document and check
        private DocumentBuilder getDocumentBuilder(){
            try{
                return DocumentBuilderFactory.newInstance().newDocumentBuilder();
            }
            catch (ParserConfigurationException e) {

            }



         */

    }
}
