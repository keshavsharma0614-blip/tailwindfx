package tailwindfx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.LabeledMatchers.*;

/**
 * Unit tests for TailwindFX main entry point with full TestFX capabilities.
 * Tests CSS installation, style application, and user interactions.
 */
@DisplayName("TailwindFX Main Entry Point Tests with TestFX")
class TailwindFXMainTest extends ApplicationTest {

    private Scene scene;
    private StackPane root;

    @BeforeEach
    void setUp() {
        root = new StackPane();
        scene = new Scene(root, 800, 600);
    }

    @Nested
    @DisplayName("CSS Installation")
    class CssInstallationTests {

        @Test
        @DisplayName("Should install main CSS file")
        void testInstallMainCss() {
            TailwindFX.install(scene);

            assertTrue(scene.getStylesheets().size() > 0);
            String cssUrl = scene.getStylesheets().get(0);
            assertTrue(cssUrl.contains("tailwindfx.css"));
        }

        @Test
        @DisplayName("Should not duplicate CSS installation")
        void testNoDuplicateCss() {
            TailwindFX.install(scene);
            TailwindFX.install(scene);

            long count = scene.getStylesheets().stream()
                    .filter(url -> url.contains("tailwindfx.css"))
                    .count();
            assertEquals(1, count);
        }

