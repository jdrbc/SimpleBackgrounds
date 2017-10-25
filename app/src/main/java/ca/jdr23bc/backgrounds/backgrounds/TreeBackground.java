package ca.jdr23bc.backgrounds.backgrounds;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Random;

import ca.jdr23bc.backgrounds.colors.ColorScheme;
import ca.jdr23bc.backgrounds.colors.Colour;
import ca.jdr23bc.backgrounds.utils.MathUtils;
import ca.jdr23bc.backgrounds.utils.RandomUtils;

// TODO-jdr refactor
// http://algorithmicbotany.org/papers/colonization.egwnp2007.html
public class TreeBackground extends Background {

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
    public int rootColor;
    public float leafWidthRatio;
    public float leafLength;
    public float leafWidestPoint;
    public boolean drawLeaves;
    public float branchWidthStep;
    public int minBranchWidth;
    public int maxBranchWidth;
    public int branchLength;
    public float additionalRandomness;
    public Random random = RandomUtils.random;
    public Paint paint;
    public ColorScheme colorScheme;
    public int leafColor;
    public int branchColor;
    public float width;
    public float height;

    public TreeBackground(int width, int height) {
        super(width, height);
    }

    public void init() {
        int maxWidth = getWidth();
        int maxHeight = getHeight();
        int heightRatio = random.nextInt(7) + 3;
        this.maxHeight = maxHeight * (heightRatio - 1) / heightRatio;
        int minRootHeight = maxHeight / 8;
        this.rootHeight = random.nextInt(maxHeight / 4) + minRootHeight;
        this.height = maxHeight;

        this.widthCenter = Math.round(maxWidth / 2);
        int ratio = random.nextInt(9) + 2;
        this.maxWidth = maxWidth * (ratio - 1) / ratio;
        this.width = maxWidth;

        this.minBranchWidth = random.nextInt(9) + 1;
        this.maxBranchWidth = random.nextInt(150) + 50;
        this.branchWidthStep = random.nextFloat() + 0.25f;
        this.branchLength = random.nextInt(20) + 5;

        this.minDist = RandomUtils.getRandomIntInRange(MIN_MIN_DISTANCE, MAX_MIN_DISTANCE);
        this.maxDist = Math.min(maxWidth, maxHeight);

        this.leafCount = RandomUtils.getRandomIntInRange(MIN_LEAF_COUNT, MAX_LEAF_COUNT);
        this.leafLength = (random.nextFloat() * 80) + 40;
        this.leafWidthRatio = random.nextFloat() + 0.5f;
        this.leafWidestPoint = Math.min(Math.max(random.nextFloat(), 0.1f), 0.9f);
        this.drawLeaves = random.nextBoolean();

        this.rootColor = Color.HSVToColor(new float[] { random.nextFloat() * 256, 128, 128 });
        this.colorScheme = new ColorScheme(rootColor);

        this.additionalRandomness = RandomUtils.getRandomFloatInRange(0, MAX_ADDITIONAL_RANDOMNESS);

        // Populate leaves
        for (int i = 0; i < leafCount; i++) {
            leaves.add(new Leaf(this));
        }

        // Root starts in middle bottom and grows straight up
        root = new Branch(new PointF(maxWidth / 2, maxHeight), new PointF(0, -1), this);
        paint = new Paint();
        paint.setAntiAlias(true);
        branches.add(root);

        fillBackground(getCanvas());
        paint.setShadowLayer(5.0f, 0.0f, 2.0f, colorScheme.popRandom());
        leafColor =  Colour.darken(colorScheme.popRandom(), 0.5f);
        branchColor = colorScheme.popRandom();
        grow();
    }


    private Boolean drawn = false;
    @Override
    public Boolean hasNextDrawStep() {
        return !drawn;
    }

    @Override
    public void drawStep() {
        drawn = true;
        paint.setColor(branchColor);
        drawBranches(getCanvas(), paint);
        if (drawLeaves) {
            paint.setColor(leafColor);
            drawLeaves(getCanvas(), paint);
        }
    }

    private void grow() {        // Need to ensure that the root is within range of a leaf
        boolean withinRange = false;
        Branch currBranch = root;
        while(!withinRange) {
            for (Leaf leaf : leaves) {
                Double dist = MathUtils.getDist(leaf.pos, currBranch.pos);
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
            ArrayList<Branch> branchesToGrow = new ArrayList<>();
            ArrayList<Leaf> connectedLeaves = new ArrayList<>();
            for (Leaf leaf : freeLeaves) {
                Branch closestBranch = null;
                double closestDist = 0;
                // Find the closest branch
                for (Branch branch : nearBranches) {
                    double dist = MathUtils.getDist(leaf.pos, branch.pos);

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

    private void fillBackground(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorScheme.popRandom());
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
        TreeBackground tree;

        // List of attracting points
        private ArrayList<PointF> curAttractors = new ArrayList<>();

        public Branch(PointF pos, PointF dir, TreeBackground tree) {
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
                attractedDir = MathUtils.add(attractedDir, MathUtils.normalize(MathUtils.sub(attractor, pos)));
                double dist = MathUtils.getDist(pos, attractor);
                if (dist < closestAttractorDist) {
                    closestAttractorDist = dist;
                }
            }

            double scale = 1 - (closestAttractorDist / maxDist);
            double randomnessScale = MIN_RANDOMNESS_SCALE + tree.additionalRandomness * scale;

            // Closer the closest attractor is the more random things get
            PointF randomness = MathUtils.mult(new PointF(RandomUtils.getRandomFloatInRange(-1, 1),
                    RandomUtils.getRandomFloatInRange(-1, 1)), randomnessScale);
            attractedDir = MathUtils.normalize(MathUtils.add(randomness, attractedDir));
            curAttractors.clear();
            return new Branch(MathUtils.add(pos, MathUtils.mult(attractedDir, tree.branchLength)), attractedDir, this);
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
        TreeBackground tree;

        public Leaf (TreeBackground t) {
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
            PointF topToPos = MathUtils.normalize(MathUtils.sub(pos, top));
            PointF tip = MathUtils.add(pos, MathUtils.mult(topToPos, tree.leafLength));
            PointF midTip = MathUtils.add(pos, MathUtils.mult(topToPos, tree.leafLength * leafWidestPoint));

            // Find sides
            float width =  tree.leafLength * tree.leafWidthRatio;
            PointF side1 = MathUtils.add(midTip, MathUtils.mult(new PointF(-topToPos.y, topToPos.x), width / 2));
            PointF side2 = MathUtils.add(midTip, MathUtils.mult(new PointF(topToPos.y, -topToPos.x), width / 2));

            leaf.quadTo(side1.x, side1.y, tip.x, tip.y);
            leaf.quadTo(side2.x, side2.y, pos.x, pos.y);
            leaf.close();

            // draw circle
            canvas.drawPath(leaf, paint);
        }
    }
}
