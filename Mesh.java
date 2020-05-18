import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Mesh
{

	public List<Triangle> Tris = new ArrayList<Triangle>();
	public Matrix4x4 Transformation = Matrix4x4.Identity();

	// Creates an empty mesh
	public Mesh() {}

	// Creates a new mesh with Triangles tris
	public Mesh(Triangle[] tris)
	{
		Tris = Arrays.asList(tris);
	}

	// Clones Mesh m without transformation
	public Mesh(Mesh m)
	{
		for (int i = 0; i < m.Tris.size(); i++)
		{
			Tris.add(new Triangle(m.Tris.get(i)));
		}
	}

	// Tests if this mesh is equal to other mesh
	public boolean Equals(Mesh other)
	{
		return Tris.equals(other.Tris) && Transformation.Equals(other.Transformation);
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
		catch (FileNotFoundException e) // Unable to locate file -- return null
		{
			return null;
		}
		catch (IndexOutOfBoundsException e) // Invalid file format -- return null
		{
			return null;
		}
		catch (NumberFormatException e) // Invalid number format -- return null
		{
			return null;
		}
	}
}