public class GameObject
{

    public Mesh Mesh;
    public Vector3 Position;
    public float Radius;

    public GameObject()
    {
        Mesh = new Mesh();
        Position = Vector3.Zero();
    }

    public GameObject(Mesh mesh, Vector3 position, float radius)
    {
        Mesh = mesh;
        Position = position;
        Radius = radius;
    }

    public void Move(float f)
    {
        // If nothing else, apply position to transformation matrix
        Mesh.Transformation = Matrix4x4.Translation(Position);
    }

    public boolean IsColliding(GameObject other)
    {
        return Position.Distance(other.Position) < Radius + other.Radius;
    }
}