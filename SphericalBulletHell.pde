import java.util.*;

// Note to self:
//   - Remember to focus on window before wondering
//     why input isnt working

Mesh mesh;
Mesh ship;
GameObject[] go = new GameObject[10];

Renderer rend;

Vector3 cameraPos = Vector3.Zero();
Vector3 lightPos = new Vector3(0, 0, -1);

float zNear = 0.1f;
float zFar = 1000.0f;
float fov = 90.0f;

float objRadius = 1.1f;

long pFrameTime = 0;

float x;
float y;

float velocityX = 0.0f;
float velocityY = 0.0f;

float xRange = 5;
float yRange = 5;

GameState state = GameState.Starting;

boolean upHeld = false;
boolean downHeld = false;
boolean rightHeld = false;
boolean leftHeld = false;

int score;
int startFrame;
int scoreRate = 10;

enum GameState
{
	Starting,
	InGame,
	Dead
}

void setup()
{
	size(1000, 1000);

	mesh = Mesh.LoadFromFile(sketchPath("sphere.obj"));
	ship = Mesh.LoadFromFile(sketchPath("ship.obj"));
	rend = new Renderer(width, height, zNear, zFar, fov, lightPos, cameraPos);

	x = 0;
	y = 0;

	score = 0;

	go[0] = new Ship(ship, new Vector3(0.2f, 0.2f, 0.2f), new Vector3(x, y, 5), objRadius);

	// Init asteroids
	for (int i = 1; i < go.length; i++)
	{
		go[i] = new Asteroid(xRange, yRange, 100, 200, 1, objRadius, mesh);
	}

	lightPos.Normalize();
}

void draw()
{
	background(0);

	if (state == GameState.Starting)
	{
		fill(255, 255, 255);
		textAlign(CENTER, CENTER);
		textSize(64);
		text("Press Space to start", width / 2, height / 2);

		return;
	}
	else if (state == GameState.Dead)
	{
		fill(255, 255, 255);
		textAlign(CENTER, CENTER);
		textSize(64);
		text("You are dead", width / 2, height / 2);
		text("Score: " + Integer.toString(score), width / 2, height / 2 + 100);

		return;
	}

	long currentTime = millis();
	float deltaTime = (currentTime - pFrameTime) / 1000.0f;

	move();

	go[0].Move(new Vector3(x, y, 5), deltaTime);

	for (int i = 1; i < go.length; i++)
	{
		go[i].Move(new Vector3(0.5f), deltaTime);
	}

	List<Triangle> triRaster = rend.RenderMeshes(go);

	// Render triangles
	for (int j = 0; j < triRaster.size(); j++)
	{
		Triangle current = triRaster.get(j);

		stroke(current.Luminance, current.Luminance, current.Luminance, 255);
		fill(current.Luminance, current.Luminance, current.Luminance, 255);

		triangle(current.Points[0].X, current.Points[0].Y, current.Points[1].X, current.Points[1].Y, current.Points[2].X, current.Points[2].Y);
	}

	// Check collisions
	/*for (int i = 1; i < go.length; i++)
	{
		if (go[i].IsColliding(go[0])) state = GameState.Dead;
	}*/

	// Update score
	score = (frameCount - startFrame) / scoreRate;

	// Draw Score
	fill(255, 255, 255);
	textSize(64);
	textAlign(LEFT, TOP);
	text(Integer.toString(score), 0, 0);

	pFrameTime = currentTime;
}

// Handles key pressage
void keyPressed()
{
	handleInput(keyCode, true);
}

// Handles key releasage
void keyReleased()
{
	handleInput(keyCode, false);
}

// Moves player based off of current input
void move()
{
	if (leftHeld ^ rightHeld)
	{
		if (leftHeld && x > -xRange)
		{
			velocityX -= 0.05f;
		}
		else if (rightHeld && x < xRange)
		{
			velocityX += 0.05f;
		}
	}

	if (upHeld ^ downHeld)
	{
		if (upHeld && y > -yRange)
		{
			velocityY -= 0.05f;
		}
		else if (downHeld && y < yRange)
		{
			velocityY += 0.05f;
		}
	}

	// Apply friction
	velocityX += velocityX < -0.01f ? 0.025f : velocityX > 0.01f ? -0.025f : 0f;
	velocityY += velocityY < -0.01f ? 0.025f : velocityY > 0.01f ? -0.025f : 0f;

	// Clamp velocity
	velocityX = clamp(velocityX, 0.2f);
	velocityY = clamp(velocityY, 0.2f);

	// Apply velocity
	x += velocityX;
	y += velocityY;
}

// Clamps value to be between -m and +m
float clamp(float v, float m)
{
	return v < -m ? -m : v > m ? m : v;
}

// Handles an input interrupt
void handleInput(int k, boolean down)
{
	switch (k)
	{
		case UP:
			upHeld = down;
			break;
		case DOWN:
			downHeld = down;
			break;
		case LEFT:
			leftHeld = down;
			break;
		case RIGHT:
			rightHeld = down;
			break;
		case 32: // Spacebar
			if (state == GameState.Starting)
			{
				startFrame = frameCount;
				state = GameState.InGame;
			}
			else if (state == GameState.Dead)
			{
				state = GameState.InGame;
				startFrame = 0;
				frameCount = -1;
			}

			break;
		default:
			return;
	}
}

// Helps for debugging collisions -- renders colliders
void drawDebug(GameObject obj)
{
	fill(255, 0, 0);
	stroke(255, 0, 0);

	Vector3 p = rend.WorldToScreen(obj.Position);
	Vector3 q = rend.WorldToScreen(obj.Position.Add(new Vector3(obj.Radius, 0, 0)));

	circle(p.X, p.Y, 10);

	fill(0, 255, 0, 128);
	stroke(0, 255, 0, 128);

	circle(p.X, p.Y, p.X - q.X);
}