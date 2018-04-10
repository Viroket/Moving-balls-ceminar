import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

/**
 * This class provides a UI with JavaFx for displaying moving balls in a 3D
 * environment
 * 
 * 
 * <br>
 * Extends: {@link Application}
 * 
 * @author Ofer Hod & Tal Hananel
 * @version 1.1
 * @since JDK 1.8
 */
public class MovingBalls3D extends Application {
	private MediaPlayer mp;
	private static final int MOVE = 20;
	/**
	 * Main of <code>MovingBalls3D</code> starts the application
	 * 
	 * @param args
	 *            the command line arguments
	 * @see start
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Moving Balls 3D");
		init();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		BallControlTA ballControl = new BallControlTA();
		VBox pane = new VBox();

		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateY(920);
		camera.setTranslateX(820);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setFieldOfView(20);

		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(yRotate, new Translate(0, 0, -7020));
		
		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				yRotate.setAngle(event.getScreenX());
			}			
		});
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.W) {
					camera.setTranslateZ(camera.getTranslateZ() + MOVE*10);
				} else if (e.getCode() == KeyCode.S) {
					camera.setTranslateZ(camera.getTranslateZ() - MOVE*10);
				} else if (e.getCode() == KeyCode.D) {
					yRotate.setAngle(yRotate.getAngle() - MOVE);
				} else if (e.getCode() == KeyCode.A) {
					yRotate.setAngle(yRotate.getAngle() + MOVE);
				}
			}
		});
		Media media = new Media(new File(("Circus theme song.mp3")).toURI().toString());
		mp = new MediaPlayer(media);
		mp.setOnEndOfMedia(new Runnable() {
			public void run() {
				mp.seek(Duration.ZERO);
			}
		});
		mp.play();

		Group root = new Group();
		root.getChildren().add(camera);
		root.getChildren().add(ballControl.getBallPane());

		SubScene sub = new SubScene(root, 1000, 500, true, SceneAntialiasing.BALANCED);
		sub.setCamera(camera);
		sub.setFill(Color.TURQUOISE);
		sub.setHeight(720);

		pane.getChildren().add(ballControl.getScrollPane());
		pane.getChildren().add(sub);
		pane.getChildren().add(ballControl.getManagerPane());

		Scene scene = new Scene(pane, 1000, 1000);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}

/**
 * This class provides a UI and Controller for Moving Balls 3D
 * 
 * <br>
 * Extends: {@link Thread}
 * 
 * @author Ofer Hod & Tal Hananel
 * @version 1.1
 * @since JDK 1.8
 */
class BallControlTA extends BorderPane {
	private Button jbtSuspendUpL = new Button("Suspend");
	private Button jbtResumeUpL = new Button("Resume");
	private Button jbtTurnUpL = new Button("Turn");
	private Button jbtAddUpL = new Button("+1");
	private Button jbtSubtractUpL = new Button("-1");
	private Button jbtSuspendDownL = new Button("Suspend");
	private Button jbtResumeDownL = new Button("Resume");
	private Button jbtTurnDownL = new Button("Turn");
	private Button jbtAddDownL = new Button("+1");
	private Button jbtSubtractDownL = new Button("-1");
	private Button jbtSuspendUpR = new Button("Suspend");
	private Button jbtResumeUpR = new Button("Resume");
	private Button jbtTurnUpR = new Button("Turn");
	private Button jbtAddUpR = new Button("+1");
	private Button jbtSubtractUpR = new Button("-1");
	private Button jbtSuspendDownR = new Button("Suspend");
	private Button jbtResumeDownR = new Button("Resume");
	private Button jbtTurnDownR = new Button("Turn");
	private Button jbtAddDownR = new Button("+1");
	private Button jbtSubtractDownR = new Button("-1");
	private TextField leftUpNumOfBalls = new TextField();
	private TextField rightUpNumOfBalls = new TextField();
	private TextField leftDownNumOfBalls = new TextField();
	private TextField rightDownNumOfBalls = new TextField();
	private ScrollBar jsbdelayLRUpDown = new ScrollBar();
	private Label jlbDelay = new Label("Delay time (ms): ");
	private TextField delayTime = new TextField();
	private Button jbtRadiusRandom = new Button("Random");
	private Button jbtRadiusSet = new Button("Set");
	private Label jlbRadius = new Label(" Radius and Mass: ");
	private BallPanelTA ballPanel = new BallPanelTA(leftUpNumOfBalls, rightUpNumOfBalls, leftDownNumOfBalls,
			rightDownNumOfBalls, delayTime);
	private GridPane managerPanel = new GridPane();
	private GridPane scrollBarPanel = new GridPane();
	private GridPane speedAndRadius = new GridPane();
	private Button jbtEnableCollisions = new Button("On");
	private Button jbtDisableCollisions = new Button("Off");
	private TextField isCollision = new TextField("Disabled");

