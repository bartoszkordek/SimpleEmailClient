package io.github.email.client.base;


import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.Assume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.awt.*;

@EnabledOnOs(OS.WINDOWS)
class MainScreenTest extends AssertJSwingTestCaseTemplate {
	private FrameFixture window;

	@BeforeAll
	static void beforeAll() {
		Assume.assumeFalse(GraphicsEnvironment.isHeadless());
		FailOnThreadViolationRepaintManager.install();
	}

	@BeforeEach
	void setUp() {
		MainScreen frame = GuiActionRunner.execute(MainScreen::new);
		window = new FrameFixture(frame);
	}

	@Test
	void shouldHaveSendEmailButton() {
		window.button("Send email")
				.requireText("Send email")
				.requireVisible();
	}

	@Test
	void shouldHaveSettingsButton() {
		window.button("Settings")
				.requireText("Settings")
				.requireVisible();
	}

	@AfterEach
	void tearDown() {
		window.cleanUp();
	}
}