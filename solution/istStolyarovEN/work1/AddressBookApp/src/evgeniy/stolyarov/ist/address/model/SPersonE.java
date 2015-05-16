package evgeniy.stolyarov.ist.address.model;



import java.time.LocalDate;


import evgeniy.stolyarov.ist.address.util.SLocalDateAdapterE;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SPersonE {

    private final StringProperty sfirstNamee;
    private final StringProperty slastNamee;
    private final StringProperty sstreete;
    private final IntegerProperty spostalCodee;
    private final StringProperty scitye;
    private final ObjectProperty<LocalDate> sbirthdaye;

    /**
     * Default constructor.
     */
    public SPersonE() { this(null, null); }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public SPersonE(String firstName, String lastName) {
        this.sfirstNamee = new SimpleStringProperty(firstName);
        this.slastNamee = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.sstreete = new SimpleStringProperty("some street");
        this.spostalCodee = new SimpleIntegerProperty(1234);
        this.scitye = new SimpleStringProperty("some city");
        this.sbirthdaye = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }
    @XmlElement(name = "firstName")
    public String getFirstName() { return sfirstNamee.get(); }

    public void setFirstName(String firstName) { this.sfirstNamee.set(firstName); }

    public StringProperty firstNameProperty() { return sfirstNamee; }

    @XmlElement(name = "lastName")
    public String getLastName() { return slastNamee.get(); }

    public void setLastName(String lastName) { this.slastNamee.set(lastName); }

    public StringProperty lastNameProperty() { return slastNamee; }

    @XmlElement(name = "street")
    public String getStreet() { return sstreete.get(); }

    public void setStreet(String street) { this.sstreete.set(street); }

    public StringProperty streetProperty() { return sstreete; }

    @XmlElement(name = "postalCode")
    public int getPostalCode() { return spostalCodee.get(); }

    public void setPostalCode(int postalCode) { this.spostalCodee.set(postalCode); }

    public IntegerProperty postalCodeProperty() { return spostalCodee; }

    @XmlElement(name = "city")
    public String getCity() { return scitye.get(); }

    public void setCity(String city) { this.scitye.set(city); }

    public StringProperty cityProperty() { return scitye; }

    @XmlJavaTypeAdapter(SLocalDateAdapterE.class)
    public LocalDate getBirthday() { return sbirthdaye.get(); }

    public void setBirthday(LocalDate birthday) { this.sbirthdaye.set(birthday); }

    public ObjectProperty<LocalDate> birthdayProperty() { return sbirthdaye; }
}