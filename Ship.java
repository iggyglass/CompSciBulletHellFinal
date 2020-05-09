public final class Ship extends GameObject
{

    public Vector3 Scale;
    public Vector3 Rotation;

    private Vector3 pPos = Vector3.Zero();

    // Creates a new ship with given parameters
    public Ship(Mesh mesh, Vector3 scale, Vector3 position, float radius)
    {
        Scale = scale;
        Position = position;
        Rotation = Vector3.Zero();

        this.Mesh = mesh;
        this.Radius = radius;
    }

    // Moves the ship (akin to update() in unity)
    @Override
    public void Move(Vector3 pos, float t)
    {
        this.Position = pos;
        Vector3 tRot = Vector3.Zero();

        if (pPos.Y > pos.Y + 0.01f) tRot.X = Matrix4x4.Deg2Rad(45);
        else if (pPos.Y < pos.Y - 0.01f) tRot.X = Matrix4x4.Deg2Rad(-45);
        else tRot.X = 0;

        if (pPos.X > pos.X + 0.01f) tRot.Z = Matrix4x4.Deg2Rad(pPos.Y + 0.01f >= pos.Y ? -45 : 45);
        else if (pPos.X < pos.X - 0.01f) tRot.Z = Matrix4x4.Deg2Rad(pPos.Y + 0.01f >= pos.Y ? 45 : -45);
        else tRot.Z = 0;

        Mesh.Transformation = Matrix4x4.Scale(Scale).MultiplyMatrix(getRotation(tRot, t * 10.0f)).MultiplyMatrix(Matrix4x4.Translation(pos));

        pPos = pos;
    }

    // Returns rotation matrix given the target rotation and t
    private Matrix4x4 getRotation(Vector3 tRot, float t)
    {
        Vector3 v = Rotation.Lerp(tRot, t);

        Rotation = v;

        return Matrix4x4.RotationX(v.X).MultiplyMatrix(Matrix4x4.RotationY(v.Y)).MultiplyMatrix(Matrix4x4.RotationZ(v.Z));
    }
}