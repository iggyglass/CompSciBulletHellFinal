import java.lang.Math;

public class Vector3
{

	public float X;
	public float Y;
	public float Z;

	// Creates a vector3 with components x, y, z
	public Vector3(float x, float y, float z)
	{
		X = x;
		Y = y;
		Z = z;
	}

	// Clones the input vector3
	public Vector3(Vector3 v)
	{
		X = Float.valueOf(v.X);
		Y = Float.valueOf(v.Y);
		Z = Float.valueOf(v.Z);
	}

	// Normalizes this vector
	public void Normalize()
	{
		float l = Magnitude();

		X /= l;
		Y /= l;
		Z /= l;
	}

	// Returns the magnitude of this vector
	public float Magnitude()
	{
		return (float)Math.sqrt(X * X + Y * Y + Z * Z);
	}

	// Returns the cross product of this vector and input vector v
	public Vector3 Cross(Vector3 v)
	{
		return new Vector3(Y * v.Z - Z * v.Y, Z * v.X - X * v.Z, X * v.Y - Y * v.X);
	}

	// Returns the dot product of this vector and input vector v
	public float Dot(Vector3 v)
	{
		return X * v.X + Y * v.Y + Z * v.Z;
	}

	// Returns the result of adding this vector with input vector v
	public Vector3 Add(Vector3 v)
	{
		return new Vector3(X + v.X, Y + v.Y, Z + v.Z);
	}

	// Returns the result of subtracting input vector v from this vector
	public Vector3 Subtract(Vector3 v)
	{
		return new Vector3(X - v.X, Y - v.Y, Z - v.Z);
	}

	// Returns the result of multiplying this vector with input vector v
	public Vector3 Multiply(Vector3 v)
	{
		return new Vector3(X * v.X, Y * v.Y, Z * v.Z);
	}

	// Returns the result of dividing this vector by input vector v
	public Vector3 Divide(Vector3 v)
	{
		return new Vector3(X / v.X, Y / v.Y, Z / v.Y);
	}

	// Returns whether this vector and input vector v are equal;
	public boolean Equals(Vector3 v)
	{
		return X == v.X && Y == v.Y && Z == v.Z;
	}

	// Returns a new vector with 0 in all components
	public static Vector3 Zero()
	{
		return new Vector3(0, 0, 0);
	}

	// Returns a new vector with 1 in all components
	public static Vector3 One()
	{
		return new Vector3(1, 1, 1);
	}
}