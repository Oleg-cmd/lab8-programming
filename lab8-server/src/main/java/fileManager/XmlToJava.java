package fileManager;

import collectionWorker.Command;

import model.*;
import modules.CommandOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;

/**
 * The XmlToJava class provides a method to parse an XML file and convert its
 * contents to a HashSet of Movie objects.
 */
public class XmlToJava implements Command {
    private static CollectionManager collectionManager;

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * A HashSet of Movie objects to store parsed XML data.
     */
    public static HashSet<Movie> movies = new HashSet<>();
    private static final Logger logger = LogManager.getLogger(XmlToJava.class);

    /**
     * Parses an XML file and returns a HashSet of Movie objects.
     *
     * @param filename the name of the XML file to be parsed.
     * @param output   the output to control out for client
     * @param XMLData  the String format XML document that sent from client
     * @return a HashSet of Movie objects containing data from the specified XML
     *         file.
     */
    public static HashSet<Movie> parseXml(String filename, CommandOutput output, String XMLData) {
        try {
            Document doc;
            if (new File(filename).exists()) {
                logger.info("use path");
                // Open the XML file and create a reader
                File xmlFile = new File(filename);
                Reader fileReader = new FileReader(xmlFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                // Parse the XML file using a DocumentBuilder
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(xmlFile);

                // Close the reader
                bufferedReader.close();
            } else {
                logger.info("use xml data instead of path");

                // Get the current number of XML files in the directory
                File dir = new File("./xml");
                int fileCount = Objects.requireNonNull(dir.listFiles()).length;

                // Create a new file with an adaptive name
                String fileName = "./xml/" + (fileCount + 1) + ".xml";
                File file = new File(fileName);

                // Write the XML data to the new file
                FileWriter writer = new FileWriter(file);
                writer.write(XMLData);
                writer.close();
                logger.info("XML data written to file " + fileName + " successfully.");

                // Parse the XML data provided
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(file);
            }

            // Normalize the document
            doc.getDocumentElement().normalize();

            // Get all nodes in the document
            NodeList nodeList = doc.getElementsByTagName("*");

            // Loop through all nodes and extract data for Movie objects
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (!node.getNodeName().equals("root") && node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList childNodes = element.getChildNodes();
                    if (childNodes.getLength() != 1) {
                        Element elem = (Element) childNodes;
                        // System.out.println(elem.getNodeName());
                        if (elem.getNodeName().equals("Movie")) {
                            Movie movie = new Movie();
                            Coordinates coordinates = new Coordinates();
                            Person director = new Person();
                            for (int i = 0; i < childNodes.getLength(); i++) {
                                Node childNode = childNodes.item(i);
                                if (childNode != null && childNode.getNodeType() == Node.ELEMENT_NODE) {
                                    String content = childNode.getTextContent();
                                    if (content != null) {
                                        switch (childNode.getNodeName()) {
                                            case "name":
                                                movie.setName(content);
                                                break;
                                            case "coordinates":
                                                Element coordinatesElement = (Element) childNode;
                                                NodeList xList = coordinatesElement.getElementsByTagName("x");
                                                NodeList yList = coordinatesElement.getElementsByTagName("y");
                                                if (xList.getLength() > 0 && yList.getLength() > 0) {
                                                    try {
                                                        coordinates
                                                                .setX(Float.parseFloat(xList.item(0).getTextContent()));
                                                        coordinates
                                                                .setY(Float.parseFloat(yList.item(0).getTextContent()));
                                                    } catch (NumberFormatException e) {
                                                        logger.warn("incorrect coordinates");
                                                        coordinates.setX(0);
                                                        coordinates.setY(0);
                                                    }
                                                    movie.setCoordinates(coordinates);
                                                }
                                                break;
                                            case "creationDate":
                                                try {
                                                    movie.setCreationDate(ZonedDateTime.parse(content));
                                                } catch (IllegalArgumentException e) {
                                                    logger.warn("illegal creation date");
                                                    movie.setCreationDate(ZonedDateTime.now());
                                                }
                                                break;
                                            case "oscarsCount":
                                                try {
                                                    movie.setOscarsCount(Integer.parseInt(content));
                                                } catch (NumberFormatException e) {
                                                    logger.warn("not valid field");
                                                    movie.setOscarsCount(0);
                                                }
                                                break;
                                            case "goldenPalmCount":
                                                try {
                                                    movie.setGoldenPalmCount(Integer.parseInt(content));
                                                } catch (NumberFormatException e) {
                                                    logger.warn("not valid field");
                                                    movie.setGoldenPalmCount(0);
                                                }
                                                break;
                                            case "tagline":
                                                movie.setTagline(content);
                                                break;
                                            case "mpaaRating":
                                                try {
                                                    movie.setMpaaRating(MpaaRating.valueOf(content));
                                                } catch (IllegalArgumentException e) {
                                                    logger.warn("illegal rating");
                                                    movie.setMpaaRating(MpaaRating.PG);
                                                }
                                                break;
                                            case "director":
                                                Element directorElement = (Element) childNode;
                                                NodeList nameElements = directorElement.getElementsByTagName("name");
                                                if (nameElements.getLength() > 0) {
                                                    director.setName(nameElements.item(0).getTextContent());
                                                }
                                                NodeList heightElements = directorElement
                                                        .getElementsByTagName("height");
                                                if (heightElements.getLength() > 0) {
                                                    try {
                                                        director.setHeight(Double
                                                                .parseDouble(heightElements.item(0).getTextContent()));
                                                    } catch (NumberFormatException e) {
                                                        logger.warn("illegal height");
                                                        director.setHeight(1);
                                                    }

                                                }
                                                NodeList birth = directorElement.getElementsByTagName("birthday");
                                                try {
                                                    director.setBirthday(
                                                            ZonedDateTime.parse(birth.item(0).getTextContent()));
                                                } catch (IllegalArgumentException e) {
                                                    logger.warn("illegal birthday");
                                                    director.setBirthday(ZonedDateTime.now());
                                                }

                                                NodeList color = directorElement.getElementsByTagName("eyeColor");
                                                try {
                                                    director.setEyeColor(Color.valueOf(color.item(0).getTextContent()));
                                                } catch (IllegalArgumentException e) {
                                                    logger.warn("illegal color");
                                                    director.setEyeColor(Color.GREEN);
                                                }

                                                NodeList location = directorElement.getElementsByTagName("location");
                                                if (location.getLength() > 0) {
                                                    Element locationElement = (Element) location.item(0);
                                                    NodeList xElements = locationElement.getElementsByTagName("x");
                                                    NodeList yElements = locationElement.getElementsByTagName("y");
                                                    NodeList name = locationElement.getElementsByTagName("name");
                                                    double directorX = 0.0;
                                                    double directorY = 0.0;
                                                    String directorLocationName = "";
                                                    if (xElements.getLength() > 0) {
                                                        try {
                                                            directorX = Double
                                                                    .parseDouble(xElements.item(0).getTextContent());
                                                        } catch (NumberFormatException e) {
                                                            logger.warn("illegal directorX");
                                                            directorX = 1.0;
                                                        }

                                                    }
                                                    if (yElements.getLength() > 0) {
                                                        try {
                                                            directorY = Double
                                                                    .parseDouble(yElements.item(0).getTextContent());
                                                        } catch (NumberFormatException e) {
                                                            logger.warn("illegal directorX");
                                                            directorY = 1.0;
                                                        }
                                                    }
                                                    if (name.getLength() > 0) {
                                                        directorLocationName = name.item(0).getTextContent();
                                                    }
                                                    Location directorLocation = new Location();
                                                    directorLocation.setLocation(directorX, directorY,
                                                            directorLocationName);
                                                    director.setLocation(directorLocation);
                                                }
                                                movie.setDirector(director);
                                                break;
                                        }
                                    } else {
                                        logger.warn("Content is null");
                                        output.append("Content of document is null");
                                    }
                                }
                            }
                            movies.add(movie);
                        }
                    }
                }

            }
            // System.out.println(movies);
            return movies;
        } catch (Exception e) {
            logger.error("no such file");
            output.append("no such file");
        }
        return movies;
    }

    @Override
    public void execute(CommandOutput output) {
    }
}