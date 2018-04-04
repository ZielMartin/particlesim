import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Particle extends BodyDef {
    private Body body;
    private Box2DProcessing box2DProcessing;

    private float width, height;

    public Particle(Box2DProcessing box2DProcessing) {
        this.box2DProcessing = box2DProcessing;
        this.width = 1.0f;
        this.height = 1.0f;

        this.width *= Options.SCALEFACTOR;
        this.height *= Options.SCALEFACTOR;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(new Vec2(5.0f, -5.0f));
        body = box2DProcessing.createBody(bodyDef);

//        body.setFixedRotation(true);

        PolygonShape box = new PolygonShape();
        box.setAsBox(this.width / 2, this.height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = .3f;


        body.createFixture(fixtureDef);

    }

    public void setPos(Vec2 pos) {
        body.setTransform(pos, body.getAngle());
    }


    public void draw(PApplet pApplet) {
        Vec2 pos = box2DProcessing.coordWorldToPixels(body.getPosition());
        pApplet.rectMode(PApplet.CENTER);
        pApplet.noStroke();
        pApplet.fill(255, 0, 0);
        pApplet.rect(pos.x, pos.y, this.width, this.height);

    }

    public Body getBody() {
        return this.body;
    }
}
