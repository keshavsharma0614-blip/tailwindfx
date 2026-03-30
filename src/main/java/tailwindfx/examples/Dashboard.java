package tailwindfx.examples;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tailwindfx.TailwindFX;
import tailwindfx.ComponentFactory;

/**
 * Dashboard Example — Shows TailwindFX capabilities.
 * 
 * Features demonstrated:
 * - Layout builders (HBox, VBox, GridPane)
 * - Color utilities (bg-*, text-*)
 * - Spacing utilities (p-*, m-*, gap-*)
 * - Shadow and effects
 * - Components (cards, badges, buttons, avatars)
 * - Dark mode support
 * - Responsive breakpoints
 */
public class Dashboard {

    public static VBox create() {
        // Main container with background and padding
        VBox dashboard = new VBox(16);
        dashboard.setPadding(new Insets(24));
        dashboard.setStyle("-fx-background-color: -color-gray-50;");

        // Header
        dashboard.getChildren().add(createHeader());

        // Stats row
        dashboard.getChildren().add(createStatsRow());

        // Main content area (charts + recent activity)
        HBox contentRow = new HBox(16);
        contentRow.getChildren().addAll(
            createChartSection(),
            createRecentActivity()
        );
        HBox.setHgrow(createChartSection(), Priority.ALWAYS);
        HBox.setHgrow(createRecentActivity(), Priority.ALWAYS);
        
        dashboard.getChildren().add(contentRow);

        // Install TailwindFX styles
        TailwindFX.install(dashboard.getScene());

        return dashboard;
    }

    // =========================================================================
    // Header Section
    // =========================================================================

    private static HBox createHeader() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("card");
        header.setPadding(new Insets(16, 24, 16, 24));

        // Avatar
        StackPane avatar = ComponentFactory.avatar("JD", "blue", 48);
        TailwindFX.apply(avatar, "rounded-full", "shadow-md");

        // Title and subtitle
        VBox text = new VBox(4);
        Label title = new Label("Welcome back, John!");
        TailwindFX.apply(title, "text-xl", "font-bold", "text-gray-900");

        Label subtitle = new Label("Here's what's happening today");
        TailwindFX.apply(subtitle, "text-sm", "text-gray-500");

        text.getChildren().addAll(title, subtitle);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Action buttons
        HBox actions = new HBox(8);
        Button primary = new Button("New Report");
        TailwindFX.apply(primary, "btn", "btn-primary");

        Button secondary = new Button("Export");
        TailwindFX.apply(secondary, "btn", "btn-secondary");

        actions.getChildren().addAll(primary, secondary);

