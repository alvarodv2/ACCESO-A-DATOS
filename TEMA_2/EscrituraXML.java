import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class EscrituraXML {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            // Leemos el documento XML original
            Document originalDoc = builder.parse(new File("libro.xml"));
            originalDoc.getDocumentElement().normalize();

            // Obtenemos la implementación DOM
            DOMImplementation domImpl = originalDoc.getImplementation();

            // Creamos nuevo documento XML traducido
            Document translatedDoc = domImpl.createDocument(null, "Catalogo", null);
            translatedDoc.setXmlVersion("1.0");
            translatedDoc.setXmlStandalone(true);

            // Obtenemos todos los nodos "book" del documento original
            NodeList books = originalDoc.getElementsByTagName("book");


            for (int i = 0; i < books.getLength(); i++) {
                Node bookNode = books.item(i);

                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Creamos el nuevo elemento "Libro" para el documento traducido
                    Element libroElement = translatedDoc.createElement("Libro");

                    // Obtenemos los hijos del nodo "book"
                    NodeList bookDetails = bookNode.getChildNodes();

                    for (int j = 0; j < bookDetails.getLength(); j++) {
                        Node detailNode = bookDetails.item(j);

                        if (detailNode.getNodeType() == Node.ELEMENT_NODE) {
                            String translatedTag = translateTag(detailNode.getNodeName());

                            Element translatedElement = translatedDoc.createElement(translatedTag);
                            translatedElement.setTextContent(detailNode.getTextContent());

                            libroElement.appendChild(translatedElement);
                        }
                    }

                    // Añadir el elemento "Libro" al nodo raíz "Catalogo"
                    translatedDoc.getDocumentElement().appendChild(libroElement);
                }
            }

            // Guardamos el documento traducido en un nuevo fichero con formato
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Configuramos sangría y saltos de línea
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Definir fuente y resultado
            DOMSource source = new DOMSource(translatedDoc);
            StreamResult result = new StreamResult(new File("librosTraducidos.xml"));

            // Transformar (escribir) el XML en el archivo
            transformer.transform(source, result);

            System.out.println("Documento XML traducido correctamente!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo para traducir las etiquetas del XML
    public static String translateTag(String tagName) {
        switch (tagName) {
            case "book":
                return "Libro";
            case "author":
                return "Autor";
            case "title":
                return "Titulo";
            case "genre":
                return "Genero";
            case "price":
                return "Precio";
            case "publish_date":
                return "Fecha_de_publicacion";
            case "description":
                return "Descripcion";
            default:
                return tagName;
        }
    }
}
