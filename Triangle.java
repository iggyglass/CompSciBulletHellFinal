public class Triangle
{

    public Vector3 Points[];
    public int Luminance = 0;

	// Creates a new triangle
    public Triangle()
    {
        Points = new Vector3[3];

        Points[0] = Vector3.Zero();
        Points[1] = Vector3.Zero();
        Points[2] = Vector3.Zero();
    }

	// Deep clones given triangle
    public Triangle(Triangle other)
    {
        Points = new Vector3[3];

        Points[0] = new Vector3(other.Points[0]);
        Points[1] = new Vector3(other.Points[1]);
        Points[2] = new Vector3(other.Points[2]);

        Luminance = other.Luminance;
    }

	// Creates a new triangle with given points
    public Triangle(Vector3 a, Vector3 b, Vector3 c)
    {
        Points = new Vector3[3];

        Points[0] = a;
        Points[1] = b;
        Points[2] = c;
    }

	// Calculates the normal of this triangle
    public Vector3 CalculateNormal()
    {
        Vector3 lineA = Vector3.Zero();
        Vector3 lineB = Vector3.Zero();

        lineA.X = Points[1].X - Points[0].X;
        lineA.Y = Points[1].Y - Points[0].Y;
        lineA.Z = Points[1].Z - Points[0].Z;
        
        lineB.X = Points[2].X - Points[0].X;
        lineB.Y = Points[2].Y - Points[0].Y;
        lineB.Z = Points[2].Z - Points[0].Z;

        Vector3 normal = lineA.Cross(lineB);

        normal.Normalize();

        return normal;  
    }

    // Returns the center point of this triangle
    public Vector3 GetCenter()
    {
        float x = (Points[0].X + Points[1].X + Points[2].X) / 3.0f;
        float y = (Points[0].Y + Points[1].Y + Points[2].Y) / 3.0f;
        float z = (Points[0].Z + Points[1].Z + Points[2].Z) / 3.0f;

        return new Vector3(x, y, z);
    }

    // Returns whether this triangle is greater than other triangle t
    public boolean GreaterThan(Triangle t)
    {
        return GetCenter().Z > t.GetCenter().Z;
    }

    // Returns whether this triangle is less than other triangle t
    public boolean LessThan(Triangle t)
    {
        return GetCenter().Z < t.GetCenter().Z;
    }
}