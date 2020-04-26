public class GameObject
{

    public Mesh Mesh;
    public Vector3 Position;
    public float Radius;

    // Creates an empty GameObject
    public GameObject()
    {
        Mesh = new Mesh();
        Position = Vector3.Zero();
    }

    // Creates a GameObject with given parameters
    public GameObject(Mesh mesh, Vector3 position, float radius)
    {
        Mesh = mesh;
        Position = position;
        Radius = radius;
    }

    // Moves this GameObject (akin to update() in unity)
    public void Move(float f)
    {
        // If nothing else, apply position to transformation matrix
        Mesh.Transformation = Matrix4x4.Translation(Position);
    }

    // Checks if this GameObject is colliding with other GameObject
    public boolean IsColliding(GameObject other)
    {
        return Position.Distance(other.Position) < Radius + other.Radius;
    }
}