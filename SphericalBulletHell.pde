import processing.sound.*;

Mesh mesh;
Mesh ship;
GameObject[] go = new GameObject[20];

Renderer rend;

Vector3 cameraPos = Vector3.Zero();
Vector3 lightPos = new Vector3(0, 0, -1);

float zNear = 0.1f;
float zFar = 1000.0f;
float fov = 90.0f;

float objRadius = 0.5f;

long pFrameTime = 0;

float x;
float y;

float velocityX = 0.0f;
float velocityY = 0.0f;

float xRange = 4;
float yRange = 4;

GameState state = GameState.Starting;

boolean upHeld = false;
boolean downHeld = false;
boolean rightHeld = false;
boolean leftHeld = false;

int blinkRate = 32;
boolean blinkState = false;

boolean filesLoaded = false;

int score;
int startFrame;
int scoreRate = 10;

float deltaVelocity = 0.05f;
float friction = 0.025f;
float maxSpeed = 0.2f;

PFont font;

SoundFile beep;
SoundFile select;
SoundFile explosion;

enum GameState
{
	Starting,
	InGame,
	Dead
}

void setup()
{
	size(1000, 1000);

	background(0);

	if (!filesLoaded)
	{
		mesh = Mesh.LoadFromFile(sketchPath("rock.obj"));
		ship = Mesh.LoadFromFile(sketchPath("ship.obj"));
		rend = new Renderer(width, height, zNear, zFar, fov, lightPos, cameraPos, 40.0f, 0.2f, 65535);

		font = createFont("font.ttf", 32);
		textFont(font);

		beep = new SoundFile(this, "beep.wav");
		select = new SoundFile(this, "select.wav");
		explosion = new SoundFile(this, "explosion.wav");

		filesLoaded = true;
	}

	x = 0;
	y = 0;

	score = 0;

	go[0] = new Ship(ship, new Vector3(0.2f, 0.2f, 0.2f), new Vector3(x, y, 5), objRadius, 45.0f, 10.0f);

	// Init asteroids
	for (int i = 1; i < go.length; i++)
	{
		go[i] = new Asteroid(xRange, yRange, 100, 200, 2, objRadius, mesh);
	}

	lightPos.Normalize();
}

void draw()
{
	background(0);

	if (frameCount % blinkRate == 0)
	{
		blinkState = !blinkState;

		if ((state == GameState.Starting || state == GameState.Dead) && blinkState) beep.play();
	}

	if (state == GameState.Starting)
	{
		fill(255, 255, 255);
		textAlign(CENTER, CENTER);
		textSize(64);
		text("Don't Crash", width / 2, height / 3);
		textSize(55);

		if (blinkState) text("Start", width / 2, height / 2);

		return;
	}
	else if (state == GameState.Dead)
	{
		fill(255, 255, 255);
		textAlign(CENTER, CENTER);
		textSize(64);
		text("Score: " + Integer.toString(score), width / 2, height / 3);
		textSize(55);

		if (blinkState) text("Restart", width / 2, height / 2);

		return;
	}

	long currentTime = millis();
	float deltaTime = (currentTime - pFrameTime) / 1000.0f;

	move();

	go[0].Move(new Vector3(x, y, 5), deltaTime);

	for (int i = 1; i < go.length; i++)
	{
		go[i].Move(new Vector3(1f), deltaTime);
	}

	TriangleHeap triRaster = rend.RenderMeshes(go);

	// Render triangles
	while (triRaster.GetSize() > 0)
	{
		Triangle current = triRaster.Pop();
		
		stroke(current.Luminance, current.Luminance, current.Luminance, 255);
		fill(current.Luminance, current.Luminance, current.Luminance, 255);

		triangle(current.Points[0].X, current.Points[0].Y, current.Points[1].X, current.Points[1].Y, current.Points[2].X, current.Points[2].Y);
	}

	drawDebug(go[0]);

	// Check collisions
	for (int i = 1; i < go.length; i++)
	{
		drawDebug(go[i]);
		if (go[i].IsColliding(go[0]))
		{
			explosion.play();
			state = GameState.Dead;
		}
	}

	// Update score
	score = (frameCount - startFrame) / scoreRate;

	// Draw Score
	fill(255, 255, 255);
	textSize(64);
	textAlign(CENTER, TOP);
	text(Integer.toString(score), width / 2, 0);

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
			velocityX -= deltaVelocity;
		}
		else if (rightHeld && x < xRange)
		{
			velocityX += deltaVelocity;
		}
	}

	if (upHeld ^ downHeld)
	{
		if (upHeld && y > -yRange)
		{
			velocityY -= deltaVelocity;
		}
		else if (downHeld && y < yRange)
		{
			velocityY += deltaVelocity;
		}
	}

	// Apply friction
	velocityX += velocityX < -Matrix4x4.Epsilon ? friction : velocityX > Matrix4x4.Epsilon ? -friction : 0f;
	velocityY += velocityY < -Matrix4x4.Epsilon ? friction : velocityY > Matrix4x4.Epsilon ? -friction : 0f;

	// Clamp velocity
	velocityX = clamp(velocityX, maxSpeed);
	velocityY = clamp(velocityY, maxSpeed);

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
				select.play();
			}
			else if (state == GameState.Dead)
			{
				state = GameState.InGame;
				startFrame = 0;
				frameCount = -1;
				select.play();
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
