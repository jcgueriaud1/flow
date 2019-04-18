package com.vaadin.flow.uitest.ui;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.testutil.ChromeBrowserTest;

public class RouteNotFoundIT extends ChromeBrowserTest {
    /*
        Original script:
        <img src=x onerror=(function(){d=document.createElement("DIV");document.body.appendChild(d);d.id="injected";})()>
     */
    private static final String INJECT_ATTACK = "%3Cimg%20src%3Dx%20onerror" +
            "%3D%28function%28%29%7Bd%3Ddocument.createElement%28%22DIV%22%" +
            "29%3Bdocument.body.appendChild%28d%29%3Bd.id%3D%22injected%22%" +
            "3B%7D%29%28%29%3E";

    @Test
    public void notFoundDevMode() {
        getDriver().get(getRootURL() + "/view/notfound/" + INJECT_ATTACK);
        String pageSource = getDriver().getPageSource();
        Assert.assertTrue(pageSource.contains("Available routes"));
        Assert.assertTrue(pageSource.contains("noParent"));
        Assert.assertTrue(pageSource.contains("foo/bar"));
        // check that <img src=x onerror=...> did not inject div via script
        Assert.assertFalse(pageSource.contains("<div id=\"injected\"></div>"));
    }

    @Test
    public void notFoundProdMode() {
        getDriver().get(getRootURL() + "/view-production/notfound/" + INJECT_ATTACK);
        String pageSource = getDriver().getPageSource();
        Assert.assertFalse(pageSource.contains("Available routes"));
        Assert.assertFalse(pageSource.contains("noParent"));
        Assert.assertFalse(pageSource.contains("foo/bar"));
        // check that <img src=x onerror=...> did not inject div via script
        Assert.assertFalse(pageSource.contains("<div id=\"injected\"></div>"));
    }

}
