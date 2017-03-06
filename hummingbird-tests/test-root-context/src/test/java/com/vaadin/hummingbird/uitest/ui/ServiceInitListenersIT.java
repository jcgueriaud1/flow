/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.hummingbird.uitest.ui;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.testutil.PhantomJSTest;
import com.vaadin.testbench.By;

/**
 * @author Vaadin Ltd
 *
 */
public class ServiceInitListenersIT extends PhantomJSTest {

    @Test
    public void testServiceInitListenerTriggered() {
        open();

        List<WebElement> labels = findElements(By.tagName("label"));
        Assert.assertNotEquals(labels.get(0).getText(), 0,
                extractCount(labels.get(0).getText()));
        Assert.assertNotEquals(labels.get(1).getText(), 0,
                extractCount(labels.get(1).getText()));
    }

    private int extractCount(String logRow) {
        // Assuming row pattern is "label: 1"
        String substring = logRow.replaceAll("[^:]*:\\s*", "");
        return Integer.parseInt(substring);
    }
}
