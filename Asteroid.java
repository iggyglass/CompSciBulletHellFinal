import java.util.Random;

public class Asteroid extends GameObject
{

    private Random rand;
    private float xRange;
    private float yRange;
    private float startZ;
    private int startTickets;
    private int maxTickets;
    private float minZ;

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

    public void Restart()
    {
        float x = (float)(rand.nextInt(2 * Math.round(xRange)) - xRange);
        float y = (float)(rand.nextInt(2 * Math.round(yRange)) - yRange);

        Position = new Vector3(x, y, startZ);

        startTickets = rand.nextInt(maxTickets);

        updatePosition();
    }

    @Override
    public void Move(float speed)
    {
        if (startTickets > 0)
        {
            startTickets--;
            return;
        }

        Position.Z -= speed;

        if (Position.Z < minZ) Restart();
        else updatePosition();
    }

    private void updatePosition()
    {
        Mesh.Transformation = Matrix4x4.Translation(Position);
    }
}