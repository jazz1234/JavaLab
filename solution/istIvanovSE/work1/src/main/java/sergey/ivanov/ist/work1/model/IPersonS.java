package sergey.ivanov.ist.work1.model;

import javafx.beans.property.*;
import sergey.ivanov.ist.work1.util.ILocalDateAdapterS;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Created by yukkasarasti on 22.04.15.
 */
public class IPersonS {

    private final StringProperty ifirstNames;
    private final StringProperty ilastNames;
    private final StringProperty istreets;
    private final IntegerProperty ipostalCodes;
    private final StringProperty icitys;
    private final ObjectProperty<LocalDate> ibirthdays;


    public IPersonS() {
        this(null, null);
    }

    public IPersonS(String firstName, String lastName) {
        this.ifirstNames = new SimpleStringProperty(firstName);
        this.ilastNames = new SimpleStringProperty(lastName);

        this.istreets = new SimpleStringProperty("some street");
        this.ipostalCodes = new SimpleIntegerProperty(1234);
        this.icitys = new SimpleStringProperty("some city");
        this.ibirthdays = new SimpleObjectProperty<>(LocalDate.of(1999, 2, 21));
    }

    public String getFirstName() {
        return ifirstNames.get();
    }

    public void setFirstName(String firstName) {
        this.ifirstNames.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return ifirstNames;
    }

    public String getLastName() {
        return ilastNames.get();
    }

    public void setLastName(String lastName) {
        this.ilastNames.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return ilastNames;
    }

    public String getStreet() {
        return istreets.get();
    }

    public void setStreet(String street) {
        this.istreets.set(street);
    }

    public StringProperty streetProperty() {
        return istreets;
    }

    public int getPostalCode() {
        return ipostalCodes.get();
    }

    public void setPostalCode(int postalCode) {
        this.ipostalCodes.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return ipostalCodes;
    }

    public String getCity() {
        return icitys.get();
    }

    public void setCity(String city) {
        this.icitys.set(city);
    }

    public StringProperty cityProperty() {
        return icitys;
    }

    @XmlJavaTypeAdapter(ILocalDateAdapterS.class)
    public LocalDate getBirthday() {
        return ibirthdays.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.ibirthdays.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return ibirthdays;
    }
}
