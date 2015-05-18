package sergey.ivanov.ist.work1.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by yukkasarasti on 22.04.15.
 */
@XmlRootElement(name = "persons")
public class IPersonListWrapperS {

    private List<IPersonS> ipersoness;

    @XmlElement(name = "person")
    public List<IPersonS> getIpersoness() {
        return ipersoness;
    }

    public void setIpersoness(List<IPersonS> ipersoness) {
        this.ipersoness = ipersoness;
    }
}
