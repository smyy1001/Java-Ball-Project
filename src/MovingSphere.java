import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.*;
import javax.vecmath.*;

public class MovingSphere {
    private TransformGroup tg;
    private Transform3D transform;
    private Vector3f position;
    private Vector3f velocity;
    private float radius;

    public MovingSphere(float radius, Vector3f initialPosition, Vector3f initialVelocity) {
        this.radius = radius;
        this.position = new Vector3f(initialPosition);
        this.velocity = new Vector3f(initialVelocity);

        transform = new Transform3D();
        transform.setTranslation(position);

        tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Create white appearance
        Appearance appearance = createWhiteAppearance();

        // Create the sphere with the white appearance
        Sphere sphere = new Sphere(radius, Sphere.GENERATE_NORMALS, appearance);
        tg.addChild(sphere);
        tg.setTransform(transform);
    }

    public TransformGroup getTransformGroup() {
        return tg;
    }

    public float getRadius() {
        return radius;
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    public void updatePosition() {
        position.add(velocity);
        transform.setTranslation(position);
        tg.setTransform(transform);
    }

    public void invertVelocityX() {
        velocity.x = -velocity.x;
    }

    public void invertVelocityY() {
        velocity.y = -velocity.y;
    }

    public void invertVelocityZ() {
        velocity.z = -velocity.z;
    }

    public boolean isCollidingWith(MovingSphere other) {
        Vector3f otherPos = other.getPosition();
        Vector3f difference = new Vector3f(position);
        difference.sub(otherPos);
        float distanceSquared = difference.lengthSquared();
        float radiiSum = this.radius + other.getRadius();
        return distanceSquared < (radiiSum * radiiSum);
    }

    public void handleCollision(MovingSphere other) {
        Vector3f temp = new Vector3f(velocity);
        velocity.set(other.velocity);
        other.velocity.set(temp);
    }

    // Helper method to create a white appearance for the sphere
    private Appearance createWhiteAppearance() {
        Appearance appearance = new Appearance();
        ColoringAttributes colorAttr = new ColoringAttributes();
        colorAttr.setColor(1.0f, 1.0f, 1.0f); // Set the color to white
        appearance.setColoringAttributes(colorAttr);
        return appearance;
    }
}
