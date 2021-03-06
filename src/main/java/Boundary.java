// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2012
// Box2DProcessing example

// A fixed boundary class

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

class Boundary {

    // A boundary is a simple rectangle with x,y,width,and height
    float x;
    float y;
    float w;
    float h;

    // But we also have to make a body for box2d to know about it
    Body b;

    public Boundary(Box2DProcessing box2d, float x_, float y_, float w_, float h_) {
        x = x_;
        y = y_;
        w = w_;
        h = h_;

        // Define the polygon
        PolygonShape sd = new PolygonShape();
        // Figure out the box2d coordinates
        float box2dW = box2d.scalarPixelsToWorld(w/2);
        float box2dH = box2d.scalarPixelsToWorld(h/2);
        // We're just a box
        sd.setAsBox(box2dW, box2dH);


        // Create the body
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position.set(box2d.coordPixelsToWorld(x,y));
        b = box2d.createBody(bd);

        // Attached the shape to the body using a Fixture
        b.createFixture(sd,1);
    }

    // Draw the boundary, if it were at an angle we'd have to do something fancier
    void display(PApplet pApplet) {
        pApplet.fill(0);
        pApplet.stroke(0);
        pApplet.rectMode(PApplet.CENTER);
        pApplet.rect(x,y,w,h);
    }

    public Body getBody() {
        return this.b;
    }

}