public class Triangle
{

    public Vector3 Points[];

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
}