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
        float l = Magnitude();

        X /= l;
        Y /= l;
        Z /= l;
    }

    public float Magnitude()
    {
        return (float)Math.sqrt(X * X + Y * Y + Z * Z);
    }

    public Vector3 Cross(Vector3 v)
    {
        return new Vector3(Y * v.Z - Z * v.Y, Z * v.X - X * v.Z, X * v.Y - Y * v.X);
    }

    public float Dot(Vector3 v)
    {
        return X * v.X + Y * v.Y + Z * v.Z;
    }

    public Vector3 Add(Vector3 v)
    {
        return new Vector3(X + v.X, Y + v.Y, Z + v.Z);
    }

    public Vector3 Subtract(Vector3 v)
    {
        return new Vector3(X - v.X, Y - v.Y, Z - v.Z);
    }

    public Vector3 Multiply(Vector3 v)
    {
        return new Vector3(X * v.X, Y * v.Y, Z * v.Z);
    }

    public Vector3 Divide(Vector3 v)
    {
        return new Vector3(X / v.X, Y / v.Y, Z / v.Y);
    }

    public boolean Equals(Vector3 v)
    {
        return X == v.X && Y == v.Y && Z == v.Z;
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