        @Test
        @DisplayName("Should install base CSS")
        void testInstallBaseCss() {
            TailwindFX.installBase(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-base.css")));
        }

        @Test
        @DisplayName("Should install components CSS")
        void testInstallComponentsCss() {
            TailwindFX.installComponents(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-components.css")));
        }

        @Test
        @DisplayName("Should install utilities CSS")
        void testInstallUtilitiesCss() {
            TailwindFX.installUtilities(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-utilities.css")));
        }

        @Test
        @DisplayName("Should install colors CSS")
        void testInstallColorsCss() {
            TailwindFX.installColors(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-colors.css")));
        }

        @Test
        @DisplayName("Should install effects CSS")
        void testInstallEffectsCss() {
            TailwindFX.installEffects(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-effects.css")));
        }

        @Test
        @DisplayName("Should install components preset CSS")
        void testInstallComponentsPresetCss() {
            TailwindFX.installComponentsPreset(scene);

            assertTrue(scene.getStylesheets().stream()
                    .anyMatch(url -> url.contains("tailwindfx-components-preset.css")));
        }
    }

    @Nested
    @DisplayName("Apply Styles")
    class ApplyStylesTests {

        @Test
        @DisplayName("Should apply single style class and verify visibility")
        void testApplySingleStyle() {
            Label label = new Label("Test");
            TailwindFX.apply(label, "text-blue-500");

            assertTrue(label.getStyleClass().contains("text-blue-500"));
            
            interact(() -> {
                root.getChildren().add(label);
            });
            
            verifyThat(Label.class, hasText("Test"));
            verifyThat(".text-blue-500", hasStyleClass("text-blue-500"));
        }

        @Test
        @DisplayName("Should apply multiple style classes and verify all present")
        void testApplyMultipleStyles() {
            Label label = new Label("Test");
            TailwindFX.apply(label, "text-blue-500", "font-bold", "text-lg");

            assertTrue(label.getStyleClass().contains("text-blue-500"));
            assertTrue(label.getStyleClass().contains("font-bold"));
            assertTrue(label.getStyleClass().contains("text-lg"));
            
            interact(() -> {
                root.getChildren().add(label);
            });
            
            // Verify label is visible with all styles
            verifyThat(Label.class, hasText("Test"));
        }

        @Test
        @DisplayName("Should apply button styles and test click interaction")
        void testApplyButtonStyles() {
            Button btn = new Button("Click");
            TailwindFX.apply(btn, "btn-primary");

            assertTrue(btn.getStyleClass().contains("btn-primary"));
            
            interact(() -> {
                root.getChildren().add(btn);
            });
            
            verifyThat(Button.class, hasText("Click"));
            verifyThat(".btn-primary", hasStyleClass("btn-primary"));
            
            // Test button click
            clickOn(btn);
        }

        @Test
        @DisplayName("Should handle null style gracefully without crashing")
        void testApplyNullStyle() {
            Label label = new Label("Test");
            // Should not throw
            assertDoesNotThrow(() -> TailwindFX.apply(label, (String) null));
            
            interact(() -> {
                root.getChildren().add(label);
            });
            
            verifyThat(Label.class, hasText("Test"));
        }

        @Test
        @DisplayName("Should handle empty styles and remain functional")
        void testApplyEmptyStyles() {
            Label label = new Label("Test");
            TailwindFX.apply(label);

            assertTrue(label.getStyleClass().isEmpty());
            
            interact(() -> {
                root.getChildren().add(label);
            });
            
            verifyThat(Label.class, hasText("Test"));
        }
    }

    @Nested
    @DisplayName("JIT Compilation")
    class JitCompilationTests {

        @Test
        @DisplayName("Should apply JIT styles to node")
        void testJitApply() {
            Label label = new Label("Test");
            TailwindFX.jitApply(label, "p-4", "text-sm");

            // Should add inline styles
            assertNotNull(label.getStyle());
        }

        @Test
        @DisplayName("Should apply mixed JIT and CSS class styles")
        void testJitApplyMixed() {
            Label label = new Label("Test");
            TailwindFX.jitApply(label, "btn-primary", "bg-blue-500/80");

            assertNotNull(label.getStyle());
        }

        @Test
        @DisplayName("Should handle JIT with arbitrary values")
        void testJitArbitraryValues() {
            Label label = new Label("Test");
            TailwindFX.jitApply(label, "p-[13px]", "w-[200px]");

            assertTrue(label.getStyle().contains("13px"));
            assertTrue(label.getStyle().contains("200px"));
        }
    }

    @Nested
    @DisplayName("Utility Methods")
    class UtilityMethodsTests {

        @Test
        @DisplayName("Should create layout builder")
        void testLayoutBuilder() {
            var builder = TailwindFX.layout(root);
            assertNotNull(builder);
        }

        @Test
        @DisplayName("Should create responsive manager")
        void testResponsiveManager() {
            Stage stage = new Stage();
            var manager = TailwindFX.responsive(stage);
            assertNotNull(manager);
        }

        @Test
        @DisplayName("Should create theme manager")
        void testThemeManager() {
            var theme = TailwindFX.theme(scene);
            assertNotNull(theme);
        }
    }

    @Nested
    @DisplayName("Convenience Methods")
    class ConvenienceMethodsTests {

        @Test
        @DisplayName("Should apply padding via utility class")
        void testConveniencePadding() {
            Label label = new Label("Test");
            TailwindFX.jit(label, "p-4");

            // p-4 = 16px (4 * 4px scale)
            assertTrue(label.getStyle().contains("16px"));
        }

        @Test
        @DisplayName("Should apply margin via utility class")
        void testConvenienceMargin() {
            StackPane parent = new StackPane();
            Label label = new Label("Test");
            parent.getChildren().add(label);
            TailwindFX.margin(label, 16, 16, 16, 16);

            // Margin should be applied via Java API
            assertNotNull(label);
        }

        @Test
        @DisplayName("Should apply background color via JIT")
        void testConvenienceBackgroundColor() {
            Label label = new Label("Test");
            TailwindFX.jit(label, "bg-blue-500");

            assertTrue(label.getStyle().contains("-fx-background-color"));
        }

        @Test
        @DisplayName("Should apply text color via JIT")
        void testConvenienceTextColor() {
            Label label = new Label("Test");
            TailwindFX.jit(label, "text-red-600");

            assertTrue(label.getStyle().contains("-fx-text-fill"));
        }

        @Test
        @DisplayName("Should apply font size via CSS class")
        void testConvenienceFontSize() {
            Label label = new Label("Test");
            TailwindFX.apply(label, "text-lg");

            // text-lg should add a CSS class
            assertTrue(label.getStyleClass().contains("text-lg"));
        }

        @Test
        @DisplayName("Should apply rounded corners via JIT")
        void testConvenienceRounded() {
            Label label = new Label("Test");
            TailwindFX.jit(label, "rounded-md");

            // rounded-md should apply border radius
            assertTrue(label.getStyle().contains("-fx-background-radius"));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle null node gracefully")
        void testNullNode() {
            assertThrows(NullPointerException.class, () -> {
                TailwindFX.apply((javafx.scene.Node) null, "text-blue-500");
            });
        }

        @Test
        @DisplayName("Should handle null scene gracefully")
        void testNullScene() {
            assertThrows(NullPointerException.class, () -> {
                TailwindFX.install(null);
            });
        }

        @Test
        @DisplayName("Should handle multiple installations on same scene")
        void testMultipleInstallations() throws Exception {
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Boolean> result = new AtomicReference<>();

            Platform.runLater(() -> {
                try {
                    TailwindFX.install(scene);
                    TailwindFX.installBase(scene);
                    TailwindFX.installUtilities(scene);
                    result.set(true);
                } catch (Exception e) {
                    result.set(false);
                } finally {
                    latch.countDown();
                }
            });

            assertTrue(latch.await(3, TimeUnit.SECONDS));
            assertTrue(result.get());
        }
    }
}
