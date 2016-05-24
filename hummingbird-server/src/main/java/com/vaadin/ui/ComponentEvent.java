/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.ui;

import java.util.EventObject;

import com.vaadin.hummingbird.event.ComponentEventBus;

/**
 * An event whose source is a {@link Component}.
 * <p>
 * Typically used together with {@link ComponentEventBus}.
 *
 * @param <T>
 *            the event source type
 *
 * @author Vaadin Ltd
 */
public class ComponentEvent<T extends Component> extends EventObject {

    private boolean fromClient = false;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source
     *            the source component
     * @param fromClient
     *            <code>true</code> if the event originated from the client
     *            side, <code>false</code> otherwise
     */
    public ComponentEvent(T source, boolean fromClient) {
        super(source);
        this.fromClient = fromClient;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getSource() {
        return (T) super.getSource();
    }

    /**
     * Checks if this event originated from the client side.
     *
     * @return <code>true</code> if the event originated from the client side,
     *         <code>false</code> otherwise
     */
    public boolean isFromClient() {
        return fromClient;
    }
}
