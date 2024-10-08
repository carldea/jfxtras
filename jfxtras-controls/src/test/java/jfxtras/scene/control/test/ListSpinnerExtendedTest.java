/**
 * Copyright (c) 2011-2024, JFXtras
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    Neither the name of the organization nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL JFXTRAS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jfxtras.scene.control.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.ListSpinner;
import jfxtras.scene.layout.VBox;
import jfxtras.test.JFXtrasGuiTest;
import jfxtras.test.TestUtil;

/**
 * Created by bblonski on 9/12/14.
 */
public class ListSpinnerExtendedTest extends JFXtrasGuiTest {

    private static Pane root;
    final private List<String> expected = FXCollections.observableArrayList("ListSpinner");
    private volatile boolean pass = true;

    @Test
    public void TestAnonymousListSpinner() throws Exception {
        final ListSpinner spinner = new ListSpinner() {};
        Assert.assertEquals(expected, spinner.getStyleClass());
        TestUtil.runThenWaitForPaintPulse( () -> {
            root.getChildren().add(spinner);
        });
        Assert.assertTrue(pass);
    }

    @Test
    public void TestExtendedListSpinner() throws Exception {
        final TestListSpinner spinner = new TestListSpinner();
        Assert.assertEquals(expected, spinner.getStyleClass());
        TestUtil.runThenWaitForPaintPulse( () -> {
            root.getChildren().add(spinner);
        });
        Assert.assertTrue(pass);
    }

    @Override
    protected Parent getRootNode() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                pass = false;
            }
        });
        root = new VBox();
        return root;
    }

    private class TestListSpinner extends ListSpinner {};
}
