package io.github.email.client.base;


import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainScreenTest {
	private FrameFixture window;

	@BeforeAll
	static void beforeAll() {
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