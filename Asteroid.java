import java.util.Random;

public final class Asteroid extends GameObject
{

    public Vector3 Rotation;

    private Random rand;
    private float xRange;
    private float yRange;
    private float startZ;
    private int startTickets;
    private int maxTickets;
    private float minZ;

    // Creates a new asteroid with given parameters
    public Asteroid(float xRange, float yRange, float startZ, int maxTickets, float minZ, float radius, Mesh mesh)
    {
        super();

        Mesh = new Mesh(mesh);
        Radius = radius;

        this.xRange = xRange;
        this.yRange = yRange;
        this.startZ = startZ;
        this.maxTickets = maxTickets;
        this.minZ = minZ;

        rand = new Random();

        Restart();
    }

    // Resets this asteroid
    public void Restart()
    {
        float x = randomRange(-xRange, xRange);
        float y = randomRange(-yRange, yRange);

        Position = new Vector3(x, y, startZ);
        Rotation = randomRotation();

        startTickets = rand.nextInt(maxTickets);

        updatePosition();
    }

    // Moves this asteroid (akin to update() in unity)
    @Override
    public void Move(Vector3 speed, float t)
    {
        if (startTickets > 0)
        {
            startTickets--;
            return;
        }

        Position.Z -= speed.X;

        if (Position.Z < minZ) Restart();
        else updatePosition();
    }

    // Updates the transformation to align with the actual position of asteroid
    private void updatePosition()
    {
        Mesh.Transformation = getRotation().MultiplyMatrix(Matrix4x4.Translation(Position));
    }

    // Returns a random float within range 
    private float randomRange(float min, float max)
    {
        return rand.nextFloat() * (max - min) + min;
    }

    // Returns a random rotation
    private Vector3 randomRotation()
    {
        float x = Matrix4x4.Deg2Rad(randomRange(0f, 359f));
        float y = Matrix4x4.Deg2Rad(randomRange(0f, 359f));
        float z = Matrix4x4.Deg2Rad(randomRange(0f, 359f));

        return new Vector3(x, y, z);
    }

    // Returns the rotation matrix for this object
    public Matrix4x4 getRotation()
    {
        return Matrix4x4.RotationX(Rotation.X).MultiplyMatrix(Matrix4x4.RotationY(Rotation.Y)).MultiplyMatrix(Matrix4x4.RotationZ(Rotation.Z));
    }
}