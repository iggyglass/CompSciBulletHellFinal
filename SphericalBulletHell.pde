import java.util.*;

Mesh mesh;

Renderer rend;

Vector3 cameraPos = Vector3.Zero();
Vector3 lightPos = new Vector3(0, 1, -1);

float zNear = 0.1f;
float zFar = 1000.0f;
float fov = 90.0f;

long pFrameTime = 0;

float theta = 0f;

void setup()
{
	size(1000, 1000);

	mesh = Mesh.LoadFromFile(sketchPath("sphere.obj"));
	rend = new Renderer(width, height, zNear, zFar, fov, lightPos, cameraPos);

	mesh.Transformation = Matrix4x4.Translation(new Vector3(0, 0, 5));

	lightPos.Normalize();
}

void draw()
{
	background(0);

	long currentTime = millis();
	long deltaTime = currentTime - pFrameTime;

	theta += (float)deltaTime * 0.01f;

	mesh.Transformation = Matrix4x4.RotationX(Matrix4x4.Deg2Rad(theta)).MultiplyMatrix(Matrix4x4.Translation(new Vector3(0, 0, 5)));

	List<Triangle> triRaster = rend.RenderMeshes(new Mesh[] { mesh });

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