	public BallControlTA() {
		managerPanel.add(new Label("Collisions"), 0, 0);
		managerPanel.add(jbtEnableCollisions, 1, 0);
		managerPanel.add(jbtDisableCollisions, 2, 0);
		managerPanel.add(isCollision, 3, 0);

		managerPanel.add(new Label("Up Left Balls"), 0, 1);
		managerPanel.add(jbtSuspendUpL, 1, 1);
		managerPanel.add(jbtResumeUpL, 2, 1);
		managerPanel.add(jbtTurnUpL, 3, 1);
		managerPanel.add(jbtAddUpL, 4, 1);
		managerPanel.add(jbtSubtractUpL, 5, 1);
		managerPanel.add(new Label("number of Up Left Balls"), 6, 1);
		managerPanel.add(leftUpNumOfBalls, 7, 1);

		managerPanel.add(new Label("Up Right Balls"), 0, 2);
		managerPanel.add(jbtSuspendUpR, 1, 2);
		managerPanel.add(jbtResumeUpR, 2, 2);
		managerPanel.add(jbtTurnUpR, 3, 2);
		managerPanel.add(jbtAddUpR, 4, 2);
		managerPanel.add(jbtSubtractUpR, 5, 2);
		managerPanel.add(new Label("number of Up Right Balls"), 6, 2);
		managerPanel.add(rightUpNumOfBalls, 7, 2);

		managerPanel.add(new Label("Down Left Balls"), 0, 3);
		managerPanel.add(jbtSuspendDownL, 1, 3);
		managerPanel.add(jbtResumeDownL, 2, 3);
		managerPanel.add(jbtTurnDownL, 3, 3);
		managerPanel.add(jbtAddDownL, 4, 3);
		managerPanel.add(jbtSubtractDownL, 5, 3);
		managerPanel.add(new Label("number of Down Left Balls"), 6, 3);
		managerPanel.add(leftDownNumOfBalls, 7, 3);

		managerPanel.add(new Label("Down Right Balls"), 0, 4);
		managerPanel.add(jbtSuspendDownR, 1, 4);
		managerPanel.add(jbtResumeDownR, 2, 4);
		managerPanel.add(jbtTurnDownR, 3, 4);
		managerPanel.add(jbtAddDownR, 4, 4);
		managerPanel.add(jbtSubtractDownR, 5, 4);
		managerPanel.add(new Label("number of Down Right Balls"), 6, 4);
		managerPanel.add(rightDownNumOfBalls, 7, 4);

		managerPanel.setPrefSize(1000, 200);
		speedAndRadius.setPrefSize(1000, 100);
		scrollBarPanel.setPrefSize(1000, 100);

		jbtSuspendUpL.setOnAction(new Listener());
		jbtResumeUpL.setOnAction(new Listener());
		jbtAddUpL.setOnAction(new Listener());
		jbtSubtractUpL.setOnAction(new Listener());
		jbtSuspendDownL.setOnAction(new Listener());
		jbtResumeDownL.setOnAction(new Listener());
		jbtAddDownL.setOnAction(new Listener());
		jbtSubtractDownL.setOnAction(new Listener());
		jbtTurnDownL.setOnAction(new Listener());
		jbtTurnDownR.setOnAction(new Listener());
		jbtTurnUpL.setOnAction(new Listener());
		jbtTurnUpR.setOnAction(new Listener());
		jbtEnableCollisions.setOnAction(new Listener());
		jbtDisableCollisions.setOnAction(new Listener());

		jsbdelayLRUpDown.setOrientation(Orientation.HORIZONTAL);
		ballPanel.setdelayLRUpDown((int) jsbdelayLRUpDown.getMax());

		delayTime.setText("" + ballPanel.getdelayLRUpDown());

		speedAndRadius.add(jlbDelay, 0, 0);
		speedAndRadius.add(delayTime, 1, 0);
		speedAndRadius.add(new Label(), 2, 0);
		speedAndRadius.add(jlbRadius, 3, 0);
		speedAndRadius.add(jbtRadiusRandom, 4, 0);
		speedAndRadius.add(jbtRadiusSet, 5, 0);

		scrollBarPanel.add(new Label("Speed ScrollBar"), 0, 0);
		scrollBarPanel.add(jsbdelayLRUpDown, 0, 1);
		scrollBarPanel.add(speedAndRadius, 0, 2);

		

		jsbdelayLRUpDown.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int rate = (int) (jsbdelayLRUpDown.getMax() - jsbdelayLRUpDown.getValue());
				ballPanel.setdelayLRUpDown(rate);
				delayTime.setText("" + rate);
			}
		});
		jsbdelayLRUpDown.setValue(80);

		jbtSuspendUpR.setOnAction(new Listener());
		jbtResumeUpR.setOnAction(new Listener());
		jbtAddUpR.setOnAction(new Listener());
		jbtSubtractUpR.setOnAction(new Listener());
		jbtSuspendDownR.setOnAction(new Listener());
		jbtResumeDownR.setOnAction(new Listener());
		jbtAddDownR.setOnAction(new Listener());
		jbtSubtractDownR.setOnAction(new Listener());
		jbtRadiusRandom.setOnAction(new Listener());
		jbtRadiusSet.setOnAction(new Listener());
	}

	public Pane getScrollPane() {
		return scrollBarPanel;
	}

	public Pane getBallPane() {
		return ballPanel;
	}

	public Pane getManagerPane() {
		return managerPanel;
	}

	/**
	 * This class provides an EventHandler for Moving Balls 3D. allows user to
	 * add balls,rotate them,suspend and more
	 * 
	 * <br>
	 * Extends: {@link Thread}
	 * 
	 * @author Ofer Hod & Tal Hananel
	 * @version 1.1
	 * @since JDK 1.8
	 */
	class Listener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (e.getSource() == jbtSuspendUpL) {
				ballPanel.suspendUpLeft();
			} else if (e.getSource() == jbtResumeUpL) {
				ballPanel.resumeUpLeft();
			} else if (e.getSource() == jbtAddUpL) {
				ballPanel.addUpLeft();
			} else if (e.getSource() == jbtSubtractUpL) {
				ballPanel.subtractUpLeft();
			} else if (e.getSource() == jbtSuspendUpR) {
				ballPanel.suspendUpRight();
			} else if (e.getSource() == jbtResumeUpR) {
				ballPanel.resumeUpRight();
			} else if (e.getSource() == jbtAddUpR) {
				ballPanel.addUpRight();
			} else if (e.getSource() == jbtSubtractUpR) {
				ballPanel.subtractUpRight();
			} else if (e.getSource() == jbtSuspendDownL) {
				ballPanel.suspendDownLeft();
			} else if (e.getSource() == jbtResumeDownL) {
				ballPanel.resumeDownLeft();
			} else if (e.getSource() == jbtAddDownL) {
				ballPanel.addDownLeft();
			} else if (e.getSource() == jbtSubtractDownL) {
				ballPanel.subtractDownLeft();
			} else if (e.getSource() == jbtSuspendDownR) {
				ballPanel.suspendDownRight();
			} else if (e.getSource() == jbtResumeDownR) {
				ballPanel.resumeDownRight();
			} else if (e.getSource() == jbtAddDownR) {
				ballPanel.addDownRight();
			} else if (e.getSource() == jbtSubtractDownR) {
				ballPanel.subtractDownRight();
			} else if (e.getSource() == jbtTurnUpL) {
				ballPanel.turnUpLeft();
			} else if (e.getSource() == jbtTurnUpR) {
				ballPanel.turnUpRight();
			} else if (e.getSource() == jbtTurnDownL) {
				ballPanel.turnDownLeft();
			} else if (e.getSource() == jbtTurnDownR) {
				ballPanel.turnDownRight();
			} else if (e.getSource() == jbtEnableCollisions) {
				ballPanel.setCollisionsOn();
				isCollision.setText("Enabled");
			} else if (e.getSource() == jbtDisableCollisions) {
				ballPanel.setCollisionsOff();
				isCollision.setText("Disabled");
			} else if(e.getSource() ==  jbtRadiusRandom){
				ballPanel.setRadiusRandom();
			}else if(e.getSource() == jbtRadiusSet){
				ballPanel.setRadiusSet();
			}
		}
	}
}

