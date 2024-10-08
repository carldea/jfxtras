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
package jfxtras.scene.layout.test;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.test.TestUtil;
import org.junit.Test;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import jfxtras.scene.layout.HBox;
import jfxtras.test.AssertNode;
import jfxtras.test.JFXtrasGuiTest;

/**
 * Created by Tom Eugelink on 26-12-13.
 */
public class HBoxTest extends JFXtrasGuiTest {

	@Override
	public void start(Stage stage) throws Exception {
		javafx.scene.text.Font.loadFont(this.getClass().getResource("/" + "Roboto-Medium.ttf").toExternalForm(), 12);

		this.stage = stage;
		Scene scene = new Scene(getRootNode());
		stage.setScene(scene);
		stage.show();
	}
	protected Stage stage;

	/**
	 * 
	 */
	@Override
	public Parent getRootNode()
	{
		stage.setWidth(800.0);
		stage.setHeight(600.0);

		hbox = new HBox(5.0);
		hbox.add(newButton("grow"), new HBox.C().hgrow(Priority.ALWAYS));
		hbox.add(newButton("margin 5 grow"), new HBox.C().margin(new Insets(5.0)).hgrow(Priority.ALWAYS));
		hbox.getChildren().add(newButton("old style"));
		hbox.add(newButton("margin 20 nogrow"), new HBox.C().margin(new Insets(20.0)));
		hbox.add(newButton("grow maxheight 50"), new HBox.C().hgrow(Priority.ALWAYS).maxHeight(50.0));

		return hbox;
	}
	private HBox hbox = null;

	/**
	 * 
	 */
	@Test
	public void checkPositions()
	{
		AssertNode.generateSource("hbox", hbox.getChildren(), null, false, AssertNode.A.XYWH, AssertNode.A.CLASS);
		new AssertNode(hbox.getChildren().get(0)).assertXYWH(0.0, 0.0, 142.0, 25.0, 0.01).assertClass(javafx.scene.control.Button.class);
		new AssertNode(hbox.getChildren().get(1)).assertXYWH(152.0, 5.0, 193.0, 25.0, 0.01).assertClass(javafx.scene.control.Button.class);
		new AssertNode(hbox.getChildren().get(2)).assertXYWH(355.0, 0.0, 62.0, 25.0, 0.01).assertClass(javafx.scene.control.Button.class);
		new AssertNode(hbox.getChildren().get(3)).assertXYWH(442.0, 20.0, 114.0, 25.0, 0.01).assertClass(javafx.scene.control.Button.class);
		new AssertNode(hbox.getChildren().get(4)).assertXYWH(581.0, 0.0, 219.0, 50.0, 0.01).assertClass(javafx.scene.control.Button.class);
	}

	private Button newButton(String label) {
		Button button = new Button(label);
		button.setFont(new Font("Roboto Medium", 12));
		return button;
	}
}
