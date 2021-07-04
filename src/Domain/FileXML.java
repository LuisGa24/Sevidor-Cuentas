/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author LuisGa && Sebas
 */
public class FileXML {

    public void createXML(String objectName, String fileName) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(objectName);
            doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(/*address + "\\" + */fileName + ".xml"));
            transformer.transform(source, result);

           

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) { //Se encarga de eliminar un archivo
        try {
            Files.delete(Paths.get(fileName + ".xml"));
        } catch (IOException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeXML(String FileName, String elementType, String[] dataName, String[] data) throws TransformerException, org.xml.sax.SAXException, IOException {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FileName));
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            Element ele = doc.createElement(elementType);
            rootElement.appendChild(ele);

            Attr attr = doc.createAttribute(dataName[0]);
            attr.setValue(data[0]);
            ele.setAttributeNode(attr);

            for (int i = 1; i < data.length; i++) {

                Element dato = doc.createElement(dataName[i]);

                dato.appendChild(doc.createTextNode(data[i]));

                ele.appendChild(dato);
            }
            //escribirmos el contenido en un archivo xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(FileName));
            transformer.transform(source, result);

           

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public Boolean exist(String file) {
        File archive = new File(file);

        return archive.exists();
    }

    
    public LinkedList<Client> readXMLtoClientList(String elementType) {

        LinkedList<Client> cList = new LinkedList<>();

        try {
            File inputFile = new File("Client.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Client c= new Client("", "", "", "");
            NodeList nList = doc.getElementsByTagName(elementType);

            for (int indice = 0; indice < nList.getLength(); indice++) {
                
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    c = new Client(
                            eElement.getAttribute("clientId"),
                            eElement.getElementsByTagName("clientName").item(0).getTextContent(),
                            eElement.getElementsByTagName("phoneNumber").item(0).getTextContent(),
                            eElement.getElementsByTagName("address").item(0).getTextContent()
                    
                    );
                    
                     }
                cList.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cList;
    }

    LinkedList<Account> readXMLtoSAccountList(String elementType) {
        LinkedList<Account> aList = new LinkedList<>();

        try {
            File inputFile = new File("SavingsAccount.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            SavingsAccount sa= null;
            NodeList nList = doc.getElementsByTagName(elementType);

            for (int indice = 0; indice < nList.getLength(); indice++) {
                
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = format.parse(eElement.getElementsByTagName("openingDate").item(0).getTextContent());
                    
                    sa = new SavingsAccount(
                            eElement.getElementsByTagName("currency").item(0).getTextContent().charAt(0),
                            date,
                            Float.parseFloat(eElement.getElementsByTagName("balance").item(0).getTextContent()),
                            eElement.getElementsByTagName("clientID").item(0).getTextContent(),
                            Float.parseFloat(eElement.getElementsByTagName("interestRate").item(0).getTextContent())
                    
                    );
                    sa.setState(eElement.getElementsByTagName("state").item(0).getTextContent().charAt(0));
                    sa.setId(Integer.parseInt(eElement.getAttribute("id")));
                    }
                aList.add(sa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aList;
    }

    LinkedList<Account> readXMLtoTAccountList(String elementType) {
        LinkedList<Account> aList = new LinkedList<>();

        try {
            File inputFile = new File("TermAccount.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            TermAccount ta= null;
            NodeList nList = doc.getElementsByTagName(elementType);

            for (int indice = 0; indice < nList.getLength(); indice++) {
                
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = format.parse(eElement.getElementsByTagName("openingDate").item(0).getTextContent());
                    
                    ta = new TermAccount(
                            eElement.getElementsByTagName("currency").item(0).getTextContent().charAt(0),
                            date,
                            eElement.getElementsByTagName("clientID").item(0).getTextContent(),
                            Float.parseFloat(eElement.getElementsByTagName("startingAmount").item(0).getTextContent()),
                            Float.parseFloat(eElement.getElementsByTagName("interestRate").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("term").item(0).getTextContent())
                    );
                    ta.setId(Integer.parseInt(eElement.getAttribute("id")));
                    }
                aList.add(ta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aList;
    }

}