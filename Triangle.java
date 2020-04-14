public class Triangle
{

    public Vector3 Points[];
    public int Luminance = 0;

    public Triangle()
    {
        Points = new Vector3[3];

        Points[0] = Vector3.Zero();
        Points[1] = Vector3.Zero();
        Points[2] = Vector3.Zero();
    }

    public Triangle(Triangle other)
    {
        Points = new Vector3[3];

        Points[0] = new Vector3(other.Points[0]);
        Points[1] = new Vector3(other.Points[1]);
        Points[2] = new Vector3(other.Points[2]);
    }

    public Triangle(Vector3 a, Vector3 b, Vector3 c)
    {
        Points = new Vector3[3];

        Points[0] = a;
        Points[1] = b;
        Points[2] = c;
    }

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
}