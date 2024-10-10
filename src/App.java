// import com.sun.j3d.utils.universe.SimpleUniverse;
// import com.sun.j3d.utils.geometry.Sphere;
// import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
// import javax.media.j3d.*;
// import javax.swing.*;
// import javax.vecmath.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.List;

// public class App extends JPanel {
//     private SimpleUniverse universe;
//     private Canvas3D canvas;
//     private TransformGroup rotateGroup;
//     private TransformGroup sphereGroup; // FIX: Declare sphereGroup
//     private List<MovingSphere> spheres;
//     private Timer physicsTimer;

//     public App() {
//         setLayout(new BorderLayout());
//         spheres = new ArrayList<>();
//         setup3DGraphics();
//         setupUI();
//         setupPhysics();
//     }

//     // private void setup3DGraphics() {
//     //     GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//     //     canvas = new Canvas3D(config);
//     //     add(canvas, BorderLayout.CENTER);

//     //     universe = new SimpleUniverse(canvas);
//     //     universe.getViewingPlatform().setNominalViewingTransform();

//     //     // Create a BranchGroup as the root of the scene
//     //     BranchGroup scene = createSceneGraph();
//     //     scene.compile();

//     //     // Add the BranchGroup to the SimpleUniverse
//     //     universe.addBranchGraph(scene);

//     //     // Mouse Rotation
//     //     MouseRotate behavior = new MouseRotate();
//     //     behavior.setTransformGroup(rotateGroup);
//     //     behavior.setSchedulingBounds(new BoundingSphere());
//     //     rotateGroup.addChild(behavior);
//     // }

//     // private BranchGroup createSceneGraph() {
//     //     // Create the root of the scene graph
//     //     BranchGroup objRoot = new BranchGroup();

//     //     // Create the rotatable wireframe cube
//     //     rotateGroup = new TransformGroup();
//     //     rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//     //     WireframeCube cube = new WireframeCube(0.5f);
//     //     rotateGroup.addChild(cube);
//     //     objRoot.addChild(rotateGroup); // Add the TransformGroup to the BranchGroup

//     //     // Create and add spheres to the scene
//     //     sphereGroup = new TransformGroup(); // FIX: Initialize the sphereGroup
//     //     sphereGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//     //     objRoot.addChild(sphereGroup); // Add sphereGroup to the root node (BranchGroup)

//     //     return objRoot;
//     // }

//     private void setup3DGraphics() {
//         GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//         canvas = new Canvas3D(config);
//         add(canvas, BorderLayout.CENTER);

//         universe = new SimpleUniverse(canvas);
//         universe.getViewingPlatform().setNominalViewingTransform();

//         // Create a BranchGroup as the root of the scene
//         BranchGroup scene = createSceneGraph();
//         scene.compile();

//         // Add the BranchGroup to the SimpleUniverse
//         universe.addBranchGraph(scene);

//         // Mouse Rotation
//         MouseRotate behavior = new MouseRotate(); // Create MouseRotate behavior
//         behavior.setTransformGroup(rotateGroup); // Apply it to rotateGroup
//         behavior.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0)); // Set bounds
//         rotateGroup.addChild(behavior); // Add behavior to rotateGroup
//     }

//     private BranchGroup createSceneGraph() {
//         // Create the root of the scene graph as a BranchGroup
//         BranchGroup objRoot = new BranchGroup();

//         // Create the rotatable wireframe cube
//         rotateGroup = new TransformGroup();
//         rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // Enable rotation
//         WireframeCube cube = new WireframeCube(0.5f);
//         rotateGroup.addChild(cube); // Add cube to rotateGroup

//         // Add the rotateGroup to the BranchGroup
//         objRoot.addChild(rotateGroup);

//         // Create and add spheres to the scene
//         sphereGroup = new TransformGroup(); // Create sphereGroup here
//         sphereGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

//         // Add sphereGroup to the BranchGroup
//         objRoot.addChild(sphereGroup); // Add sphereGroup to objRoot (BranchGroup)

//         return objRoot; // Return the BranchGroup as the root of the scene graph
//     }

