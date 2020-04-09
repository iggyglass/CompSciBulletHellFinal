import java.lang.Math;

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

    public void Normalize()
    {
        float l = (float)Math.sqrt(X * X + Y * Y + Z * Z);

        X /= l;
        Y /= l;
        Z /= l;
    }

    public Vector3 Cross(Vector3 v)
    {
        return new Vector3(Y * v.Z - Z * v.Y, Z * v.X - X * v.Z, X * v.Y - Y * v.X);
    }

    public Vector3 Minus(Vector3 v)
    {
        return new Vector3(X - v.X, Y - v.Y, Z - v.Z);
    }

    public float Dot(Vector3 v)
    {
        return X * v.X + Y * v.Y + Z * v.Z;
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