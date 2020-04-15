import java.util.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class Mesh
{

	public List<Triangle> Tris = new ArrayList<Triangle>();
	public Matrix4x4 Transformation = Matrix4x4.Identity();

	public Mesh() {}

	public Mesh(Triangle[] tris)
	{
		Tris = Arrays.asList(tris);
	}

	// Loads an object file (.obj) from path file then parses it
	// into a mesh. Returns Mesh on success and null on failure.
	public static Mesh LoadFromFile(String file)
	{
		try
		{
			File f = new File(file);
			Scanner reader = new Scanner(f);

			List<Vector3> verts = new ArrayList<Vector3>();
			Mesh mesh = new Mesh();

			while (reader.hasNextLine())
			{
				String line = reader.nextLine();

				// Remove excess whitespace
				line = line.trim().replaceAll("\t", "").replaceAll(" +", " ");

				String[] parts = line.split(" ");

				if (parts[0].endsWith("v")) // Vertex
				{
					Vector3 vec = new Vector3(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
					verts.add(vec);
				}
				else if (parts[0].endsWith("f")) // Face
				{  
					// We only need the face itself -- not the texture or normals
					int[] face = { Integer.parseInt(parts[1].split("/")[0]), Integer.parseInt(parts[2].split("/")[0]), Integer.parseInt(parts[3].split("/")[0]) };
					mesh.Tris.add(new Triangle(verts.get(face[0] - 1), verts.get(face[1] - 1), verts.get(face[2] - 1)));
				}
			}

			reader.close();
			
			return mesh;
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
		catch (IndexOutOfBoundsException e)
		{
			return null;
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
}