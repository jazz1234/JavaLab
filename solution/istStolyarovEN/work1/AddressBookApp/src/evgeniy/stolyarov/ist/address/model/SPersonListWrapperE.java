package evgeniy.stolyarov.ist.address.model;

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
public class SPersonListWrapperE {

    private List<SPersonE> sPersonse;

    @XmlElement(name = "person")
    public List<SPersonE> getPersons() {
        return sPersonse;
    }

    public void setPersons(List<SPersonE> persons) {
        this.sPersonse = persons;
    }
}