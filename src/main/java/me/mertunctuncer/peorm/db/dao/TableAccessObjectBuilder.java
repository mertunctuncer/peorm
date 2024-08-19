package me.mertunctuncer.peorm.db.dao;

import me.mertunctuncer.peorm.reflection.ClassParser;
import me.mertunctuncer.peorm.reflection.InstanceFactory;
import me.mertunctuncer.peorm.reflection.ReflectionContainer;

import java.util.Map;

public class TableAccessObjectBuilder<T> {

    private final ReflectionContainer<T> reflectionContainer;
    private final InstanceFactory<T> instanceFactory;
    private T defaults = null;

    public TableAccessObjectBuilder(ReflectionContainer<T> reflectionContainer) {
        this.reflectionContainer = reflectionContainer;
        this.instanceFactory = new InstanceFactory<>(reflectionContainer);
    }

    public TableAccessObjectBuilder<T> withDefaultsFrom(T defaults) {
        this.defaults = defaults;
        return this;
    }

    public TableAccessObject<T> build() {
        ClassParser<T> parser = new ClassParser<>(reflectionContainer);

        if(defaults != null) parser.setDefaults(defaults);

        Map<String, String> aliases = parser.getFieldAliases();
        instanceFactory.addAlias(aliases);

        return new BlockingTableAccessObject<>(
                parser.getTableProperties(),
                reflectionContainer,
                instanceFactory
        );
    }
}
