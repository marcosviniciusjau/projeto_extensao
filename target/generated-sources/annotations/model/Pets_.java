package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Adotantes;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-09-23T16:19:52", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pets.class)
public class Pets_ { 

    public static volatile SingularAttribute<Pets, BigDecimal> peso;
    public static volatile SingularAttribute<Pets, Date> dataVacinas;
    public static volatile SingularAttribute<Pets, Date> dataCastracao;
    public static volatile SingularAttribute<Pets, Date> dataAdocao;
    public static volatile SingularAttribute<Pets, Adotantes> adotanteCpf;
    public static volatile SingularAttribute<Pets, Integer> codigoMicrochip;

}