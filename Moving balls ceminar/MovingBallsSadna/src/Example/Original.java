package Example;

import javax.swing.border.LineBorder;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Original extends JApplet {
	public static void main(String[] args) {
		JFrame frame = new JFrame("MovingBalls");
		Original applet = new Original();
		applet.init();
		frame.add(applet, BorderLayout.CENTER);
		frame.add(applet, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

	public Original() {
		add(new BallControlTA());
	}
}

class BallControlTA extends JPanel {
	private JButton jbtSuspendUpL = new JButton("Suspend");
	private JButton jbtResumeUpL = new JButton("Resume");
	private JButton jbtAddUpL = new JButton("+1");
	private JButton jbtSubtractUpL = new JButton("-1");
	private JButton jbtSuspendDownL = new JButton("Suspend");
	private JButton jbtResumeDownL = new JButton("Resume");
	private JButton jbtAddDownL = new JButton("+1");
	private JButton jbtSubtractDownL = new JButton("-1");
	private JButton jbtSuspendUpR = new JButton("Suspend");
	private JButton jbtResumeUpR = new JButton("Resume");
	private JButton jbtAddUpR = new JButton("+1");
	private JButton jbtSubtractUpR = new JButton("-1");
	private JButton jbtSuspendDownR = new JButton("Suspend");
	private JButton jbtResumeDownR = new JButton("Resume");
	private JButton jbtAddDownR = new JButton("+1");
	private JButton jbtSubtractDownR = new JButton("-1");
	private JTextField leftUpNumOfBalls = new JTextField();
	private JTextField rightUpNumOfBalls = new JTextField();
	private JTextField leftDownNumOfBalls = new JTextField();
	private JTextField rightDownNumOfBalls = new JTextField();
	private JScrollBar jsbdelayLRUpDown = new JScrollBar();
	private JLabel jlbDelay = new JLabel("Delay time (ms): ");
	private JTextField delayTime = new JTextField();
	private JLabel jlbRadius = new JLabel("Radius (pixels): ");
	private JTextField radiusLength = new JTextField();
	private BallPanelTA ballPanel = new BallPanelTA(leftUpNumOfBalls, rightUpNumOfBalls, leftDownNumOfBalls,
			rightDownNumOfBalls, delayTime, radiusLength);
	private JPanel managerPanel = new JPanel();
	private JPanel scrollBarPanel = new JPanel();
	private JPanel speedAndRadius = new JPanel();

	public BallControlTA() {
		managerPanel.setLayout(new GridLayout(4, 7, 5, 5));
		managerPanel.add(new JLabel("Up Left Balls"));
		managerPanel.add(jbtSuspendUpL);
		managerPanel.add(jbtResumeUpL);
		managerPanel.add(jbtAddUpL);
		managerPanel.add(jbtSubtractUpL);
		managerPanel.add(new JLabel("number of Up Left Balls"));
		managerPanel.add(leftUpNumOfBalls);
		managerPanel.add(new JLabel("Up Right Balls"));
		managerPanel.add(jbtSuspendUpR);
		managerPanel.add(jbtResumeUpR);
		managerPanel.add(jbtAddUpR);
		managerPanel.add(jbtSubtractUpR);
		managerPanel.add(new JLabel("number of Up Right Balls"));
		managerPanel.add(rightUpNumOfBalls);
		managerPanel.add(new JLabel("Down Left Balls"));
		managerPanel.add(jbtSuspendDownL);
		managerPanel.add(jbtResumeDownL);
		managerPanel.add(jbtAddDownL);
		managerPanel.add(jbtSubtractDownL);
		managerPanel.add(new JLabel("number of Down Left Balls"));
		managerPanel.add(leftDownNumOfBalls);
		managerPanel.add(new JLabel("Down Right Balls"));
		managerPanel.add(jbtSuspendDownR);
		managerPanel.add(jbtResumeDownR);
		managerPanel.add(jbtAddDownR);
		managerPanel.add(jbtSubtractDownR);
		managerPanel.add(new JLabel("number of Down Right Balls"));
		managerPanel.add(rightDownNumOfBalls);
		jbtSuspendUpL.addActionListener(new Listener());
		jbtResumeUpL.addActionListener(new Listener());
		jbtAddUpL.addActionListener(new Listener());
		jbtSubtractUpL.addActionListener(new Listener());
		jbtSuspendDownL.addActionListener(new Listener());
		jbtResumeDownL.addActionListener(new Listener());
		jbtAddDownL.addActionListener(new Listener());
		jbtSubtractDownL.addActionListener(new Listener());
		ballPanel.setBorder(new LineBorder(Color.red));
		jsbdelayLRUpDown.setOrientation(JScrollBar.HORIZONTAL);
		ballPanel.setdelayLRUpDown(jsbdelayLRUpDown.getMaximum());
		speedAndRadius.setLayout(new GridLayout(1, 5, 5, 5));
		delayTime.setText("" + ballPanel.getdelayLRUpDown());
		radiusLength.setText("" + ballPanel.getRadius());
		speedAndRadius.add(jlbDelay);
		speedAndRadius.add(delayTime);
		speedAndRadius.add(new JLabel());
		speedAndRadius.add(jlbRadius);
		speedAndRadius.add(radiusLength);
		scrollBarPanel.setLayout(new GridLayout(3, 1, 5, 5));
		scrollBarPanel.add(new JLabel("Speed ScrollBar"));
		scrollBarPanel.add(jsbdelayLRUpDown);
		scrollBarPanel.add(speedAndRadius);
		setLayout(new BorderLayout());
		add(scrollBarPanel, BorderLayout.NORTH);
		add(ballPanel, BorderLayout.CENTER);
		add(managerPanel, BorderLayout.SOUTH);
		jsbdelayLRUpDown.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int rate = jsbdelayLRUpDown.getMaximum() - e.getValue();
				ballPanel.setdelayLRUpDown(rate);
				delayTime.setText("" + rate);
			}
		});
		jbtSuspendUpR.addActionListener(new Listener());
		jbtResumeUpR.addActionListener(new Listener());
		jbtAddUpR.addActionListener(new Listener());
		jbtSubtractUpR.addActionListener(new Listener());
		jbtSuspendDownR.addActionListener(new Listener());
		jbtResumeDownR.addActionListener(new Listener());
		jbtAddDownR.addActionListener(new Listener());
		jbtSubtractDownR.addActionListener(new Listener());
	}

	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
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
			}
		}
	}
}