//     private void setupUI() {
//         // Button to add spheres
//         JButton addSphereButton = new JButton("Add Sphere");
//         addSphereButton.addActionListener(e -> addSphere());
//         JPanel controlPanel = new JPanel();
//         controlPanel.add(addSphereButton);
//         add(controlPanel, BorderLayout.SOUTH);
//     }

//     private void addSphere() {
//         float radius = 0.05f;
//         MovingSphere movingSphere = new MovingSphere(radius, randomPosition(), new Vector3f(0.005f, 0.01f, 0.002f));
//         spheres.add(movingSphere);
//         sphereGroup.addChild(movingSphere.getTransformGroup()); // FIX: Add spheres to sphereGroup
//     }

//     private Vector3f randomPosition() {
//         // Generate a random position within the cube's bounds
//         float x = (float) (Math.random() * 0.4 - 0.2);
//         float y = (float) (Math.random() * 0.4 - 0.2);
//         float z = (float) (Math.random() * 0.4 - 0.2);
//         return new Vector3f(x, y, z);
//     }

//     private void setupPhysics() {
//         // Timer to update the positions of spheres (basic physics and collision
//         // detection)
//         physicsTimer = new Timer(16, e -> {
//             for (MovingSphere sphere : spheres) {
//                 sphere.updatePosition();
//                 checkWallCollision(sphere);
//             }
//             checkSphereCollisions();
//         });
//         physicsTimer.start();
//     }

//     private void checkWallCollision(MovingSphere sphere) {
//         Vector3f pos = sphere.getPosition();
//         float radius = sphere.getRadius();

//         if (pos.x - radius < -0.5f || pos.x + radius > 0.5f) {
//             sphere.invertVelocityX();
//         }
//         if (pos.y - radius < -0.5f || pos.y + radius > 0.5f) {
//             sphere.invertVelocityY();
//         }
//         if (pos.z - radius < -0.5f || pos.z + radius > 0.5f) {
//             sphere.invertVelocityZ();
//         }
//     }

//     private void checkSphereCollisions() {
//         for (int i = 0; i < spheres.size(); i++) {
//             for (int j = i + 1; j < spheres.size(); j++) {
//                 MovingSphere s1 = spheres.get(i);
//                 MovingSphere s2 = spheres.get(j);
//                 if (s1.isCollidingWith(s2)) {
//                     s1.handleCollision(s2);
//                 }
//             }
//         }
//     }

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Rotatable Wireframe Cube with Moving Spheres");
//         App app = new App();
//         frame.add(app);
//         frame.setSize(800, 600);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setVisible(true);
//     }
// }



