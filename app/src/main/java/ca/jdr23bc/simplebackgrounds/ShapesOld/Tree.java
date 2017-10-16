package ca.jdr23bc.simplebackgrounds.ShapesOld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import ca.jdr23bc.simplebackgrounds.colors.Colour;

// TODO-jdr horribly inefficient probably
// http://algorithmicbotany.org/papers/colonization.egwnp2007.html
public class Tree extends Shape {

    private static final int MAX_MIN_DISTANCE = 50;
    private static final int MIN_MIN_DISTANCE = 15;
    private static final int MAX_LEAF_COUNT = 1000;
    private static final int MIN_LEAF_COUNT = 50;
    private static final int MIN_RANDOMNESS_SCALE = 1;
    private static final int MAX_ADDITIONAL_RANDOMNESS = 5;

    public float minDist;
    public float maxDist;
    public int maxHeight;
    public int maxWidth;
    public int widthCenter;
    public int rootHeight;

    public ArrayList<Leaf> leaves = new ArrayList<>();
    public ArrayList<Branch> branches = new ArrayList<>();
    public ArrayList<Branch> endBranches = new ArrayList<>();
    public Branch root;

    public int leafCount;
    public float leafWidthRatio;
    public float leafLength;
    public float leafWidestPoint;
    public boolean drawLeaves;
    public float branchWidthStep;
    public int minBranchWidth;
    public int maxBranchWidth;
    public int branchLength;
    public float additionalRandomness;

    public static Double getAngle(PointF p1, PointF p2) { return Math.acos(p1.x * p2.x + p1.y * p2.y); }

    public static Double getDist(PointF p1, PointF p2) { return Math.hypot(p1.x - p2.x, p1.y - p2.y); }

    public static PointF add(PointF p1, PointF p2) { return new PointF(p1.x + p2.x, p1.y + p2.y); }

    public static PointF sub(PointF p1, PointF p2) { return new PointF(p1.x - p2.x, p1.y - p2.y); }

    public static PointF mult(PointF p1, int length) { return new PointF(p1.x * length, p1.y * length); }

    public static PointF mult(PointF p1, double length) { return new PointF(new Float(p1.x * length), new Float(p1.y * length)); }

    public static PointF normalize(PointF p) {
        Double len = getDist(p, new PointF(0, 0));
        return new PointF(new Float(p.x / len), new Float(p.y / len));
    }

    public Tree(int maxWidth, int maxHeight) {
        super(maxWidth, maxHeight);

        int heightRatio = random.nextInt(7) + 3;
        this.maxHeight = maxHeight * (heightRatio - 1) / heightRatio;
        int minRootHeight = maxHeight / 8;
        this.rootHeight = random.nextInt(maxHeight / 4) + minRootHeight;

        this.widthCenter = Math.round(maxWidth / 2);
        int ratio = random.nextInt(9) + 2;
        this.maxWidth = maxWidth * (ratio - 1) / ratio;

        this.minBranchWidth = random.nextInt(9) + 1;
        this.maxBranchWidth = random.nextInt(150) + 50;
        this.branchWidthStep = random.nextFloat() + 0.25f;
        this.branchLength = random.nextInt(20) + 5;

        this.minDist = nextIntInRange(MIN_MIN_DISTANCE, MAX_MIN_DISTANCE);
        this.maxDist = Math.min(maxWidth, maxHeight);

        this.leafCount = nextIntInRange(MIN_LEAF_COUNT, MAX_LEAF_COUNT);
        this.leafLength = (random.nextFloat() * 80) + 40;
        this.leafWidthRatio = random.nextFloat() + 0.5f;
        this.leafWidestPoint = Math.min(Math.max(random.nextFloat(), 0.1f), 0.9f);
        this.drawLeaves = random.nextBoolean();

        this.rootColor = Color.HSVToColor(new float[] { random.nextFloat() * 256, 128, 128 });

        this.additionalRandomness = nextFloatInRange(0, MAX_ADDITIONAL_RANDOMNESS);

        // Populate leaves
        for (int i = 0; i < leafCount; i++) {
            leaves.add(new Leaf(this));
        }

        // Root starts in middle bottom and grows straight up
        root = new Branch(new PointF(maxWidth / 2, maxHeight), new PointF(0, -1), this);
        branches.add(root);
    }

