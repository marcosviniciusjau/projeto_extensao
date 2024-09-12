package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pets;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-09-12T12:23:54", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Adotante.class)
public class Adotante_ { 

    public static volatile ListAttribute<Adotante, Pets> petsList;
    public static volatile SingularAttribute<Adotante, String> telefone;
    public static volatile SingularAttribute<Adotante, String> endereco;
    public static volatile SingularAttribute<Adotante, String> cpf;
    public static volatile SingularAttribute<Adotante, String> nome;
    public static volatile SingularAttribute<Adotante, Date> dataNascimento;
    public static volatile SingularAttribute<Adotante, String> cep;

}