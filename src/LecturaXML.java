import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

/**
 * Clase LecturaXML que lee un archivo XML y muestra su contenido de forma jerárquica.
 * Utiliza el modelo DOM para procesar el documento XML.
 */
public class LecturaXML {

    private static final String INDENT_CHAR = "  "; // Espacios para la indentación

    /**
     * Muestra la informacion de un nodo XML y recorre sus hijos de manera recursiva.
     *
     * @param nodo El nodo XML que se va a procesar.
     * @param level El nivel de indentacion para la jerarquia de nodos.
     * @param ps El PrintStream para imprimir la salida, puede ser System.out o cualquier otro.
     */
    public static void muestraNodo(Node nodo, int level, PrintStream ps) {
        // Ignorar nodos de texto que solo contienen espacios o saltos de línea
        if (nodo.getNodeType() == Node.TEXT_NODE && nodo.getNodeValue().trim().isEmpty()) {
            return;
        }

        // Imprimir la indentación
        for (int i = 0; i < level; i++) {
            ps.print(INDENT_CHAR);
        }

        // Mostrar informacion del nodo
        ps.print("Nodo - Tipo: (" + getNodeTypeString(nodo.getNodeType()) +
                ") - Nombre: " + nodo.getNodeName());

        // Si el nodo tiene atributos, mostrarlos
        if (nodo.hasAttributes()) {
            NamedNodeMap attributes = nodo.getAttributes();
            ps.print(" - Atributos: {");
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attr = attributes.item(i);
                ps.print(attr.getNodeName() + " = " + attr.getNodeValue());
                if (i < attributes.getLength() - 1) {
                    ps.print(", ");
                }
            }
            ps.print("}");
        }

        // Imprimir el valor del nodo si es un nodo de texto
        if (nodo.getNodeType() == Node.TEXT_NODE) {
            String valor = nodo.getNodeValue().trim();
            if (!valor.isEmpty()) {
                ps.print(" - Valor: " + valor);
            }
        }

        ps.println();

        // Recorrer y mostrar los nodos hijos
        NodeList childNodes = nodo.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            muestraNodo(childNodes.item(i), level + 1, ps);
        }
    }

    /**
     * Obtiene una representacion en forma de cadena del tipo de nodo XML.
     *
     * @param nodeType El tipo de nodo según el API DOM (por ejemplo, Node.ELEMENT_NODE).
     * @return Una cadena que describe el tipo de nodo.
     */
    private static String getNodeTypeString(int nodeType) {
        switch (nodeType) {
            case Node.ELEMENT_NODE: return "Elemento";
            case Node.ATTRIBUTE_NODE: return "Atributo";
            case Node.TEXT_NODE: return "Texto";
            case Node.CDATA_SECTION_NODE: return "Sección CDATA";
            case Node.ENTITY_REFERENCE_NODE: return "Referencia de entidad";
            case Node.PROCESSING_INSTRUCTION_NODE: return "Instrucción de procesamiento";
            case Node.COMMENT_NODE: return "Comentario";
            case Node.DOCUMENT_NODE: return "Documento";
            default: return "Desconocido";
        }
    }

    public static void main(String[] args) {

        String nomFich = "BOOKS.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(nomFich));
            muestraNodo(doc, 0, System.out);

            System.out.println("Encoding: " + doc.getXmlEncoding());
            System.out.println("Version: " + doc.getXmlVersion());
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
