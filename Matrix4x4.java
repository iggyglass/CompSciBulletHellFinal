import java.lang.Math;

public class Matrix4x4
{

    // Row, Col
    public float[][] M;

    // A static epsilon value used throughout code
    public static float Epsilon = 0.0001f;

	// Creates a new Matrix4x4 with values of 0
    public Matrix4x4()
    {
        M = new float[4][4];
    }

    // Creates a clone of Matrix4x4 m
    public Matrix4x4(Matrix4x4 m)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                M[i][j] = Float.valueOf(m.M[i][j]);
            }
        }
    }

    // Tests if this matrix is equal to other matrix
    public boolean Equals(Matrix4x4 other)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (M[i][j] != other.M[i][j]) return false;
            }
        }

        return true;
    }

	// Returns the result of multiplying vector v by this matrix
    public Vector3 MultiplyVector(Vector3 v)
    {
        Vector3 out = Vector3.Zero();

        out.X = v.X * M[0][0] + v.Y * M[1][0] + v.Z * M[2][0] + M[3][0];
        out.Y = v.X * M[0][1] + v.Y * M[1][1] + v.Z * M[2][1] + M[3][1];
        out.Z = v.X * M[0][2] + v.Y * M[1][2] + v.Z * M[2][2] + M[3][2];

        float w = v.X * M[0][3] + v.Y * M[1][3] + v.Z * M[2][3] + M[3][3];

        if (w != 0.0f)
        {
            out.X /= w;
            out.Y /= w;
            out.Z /= w;
        }
        
        return out;
    }

	// Returns the result of multiplying matrix m by this matrix
    public Matrix4x4 MultiplyMatrix(Matrix4x4 m)
    {
        Matrix4x4 mat = new Matrix4x4();

        for (int c = 0; c < 4; c++)
        {
            for (int r = 0; r < 4; r++)
            {
                mat.M[r][c] = M[r][0] * m.M[0][c] + M[r][1] * m.M[1][c] + M[r][2] * m.M[2][c] + M[r][3] * m.M[3][c];
            }
        }

        return mat;
    }

    // Creates an identity matrix
    public static Matrix4x4 Identity()
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = 1.0f;
        mat.M[1][1] = 1.0f;
        mat.M[2][2] = 1.0f;
        mat.M[3][3] = 1.0f;

        return mat;
    }

    // Creates a X rotation matrix at angle in radians
    public static Matrix4x4 RotationX(float angle)
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = 1.0f;
        mat.M[1][1] = (float)Math.cos(angle);
        mat.M[1][2] = (float)Math.sin(angle);
        mat.M[2][1] = -(float)Math.sin(angle);
        mat.M[2][2] = (float)Math.cos(angle);
        mat.M[3][3] = 1.0f;

        return mat;
    }

    // Creates a Y rotation matrix at angle in radians
    public static Matrix4x4 RotationY(float angle)
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = (float)Math.cos(angle);
        mat.M[0][2] = (float)Math.sin(angle);
        mat.M[2][0] = -(float)Math.sin(angle);
        mat.M[1][1] = 1.0f;
        mat.M[2][2] = (float)Math.cos(angle);
        mat.M[3][3] = 1.0f;

        return mat;
    }

    // Creates a Z rotation matrix at angle in radians
    public static Matrix4x4 RotationZ(float angle)
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = (float)Math.cos(angle);
        mat.M[0][1] = (float)Math.sin(angle);
        mat.M[1][0] = -(float)Math.sin(angle);
        mat.M[1][1] = (float)Math.cos(angle);
        mat.M[2][2] = 1.0f;
        mat.M[3][3] = 1.0f;

        return mat;
    }

    // Creates a translation matrix
    public static Matrix4x4 Translation(Vector3 v)
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = 1.0f;
        mat.M[1][1] = 1.0f;
        mat.M[2][2] = 1.0f;
        mat.M[3][3] = 1.0f;
        mat.M[3][0] = v.X;
        mat.M[3][1] = v.Y;
        mat.M[3][2] = v.Z;

        return mat;
    }

    // Creates a scaling matrix
    public static Matrix4x4 Scale(Vector3 v)
    {
        Matrix4x4 mat = new Matrix4x4();

        mat.M[0][0] = v.X;
        mat.M[1][1] = v.Y;
        mat.M[2][2] = v.Z;
        mat.M[3][3] = 1.0f;

        return mat;
    }

	// Creates a projection matrix given parameters
    public static Matrix4x4 Projection(float width, float height, float fov, float zNear, float zFar)
    {
        Matrix4x4 mat = new Matrix4x4();
        float aspectRatio = (float)height / (float)width;
        float fovRad = 1.0f / (float)Math.tan(fov * 0.5f / 180.0f * Math.PI);

        mat.M[0][0] = aspectRatio * fovRad;
        mat.M[1][1] = fovRad;
        mat.M[2][2] = zFar / (zFar - zNear);
        mat.M[3][2] = (-zFar * zNear) / (zFar - zNear);
        mat.M[2][3] = 1.0f;
        mat.M[3][3] = 0.0f;

        return mat;
    }

	// Converts Degrees to Radians
    public static float Deg2Rad(float degrees)
    {
        return degrees * (float)Math.PI / 180.0f;
    }

	// Converts Radians to Degrees
    public static float Rad2Deg(float radians)
    {
        return radians * 180.0f / (float)Math.PI;
    }

    // Linearly interpolates between values a and b given t
    public static float Lerp(float a, float b, float t)
    {
        return (a * (1.0f - t)) + (b * t);
    }
}