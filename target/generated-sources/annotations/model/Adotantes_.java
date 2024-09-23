package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pets;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-09-23T16:19:52", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Adotantes.class)
public class Adotantes_ { 

    public static volatile ListAttribute<Adotantes, Pets> petsList;
    public static volatile SingularAttribute<Adotantes, String> telefone;
    public static volatile SingularAttribute<Adotantes, String> endereco;
    public static volatile SingularAttribute<Adotantes, String> cpf;
    public static volatile SingularAttribute<Adotantes, String> nome;
    public static volatile SingularAttribute<Adotantes, Date> dataNascimento;
    public static volatile SingularAttribute<Adotantes, String> cep;

}