import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class App extends JPanel {
    private SimpleUniverse universe;
    private Canvas3D canvas;
    private TransformGroup rotateGroup;
    private TransformGroup sphereGroup;
    private List<MovingSphere> spheres;
    private Timer physicsTimer;

    public App() {
        setLayout(new BorderLayout());
        spheres = new ArrayList<>();
        setup3DGraphics();
        setupUI();
        setupPhysics();
    }

    private void setup3DGraphics() {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        add(canvas, BorderLayout.CENTER);

        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        // Create a BranchGroup as the root of the scene
        BranchGroup scene = createSceneGraph(); // Returns a valid BranchGroup

        scene.compile();

        // Add the BranchGroup to the SimpleUniverse, not TransformGroup
        universe.addBranchGraph(scene); // This must be a BranchGroup, NOT a TransformGroup
    }

    // private BranchGroup createSceneGraph() {
    //     // Create the root of the scene graph as a BranchGroup
    //     BranchGroup objRoot = new BranchGroup();

    //     // Create the rotatable wireframe cube
    //     rotateGroup = new TransformGroup();
    //     rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    //     WireframeCube cube = new WireframeCube(0.5f);
    //     rotateGroup.addChild(cube);
    //     MouseRotate behavior = new MouseRotate();
    //     behavior.setTransformGroup(rotateGroup);
    //     behavior.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
    //     rotateGroup.addChild(behavior);

    //     // Add the rotateGroup to the BranchGroup
    //     objRoot.addChild(rotateGroup); // Ensure rotateGroup is added to objRoot (BranchGroup)

    //     // Create and add spheres to the scene
    //     sphereGroup = new TransformGroup();
    //     sphereGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    //     // Add sphereGroup to the BranchGroup
    //     objRoot.addChild(sphereGroup); // Add the sphereGroup to objRoot (BranchGroup)

    //     return objRoot; // Return the BranchGroup as the root of the scene graph
    // }

    private BranchGroup createSceneGraph() {
        // Create the root of the scene graph as a BranchGroup
        BranchGroup objRoot = new BranchGroup();

        // Create the rotatable wireframe cube
        rotateGroup = new TransformGroup();
        rotateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        WireframeCube cube = new WireframeCube(0.5f);
        rotateGroup.addChild(cube);

        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(rotateGroup);
        behavior.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
        rotateGroup.addChild(behavior);

        // Add the rotateGroup to the BranchGroup
        objRoot.addChild(rotateGroup);

        // Create and configure sphereGroup
        sphereGroup = new TransformGroup();
        sphereGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND); // Allow adding children dynamically
        sphereGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE); // Allow modification of children

        // MouseRotate behavior2 = new MouseRotate();
        // behavior2.setTransformGroup(sphereGroup);
        // behavior2.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
        // sphereGroup.addChild(behavior2);

        // Add sphereGroup to the BranchGroup
        objRoot.addChild(sphereGroup);

        return objRoot; // Return the BranchGroup as the root of the scene graph
    }

    private void setupUI() {
        // Button to add spheres
        JButton addSphereButton = new JButton("Add Sphere");
        addSphereButton.addActionListener(e -> addSphere());
        JPanel controlPanel = new JPanel();
        controlPanel.add(addSphereButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void addSphere() {
        float radius = 0.05f;
        MovingSphere movingSphere = new MovingSphere(radius, randomPosition(), new Vector3f(0.005f, 0.01f, 0.002f));
        spheres.add(movingSphere);

        // Wrap the TransformGroup of the MovingSphere in a BranchGroup
        BranchGroup sphereBranchGroup = new BranchGroup();
        sphereBranchGroup.addChild(movingSphere.getTransformGroup());

        // Add the BranchGroup (which contains the sphere's TransformGroup) to
        // sphereGroup
        sphereGroup.addChild(sphereBranchGroup);
    }

    private Vector3f randomPosition() {
        // Generate a random position within the cube's bounds
        float x = (float) (Math.random() * 0.4 - 0.2);
        float y = (float) (Math.random() * 0.4 - 0.2);
        float z = (float) (Math.random() * 0.4 - 0.2);
        return new Vector3f(x, y, z);
    }

    private void setupPhysics() {
        // Timer to update the positions of spheres (basic physics and collision
        // detection)
        physicsTimer = new Timer(16, e -> {
            for (MovingSphere sphere : spheres) {
                sphere.updatePosition();
                checkWallCollision(sphere);
            }
            checkSphereCollisions();
        });
        physicsTimer.start();
    }

    private void checkWallCollision(MovingSphere sphere) {
        Vector3f pos = sphere.getPosition();
        float radius = sphere.getRadius();

        if (pos.x - radius < -0.5f || pos.x + radius > 0.5f) {
            sphere.invertVelocityX();
        }
        if (pos.y - radius < -0.5f || pos.y + radius > 0.5f) {
            sphere.invertVelocityY();
        }
        if (pos.z - radius < -0.5f || pos.z + radius > 0.5f) {
            sphere.invertVelocityZ();
        }
    }

    private void checkSphereCollisions() {
        for (int i = 0; i < spheres.size(); i++) {
            for (int j = i + 1; j < spheres.size(); j++) {
                MovingSphere s1 = spheres.get(i);
                MovingSphere s2 = spheres.get(j);
                if (s1.isCollidingWith(s2)) {
                    s1.handleCollision(s2);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotatable Wireframe Cube with Moving Spheres");
        App app = new App();
        frame.add(app);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
