import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private Voter voter;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if(qName.equals("voter") && voter == null && DBConnection.getInsertQuery().length() <= 2000){
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            DBConnection.countVoter(name, birthDay);
            } else if (DBConnection.getInsertQuery().length() > 2000) {
                DBConnection.executeMultiInsert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
