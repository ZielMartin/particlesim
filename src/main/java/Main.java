import javafx.scene.input.MouseButton;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.joints.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;
import shiffman.box2d.Box2DProcessing;

import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {
    private Box2DProcessing box2DProcessing;
    private long millis = -1;

    private Particle particle;
    private PFont f;

    private List<Particle> particles = new ArrayList<>();
    private List<MouseJoint> joints = new ArrayList<>();

    private Boundary boundary;


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
                    for(Particle p : particles) {

                        Vec2 position = p.getBody().getPosition();

                        Vec2 target = new Vec2(mouseX, mouseY);
                        target = box2DProcessing.coordPixelsToWorld(target);

                        Vec2 direction = target.sub(position);

                        direction.normalize();
                        direction.mulLocal(1000.0f);

                        p.getBody().applyForceToCenter(direction);
                    }
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

        System.out.println("creating particle at: " + mouseX + ";" + mouseY);

        Vec2 pos = box2DProcessing.coordPixelsToWorld(mouseX, mouseY);

        p.setPos(pos);
        particles.add(p);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        switch (event.getButton()) {
            case 37:
                //handled by draw
                break;
            case 3:
                particles.clear();
                box2DProcessing.createWorld();
                boundary = new Boundary(box2DProcessing, width / 2, height, width, 10);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        super.mouseReleased(event);

        System.out.println("released: " + event.getButton());
    }

    public static void main(String... args) {
        PApplet.main("Main");
    }

}
