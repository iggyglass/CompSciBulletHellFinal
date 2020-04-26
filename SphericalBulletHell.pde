import java.util.*;

// Note to self:
//   - Remember to focus on window before wondering
//     why input isnt working

Mesh mesh;
GameObject[] go = new GameObject[2];

Renderer rend;

Vector3 cameraPos = Vector3.Zero();
Vector3 lightPos = new Vector3(0, 0, -1);

float zNear = 0.1f;
float zFar = 1000.0f;
float fov = 90.0f;

long pFrameTime = 0;

float x = 0;
float y = 0;

float xRange = 5;
float yRange = 5;

void setup()
{
	size(1000, 1000);

	mesh = Mesh.LoadFromFile(sketchPath("sphere.obj"));
	rend = new Renderer(width, height, zNear, zFar, fov, lightPos, cameraPos);

	mesh.Transformation = Matrix4x4.Translation(new Vector3(0, 0, 5));

	go[0] = new GameObject(mesh, new Vector3(0, 0, 5), 1.5f);
	go[1] = new Asteroid(xRange, yRange, 5, 100, 1, 1.5f, mesh);

	lightPos.Normalize();
}

void draw()
{
	background(0);

	long currentTime = millis();
	long deltaTime = currentTime - pFrameTime;

	go[0].Mesh.Transformation = Matrix4x4.Translation(new Vector3(x, y, 5));
	go[1].Move(0.1f);

	List<Triangle> triRaster = rend.RenderMeshes(go);

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

void keyPressed()
{
	if (keyCode == LEFT && x > -xRange)
	{
		x -= 0.2f;
	}
	else if (keyCode == RIGHT && x < xRange)
	{
		x += 0.2f;
	}	

	if (keyCode == UP && y > -yRange)
	{
		y -= 0.2f;
	}
	else if (keyCode == DOWN && y < yRange)
	{
		y += 0.2f;
	}
}
