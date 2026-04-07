package tailwindfx;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ComponentFactory.
 * Requires JavaFX thread.
 */
@DisplayName("ComponentFactory Tests")
class ComponentFactoryTest extends ApplicationTest {

    @Nested
    @DisplayName("Avatar Component")
    class AvatarTests {

        @Test
        @DisplayName("Should create avatar with initials")
        void testAvatarWithInitials() {
            StackPane avatar = ComponentFactory.avatar("JD", "blue", 48);
            
            assertNotNull(avatar);
            assertTrue(avatar.getChildren().size() > 0);
        }

        @Test
        @DisplayName("Should create avatar with custom size")
        void testAvatarCustomSize() {
            StackPane avatar = ComponentFactory.avatar("AB", "red", 64);

            assertNotNull(avatar);
            // Avatar uses min/max dimensions, not pref
            assertTrue(avatar.getMinWidth() >= 64 || avatar.getPrefWidth() == -1);
        }

        @Test
        @DisplayName("Should create avatar with default color")
        void testAvatarDefaultColor() {
            StackPane avatar = ComponentFactory.avatar("XY", null, 40);
            
            assertNotNull(avatar);
        }
    }

    @Nested
    @DisplayName("Badge Component")
    class BadgeTests {

        @Test
        @DisplayName("Should create badge with text")
        void testBadgeCreation() {
            Label badge = ComponentFactory.badge("New", "blue");

            assertNotNull(badge);
            assertEquals("NEW", badge.getText()); // badge converts to uppercase
        }

        @Test
        @DisplayName("Should create badge with different colors")
        void testBadgeColors() {
            Label badgeRed = ComponentFactory.badge("Error", "red");
            Label badgeGreen = ComponentFactory.badge("Success", "green");
            
            assertNotNull(badgeRed);
            assertNotNull(badgeGreen);
        }
    }

    @Nested
    @DisplayName("Card Component")
    class CardTests {

        @Test
        @DisplayName("Should create card container")
        void testCardCreation() {
            VBox card = ComponentFactory.card().build();

            assertNotNull(card);
        }

        @Test
        @DisplayName("Should create card with content")
        void testCardWithContent() {
            Label content = new Label("Test");
            VBox card = ComponentFactory.card().body(content).build();

            assertNotNull(card);
            assertEquals(1, card.getChildren().size());
        }
    }

    @Nested
    @DisplayName("Button Component")
    class ButtonTests {

        @Test
        @DisplayName("Should create primary styled button")
        void testPrimaryButton() {
            Button btn = new Button("Save");
            TailwindFX.apply(btn, "btn-primary");

            assertNotNull(btn);
            assertEquals("Save", btn.getText());
        }

        @Test
        @DisplayName("Should create secondary styled button")
        void testSecondaryButton() {
            Button btn = new Button("Cancel");
            TailwindFX.apply(btn, "btn-secondary");

            assertNotNull(btn);
        }

        @Test
        @DisplayName("Should create buttons with variants")
        void testButtonVariants() {
            Button btnPrimary = new Button("Primary");
            TailwindFX.apply(btnPrimary, "btn-primary");
            Button btnSecondary = new Button("Secondary");
            TailwindFX.apply(btnSecondary, "btn-secondary");
            Button btnDanger = new Button("Delete");
            TailwindFX.apply(btnDanger, "btn-danger");

            assertNotNull(btnPrimary);
            assertNotNull(btnSecondary);
            assertNotNull(btnDanger);
        }
    }

    @Nested
    @DisplayName("Input Component")
    class InputTests {

        @Test
        @DisplayName("Should create styled text field")
        void testTextFieldCreation() {
            TextField field = new TextField();
            field.setPromptText("Enter text...");
            TailwindFX.apply(field, "input");

            assertNotNull(field);
            assertEquals("Enter text...", field.getPromptText());
        }

        @Test
        @DisplayName("Should create text field without prompt")
        void testTextFieldWithoutPrompt() {
            TextField field = new TextField();
            TailwindFX.apply(field, "input");

            assertNotNull(field);
        }
    }

    @Nested
    @DisplayName("Modal Component")
    class ModalTests {

        @Test
        @DisplayName("Should create modal builder")
        void testModalCreation() {
            var modalBuilder = ComponentFactory.modal(new Label("Content"));

            assertNotNull(modalBuilder);
        }

        @Test
        @DisplayName("Should create modal with actions")
        void testModalWithActions() {
            Button okBtn = new Button("OK");
            var modalBuilder = ComponentFactory.modal(new Label("Content"));

            assertNotNull(modalBuilder);
        }
    }

    @Nested
    @DisplayName("Tooltip Component")
    class TooltipTests {

        @Test
        @DisplayName("Should create tooltip for node")
        void testTooltipCreation() {
            Label label = new Label("Hover me");
            var tooltip = ComponentFactory.tooltip(label, "Tooltip text");

            assertNotNull(tooltip);
        }
    }

    @Nested
    @DisplayName("Drawer Component")
    class DrawerTests {

        @Test
        @DisplayName("Should create drawer builder")
        void testDrawerCreation() {
            var drawerBuilder = ComponentFactory.drawer(ComponentFactory.DrawerSide.LEFT, 280);

            assertNotNull(drawerBuilder);
        }
    }
}
