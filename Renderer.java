import java.util.*;
import java.awt.image.BufferedImage;

public class Renderer
{

	public int Width;
	public int Height;
	public float ZNear;
	public float ZFar;
	public float Fov;
	public Vector3 LightPosition;
	public Vector3 CameraPosition;

	public BufferedImage Screen;

	private Matrix4x4 matProj;
	private BufferedImage blankScreen;

	// Initalizes a new renderer given parameters
	public Renderer(int width, int height, float zNear, float zFar, float fov, Vector3 lightPosition, Vector3 cameraPosition)
	{
		Width = width;
		Height = height;
		ZNear = zNear;
		ZFar = zFar;
		Fov = fov;
		LightPosition = lightPosition;
		CameraPosition = cameraPosition;

		Screen = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);

		matProj = Matrix4x4.Projection(width, height, fov, zNear, zFar);
		blankScreen = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
	}

	// Takes in list of Meshes and rasterizes them onto ScreenBuffer
	public void RenderMeshes(Mesh[] meshes)
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

		for (int i = 0; i < triRaster.size(); i++)
		{
			Triangle t = triRaster.get(i);

			Color color = new Color(t.Luminance, t.Luminance, t.Luminance);

			int x1 = Math.round(t.Points[0].X);
			int y1 = Math.round(t.Points[0].Y);

			int x2 = Math.round(t.Points[1].X);
			int y2 = Math.round(t.Points[1].Y);

			int x3 = Math.round(t.Points[2].X);
			int y3 = Math.round(t.Points[2].Y);

			RenderTriangle(x1, y1, x2, y2, x3, y3, color);
		}
	}

	// Updates the projection matrix for if something (e.g. light position) has changed
	public void UpdateProjection()
	{
		matProj = Matrix4x4.Projection(Width, Height, Fov, ZNear, ZFar);
	}

	// Clears the screen buffer
	public void CleanScreen()
	{
		Screen.setData(blankScreen.getRaster());
	}

	// Draws triangle onto screen buffer
	public void RenderTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color)
	{

		// Fun Fact: Triangles are annoying to draw

		if (y2 < y1)
		{
			// Since java is pass by value, this swap method is odd
			// syntactically as it uses some java wizardry
			y2 = swap(y1, y1 = y2);
			x2 = swap(x1, x1 = x2);
		}

		if (y3 < y1)
		{
			y3 = swap(y1, y1 = y3);
			x3 = swap(x1, x1 = x3);
		}

		if (y3 < y2)
		{
			y3 = swap(y2, y2 = y3);
			x3 = swap(x2, x2 = x3);
		}

		int dy1 = y2 - y1;
		int dx1 = x2 - x1;
		int dy2 = y3 - y1;
		int dx2 = x3 - x1;

		float daxStep = 0.0f;
		float dbxStep = 0.0f;

		if (dy1 != 0) daxStep = dx1 / (float)Math.abs(dy1);
		if (dy2 != 0) dbxStep = dx2 / (float)Math.abs(dy2);

		if (dy1 != 0)
		{
			for (int i = y1; i <= y2; i++)
			{
				int ax = Math.round(x1 + (float)(i - y1) * daxStep);
				int bx = Math.round(x1 + (float)(i - y1) * dbxStep);

				if (ax > bx) bx = swap(ax, ax = bx);

				for (int j = ax; j < bx; j++)
				{
					int p = (0xFF << 24) | (((int)color.R) << 16) | (((int)color.G) << 8) | ((int)color.B);

					Screen.setRGB(j, i, p);
				}
			}

			dy1 = y3 - y2;
			dx1 = x3 - x2;

			if (dy1 != 0) daxStep = dx1 / (float)Math.abs(dy1);
			if (dy2 != 0) dbxStep = dx2 / (float)Math.abs(dy2);

			for (int i = y2; i <= y3; i++)
			{
				int ax = Math.round(x2 + (float)(i - y2) * daxStep);
				int bx = Math.round(x1 + (float)(i - y1) * dbxStep);

				if (ax > bx) bx = swap(ax, ax = bx);

				for (int j = ax; j < bx; j++)
				{
					int p = (0xFF << 24) | (((int)color.R) << 16) | (((int)color.G) << 8) | ((int)color.B);

					Screen.setRGB(j, i, p);
				}
			}
		}
	}

	// Used in the swapping of elements a and b.
	// Usage: b = swap(a, a = b);
	private int swap(int a, int b)
	{
		return a;
	}
}