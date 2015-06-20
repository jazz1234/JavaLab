package Nikita.Kolchevnikov.Ivt.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement(name = "persons")
public class KPersonListWrapperN {

    private List<KPersonN> persons;

    @XmlElement(name = "person")
    public List<KPersonN> getPersons() {
        return persons;
    }

    public void setPersons(List<KPersonN> persons) {
        this.persons = persons;
    }
}