/**
 * This class provides a UI and Logic for Moving Balls 3D
 * 
 * <br>
 * Extends: {@link Thread}
 * 
 * @author Ofer Hod & Tal Hananel
 * @version 1.1
 * @since JDK 1.8
 */
class BallPanelTA extends Pane {
	final static int PageDownAndUp = 10;
	final static int RadiosDownAndUp = 200;
	private TextField numOfLeftUpBalls;
	private TextField numOfRightUpBalls;
	private TextField numOfLeftDownBalls;
	private TextField numOfRightDownBalls;
	private int delayLRUpDown = 50;
	private ArrayList<SingleBallLRA> listUpL = new ArrayList<>();
	private ArrayList<SingleBallLRA> listUpR = new ArrayList<>();
	private ArrayList<SingleBallLRA> listDownL = new ArrayList<>();
	private ArrayList<SingleBallLRA> listDownR = new ArrayList<>();
	private Boolean listUpLFlag = true;
	private Boolean listUpRFlag = true;
	private Boolean listDownLFlag = true;
	private Boolean listDownRFlag = true;
	private Timeline timer = new Timeline();
	private KeyFrame keyFrame;
	private Group ballGroup = new Group();
	private Boolean Collisions = false;

	public BallPanelTA(TextField numOfLeftUpBalls, TextField numOfRightUpBalls, TextField numOfLeftDownBalls,
			TextField numOfRightDownBalls, TextField delayTime) {
		this.numOfLeftUpBalls = numOfLeftUpBalls;
		this.numOfRightUpBalls = numOfRightUpBalls;
		this.numOfLeftDownBalls = numOfLeftDownBalls;
		this.numOfRightDownBalls = numOfRightDownBalls;
		setPrefHeight(2200);
		setMaxHeight(2200);
		setPrefWidth(3200);
		setMaxWidth(3200);

		keyFrame = new KeyFrame(Duration.millis(delayLRUpDown), e -> paintComponent());
		timer = new Timeline();
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.getKeyFrames().add(keyFrame);
		timer.play();

		PointLight light = new PointLight();
		light.setColor(Color.WHITE);

		Group lightGroup = new Group();
		lightGroup.getChildren().add(light);

		getChildren().add(lightGroup);
		getChildren().add(ballGroup);

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.UP) {
					addUpLeft();
					addUpRight();
					addDownLeft();
					addDownRight();
				} else if (e.getCode() == KeyCode.PAGE_UP) {
					for (int i = 0; i < (int) (Math.random() * PageDownAndUp); i++) {
						addUpLeft();
						addUpRight();
						addDownLeft();
						addDownRight();
					}
				} else if (e.getCode() == KeyCode.DOWN) {
					subtractUpLeft();
					subtractUpRight();
					subtractDownLeft();
					subtractDownRight();
				} else if (e.getCode() == KeyCode.PAGE_DOWN) {
					for (int i = 0; i < (int) (Math.random() * PageDownAndUp); i++) {
						subtractUpLeft();
						subtractUpRight();
						subtractDownLeft();
						subtractDownRight();
					}
				}
			}
		});
	}

	public void setRadiusSet() {
		SingleBallLRA.randomMassRad = false;
		for(int i = 0 ; i < ballGroup.getChildren().size() ; i++){
			((SingleBallLRA)ballGroup.getChildren().get(i)).setMassRad();
		}
	}

	public void setRadiusRandom() {
		SingleBallLRA.randomMassRad = true;
		for(int i = 0 ; i < ballGroup.getChildren().size() ; i++){
			((SingleBallLRA)ballGroup.getChildren().get(i)).setMassRad();
		}
	}

	public void setCollisionsOff() {
		Collisions = false;
	}

	public void setCollisionsOn() {
		Collisions = true;
	}

	public void turnDownRight() {
		for (SingleBallLRA ball : listDownR) {
			ball.turn();
		}
		requestLayout();
	}

	public void turnDownLeft() {
		for (SingleBallLRA ball : listDownL) {
			ball.turn();
		}
		requestLayout();
	}

	public void turnUpRight() {
		for (SingleBallLRA ball : listUpR) {
			ball.turn();
		}
		requestLayout();
	}

	public void turnUpLeft() {
		for (SingleBallLRA ball : listUpL) {
			ball.turn();
		}
		requestLayout();
	}

	public void addUpLeft() {
		listUpL.add(new SingleBallLRA(0, 0, 0, 2, 2, 2));
		setTextNumberOfBalls();
	}

	public void addDownLeft() {
		listDownL.add(new SingleBallLRA(0, (int) getHeight(), 100, 2, 2, 2));
		setTextNumberOfBalls();
	}

	public void addUpRight() {
		listUpR.add(new SingleBallLRA((int) getWidth(), -100, 0, 2, 2, 2));
		setTextNumberOfBalls();
	}

	public void addDownRight() {
		listDownR.add(new SingleBallLRA((int) getWidth(), (int) getHeight(), 0, 2, 2, 2));
		setTextNumberOfBalls();
	}

	public void subtractUpLeft() {
		if (listUpL.size() > 0)
			listUpL.remove(listUpL.size() - 1);
		setTextNumberOfBalls();
		requestLayout();
	}

	public void subtractDownLeft() {
		if (listDownL.size() > 0)
			listDownL.remove(listDownL.size() - 1);
		setTextNumberOfBalls();
		requestLayout();
	}

	public void subtractUpRight() {
		if (listUpR.size() > 0)
			listUpR.remove(listUpR.size() - 1);
		setTextNumberOfBalls();
		requestLayout();
	}

	public void subtractDownRight() {
		if (listDownR.size() > 0)
			listDownR.remove(listDownR.size() - 1);
		setTextNumberOfBalls();
		requestLayout();
	}

	private void setTextNumberOfBalls() {
		if (listUpL != null)
			numOfLeftUpBalls.setText("" + listUpL.size());
		if (listUpR != null)
			numOfRightUpBalls.setText("" + listUpR.size());
		if (listDownL != null)
			numOfLeftDownBalls.setText("" + listDownL.size());
		if (listDownR != null)
			numOfRightDownBalls.setText("" + listDownR.size());
	}

	/**
	 * Paints the spheres on the pane
	 * 
	 * @return void
	 */
	protected void paintComponent() {
		if (Collisions) {
			for (int i = 0; i < ballGroup.getChildren().size(); i++) {
				((SingleBallLRA) ballGroup.getChildren().get(i)).checkCollision(ballGroup);
			}
		}
		if (!ballGroup.getChildren().isEmpty()) {
			ballGroup.getChildren().clear();
		}
		if (listUpLFlag) {
			for (int i = 0; i < listUpL.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listUpL.get(i);
				setXandYandZ(ball);
				drawBall(ball);
			}
		}
		if (listUpRFlag) {
			for (int i = 0; i < listUpR.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listUpR.get(i);
				setXandYandZ(ball);
				drawBall(ball);
			}
		}
		if (listDownLFlag) {
			for (int i = 0; i < listDownL.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listDownL.get(i);
				setXandYandZ(ball);
				drawBall(ball);
			}
		}
		if (listDownRFlag) {
			for (int i = 0; i < listDownR.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listDownR.get(i);
				setXandYandZ(ball);
				drawBall(ball);
			}
		}
		setANDrequestFocus();
	}

	/**
	 * Moves the Spheres in 3D vectors
	 * 
	 * @return void
	 */
	private void setXandYandZ(SingleBallLRA ball) {
		if (ball.pos.getX() < ball.getRadius())
			ball.dx = Math.abs(ball.dx);
		if (ball.pos.getX() > getWidth() - ball.getRadius())
			ball.dx = -Math.abs(ball.dx);

		if (ball.pos.getY() < ball.getRadius())
			ball.dy = Math.abs(ball.dy);
		if (ball.pos.getY() > getHeight() - ball.getRadius())
			ball.dy = -Math.abs(ball.dy);

		if (ball.pos.getZ() < ball.getRadius())
			ball.dz = Math.abs(ball.dz);
		if (ball.pos.getZ() > getMaxWidth() - ball.getRadius())
			ball.dz = -Math.abs(ball.dz);

		ball.pos = ball.pos.add(ball.dx, ball.dy, ball.dz);
	}

	/**
	 * draws a sphere and adds it to the ball group
	 * 
	 * @return void
	 */
	private void drawBall(SingleBallLRA ball) {
		ball.setTranslateX(ball.pos.getX());
		ball.setTranslateY(ball.pos.getY());
		ball.setTranslateZ(ball.pos.getZ());
		ballGroup.getChildren().add(ball);
	}

	public void suspendUpLeft() {
		listUpLFlag = false;
	}

	public void resumeUpLeft() {
		listUpLFlag = true;
	}

	public void suspendUpRight() {
		listUpRFlag = false;
	}

	public void resumeUpRight() {
		listUpRFlag = true;
	}

	public void suspendDownLeft() {
		listDownLFlag = false;
	}

	public void resumeDownLeft() {
		listDownLFlag = true;
	}

	public void suspendDownRight() {
		listDownRFlag = false;
	}

	public void resumeDownRight() {
		listDownRFlag = true;
	}

	public void setdelayLRUpDown(int delayLRUpDown) {
		this.delayLRUpDown = delayLRUpDown;
		timer.setRate(delayLRUpDown);
	}

	public int getdelayLRUpDown() {
		return delayLRUpDown;
	}

	private void setANDrequestFocus() {
		setFocused(true);
		requestFocus();
	}
}

