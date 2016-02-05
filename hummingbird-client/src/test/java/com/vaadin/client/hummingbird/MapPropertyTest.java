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
package com.vaadin.client.hummingbird;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.client.hummingbird.reactive.CountingComputation;
import com.vaadin.client.hummingbird.reactive.Reactive;

import elemental.events.EventRemover;

public class MapPropertyTest {
    private MapProperty property = new MapProperty("foo",
            new MapNamespace(0, new StateNode(0, new StateTree())));

    @Test
    public void testValue() {
        Assert.assertNull(property.getValue());
        Assert.assertFalse(property.hasValue());

        property.setValue("bar");

        Assert.assertEquals("bar", property.getValue());
        Assert.assertTrue(property.hasValue());
    }

    @Test
    public void testChangeListener() {
        AtomicReference<MapPropertyChangeEvent> lastEvent = new AtomicReference<>();

        EventRemover remover = property
                .addChangeListener(new MapPropertyChangeListener() {
                    @Override
                    public void onPropertyChange(MapPropertyChangeEvent event) {
                        Assert.assertNull("Got unexpected event",
                                lastEvent.get());
                        lastEvent.set(event);
                    }
                });

        property.setValue("foo");

        MapPropertyChangeEvent event = lastEvent.get();
        Assert.assertSame(property, event.getSource());
        Assert.assertNull(event.getOldValue());
        Assert.assertEquals("foo", event.getNewValue());

        property.setValue("foo");
        Assert.assertSame("No new event should have fired", event,
                lastEvent.get());

        lastEvent.set(null);
        property.removeValue();

        MapPropertyChangeEvent removeEvent = lastEvent.get();
        Assert.assertNull(removeEvent.getNewValue());

        property.removeValue();
        Assert.assertSame("No new event should have fired", removeEvent,
                lastEvent.get());

        lastEvent.set(null);
        property.setValue(null);
        MapPropertyChangeEvent addBackEvent = lastEvent.get();
        Assert.assertNull(addBackEvent.getOldValue());

        remover.remove();

        property.setValue("bar");

        Assert.assertSame("No new event should have fired", addBackEvent,
                lastEvent.get());
    }

    @Test
    public void testReactive() {
        CountingComputation computation = new CountingComputation(
                () -> property.getValue());

        Reactive.flush();

        Assert.assertEquals(1, computation.getCount());

        property.setValue("bar");
        property.setValue("baz");

        Assert.assertEquals(1, computation.getCount());

        Reactive.flush();

        Assert.assertEquals(2, computation.getCount());
    }

    @Test
    public void testHasValueReactive() {
        CountingComputation computation = new CountingComputation(
                () -> property.hasValue());

        Reactive.flush();

        property.setValue("baz");

        Assert.assertEquals(1, computation.getCount());

        Reactive.flush();

        Assert.assertEquals(2, computation.getCount());
    }

    @Test
    public void testRemoveValue() {
        property.setValue("foo");
        Assert.assertTrue(property.hasValue());

        property.removeValue();

        Assert.assertFalse(property.hasValue());
        Assert.assertNull(property.getValue());
    }
}
