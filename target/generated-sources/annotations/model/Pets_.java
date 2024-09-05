package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-09-05T10:25:27", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pets.class)
public class Pets_ { 

    public static volatile SingularAttribute<Pets, Date> castrateDate;
    public static volatile SingularAttribute<Pets, String> name;
    public static volatile SingularAttribute<Pets, Integer> id;
    public static volatile SingularAttribute<Pets, Date> adoptionDate;

}