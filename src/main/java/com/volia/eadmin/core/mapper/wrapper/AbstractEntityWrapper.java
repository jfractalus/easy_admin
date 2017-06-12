package com.volia.eadmin.core.mapper.wrapper;

import com.volia.eadmin.core.annotation.AnnotationParser;

public abstract class AbstractEntityWrapper<R> implements Wrapper<R> {
    private AnnotationParser annotationParser;

    public AbstractEntityWrapper(AnnotationParser annotationParser) {
        this.annotationParser = annotationParser;
    }

    protected AnnotationParser getAnnotationParser() {
        return annotationParser;
    }
}
