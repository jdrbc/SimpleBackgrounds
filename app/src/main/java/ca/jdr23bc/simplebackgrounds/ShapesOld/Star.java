package ca.jdr23bc.simplebackgrounds.ShapesOld;

import android.graphics.Canvas;

public class Star extends Shape {
    float radius;
    float centerX;
    float centerY;
    int numPoints;
    float strokeWidth;
    float rotationStep;
    int skip;

    public Star(int width, int height) {
        super(width, height);
        this.radius = width / 2;
        this.centerX = random.nextInt(width);
        this.centerY = random.nextInt(height);
        this.numPoints = Math.max(7, random.nextInt(250));
        this.rotationStep = (float) (2*Math.PI/numPoints);
        this.skip = (numPoints / 4) + random.nextInt(numPoints / 2);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(colorScheme.getRandom());
        paint.setStrokeWidth(strokeWidth);

        int count = 0;
        int stepCount = 1;
        float currAngle = (float) (Math.PI/4);
        float currX = (float) (Math.cos(currAngle) * radius) + centerX;
        float currY = (float) (Math.sin(currAngle) * radius) + centerY;
        canvas.drawPoint(currX, currY, paint);
        while(count < numPoints) {
            float nextAngle = (float) ((rotationStep * skip * stepCount + currAngle) % (2 * Math.PI));
            float nextX = (float) (Math.cos(nextAngle) * radius) + centerX;
            float nextY = (float) (Math.sin(nextAngle) * radius) + centerY;
            canvas.drawLine(currX, currY, nextX, nextY, paint);
            float diff = Math.abs(nextAngle - currAngle);
            if (diff < 0.0001) {
                currAngle += rotationStep;
                stepCount = 0;
                nextX = (float) (Math.cos(currAngle) * radius) + centerX;
                nextY = (float) (Math.sin(currAngle) * radius) + centerY;
            }
            currX = nextX;
            currY = nextY;
            stepCount++;
            count++;
        }
    }
}
