package org.mockserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jamesdbloom
 */
public abstract class ObjectWithReflectiveEqualsHashCodeToString {

    @JsonIgnore
    private String[] fieldsExcludedFromEqualsAndHashCode = new String[]{"logger", "fieldsExcludedFromEqualsAndHashCode"};

    @JsonIgnore
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
    }

    protected String[] fieldsExcludedFromEqualsAndHashCode() {
        return fieldsExcludedFromEqualsAndHashCode;
    }

    protected void addFieldsExcludedFromEqualsAndHashCode(String... toExclude) {
        fieldsExcludedFromEqualsAndHashCode = ArrayUtils.addAll(fieldsExcludedFromEqualsAndHashCode, toExclude);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, fieldsExcludedFromEqualsAndHashCode());
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other, fieldsExcludedFromEqualsAndHashCode());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, fieldsExcludedFromEqualsAndHashCode());
    }
}
