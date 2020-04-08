public class Vector3
{

    public float X;
    public float Y;
    public float Z;

    public Vector3(float x, float y, float z)
    {
        X = x;
        Y = y;
        Z = z;
    }

    public Vector3(Vector3 v)
    {
        X = Float.valueOf(v.X);
        Y = Float.valueOf(v.Y);
        Z = Float.valueOf(v.Z);
    }

    public static Vector3 Zero()
    {
        return new Vector3(0, 0, 0);
    }

    public static Vector3 One()
    {
        return new Vector3(1, 1, 1);
    }
}