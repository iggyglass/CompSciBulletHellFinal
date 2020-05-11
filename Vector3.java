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

	// Creates a vector3 with all components set to float v
	public Vector3(float v)
	{
		X = Float.valueOf(v);
		Y = Float.valueOf(v);
		Z = Float.valueOf(v);
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

	// Returns the distance between this vector and input vector v
	public float Distance(Vector3 v)
	{
		float dX = v.X - X;
		float dY = v.Y - Y;
		float dZ = v.Z - Z;

		return (float)Math.sqrt(dX * dX + dY * dY + dZ * dZ);
	}

	// Returns the distance squared between this vector and input vector v
	public float DistanceSquared(Vector3 v)
	{
		float dX = v.X - X;
		float dY = v.Y - Y;
		float dZ = v.Z - Z;

		return dX * dX + dY * dY + dZ * dZ;
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

	// Linearly interpolates between this vector3 and vector3 v given t
	public Vector3 Lerp(Vector3 v, float t)
	{
		float x = Matrix4x4.Lerp(X, v.X, t);
		float y = Matrix4x4.Lerp(Y, v.Y, t);
		float z = Matrix4x4.Lerp(Z, v.Z, t);

		return new Vector3(x, y, z);
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