/**
 * This class provides custom Spheres for Moving Balls 3D
 * 
 * <br>
 * Extends: {@link Thread}
 * 
 * @author Ofer Hod & Tal Hananel
 * @version 1.1
 * @since JDK 1.8
 */
class SingleBallLRA extends Sphere {
	private static final int SPHERE_RADIUS = 130;
	private static final int SPHERE_MASS = 10;
	private static final String DIFFUSE_MAP = "file:earth_gebco8_texture_8192x4096.jpg";
	private static final String NORMAL_MAP = "file:earth_normalmap_flat_8192x4096.jpg";
	private static final String SPECULAR_MAP = "file:earth_specularmap_flat_8192x4096.jpg";
	public static boolean randomMassRad = false;
	double dx,dy,dz,mass;
	private boolean isRotate = true;
	private RotateTransition rotate;
	private Color color = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	private PhongMaterial material;
	Point3D pos;
	
	SingleBallLRA(int x, int y, int z, int dx, int dy, int dz) {
		setMassRad();
		material = new PhongMaterial(color);
		material.setDiffuseMap(new Image(DIFFUSE_MAP, true));
		material.setBumpMap(new Image(NORMAL_MAP, true));
		material.setSpecularMap(new Image(SPECULAR_MAP, true));
		setMaterial(material);
		pos = new Point3D(x, y, z);
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		initRotation();
	}
	/**
	 * initiates the rotation of the sphere
	 * 
	 * @return void
	 */
	private void initRotation(){
		rotate = new RotateTransition(Duration.seconds(5), SingleBallLRA.this);
		rotate.setAxis(Rotate.Y_AXIS);
		rotate.setFromAngle(0);
		rotate.setToAngle(360);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(RotateTransition.INDEFINITE);
		rotate.play();
	}
	/**
	 * sets the rotation of the sphere clockwise or anti-clockwise
	 * 
	 * @return void
	 */
	public void turn() {
		if (!isRotate) {
			rotate.setFromAngle(0);
			rotate.setToAngle(360);
			rotate.playFromStart();
			isRotate = true;
		} else {
			rotate.setFromAngle(360);
			rotate.setToAngle(0);
			rotate.playFromStart();
			isRotate = false;
		}
	}
	public void setMassRad(){
		if(randomMassRad){
			mass = (Math.random() * 10) + 5;
			setRadius(mass * 12);
		}else{
			mass = SPHERE_MASS;
			setRadius(SPHERE_RADIUS);
		}
	}
	/**
	 * handles collisions with other spheres
	 * 
	 * @return void
	 */
	public void checkCollision(Group g) {
		for (int i = 0; i < g.getChildren().size(); i++) {
			SingleBallLRA sphere = (SingleBallLRA) g.getChildren().get(i);
			if (this != sphere && checkSphereCollision(sphere)) {
				dx = -((dx * 2 * mass) + (2 * sphere.mass * sphere.dx)) / (mass + sphere.mass) +dx;
				dy = -((dy * 2 * mass) + (2 * sphere.mass * sphere.dy)) / (mass + sphere.mass) +dy;
				dz = -((dz * 2 * mass) + (2 * sphere.mass * sphere.dz)) / (mass + sphere.mass) +dz;
			}
		}
	}
	/**
	 * checks if a collision has occurred between two spheres
	 * 
	 * @return void
	 */
	private boolean checkSphereCollision(SingleBallLRA sphere) {
		double newX = pos.getX() - sphere.pos.getX();
		double newY = pos.getY() - sphere.pos.getY();
		double newZ = pos.getZ() - sphere.pos.getZ();

		double distance = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2) + Math.pow(newZ, 2));
		return distance < (sphere.getRadius() + getRadius());
	}
}