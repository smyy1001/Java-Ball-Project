import javax.media.j3d.*;
import javax.vecmath.*;

public class WireframeCube extends Shape3D {
    public WireframeCube(float size) {
        LineArray lineArray = new LineArray(24, LineArray.COORDINATES);

        // Define the 8 corners of the cube
        float[][] vertices = {
                { size, size, size },
                { -size, size, size },
                { -size, -size, size },
                { size, -size, size },
                { size, size, -size },
                { -size, size, -size },
                { -size, -size, -size },
                { size, -size, -size }
        };

        // Define the 12 edges of the cube
        int[][] edges = {
                { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 }, // Front face
                { 4, 5 }, { 5, 6 }, { 6, 7 }, { 7, 4 }, // Back face
                { 0, 4 }, { 1, 5 }, { 2, 6 }, { 3, 7 } // Connecting edges
        };

        // Add the coordinates for each edge
        for (int i = 0; i < edges.length; i++) {
            lineArray.setCoordinate(i * 2, new Point3f(vertices[edges[i][0]]));
            lineArray.setCoordinate(i * 2 + 1, new Point3f(vertices[edges[i][1]]));
        }

        // Set the geometry of this Shape3D object to the LineArray
        this.setGeometry(lineArray);
        this.setAppearance(new Appearance());
    }
}
