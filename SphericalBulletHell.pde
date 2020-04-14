import java.util.*;

Mesh mesh = Mesh.LoadFromFile("C:\\Users\\iggyg\\OneDrive\\Documents\\Processing\\SphericalBulletHell\\sphere.obj");
Matrix4x4 matProj;

Vector3 cameraPos = Vector3.Zero();
Vector3 lightPos = new Vector3(0, 1, -1);

float zNear = 0.1f;
float zFar = 1000.0f;
float fov = 90.0f;

// For testing
float theta = 0.0f;

long pFrameTime = 0;

void setup()
{
    size(1000, 1000);

    matProj = Matrix4x4.Projection(width, height, fov, zNear, zFar);

    lightPos.Normalize();
}

void draw()
{
    background(0);

    long currentTime = millis();
    long deltaTime = currentTime - pFrameTime;

    theta += 0.001f * (float)deltaTime;

    List<Triangle> triRaster = new ArrayList<Triangle>();

    // Draw Mesh
    for (int i = 0; i < mesh.Tris.size(); i++)
    {
        Triangle triProj = new Triangle();
        Triangle tri = new Triangle(mesh.Tris.get(i));

        Triangle triTrans = tri;

        // Stuff for testing 
        triTrans.Points[0].Z += sin(theta) + 3;
        triTrans.Points[1].Z += sin(theta) + 3;
        triTrans.Points[2].Z += sin(theta) + 3;
        triTrans.Points[0].Y += cos(theta);
        triTrans.Points[1].Y += cos(theta);
        triTrans.Points[2].Y += cos(theta);
        triTrans.Points[0].X += cos(theta) * 1.2f;
        triTrans.Points[1].X += cos(theta) * 1.2f;
        triTrans.Points[2].X += cos(theta) * 1.2f;

        // Calculate Normals
        Vector3 normal = triTrans.CalculateNormal();

        if (normal.Dot(triTrans.Points[0].Subtract(cameraPos)) < 0.0f)
        {
            // Lighting
            float lightDot = normal.Dot(lightPos);
            int col = (int)(lightDot * 255.0f);

            triProj.Luminance = col;

            // Project 3D => 2D
            triProj.Points[0] = matProj.MultiplyVector(triTrans.Points[0]);
            triProj.Points[1] = matProj.MultiplyVector(triTrans.Points[1]);
            triProj.Points[2] = matProj.MultiplyVector(triTrans.Points[2]);

            // Scale into view
            triProj.Points[0].X += 1.0f;
            triProj.Points[0].Y += 1.0f;
            triProj.Points[1].X += 1.0f;
            triProj.Points[1].Y += 1.0f;
            triProj.Points[2].X += 1.0f;
            triProj.Points[2].Y += 1.0f;

            triProj.Points[0].X *= 0.5f * (float)width;
            triProj.Points[0].Y *= 0.5f * (float)height;
            triProj.Points[1].X *= 0.5f * (float)width;
            triProj.Points[1].Y *= 0.5f * (float)height;
            triProj.Points[2].X *= 0.5f * (float)width;
            triProj.Points[2].Y *= 0.5f * (float)height;

            triRaster.add(triProj);
        }
    }

    // Sort triangles
    Collections.sort(triRaster, new SortTriangleByZ());

    // Render triangles
    for (int j = 0; j < triRaster.size(); j++)
    {
        Triangle current = triRaster.get(j);

        stroke(current.Luminance, current.Luminance, current.Luminance, 255);
        fill(current.Luminance, current.Luminance, current.Luminance, 255);

        triangle(current.Points[0].X, current.Points[0].Y, current.Points[1].X, current.Points[1].Y, current.Points[2].X, current.Points[2].Y);
    }

    pFrameTime = currentTime;
}
