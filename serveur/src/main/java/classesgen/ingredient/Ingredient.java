//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2019.05.05 � 04:57:11 PM CEST 
//


package classesgen.ingredient;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Ingredient.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Ingredient">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="jambon"/>
 *     &lt;enumeration value="saumon"/>
 *     &lt;enumeration value="thon"/>
 *     &lt;enumeration value="poulet"/>
 *     &lt;enumeration value="mozza"/>
 *     &lt;enumeration value="chevre"/>
 *     &lt;enumeration value="roquefort"/>
 *     &lt;enumeration value="gruyere"/>
 *     &lt;enumeration value="champignons"/>
 *     &lt;enumeration value="poivrons"/>
 *     &lt;enumeration value="aubergine"/>
 *     &lt;enumeration value="ananas"/>
 *     &lt;enumeration value="coca"/>
 *     &lt;enumeration value="bananes"/>
 *     &lt;enumeration value="melon"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Ingredient", namespace = "http://classesGen/ingredient")
@XmlEnum
public enum Ingredient {

    @XmlEnumValue("jambon")
    JAMBON("jambon"),
    @XmlEnumValue("saumon")
    SAUMON("saumon"),
    @XmlEnumValue("thon")
    THON("thon"),
    @XmlEnumValue("poulet")
    POULET("poulet"),
    @XmlEnumValue("mozza")
    MOZZA("mozza"),
    @XmlEnumValue("chevre")
    CHEVRE("chevre"),
    @XmlEnumValue("roquefort")
    ROQUEFORT("roquefort"),
    @XmlEnumValue("gruyere")
    GRUYERE("gruyere"),
    @XmlEnumValue("champignons")
    CHAMPIGNONS("champignons"),
    @XmlEnumValue("poivrons")
    POIVRONS("poivrons"),
    @XmlEnumValue("aubergine")
    AUBERGINE("aubergine"),
    @XmlEnumValue("ananas")
    ANANAS("ananas"),
    @XmlEnumValue("coca")
    COCA("coca"),
    @XmlEnumValue("bananes")
    BANANES("bananes"),
    @XmlEnumValue("melon")
    MELON("melon");
    private final String value;

    Ingredient(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Ingredient fromValue(String v) {
        for (Ingredient c: Ingredient.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