    public void grow() {        // Need to ensure that the root is within range of a leaf
        boolean withinRange = false;
        Branch currBranch = root;
        while(!withinRange) {
            for (Leaf leaf : leaves) {
                Double dist = getDist(leaf.pos, currBranch.pos);
                if (dist < maxDist) {
                    withinRange = true;
                }
            }

            // If not within range of a leaf
            if (!withinRange) {
                // Grow the root to get closer to a leaf
                currBranch = currBranch.next();
                branches.add(currBranch);
            }
        }

        ArrayList<Leaf> freeLeaves = new ArrayList<>();
        ArrayList<Branch> nearBranches = new ArrayList<>();
        nearBranches.addAll(branches);
        freeLeaves.addAll(leaves);

        int lastBranchesToGrowCount = 0;
        while(!freeLeaves.isEmpty()) {
            Log.d("test", "growing: " + freeLeaves.size());
            ArrayList<Branch> branchesToGrow = new ArrayList<>();
            ArrayList<Leaf> connectedLeaves = new ArrayList<>();
            for (Leaf leaf : freeLeaves) {
                Branch closestBranch = null;
                double closestDist = 0;
                // Find the closest branch
                for (Branch branch : nearBranches) {
                    double dist = getDist(leaf.pos, branch.pos);

                    if (dist < minDist) {
                        // Leaf has been reached
                        connectedLeaves.add(leaf);
                        closestBranch = null;
                        break;
                    } else if (dist > maxDist) {
                        // Leaf is too far away
                    } else if (closestBranch == null || dist < closestDist) {
                        // Found a new closest branch
                        closestBranch = branch;
                        closestDist = dist;
                    }
                }

                // Point the branch towards the leaf
                if (closestBranch != null) {
                    closestBranch.addAttractor(leaf.pos);
                    if (!branchesToGrow.contains(closestBranch)) {
                        branchesToGrow.add(closestBranch);
                    }
                }
            }

            // Prune any connected leaves
            freeLeaves.removeAll(connectedLeaves);
            nearBranches = new ArrayList<>();

            // Grow any attracted branches
            for (Branch branch : branchesToGrow) {
                Branch next = branch.next();
                nearBranches.add(branch);
                nearBranches.add(next);
                endBranches.remove(branch);
                endBranches.add(next);
                branches.add(next);
            }

            // Sometimes a branch will get stuck between a few leaves...
            if (lastBranchesToGrowCount == branchesToGrow.size()) {
                this.minDist += 0.05;
                this.maxDist += 0.1;
            }
            lastBranchesToGrowCount = branchesToGrow.size();
        }

        // Set branch widths
        for (Branch branch : endBranches) {
            branch.setWidth(minBranchWidth);
        }
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setShadowLayer(5.0f, 0.0f, 2.0f, colorScheme.popRandom());
        paint.setColor(colorScheme.popRandom());
        drawBranches(canvas, paint);
        if (drawLeaves) {
            int leafColor = Colour.darken(colorScheme.getRandom(), 0.5f);
            paint.setColor(leafColor);
            drawLeaves(canvas, paint);
        }
    }

    @Override
    public void fillBackground(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorScheme.colors.get(0));
        canvas.drawPaint(paint);
    }

    private void drawBranches(Canvas c, Paint p) {
        p.setStrokeCap(Paint.Cap.ROUND);
        for (Branch branch : branches) {
            branch.draw(c, p);
        }
    }

    private void drawLeaves(Canvas c, Paint p) {
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        for (Branch branch : endBranches) {
            new Leaf(branch).draw(c, p);
        }
    }

    private class Branch {
        float width = 0;
        PointF pos;
        PointF dir;
        Branch parent;
        Tree tree;

        // List of attracting points
        private ArrayList<PointF> curAttractors = new ArrayList<>();

        public Branch(PointF pos, PointF dir, Tree tree) {
            this.pos = pos;
            this.dir = dir;
            this.tree = tree;
        }

        public Branch(PointF pos, PointF dir, Branch parent) {
            this.tree = parent.tree;
            this.pos = pos;
            this.dir = dir;
            this.parent = parent;
        }

        public Branch next() {
            PointF attractedDir = new PointF(dir.x, dir.y);
            double maxDist = Math.max(tree.width, tree.height);
            double closestAttractorDist = maxDist;
            for (PointF attractor : curAttractors) {
                attractedDir = add(attractedDir, normalize(sub(attractor, pos)));
                double dist = getDist(pos, attractor);
                if (dist < closestAttractorDist) {
                    closestAttractorDist = dist;
                }
            }

            double scale = 1 - (closestAttractorDist / maxDist);
            double randomnessScale = MIN_RANDOMNESS_SCALE + tree.additionalRandomness * scale;

            // Closer the closest attractor is the more random things get
            PointF randomness = mult(new PointF(nextFloatInRange(-1, 1), nextFloatInRange(-1, 1)), randomnessScale);
            attractedDir = normalize(add(randomness, attractedDir));
            curAttractors.clear();
            return new Branch(add(pos, mult(attractedDir, tree.branchLength)), attractedDir, this);
        }

        public void setWidth(float width) {
            this.width = Math.min(width, tree.maxBranchWidth);
            if (this.parent != null && this.parent.width < width) {
                this.parent.setWidth(width + tree.branchWidthStep);
            }
        }

        public void draw(Canvas canvas, Paint paint) {
            // Draw line
            if (parent != null) {
                paint.setStrokeWidth(width);
                canvas.drawLine(pos.x, pos.y, parent.pos.x, parent.pos.y, paint);
            }
        }

        public void addAttractor(PointF p) {
            curAttractors.add(p);
        }
    }

    private class Leaf {
        PointF pos;
        Tree tree;

        public Leaf (Tree t) {
            this.tree = t;
            this.pos = new PointF(
                    widthCenter + (t.maxWidth / 2 - t.random.nextInt(t.maxWidth)),
                    t.random.nextInt(t.maxHeight - t.rootHeight));
        }

        public Leaf (Branch b) {
            this.tree = b.tree;
            this.pos = b.pos;
        }

        public void draw(Canvas canvas, Paint paint) {
            Path leaf = new Path();
            leaf.moveTo(pos.x, pos.y);

            // Get vector from middle top of screen to the leaf to get tip of leaf
            PointF top = new PointF(random.nextInt(canvas.getWidth() / 2) + canvas.getWidth() / 4, -canvas.getHeight()/4);
            PointF topToPos = normalize(sub(pos, top));
            PointF tip = add(pos, mult(topToPos, tree.leafLength));
            PointF midTip = add(pos, mult(topToPos, tree.leafLength * leafWidestPoint));

            // Find sides
            float width =  tree.leafLength * tree.leafWidthRatio;
            PointF side1 = add(midTip, mult(new PointF(-topToPos.y, topToPos.x), width / 2));
            PointF side2 = add(midTip, mult(new PointF(topToPos.y, -topToPos.x), width / 2));

            leaf.quadTo(side1.x, side1.y, tip.x, tip.y);
            leaf.quadTo(side2.x, side2.y, pos.x, pos.y);
            leaf.close();

            // draw circle
            canvas.drawPath(leaf, paint);
        }
    }
}
