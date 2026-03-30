package tailwindfx.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tailwindfx.TailwindFX;

/**
 * Dashboard Demo Application.
 * 
 * Run this to see TailwindFX capabilities in action.
 * 
 * Usage:
 *   mvn exec:java -Dexec.mainClass=tailwindfx.examples.DashboardApp
 */
public class DashboardApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();

        // Create dashboard content
        VBox dashboard = Dashboard.create();

        // Create demo controls panel
        VBox controls = Dashboard.createDemoControls();

        root.setCenter(dashboard);
        root.setRight(controls);
        BorderPane.setMargin(controls, new javafx.geometry.Insets(0, 0, 0, 16));

        // Scene with TailwindFX
        Scene scene = new Scene(root, 1200, 800);
        TailwindFX.install(scene);

        // Configure stage
        primaryStage.setTitle("TailwindFX — Dashboard Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Show dark mode toggle info
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     TailwindFX Dashboard Demo          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Features demonstrated:");
        System.out.println("  ✓ Layout builders (HBox, VBox, GridPane)");
        System.out.println("  ✓ Color utilities (bg-*, text-*)");
        System.out.println("  ✓ Spacing utilities (p-*, m-*, gap-*)");
        System.out.println("  ✓ Shadows and effects");
        System.out.println("  ✓ Components (cards, badges, buttons)");
        System.out.println("  ✓ Form elements (input, checkbox, choice)");
        System.out.println();
        System.out.println("To enable dark mode:");
        System.out.println("  root.getStyleClass().add(\"dark\");");
        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
