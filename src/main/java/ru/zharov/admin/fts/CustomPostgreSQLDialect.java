package ru.zharov.admin.fts;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.PatternFunctionDescriptorBuilder;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomPostgreSQLDialect extends PostgreSQLDialect {

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {

        super.initializeFunctionRegistry(functionContributions);
        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();
        TypeConfiguration types = functionContributions.getTypeConfiguration();

        /*
            = 'null' сделано по следующей причине
            если с веба в json не приходит параметр, т.е. String имеет пустую ссылку, то возникает ошибка
            SQLState: 42883
            ERROR: function plainto_tsquery(bytea) does not exist
            вариант в pattern сделать
            CASE WHEN ?2 IS NULL THEN TRUE ELSE ?1 @@ plainto_tsquery(?2) END
            не работает
            также если в JPQL добавить условием на is null, то hibernate все равно вызывает функцию fts и падает с ошибкой
        */
        new PatternFunctionDescriptorBuilder(registry, "fts", FunctionKind.NORMAL,
                "(?2 = 'null' or ?2 != 'null' AND ?1 @@ plainto_tsquery(?2))")
                .setExactArgumentCount(2)
                .setInvariantType(types.getBasicTypeForJavaType(Boolean.class))
                .register();
    }

}
