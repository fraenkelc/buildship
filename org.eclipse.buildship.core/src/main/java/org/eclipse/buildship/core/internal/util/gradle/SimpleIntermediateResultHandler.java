/*
 * Copyright (c) 2019 the original author or authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.buildship.core.internal.util.gradle;

import org.gradle.tooling.IntermediateResultHandler;

public final class SimpleIntermediateResultHandler<T> implements IntermediateResultHandler<T> {

    private T result;

    @Override
    public void onComplete(T result) {
        this.result = result;
    }

    public T getValue() {
        return this.result;
    }
}
