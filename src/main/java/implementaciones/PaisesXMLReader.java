package implementaciones;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PaisesXMLReader {

    public static List<String[]> leerPaises() {
        List<String[]> paises = new ArrayList<>();
        try {
            File file = new File("src/main/resources/paises.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList lista = doc.getElementsByTagName("pais");
            for (int i = 0; i < lista.getLength(); i++) {
                Element pais = (Element) lista.item(i);
                String id = pais.getElementsByTagName("id").item(0).getTextContent();
                String nombre = pais.getElementsByTagName("nombre").item(0).getTextContent();
                paises.add(new String[]{id, nombre});
            }
        } catch (Exception e) {
            System.out.println("Error al leer paises.xml: " + e.getMessage());
        }
        return paises;
    }

    public static String obtenerNombrePaisPorId(List<String[]> paises, String id) {
        for (String[] p : paises) {
            if (p[0].equalsIgnoreCase(id)) return p[1];
        }
        return null;
    }
}

