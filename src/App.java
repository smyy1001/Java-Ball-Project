import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.awt.event.*;
import java.util.ArrayList;


public class App extends JPanel {

    private List<Ball> balls = new ArrayList<>();
    private final double gravity = 0.005;
    private Rectangle box;
    private Timer createBallsTimer;
    private volatile int currentX, currentY;
    private int ballDiameter = 50;


    public App() {
        setPreferredSize(new Dimension(500, 500));
        calculateBoxSize();
        setupMouseHandlers();
        setupComponentListener();
        startAnimationTimer();
    }


    private void setupMouseHandlers() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            public void mouseReleased(MouseEvent e) {
                handleMouseReleased();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                updateCurrentMousePosition(e);
            }
        });
    }


    private void setupComponentListener() {
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                recalculateBoxSize();
            }
        });
    }


    private void startAnimationTimer() {
        Timer timer = new Timer(1, e -> animateBalls());
        timer.start();
    }


    private void handleMousePressed(MouseEvent e) {
        if (box.contains(e.getX(), e.getY())) {
            currentX = e.getX();
            currentY = e.getY();
            startCreatingBalls();
        }
    }


    private void handleMouseReleased() {
        if (createBallsTimer != null) {
            createBallsTimer.stop();
        }
    }


    private void updateCurrentMousePosition(MouseEvent e) {
        if (box.contains(e.getX(), e.getY())) {
            currentX = e.getX();
            currentY = e.getY();
        }
    }


    public void updateBallDiameter(int newDiameter) {
        this.ballDiameter = newDiameter;
        for (Ball ball : balls) {
            ball.setDiameter(newDiameter);
        }
        repaint();
    }


    private void startCreatingBalls() {
        createBallsTimer = new Timer(50, ev -> {
            balls.add(new Ball(currentX, currentY, ballDiameter, box));
            repaint();
        });
        createBallsTimer.start();
    }


    private void animateBalls() {
        moveBalls();
        checkCollisions();
        repaint();
    }


    private void recalculateBoxSize() {
        calculateBoxSize();
        for (Ball ball : balls) {
            ball.setBox(box);
        }
    }


    private void calculateBoxSize() {
        int margin = 50;
        int boxWidth = getWidth() - 2 * margin;
        int boxHeight = getHeight() - 2 * margin;
        int x = margin;
        int y = margin;
        box = new Rectangle(x, y, boxWidth, boxHeight);
    }


    private void moveBalls() {
        for (Ball ball : balls) {
            ball.move(gravity);
        }
    }


    private void checkCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);
                if (ball1.isCollidingWith(ball2)) {
                    ball1.handleCollision(ball2);
                }
            }
        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(box.x, box.y, box.width, box.height);
        for (Ball ball : balls) {
            ball.draw(g);
        }
    }

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Top Projesi");
        App app = new App();

        JSlider slider = new JSlider(10, 100, 50);
        slider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int newDiameter = source.getValue();
                app.updateBallDiameter(newDiameter);
            }
        });
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JLabel("Ball Size: "));
        menuBar.add(slider);
        frame.setJMenuBar(menuBar);

        frame.add(app);
        frame.setSize(500, 500);        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
