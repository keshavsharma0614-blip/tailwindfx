package tailwindfx.examples.animations;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tailwindfx.FxAnimation;

public class AnimationDemoApp extends Application {

    @Override
    public void start(Stage stage) {

        Button demoButton = new Button("Animate Me");

        Button fadeBtn = new Button("Fade In");
        fadeBtn.setOnAction(e -> FxAnimation.fadeIn(demoButton).play());

        Button slideBtn = new Button("Slide Up");
        slideBtn.setOnAction(e -> FxAnimation.slideUp(demoButton).play());

        Button scaleBtn = new Button("Scale In");
        scaleBtn.setOnAction(e -> FxAnimation.scaleIn(demoButton).play());

        Button rotateBtn = new Button("Spin");
        rotateBtn.setOnAction(e -> FxAnimation.spin(demoButton).play());

        Button bounceBtn = new Button("Bounce");
        bounceBtn.setOnAction(e -> FxAnimation.bounce(demoButton).play());

        VBox root = new VBox(15,
                demoButton,
                fadeBtn,
                slideBtn,
                scaleBtn,
                rotateBtn,
                bounceBtn
        );

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("TailwindFX Animation Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