class BallPanelTA extends JPanel {
	final static int PageDownAndUp = 10;
	final static int RadiosDownAndUp = 50;
	private JTextField numOfLeftUpBalls;
	private JTextField numOfRightUpBalls;
	private JTextField numOfLeftDownBalls;
	private JTextField numOfRightDownBalls;
	private JTextField radiusLength;
	private int delayLRUpDown = 100;
	private Timer timer = new Timer(delayLRUpDown, new PaintTimer());
	private ArrayList listUpL = new ArrayList();
	private ArrayList listUpR = new ArrayList();
	private ArrayList listDownL = new ArrayList();
	private ArrayList listDownR = new ArrayList();
	private int radius = 5;
	private Boolean listUpLFlag = true;
	private Boolean listUpRFlag = true;
	private Boolean listDownLFlag = true;
	private Boolean listDownRFlag = true;

	public BallPanelTA(JTextField numOfLeftUpBalls, JTextField numOfRightUpBalls, JTextField numOfLeftDownBalls,
			JTextField numOfRightDownBalls, JTextField delayTime, JTextField radiusLength) {
		this.numOfLeftUpBalls = numOfLeftUpBalls;
		this.numOfRightUpBalls = numOfRightUpBalls;
		this.numOfLeftDownBalls = numOfLeftDownBalls;
		this.numOfRightDownBalls = numOfRightDownBalls;
		this.radiusLength = radiusLength;
		timer.start();
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					addUpLeft();
					addUpRight();
					addDownLeft();
					addDownRight();
				} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
					for (int i = 0; i < (int) (Math.random() * PageDownAndUp); i++) {
						addUpLeft();
						addUpRight();
						addDownLeft();
						addDownRight();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					subtractUpLeft();
					subtractUpRight();
					subtractDownLeft();
					subtractDownRight();
				} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
					for (int i = 0; i < (int) (Math.random() * PageDownAndUp); i++) {
						subtractUpLeft();
						subtractUpRight();
						subtractDownLeft();
						subtractDownRight();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
					setRadius((int) (Math.random() * RadiosDownAndUp));
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					setRadius(getRadius() + 1);
				else if (e.getButton() == MouseEvent.BUTTON3)
					setRadius(getRadius() - 1);
			}
		});
	}

	public void addUpLeft() {
		listUpL.add(new SingleBallLRA(0, 0, 2, 2));
		setTextNumberOfBalls();
	}

	public void addDownLeft() {
		listDownL.add(new SingleBallLRA(0, getHeight(), 2, 2));
		setTextNumberOfBalls();
	}

	public void addUpRight() {
		listUpR.add(new SingleBallLRA(getWidth(), 0, 2, 2));
		setTextNumberOfBalls();
	}

	public void addDownRight() {
		listDownR.add(new SingleBallLRA(getWidth(), getHeight(), 2, 2));
		setTextNumberOfBalls();
	}

	public void subtractUpLeft() {
		if (listUpL.size() > 0)
			listUpL.remove(listUpL.size() - 1);
		setTextNumberOfBalls();
		repaint();
	}

	public void subtractDownLeft() {
		if (listDownL.size() > 0)
			listDownL.remove(listDownL.size() - 1);
		setTextNumberOfBalls();
		repaint();
	}

	public void subtractUpRight() {
		if (listUpR.size() > 0)
			listUpR.remove(listUpR.size() - 1);
		setTextNumberOfBalls();
		repaint();
	}

	public void subtractDownRight() {
		if (listDownR.size() > 0)
			listDownR.remove(listDownR.size() - 1);
		setTextNumberOfBalls();
		repaint();
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

	private void setTextRadius() {
		radiusLength.setText("" + getRadius());
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (listUpLFlag) {
			for (int i = 0; i < listUpL.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listUpL.get(i);
				g.setColor(ball.color);
				setXandY(ball);
				drawBall(g, ball);
			}
		}
		if (listUpRFlag) {
			for (int i = 0; i < listUpR.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listUpR.get(i);
				g.setColor(ball.color);
				setXandY(ball);
				drawBall(g, ball);
			}
		}
		if (listDownLFlag) {
			for (int i = 0; i < listDownL.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listDownL.get(i);
				g.setColor(ball.color);
				setXandY(ball);
				drawBall(g, ball);
			}
		}
		if (listDownRFlag) {
			for (int i = 0; i < listDownR.size(); i++) {
				SingleBallLRA ball = (SingleBallLRA) listDownR.get(i);
				g.setColor(ball.color);
				setXandY(ball);
				drawBall(g, ball);
			}
		}
		setANDrequestFocus();
	}

	private void setXandY(SingleBallLRA ball) {
		if (ball.x < radius)
			ball.dx = Math.abs(ball.dx);
		if (ball.x > getWidth() - radius)
			ball.dx = -Math.abs(ball.dx);
		if (ball.y < radius)
			ball.dy = Math.abs(ball.dy);
		if (ball.y > getHeight() - radius)
			ball.dy = -Math.abs(ball.dy);
		ball.x += ball.dx;
		ball.y += ball.dy;
	}

	private void drawBall(Graphics g, SingleBallLRA ball) {
		g.fillOval(ball.x - radius, ball.y - radius, radius * 2, radius * 2);
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
		timer.setDelay(delayLRUpDown);
	}

	public int getdelayLRUpDown() {
		return delayLRUpDown;
	}

	public void setRadius(int radius) {
		if (radius < 0)
			radius = 0;
		this.radius = radius;
		setTextRadius();
	}

	public int getRadius() {
		return radius;
	}

	private void setANDrequestFocus() {
		setFocusable(true);
		requestFocusInWindow();
	}

	private class PaintTimer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
}

class SingleBallLRA {
	int x,y,dx,dy;
	Color color = new Color((int) (Math.random() * 256),
			(int) (Math.random() * 256),
			(int) (Math.random() * 256));

	SingleBallLRA(int x, int y, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
}