        header.getChildren().addAll(avatar, text, spacer, actions);
        return header;
    }

    // =========================================================================
    // Stats Row
    // =========================================================================

    private static HBox createStatsRow() {
        HBox stats = new HBox(16);

        stats.getChildren().addAll(
            createStatCard("Total Revenue", "$45,231", "+20.1%", "green"),
            createStatCard("Active Users", "2,350", "+15.2%", "blue"),
            createStatCard("Bounce Rate", "12.5%", "-3.2%", "purple"),
            createStatCard("Avg. Session", "4m 32s", "+8.4%", "amber")
        );

        return stats;
    }

    private static StackPane createStatCard(String title, String value, String change, String color) {
        VBox card = new VBox(8);
        card.getStyleClass().addAll("card", "shadow-md");
        card.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label(title);
        TailwindFX.apply(titleLabel, "text-sm", "font-medium", "text-gray-500");

        // Value
        Label valueLabel = new Label(value);
        TailwindFX.apply(valueLabel, "text-2xl", "font-bold", "text-gray-900");

        // Change badge
        boolean isPositive = change.startsWith("+");
        Label changeLabel = new Label(change);
        TailwindFX.apply(changeLabel, "badge", 
            isPositive ? "badge-green" : "badge-red");

        card.getChildren().addAll(titleLabel, valueLabel, changeLabel);

        StackPane wrapper = new StackPane(card);
        HBox.setHgrow(wrapper, Priority.ALWAYS);
        return wrapper;
    }

    // =========================================================================
    // Chart Section
    // =========================================================================

    private static VBox createChartSection() {
        VBox section = new VBox(16);
        section.getStyleClass().add("card");
        section.setPadding(new Insets(20));

        // Section header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Revenue Overview");
        TailwindFX.apply(title, "text-lg", "font-semibold", "text-gray-900");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ComboBox<String> period = new ComboBox<>();
        period.getItems().addAll("Last 7 days", "Last 30 days", "Last 90 days");
        period.setValue("Last 7 days");
        TailwindFX.apply(period, "input", "input-sm");

        header.getChildren().addAll(title, spacer, period);

        // Chart placeholder (in real app, use JavaFX Chart)
        StackPane chartPlaceholder = new StackPane();
        chartPlaceholder.setMinHeight(280);
        chartPlaceholder.getStyleClass().addAll("bg-gray-100", "rounded-lg");
        chartPlaceholder.setAlignment(Pos.CENTER);

        Label chartLabel = new Label("📈 Chart Area\n(Use JavaFX LineChart/BarChart here)");
        chartLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        TailwindFX.apply(chartLabel, "text-gray-400");

        chartPlaceholder.getChildren().add(chartLabel);

        section.getChildren().addAll(header, chartPlaceholder);
        return section;
    }

    // =========================================================================
    // Recent Activity
    // =========================================================================

    private static VBox createRecentActivity() {
        VBox section = new VBox(16);
        section.getStyleClass().add("card");
        section.setPadding(new Insets(20));

        // Section header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Recent Activity");
        TailwindFX.apply(title, "text-lg", "font-semibold", "text-gray-900");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button viewAll = new Button("View All");
        TailwindFX.apply(viewAll, "btn", "btn-ghost", "btn-sm");

        header.getChildren().addAll(title, spacer, viewAll);

        // Activity list
        VBox activities = new VBox(12);

        activities.getChildren().addAll(
            createActivityItem("New user registered", "2 minutes ago", "green"),
            createActivityItem("Payment received", "15 minutes ago", "blue"),
            createActivityItem("Report generated", "1 hour ago", "purple"),
            createActivityItem("System update", "3 hours ago", "amber"),
            createActivityItem("Backup completed", "5 hours ago", "gray")
        );

        section.getChildren().addAll(header, activities);
        return section;
    }

    private static HBox createActivityItem(String activity, String time, String color) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);

        // Icon dot
        StackPane dot = new StackPane();
        dot.setPrefSize(10, 10);
        dot.getStyleClass().addAll("rounded-full");
        switch (color) {
            case "green": dot.setStyle("-fx-background-color: -color-green-500;"); break;
            case "blue": dot.setStyle("-fx-background-color: -color-blue-500;"); break;
            case "purple": dot.setStyle("-fx-background-color: -color-purple-500;"); break;
            case "amber": dot.setStyle("-fx-background-color: -color-amber-500;"); break;
            default: dot.setStyle("-fx-background-color: -color-gray-500;");
        }

        // Text
        VBox text = new VBox(2);
        Label activityLabel = new Label(activity);
        TailwindFX.apply(activityLabel, "text-sm", "font-medium", "text-gray-900");

        Label timeLabel = new Label(time);
        TailwindFX.apply(timeLabel, "text-xs", "text-gray-500");

        text.getChildren().addAll(activityLabel, timeLabel);

        item.getChildren().addAll(dot, text);
        return item;
    }

    // =========================================================================
    // Demo Controls
    // =========================================================================

    public static VBox createDemoControls() {
        VBox controls = new VBox(16);
        controls.getStyleClass().add("card");
        controls.setPadding(new Insets(20));

        Label title = new Label("UI Components Demo");
        TailwindFX.apply(title, "text-lg", "font-semibold", "text-gray-900");

        // Buttons
        HBox buttons = new HBox(8);
        buttons.setAlignment(Pos.CENTER_LEFT);

        Button primary = new Button("Primary");
        TailwindFX.apply(primary, "btn", "btn-primary");

        Button secondary = new Button("Secondary");
        TailwindFX.apply(secondary, "btn", "btn-secondary");

        Button success = new Button("Success");
        TailwindFX.apply(success, "btn", "btn-success");

        Button danger = new Button("Danger");
        TailwindFX.apply(danger, "btn", "btn-danger");

        Button warning = new Button("Warning");
        TailwindFX.apply(warning, "btn", "btn-warning");

        Button outline = new Button("Outline");
        TailwindFX.apply(outline, "btn", "btn-outline");

        buttons.getChildren().addAll(primary, secondary, success, danger, warning, outline);

        // Badges
        HBox badges = new HBox(8);
        badges.setAlignment(Pos.CENTER_LEFT);

        badges.getChildren().addAll(
            createBadge("Default", "gray"),
            createBadge("Blue", "blue"),
            createBadge("Green", "green"),
            createBadge("Red", "red"),
            createBadge("Yellow", "yellow"),
            createBadge("Purple", "purple")
        );

        // Form elements
        VBox form = new VBox(8);

        TextField input = new TextField();
        input.setPromptText("Enter your email...");
        TailwindFX.apply(input, "input");

        ChoiceBox<String> choice = new ChoiceBox<>();
        choice.getItems().addAll("Option 1", "Option 2", "Option 3");
        choice.setValue("Option 1");
        TailwindFX.apply(choice, "input");

        CheckBox checkbox = new CheckBox("I agree to the terms");
        TailwindFX.apply(checkbox, "text-sm");

        form.getChildren().addAll(input, choice, checkbox);

        controls.getChildren().addAll(title, buttons, badges, form);
        return controls;
    }

    private static Label createBadge(String text, String color) {
        Label badge = new Label(text);
        TailwindFX.apply(badge, "badge", "badge-" + color);
        return badge;
    }
}
