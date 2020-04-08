public class Matrix4x4
{

    // Row, Col
    public float[][] M;

    public Matrix4x4()
    {
        M = new float[][]
        {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
    }

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
}