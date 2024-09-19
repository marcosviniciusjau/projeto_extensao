package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Adotante;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-09-19T13:19:47", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pets.class)
public class Pets_ { 

    public static volatile SingularAttribute<Pets, BigDecimal> peso;
    public static volatile SingularAttribute<Pets, Date> dataCastracao;
    public static volatile SingularAttribute<Pets, Date> dataAdocao;
    public static volatile SingularAttribute<Pets, Adotante> adotanteCpf;
    public static volatile SingularAttribute<Pets, Integer> codigoMicrochip;

}