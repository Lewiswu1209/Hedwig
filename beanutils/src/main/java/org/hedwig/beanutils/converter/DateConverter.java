package org.hedwig.beanutils.converter;

import java.util.Date;

import org.apache.commons.beanutils.converters.DateTimeConverter;

public final class DateConverter extends DateTimeConverter {

    public DateConverter() {
        super();
    }

    public DateConverter(final String pattern) {
        super();
        setPattern(pattern);
    }

    @Override
    protected Class<?> getDefaultType() {
        return Date.class;
    }

}
