import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.Scanner;

public class LecturaXPath {
    public static void main(String[] args) {
        try {
            // Cargamos el fichero XML
            File inputFile = new File("librosTraducidos.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Creamos el objeto XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();


            Scanner scanner = new Scanner(System.in);
            int option = 0;

            do {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Mostrar todos los títulos de los libros");
                System.out.println("2. Mostrar los títulos de los libros del género 'Fantasy'");
                System.out.println("3. Mostrar los autores de libros con un precio mayor a 40");
                System.out.println("4. Mostrar la descripción del libro 'XML Developer's Guide'");
                System.out.println("5. Salir");

                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        // Consulta 1: Obtener todos los títulos de los libros
                        String expression1 = "//Titulo";
                        NodeList nodeList1 = (NodeList) xPath.compile(expression1).evaluate(doc, XPathConstants.NODESET);
                        System.out.println("\nTítulos de todos los libros:");
                        for (int i = 0; i < nodeList1.getLength(); i++) {
                            System.out.println("- " + nodeList1.item(i).getTextContent());
                        }
                        break;

                    case 2:
                        // Consulta 2: Obtener los títulos de los libros del género 'Fantasy'
                        String expression2 = "//Libro[Genero='Fantasy']/Titulo";
                        NodeList nodeList2 = (NodeList) xPath.compile(expression2).evaluate(doc, XPathConstants.NODESET);
                        System.out.println("\nLibros del género 'Fantasy':");
                        for (int i = 0; i < nodeList2.getLength(); i++) {
                            System.out.println("- " + nodeList2.item(i).getTextContent());
                        }
                        break;

                    case 3:
                        // Consulta 3: Obtener los autores de libros con un precio mayor a 40
                        String expression3 = "//Libro[Precio>40]/Autor";
                        NodeList nodeList3 = (NodeList) xPath.compile(expression3).evaluate(doc, XPathConstants.NODESET);
                        System.out.println("\nAutores de libros con un precio mayor a 40:");
                        for (int i = 0; i < nodeList3.getLength(); i++) {
                            System.out.println("- " + nodeList3.item(i).getTextContent());
                        }
                        break;

                    case 4:
                        // Consulta 4: Obtener la descripción del libro 'XML Developer's Guide'
                        String expression4 = "//Libro[Titulo=\"XML Developer's Guide\"]/Descripcion";
                        String descripcion = (String) xPath.compile(expression4).evaluate(doc, XPathConstants.STRING);
                        System.out.println("\nDescripción del libro 'XML Developer's Guide':");
                        System.out.println(descripcion);
                        break;

                    case 5:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }

                System.out.println();

            } while (option != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

