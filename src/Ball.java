import java.awt.*;
import java.util.Random;


public class Ball {
    private double x, y;
    private double velocityX = 0, velocityY = 0;
    private int diameter;
    private final double damping = 0.8;
    private final double friction = 0.999;
    private final Color color;
    private Rectangle box;


    public Ball(int x, int y, int diameter, Rectangle box) {
        this.x = x - diameter / 2;
        this.y = y - diameter / 2;
        this.diameter = diameter;
        this.box = box;
        this.color = generateRandomColor();
    }


    public void setBox(Rectangle newBox) {
        this.box = newBox;
    }



    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }


    public void move(double gravity) {
        velocityY += gravity;
        handleVerticalMovement();
        handleHorizontalMovement();
    }


    private void handleVerticalMovement() {
        if (y + velocityY > box.getMaxY() - diameter) {
            y = box.getMaxY() - diameter;
            velocityY = -velocityY * damping;
            velocityX *= friction;
        } else if (y + velocityY < box.getMinY()) {
            y = box.getMinY();
            velocityY = -velocityY * damping;
            velocityX *= friction;
        } else {
            y += velocityY;
        }
    }


    private void handleHorizontalMovement() {
        if (x + velocityX > box.getMaxX() - diameter) {
            x = box.getMaxX() - diameter;
            velocityX = -velocityX * friction;
        } else if (x + velocityX < box.getMinX()) {
            x = box.getMinX();
            velocityX = -velocityX * friction;
        } else {
            x += velocityX;
        }
    }


    public boolean isCollidingWith(Ball other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy) < diameter;
    }


    public void handleCollision(Ball other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double nx = dx / distance;
        double ny = dy / distance;
        double p1 = this.velocityX * nx + this.velocityY * ny;
        double p2 = other.velocityX * nx + other.velocityY * ny;
        double newP1 = p2;
        double newP2 = p1;
        this.velocityX = newP1 * nx + (this.velocityX - p1 * nx);
        this.velocityY = newP1 * ny + (this.velocityY - p1 * ny);
        other.velocityX = newP2 * nx + (other.velocityX - p2 * nx);
        other.velocityY = newP2 * ny + (other.velocityY - p2 * ny);
        double overlap = 0.5 * (diameter - distance + 1);
        this.x -= overlap * nx;
        this.y -= overlap * ny;
        other.x += overlap * nx;
        other.y += overlap * ny;
    }

    private Color generateRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r, g, b);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, diameter, diameter);
    }
}
