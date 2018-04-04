import javafx.scene.input.MouseButton;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import processing.core.PApplet;
import processing.core.PFont;
import shiffman.box2d.Box2DProcessing;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {
    private Box2DProcessing box2DProcessing;
    private long millis = -1;

    private Particle particle;
    private PFont f;

    List<Particle> particles = new ArrayList<>();

    Boundary boundary;


    @Override
    public void settings() {
        size(800, 600);

        box2DProcessing = new Box2DProcessing(this);
        box2DProcessing.scaleFactor = 1;
        box2DProcessing.createWorld();

        boundary = new Boundary(box2DProcessing, width / 2, height, width, 10);
    }

    @Override
    public void setup() {
        super.setup();
        f = createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on
        this.millis = millis();
    }

    @Override
    public void draw() {
        long passedTime = millis() - this.millis;
        this.millis += passedTime;
        background(255);
        noStroke();
        fill(0);

        textFont(f, 12);
        fill(0);
        int fps = (int) (1000.0f / passedTime);
        String fpsstring = "FPS: " + fps;

        text(fpsstring, 5, 25);
        float timeStep = passedTime / 1000.0f;

        if (mousePressed) {
            switch (mouseButton) {
                case 37:
                    createParticle();
                    break;
                case 39:
                    for (int i = 0; i < 50; i++)
                        createParticle();
                    break;
                case 3:
                    particles.clear();
                    box2DProcessing.createWorld();
                    boundary = new Boundary(box2DProcessing, width / 2, height, width, 10);
                    break;
            }
        }

        box2DProcessing.step(timeStep, 10, 8);

        boundary.display(this);

        fill(255, 0, 0);

        for (Particle p : particles)
            p.draw(this);
    }

    private void createParticle() {
        Particle p = new Particle(box2DProcessing);

        Vec2 pos = box2DProcessing.coordPixelsToWorld(mouseX, mouseY);

        p.setPos(pos);
        particles.add(p);
    }

    public static void main(String... args) {
        PApplet.main("Main");
    }

}
