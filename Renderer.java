import java.util.*;

public class Renderer
{

    public int Width;
    public int Height;
    public float ZNear;
    public float ZFar;
    public float Fov;
    public Vector3 LightPosition;
    public Vector3 CameraPosition;

    private Matrix4x4 matProj;

    public Renderer(int width, int height, float zNear, float zFar, float fov, Vector3 lightPosition, Vector3 cameraPosition)
    {
        Width = width;
        Height = height;
        ZNear = zNear;
        ZFar = zFar;
        Fov = fov;
        LightPosition = lightPosition;
        CameraPosition = cameraPosition;

        matProj = Matrix4x4.Projection(width, height, fov, zNear, zFar);
    }

    // Takes in list of Meshes and returns triangles to raster
    public List<Triangle> RenderMeshes(Mesh[] meshes)
    {
        List<Triangle> triRaster = new ArrayList<Triangle>();

        // Loop through Meshes
        for (int i = 0; i < meshes.length; i++)
        {
            Mesh mesh = meshes[i];

            // Render Mesh
            for (int j = 0; j < mesh.Tris.size(); j++)
            {
                Triangle triProj = new Triangle();
                Triangle tri = new Triangle(mesh.Tris.get(j));

                Triangle triTrans = tri;

                // Apply Transformation matrix of mesh
                triTrans.Points[0] = mesh.Transformation.MultiplyVector(tri.Points[0]);
                triTrans.Points[1] = mesh.Transformation.MultiplyVector(tri.Points[1]);
                triTrans.Points[2] = mesh.Transformation.MultiplyVector(tri.Points[2]);

                // Calculate Normals
                Vector3 normal = triTrans.CalculateNormal();

                if (normal.Dot(triTrans.Points[0].Subtract(CameraPosition)) < 0.0f)
                {
                    // Lighting
                    float lightDot = normal.Dot(LightPosition);
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

                    triProj.Points[0].X *= 0.5f * (float)Width;
                    triProj.Points[0].Y *= 0.5f * (float)Height;
                    triProj.Points[1].X *= 0.5f * (float)Width;
                    triProj.Points[1].Y *= 0.5f * (float)Height;
                    triProj.Points[2].X *= 0.5f * (float)Width;
                    triProj.Points[2].Y *= 0.5f * (float)Height;

                    triRaster.add(triProj);
                }
            }
        }

        // Sort triangles (Depth buffer hack)
        Collections.sort(triRaster, new SortTriangleByZ());

        return triRaster;
    }

    public void UpdateProjection()
    {
        matProj = Matrix4x4.Projection(Width, Height, Fov, ZNear, ZFar);
    }
}